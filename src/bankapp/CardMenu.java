package bankapp;

import java.util.List;
import java.util.Scanner;

public class CardMenu {

    private Scanner in;
	private Card card;
	private BankAccount account;

	//Constructor
	public CardMenu(BankAccount account) {
		this.in = new Scanner(System.in);
		this.card = new Card();
		this.account = account;
	}

	public void cardMenuDisplay(){
		displayOptionForNumber();
        String number = getValidUserInputNumber();
        displayOptionForType();
        int type = getValidUserInputType();
        processingUserSelection(number, type);
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
		card.setNumber(number);
        card.setType(type);
		System.out.println("Your " + card.getType() + " card (" + card.getNumber() + ") is now linked to your account.");
	}

	public void displayCardsForAccount() {
        List<Card> cards = account.getCards();
        if (cards.isEmpty()) {
            System.out.println("No cards linked to this account.");
        } else {
            System.out.println("Cards linked to account:" + this.account);
            for (Card card : cards) {
                System.out.println("Card Number: " + card.getNumber() + ", Type: " + card.getType());
            }
        }
	
	public Card getCard() {
		return card;
	}

}
