package com.revature.bankingapp.util;

/**
 * 
 * Custom exception for when user withdraws more than their available balance.
 *
 */
public class InsufficientFundsException extends Exception {

	public InsufficientFundsException() {
		super("You have insufficient funds for this transaction.");
	}
	public InsufficientFundsException(String message) {
		super(message);
	}
}
