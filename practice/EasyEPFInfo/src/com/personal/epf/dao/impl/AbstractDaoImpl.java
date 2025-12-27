package com.personal.epf.dao.impl;

import java.sql.Connection;

import com.personal.epf.dao.AbstractDao;

/**
 * @author brahma
 * 
 *         The Class AbstractDaoImpl. It handles the connection related
 *         operations to data base
 */
public class AbstractDaoImpl implements AbstractDao {

	/** The connection. */
	private Connection connection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.personal.epf.dao.AbstractDao#getConnection()
	 */
	public Connection getConnection() {
		return connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.personal.epf.dao.AbstractDao#setConnection(java.sql.Connection)
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
