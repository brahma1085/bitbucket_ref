package edu.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import edu.exceptions.DaoException;
import edu.exceptions.ResourceHelperException;
import edu.models.Details;
import edu.utils.ResourceHelper;

public class StudentDaoImpl implements StudentDao {
	private String sql = "INSERT INTO KRISHNA VALUES(?,?)";
	private Connection connection;
	PreparedStatement statement;

	public boolean insertDetails(Details details) throws DaoException {
		boolean flag = false;
		try {
			connection = ResourceHelper.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setInt(1, details.getUserid());
			statement.setString(2, details.getPwd());
			flag = statement.execute();
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			throw new DaoException();
		} catch (ResourceHelperException e) {
			System.out.println("ResourceHelperException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
			DbUtils.closeQuietly(connection);
			throw new DaoException();
		} finally {
			DbUtils.closeQuietly(statement);
		}
		return flag;
	}
}
