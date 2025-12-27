package edu.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionUtil {
	private static Properties properties = new Properties();
	static {
		try {
			properties.load(ConnectionUtil.class.getClassLoader()
					.getResourceAsStream("db-config.properties"));
		} catch (IOException e) {
			System.err.println("IOException" + e.getMessage());
		}
	}

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(properties.getProperty("driverclass"));
			connection = DriverManager.getConnection(properties
					.getProperty("url"), properties.getProperty("username"),
					properties.getProperty("password"));
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException" + e.getMessage());
		} catch (SQLException e) {
			System.err.println("SQLException" + e.getMessage());
		}
		return connection;
	}

	public static void closeQuietly(Connection con) {
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				System.err.println("SQLException" + e.getMessage());
			}
	}

	public static void closeQuietly(Statement st) {
		if (st != null)
			try {
				st.close();
			} catch (SQLException e) {
				System.err.println("SQLException" + e.getMessage());
			}
	}

	public static void closeQuietly(ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				System.err.println("SQLException" + e.getMessage());
			}
	}
}
