package com.personal.epf.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import com.personal.epf.dao.EmployeeDetailsDao;
import com.personal.epf.exceptions.DaoException;
import com.personal.epf.exceptions.ErrorMessages;
import com.personal.epf.exceptions.ResourceHelperException;
import com.personal.epf.model.Employee;
import com.personal.epf.utils.Constants;
import com.personal.epf.utils.ResourceHelper;

/**
 * @author brahma
 * 
 *         The Class EmployeeDetailsDaoImpl. It handles all the data base
 *         operations like employee id generation, registration, pf calculation
 *         and fetching the employee details based on the employe id.
 */
public class EmployeeDetailsDaoImpl implements EmployeeDetailsDao {

	/** The Constant logger. */
	private static final Logger logger = Logger
			.getLogger(EmployeeDetailsDaoImpl.class);

	/**
	 * Generates new employee id each time the user registered with the
	 * application.
	 * 
	 * @return the employee id
	 * @throws Exception
	 *             the exception
	 */
	private int getEmployeeId() throws Exception {
		int empId = Constants.DEFAULT_ID;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ResourceHelper.getConnection();
			preparedStatement = connection.prepareStatement(Constants.EMP_ID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet == null) {
				return Constants.DEFAULT_ID + 1;
			}
			while (resultSet.next()) {
				empId = resultSet.getInt(Constants.DB_COLUMN);
			}
		} catch (Exception e) {
			throw e;
		}
		return empId + 6;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.personal.epf.dao.EmployeeDetailsDao#retrieveEmployeeDetails(int)
	 */
	@Override
	public Employee retrieveEmployeeDetails(int empid) throws DaoException {
		Employee employee = new Employee();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ResourceHelper.getConnection();
			preparedStatement = connection
					.prepareStatement(Constants.SELECT_EMP);
			preparedStatement.setInt(1, empid);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				employee.setEmpid(empid);
				employee.setAddress(resultSet.getString(Constants.ADDRESS));
				employee.setCity(resultSet.getString(Constants.CITY));
				employee.setCountry(resultSet.getString(Constants.COUNTRY));
				employee.setDayphone(resultSet.getString(Constants.DAYPHONE));
				employee.setDesignation(resultSet
						.getString(Constants.DESIGNATION));
				employee.setDob(resultSet.getString(Constants.DOB));
				employee.setEmail(resultSet.getString(Constants.EMAIL));
				employee.setFirstname(resultSet.getString(Constants.FIRSTNAME));
				employee.setGender(resultSet.getString(Constants.GENDER));
				employee.setLastname(resultSet.getString(Constants.LASTNAME));
				employee.setMaritalstatus(resultSet
						.getString(Constants.MARITALSTATUS));
				employee.setMiddlename(resultSet
						.getString(Constants.MIDDLENAME));
				employee.setMobile(resultSet.getString(Constants.MOBILE));
				employee.setPfvalue(resultSet.getString(Constants.PFVALUE));
				employee.setPwd(resultSet.getString(Constants.PWD));
				employee.setSalary(resultSet.getString(Constants.SALARY));
				employee.setState(resultSet.getString(Constants.STATE));
				employee.setZip(resultSet.getInt(Constants.ZIP));
			}
		} catch (ResourceHelperException e) {
			DbUtils.rollbackAndCloseQuietly(connection);
			logger.error(Constants.RETRIEVE_ERROR_MSG);
			throw new DaoException(e.getMessage());
		} catch (SQLException e) {
			logger.error(Constants.RETRIEVE_ERROR_MSG);
			throw new DaoException(ErrorMessages.INTERNAL_ERROR);
		} catch (Exception e) {
			logger.error(Constants.RETRIEVE_ERROR_MSG);
			throw new DaoException(ErrorMessages.INTERNAL_ERROR);
		}
		return employee;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.personal.epf.dao.EmployeeDetailsDao#insertEmployeeDetails(com.personal
	 * .epf.model.Employee)
	 */
	@Override
	public int insertEmployeeDetails(Employee employee) throws DaoException {
		boolean flag = Boolean.FALSE;
		int empId = Constants.DEFAULT_ID;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ResourceHelper.getConnection();
			preparedStatement = connection
					.prepareStatement(Constants.INSERT_QUERY);
			empId = getEmployeeId();
			preparedStatement.setInt(1, empId);
			preparedStatement.setString(2, employee.getFirstname());
			preparedStatement.setString(3, employee.getMiddlename());
			preparedStatement.setString(4, employee.getLastname());
			preparedStatement.setString(5, employee.getPwd());
			preparedStatement.setString(6, employee.getState());
			preparedStatement.setString(7, employee.getDob());
			preparedStatement.setString(8, employee.getDesignation());
			preparedStatement.setString(9, employee.getDayphone());
			preparedStatement.setString(10, employee.getMobile());
			preparedStatement.setString(11, employee.getPfvalue());
			preparedStatement.setString(12, employee.getMaritalstatus());
			preparedStatement.setString(13, employee.getCountry());
			preparedStatement.setString(14, employee.getGender());
			preparedStatement.setString(15, employee.getAddress());
			preparedStatement.setString(16, employee.getEmail());
			preparedStatement.setString(17, employee.getCity());
			preparedStatement.setInt(18, employee.getZip());
			preparedStatement.setString(19, employee.getSalary());
			flag = preparedStatement.executeUpdate() > 0 ? Boolean.TRUE
					: Boolean.FALSE;
			if (flag) {
				return empId;
			} else {
				return empId;
			}
		} catch (ResourceHelperException e) {
			DbUtils.rollbackAndCloseQuietly(connection);
			logger.error(Constants.INSERT_ERROR_MSG);
			throw new DaoException(e.getMessage());
		} catch (SQLException e) {
			logger.error(Constants.INSERT_ERROR_MSG);
			throw new DaoException(ErrorMessages.INTERNAL_ERROR);
		} catch (Exception e) {
			logger.error(Constants.INSERT_ERROR_MSG);
			throw new DaoException(ErrorMessages.INTERNAL_ERROR);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.personal.epf.dao.EmployeeDetailsDao#calculateEmployeePF(int)
	 */
	@Override
	public double calculateEmployeePF(int empid) throws DaoException {
		double empPf = Constants.DEFAULT_PF;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ResourceHelper.getConnection();
			preparedStatement = connection.prepareStatement(Constants.PF_QUERY);
			preparedStatement.setInt(1, empid);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String salary = resultSet.getString(Constants.SALARY);
				String pf = resultSet.getString(Constants.PFVALUE);
				empPf = (Double.parseDouble(pf) / 100)
						* Double.parseDouble(salary);
				break;
			}
		} catch (ResourceHelperException e) {
			DbUtils.rollbackAndCloseQuietly(connection);
			logger.error(Constants.RETRIEVE_ERROR_MSG);
			throw new DaoException(e.getMessage());
		} catch (SQLException e) {
			logger.error(Constants.RETRIEVE_ERROR_MSG);
			throw new DaoException(ErrorMessages.INTERNAL_ERROR);
		} catch (Exception e) {
			logger.error(Constants.RETRIEVE_ERROR_MSG);
			throw new DaoException(ErrorMessages.INTERNAL_ERROR);
		}
		return empPf;
	}

}
