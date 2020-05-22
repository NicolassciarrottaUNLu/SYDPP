package sobelRMI;

import java.rmi.RemoteException;

public class MainBalancer {

	public static void main(String[] args) {
		Balanceador b = new Balanceador();
		try {
			b.initBalancer();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
