package edu.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import edu.exceptions.StudentException;
import edu.pojos.Students;

public class StudentDao {
	private static String sql = "INSERT INTO STUDENTS VALUES(?,?)";
	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void insertStudent(Students students) throws StudentException {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, students.getSno());
			ps.setString(2, students.getSname());
			ps.executeUpdate();
			System.out.println("values set to statement");
		} catch (SQLException e) {
			throw new StudentException(e.getMessage());
		} finally {
			DbUtils.closeQuietly(connection);
		}
	}
}
