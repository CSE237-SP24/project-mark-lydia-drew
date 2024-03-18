package bankapp;

import java.util.Scanner;

public class Menu {

	private Scanner in;
	private BankAccount account;
	
	//not tested
	public static void main(String[] args) {
		Menu mainMenu = new Menu();
		mainMenu.displayingOptions();
		double amount = mainMenu.getValidUserInput();
		mainMenu.processingUserSelection(amount);
	}
	
	//Constructor
	public Menu() {
        this.in = new Scanner(System.in);
        this.account = createAccountWithUsername();
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
	}
	
	//Code that gets user input
	//No tests needed...for now (probably discuss in future class)
	public double getValidUserInput() {
		double amount = in.nextDouble();
		while(amount < 0) {
			System.out.println("Invalid value!");
			System.out.println("How much money do you want to deposit?");
			amount = in.nextDouble();
		}
		return amount;
	}
	
	//Does work - needs tests
	public void processingUserSelection(double amount) {
		account.deposit(amount);
		System.out.println("Your balance is now: " + account.getBalance());
	}
	
	public BankAccount getAccount() {
		return account;
	}
}