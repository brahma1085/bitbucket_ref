package edu.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import edu.exceptions.JDBCException;

public class ConnectionUtil {
	private static Properties properties = new Properties();
	static {
		try {
			properties.load(ConnectionUtil.class.getClassLoader()
					.getResourceAsStream("DBUtils.properties"));
			System.out.println("properties loaded");
		} catch (IOException e) {
			System.out.println("IOException--" + e.getClass().getName() + "--"
					+ e.getMessage() + "properties file is not loaded");
		}
	}

	public static Connection getConnection() throws JDBCException {
		Connection connection = null;
		try {
			Class.forName(properties.getProperty("driverclass"));
			connection = DriverManager.getConnection(properties
					.getProperty("url"), properties.getProperty("username"),
					properties.getProperty("password"));
			System.out.println("connection established");
		} catch (ClassNotFoundException e) {
			throw new JDBCException(e.getMessage());
		} catch (SQLException e) {
			throw new JDBCException(e.getMessage());
		}
		return connection;
	}
}
