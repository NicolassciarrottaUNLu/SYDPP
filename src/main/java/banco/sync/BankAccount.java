package banco.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BankAccount {

	private final static Logger log = LoggerFactory.getLogger(BankAccount.class);
	private double balance;
	
	public BankAccount(double balance) {
		this.balance=balance;
		log.info("Bank account created");
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public void deposit(double deposit) {
		try {
			Thread.sleep(80);
			this.balance += deposit;
			log.info("Successful deposit");
			System.out.println("Your balance is " + balance);
		} catch (InterruptedException e) {
			log.error("Error - " + e.getMessage());
		}
		
	}
	
	public void extract(double extract) {
		try {
			Thread.sleep(60);
				if (balance>=extract) {
					balance -= extract;
					log.info("Successful extract");
					System.out.println("Your balance is " + balance);
				}else {
					System.out.println("Your balance is insufficient to make this action");
				}
		} catch (InterruptedException e) {
			log.error("Error - " + e.getMessage());
		}
	}
}
