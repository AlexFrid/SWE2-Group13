package newbank.server;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;
	private ArrayList<Loan> loans;
	
	private NewBank() {
		customers = new HashMap<>();
		loans = new ArrayList<>();
		addTestData();
	}
	
	private void addTestData() {
		Customer bhagy = new Customer();
		bhagy.addAccount(new Account("Main", 1000.0));
		bhagy.addAccount(new Account("Loan", 0));
		customers.put("Bhagy", bhagy);
		
		Customer christina = new Customer();
		christina.addAccount(new Account("Savings", 1500.0));
		christina.addAccount(new Account("Loan", 1500.0));
		customers.put("Christina", christina);
		
		Customer john = new Customer();
		john.addAccount(new Account("Checking", 250.0));
		customers.put("John", john);

		loans.add(new Loan("testLoan", new CustomerID("Bhagy"), new CustomerID("Christina"), 300.00));
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
		//Splits user input on a space to get command parameters
		String[] requestParams = request.split("\\s+");
		String command = requestParams[0];

		if(customers.containsKey(customer.getKey())) {
			switch(command) {
			//show customer's accounts
			case "SHOWMYACCOUNTS" : 
				return showMyAccounts(customer);
			
			//reset customer's password
			case "RESETPASSWORD" :
				if(requestParams.length != 2) {
					return "Not enough or too many arguments have been supplied for this command";
				}
				
				String password = requestParams[1];
				if(isPasswordValid(password)) {
					resetPassword(customer, password);
					return "Password has been successfully reset";
				}
				return "Password has not been reset - new password is invalid. Must be between 9 and 15 characters with a combination of uppercase letters, lowercase letters and numbers with no spaces";
			
			//create an account for a customer
			case "CREATEACCOUNT" :
				if(requestParams.length != 3) {
					return "Not enough or too many arguments have been supplied for this command";
				}

				String accountName = requestParams[1];
				String openingBalance = requestParams[2];
				boolean existingAccount = customers.get(customer.getKey()).isDuplicateAccount(accountName);

				if(existingAccount) {
					return "Account has not been created - an account already exists with this name";
				}
				else if(!Account.isNameValid(accountName)) {
					return "Account has not been created - account name is invalid. Account name must be either Main, Savings or Loan";
				}
				else {
					createAccount(customer, accountName, Double.parseDouble(openingBalance));
					return "Account has been successfully created";
				}

			// move cash between accounts 
			case "MOVE" :
				if(requestParams.length != 4) {
					return "Not enough or too many arguments have been supplied for this command";
				}
				String amountToMove = requestParams[1];
				String account1 = requestParams[2];
				String account2 = requestParams[3];
				
				boolean validAccount1 = customers.get(customer.getKey()).isDuplicateAccount(account1);
				boolean validAccount2 = customers.get(customer.getKey()).isDuplicateAccount(account2);
				boolean validAmount = customers.get(customer.getKey()).isAmountAvailable(account1, amountToMove);			
				if(!validAccount1 ||!validAccount2 ) {
					return "Please enter valid account names to complete the transaction";
				}
				else if (!validAmount) {
					return "Insufficient funds";
				}
				else {
					customers.get(customer.getKey()).addToAccount(account2, amountToMove);
					customers.get(customer.getKey()).removeFromAccount(account1, amountToMove);
					return "Funds have been transferred successfully";
				}
			
			//show customer's MicroLoan lending activity
			case "SHOWMYLENDINGS" :
				if(requestParams.length != 1) {
					return "Not enough or too many arguments have been supplied for this command";
				}

				return showMyLendings(customer);
			
			//show customer's MicroLoan borrowing activity
			case "SHOWMYBORROWINGS" :
				if(requestParams.length != 1) {
					return "Not enough or too many arguments have been supplied for this command";
				}

				return showMyBorrowings(customer);
			
			//allow a customer to pay a microloan
			case "PAYMYLOANS" :
				if(requestParams.length != 3) {
					return "Not enough or too many arguments have been supplied for this command";
				}

				String loanID = requestParams[1];
				String amount = requestParams[2];

				for(Loan l : loans) {
					if(l.getLoanID().equals(loanID)) {
						CustomerID borrower = l.getBorrower();
						CustomerID lender = l.getLender();

						//Add to lender's account
						customers.get(lender.getKey()).addToAccount("Loan", amount);
						//Remove from borrower's account
						customers.get(borrower.getKey()).removeFromAccount("Loan", amount);
						//Update Loan balance
						l.decreaseBalance(amount);
						return "Loan balance has been successfully updated";
					}
				}

			//log customer out
			case "LOGOUT" :
				if(requestParams.length != 1) {
					return "Not enough or too many arguments have been supplied for this command";
				}

				return "END";

			default : 
				return "FAIL";
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

	private void resetPassword(CustomerID customer, String password) {
		customers.get(customer.getKey()).setPassword(password);
	}

	private void createAccount(CustomerID customer, String accountName, double openingBalance) {
		customers.get(customer.getKey()).addAccount(new Account(accountName, openingBalance));
	}
	
	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

	public String showMyLendings(CustomerID customer) {
		String s = "";
		for(Loan l : loans) {
			if(l.getLender().getKey().equals(customer.getKey())) {
				s += l.toString() + "\n";
			}
		}
		return s;
	}

	public String showMyBorrowings(CustomerID customer) {
		String s = "";
		for(Loan l : loans) {
			if(l.getBorrower().getKey().equals(customer.getKey())) {
				s += l.toString() + "\n";
			}
		}
		return s;
	}

}
