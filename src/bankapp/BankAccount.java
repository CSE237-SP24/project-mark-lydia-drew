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
		try {
			if (Double.isNaN(amount) || Double.isInfinite(amount) || amount < 0) {
				throw new IllegalArgumentException("Invalid arguments! Check your deposit amount.");
			}
			this.balance += amount;
		} catch (IllegalArgumentException e) {
			// Handle the exception (e.g., log it, display an error message)
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void withdraw(double amount) {
		try {
			if (Double.isNaN(amount) || Double.isInfinite(amount) || amount < 0 || amount > balance) {
				throw new IllegalArgumentException("Invalid amount! Check your withdrawal amount.");
			}
			this.balance -= amount;
		} catch (IllegalArgumentException e) {
			// Handle the exception (e.g., log it, display an error message)
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	//getters and setters - not tested
	public double getBalance() {
		return this.balance;
	}
	public String getUsername() {
		return this.username;
	}
	public void addCard(Card newCard) {
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
