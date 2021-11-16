package newbank.server;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;
	
	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}
	
	private void addTestData() {
		Customer bhagy = new Customer();
		bhagy.addAccount(new Account("Main", 1000.0));
		customers.put("Bhagy", bhagy);
		
		Customer christina = new Customer();
		christina.addAccount(new Account("Savings", 1500.0));
		customers.put("Christina", christina);
		
		Customer john = new Customer();
		john.addAccount(new Account("Checking", 250.0));
		customers.put("John", john);
	}
	
	public static NewBank getBank() {
		return bank;
	}
	
	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		//Check username
		if(customers.containsKey(userName)) {
			//Check password
			Customer customer = customers.get(userName);
			if(customer.getPassword().equals(password)) {
				return new CustomerID(userName);
			}
		}
		return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request) {
		if(customers.containsKey(customer.getKey())) {
			switch(request) {
			case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
			default : return "FAIL";
			}
		}
		return "FAIL";
	}

	//Method to check whether a password is valid (upon registration)
	//Validation: must contain at least one uppercase character, one lowercase character, one number and no spaces
	//Must also be in character range
	public boolean isPasswordValid(String password) {
		String[] badPasswords = {"PASSWORD", "password", "123456789"};
		int upperCharLimit = 15;
		int lowerCharLimit = 9;

		//False if password is common example of bad password
		for(String x : badPasswords) {
			if(x.equals(password)) {
				return false;
			}
		}

		//Check password matches Regex pattern and is in character range
		Pattern pattern = Pattern.compile("(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(^\\S+$)");
		Matcher matcher = pattern.matcher(password);
		boolean match = matcher.find();
		int strLen = password.length();

		if(match && strLen >= lowerCharLimit && strLen <= upperCharLimit) {
			return true;
		} 
		else {
			return false;
		}
	}

	//Method to check whether a username is valid (upon registration)
	//Validation: username is unique, in character range and contains no spaces
	public boolean isUserNameValid(String userName) {
		boolean duplicate = customers.containsKey(userName);
		int upperCharLimit = 15;
		int lowerCharLimit = 6;
		int strLen = userName.length();

		if(!duplicate && strLen >= lowerCharLimit && strLen <= upperCharLimit && !userName.contains(" ")) {
			return true;
		}
		else {
			return false;
		}
	}

	public CustomerID registerCustomer(String userName, String password) {
		Customer customer = new Customer();
		customer.setPassword(password);
		customers.put(userName, customer);
		return new CustomerID(userName);
	}
	
	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

}
