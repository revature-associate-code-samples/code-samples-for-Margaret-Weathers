package com.revature.bankingapp.pojos;

import com.revature.bankingapp.util.InsufficientFundsException;

public class Account {
	
	private int accId;
	private int usrId;
	private int accType;
	private String nickname;
	private double balance;
	private double interest;
	private int active;

	public Account() {}
	
	public Account(int usrId, int accType, String nickname, double balance) {
		super();
		this.usrId = usrId;
		this.accType = accType;
		this.nickname = nickname;
		this.balance = balance;
		if (accType == 1 || accType ==2) interest = 0;
		else if (accType == 3 && (balance >= 10000 && balance <= 49999)) interest = .00120; 
		else if (accType == 3 && (balance >= 50000 && balance <= 99999)) interest = .00129;
		else if (accType == 3 && (balance >= 100_000 && balance <= 249_000)) interest = .00137;
		else if (accType == 3 && (balance >= 250_000)) interest = .00146;
		setActive(1);
	}

	/**
	 * 
	 * @param withdrawal amount
	 * @return NEW BALANCE
	 * @throws InsufficientFundsException
	 */
	public double withraw(double amount) throws InsufficientFundsException {		
		if (amount > balance) {
			throw new InsufficientFundsException();
		}
		if (balance - amount < 10) {
			System.out.println("Warning: You now have less than $10 in your account");
		}		
		balance -= amount;
		return balance;
	}
	
	public double deposit(double amount) {		
		return balance += amount;
	}
	
	public double checkBalance(Account a) {	
		return balance;
	}
	
	public void transfer(Account a, double amount) throws InsufficientFundsException {
		if (amount > balance) {
			throw new InsufficientFundsException();
		}
		balance -= amount;
		a.deposit(amount);
	}


	// Getters and Setters	
	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

	public int getUsrId() {
		return usrId;
	}

	public void setUsrId(int usrId) {
		this.usrId = usrId;
	}

	public int getAccType() {
		return accType;
	}

	public void setAccType(int accType) {
		this.accType = accType;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}
	
	
	@Override
	public String toString() {
		if (accType == 1) {
			return "Checkings: " + nickname;
		}
		else if (accType == 2) {
			return "Savings: " + nickname;
		}
		else {
			return "Money Market: " + nickname;
		}
	}

	
}
