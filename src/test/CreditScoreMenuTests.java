package test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bankapp.CreditScoreMenu;

public class CreditScoreMenuTests {
	
	CreditScoreMenu menu;
	
	@BeforeEach
	public void setUp(){
		this.menu = new CreditScoreMenu();
	}
	
	@Test
	void testCreditScoreConversion() {
		int highScore = menu.convertToCreditRange(1000);
		assertEquals(850, highScore);
		int lowScore = menu.convertToCreditRange(100);
		assertEquals(300, lowScore);
	}
	

}
