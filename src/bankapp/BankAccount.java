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
	public void deposit(String input) {
		double parsedAmount;
		try {
			parsedAmount = Double.parseDouble(input);
			if (parsedAmount < 0 || Double.isNaN(parsedAmount) || Double.isInfinite(parsedAmount)) {
				throw new IllegalArgumentException("Invalid deposit amount! Deposit amount should be a positive number.");
			}
			else {
				this.balance += parsedAmount;
			}
		}catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid deposit amount! Deposit amount should be a valid number.");
		}
	}
	
	public void withdraw(String input) {
		double parsedAmount;
		try {
			parsedAmount = Double.parseDouble(input);
			if (parsedAmount < 0 || Double.isNaN(parsedAmount) || Double.isInfinite(parsedAmount)) {
				throw new IllegalArgumentException("Invalid withdrawal amount! Withdrawal amount should be a positive number.");
			}
			else {
				double test = this.balance - parsedAmount;
				if (test < 0) {
					throw new IllegalArgumentException("Sorry, this would overdraw your balance. Withdraw a smaller amount.");
				}
				else {
					this.balance -= parsedAmount;
				}
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid deposit amount! Deposit amount should be a valid number.");
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
		if (cards.isEmpty()) {
			throw new IllegalArgumentException();
		}
		int i = 0;
		while (!cards.get(i).getNumber().equals(number)) {
			i++;
			if (i > cards.size()-1) {
				throw new IllegalAccessError();
			}
		}
		cards.remove(i);
	}
}
