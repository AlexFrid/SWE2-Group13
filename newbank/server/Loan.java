package newbank.server;

public class Loan {
    
    private String loanName;
    private CustomerID lender;
    private CustomerID borrower;
    private double balance;

    public Loan(String loanName, CustomerID lender, CustomerID borrower, double balance) {
        this.loanName = loanName;
        this.lender = lender;
        this.borrower = borrower;
        this.balance = balance;
    }

    public String toString() {
        return ("Lender:" + lender.getKey() + " | Borrower:" + borrower.getKey() + " | Balance: " + balance);
    }

    public CustomerID getLender() {
        return lender;
    }

    public CustomerID getBorrower() {
        return borrower;
    }


}
