package edu.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import edu.exception.ResourceHelperException;

public class ResourceHelper {
	private static Properties properties = new Properties();
	static {
		try {
			properties.load(ResourceHelper.class.getClassLoader()
					.getResourceAsStream("db-utils.properties"));
		} catch (IOException e) {
			System.err.println("IOException--" + e);
			throw new ExceptionInInitializerError(
					"error in loading properties file");
		}
	}

	public static Connection getConnection() throws ResourceHelperException {
		Connection connection = null;
		try {
			Class.forName(properties.getProperty("driverclass"));
			connection = DriverManager.getConnection(properties
					.getProperty("url"), properties.getProperty("username"),
					properties.getProperty("password"));
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException" + e);
			throw new ResourceHelperException(e.getMessage());
		} catch (SQLException e) {
			System.err.println("SQLException" + e);
			throw new ResourceHelperException(e.getMessage());
		}
		return connection;
	}
}
