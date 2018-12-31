package com.revature.bankingapp.DAO;

import java.io.Serializable;
import java.util.List;

public interface DAO<T, I extends Serializable> {

	List<T> findAll();
	T findById(I id);
	T create(T obj);
	T update(T obj);

	/*
	 * Adding default methods so we can instantiate 
	 * DAO concrete classes with a reference
	 * to the DAO interface and override these methods.
	 */
	default boolean isUnique(T obj) {
		return true;
	}
	
	// default User methods
	default List<String> findAllUsernames(){
		return null;
	}
	default T findByUsername(String username) {
		return null;
	}
	
	// default Account methods
	default List<T> findByUserID(int id){
		return null;
	}
}
