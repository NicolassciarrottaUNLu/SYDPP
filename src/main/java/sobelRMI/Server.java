package sobelRMI;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server{


	private static int port = 9000;
	
	public static void main(String[] args) {
		
		Registry serverRMI;
		try {
			serverRMI = LocateRegistry.createRegistry(port);
			Worker worker = new Worker();
			ISobel service= (ISobel) UnicastRemoteObject.exportObject(worker,port);
			serverRMI.rebind("service", service);
			System.out.println("Server RMI has created as port " + port);
		} catch (RemoteException e) {	
			e.printStackTrace();
		}
		
	}
}
