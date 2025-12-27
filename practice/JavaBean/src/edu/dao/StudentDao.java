package edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.pojo.Stud;
import edu.util.DBUtil;

public class StudentDao {
	public void insertStudent(Stud st) {
		Connection connection = DBUtil.getConnection();
		String sql = "INSERT INTO STUD VALUES(?,?,?,?,?)";
		try {
			PreparedStatement psStatement = connection.prepareStatement(sql);
			psStatement.setString(1, st.getStdNo());
			psStatement.setString(2, st.getStdName());
			psStatement.setString(3, st.getStdAge());
			psStatement.setString(4, st.getStdMarks());
			psStatement.setString(5, st.getStdQual());
			psStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("SQLException--" + e.getClass().getName() + "--"
					+ e.getMessage());
		}
	}
}
