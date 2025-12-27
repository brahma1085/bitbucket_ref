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
	private static String JNDI_NAME = "java:comp/env/brahmaJNDI";

	public static Connection getConnection() throws ResourceHelperException {
		Connection connection = null;
		Context context = null;
		DataSource source = null;
		try {
			source = (DataSource) map.get(ResourceHelper.JNDI_NAME);
			if (source != null)
				return source.getConnection();
			context = new InitialContext();
			source = (DataSource) context.lookup(ResourceHelper.JNDI_NAME);
			map.put(ResourceHelper.JNDI_NAME, source);
			connection = source.getConnection();
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			throw new ResourceHelperException();
		} catch (NamingException e) {
			System.out.println("NamingException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			throw new ResourceHelperException();
		}
		return connection;
	}
}
