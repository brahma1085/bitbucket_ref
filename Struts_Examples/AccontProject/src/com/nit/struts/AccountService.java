package com.nit.struts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountService {
	Account account = new Account();

	public Account getAccountDetails(int accno) throws DBException {
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
			if (resultSet != null) {
				while (resultSet.next()) {
					account = new Account();
					account.setAccno(resultSet.getInt(1));
					account.setAccname(resultSet.getString(2));
					account.setBal(resultSet.getDouble(3));
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
			throw new DBException();
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			throw new DBException();
		}
		return account;
	}
}