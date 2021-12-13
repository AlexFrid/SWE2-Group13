package newbank.server;

import java.util.ArrayList;
import java.util.Random;

public class LoanCollection {
    private ArrayList<Loan> loans;

    public LoanCollection() {
        this.loans = new ArrayList<>();
    }

    //Add loan - assigns random integer as loanID
    public void addLoan(CustomerID lender, CustomerID borrower, double balance) {
        Random rand = new Random();
        int randInt = rand.nextInt(5000);

        this.loans.add(new Loan(randInt, lender, borrower, balance));
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    //Reduce loan balance
    public void decreaseBalance(int loanID, double amount) {
        for(Loan l : loans) {
            if(l.getLoanID() == loanID) {
                l.decreaseBalance(amount);
                return;
            }
        }
    }

    //Show customer's lending activity
    public String showMyLendings(CustomerID customer) {
		String s = "";
		for(Loan l : loans) {
			if(l.getLender().getKey().equals(customer.getKey())) {
				s += l.toString() + "\n";
			}
		}
		return s;
	}

    //Show customer's borrowing activity
	public String showMyBorrowings(CustomerID customer) {
		String s = "";
		for(Loan l : loans) {
			if(l.getBorrower().getKey().equals(customer.getKey())) {
				s += l.toString() + "\n";
			}
		}
		return s;
	}

    //Checks whether user is authorised to perform action on loan with given loanID
    //e.g. paying a loan on which the user is a lender
    public boolean isRequestValid(Loan loan, CustomerID borrower) {
        if(loan.getBorrower().getKey().equals(borrower.getKey())) {
            return true;
        }
        return false;
    }

}
