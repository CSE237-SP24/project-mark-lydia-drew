package bankapp;

import java.util.*;
import java.io.*;

public class Menu {
	private Scanner in;
	private PrintStream out;
	private BankAccount account;
	
	//not tested
	public static void main(String[] args) {
		Menu mainMenu = new Menu();
		mainMenu.infiniteMenu();
	}
	
	//Constructors
	public Menu() {
		this.in = new Scanner(System.in);
        this.out = System.out;
        // Check if any accounts exist in the file
        this.account = loadAccountFromFile("src/accountData/accounts.txt");
        // If no accounts exist, prompt for a new user
        if (this.account == null) {
            this.account = createAccount();
            saveNewAccountToFile(this.account, "src/accountData/accounts.txt");
        }
	}
	//no tests needed because CardMenu has its own tests
	public void displayCardMenu(BankAccount account) {
		// Create an instance of CardMenu
		CardMenu cardMenu = new CardMenu(account);
		// Call a method in CardMenu to display the menu
		cardMenu.cardMenuDisplay();
	}
	// Prompts the user for a username and creates a new BankAccount
	private BankAccount createAccount() {
		out.println("Enter your username:");
		String username = in.nextLine();
		out.println("Now, enter a password to secure your account:");
		String passwd = in.nextLine();
		return new BankAccount(username, passwd);
	}
	
	//Code that just displays stuff - no tests needed
	public void displayingOptions() {
		System.out.print("To deposit money enter \"1\". To withdraw money enter \"2\".\n" +"To go to card menu enter \"3\". To quit enter \"4\": ");
	}
	
	//Menu method to loop until quit
	public void infiniteMenu() {
		while (true) {
			//Display options and send to helpers
			displayingOptions();
			String input = in.nextLine();
			if(menuLoop(input)) break;
		}
	}
	
	private boolean menuLoop(String input) {
		switch (input.toLowerCase()) {
			case "1":
				System.out.print("How much would you like to deposit: ");
				String dAmount = getValidUserInput();
				handleDeposit(dAmount);
				return false; // Continue loop
			case "2":
				System.out.print("How much would you like to withdraw: ");
				String wAmount = getValidUserInput();
				handleWithdrawal(wAmount);
				return false; // Continue loop
			case "3":
				displayCardMenu(account);
				return false; // Continue loop
			case "4":
				saveOverwriteAccountToFile(this.account, "src/accountData/accounts.txt");
				out.println("Thank you. Have a nice day!");
				return true; // Exit loop
			default:
				out.println("Invalid input.");
			return false; // Continue loop
		}
	}
	
	private void handleDeposit(String dAmount) {
		try {
			account.deposit(dAmount);
			out.println();
			processingUserSelection();
		}catch(IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	private void handleWithdrawal(String wAmount) {
		try {
			account.withdraw(wAmount);
			out.println();
			processingUserSelection();
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public String getValidUserInput() {
		double amount;
		while (true) {
			String input = in.nextLine(); // Read input as a string
			try {
				// Attempt to parse the input as a double
				amount = Double.parseDouble(input);
				// Check if the amount is positive
				if (amount < 0) {
					out.println("Invalid value! Amount must be positive.");
					continue; // Restart the loop to prompt the user again
				}
				break; // Break out of the loop if input is valid
			} catch (IllegalArgumentException e) {
				// Handle the exception (e.g., log it, display an error message)
				out.println("Error: " + e.getMessage() + " Try again.");
				continue;
			}
		}
		return String.valueOf(amount);
	}
	
	public void processingUserSelection() {
		out.println("Your balance is now: " + account.getBalance() + " for " + account.getUsername());
	}
	public BankAccount getAccount() {
		return account;
	}
	private BankAccount loadAccountFromFile(String filename) {
	        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                if (line.equals("ACCOUNT HEADER")) {
	                    String username = reader.readLine(); // Assuming "Username: " is 10 characters long
	                    String password = reader.readLine(); // Assuming "Password: " is 10 characters long
	                    double balance = Double.parseDouble(reader.readLine()); // Assuming "Balance: " is 9 characters long
	                    List<Card> cards = new ArrayList<>();
	                    while ((line = reader.readLine()) != null && !line.equals("END OF ACCOUNT")) {
	                        String[] cardInfo = line.split("\\s+");
	                        cards.add(new Card(cardInfo[0], Integer.parseInt(cardInfo[1])));
	                    }
	                    return new BankAccount(username, password, balance, cards);
	                }
	            }
	        } catch (IOException | NumberFormatException e) {
	            // Handle the exception
	           System.out.println("No file currently exists! Creating a new file from scratch.");
	            return null;
	        }
	        return null;
	}
	
	 private void saveNewAccountToFile(BankAccount account, String filename) {
	        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
	            // Write account information to the file
	            writer.println("ACCOUNT HEADER");
	            writer.println("Username: " + account.getUsername());
	            writer.println("Password: " + account.getPassword());
	            writer.println("Balance: " + account.getBalance());
	            writer.println("Cards:");
	            for (Card card : account.getCards()) {
	                writer.print(card.getNumber() + "  " + card.getType());
	            }
	            writer.println(); // Add a new line to separate accounts
	        } catch (IOException e) {
	            // Handle the exception
	            System.err.println("Error writing new account information to file: " + e.getMessage());
	        }
	    }
    // Method to save account to file
	private void saveOverwriteAccountToFile(BankAccount account, String filename) {
        try (RandomAccessFile file = new RandomAccessFile(filename, "rw")) {
            String line;
            long position = 0;
            while ((line = file.readLine()) != null) {
                if (line.equals("ACCOUNT HEADER")) {
                    String username = file.readLine().substring(10); // Assuming "Username: " is 10 characters long
                    if (username.equals(account.getUsername())) {
                    	position = file.getFilePointer() - ("ACCOUNT HEADER\n".length() + "Username: ".length() + username.length() + 1);
                        // Found the account to update, so seek back to the beginning of this account data
                        file.seek(position);
                        // Write account information to the file
                        file.writeBytes("ACCOUNT HEADER\n");
                        file.writeBytes("Username: " + account.getUsername() + "\n");
                        file.writeBytes("Password: " + account.getPassword() + "\n");
                        file.writeBytes("Balance: " + account.getBalance() + "\n");
                        file.writeBytes("Cards:\n");
                        for (Card card : account.getCards()) {
                            file.writeBytes(card.getNumber() + "  " + card.getType());
                        }
                        // Fill remaining space with spaces to overwrite any existing data
                        file.setLength(file.getFilePointer());
                        break;
                    }
                }
                position = file.getFilePointer();
            }
        } catch (IOException e) {
            // Handle the exception
            System.err.println("Error writing account information to file: " + e.getMessage());
        }
    }
}