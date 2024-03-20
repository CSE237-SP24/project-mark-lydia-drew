package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import bankapp.Card;

class CardTests {

    @Test
	void testValidNumber() {
	
		Card testCard = new Card();

		testCard.setNumber("1234123412341234");

		assertEquals("1234123412341234", testCard.getNumber());	
	}

    @Test 
    void testInvalidLength() {

        Card testCard = new Card();

        try {
			testCard.setNumber("9876");
			fail();
		} catch (IllegalArgumentException e) {
			//we expect to end up here, "9876" is a bad input
			assertTrue(true);
		}
    }

    @Test 
    void testInvalidCharacters() {

        Card testCard = new Card();

        try {
			testCard.setNumber("1234A09ix2O19274");
			fail();
		} catch (IllegalArgumentException e) {
			//we expect to end up here, "1234A09ix2O19274" is a bad input
			assertTrue(true);
		}

    }

    @Test
    void testValidTypeCredit() {

        Card testCreditCard = new Card();
        testCreditCard.setType(0);
        assertEquals("credit", testCreditCard.getType());
    }

    @Test
    void testValidTypeDebit() {

        Card testDebitCard = new Card();
        testDebitCard.setType(1);
        assertEquals("debit", testDebitCard.getType());
    }

    @Test
    void testInvalidType() {

        Card testCard = new Card();

        try {
			testCard.setType(100);
			fail();
		} catch (IllegalArgumentException e) {
			//we expect to end up here, 100 is a bad input
			assertTrue(true);
		}

    }


}
