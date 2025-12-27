package edu.daos;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import edu.pojos.Students;

public class StudentDao extends AbstractDao {
	private static String sql = "INSERT INTO STUDENTS VALUES(?,?)";

	public void insertStudent(Students students) {
		PreparedStatement ps = null;
		try {
			ps = getConnection().prepareStatement(sql);
			ps.setInt(1, students.getSno());
			ps.setString(2, students.getSname());
			ps.executeUpdate();
			System.out.println("values set to statement");
		} catch (SQLException e) {
		} finally {
			DbUtils.closeQuietly(ps);
		}
	}
}
