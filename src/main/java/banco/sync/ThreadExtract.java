package banco.sync;

public class ThreadExtract implements Runnable {

	private BankAccount bankAccount;
	private double extract;
	
	public ThreadExtract(BankAccount bankAccount, double extract) {
		this.bankAccount=bankAccount;
		this.extract = extract;
	}

	@Override
	public void run() {
			synchronized(this.bankAccount) {
				this.bankAccount.extract(extract);
		}
		
	}

}
