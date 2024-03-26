package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

import bankapp.BankAccount;
import bankapp.Menu;

class MenuTests {
	
	

	@Test
	void testGetValidUserInputSimple() {
		BankAccount account = new BankAccount("test");
		Menu m = new Menu(account);
		
		System.setIn(new ByteArrayInputStream("5".getBytes()));
		
		String valid = m.getValidUserInput();
		System.out.println("t");
		
		assertTrue(valid.equals("5"));
	}

}
