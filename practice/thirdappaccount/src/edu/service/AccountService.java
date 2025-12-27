package edu.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.exceptions.AccountNotFoundException;
import com.exceptions.DataProcessingException;

import edu.domainobj.Account;

public class AccountService {
	public Account getAccount(int accno) throws DataProcessingException,
			AccountNotFoundException {
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
				account.setName(resultSet.getString("name"));
				account.setBalance(resultSet.getFloat("balance"));
			} else
				throw new AccountNotFoundException();
		} catch (ClassNotFoundException e) {
			throw new DataProcessingException();
		} catch (SQLException e) {
			throw new DataProcessingException();
		}
		return account;
	}
}
