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
			s += a.toString();
		}
		return s;
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
