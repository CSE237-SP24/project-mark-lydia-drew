package bankapp;

import java.util.*;

public class Menu {

	private Scanner in;
	private BankAccount account;
	
	//not tested
	public static void main(String[] args) {
		Menu mainMenu = new Menu();
		mainMenu.infiniteMenu();
	}
	
	//Constructor
	public Menu() {
        this.in = new Scanner(System.in);
        this.account = createAccountWithUsername();
    }

	public void displayCardMenu(BankAccount account) {
        // Create an instance of CardMenu
        CardMenu cardMenu = new CardMenu(account);
        
        // Call a method in CardMenu to display the menu
        cardMenu.cardMenuDisplay();
    }
    // Prompts the user for a username and creates a new BankAccount
    private BankAccount createAccountWithUsername() {
        System.out.println("Enter your username:");
        String username = in.nextLine();
        return new BankAccount(username);
    }
	
	//Code that just displays stuff - no tests needed
	public void displayingOptions() {
			System.out.println("How much money do you want to deposit?");
			System.out.print("To Deposit money enter \"d\". To withdraw money enter \"w\".\n" +
                 "To go to card menu enter \"c\". To quit enter \"q\": ");
		}
	
	
	public void infiniteMenu(){
		boolean done=false;
		while(!done){
			displayingOptions();
			String input = in.nextLine();
			switch(input.toLowerCase()){
				case "d":
					System.out.print("How much would you like to deposit: ");
					double dAmount= getValidUserInput();
					account.deposit(dAmount);
					System.out.println();
					processingUserSelection();
					break;
				case "w":
					System.out.print("How much would you like to withdraw: ");
					double wAmount= getValidUserInput();
					account.withdraw(wAmount);
					System.out.println();
					processingUserSelection();
					break;
				case "c":
					displayCardMenu(account);
					break;
				case "q":
					done=true;
					System.out.println("Thank you. Have a nice day!");
					break;
				default:
					System.out.println("Invalid input.");
			}
		}
	}
	
	//Code that gets user input
	//No tests needed...for now (probably discuss in future class)
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
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid value! Please enter a valid number.");
	    	}
		}
	    return amount;
	}
	//Does work - needs tests
	public void processingUserSelection() {
		System.out.println("Your balance is now: " + account.getBalance() + " for " + account.getUsername());
	}
	//Testing later
	public void addCard() {
		System.out.println("Do you want to add a card? ");
	}

	public BankAccount getAccount() {
		return account;
	}
}