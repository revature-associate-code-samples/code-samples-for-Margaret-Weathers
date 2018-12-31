package com.revature.bankingapp.pojos;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

	private int transId;
	private int usrId;
	private int accId;
	private String accNickname;
	private int transType;
	private double amount;
	private double newBal;
	private String date;
	static SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm");
	static DecimalFormat decf = new DecimalFormat("0.00"); 
	
	public Transaction() {}
	
	public Transaction(int usrId, int accId, String accNickname, int transType, double amount, double newBal) {
		super();
		this.usrId = usrId;
		this.accId = accId;
		this.accNickname = accNickname;
		this.transType = transType;
		this.amount = amount;
		this.newBal = newBal;
		setDate(df.format(new Date()));
	}


	public String getAccNickname() {
		return accNickname;
	}

	public void setAccNickname(String accNickname) {
		this.accNickname = accNickname;
	}

	public int getTransId() {
		return transId;
	}

	public void setTransId(int transId) {
		this.transId = transId;
	}

	public int getUsrId() {
		return usrId;
	}

	public void setUsrId(int usrId) {
		this.usrId = usrId;
	}

	public int getTransType() {
		return transType;
	}

	public void setTransType(int transType) {
		this.transType = transType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getNewBal() {
		return newBal;
	}

	public void setNewBal(double newBal) {
		this.newBal = newBal;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}
	
	@Override
	public String toString() {
		if (transType == 1) {
			return date + ": Withdrawal $(-" + decf.format(amount) + ")" + ". Available balance: $" + decf.format(newBal);
			}
		else if (transType == 2) {
			return date + ": Deposit $(+" + decf.format(amount) + ")" + ". Available balance: $" + decf.format(newBal);
		}
		else {
			return date + ": Transfer $(" + decf.format(amount) + ")" + ". Available balance: $" + decf.format(newBal);
		}		
	}
	
}
