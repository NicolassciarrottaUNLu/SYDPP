package banco.noSync;

public class ThreadExtract implements Runnable {

	private BankAccount bankAccount;
	private double extract;
	
	public ThreadExtract(BankAccount bankAccount, double extract) {
		this.bankAccount=bankAccount;
		this.extract = extract;
	}

	@Override
	public void run() {

				this.bankAccount.extract(extract);
		
		
	}

}
