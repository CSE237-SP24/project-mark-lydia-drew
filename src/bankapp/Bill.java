package bankapp;

public class Bill {
	
	private double amount;
	private String reciever;
	
	public Bill() {
		this.amount = 0;
		this.reciever = null;
	}
	
	public Bill(double amount, String reciever) {
		this.amount = amount;
		this.reciever = reciever;
	}
	
	public void setAmount(double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Bill amount must be positive.");
		}
		this.amount = amount;
	}
	
	public void setReciever(String reciever) {
		this.reciever = reciever;
	}
	
	public double getAmount() {
		return this.amount;
	}
	
	public String reciever() {
		return this.reciever;
	}
	
	public void display() {
		System.out.println("You have paid $" + this.amount + " to " + this.reciever);
	}
	
	

}
