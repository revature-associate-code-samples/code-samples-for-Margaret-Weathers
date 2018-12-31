package com.re.proj1.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	
	// Lazy Singleton
	private static ConnectionFactory cf = null;
	
	private ConnectionFactory() {	}
	
	public static synchronized ConnectionFactory getInstance() {
		if (cf == null) cf = new ConnectionFactory();
		return cf;
	}
	
	public Connection getConnection() {
		Connection conn = null;
		Properties prop = new Properties();
		String path = "C:/Users/Margy/git_repos/RevWork/PROJ1/ERS/src/main/resources/database.properties";
		try {
			prop.load(new FileReader(path));
			// Uses reflection and the .properties file in order to
			//   instantiate the driver class listed in the file
			Class.forName(prop.getProperty("driver"));
			
			/*
			 * The DriverManager provides a basic service for managing a set of JDBC drivers. 
			 * As part of its initialization, the DriverManager class will attempt to load the 
			 * driver class referenced previously
			 */
			conn = DriverManager.getConnection(
					prop.getProperty("url"),
					prop.getProperty("usr"),
					prop.getProperty("pwd"));
			
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
					
		return conn;
	}
}
