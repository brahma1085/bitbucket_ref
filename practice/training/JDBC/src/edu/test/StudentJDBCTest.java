package edu.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentJDBCTest {

	public static void main(String[] args) {
		String sql = "insert into student values(?,?)";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "system", "satya");
			ps = connection.prepareStatement(sql);
			ps.setInt(1, 1);
			ps.setString(2, "N@IT");
			System.out.println("SUCCESS");
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException" + e.getMessage());
		} catch (SQLException e) {
			System.err.println("SQLException" + e.getMessage());
		} catch (Exception e) {
			System.err.println("Exception" + e.getMessage());
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					System.err.println("SQLException" + e.getMessage());
				}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					System.err.println("SQLException" + e.getMessage());
				}
		}
	}
}
