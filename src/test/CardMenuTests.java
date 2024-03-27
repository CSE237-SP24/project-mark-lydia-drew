package test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import bankapp.BankAccount;
import bankapp.CardMenu;

public class CardMenuTests {
	
	@Test
	void testConstructorWithUsername() {
		CardMenu menu = new CardMenu(new BankAccount("testUser"));
		assertNotNull(menu);
	}

    @Test
	void testAddCard() {
		CardMenu m = new CardMenu(new BankAccount("testuser"));
		m.addCard();
		assertEquals(1, m.numberOfCards());
	}
    
    @Test
	void testAddMultipleCards() {
		CardMenu m = new CardMenu(new BankAccount("testuser"));
		m.addCard();
		m.addCard();
		m.addCard();
		m.addCard();
		assertEquals(4, m.numberOfCards());
	}

    

}
