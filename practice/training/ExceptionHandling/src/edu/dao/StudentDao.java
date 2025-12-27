package edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.exceptions.StudentException;
import edu.util.ConnectionUtil;

public class StudentDao {
	private static String sql = "INSERT INTO STUDENT VALUES(?,?)";

	public void insertStudent(int studentNo, String studentName)
			throws StudentException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		connection = ConnectionUtil.getConnection();
		try {
			pStatement = connection.prepareStatement(sql);
			pStatement.setInt(1, studentNo);
			pStatement.setString(2, studentName);
			pStatement.executeUpdate();
			System.out.println("insertion succesful");
		} catch (SQLException e) {
			throw new StudentException();
		} finally {
		}
	}
}
