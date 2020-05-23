package sobelRMI;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int port = 9000;
	
	private int generatePort(int endPort) {
		if (endPort>9) {
			return (9020 + endPort);
		}else
			return(9010 + endPort);
	}
	
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
