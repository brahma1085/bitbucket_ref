package edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import edu.exception.EmployeeException;
import edu.exception.ResourceHelperException;
import edu.model.Employee;
import edu.util.ResourceHelper;

public class EmployeeDao {
	private static String sql = "INSERT INTO EMPLOYEES VALUES(?,?)";

	public void insertStudent(Employee employee) throws EmployeeException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = ResourceHelper.getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setLong(1, employee.getEmployeeNo());
			pStatement.setString(2, employee.getEmployeeName());
			pStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("SQLException" + e);
			throw new EmployeeException("EmployeeException" + e);
		} catch (ResourceHelperException e) {
			System.err.println("ResourceHelperException" + e);
			throw new EmployeeException("EmployeeException" + e);
		} finally {
			DbUtils.closeQuietly(pStatement);
			DbUtils.closeQuietly(connection);
		}
	}
}
