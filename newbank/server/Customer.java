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
}
