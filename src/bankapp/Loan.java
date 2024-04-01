package bankapp;

import java.util.Scanner;

public class Loan {
    private final double LOAN_CALCULATION_YEARS=5;
    private final double LOAN_CALCULATION_FRACTION=0.5;
    private final double COMPOUNDING_TIME_PERIOD=12;
    private double intialAmount;
    private double amountUnpaid;
    private double interestRate;
    private int termInTimePeriod;
    private BankAccount loanHolder;
    private Scanner in;

    public Loan(double intialAmount,double interestRate){
        this.intialAmount=intialAmount;
        amountUnpaid=intialAmount;
        this.interestRate=interestRate;
        this.termInTimePeriod=0;
        this.in = new Scanner(System.in);
        this.loanHolder=null;
    }

    public boolean isApproved(){
        return loanHolder!=null;
    }
    public double getApprovalThreshold(){
        return intialAmount*Math.pow((1+(interestRate)/COMPOUNDING_TIME_PERIOD),LOAN_CALCULATION_YEARS*COMPOUNDING_TIME_PERIOD)*LOAN_CALCULATION_FRACTION;
    }
    public boolean authorizeLoan(double income,BankAccount applicant){
        return applicant.getBalance()+income*LOAN_CALCULATION_YEARS>getApprovalThreshold(); 
    }
    public void grantLoan(BankAccount applicant){
        System.out.println("Enter your income: ");
        double income = getValidUserInput();
        if(authorizeLoan(income, applicant)){
            this.loanHolder=applicant;
            System.out.println("Congratulations, your loan has been approved");
        }else{
            System.out.println("Loan Application Rejected");
        }
    }

    public double getValidUserInput() {
		double amount;
		while (true) {
			String input = in.nextLine(); // Read input as a string
			try {
				// Attempt to parse the input as a double
				amount = Double.parseDouble(input);
				// Check if the amount is positive
				if (amount < 0) {
					System.out.println("Invalid value! Amount must be positive.");
					continue; // Restart the loop to prompt the user again
				}
				break; // Break out of the loop if input is valid
			} catch (IllegalArgumentException e) {
				// Handle the exception (e.g., log it, display an error message)
				System.out.println("Error: " + e.getMessage() + " Try again.");
				continue;
			}
		}
		return amount;
	}
    
}
