package newbank.server;

public class Account {
	
	private String accountName;
	private double openingBalance;
	private double currentBalance;


	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
	}

	public static boolean isNameValid(String accountName) {
		if(accountName.equals("Main") || accountName.equals("Savings") || accountName.equals("Loan")) {
			return true;
		}
		else {
			return false;
		}
	}

	public String getAccountName() {
		return this.accountName;
	}

	public String toString() {
		return (accountName + ": " + openingBalance);
	}
	
	// no transactions yet affecting the current balance 
	public double getCurrentBalance() {
		currentBalance = openingBalance;
		return currentBalance;
	}
	

}
