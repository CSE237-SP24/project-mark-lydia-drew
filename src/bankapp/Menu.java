package bankapp;

import java.util.*;
import java.io.*;

public class Menu {
	private Scanner in;
	private PrintStream out;
	private BankAccount account;
	private String filepath;
	private double DEFAULT_INTEREST_RATE=0.05;
	
	//not tested
	public static void main(String[] args) {
		Menu mainMenu = new Menu();
		mainMenu.infiniteMenu();
	}

	//Constructors
	public Menu() {
		this.in = new Scanner(System.in);
		this.out = System.out;
		this.filepath = "src/accountData/accounts.txt";
		checkAccountsFileExists(filepath);
	}

	public Menu(BankAccount account, String testFile) {
		this.in = new Scanner(System.in);
		this.out = System.out;
		this.account = account;
		this.filepath = testFile;
	}


	public boolean checkAccountsFileExists(String filename) {
		File file = new File(filename);
		if (file.exists()) {
			handleExistingAccountsFile(filename);
			return true;
		} else {
			handleNonExistingAccountsFile(filename);
			return false;
		}
	}

	private void handleExistingAccountsFile(String filename) {
		out.println("Welcome to the BankApp!");
		out.println("Accounts file detected.");
		out.println("Would you like to create a new account or log in?");
		out.println("Enter '1' to create a new account, '2' to log in:");
		String input = in.nextLine();
		if ("1".equals(input)) {
			this.account = createAccount();
			saveNewAccountToFile(this.account, filename);
		} else if ("2".equals(input)) {
			this.account = login(filename);
		} else {
			out.println("Invalid input. Please try again.");
		}
	}

	private void handleNonExistingAccountsFile(String filename) {
		out.println("Welcome to the BankApp!");
		out.println("No accounts file detected.");
		out.println("Would you like to create a new account?");
		out.println("Enter '1' to create a new account:");
		String input = in.nextLine();
		if ("1".equals(input)) {
			this.account = createAccount();
			saveNewAccountToFile(this.account, filename);
		} else {
			out.println("Invalid input. Exiting program.");
			System.exit(0);
		}
	}

	// Method to handle the login process
	private BankAccount login(String filename) {
		out.println("Login");
		out.println("Enter your username:");
		String username = in.nextLine();
		if (username.equalsIgnoreCase("quit")) {
			return null; 
		}
		out.println("Enter your password:");
		String password = in.nextLine();
		BankAccount loggedInAccount = findAccount(username, password, filename);
		if (loggedInAccount != null) {
			out.println("Login successful. Welcome, " + username + "!");
		} else {
			out.println("Username or password is incorrect. Please try again.");
			loggedInAccount = login(filename); // Retry login recursively
		}
		return loggedInAccount;
	}

	public BankAccount findAccount(String username, String password, String filename) {
		try (Scanner scanner = new Scanner(new File(filename))) {
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
			return new BankAccount(username, password, balance, cards, null);
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
		System.out.print("To deposit money enter \"1\". To withdraw money enter \"2\".\n" +"To go to card menu enter \"3\". To pay a bill enter \"4\".\n" + "To handle loans enter \"5\". To quit enter \"6\". " + "If you want to close your account, enter \"7\": ");
	}

	//Menu method to loop until quit
	public void infiniteMenu() {
		while (true) {
			//Display options and send to helpers
			displayingOptions();
			String input = in.nextLine();
			if(menuLoop(input)) {
				break;
			}
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
			handleLoan();
			return false; //Continue loop
		case "6":
			saveOverwriteAccountToFile(this.account, "src/accountData/accounts.txt");
			out.println("Thank you. Have a nice day!");
			return true; // Exit loop
		case "7":
			boolean didDelete = handleAccountDeletion(this.account);
			if (didDelete) {
				out.println("Thank you for doing business with us.");
				return true; // Exit loop
			}
			else {
				return false; 
			}

		default:
			out.println("Invalid input.");
			return false; // Continue loop
		}
	}
	public void listLoans() {
		List<Loan> loans =account.getLoans();
		int loanIndex=1;
		for(Loan l : loans) {
			System.out.println(loanIndex+") amount unpaid: "+l.getAmountUnpaid());
			loanIndex++;
		}
	}
	public void handleLoanApplication(double amount) {
		Loan loan = new Loan(amount, DEFAULT_INTEREST_RATE);
		loan.grantLoanWithIncome(account);
	}
	public void handleLoanPayment(List<Loan> loans, int loanIndx) {
		System.out.print("Payment size for loan "+loanIndx+": ");
		Double paymentAmount = Double.parseDouble(getValidUserInput());
		try {
			loans.get(loanIndx-1).tryLoanPayment(paymentAmount);
		}catch(IllegalArgumentException e) {
			System.out.print("Error: "+e.getMessage());
		}
	}
	public void handleLoanPaymentSelection() {
		List<Loan> loans = account.getLoans();
		System.out.println("these are your current loans");
		listLoans();
		System.out.println("Enter the number of which loan would you like to make a payment to, or \"0\" to quit: ");
		int loanSelection = (int) Double.parseDouble(getValidUserInput());
		if(loanSelection>0&&loanSelection<=loans.size()) {handleLoanPayment(loans,loanSelection);}
		else {System.out.println("no payment made, leaving payment option");}
		
		
	}
	public void handleLoan() {
		System.out.println("To apply for a loan enter \"1\". To make a payment on an existing loan enter \"2\". To see all of your loans enter \"3\". To leave loan menu enter anything else");
		String input = in.nextLine();
		switch(input) {
			case "1":
				System.out.println("How much money do you want the loan to be: ");
				double lAmount = Double.parseDouble(getValidUserInput());
				handleLoanApplication(lAmount);
				break;
			case "2":
				handleLoanPaymentSelection();
				break;
			case "3":
				listLoans();
				break;
			default:
				System.out.println("leaving loan menu");
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

	public boolean handleAccountDeletion(BankAccount account) {
		out.println("WARNING: Closing your account will remove all data on file, including balances and loans.");
		out.println("You are still legally obligated to fulfill any debts. As this is just a simulation, you will also lose all of your funds stored with us.");
		out.println("Are you sure you wish to close your account? Type 'Y' for yes, 'N' for no.");
		String confirmation = in.nextLine();
		if (confirmation.equalsIgnoreCase("Y")) {
			out.println("To proceed, please enter your password:");
			String enteredPassword = in.nextLine();
			// Verify the entered password with the account's password
			if (enteredPassword.equals(account.getPassword())) {
				out.println("Account closed successfully.");
				removeAccountFromFile(account, filepath);
				return true;
			} else {
				out.println("Password incorrect. Account closure aborted.");
			}
		} else if (confirmation.equalsIgnoreCase("N")) {
			out.println("Account closure aborted.");
		} else {
			out.println("Invalid input. Account closure aborted.");
		}
		// Return false if deletion is aborted or unsuccessful
		return false;
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
	public void saveNewAccountToFile(BankAccount account, String filename) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
			// Write account information to the file
	
	 public void saveNewAccountToFile(BankAccount account, String filename) {
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
		
			return new BankAccount(username, password, balance, cards,null);
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
			writer.println("Cards:");
			for (Card card : account.getCards()) {
				writer.print(card.getNumber() + "  " + card.getTypeNum());
			}
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
	public void saveOverwriteAccountToFile(BankAccount account, String filename) {
		List<BankAccount> accounts = readAccountsFromFile(filename);
		updateOrAddAccount(accounts, account);
		writeAccountsToFile(accounts, filename);
	}

	public void removeAccountFromFile(BankAccount account, String filepath) {
		List<BankAccount> accounts = readAccountsFromFile(filepath);
		accounts.removeIf(acc -> acc.getUsername().equals(account.getUsername())); // Remove the account
		// Rewrite the file with the updated list of accounts
		writeAccountsToFile(accounts, filepath);
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