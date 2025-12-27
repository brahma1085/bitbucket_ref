package edu.utils;

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
			System.out.println("IOException--" + e.getClass().getName() + "--"
					+ e.getMessage());
		}
	}

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(properties.getProperty("driverclass"));
			connection = DriverManager.getConnection(properties
					.getProperty("url"), properties.getProperty("username"),
					properties.getProperty("password"));
			System.out.println("connected to DB");
		} catch (ClassNotFoundException e) {
			System.out.println("driver class not found");
		} catch (SQLException e) {
			System.out.println("not connected to DB");
		}
		return connection;
	}
}
