package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import bankapp.Card;
import bankapp.CardMenu;

public class CardMenuTests {

    @Test
	void testNewCard() {
		CardMenu m = new CardMenu();
		//user has provided a debit card with the number "0000111122223333"
		m.processingUserSelection("0000111122223333", 1);
		
		Card card = m.getCard();
		assertEquals("0000111122223333", card.getNumber());
        assertEquals("debit", card.getType());
	}

}
