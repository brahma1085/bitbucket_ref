package edu.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import edu.exceptions.StudentException;

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

	public static Connection getConnection() throws StudentException {
		Connection connection = null;
		try {
			Class.forName(properties.getProperty("driverclass"));
			System.out.println("driver class loaded");
			connection = DriverManager.getConnection(properties
					.getProperty("url"), properties.getProperty("username"),
					properties.getProperty("password"));
			System.out.println("connection successful");
		} catch (ClassNotFoundException e) {
			throw new StudentException(e.getMessage());
		} catch (SQLException e) {
			throw new StudentException(e.getMessage());
		}
		return connection;
	}

	public static void closeQuietly(Connection connection,
			PreparedStatement psPreparedStatement) throws StudentException {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new StudentException(e.getMessage());
			}
		}
		if (psPreparedStatement != null) {
			try {
				psPreparedStatement.close();
			} catch (SQLException e) {
				throw new StudentException(e.getMessage());
			}
		}
	}
}
