package newbank.server;

import java.util.ArrayList;

public class Customer {
	
	private ArrayList<Account> accounts;
	private String password = "PASSWORD"; //default for now
	
	public Customer() {
		accounts = new ArrayList<>();
	}
	
	public String accountsToString() {
		String s = "";
		for(Account a : accounts) {
			s += a.toString() + "\n";
		}
		return s;
	}

	//Check if customer already has an account with the same name
	public boolean isDuplicateAccount(String accountName) {
		for(Account account : accounts) {
			if(account.getAccountName().equals(accountName)) {
				return true;
			}
		}
		return false;
	}

	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String s) {
		this.password = s;
	}

	public void addAccount(Account account) {
		accounts.add(account);		
	}
	
	public boolean isAmountAvailable(String accountName, String amount) {
		double parsedAmount = Double.parseDouble(amount);
		for (Account a : accounts){
			if (a.getAccountName().equals(accountName)) {
				if (a.getCurrentBalance() - parsedAmount >= 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void addToAccount(String accountName, String amount) {
		double parsedAmount = Double.parseDouble(amount);
		for (Account a : accounts){
			if (a.getAccountName().equals(accountName)) {
				a.addToAccount(parsedAmount);
			}
		}
	}
	public void removeFromAccount(String accountName, String amount) {
		double parsedAmount = Double.parseDouble(amount);
		for (Account a : accounts){
			if (a.getAccountName().equals(accountName)) {
				a.removeFromAccount(parsedAmount);
			}
		}
	}
}
