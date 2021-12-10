# <center>Skeleton Code Functionality Directory</center>
## <center>What functionality already exists in the Skeleton Code?</center>
### <center>Account.java</center>

```java
public class Account
// This class contains all other methods and variables listed below:

private String accountName
// This is a private String that stores the account name.

private double openingBalance
// This is a private double that stores the opening account balance.

public Account(String accountName, double openingBalance)
// This constructor takes an account name and an opening balance and populates an account with these details.

public String toString()
/* This method returns the account name in question (call the account name in the function) along with a colon that seperates the balance and displays an opening balance to the right of the account name. */
```
### <center>Customer.java</center>

```java
public class Customer
// This class contains all other methods and variables listed below:

private ArrayList<Account> accounts
// An arraylist of customer accounts.

public Customer()
// This constructor initializes the accounts using a new ArrayList<>();

public String accountsToString()
// This method initializes a String variable s, iterates using a for loop over accounts and appends each member of accounts as a string into s.

public void addAccount(Account account)
// This method adds the specified account into the accounts ArrayList.
```
### <center>CustomerID.java</center>

```java
public class CustomerID(String key)
// This class contains all other functions and variables listed below:

private String key
// A String variable intended for the key value.

public CustomerID(String key)
// Method that takes the String key value and appends it to the key instance.

public String getKey()
// Returns the value of key.
```
### <center>NewBank.java</center>

```java
public class NewBank
// This class contains all other methods and variables listed below:

private static final NewBank bank = new NewBank();
// NewBank object that is initialized on using new NewBank().

private HashMap<String,Customer> customers;
// A HashMap containing the String and Customer values of customers.

private NewBank()
// A method that takes a new HashMap, stores it in customers and also calls addTestData().

private void addTestData()
// This is a method that adds Customer test data. It creates new Customers (i.e. Customer bhagy = new Customer()) and populates the data (three entries) to be used in NewBank().

public static NewBank getBank()
// Simply returns bank.

public synchronized CustomerID checkLogInDetails(String userName, String password)
// Checks if the customers HashMap contains the key for a userName and if so returns a new CustomerID with userName.

public synchronized String processRequest(CustomerID customer, String request)
// Commands from the NewBank customer are processed in this method.

private String showMyAccounts(CustomerID customer)
// Returns all customer accounts in a String format (accountsToString()).
```

### <center>NewBankClientHandler.java</center>
```java
public class NewBankClientHandler extends Thread
// This class contains all other methods and variables listed below:

private NewBank bank
// Variable containing the value of bank.

private BufferedReader in
// Variable that allows the usage of BufferedReader and stores it.

private PrintWriter out
// Variable that allows for a value to be printed to the screen (stores this value).

public NewBankClientHandler(Socket s) throws IOException
// Client handler method that generates the above variables.

public void run()
// Uses a try if else catch finally statement that prompts for the username, password and authenticates the user, generating a customer ID token from bank to use in subsequent requests. If the user is authenticated, the if statement then gets requests from the user and processes them.
```

### <center>NewBankServer.java</center>
```java
public class NewBankServer extends Thread
// This class contains all other methods and variables listed below:

private ServerSocket server;
// Variable containing the ServerSocket used for connections.

public NewBankServer(int port) throws IOException
// This method initializes the server variable with a new ServerSocket(port).

public void run()
// This method starts up a new client handler thread to receive incoming connections and process requests using a try while catch finally statement, the socket and clientHandler.

public static void main(String[] args) throws IOException
// This method starts a new NewBankServer thread on a specified port number.
```