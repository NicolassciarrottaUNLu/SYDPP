package banco.noSync;

public class ClientBank implements Runnable{
	
	private int id_client;
	private double extract;
	private double deposit;
	private BankAccount bankAccount;



	public ClientBank(int id_client, double extract, double deposit, BankAccount bankAccount ) {
		super();
		this.id_client = id_client;
		this.extract = extract;
		this.deposit = deposit;
		this.bankAccount = bankAccount;
	}

	public String clientToString() {
		return ("Client: " + id_client+ ", extract: " + extract + ", deposit:" + deposit);
	}

	public double diference() {
		return deposit-extract;
	}
	@Override
	public void run() {
		ThreadDeposit threadDeposit = new ThreadDeposit(bankAccount, deposit);
		ThreadExtract threadExtract = new ThreadExtract(bankAccount, extract);
		Thread tThreadDeposit = new Thread(threadDeposit);
		Thread tThreadExtract = new Thread(threadExtract);
		tThreadDeposit.start();
		tThreadExtract.start();
	}


}
