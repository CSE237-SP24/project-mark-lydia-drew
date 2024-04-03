package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import bankapp.BankAccount;
import bankapp.Bill;
import bankapp.BillMenu;

public class BillMenuTests {
	
//	private BankAccount account;
//	private BillMenu menu;
//	
//	@BeforeEach 
//	void setUp() {
//		account = new BankAccount("testuser");
//		account.deposit("100");
//		menu = new BillMenu(account);
//	}
	
	@Test
	public void paySimpleBill() {
		BankAccount account = new BankAccount("testuser");
		account.deposit("100");
		BillMenu menu = new BillMenu(account);
		Bill bill = new Bill(50, "bank");
		menu.payBill(bill);
		assertEquals(50, bill.getAmount());
		assertEquals(50, account.getBalance());
	}
	
	@Test
	public void payMultipleBills() {
		BankAccount account = new BankAccount("testuser");
		account.deposit("300");
		BillMenu menu = new BillMenu(account);
		Bill billOne = new Bill(50, "bank");
		menu.payBill(billOne);
		Bill billTwo = new Bill(200, "school");
		menu.payBill(billTwo);
		Bill billThree = new Bill(10, "insurance");
		menu.payBill(billThree);
		assertEquals(40, account.getBalance());
	}
	
	@Test 
	public void payMultipleBillsInsufficient() {
		BankAccount account = new BankAccount("testuser");
		account.deposit("300");
		BillMenu menu = new BillMenu(account);
		Bill billOne = new Bill(50, "bank");
		menu.payBill(billOne);
		Bill billTwo = new Bill(300, "school");
		menu.payBill(billTwo);
		Bill billThree = new Bill(20, "insurance");
		menu.payBill(billThree);
		assertEquals(230, account.getBalance());
	}
	
	@Test
	public void payInsufficientFunds() {
		BankAccount account = new BankAccount("testuser");
		account.deposit("100");
		BillMenu menu = new BillMenu(account);
		Bill bill = new Bill(200, "bank");
		menu.payBill(bill);
		assertEquals(100, account.getBalance());
	}
	
	@Test
	public void payNegativeBill() {
		BankAccount account = new BankAccount("testuser");
		account.deposit("100");
		BillMenu menu = new BillMenu(account);
		Bill bill = new Bill(-200, "bank");
		try {
			menu.payBill(bill);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

}
