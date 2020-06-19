package sobelBalanceado;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Balanceador{
	
	private int _PORT = 9000;
	private String _SERVER = "localhost";
	private ArrayList<Server> listServer = new ArrayList<Server>();
	private static Logger log = LoggerFactory.getLogger(Balanceador.class);
	private static ISobel isobel ;
	
	private int generatePort(int endPort) {
		if (endPort>9) {
			return (9020 + endPort);
		}else
			return(9010 + endPort);
	}
		
	/*
	 * create a new node and new thread
	 */
	public Server newServer(){
		Server server = null;
			synchronized(listServer){
				server = new Server(generatePort(listServer.size()),listServer.size(),_SERVER);
					
				Thread tserver = new Thread(server); //Create a new Thread
				tserver.start();
				listServer.add(server);
				}
		log.info("Server "+ server.getDirection() +" created");	
		return server;
	}
	
	
	
	public void initBalancer() throws RemoteException {
		Remote iSobel = UnicastRemoteObject.exportObject( new ISobel() {
		int fallidas = 0;

			@Override
			public Imagen send(Imagen image) throws RemoteException {
				Registry registry=null;
				ArrayList<Imagen> imagePartsCut = new ArrayList<Imagen>();
				ArrayList<Imagen> results = new ArrayList<Imagen>();
				ImageManipulation im = null;
				Imagen finalImagen;
				BufferedImage a = null;
				ArrayList<BufferedImage> imageParts = null;
			
					try {
						im = new ImageManipulation(image.getImage(), image.get_CORTES());
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					
					imageParts = im.cutImage();
					
					for(int i=0;i<imageParts.size();i++) {
							try {
								isobel = convert(registry, i, fallidas, imageParts.get(i), imageParts, imagePartsCut);
								results.add(isobel.send(imagePartsCut.get(i)));
								continue;
							}catch(Exception e1) {
								
							}
							
							
					}
					
					
					
					ArrayList<BufferedImage> returnThis = new ArrayList<BufferedImage>();
					for(Imagen ima : results) {
						returnThis.add(ima.getImage());
					}
					
					a = im.joinImage(returnThis);
				
				
				
				//devuelvo imagen
				finalImagen = new Imagen(a,0,0);
				for(Server server : listServer) {
					server.serverStop();
				}
				
				
				
				return finalImagen;
				
			
			}

			private ISobel convert(Registry registry, int i, int fallidas, BufferedImage bufferedImage,ArrayList<BufferedImage> imageParts,
					ArrayList<Imagen> imagePartsCut) throws AccessException, RemoteException, NotBoundException {
				try {
					Imagen e = new Imagen(imageParts.get(i),1,i);
					imagePartsCut.add(e);
					newServer();
					registry = LocateRegistry.getRegistry(listServer.get(i).getPort());
					isobel = (ISobel) registry.lookup("serviceServer");
				} catch (RemoteException|NotBoundException e1) {
					fallidas++;
					System.out.println("Server " + listServer.get(i).getDirection() +" fail. Fail n°:" + fallidas);
					listServer.get(i).serverStop();
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e3) {
						
					}
					convert(registry, i-1, fallidas, bufferedImage, imageParts, imagePartsCut);
					
				}
				
				
				return isobel;
			}
			
		},0);
		
		
			Registry registry = LocateRegistry.createRegistry(_PORT);
			registry.rebind("service", iSobel);
			log.info("Balancer RMI initiated on port " + _PORT);
		
		
	}
	
	
}