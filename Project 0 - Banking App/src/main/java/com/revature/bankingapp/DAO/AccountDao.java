package com.revature.bankingapp.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.bankingapp.pojos.Account;
import com.revature.bankingapp.util.ConnectionFactory;

public class AccountDao implements DAO<Account, Integer> {

	@Override
	public List<Account> findAll() {
		List<Account> accounts = new ArrayList<Account>();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "SELECT * FROM ACCOUNT";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);			
			while(rs.next()) {
				Account temp = new Account();
				temp.setAccId(rs.getInt(1));
				temp.setUsrId(rs.getInt(2));
				temp.setAccType(rs.getInt(3));
				temp.setNickname(rs.getString(4));
				temp.setBalance(rs.getDouble(5));
				temp.setInterest(rs.getDouble(6));
				temp.setActive(rs.getInt(7));
				accounts.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}

	public List<Account> findByUserID(int id){
		List<Account> accounts = new ArrayList<Account>();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "SELECT * FROM ACCOUNT WHERE USER_ID = ? ORDER BY ACC_ID";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Account temp = new Account();
				temp.setAccId(rs.getInt(1));
				temp.setUsrId(rs.getInt(2));
				temp.setAccType(rs.getInt(3));
				temp.setNickname(rs.getString(4));
				temp.setBalance(rs.getDouble(5));
				temp.setInterest(rs.getDouble(6));
				temp.setActive(rs.getInt(7));
				if (temp.getActive() == 1) {				// ONLY ADDING ACTIVE ACCOUNTS. Change later for
					accounts.add(temp);					   	//	more additional functionality (ability to reactivate)
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return accounts;
	}

	@Override
	public Account findById(Integer id) {

		return null;
	}

	@Override
	public Account create(Account acc) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "INSERT INTO ACCOUNT (USER_ID, ACC_TYPE, NICKNAME, BALANCE, INTEREST, ACTIVE) VALUES(?, ?, ?, ?, ?, ?)";
			String[] keyNames = {"ACC_ID"};
			PreparedStatement ps = conn.prepareStatement(sql, keyNames);
			ps.setInt(1, acc.getUsrId());
			ps.setInt(2, acc.getAccType());
			ps.setString(3, acc.getNickname());
			ps.setDouble(4, acc.getBalance());
			ps.setDouble(5, acc.getInterest());
			ps.setInt(6, acc.getActive());
			
			int numRows = ps.executeUpdate();
			if (numRows > 0) {								// Making sure the sql statement returned something
				ResultSet pk = ps.getGeneratedKeys();		// primary keys
				while (pk.next()) {
					acc.setAccId(pk.getInt(1));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return acc;
	}

	@Override
	public Account update(Account acc) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "UPDATE ACCOUNT SET BALANCE = ?, INTEREST = ?, ACTIVE = ? WHERE ACC_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, acc.getBalance());
			ps.setDouble(2, acc.getInterest());
			ps.setInt(3, acc.getActive());
			ps.setInt(4, acc.getAccId());
			ps.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return acc;
	}


}
