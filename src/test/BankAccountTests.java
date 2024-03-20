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

	@Test
	void testAddCard() {
		BankAccount testAccount = new BankAccount();
		testAccount.addCard("1234123412341234", 1);
		int numberOfCards = testAccount.getCards().size();
		assertEquals(1, numberOfCards);
	}

	@Test
	void testAddMultipleCards() {
		BankAccount testAccount = new BankAccount();
		testAccount.addCard("0000000000000000", 1);
		testAccount.addCard("2468246824682468", 0);
		testAccount.addCard("1111111111111111", 0);
		int numberOfCards = testAccount.getCards().size();
		assertEquals(3, numberOfCards);
	}

	@Test
	void testRemoveCard() {
		BankAccount testAccount = new BankAccount();
		testAccount.addCard("1234123412341234", 1);
		testAccount.removeCard("1234123412341234");
		int numberOfCards = testAccount.getCards().size();
		assertEquals(0, numberOfCards);
	}

	@Test
	void testRemoveCardInvalid() {
		BankAccount testAccount = new BankAccount();
		testAccount.addCard("0000000000000000", 1);
		testAccount.addCard("2468246824682468", 0);
		testAccount.addCard("1111111111111111", 0);
		try {
			testAccount.removeCard("2222222222222222");
			fail();
		} catch (IllegalAccessError e) {
			//we expect to end up here, the card "2222222222222222" does not exist
			assertTrue(true);
		}

	}

}
