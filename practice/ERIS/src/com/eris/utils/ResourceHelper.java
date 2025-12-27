package com.eris.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.eris.exceptions.ResourceHelperException;

public class ResourceHelper {
	private static Map<String, DataSource> cache = new HashMap<String, DataSource>();
	private static String ERIS_JNDI = "java:comp/env/brahmaJNDI";

	public static Connection getConnection() throws ResourceHelperException {
		Context context = null;
		DataSource dataSource = null;
		Connection connection = null;
		try {
			dataSource = (DataSource) cache.get(ResourceHelper.ERIS_JNDI);
			if (dataSource != null) {
				return dataSource.getConnection();
			}
			context = new InitialContext();
			dataSource = (DataSource) context.lookup(ResourceHelper.ERIS_JNDI);
			cache.put(ResourceHelper.ERIS_JNDI, dataSource);
			connection = dataSource.getConnection();
		} catch (NamingException e) {
			throw new ResourceHelperException(e.getMessage());
		} catch (SQLException e) {
			throw new ResourceHelperException(e.getMessage());
		}
		return connection;
	}
}
