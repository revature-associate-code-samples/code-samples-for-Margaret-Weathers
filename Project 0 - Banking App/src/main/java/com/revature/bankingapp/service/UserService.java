package com.revature.bankingapp.service;

import java.util.List;

import com.revature.bankingapp.DAO.DAO;
import com.revature.bankingapp.DAO.UserDao;
import com.revature.bankingapp.pojos.User;

public class UserService {
	
	static DAO<User, Integer> userDao = new UserDao();
	
	public List<User> getAllUsers(){
		List<User> users = userDao.findAll();
		if (users.size() == 0) return null;
		return users;
	}
	
	public List<String> getAllUsernames(){
		List<String> usernames = userDao.findAllUsernames();
		return usernames;
	}
	
	public User getUserByUsername(String username) {
		User u = userDao.findByUsername(username);
		return u;
	}
	
	public void createUser(User u) {
		userDao.create(u);
	}
	
}
