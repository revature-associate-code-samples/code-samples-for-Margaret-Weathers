package com.revature.bankingapp.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.revature.bankingapp.pojos.Account;
import com.revature.bankingapp.pojos.Transaction;
import com.revature.bankingapp.service.AccountService;
import com.revature.bankingapp.service.TransactionService;


/**
 * 
 * Utility class with methods to  withdraw, deposit, transfer, view balance, and delete account.
 *
 */
public class AccountTransactions {

	static DecimalFormat df = new DecimalFormat("0.00"); 
	static AccountService as = new AccountService();
	static TransactionService ts = new TransactionService();

	/**
	 * Withdraw
	 */
	public static Account withdraw(Account acc) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		double amount = 0;
		System.out.println("How much would you like to withdraw?");
		System.out.print("$");
		try {
			amount = in.nextDouble();
			amount = round(amount);
			if (amount <= 0) {
				System.out.println("You must enter a positive dollar amount.");
				acc = withdraw(acc);
			}
			else { // Successful withdrawal!
				double newBalance = acc.withraw(amount);
				Transaction trans = new Transaction(acc.getUsrId(), acc.getAccId(), acc.getNickname(), 1, amount, newBalance);
				ts.createTransaction(trans);
				System.out.println("You just withdrew $" + df.format(amount) + " from your account.");
			}			
		} catch(InputMismatchException e) {
			System.out.println("Please enter a valid dollar and/or cents amount");
			acc = withdraw(acc);
		} catch (InsufficientFundsException e) {
			return null;
		}
		return acc;
	}

	
	/**
	 * Deposit
	 */
	public static Account deposit(Account acc) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		double amount = 0;
		System.out.println("How much would you like to deposit?");
		System.out.print("$");
		try {
			amount = in.nextDouble();
			amount = round(amount);
			if (amount <= 0) {
				System.out.println("You must enter a positive dollar amount.");
				acc = deposit(acc);
			}
			else { // Successful deposit!
				double newBalance = acc.deposit(amount);
				Transaction trans = new Transaction(acc.getUsrId(), acc.getAccId(), acc.getNickname(), 2, amount, newBalance);
				ts.createTransaction(trans);
				System.out.println("You just deposited $" + df.format(amount) + " to your account.");
			}			
		} catch(InputMismatchException e) {
			System.out.println("Please enter a valid dollar and/or cents amount");
			acc = deposit(acc);
		}		
		return acc;
	}

	
	/**
	 * Transfer
	 */
	public static void transfer(Account transTo, Account transFrom) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		double amount = 0;
		System.out.println("How much would you like to transfer?");
		System.out.print("$");
		try {
			amount = in.nextDouble();
			amount = round(amount);
			if (amount <= 0) {
				System.out.println("You must enter a positive dollar amount.");
				transfer(transTo, transFrom);
			}
			else { // Successful transfer
				double newBalanceFrom = transFrom.withraw(amount);
				double newBalanceTo = transTo.deposit(amount);
				as.updateAccount(transTo);
				as.updateAccount(transFrom);
				Transaction tTo = new Transaction(transTo.getUsrId(), transTo.getAccId(), transTo.getNickname(), 
						3, amount, newBalanceTo);
				Transaction tFrom = new Transaction(transFrom.getUsrId(), transFrom.getAccId(), transFrom.getNickname(), 
						3, -amount, newBalanceFrom);
				ts.createTransaction(tTo);
				ts.createTransaction(tFrom);
				System.out.println("Transfer successful.");
			}			
		} catch(InputMismatchException e) {
			System.out.println("Please enter a valid dollar and/or cents amount");
			transfer(transTo, transFrom);
		} catch (InsufficientFundsException e) {
			System.out.println("You do not have enough funds in your account for this transaction.");
		}
	}


	/**
	 * View balance of account
	 */
	public static void viewBalance(Account acc) {
		double balance = acc.getBalance();
		System.out.println("Your current available balance is: $" + df.format(balance));
	}

	
	public static Account deActivateAccount(Account acc) {
		acc.setActive(0);	// 0 is inactive
		return acc;
	}
	
	

	/**
	 * Used to round to 2 decimal places
	 */
	public static double round(double value) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}


}
