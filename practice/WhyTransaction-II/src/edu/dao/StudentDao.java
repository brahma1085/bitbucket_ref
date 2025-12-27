package edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import edu.exceptions.StudentException;
import edu.pojo.Student;
import edu.pojo.StudentXtra;
import edu.util.ConnectionUtil;

public class StudentDao {
	private static String ssql = "INSERT INTO STUDENT VALUES(?,?)";
	private static String sxsql = "INSERT INTO STUDENTXTRA VALUES(?,?)";
	Connection connection = null;

	public void insert(Student student) {
		try {
			connection = ConnectionUtil.getConnection();
			connection.setAutoCommit(false);
			insertStudent(student);
			System.out.println("break point");
			insertStudentXtra(student.getStudentXtra());
			connection.commit();
		} catch (StudentException e) {
			try {
				DbUtils.rollback(connection);
			} catch (SQLException e1) {
				DbUtils.closeQuietly(connection);
			}
		} catch (SQLException e) {
			try {
				DbUtils.rollback(connection);
			} catch (SQLException e1) {
				DbUtils.closeQuietly(connection);
			}
		}
	}

	public void insertStudent(Student student) throws StudentException {

		PreparedStatement psStatement = null;

		try {
			psStatement = connection.prepareStatement(ssql);
			psStatement.setInt(1, student.getStudentNo());
			psStatement.setString(2, student.getStudentName());
			psStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException--" + e.getClass().getName() + "--"
					+ e.getMessage());
		} finally {
			DbUtils.closeQuietly(psStatement);
		}
	}

	public void insertStudentXtra(StudentXtra studentXtra)
			throws StudentException {
		PreparedStatement psStatement = null;
		try {
			psStatement = connection.prepareStatement(sxsql);
			psStatement.setInt(1, studentXtra.getStudentNo());
			psStatement.setInt(2, studentXtra.getStudentAge());
			psStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException--" + e.getClass().getName() + "--"
					+ e.getMessage());
		} finally {
			DbUtils.closeQuietly(psStatement);
		}
	}
}
