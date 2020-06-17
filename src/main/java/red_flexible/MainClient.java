package red_flexible;

import java.rmi.NotBoundException;

public class MainClient {

	public static void main(String[] args) throws NotBoundException, InterruptedException {
		Client client = new Client(1);
		client.clientUp();

	}

}
