package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bankapp.CreditScore;

class CreditScoreTests {
	
	CreditScore score;
	
	@BeforeEach
	public void setUp() {
		this.score = new CreditScore();
	}

	@Test
	void testSimpleCreditScore() {
		score.setScore(740);
		assertEquals("Excellent", score.getScoreRange());
	}
	
	@Test
	void testSecondSimpleCreditScore() {
		score.setScore(578);
		assertEquals("Poor", score.getScoreRange());
	}
	
	@Test
	void testInvalidScore() {
		try {
			score.setScore(1000);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	void testZeroScore() {
		try {
			score.setScore(0);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	

}
