package sobelRMI;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class Balanceador{


	/*
	 * @Threshold definition
	 * 0 clients -> light 
	 * 0-3 clients -> normal
	 * 4 clients -> alert
	 * 5 clients -> critic
	 * 
	 */
	
	/**
	 * 
	 */
	private int _LIGHT = 0; 
	private int _NORMAL= 3; 
	private int _ALERT = 4;
	private int _CRITIC = 5;
	private int _PORT = 8000;
	private int _INITIALSERVERS = 3;
	private String _SERVER = "localhost";
	private ArrayList<Server> listServer = new ArrayList<Server>();
	private static Logger log = LoggerFactory.getLogger(Balanceador.class);
	private ImageManipulation imageManipulation;
	
	
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
				server = new Server(generatePort(listServer.size()),listServer.size(),_SERVER,_LIGHT);
				Thread tserver = new Thread(server);
				tserver.start();
				listServer.add(server);
				}
		log.info("Server "+ server.getDirection() +" created");	
		return server;
	}
	
	
	public void stopServer(Server server) throws NotBoundException {
		try {
			Registry registry = LocateRegistry.getRegistry(server.getPort());
			IControl remoteInt = (IControl) registry.lookup("service");
			remoteInt.serverStop();
			synchronized(listServer) {
				listServer.remove(server);
			}
			
		} catch (NumberFormatException |RemoteException e) {
			log.error("Error - Can't stop server");
		}
	}
	
	
	public synchronized  Server assignTask() {
		Collections.sort(listServer, new SortMyList());
		Server server = listServer.get(0);
		String direction = server.getDirection();
			if(server.getTasks()<=_NORMAL) {
				server.addTasks();
				log.info("SERVER " + server.getDirection() + " STATUS is NORMAL");
				log.info("Task assigned to " + server.getDirection());
			}else if((server.getTasks()>=_ALERT) && (server.getTasks()<_CRITIC)) { 
				server.addTasks();
				log.info("SERVER " + server.getDirection() + " STATUS is ALERT");
				log.info("Task assigned to " + server.getDirection());
			}else if(server.getTasks()==_CRITIC){
				Server nNew = newServer(); //Creo el nodo pero asigno la tarea al nodo anterior
				server.addTasks();
				log.info("SERVER " + direction + " STATUS is CRITIC. This server isn't work for now");
				log.info("New server has created as " + nNew.getDirection());
				log.info("Task assigned to " + nNew.getDirection());
			}
		
	return server;
	}
	
	
	public synchronized void terminateTask(int serverPosition) throws NotBoundException, InterruptedException {
		synchronized (listServer) {
			listServer.get(serverPosition).substracTasks();
			log.info("Task terminated by server: " + listServer.get(serverPosition).getDirection());
			if ((listServer.get(serverPosition).getTasks()==0) && (listServer.size()>_INITIALSERVERS)) {
				stopServer(listServer.get(serverPosition));
			}
		}
	}
	
	private ArrayList<BufferedImage> distributeImage(BufferedImage image, int _PARTS) {
		ArrayList<BufferedImage> imageParts = null;
		try {
			imageManipulation = new ImageManipulation(image, _PARTS);
			imageParts = imageManipulation.cutImage();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return imageParts;
		
	}
	
	
	public void initBalancer() throws RemoteException {
		Remote iRemote = UnicastRemoteObject.exportObject( new ISobel() {
		
			@Override
			public BufferedImage applyFilter(BufferedImage image, int _PARTS) throws RemoteException{
				Registry registry;
				BufferedImage result = null;
					
					try {
						Server server = assignTask();
						int i = server.getPosition();
						registry = LocateRegistry.getRegistry(server.getPort());
						ISobel iSobelBalancer;
						iSobelBalancer = (ISobel) registry.lookup("serviceServer");
						result = iSobelBalancer.applyFilter(image,1);
						terminateTask(i);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				return result;
			}
			
		},0);
		
		if (listServer.size()==0) {
			Registry registry = LocateRegistry.createRegistry(_PORT);
			registry.rebind("service", iRemote);
			log.info("Balancer RMI initiated on port " + _PORT);
		}
		
	}
	
	
}