package edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import edu.exception.ResourceHelperException;
import edu.exception.StudentException;
import edu.model.Student;
import edu.util.ResourceHelper;

public class StudentDao {
	private static String sql = "INSERT INTO STUDENTS VALUES(?,?)";

	public void insertStudent(Student student) throws StudentException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = ResourceHelper.getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setLong(1, student.getStudentNo());
			pStatement.setString(2, student.getStudentName());
			pStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("SQLException" + e);
			throw new StudentException("StudentException" + e);
		} catch (ResourceHelperException e) {
			System.err.println("ResourceHelperException" + e);
			throw new StudentException("StudentException" + e);
		} finally {
			DbUtils.closeQuietly(pStatement);
			DbUtils.closeQuietly(connection);
		}
	}
}
