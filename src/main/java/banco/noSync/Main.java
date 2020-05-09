package banco.noSync;

import java.util.ArrayList;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		
		double initial = 10000;
		double sum = initial;
		try {
			BankAccount bankAccount = new BankAccount(initial);
			Random random = new Random();		
			ArrayList<Thread> threads = new ArrayList<Thread>();
			Thread t;
			
			
			for(int i=0; i<5; i++) {
				ClientBank client = new ClientBank(i,random.nextInt(5000),random.nextInt(5000),bankAccount);
				System.out.println(client.clientToString());
				sum += client.diference();
				t = new Thread(client);
				threads.add(t);
				t.start();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Your balance final is: "+ sum +"?");
		
		
		
		
		
		
	}

}
