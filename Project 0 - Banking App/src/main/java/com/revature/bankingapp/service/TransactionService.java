package com.revature.bankingapp.service;

import java.util.List;

import com.revature.bankingapp.DAO.TransactionDao;
import com.revature.bankingapp.pojos.Transaction;

public class TransactionService {

	static TransactionDao tDao = new TransactionDao();
	
	public List<Transaction> getTransactionsbyUser(int usrId, int accId){
		List<Transaction> transactions = tDao.findByUserID(usrId, accId);
		if (transactions.isEmpty()) {
			return null;
		}
		return transactions;
	}
	
	public void createTransaction(Transaction t) {
		tDao.create(t);
	}
}
