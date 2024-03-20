package bankapp;

import java.util.ArrayList;

public class BankAccount {
	
	private double balance;
	private ArrayList<Card> cards;
	
	//Constructors - not tested
	public BankAccount() {
		this.balance = 0;
		this.cards = new ArrayList<Card>();
	}
	
	//public method doing some work - lots of tests
	public void deposit(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		this.balance += amount;
	}
	
	//getters and setters - not tested
	public double getBalance() {
		return this.balance;
	}

	public void addCard(String number, int type) {
		Card newCard = new Card();
		newCard.setNumber(number);
		newCard.setType(type);
		cards.add(newCard);
	}

	public ArrayList<Card> getCards() {
		return this.cards;
	}

	public void removeCard(String number) {
		int i = 0;
		while (!cards.get(i).getNumber().equals(number)) {
			i++;
			if (i > cards.size()-1) {
				throw new IllegalAccessError("This card does not exist");
			}
		}
		cards.remove(i);
	}
}
