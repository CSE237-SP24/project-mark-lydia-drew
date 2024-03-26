package bankapp;

import java.util.*;
import java.io.*;

public class Menu {

	private Scanner in;
    private PrintStream out;
	private BankAccount account;
	
	//not tested
	public static void main(String[] args) {
	     Menu mainMenu = new Menu();
	     mainMenu.infiniteMenu();
	}
	
	//Constructors
	public Menu() {
	     this.in = new Scanner(System.in);
	     this.out = new PrintStream(System.out);
	     this.account = createAccountWithUsername();
    }
	public Menu(BankAccount account) {
		 this.in = new Scanner(System.in);
		 this.out = new PrintStream(System.out);
		 this.account = account;
	}

	//no tests needed because CardMenu has its own tests
	public void displayCardMenu(BankAccount account) {
        // Create an instance of CardMenu
        CardMenu cardMenu = new CardMenu(account);
        // Call a method in CardMenu to display the menu
        cardMenu.cardMenuDisplay();
    }
    // Prompts the user for a username and creates a new BankAccount
    private BankAccount createAccountWithUsername() {
        out.println("Enter your username:");
        String username = in.nextLine();
        return new BankAccount(username);
    }
	
	//Code that just displays stuff - no tests needed
	public void displayingOptions() {
			System.out.print("To deposit money enter \"d\". To withdraw money enter \"w\".\n" +
                 "To go to card menu enter \"c\". To quit enter \"q\": ");
		}
	
	//Menu method to loop until quit
	public void infiniteMenu() {
	    boolean done = false;
	    while (!done) {
	    	//Display options and send to helpers
	        displayingOptions();
	        String input = in.nextLine();
	        done = menuLoop(input);
	    }
	}
	
	private boolean menuLoop(String input) {
		switch (input.toLowerCase()) {
        case "d":
            handleDeposit();
            return false; // Continue loop
        case "w":
            handleWithdrawal();
            return false; // Continue loop
        case "c":
            displayCardMenu(account);
            return false; // Continue loop
        case "q":
            out.println("Thank you. Have a nice day!");
            return true; // Exit loop
        default:
            out.println("Invalid input.");
            return false; // Continue loop
		}
	}

	private void handleDeposit() {
	    System.out.print("How much would you like to deposit: ");
	    String dAmount = getValidUserInput();
	    account.deposit(dAmount);
	    out.println();
	    processingUserSelection();
	}

	private void handleWithdrawal() {
	    while (true) {
	        System.out.print("How much would you like to withdraw: ");
	        String wAmount = getValidUserInput();
	        try {
	            account.withdraw(wAmount);
	            out.println();
	            processingUserSelection();
	            break; // Break out of the loop if withdrawal is successful
	        } catch (IllegalArgumentException e) {
	            out.println("Error: " + e.getMessage() + " Try again.");
	            // Retry getting valid amount to withdraw
	        }
	    }
	}

	public String getValidUserInput() {
	    double amount;
	    while (true) {
	        String input = in.nextLine(); // Read input as a string

	        try {
	            // Attempt to parse the input as a double
	            amount = Double.parseDouble(input);
	            // Check if the amount is positive
	            if (amount < 0) {
	                out.println("Invalid value! Amount must be positive.");
	                continue; // Restart the loop to prompt the user again
	            }
	            break; // Break out of the loop if input is valid
	        } catch (IllegalArgumentException e) {
				// Handle the exception (e.g., log it, display an error message)
				out.println("Error: " + e.getMessage() + " Try again.");
				continue;
			}
		}
	    return String.valueOf(amount);
	}

	public void processingUserSelection() {
		out.println("Your balance is now: " + account.getBalance() + " for " + account.getUsername());
	}

	public BankAccount getAccount() {
		return account;
	}
}