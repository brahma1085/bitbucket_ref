package com.nit.struts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;

public class AccountService {
	Account account = new Account();

	public Account getAccountDetails(int accno) throws DBException,
			AccountNotFoundException, SQLException {
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
			System.out.println("continue....");

			SQLWarning warning = statement.getWarnings();
			if (warning != null) {
			while (warning != null) {
			System.out.println("Message: " + warning.getMessage());
			System.out.println("SQLState: " + warning.getSQLState());
			System.out.print("Vendor error code: ");
			System.out.println(warning.getErrorCode());
			warning = warning.getNextWarning();
			}
			}

			if (resultSet != null) {
				while (resultSet.next()) {
					System.out.println("in the while condition");
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
			throw new AccountNotFoundException();
		} finally {
			statement.close();
			resultSet.close();
			connection.close();
		}
		return account;
	}

	public static void main(String[] args) throws DBException,
			AccountNotFoundException, SQLException {

		AccountService as = new AccountService();
		System.out.println("no account found");
		as.getAccountDetails(12345);

	}

}