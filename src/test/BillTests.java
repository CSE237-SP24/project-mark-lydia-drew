package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import bankapp.Bill;

class BillTests {

	@Test
	void testSimpleBill() {
		Bill bill = new Bill();
		bill.setAmount(50.00);
		assertEquals(50.00, bill.getAmount(), 0.001);
	}
	
	@Test
	void testZeroBill() {
		Bill bill = new Bill();
		bill.setAmount(0);
		assertEquals(0, bill.getAmount(), 0.001);
	}
	
	@Test
	void testInvalidBill() {
		Bill bill = new Bill();
		try {
			bill.setAmount(-100);
			fail();
		} catch (IllegalArgumentException e) {
			//we expect to end up here, "-100" is a bad input
			assertTrue(true);
		}
		
	}

}
