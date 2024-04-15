package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import bankapp.Menu;
import bankapp.BankAccount;

public class MenuTests {
    
    @Test
    public void handleSimpleDeposit() {
    	
		BankAccount account = new BankAccount("testuser", "passwd");
		String filename = "src/accountData/accounts_test.txt";
        Menu menu = new Menu(account, filename);

    	menu.handleDeposit("5");
    	assertEquals(account.getBalance(),5,.01);
    }
    @Test
    public void handleMultipleDeposits() {
    	BankAccount account = new BankAccount("testuser", "passwd");
		String filename = "src/accountData/accounts_test.txt";
        Menu menu = new Menu(account, filename);
		
		menu.handleDeposit("25");
		menu.handleDeposit("15.5");
		
		assertEquals(40.5, account.getBalance(), 0.01);	
	}
    @Test
	public void handleMultipleDepositsWithError() {
    	BankAccount account = new BankAccount("testuser", "passwd");
		String filename = "src/accountData/accounts_test.txt";
        Menu menu = new Menu(account, filename);
		
		menu.handleDeposit("25");
		menu.handleDeposit("yjtjdyjydtj");
		menu.handleDeposit("15.5");
		
		assertEquals(40.5, account.getBalance(), 0.01);	
	}
    @Test
	public void handleSimpleWithdrawal() {
    	BankAccount account = new BankAccount("testuser", "passwd");
		String filename = "src/accountData/accounts_test.txt";
        Menu menu = new Menu(account, filename);
		
		account.deposit("25");
		menu.handleWithdrawal("10");
		
		assertEquals(15.0, account.getBalance(), 0.01);	
	}

	@Test
	public void handleMultipleWithdrawals() {
		BankAccount account = new BankAccount("testuser", "passwd");
		String filename = "src/accountData/accounts_test.txt";
        Menu menu = new Menu(account, filename);
		
		account.deposit("25");
		
		menu.handleWithdrawal("10");
		menu.handleWithdrawal("7.5");
		
		assertEquals(7.5, account.getBalance(), 0.01);	
	}

	@Test
	public void handleGreaterThanBalanceWithdrawal() {
		BankAccount account = new BankAccount("testuser", "passwd");
		String filename = "src/accountData/accounts_test.txt";
        Menu menu = new Menu(account, filename);
		
		account.deposit("25");
		
		menu.handleWithdrawal("100");
		
		assertEquals(25, account.getBalance(), 0.01);	
	}


	@Test
	public void handleMultipleWithdrawWithError() {
		BankAccount account = new BankAccount("testuser", "passwd");
		String filename = "src/accountData/accounts_test.txt";
        Menu menu = new Menu(account, filename);
		
		account.deposit("25");
		
		menu.handleWithdrawal("10");
		menu.handleWithdrawal("10000");
		menu.handleWithdrawal("7.5");
		
		assertEquals(7.5, account.getBalance(), 0.01);	
	}

}
