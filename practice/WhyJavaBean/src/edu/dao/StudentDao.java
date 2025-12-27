package edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.util.DBUtil;

public class StudentDao {
	private static Connection connection = DBUtil.getConnection();
	private static PreparedStatement psStatement = null;

	public void createStud() {
		String sql = "CREATE TABLE STUD(SNO NUMBER(5),SNAME VARCHAR(20),SAGE NUMBER(4),SMARKS NUMBER(10),SQUAL VARCHAR(20))";
		try {
			psStatement = connection.prepareStatement(sql);
			psStatement.executeUpdate();
			System.out.println("table created");
		} catch (SQLException e) {
			System.err.println("SQLException--" + e.getClass().getName() + "--"
					+ e.getMessage());
		}
	}

	public void insertStud(String stdNo, String stdName, String stdAge,
			String stdMarks, String stdQual) {
		String sql = "INSERT INTO STUD VALUES(?,?,?,?,?)";
		try {
			psStatement = connection.prepareStatement(sql);
			psStatement.setString(1, stdNo);
			psStatement.setString(2, stdName);
			psStatement.setString(3, stdAge);
			psStatement.setString(4, stdMarks);
			psStatement.setString(5, stdQual);
			psStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("SQLException--" + e.getClass().getName() + "--"
					+ e.getMessage());
		}
	}
}
