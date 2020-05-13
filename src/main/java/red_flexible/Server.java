package red_flexible;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server implements Runnable, IControl{

	private int _PORT;
	private Registry serverRMI;
	private Logger log = LoggerFactory.getLogger(Server.class);
	
	
	
	public Server(int _PORT) {
		this._PORT = _PORT;
	}
	
			
	public void run() {
		
		try {
			serverRMI = LocateRegistry.createRegistry(_PORT);
			ServerImplementer serverImplementer = new ServerImplementer();
			IRemoteInt service = (IRemoteInt) UnicastRemoteObject.exportObject(serverImplementer,_PORT);
			serverRMI.rebind("service", service);
			System.out.println("Server RMI has created as port: " + _PORT);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void serverStop() throws RemoteException {
		try {
			serverRMI.unbind("service");
			log.info("Server unbinded");
		} catch (RemoteException | NotBoundException e) {
			log.error("Error - Fail to unbind server");
		}
	}
}
