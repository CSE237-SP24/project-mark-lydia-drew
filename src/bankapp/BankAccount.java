package bankapp;

import java.util.*;
import java.io.*;

public class BankAccount {
	private double balance;
	private String password;
	private String username;
	private List<Card> cards;
	
	//Constructors - not tested
	public BankAccount(String userToAdd, String passwd) {
		this.username = userToAdd;
		this.password = passwd;
		this.balance = 0;
		this.cards = new ArrayList<Card>();
	}
	
	public BankAccount(String userToAdd, String passwd, double balanc, List<Card> thisCards) {
		this.username = userToAdd;
		this.password = passwd;
		this.balance = balanc;
		this.cards = thisCards;
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
	public String getPassword() {
		return this.password;
	}
	public void addCard(Card newCard) {
		cards.add(newCard);
	}
	
	public List<Card> getCards() {
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
	 public void saveAccountToFile(String filename) {
	        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
	        	writer.println("ACCOUNT HEADER");
	            writer.println("Username: " + username);
	            writer.println("Password: " + password);
	            writer.println("Balance: " + balance);
	            writer.println("Cards:");
	            for (Card card : cards) {
	                writer.println(card.getNumber() + "  "+ card.getTypeNum());
	            }
	        } catch (IOException e) {
	        	// Handle the exception, for example, by printing an error message
	        	System.err.println("Error writing account information to file: " + e.getMessage());
	        }
	 }
}
