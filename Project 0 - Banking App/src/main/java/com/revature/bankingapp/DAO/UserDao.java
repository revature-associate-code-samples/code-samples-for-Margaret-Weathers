package com.revature.bankingapp.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.revature.bankingapp.pojos.User;
import com.revature.bankingapp.util.ConnectionFactory;

public class UserDao implements DAO<User, Integer>  {

	@Override
	public List<User> findAll() {
		List<User> users = new ArrayList<User>();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "SELECT * FROM USR";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				User u = new User();
				u.setUsrId(rs.getInt(1));
				u.setUsername(rs.getString(2));
				u.setPw(rs.getString(3));
				u.setFirst(rs.getString(4));
				u.setLast(rs.getString(5));
				users.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	// Unused method
	@Override
	public User findById(Integer id) {
		return null;
	}

	@Override
	public User create(User u) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "INSERT INTO USR (USERNAME, PW, FIRSTNAME, LASTNAME) VALUES(?,?,?,?)";
			String[] keyNames = {"USER_ID"};
			PreparedStatement ps = conn.prepareStatement(sql, keyNames);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPw());
			ps.setString(3, u.getFirst());
			ps.setString(4, u.getLast());
			int numRows = ps.executeUpdate();
			if (numRows > 0) {				// making sure the sql statement returned something
				ResultSet pk = ps.getGeneratedKeys();	// primary keys
				while (pk.next()) {
					u.setUsrId(pk.getInt(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}

	// Unused method...
	@Override
	public User update(User obj) {
		return null;
	}

	public List<String> findAllUsernames(){
		UserDao ud = new UserDao();
		List<User> users = ud.findAll();
		List<String> usernames = new ArrayList<String>();
		Iterator<User> itr = users.iterator();
		while (itr.hasNext()) {
			usernames.add(itr.next().getUsername());
		}	
		return usernames;
	}
	
	public User findByUsername(String username) {
		User u = null;
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "SELECT * FROM USR WHERE USERNAME = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				u = new User();
				u.setUsrId(rs.getInt(1));
				u.setUsername(username);
				u.setPw(rs.getString(3));
				u.setFirst(rs.getString(4));
				u.setLast(rs.getString(5));
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}

}
