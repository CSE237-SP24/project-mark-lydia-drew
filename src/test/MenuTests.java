package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import bankapp.Menu;
import bankapp.BankAccount;

public class MenuTests {

    private Menu menu;


    @Test
    public void testConstructorWithUsername() {
    	menu = new Menu(new BankAccount("testUser"));
    	assertNotNull(menu);
        assertNotNull(menu.getAccount());
    }

}
