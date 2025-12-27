package edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.util.ConnectionUtil;

public class EmployeeDao {
	private static String sql = "insert into employee values(?,?)";

	public void insertEmployee(int employeeNo, String employeeName) {
		Connection connection = null;
		PreparedStatement ps = null;
		connection = ConnectionUtil.getConnection();
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, employeeNo);
			ps.setString(2, employeeName);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("SQLException" + e.getMessage());
		} finally {
			ConnectionUtil.closeQuietly(connection);
			ConnectionUtil.closeQuietly(ps);
		}
	}
}