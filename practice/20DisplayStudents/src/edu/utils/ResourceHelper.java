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
	private static String JNDI_NAME = "java:comp/env/brahmaJNDI";
	private static Map<String, DataSource> map = new HashMap<String, DataSource>();

	public static Connection getConnection() throws ResourceHelperException {
		Connection connection = null;
		DataSource dataSource = null;
		Context context = null;
		try {
			dataSource = map.get(ResourceHelper.JNDI_NAME);
			if (dataSource != null)
				return dataSource.getConnection();
			context = new InitialContext();
			dataSource = (DataSource) context.lookup(ResourceHelper.JNDI_NAME);
			map.put(ResourceHelper.JNDI_NAME, dataSource);
			connection = dataSource.getConnection();
		} catch (NamingException e) {
			System.out.println("NamingException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			throw new ResourceHelperException();
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			throw new ResourceHelperException();
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
			throw new ResourceHelperException();
		}
		return connection;
	}
}
