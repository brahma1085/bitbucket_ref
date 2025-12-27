package edu.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
	private static Properties properties = new Properties();
	static {
		try {
			properties.load(ConnectionUtil.class.getClassLoader()
					.getResourceAsStream("DBUtil.properties"));
			System.out.println("loading prop successful");
		} catch (IOException e) {
			System.out.println("IOException" + e.getMessage());
		}
	}

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(properties.getProperty("driverclass"));
			System.out.println("driver class loaded");
			connection = DriverManager.getConnection(properties
					.getProperty("url"), properties.getProperty("username"),
					properties.getProperty("password"));
			System.out.println("connection successful");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException" + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQLException" + e.getMessage());
		}
		return connection;
	}
}
