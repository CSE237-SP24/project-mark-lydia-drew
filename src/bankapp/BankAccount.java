package bankapp;

public class BankAccount {
	
	private double balance;
	
	//Constructors - not tested
	public BankAccount() {
		this.balance = 0;
	}
	
	//public method doing some work - lots of tests
	public void deposit(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		this.balance += amount;
	}

	public void withdraw(double amount) {
		if(amount < 0) {
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
}
