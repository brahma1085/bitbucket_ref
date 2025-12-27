package edu.daos;

import java.sql.Connection;

public interface AbstractDao {
	Connection getConnection();

	void setConnection(Connection connection);
}
