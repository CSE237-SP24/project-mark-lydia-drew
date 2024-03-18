package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import bankapp.BankAccount;

class BankAccountTests {

	@Test
	void testSimpleDeposit() {
		//1. Setup Objects
		
		BankAccount testAccount = new BankAccount();
		
		//2. Call the method being tested
		testAccount.deposit(25);
		
		//3. Use assertions to verify results
		assertEquals(25.0, testAccount.getBalance(), 0.01);	
	}

	@Test
	void testMultipleDeposits() {
		//1. Setup Objects
		
		BankAccount testAccount = new BankAccount();
		
		//2. Call the method being tested
		testAccount.deposit(25);
		try {
			testAccount.deposit(-25);
		} catch (IllegalArgumentException e) {;}
		testAccount.deposit(15.5);
		
		//3. Use assertions to verify results
		assertEquals(40.5, testAccount.getBalance(), 0.01);	
	}

	@Test
	void testNegativeDeposit() {
		//1. Setup Objects	
		BankAccount testAccount = new BankAccount();
		
		//2. Call the method being tested
		try {
			testAccount.deposit(-25);
			fail();
		} catch (IllegalArgumentException e) {
			//we expect to end up here, -25 is a bad input
			assertTrue(true);
		}
	}

}
