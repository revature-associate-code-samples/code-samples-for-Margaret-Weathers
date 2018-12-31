package com.revature.bankingapp.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.bankingapp.pojos.Transaction;
import com.revature.bankingapp.util.ConnectionFactory;

import oracle.jdbc.internal.OracleTypes;


public class TransactionDao {

	public List<Transaction> findByUserID(int uid, int aid){
		List<Transaction> transactions = new ArrayList<Transaction>();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "{ CALL GET_TRANS_FROM_USRID(?,?,?) }";
			CallableStatement cs = conn.prepareCall(sql);
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setInt(2, uid);
			cs.setInt(3, aid);
			cs.execute();
			ResultSet rs = (ResultSet) cs.getObject(1);
			while(rs.next()) {
				Transaction t = new Transaction();
				t.setTransId(rs.getInt(1));
				t.setUsrId(rs.getInt(2));
				t.setAccId(rs.getInt(3));
				t.setAccNickname(rs.getString(4));
				t.setTransType(rs.getInt(5));
				t.setAmount(rs.getDouble(6));
				t.setNewBal(rs.getDouble(7));
				t.setDate(rs.getString(8));
				transactions.add(t);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return transactions;
	}

	public Transaction create(Transaction t) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "INSERT INTO TRANSACTION(USER_ID, ACC_ID, ACC_NICKNAME, TRANS_TYPE, AMOUNT, NEWBAL, TRANSDATE) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
			String[] keyNames = {"TRANS_ID"};
			PreparedStatement ps = conn.prepareStatement(sql, keyNames);
			ps.setInt(1, t.getUsrId());
			ps.setInt(2, t.getAccId());
			ps.setString(3, t.getAccNickname());
			ps.setInt(4, t.getTransType());
			ps.setDouble(5, t.getAmount());
			ps.setDouble(6, t.getNewBal());
			ps.setString(7, t.getDate());

			int numRows = ps.executeUpdate();
			if (numRows > 0) {								// Making sure the sql statement returned something
				ResultSet pk = ps.getGeneratedKeys();		// primary keys
				while (pk.next()) {
					t.setTransId(pk.getInt(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}

}















