package banco.sync;

public class ThreadDeposit implements Runnable {

	private BankAccount bankAccount;
	private double deposit;
	
	public ThreadDeposit(BankAccount bankAccount, double deposit) {
		this.bankAccount=bankAccount;
		this.deposit=deposit;
		
	}

	@Override
	public void run() {
				synchronized (this.bankAccount) {
					this.bankAccount.deposit(deposit);
				}
	}

}
