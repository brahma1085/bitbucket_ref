package edu.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.exceptions.ResourceHelperException;

public class ResourceHelper {
	private static Map<String, DataSource> map = new HashMap<String, DataSource>();
	private static String ANAND_JNDI = "java:comp/env/brahmaJNDI";

	public static Connection getConnection() throws ResourceHelperException {
		Connection connection = null;
		Context context = null;
		DataSource dataSource = null;
		try {
			dataSource = (DataSource) map.get(ResourceHelper.ANAND_JNDI);
			if (dataSource != null)
				return dataSource.getConnection();
			context = new InitialContext();
			dataSource = (DataSource) context.lookup(ANAND_JNDI);
			map.put(ResourceHelper.ANAND_JNDI, dataSource);
			connection = dataSource.getConnection();
		} catch (NamingException e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
			throw new ResourceHelperException();
		} catch (SQLException e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
			throw new ResourceHelperException();
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
			throw new ResourceHelperException();
		}
		return connection;
	}
}
