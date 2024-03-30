package bankapp;

import java.util.*;

public class CardMenu {
	private Scanner in;
	private BankAccount account;
	//Constructor
	public CardMenu(BankAccount account) {
		this.in = new Scanner(System.in);
		this.account = account;
	}
	public void cardMenuDisplay(){
		infiniteCards();
	}
	public void infiniteCards() {
		boolean userQuit = false;
		System.out.println("Welcome to your card menu.");
		while (!userQuit) {
			// Displaying options
			System.out.println("Choose an option:");
			System.out.println("1. Display cards linked to account");
			System.out.println("2. Add a new card");
			System.out.println("3. Quit");
			// Getting user input
			String input = in.nextLine();
			// Processing user input
			userQuit = cardLoop(input);
		}
	}
	private boolean cardLoop(String input) {
	    switch (input.toLowerCase()) {
	        case "1":
	            displayCardsForAccount();
	            return false; // Continue loop
	        case "2":
	            addCard();
	            return false; // Continue loop
	        case "3":
	            System.out.println("Exiting card menu.");
	            return true; // Exit loop
	        default:
	            System.out.println("Invalid input. Please try again.");
	            return false; // Continue loop
	    }
	}
	public void addCard() {
		try{
			displayOptionForNumber(); // Display prompt for card number
			String number = getValidUserInputNumber(); // Get valid card number
			displayOptionForType(); // Display prompt for card type
			int type = getValidUserInputType(); // Get valid card type
			Card card = new Card(number, type); // Create new Card object
			account.addCard(card); // Add card to account
			System.out.println("Your " + (card.getType()) + " card (" + card.getNumber() + ") is now linked to your account.");
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid card number: " + e.getMessage());
			// Consume the invalid input
			in.nextLine();
			// Retry getting valid card type
			addCard();
		} catch (InputMismatchException e) {
			System.out.println("Invalid input for card type. Please enter 0 or 1.");
			// Consume the invalid input
			in.nextLine();
			// Retry getting valid card type
			addCard();
		}
	}
	//Code that just displays stuff - no tests needed
	public void displayOptionForNumber() {
		System.out.println("Please enter your card number: ");
	}
	public void displayOptionForType() {
		System.out.println("If this card is a credit card, enter 0. If this card is a debit card, enter 1. ");
	}
	//Code that gets user input
	//No tests needed...for now (probably discuss in future class)
	public String getValidUserInputNumber() {
		String number = in.nextLine();
		return number;
	}
	
	public int getValidUserInputType() {
		int type = in.nextInt();
		while (type != 0 && type != 1) {
			System.out.println("Invalid type! Please enter 0 or 1.");
			System.out.println("What type of card is this?");
			type = in.nextInt();
		}
		return type;
	}
	
	//Does work - needs tests
	public void processingUserSelection(String number, int type) {
		Card card = new Card();
		card.setNumber(number);
		card.setType(type);
		System.out.println("Your " + card.getType() + " card (" + card.getNumber() + ") is now linked to your account.");
	}
	
	public void displayCardsForAccount() {
		List<Card> cards = account.getCards();
		if (cards.isEmpty()) {
			System.out.println("No cards linked to this account.");
		}else {
			System.out.println("Cards linked to account:" + this.account.getUsername());
			for (Card card : cards) {
				System.out.println("Card Number: " + card.getNumber() + ", Type: " + card.getType());
			}
		}
	}
	
	public int numberOfCards() {
		return account.getCards().size();
	}
}