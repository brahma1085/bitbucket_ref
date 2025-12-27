package edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import edu.exceptions.DaoException;
import edu.pojo.Students;

public class StudentDao {
	public void insertStudent(Students students, Connection connection)
			throws DaoException {
		PreparedStatement psStatement = null;
		try {
			String sql = "INSERT INTO STUDENTS VALUES(?,?)";
			psStatement = connection.prepareStatement(sql);
			psStatement.setInt(1, students.getSno());
			psStatement.setString(2, students.getSname());
			psStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		} finally {
			DbUtils.closeQuietly(psStatement);
		}
	}
}
