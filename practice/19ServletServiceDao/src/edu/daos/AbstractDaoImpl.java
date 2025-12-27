package edu.daos;

import java.sql.Connection;

public class AbstractDaoImpl implements AbstractDao {
	private Connection connection;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
