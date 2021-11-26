package newbank.server;

public class Account {
	
	private String accountName;
	private double openingBalance;

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
	}

	public static boolean checkAccountName(String accountName) {
		if(accountName.equals("Main") || accountName.equals("Savings") || accountName.equals("Loan")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String toString() {
		return (accountName + ": " + openingBalance);
	}

}
