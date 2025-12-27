package edu.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
	private static Properties properties = new Properties();
	static {
		try {
			properties.load(DBUtil.class.getClassLoader().getResourceAsStream(
					"DBUtil.properties"));
			System.out.println("properties file loaded");
		} catch (IOException e) {
			System.out.println("IOException--" + e.getClass().getName() + "--"
					+ e.getMessage());
		}
	}

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(properties.getProperty("driverclass"));
			System.out.println("driverclass loaded");
			connection = DriverManager.getConnection(properties
					.getProperty("url"), properties.getProperty("user"),
					properties.getProperty("password"));
			System.out.println("connected to DB");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException--"
					+ e.getClass().getName() + "--" + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQLException--" + e.getClass().getName() + "--"
					+ e.getMessage());
		}
		return connection;
	}
}
