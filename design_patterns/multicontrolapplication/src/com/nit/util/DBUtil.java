package com.nit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
		}
	}

	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "system", "satya");
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		}
		return connection;
	}

	public static void closeConnection(Connection connection,
			Statement statement) {
		try {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		}
	}
}
