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
					.getResourceAsStream("db-utils.properties"));
			System.out.println("properties loaded");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(properties.getProperty("driverclass"));
			connection = DriverManager.getConnection(properties
					.getProperty("url"), properties.getProperty("username"),
					properties.getProperty("password"));
			System.out.println("connection established");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}