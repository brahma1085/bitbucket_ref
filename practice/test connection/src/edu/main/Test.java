package edu.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;

import edu.util.ConnectionUtil;

public class Test {

	public static void main(String[] args) throws NamingException, SQLException {
		Connection connection = ConnectionUtil.getConnection();
		System.out.println("conection established");
		Statement statement = connection.createStatement();
		String sql = "INSERT INTO STUDENT VALUES(5, 'N@IT')";
		statement.execute(sql);
		System.out.println("statement executed");
	}
}
