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
        checkAccountsFileExists("src/accountData/accounts.txt");
	}
	
	public Menu(BankAccount account) {
		this.in = new Scanner(System.in);
        this.out = System.out;
        this.account = account;
	}

	public void checkAccountsFileExists(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            handleExistingAccountsFile();
        } else {
            handleNonExistingAccountsFile();
        }
    }

	private void handleExistingAccountsFile() {
        out.println("Welcome to the BankApp!");
        out.println("Accounts file detected.");
        out.println("Would you like to create a new account or log in?");
        out.println("Enter '1' to create a new account, '2' to log in:");
        String input = in.nextLine();
        if ("1".equals(input)) {
            this.account = createAccount();
            saveNewAccountToFile(this.account, "src/accountData/accounts.txt");
        } else if ("2".equals(input)) {
            this.account = login();
        } else {
            out.println("Invalid input. Please try again.");
        }
    }

    private void handleNonExistingAccountsFile() {
        out.println("Welcome to the BankApp!");
        out.println("No accounts file detected.");
        out.println("Would you like to create a new account?");
        out.println("Enter '1' to create a new account:");
        String input = in.nextLine();
        if ("1".equals(input)) {
            this.account = createAccount();
            saveNewAccountToFile(this.account, "src/accountData/accounts.txt");
        } else {
            out.println("Invalid input. Exiting program.");
            System.exit(0);
        }
    }

	// Method to handle the login process
    private BankAccount login() {
		out.println("Login");
		out.println("Enter your username:");
		String username = in.nextLine();
		out.println("Enter your password:");
		String password = in.nextLine();
		BankAccount loggedInAccount = findAccount(username, password);
		if (loggedInAccount != null) {
			out.println("Login successful. Welcome, " + username + "!");
		} else {
			out.println("Username or password is incorrect. Please try again.");
			loggedInAccount = login(); // Retry login recursively
		}
		return loggedInAccount;
	}
	
    private BankAccount findAccount(String username, String password) {
        try (Scanner scanner = new Scanner(new File("src/accountData/accounts.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Username: ") && line.substring(10).equals(username)) {
                    return processAccount(scanner, username, password);
                }
            }
            out.println("Username not found.");
            return null;
        } catch (FileNotFoundException e) {
            System.err.println("Error: Accounts file not found.");
            return null;
        }
    }

    private BankAccount processAccount(Scanner scanner, String username, String password) {
        String storedPassword = scanner.nextLine().substring(10);
        if (storedPassword.equals(password)) {
            double balance = Double.parseDouble(scanner.nextLine().substring(9));
            List<Card> cards = readCards(scanner);
            return new BankAccount(username, password, balance, cards);
        } else {
            out.println("Password incorrect. Please try again.");
            return null;
        }
    }

    private List<Card> readCards(Scanner scanner) {
        List<Card> cards = new ArrayList<>();
        scanner.nextLine(); // Skip empty line or "Cards:" line
        while (scanner.hasNextLine()) {
            String cardLine = scanner.nextLine();
            if (cardLine.startsWith("ACCOUNT HEADER")) {
                break; // End of card details for this account
            }
            if (!cardLine.isEmpty()) {
                cards.add(createCardFromLine(cardLine));
            }
        }
        return cards;
    }

    private Card createCardFromLine(String cardLine) {
        String[] cardDetails = cardLine.split("\\s+");
        String cardNumber = cardDetails[0];
        int cardType = Integer.parseInt(cardDetails[1]);
        return new Card(cardNumber, cardType);
    }

	

	//no tests needed because CardMenu has its own tests
	public void displayCardMenu(BankAccount account) {
		// Create an instance of CardMenu
		CardMenu cardMenu = new CardMenu(account);
		// Call a method in CardMenu to display the menu
		cardMenu.cardMenuDisplay();
	}
	
	public void displayBillMenu(BankAccount account) {
		BillMenu billMenu = new BillMenu(account);
		billMenu.billMenuDisplay();
		if (billMenu.switchMenu()) {
			infiniteMenu();
		}
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
		System.out.print("To deposit money enter \"1\". To withdraw money enter \"2\".\n" +"To go to card menu enter \"3\". To pay a bill enter \"4\".\n" + "To quit enter \"5\": ");
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
	
	public boolean menuLoop(String input) {
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
				displayBillMenu(account);
				return false; // Continue loop
			case "5":
				saveOverwriteAccountToFile(this.account, "src/accountData/accounts.txt");
				out.println("Thank you. Have a nice day!");
				return true; // Exit loop
			default:
				out.println("Invalid input.");
			return false; // Continue loop
		}
	}
	
	public void handleDeposit(String dAmount) {
		try {
			account.deposit(dAmount);
			out.println();
			processingUserSelection();
		}catch(IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void handleWithdrawal(String wAmount) {
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
	
	 private void saveNewAccountToFile(BankAccount account, String filename) {
	        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
	            // Write account information to the file
	            writer.println("ACCOUNT HEADER");
	            writer.println("Username: " + account.getUsername());
	            writer.println("Password: " + account.getPassword());
	            writer.println("Balance: " + account.getBalance());
	            writer.println("Cards:");
	            for (Card card : account.getCards()) {
	                writer.print(card.getNumber() + "  " + card.getTypeNum());
	            }
	            writer.println(); // Add a new line to separate accounts
	        } catch (IOException e) {
	            // Handle the exception
	            System.err.println("Error writing new account information to file: " + e.getMessage());
	        }
	    }
	 
		private BankAccount readAccountFromLine(Scanner scanner) {
			String test = scanner.nextLine();
			String username = null;
			if (test.equals("ACCOUNT HEADER")) {
				 username = scanner.nextLine().substring(10);
			}
			else {
				 username = test.substring(10);
			}
			String password = scanner.nextLine().substring(10); // Assuming "Password: " is 10 characters long
			double balance = Double.parseDouble(scanner.nextLine().substring(9)); // Assuming "Balance: " is 9 characters long
			// Read cards
			List<Card> cards = new ArrayList<>();
			scanner.nextLine(); // Skip the "Cards:" line
			cardAdder(scanner, cards);
		
			return new BankAccount(username, password, balance, cards);
		}
		
		private void cardAdder(Scanner scanner, List<Card> cards) {
		    while (scanner.hasNextLine()) {
		        String cardLine = scanner.nextLine();
		        if (cardLine.equals("ACCOUNT HEADER")) {
		            break; // Stop reading cards if next account header is encountered
		        }
		        String[] cardDetails = cardLine.split("\\s+");
		        String cardNumber = cardDetails[0];
		        int cardType = Integer.parseInt(cardDetails[1]);
		        cards.add(new Card(cardNumber, cardType));
		    }
		}
		
		private void writeAccountToLine(PrintWriter writer, BankAccount account) {
			// Write username, password, and balance to the file
			writer.println("ACCOUNT HEADER");
			writer.println("Username: " + account.getUsername());
			writer.println("Password: " + account.getPassword());
			writer.println("Balance: " + account.getBalance());
		
			// Write cards
			writer.println("Cards:");
			for (Card card : account.getCards()) {
				writer.println(card.getNumber() + " " + card.getTypeNum());
			}
		}
    // Method to save account to file while overwriting
		private void saveOverwriteAccountToFile(BankAccount account, String filename) {
		    List<BankAccount> accounts = readAccountsFromFile(filename);
		    updateOrAddAccount(accounts, account);

		    writeAccountsToFile(accounts, filename);
		}

		private List<BankAccount> readAccountsFromFile(String filename) {
		    List<BankAccount> accounts = new ArrayList<>();
		    try (Scanner scanner = new Scanner(new File(filename))) {
		        while (scanner.hasNextLine()) {
		            BankAccount storedAccount = readAccountFromLine(scanner);
		            accounts.add(storedAccount);
		        }
		    } catch (FileNotFoundException e) {
		        System.err.println("Error: Accounts file not found.");
		    }
		    return accounts;
		}

		private void updateOrAddAccount(List<BankAccount> accounts, BankAccount newAccount) {
		    boolean updated = false;
		    for (int i = 0; i < accounts.size(); i++) {
		        BankAccount storedAccount = accounts.get(i);
		        if (storedAccount.getUsername().equals(newAccount.getUsername())) {
		            accounts.set(i, newAccount);
		            updated = true;
		            break;
		        }
		    }
		    if (!updated) {
		        accounts.add(newAccount);
		    }
		}

		private void writeAccountsToFile(List<BankAccount> accounts, String filename) {
		    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
		        for (BankAccount storedAccount : accounts) {
		            writeAccountToLine(writer, storedAccount);
		        }
		    } catch (IOException e) {
		        System.err.println("Error writing account information to file: " + e.getMessage());
		    }
		}

}