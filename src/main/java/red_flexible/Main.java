package red_flexible;

import java.rmi.NotBoundException;

public class Main {
	private void newClient(final int n) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Client c = new Client(n);
				try {
					c.clientUp();
					Thread.sleep(200);
				} catch (NotBoundException | InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}).start();
		
		
	}
	
	public Main() {
		for (int i=0; i<20; i++) {
			newClient(i+1);
		}
	}
	
	public static void main(String[] args) throws Exception {	
		Balanceador balancer = new Balanceador();
		balancer.initBalancer();
		new Main();
		

	}          
}
