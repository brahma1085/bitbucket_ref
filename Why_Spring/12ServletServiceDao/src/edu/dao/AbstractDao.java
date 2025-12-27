package edu.dao;

import java.sql.Connection;

public interface AbstractDao {
	void setConnection(Connection connection);

	Connection getConnection();
}
