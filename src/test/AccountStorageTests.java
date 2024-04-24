package test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.Test;
import java.util.*;
import java.io.*;
import bankapp.Menu;
import bankapp.BankAccount;
import bankapp.Card;

public class AccountStorageTests {
	
	
	@Test
    public void testCheckAccountsFileExists_FileExists() {
        // Arrange
        String filename = "src/accountData/accounts.txt";
        Menu menu = new Menu(new BankAccount("testuser", "passwd"), filename);
        
        // Act
        boolean result = menu.checkAccountsFileExists(filename);
        
        // Assert
        assertTrue(result, "The method should return true when the file exists.");
    }
	@Test
    public void testCheckAccountsFileExists_FileDoesNotExist() {
        // Arrange
        String filename = "src/accountData/nonexistent.txt";
        Menu menu = new Menu(new BankAccount("testuser", "passwd"), filename);
        
        // Act
        boolean result = menu.checkAccountsFileExists(filename);
        File file = new File(filename);
        file.delete();
        // Assert
        assertFalse(result, "File does not exist, but it was detected.");
    }
	 @Test
	 public void testSaveNewAccountToFile() {
        // Arrange
        String filename = "src/accountData/accounts_test.txt";
        Menu menu = new Menu(new BankAccount("testuser", "passwd"), filename);
        BankAccount account = new BankAccount("test_user_new", "test_pass_new", 100.0, new ArrayList<>(), new ArrayList<>());
        
        // Act
        menu.saveNewAccountToFile(account, filename);
        boolean res = menu.checkAccountsFileExists(filename);
        File file = new File(filename);
        file.delete();
        // Assert
        assertTrue(res, "File was not created.");
    }

    @Test
    public void testUpdateAccountDataBalance() {
        // Arrange
        String filename = "src/accountData/accounts_test.txt";
        Menu menu = new Menu(new BankAccount("testuser", "passwd"), filename);
        BankAccount account = new BankAccount("test_user_overwrite", "test_pass_overwrite", 100.0, new ArrayList<>(), new ArrayList<>());
        menu.saveNewAccountToFile(account, filename);
        // Act
        String deposit = "150.0";
        account.deposit(deposit);
        menu.saveOverwriteAccountToFile(account, filename);
        BankAccount updatedAccount = menu.findAccount("test_user_overwrite", "test_pass_overwrite", filename);
        File file = new File(filename);
        file.delete();
        // Assert
        assertEquals(250, updatedAccount.getBalance(), "Balance was not updated.");
    }
    
    @Test
    public void testAddCard() {
        // Action: Add a new card
    	String filename = "src/accountData/accounts_test.txt";
        Menu menu = new Menu(new BankAccount("testuser", "passwd"), filename);
        BankAccount account = new BankAccount("test_user_overwrite", "test_pass_overwrite", 100.0, new ArrayList<>(), new ArrayList<>());
        menu.saveNewAccountToFile(account, filename);
        String cardNumber = "1234567890123456";
        int cardType = 1;
        Card cardToCheck = new Card(cardNumber, cardType);
        account.addCard(cardToCheck);
        menu.saveOverwriteAccountToFile(account, filename);
        BankAccount updatedAccount = menu.findAccount("test_user_overwrite", "test_pass_overwrite", filename);
        Card foundCard = updatedAccount.findCard(cardNumber);
        File file = new File(filename);
        file.delete();
        // Assertion: Verify that the added card exists in the BankAccount
        assertEquals(cardToCheck.getNumber(), foundCard.getNumber());
        assertEquals(cardToCheck.getType(), foundCard.getType());
    }
    
    @Test
    public void testDeleteCard() {
        // Action: Delete a  card
    	String filename = "src/accountData/accounts_test.txt";
        Menu menu = new Menu(new BankAccount("testuser", "passwd"), filename);
        BankAccount account = new BankAccount("test_user_overwrite", "test_pass_overwrite", 100.0, new ArrayList<>(), new ArrayList<>());
        menu.saveNewAccountToFile(account, filename);
        String cardNumber = "1234567890123456";
        int cardType = 1;
        String cardNumber2 = "0987654321098765";
        int cardType2 = 0;
        Card realCard = new Card(cardNumber, cardType);
        Card removeCard =  new Card(cardNumber2, cardType2);
        account.addCard(realCard);
        account.addCard(removeCard);
        menu.saveOverwriteAccountToFile(account, filename);
        account.removeCard(cardNumber2);
        menu.saveOverwriteAccountToFile(account, filename);
        BankAccount updatedAccount = menu.findAccount("test_user_overwrite", "test_pass_overwrite", filename);
        Card foundCard = updatedAccount.findCard(cardNumber2);
        File file = new File(filename);
        file.delete();
        // Assertion: Verify that the  card was deleted in the BankAccount
        assertNull(foundCard);
    }
    @Test
    public void testEditAccountDoesNotAffectOtherAccounts() {
        // Action: Add multiple accounts to the file
        String filename = "src/accountData/accounts_test.txt";
        Menu menu = new Menu(new BankAccount("testuser", "passwd"), filename);

        // Create and save multiple accounts to the file
        BankAccount account1 = new BankAccount("user1", "pass1", 100.0, new ArrayList<>(), new ArrayList<>());
        BankAccount account2 = new BankAccount("user2", "pass2", 200.0, new ArrayList<>(), new ArrayList<>());
        BankAccount account3 = new BankAccount("user3", "pass3", 300.0, new ArrayList<>(), new ArrayList<>());

        menu.saveNewAccountToFile(account1, filename);
        menu.saveNewAccountToFile(account2, filename);
        menu.saveNewAccountToFile(account3, filename);

        // Edit accounts and update them in the file
        String cardNumber = "1234567890123456";
        int cardType = 1;
        String cardNumber2 = "0987654321098765";
        int cardType2 = 0;
        Card realCard = new Card(cardNumber, cardType);
        Card cardToCheck =  new Card(cardNumber2, cardType2);
        account1.addCard(realCard);
        account1.addCard(cardToCheck);
        menu.saveOverwriteAccountToFile(account1, filename);
        
        String testNum = "1111111111111111";
        int testTyp = 0;
        Card testCard = new Card(testNum, testTyp);
        account2.addCard(testCard);
        menu.saveOverwriteAccountToFile(account2, filename);

        // Retrieve and verify other accounts from the file
        BankAccount updatedAccount1 = menu.findAccount("user1", "pass1", filename);
        BankAccount updatedAccount2 = menu.findAccount("user2", "pass2", filename);
        BankAccount updatedAccount3 = menu.findAccount("user3", "pass3", filename);

        // Assertion: Verify that editing account2 did not affect account1 and account3
        Card foundCard = updatedAccount1.findCard(cardNumber2);
        File file = new File(filename);
        file.delete();
        assertEquals(cardToCheck.getNumber(), foundCard.getNumber());
        assertEquals(cardToCheck.getType(), foundCard.getType());
        assertNotNull(updatedAccount2);
        assertNotNull(updatedAccount3);
    }
}
