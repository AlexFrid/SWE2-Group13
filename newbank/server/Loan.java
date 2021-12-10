package newbank.server;

public class Loan {
    
    private String loanID;
    private CustomerID lender;
    private CustomerID borrower;
    private double balance;

    public Loan(String loanID, CustomerID lender, CustomerID borrower, double balance) {
        this.loanID = loanID;
        this.lender = lender;
        this.borrower = borrower;
        this.balance = balance;
    }

    public String toString() {
        return ("loanID:" + loanID + " | Lender:" + lender.getKey() + " | Borrower:" + borrower.getKey() + " | Balance: " + balance);
    }

    public void decreaseBalance(String amount) {

        this.balance = this.balance - Double.parseDouble(amount);
    }

    public String getLoanID() {
        return loanID;
    }

    public CustomerID getLender() {
        return lender;
    }    

    public CustomerID getBorrower() {
        return borrower;
    }


}
