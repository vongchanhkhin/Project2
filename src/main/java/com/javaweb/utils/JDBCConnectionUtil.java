package com.javaweb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionUtil {
	static final String DB_URL = "jdbc:mysql://localhost:3307/estatebasic";
	static final String USER = "root";
	static final String PASSWORD = "Vck031020";
	
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connected database failed...");
		}
		
		return conn;
	}
}
