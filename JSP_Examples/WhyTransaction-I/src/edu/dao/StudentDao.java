package edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.pojo.Student;
import edu.pojo.StudentXtra;
import edu.util.ConnectionUtil;

public class StudentDao {
	private static String ssql = "INSERT INTO STUDENT VALUES(?,?)";
	private static String sxsql = "INSERT INTO STUDENTXTRA VALUES(?,?)";

	public void insert(Student student) {

		insertStudent(student);
		System.out.println("break point");
		insertStudentXtra(student.getStudentXtra());

	}

	public void insertStudent(Student student) {
		Connection connection = ConnectionUtil.getConnection();
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
			ConnectionUtil.closeQuietly(connection, psStatement);
		}
	}

	public void insertStudentXtra(StudentXtra studentXtra) {
		Connection connection = ConnectionUtil.getConnection();
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
			ConnectionUtil.closeQuietly(connection, psStatement);
		}
	}
}
