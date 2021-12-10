package newbank.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NewBankClientHandler extends Thread{
	
	private NewBank bank;
	private BufferedReader in;
	private PrintWriter out;
	
	
	public NewBankClientHandler(Socket s) throws IOException {
		bank = NewBank.getBank();
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
	}
	
	public void run() {	
		try {
			while(true) {
				///////////////////
				// 1. Process initial request (login or register)
				///////////////////
				String intialCommand = greetUser();

				///////////////////
				// 2. Control flow for request
				/////////////////// 
				CustomerID customer = null;

				processloop:
				while(true) {
					if(intialCommand.equals("LOGIN")) {
						customer = login();
					} 
					else {
						//ask for user name
						out.println("Please enter a valid username (Must be between 6 and 15 characters with no spaces)");
						String userName = in.readLine();
						//ask for password
						out.println("Please enter a valid password (Must be between 9 and 15 characters with a combination of uppercase letters, lowercase letters and numbers with no spaces)");
						String password = in.readLine();
						out.println("Checking Details...");
						//Validate account details
						if(bank.isPasswordValid(password) && bank.isUserNameValid(userName)) {
							bank.registerCustomer(userName, password);
							out.println("Registration successful. You can now log in");
							while(customer == null) {
								customer = login();
							}
						}
						
						else {
							customer = null;
							out.println("Registration failed. The username and password entered must be valid, please try again");
						}	
					}

					/////////////////// 
					// 3. Process Commands upon authentication
					/////////////////// 
					if(customer != null) {
						out.println("SHOWMYACCOUNTS - view your accounts and their balances");
						out.println("RESETPASSWORD <Password> - reset login password");
						out.println("CREATEACCOUNT <Account name> <Opening balance> - create new bank account");
						out.println("MOVE <Amount> <Account name 1> <Account name 2> - transfer amount from Account 1 to Account 2");
						out.println("SHOWMYLENDINGS - show MicroLoan lending activity");
						out.println("SHOWMYBORROWINGS - show MicroLoan borrowing activity");
						out.println("PAYMYLOANS <loanID> <Amount> - pay off a Microloan balance");
						out.println("LOGOUT - logout");
						
						// keep getting requests from the client and processing them if authenticated
						while(true) {
							String request = in.readLine();
							System.out.println("Request from " + customer.getKey());
							String response = bank.processRequest(customer, request);
							out.println(response);
							//End processloop (initiated on LOGOUT)
							if(response.equals("END")) {
								break processloop;
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}

	public String greetUser() throws IOException {
		out.println(
		"####################\n" +
		"Welcome to New Bank\n" +
		"####################");
		out.println("Type LOGIN if you are an existing user, or REGISTER if you are a new user");
		String intialCommand = in.readLine();
		boolean requestValid = false;
		while(!requestValid) {
			if(intialCommand.equals("LOGIN") || intialCommand.equals("REGISTER")) {
				requestValid = true;
			}
			else {
				out.println("Command not recognised. Please enter command LOGIN or REGISTER");
				intialCommand = in.readLine();
			}
		}
		return intialCommand;
	}

	public CustomerID login() throws IOException {
		CustomerID customer = null;
		// ask for user name
		out.println("Enter Username");
		String userName = in.readLine();
		// ask for password
		out.println("Enter Password");
		String password = in.readLine();
		out.println("Checking Details...");
		customer = bank.checkLogInDetails(userName, password);
		//Validate login details
		if(customer == null) {
			out.println("Log In Failed. Invalid Credentials, please try again.");
		}
		else {
			out.println("Log In Successful. What do you want to do?");
		}
		return customer;
	}

}
