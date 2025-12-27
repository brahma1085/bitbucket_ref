package com.nit.struts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.exceptions.DataProcessingException;

import edu.domainobj.Account;

public class AccountModel {
	public Account getAccount(int accno) throws DataProcessingException {
		Account account = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "system", "satya");
			statement = connection
					.prepareStatement("SELECT * FROM ACCOUNT WHERE ACCNO=?");
			statement.setInt(1, accno);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				account = new Account();
				account.setAccno(accno);
				account.setName(resultSet.getString(2));
				account.setBalance(resultSet.getFloat(3));
			}
		} catch (SQLException e) {
			/*
			 * System.out.println("SQLException==>" + e.getClass().getName() +
			 * "==>" + e.getMessage());
			 */
			throw new DataProcessingException(e.getMessage());
		} catch (ClassNotFoundException e) {
			/*
			 * System.out.println("ClassNotFoundException==>" +
			 * e.getClass().getName() + "==>" + e.getMessage());
			 */
			throw new DataProcessingException(e.getMessage());
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.out.println("SQLException==>" + e.getClass().getName()
						+ "==>" + e.getMessage());
			}
		}
		return account;
	}
}
