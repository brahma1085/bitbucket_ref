package edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.util.ConnectionUtil;

public class StudentDao {
	private static String sql = "insert into student values(?,?)";

	public void insertStudent(int studentNo, String StudentName) {
		Connection connection = null;
		PreparedStatement ps = null;
		connection = ConnectionUtil.getConnection();
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, studentNo);
			ps.setString(2, StudentName);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("SQLException" + e.getMessage());
		}finally{
			ConnectionUtil.closeQuietly(connection);
			ConnectionUtil.closeQuietly(ps);
		}
	}
}
