package edu.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class H5Utils {

	private static final Logger LOG = Logger.getLogger(H5Utils.class);

	private static Properties properties = new Properties();
	static {
		init();
	}

	private static void init() {
		try {
			properties.load(H5Utils.class.getClassLoader().getResourceAsStream("dbprops.properties"));
			LOG.info("database properties are : " + properties);
		} catch (IOException e) {
			LOG.error("error occured while loading the database properties. " + e.getMessage());
		}
	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(properties.getProperty("driverClass"));
		return DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
	}

}