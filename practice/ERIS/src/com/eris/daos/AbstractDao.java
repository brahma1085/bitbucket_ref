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
public interface AbstractDao {
	public void setConnection(Connection connection);
	public Connection getConnection();
}
