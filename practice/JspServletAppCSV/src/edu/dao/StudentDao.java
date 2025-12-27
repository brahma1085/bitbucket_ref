package edu.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.model.Student;

public class StudentDao extends AbstractDao {

	public void insertStudent(Student student) {
		PreparedStatement ps = null;
		String sql = "INSERT INTO STUDENTS VALUES(?,?)";
		try {
			ps = getConnection().prepareStatement(sql);
			ps.setInt(1, student.getSno());
			ps.setString(2, student.getSname());
			ps.executeUpdate();
			System.out.println("details set to statement");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
