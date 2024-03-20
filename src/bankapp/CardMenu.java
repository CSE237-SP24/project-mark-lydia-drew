package bankapp;

import java.util.Scanner;

public class CardMenu {

    private Scanner in;
	private Card card;

    public static void main(String[] args) {
		CardMenu cardMenu = new CardMenu();
		cardMenu.displayOptionForNumber();
		String number = cardMenu.getValidUserInputNumber();
        cardMenu.displayOptionForType();
        int type = cardMenu.getValidUserInputType();
		cardMenu.processingUserSelection(number, type);
	}
	
	//Constructor
	public CardMenu() {
		this.in = new Scanner(System.in);
		this.card = new Card();
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
	
	public Card getCard() {
		return card;
	}

}
