package newbank.server;

public class Loan {
    
    private int loanID;
    private CustomerID lender;
    private CustomerID borrower;
    private double balance;

    public Loan(int loanID, CustomerID lender, CustomerID borrower, double balance) {
        this.loanID = loanID; //unique ID
        this.lender = lender;
        this.borrower = borrower;
        this.balance = balance;
    }

    public String toString() {
        return ("loanID:" + loanID + " | Lender:" + lender.getKey() + " | Borrower:" + borrower.getKey() + " | Balance: " + balance);
    }

    public void decreaseBalance(double amount) {
        this.balance = this.balance - amount;
    }

    public int getLoanID() {
        return loanID;
    }

    public CustomerID getLender() {
        return lender;
    }    

    public CustomerID getBorrower() {
        return borrower;
    }


}
