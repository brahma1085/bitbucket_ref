package edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import edu.exceptions.DaoException;
import edu.exceptions.ResourceHelperException;
import edu.model.StudentDemo1;
import edu.utils.ResourceHelper;

public class StudentDaoImpl extends AbstractDaoImpl implements StudentDao {

	private String sql = "INSERT INTO STUDENTDEMO1 VALUES(?,?)";

	public boolean insertStudent(StudentDemo1 studentDemo1) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		boolean flag = false;
		try {
			connection = ResourceHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, studentDemo1.getStudentno());
			preparedStatement.setString(2, studentDemo1.getStudentname());
			flag = preparedStatement.executeUpdate() > 0 ? true : false;
		} catch (ResourceHelperException e) {
			throw new DaoException();
		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			DbUtils.closeQuietly(preparedStatement);
		}
		return flag;
	}

}
