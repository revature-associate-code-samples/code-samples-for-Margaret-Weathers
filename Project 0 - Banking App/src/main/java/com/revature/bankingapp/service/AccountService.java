package com.revature.bankingapp.service;

import java.util.List;

import com.revature.bankingapp.DAO.AccountDao;
import com.revature.bankingapp.DAO.DAO;
import com.revature.bankingapp.pojos.Account;

public class AccountService {
	static DAO<Account, Integer> accountDao = new AccountDao();

	public List<Account> getAllAccounts(){
		List<Account> accounts = accountDao.findAll();
		if (accounts.size() == 0) return null;
		return accounts;
	}
	
	public List<Account> getAccountsByUserID(int id){
		List<Account> accounts = accountDao.findByUserID(id);
		if (accounts.size() == 0) return null;
		return accounts;
	}
	
	public Account createAccount(Account acc) {
		accountDao.create(acc);
		return acc;
	}
	
	public Account updateAccount(Account acc) {
		accountDao.update(acc);
		return acc;
	}
}
