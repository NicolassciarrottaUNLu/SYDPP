package red_flexible;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Random;

public class Client {

	private int id_client;
	private String _SERVER = "localhost";
	private int _PORT = 8000;
	private Random rnd = new Random();
	private IRemoteInt iRserver;
	private Registry clientRMI;
	
	public Client(int id_client) {
		this.id_client=id_client;
	}
	
	

	private static String writeArray (ArrayList<Integer> list, String espacio) {
		String devuelvo="";
		for (Integer integer : list) {
			devuelvo=devuelvo +" [" + integer +"]" + espacio;
		}	
		return (devuelvo);
	}
	
	public void clientUp() throws NotBoundException, InterruptedException {
		
		try {
			
			ArrayList<Integer>numerosA = new ArrayList<Integer>();
			ArrayList<Integer>numerosB = new ArrayList<Integer>();
			
			clientRMI = LocateRegistry.getRegistry(_SERVER,_PORT);
			iRserver = (IRemoteInt) clientRMI.lookup("service");
			
			numerosA.add(rnd.nextInt(20));numerosA.add(rnd.nextInt(20));numerosA.add(rnd.nextInt(20));numerosA.add(rnd.nextInt(20));
			numerosB.add(rnd.nextInt(20));numerosB.add(rnd.nextInt(20));numerosB.add(rnd.nextInt(20));numerosB.add(rnd.nextInt(20));
			
				System.out.println("Client n°" + id_client);
				System.out.println("Array 1 = " + writeArray(numerosA, " "));
				System.out.println("Array 2 = " + writeArray(numerosB, " "));
				
			
			boolean finished = false;
			int intentos = 0;
			while (!finished) {
				try {
					if (iRserver.suma(numerosA, numerosB) != null) {
						finished = true;
						System.out.print("SUMA = " + writeArray(iRserver.suma(numerosA,numerosB), " "));
					}
				}catch(RemoteException e1) {
					Thread.sleep(3000);
					intentos++;
				}
				
				if(intentos>10) {
					System.out.println("[Error]");
					finished=true;
				}
				
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
			}
		
	}




}
