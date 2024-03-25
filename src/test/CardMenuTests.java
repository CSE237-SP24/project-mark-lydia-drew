package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import bankapp.BankAccount;
import bankapp.Card;
import bankapp.CardMenu;

public class CardMenuTests {

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
		assertEquals(3, m.numberOfCards());
	}
    
    

}
