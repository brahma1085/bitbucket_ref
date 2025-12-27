package com.personal.epf.dao;

import java.sql.Connection;

/**
 * @author brahma
 * 
 *         The Interface AbstractDao. It a proxy connection handler for a
 *         transaction
 */
public interface AbstractDao {

	/**
	 * Sets the connection.
	 * 
	 * @param connection
	 *            the new connection
	 */
	public void setConnection(Connection connection);

	/**
	 * Gets the connection.
	 * 
	 * @return the connection
	 */
	public Connection getConnection();

}
