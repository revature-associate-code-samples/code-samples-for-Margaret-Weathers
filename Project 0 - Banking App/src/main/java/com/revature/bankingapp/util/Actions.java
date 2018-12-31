package com.revature.bankingapp.util;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.revature.bankingapp.pojos.Account;
import com.revature.bankingapp.pojos.Transaction;
import com.revature.bankingapp.pojos.User;
import com.revature.bankingapp.service.AccountService;
import com.revature.bankingapp.service.TransactionService;
import com.revature.bankingapp.service.UserService;

/**
 * 
 * General utility class with methods to create user, login, check if user exists, create account,
 * 	and display user options once logged in.  
 *
 */
public class Actions {

	static UserService us = new UserService();
	static AccountService as = new AccountService();
	static TransactionService ts = new TransactionService();

	/**
	 * Create a new user account with username, password, first name, and last name entered by the user
	 * @return User
	 */
	@SuppressWarnings("resource")
	public static User createUser() {	
		Scanner in = new Scanner(System.in);
		String username = null;
		String password = null;
		String first = null;
		String last = null;

		// Get first and last names
		System.out.println("Enter your first name: ");
		while(in.hasNextLine()) {
			first = in.nextLine();
			if (!first.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
				System.out.println("Names must only contain an alphabetical character or hyphen\n+"
						+ "Please enter a valid first name: ");
			}
			else {
				first = first.substring(0, 1).toUpperCase() + first.substring(1);
				break;
			}
		}
		System.out.println("Enter your last name: ");
		while(in.hasNextLine()) {
			last = in.nextLine();
			if (!last.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
				System.out.println("Names must only contain an alphabetical character or hyphen\n"
						+ "Please enter a valid last name: ");
			}
			else {
				last = last.substring(0, 1).toUpperCase() + last.substring(1);
				break;
			}
		}
		// USERNAME VALIDATION
		List<String> usernames = us.getAllUsernames();
		System.out.println("Enter a username: ");
		while (in.hasNextLine()) {
			username = in.nextLine();
			username = username.toLowerCase();		//	Store all usernames in the db as lower case
			if (usernames.isEmpty()) {				// If there are no users in the database
				break;
			}
			else if (username == null || username.isEmpty()) {
				System.out.println("Username must contain at least 1 character. Please enter a valid username: ");
			}
			else if (usernames.contains(username)) {
				System.out.println("Username already exists. Please enter a different username: ");
			}
			else break;	
		}
		// PASSWORD VALIDATION
		System.out.println("Enter a password: ");
		while(in.hasNextLine()) {
			password = in.nextLine();
			if (password == null || password.isEmpty()) {
				System.out.println("Password must contain at least 1 character. Please enter a valid password: ");
			}
			else {
				System.out.println("Re-enter password: ");
				if ( in.nextLine().equals(password) ) {
					break;
				}
				else {
					System.out.println("Passwords did not match\nEnter a password: ");
				}
			}
		}
		//		in.close();
		return new User(username, password, first, last);			
	}

	/**
	 * @param username
	 * @return True if the user exists in the database
	 */
	public static boolean userExists(String username) {
		List<String> usernames = us.getAllUsernames();		
		if (usernames.contains(username)) return true;
		else return false;
	}

	/**
	 * Prompts the user to login
	 */
	public static User login() {
		User user = new User();
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Enter your username: ");
		String username = null;
		while(in.hasNextLine()) {
			username = in.nextLine();
			username = username.toLowerCase();
			boolean exists = Actions.userExists(username);					
			if (exists == false) {
				System.out.println("A user account with this username does not exist. Please enter an existing username: ");
			}
			else if (exists == true) {
				//Create User object from username and retrieve password
				user = us.getUserByUsername(username);
				String password = user.getPw();
				String input;
				int pwTries = 0;
				System.out.println("Enter your password: ");
				while (in.hasNextLine()) {
					input = in.nextLine();
					if (!input.equals(password)) {
						if (pwTries < 3) {
							System.out.println("You entered the wrong password. Please try again: ");
							pwTries++;
						}
						// Gives user 4 pw tries, then exits program
						else{
							System.out.println("You entered the wrong password too many times. Exiting banking application.");
							System.exit(0);
						}
					}
					else {
						System.out.println("Hello " + user.getFirst() + "!");
						break;
					}
				}			
				break;
			}
		}
		return user;
	}


	/**
	 * Runs welcome screen once logged in
	 * @param user
	 */
	public static void giveOptions(User user) {
		@SuppressWarnings("resource")
		Scanner in2 = new Scanner(System.in);
		String userInput;
		int userId = user.getUsrId();
		List<Account> accounts = as.getAccountsByUserID(userId);			// ONLY RETRIEVES ACTIVE ACCOUNTS
		// If user does not have any active  accounts
		if (accounts == null) {
			System.out.println("You don't have any active bank accounts. Would you like to create one? [y/n]");
			while(in2.hasNextLine()) {
				userInput = in2.nextLine();
				if( (userInput.equalsIgnoreCase("n")) || (userInput.equalsIgnoreCase("no")) ) {
					System.out.println("Okay, Goodbye!");
					System.exit(0);
				}
				else if ( (userInput.equalsIgnoreCase("y")) || (userInput.equalsIgnoreCase("yes")) ) {
					Account acc = createAccount(user);
					as.createAccount(acc);
					System.out.println("Congratulations, you made an account!");
					giveOptions(user);
					break;
				}
				else {
					System.out.println("Please enter [y] or [n]");
				}
			}
		}
		// Else, if user as at least 1 account, give them general options
		else {
			System.out.println("\nSelect a transacion:"
					+ "\n[1] Withdrawal"
					+ "\n[2] Deposit"
					+ "\n[3] Transfer"
					+ "\n[4] View Balance"
					+ "\n[5] View Transactions"
					+ "\n[6] Create Bank Account"
					+ "\n[7] Deactivate Account"
					+ "\n[8] Exit Application");
			int option = 0;
			Account acc;
			Account updatedAcc;
			try {
				option = in2.nextInt();
				switch(option) {
				case 1: // withdraw
					System.out.println("Withdraw from: ");
					acc = chooseAccount(accounts);
					if (acc.getBalance() == 0) {
						System.out.println("You have no money in this account.");
						giveOptions(user);
						break;
					}
					updatedAcc = AccountTransactions.withdraw(acc);
					if (updatedAcc == null) {
						System.out.println("You do not have enough funds in your account for this transaction.");
						giveOptions(user);
						break;
					}
					as.updateAccount(updatedAcc);
					giveOptions(user);
					break;
				case 2: // deposit
					System.out.println("Deposit to: ");
					acc = chooseAccount(accounts);
					updatedAcc = AccountTransactions.deposit(acc);
					as.updateAccount(updatedAcc);
					giveOptions(user);
					break;
				case 3:	// transfer
					if (accounts.size() < 2) {
						System.out.println("You need multiple bank accounts to complete a transfer.");
						giveOptions(user);
						break;
					}
					System.out.println("Transfer to: ");
					Account transferTo = chooseAccount(accounts);
					System.out.println("Transfer from: ");
					Account transferFrom = chooseAccount(accounts);
					AccountTransactions.transfer(transferTo, transferFrom);
					giveOptions(user);
					break;
				case 4: // view balance
					System.out.println("View balance of: ");
					acc = chooseAccount(accounts);
					AccountTransactions.viewBalance(acc);
					giveOptions(user);
					break;
				case 5: // view transactions
					System.out.println("View transactions from: ");
					acc = chooseAccount(accounts);
					List<Transaction> transactions = ts.getTransactionsbyUser(user.getUsrId(), acc.getAccId());
					printTransactions(transactions);
					giveOptions(user);
					break;
				case 6: // create account
					Account newAcc = createAccount(user);
					as.createAccount(newAcc);
					System.out.println("Congratulations, you made an account!");
					giveOptions(user);
					break;
				case 7: //deactivate account
					System.out.println("Which account would you like to permanantly deactivate?");
					acc = chooseAccount(accounts);
					Account deactivated = AccountTransactions.deActivateAccount(acc);
					as.updateAccount(deactivated);
					System.out.println("Permantly deactivated '" + deactivated.getNickname() + "' Account." );
					giveOptions(user);
					break;
				case 8: // exit program
					System.out.println("Goodbye!");
					System.exit(0);
				default:
					System.out.println("Not a valid option");
					giveOptions(user); break;
				}
			} catch(InputMismatchException e) {
				System.out.println("Must enter a numerical choice");
				giveOptions(user);			
			}
		}

		//		in2.close();
	}

	public static Account createAccount(User user) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		Account account = new Account();
		System.out.println("Which type of bank account would you like to create?"
				+ "\n[1] Checkings"
				+ "\n[2] Savings"
				+ "\n[3] Money Market");
		int accType = 0;
		try {
			accType = in.nextInt();
			switch(accType) {
			case 1: return createSpecificAccount(user, accType);
			case 2: return createSpecificAccount(user, accType);
			case 3: return createSpecificAccount(user, accType);
			default:
				System.out.println("Not a valid option");
				account = createAccount(user);
				break;
			}
		}catch(InputMismatchException e) {
			System.out.println("Must enter a numerical choice [1][2][3]");
			account = createAccount(user);		
		}	
		return account;
	}

	// Get "nickname" for creating an account
	public static Account createSpecificAccount(User user, int accType) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("What would you like to name your account?");
		String nickname = in.nextLine();		
		return new Account(user.getUsrId(), accType, nickname, 0.00);	// new account balance set to 0
	}


	public static Account chooseAccount(List<Account> accounts) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		int choice;
		Account acc = null;
		for (int i = 1; i <= accounts.size(); i++) {
			System.out.println("[" + i + "] " + accounts.get(i-1).toString());
		}
		try {
			choice = in.nextInt();
			if(choice < 1 || choice > accounts.size()) {
				System.out.println("Must enter a valid numerical choice");
				acc = chooseAccount(accounts);
			}
			else {
				acc = accounts.get(choice-1);
			}			
		} catch(InputMismatchException e) {
			System.out.println("Must enter a valid numerical choice");
			acc = chooseAccount(accounts);		
		}
		return acc; 		
	}

	static void printTransactions(List<Transaction> trans) {
		if (trans == null) {
			System.out.println("You have not performed any transactions on this account.");
		}
		else {
			Iterator<Transaction> itr = trans.iterator();
			while (itr.hasNext()) {
				System.out.println(itr.next());	
			}
		}

	}


}
