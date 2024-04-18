package bankapp;

import java.util.Scanner;

public class CreditScoreMenu {
	
	private Scanner in;
	private static final double[] WEIGHTS = {35, 30, 15, 10, 10}; // weights used for credit scoring
	
	public static void main(String args[]) {
		CreditScoreMenu menu = new CreditScoreMenu();
		menu.creditMenuDisplay();
		
	}
	
	public CreditScoreMenu() {
		this.in = new Scanner(System.in);
	}
	
	public void creditMenuDisplay() {
		CreditScore score = new CreditScore(convertToCreditRange(calculateScore()));
		score.display();
	}
	
	// no tests needed for user input
	public double calculateScore() {
		double paymentHistory = getPaymentHistory();
		double creditUtilization = getCreditUtilization();
		double length = getCreditLength();
		double newCredit = getNewCreditFrequency();
		double creditVariety = getCreditVariety();
		return ((paymentHistory/100 * WEIGHTS[0]) + (creditUtilization/100 * WEIGHTS[1]) + (length * WEIGHTS[2]) + (newCredit/100 * WEIGHTS[3]) + (creditVariety/100 * WEIGHTS[4]) );
	}
	
	//tests needed
	public int convertToCreditRange(double score) {
		if (score > 850) {
			score = 850;
		} else if (score < 300) {
			score = 300;
		}
		return (int)score;
	}
	
	
	// no tests needed for user input
	
	public double getPaymentHistory() {
		System.out.println("How would you rate your payment history? (Enter value in range 1-100)");
		return getValidUserInput();
	}
	
	public double getCreditUtilization() {
		System.out.println("How would you rate your credit utilization? (i.e. credit utilization ratio)\n(Enter value in range 1-100)");
		return getValidUserInput();
		
	}
	
	public double getCreditLength() {
		System.out.println("How many years have your credit accounts been active? ");
		return getValidUserInput();
		
	}
	
	public double getNewCreditFrequency() {
		System.out.println("How would you rate your use of new credit? (Enter value in range 1-100)");
		return getValidUserInput();
	}
	
	public double getCreditVariety() {
		System.out.println("Lastly, how would you rate your credit diversification? (Enter value in range 1-100)");
		return getValidUserInput();
		
	}
	
	// no tests needed for user input
	
	public double getValidUserInput() {
		double value;
		while (true) {
			String input = in.nextLine();
			try {
				value = Double.parseDouble(input);
				if (value < 1 || value > 100) {
					System.out.println("Invalid value. Enter integer value in range 1-100.");
					continue;
				}
				break;
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid value. Enter integer value in range 1-100.");
				continue;
			}
		}
		return value;
	}

}
