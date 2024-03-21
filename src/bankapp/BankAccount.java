package bankapp;

import java.util.*;

public class BankAccount {
	
	private double balance;
	private String username;
	private ArrayList<Card> cards;
	
	//Constructors - not tested
	public BankAccount(String userToAdd) {
		this.username = userToAdd;
		this.balance = 0;
		this.cards = new ArrayList<Card>();
	}
	
	//public method doing some work - lots of tests
	public void deposit(double amount) {
		if (Double.isNaN(amount) || Double.isInfinite(amount)) {
	        throw new IllegalArgumentException("Amount must be a valid number");
	    }
		else if(amount < 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		this.balance += amount;
	}

	public void withdraw(double amount) {
		if (Double.isNaN(amount) || Double.isInfinite(amount)) {
	        throw new IllegalArgumentException("Amount must be a valid number");
	    }
		else if(amount < 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}else if(amount>balance){
			throw new IllegalArgumentException("Amount cannont be greater than balance");
		}
		this.balance -= amount;
	}
	
	//getters and setters - not tested
	public double getBalance() {
		return this.balance;
	}
	public String getUsername() {
		return this.username;
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
