package com.eris.daos;
/*
* COPYRIGHT NOTICE 
* Copyright@2010 by Varma. All rights reserved.
*/

import java.sql.Connection;

/*
*
* @author Varma 
*
*/
public class AbstractDaoImpl implements AbstractDao {
	private Connection connection;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
