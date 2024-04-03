package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import bankapp.Menu;
import bankapp.BankAccount;

public class MenuTests {

    private Menu menu;
    private BankAccount account;

    @BeforeEach
    void setUp() {
    	account = new BankAccount("test", "passwd");
    	menu = new Menu(account);
    }
    
    @Test
    void handleSimpleDeposit() {
    	menu.handleDeposit("5");
    	assertEquals(account.getBalance(),5,.01);
    }
    @Test
    void handleMultipleDeposits() {
		menu.handleDeposit("25");
		menu.handleDeposit("15.5");
		
		assertEquals(40.5, account.getBalance(), 0.01);	
	}
    @Test
	void handleMultipleDepositsWithError() {
		menu.handleDeposit("25");
		menu.handleDeposit("yjtjdyjydtj");
		menu.handleDeposit("15.5");
		
		assertEquals(40.5, account.getBalance(), 0.01);	
	}
    @Test
	void handleSimpleWithdrawal() {
		account.deposit("25");
		menu.handleWithdrawal("10");
		
		assertEquals(15.0, account.getBalance(), 0.01);	
	}

	@Test
	void handleMultipleWithdrawals() {
		account.deposit("25");
		
		menu.handleWithdrawal("10");
		menu.handleWithdrawal("7.5");
		
		assertEquals(7.5, account.getBalance(), 0.01);	
	}

	@Test
	void handleGreaterThanBalanceWithdrawal() {
		account.deposit("25");
		
		menu.handleWithdrawal("100");
		
		assertEquals(25, account.getBalance(), 0.01);	
	}


	@Test
	void handleMultipleWithdrawWithError() {
		account.deposit("25");
		
		menu.handleWithdrawal("10");
		menu.handleWithdrawal("10000");
		menu.handleWithdrawal("7.5");
		
		assertEquals(7.5, account.getBalance(), 0.01);	
	}

}
