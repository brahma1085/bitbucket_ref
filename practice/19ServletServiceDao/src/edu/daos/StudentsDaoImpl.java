package edu.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import edu.exceptions.ResourceHelperException;
import edu.exceptions.StudentsExceptions;
import edu.models.Students;
import edu.utils.ResourceHelper;

public class StudentsDaoImpl extends AbstractDaoImpl implements StudentsDao {
	private String sql = "INSERT INTO STUDENTS VALUES(?,?)";

	public boolean insertStudents(Students students) throws StudentsExceptions {
		boolean flag = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ResourceHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, students.getStudentNo());
			preparedStatement.setString(2, students.getStudentName());
			flag = preparedStatement.executeUpdate() > 0 ? true : false;
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			throw new StudentsExceptions();
		} catch (ResourceHelperException e) {
			System.out.println("ResourceHelperException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
			DbUtils.closeQuietly(connection);
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
			throw new StudentsExceptions();
		} finally {
			DbUtils.closeQuietly(preparedStatement);
		}
		return flag;
	}

}
