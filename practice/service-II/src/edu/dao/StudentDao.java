package edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import edu.exceptions.DaoException;
import edu.pojo.Students;

public class StudentDao {
	private static String sql = "INSERT INTO STUDENTS VALUES(?,?)";
	private Connection connection;

	public StudentDao(Connection connection) {
		this.connection = connection;
	}

	public void insertStudent(Students students) throws DaoException {
		PreparedStatement psStatement = null;
		try {
			psStatement = connection.prepareStatement(sql);
			psStatement.setInt(1, students.getStudentNo());
			psStatement.setString(2, students.getStudentName());
			psStatement.executeUpdate();
			System.out.println("details set to object");
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		} finally {
			DbUtils.closeQuietly(connection);
		}
	}
}
