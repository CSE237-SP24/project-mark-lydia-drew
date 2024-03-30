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
		System.out.print("To deposit money enter \"1\". To withdraw money enter \"2\".\n" +"To go to card menu enter \"3\". To quit enter \"4\": ");
	}
	
	//Menu method to loop until quit
	public void infiniteMenu() {
		while (true) {
			//Display options and send to helpers
			displayingOptions();
			String input = in.nextLine();
			if(menuLoop(input)) break;
		}
	}
	
	private boolean menuLoop(String input) {
		switch (input.toLowerCase()) {
			case "1":
				System.out.print("How much would you like to deposit: ");
				String dAmount = getValidUserInput();
				handleDeposit(dAmount);
				return false; // Continue loop
			case "2":
				System.out.print("How much would you like to withdraw: ");
				String wAmount = getValidUserInput();
				handleWithdrawal(wAmount);
				return false; // Continue loop
			case "3":
				displayCardMenu(account);
				return false; // Continue loop
			case "4":
				out.println("Thank you. Have a nice day!");
				return true; // Exit loop
			default:
				out.println("Invalid input.");
			return false; // Continue loop
		}
	}
	
	private void handleDeposit(String dAmount) {
		try {
			account.deposit(dAmount);
			out.println();
			processingUserSelection();
		}catch(IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	private void handleWithdrawal(String wAmount) {
		try {
			account.withdraw(wAmount);
			out.println();
			processingUserSelection();
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
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