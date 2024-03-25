package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import bankapp.BankAccount;
import bankapp.Card;

class BankAccountTests {

	@Test
	void testSimpleDeposit() {
		//1. Setup Objects
		
		BankAccount testAccount = new BankAccount("testUser");
		
		//2. Call the method being tested
		testAccount.deposit("25");
		
		//3. Use assertions to verify results
		assertEquals(25.0, testAccount.getBalance(), 0.01);	
	}
	
	@Test
	void testNegativeDeposit() {
		//1. Setup Objects	
		BankAccount testAccount = new BankAccount("testUser");
		
		//2. Call the method being tested
		try {
			testAccount.deposit("-25");
			fail();
		} catch (IllegalArgumentException e) {
			//we expect to end up here, -25 is a bad input
			assertTrue(true);
		}
	}
	@Test
	void testNumericDeposit() {
		//1. Setup Objects	
		BankAccount testAccount = new BankAccount("testUser");
		
		//2. Call the method being tested
		try {
			testAccount.deposit("kk");
			fail();
		} catch (IllegalArgumentException e) {
			//we expect to end up here, -25 is a bad input
			assertTrue(true);
		}
	}
	@Test
	void testMultipleDeposits() {
		//1. Setup Objects
		
		BankAccount testAccount = new BankAccount("testuser");
		
		//2. Call the method being tested
		testAccount.deposit("25");
		testAccount.deposit("15.5");
		
		//3. Use assertions to verify results
		assertEquals(40.5, testAccount.getBalance(), 0.01);	
	}

	@Test
	void testMultipleDepositsWithError() {
		//1. Setup Objects
		
		BankAccount testAccount = new BankAccount("testuser");
		
		//2. Call the method being tested
		testAccount.deposit("25");
		try {
			testAccount.deposit("-25");
		} catch (IllegalArgumentException e) {;}
		testAccount.deposit("15.5");
		
		//3. Use assertions to verify results
		assertEquals(40.5, testAccount.getBalance(), 0.01);	
	}

	@Test
	void testSimpleWithdraw() {
		//1. Setup Objects
		
		BankAccount testAccount = new BankAccount("testuser");
		testAccount.deposit("25");
		
		//2. Call the method being tested
		testAccount.withdraw("10");
		
		//3. Use assertions to verify results
		assertEquals(15.0, testAccount.getBalance(), 0.01);	
	}

	@Test
	void testMultipleWithdraw() {
		//1. Setup Objects
		
		BankAccount testAccount = new BankAccount("testuser");
		testAccount.deposit("25");
		
		//2. Call the method being tested
		testAccount.withdraw("10");
		testAccount.withdraw("7.5");
		
		//3. Use assertions to verify results
		assertEquals(7.5, testAccount.getBalance(), 0.01);	
	}

	@Test
	void testNegativeWithdraw() {
		//1. Setup Objects	
		BankAccount testAccount = new BankAccount("testuser");
		testAccount.deposit("25");
		
		//2. Call the method being tested
		try {
			testAccount.withdraw("-20.3");
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	void testGreaterThanBalanceWithdraw() {
		//1. Setup Objects
		
		BankAccount testAccount = new BankAccount("testuser");
		testAccount.deposit("25");
		
		try {
			testAccount.withdraw("100");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}


	@Test
	void testMultipleWithdrawWithError() {
		//1. Setup Objects
		
		BankAccount testAccount = new BankAccount("testuser");
		testAccount.deposit("25");
		
		//2. Call the method being tested
		testAccount.withdraw("10");
		try {
			testAccount.withdraw("100");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		testAccount.withdraw("7.5");
		
		//3. Use assertions to verify results
		assertEquals(7.5, testAccount.getBalance(), 0.01);	
	}
	@Test
	void testAddCard() {
		BankAccount testAccount = new BankAccount("testuser");
		testAccount.addCard(new Card("1234567890123456", 1));
		int numberOfCards = testAccount.getCards().size();
		assertEquals(1, numberOfCards);
	}

	@Test
	void testAddMultipleCards() {
		BankAccount testAccount = new BankAccount("testuser");
		testAccount.addCard(new Card("1234567890123456", 1));
		testAccount.addCard(new Card("1234567890123451", 1));
		testAccount.addCard(new Card("1234567890123452", 0));
		int numberOfCards = testAccount.getCards().size();
		assertEquals(3, numberOfCards);
	}

	@Test
	void testRemoveCard() {
		BankAccount testAccount = new BankAccount("testuser");
		Card testCard = new Card("1234567890123452", 0);
		testAccount.addCard(testCard);
		testAccount.removeCard(testCard.getNumber());
		int numberOfCards = testAccount.getCards().size();
		assertEquals(0, numberOfCards);
	}

	@Test
	void testRemoveCardInvalid() {
		BankAccount testAccount = new BankAccount("testuser");
		Card fakeCard = new Card("2222222222222222", 0);
		Card trueCard = new Card("1234567890123452", 0);
		testAccount.addCard(trueCard);
		try {
			testAccount.removeCard(fakeCard.getNumber());
			fail();
		} catch (IllegalAccessError e) {
			//we expect to end up here, the card fakeCard does not exist
			assertTrue(true);
		}

	}
	@Test
	void testRemoveCardWhenNoPresent() {
		BankAccount testAccount = new BankAccount("testuser");
		Card fakeCard = new Card("2222222222222222", 0);
		try {
			testAccount.removeCard(fakeCard.getNumber());
			fail();
		} catch (IllegalAccessError e) {
			//we expect to end up here, the card fakeCard does not exist
			assertTrue(true);
		}

	}

}

