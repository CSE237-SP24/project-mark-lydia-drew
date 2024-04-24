package bankapp;

import java.util.Scanner;

public class Loan {
    private final double LOAN_CALCULATION_YEARS=5;
    private final double LOAN_CALCULATION_FRACTION=0.5;
    private final double COMPOUNDING_TIME_PERIOD=12;
    private final double intialAmount;
    private final double interestRate;
    private double amountUnpaid;
    private BankAccount loanHolder;
    private Scanner in;

    public Loan(double intialAmount,double interestRate){
        this.intialAmount=intialAmount;
        amountUnpaid=intialAmount;
        this.interestRate=interestRate;
        this.in = new Scanner(System.in);
        this.loanHolder=null;
    }
    public void incrementTimePeriod(){
        amountUnpaid=amountUnpaid*Math.pow((1+interestRate/COMPOUNDING_TIME_PERIOD),COMPOUNDING_TIME_PERIOD);

    }
    public boolean isApproved(){
        return loanHolder!=null;
    }
    public double getApprovalThreshold(){
        return intialAmount*Math.pow((1+interestRate/COMPOUNDING_TIME_PERIOD),LOAN_CALCULATION_YEARS*COMPOUNDING_TIME_PERIOD)*LOAN_CALCULATION_FRACTION;
    }
    public boolean authorizeLoan(double income,BankAccount applicant){
        return applicant.getBalance()+income*LOAN_CALCULATION_YEARS>getApprovalThreshold(); 
    }
    public void grantLoanWithIncome(BankAccount applicant){
        System.out.println("Enter your income: ");
        double income = getValidUserInput();
        if(authorizeLoan(income, applicant)){
            this.loanHolder=applicant;
            applicant.getLoans().add(this);
            System.out.println("Congratulations, your loan has been approved");
        }else{
            System.out.println("Loan Application Rejected");
        }
    }
    public void grantLoanWithoutIncome(BankAccount applicant){
        if(authorizeLoan(0, applicant)){
            this.loanHolder=applicant;
            applicant.getLoans().add(this);
            System.out.println("Congratulations, your loan has been approved");
        }else{
            System.out.println("Loan Application Rejected");
        }
    }
    public boolean tryLoanPayment(double amount){
        if(amount<0) throw new IllegalArgumentException("Invalid amount! Amount should be a positive number.");
        if(!isApproved()){
            System.out.println("Loan has not been approved");
            return false;
        }
        if(loanHolder.getBalance()<amount) {
            System.out.println("You do not have enough money to make this payment");
            return false;
        }
        if(amount > amountUnpaid) throw new IllegalArgumentException("Invalid amount! Amount should be less that or equal to amount unpaid: "+amountUnpaid);
        loanHolder.withdraw(String.valueOf(amount));
        amountUnpaid-=amount;
        return true;
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
    public double getCOMPOUNDING_TIME_PERIOD(){return COMPOUNDING_TIME_PERIOD;}
    public double getIntialAmount() {return intialAmount;}
    public double getInterestRate(){return interestRate; };
    public double getAmountUnpaid(){return amountUnpaid;}
    public BankAccount getLoanHolder(){return loanHolder;}
    
}
