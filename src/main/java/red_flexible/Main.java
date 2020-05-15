package red_flexible;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws RemoteException, NotBoundException {
		Balanceador balancer = new Balanceador();
		balancer.newServer();
		balancer.newServer();
		balancer.newServer();
		balancer.initBalancer();
				
		Scanner sc = new Scanner(System.in);
	
		
			for (int i=0; i<10; i++) {
				Client client = new Client(i);
				client.clientUp();
				sc.nextLine();
			}
		


	}          
}
/*
 * new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					balancer.initBalancer();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}).start();

 */