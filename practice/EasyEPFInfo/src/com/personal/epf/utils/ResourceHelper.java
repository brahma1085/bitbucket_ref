package com.personal.epf.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.personal.epf.exceptions.ErrorMessages;
import com.personal.epf.exceptions.ResourceHelperException;

/**
 * @author brahma
 * 
 *         The Class ResourceHelper. It provides JNDI connection
 */
public class ResourceHelper {

	/** The cache. */
	private static Map<String, DataSource> cache = new HashMap<String, DataSource>();

	/**
	 * Gets the connection.
	 * 
	 * @return the connection
	 * @throws ResourceHelperException
	 *             the resource helper exception
	 */
	public static Connection getConnection() throws ResourceHelperException {
		Context context = null;
		DataSource dataSource = null;
		Connection connection = null;
		try {
			dataSource = (DataSource) cache.get(Constants.EPF_JNDI);
			if (dataSource != null) {
				return dataSource.getConnection();
			}
			context = new InitialContext();
			dataSource = (DataSource) context.lookup(Constants.EPF_JNDI);
			cache.put(Constants.EPF_JNDI, dataSource);
			connection = dataSource.getConnection();
		} catch (NamingException e) {
			throw new ResourceHelperException(ErrorMessages.INTERNAL_ERROR);
		} catch (SQLException e) {
			throw new ResourceHelperException(
					ErrorMessages.UNABLE_TO_GET_CONNECTION);
		}
		return connection;
	}
}
