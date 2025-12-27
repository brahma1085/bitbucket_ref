package com.eris.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.eris.exceptions.DaoException;
import com.eris.exceptions.ResourceHelperException;
import com.eris.model.Employee;
import com.eris.utils.ResourceHelper;

public class EmployeeDaoImpl implements EmployeeDao {
	private String sql2 = "SELECT CLIENTID FROM CLIENTDETAILS";
	private String sql1 = "SELECT EMPLOYEE_SEQ.NEXTVAL FROM ";
	private String sql3 = "INSERT INTO EMPLOYEEDETAILS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public boolean insertEmployeeDetails(Employee employee) throws DaoException {
		boolean flag = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ResourceHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql3);
			preparedStatement.setInt(1, employee.getEmpid());
			preparedStatement.setString(2, employee.getState());
			preparedStatement.setString(3, employee.getDob());
			preparedStatement.setString(4, employee.getPassword());
			preparedStatement.setString(5, employee.getFirstname());
			preparedStatement.setString(6, employee.getLastname());
			preparedStatement.setString(7, employee.getInitial());
			preparedStatement.setString(8, employee.getDestination());
			preparedStatement.setString(9, employee.getDayphone());
			preparedStatement.setString(10, employee.getEvenphone());
			preparedStatement.setString(11, employee.getExtension());
			preparedStatement.setString(12, employee.getRole());
			preparedStatement.setString(13, employee.getMarstatus());
			preparedStatement.setString(14, employee.getCountry());
			preparedStatement.setString(15, employee.getGender());
			preparedStatement.setString(16, employee.getMobilephone());
			preparedStatement.setString(17, employee.getAddress());
			preparedStatement.setString(18, employee.getEmail());
			preparedStatement.setString(19, employee.getCity());
			preparedStatement.setString(20, employee.getZip());
			preparedStatement.setInt(21, employee.getClientid());
			flag = preparedStatement.executeUpdate() > 0 ? true : false;
		} catch (ResourceHelperException e) {
			System.out.println("ResourceHelperException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
			DbUtils.rollbackAndCloseQuietly(connection);
			throw new DaoException();
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			throw new DaoException();
		}
		return flag;
	}

	public List retrieveClientid() throws DaoException {
		List list = new ArrayList();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ResourceHelper.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql2);
			while (resultSet.next()) {
				for (int i = 0; i <= resultSet.getFetchSize(); i++) {
					i = resultSet.getInt(1);
					list.add(i);
				}
			}
		} catch (ResourceHelperException e) {
			System.out.println("ResourceHelperException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
			throw new DaoException();
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			throw new DaoException();
		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
		return list;
	}

	public long retrieveEmpid() throws DaoException {
		Long long1 = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ResourceHelper.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql1);
			while (resultSet.next()) {
				long1 = resultSet.getLong(1);
			}
		} catch (ResourceHelperException e) {
			System.out.println("ResourceHelperException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
			DbUtils.rollbackAndCloseQuietly(connection);
			throw new DaoException();
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			throw new DaoException();
		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
		}
		return long1;
	}
}
