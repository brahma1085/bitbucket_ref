package edu.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionUtil {
	private static DataSource dsDataSource;
	static {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("./config/jndi.properties"));
			InitialContext initialContext = new InitialContext();
			dsDataSource = (DataSource) initialContext.lookup("base2");
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException" + e.getMessage());
		} catch (NamingException e) {
			System.out.println("NamingException" + e.getMessage());
		}
	}

	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = dsDataSource.getConnection();
		} catch (SQLException e) {
			System.out.println("SQLException" + e.getMessage());
		}
		return connection;
	}
}
