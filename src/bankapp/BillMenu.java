package bankapp;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BillMenu {
	
	private Scanner in;
	private BankAccount account;
	
//	public BillMenu() {
//		this.in = new Scanner(System.in);
//		this.account = null;
//	}
	
	public BillMenu(BankAccount account) {
		this.in = new Scanner(System.in);
		this.account = account;
	}
	
	public void billMenuDisplay(){
		Bill bill = new Bill(getValidAmount(), getReciever());
		payBill(bill);
//		switchMenu();
	}
	
	public double getValidAmount() {
		double amount;
		while (true) {
			System.out.println("Please enter your bill amount: ");
			try {
				amount = in.nextDouble();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Please eneter a valid positive numerical amount.");
				in.nextLine(); // clear scanner input
	        }
		}
		return amount;
	}
	
	public String getReciever() {
		System.out.println("To whom is this bill addressed to? ");
		return in.next();
	}
	
	public void payBill(Bill bill) {
		double currentBalance = account.getBalance();
		double billAmount = bill.getAmount();
		double newBalance = currentBalance - billAmount;
		if (newBalance < 0) {
			System.out.println("Your current balance is insufficient to pay your bill.\nConsider depositing more money into your account.");
			System.out.println();
		} else {
			account.withdraw(String.valueOf(billAmount));
			bill.display();
			System.out.println("Your balance is now: $" + newBalance + ".");
		}
	}
	
	public boolean switchMenu() {
		boolean switchToMain = false;
		endDisplay();
		int choice = in.nextInt();
		try {
			if (choice == 1) {
				switchToMain = true;
			} else if (choice == 2) {
				billMenuDisplay();
			} else {
				throw new InputMismatchException();
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid choice");
			switchMenu();
		}
		return switchToMain;
	}
	
	public void endDisplay() {
		System.out.println("Enter 1 to return to main menu. Enter 2 to pay a different bill.");
	}

}
