package com.personal.epf.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import com.personal.epf.dao.EmployeeDetailsDao;
import com.personal.epf.exceptions.DaoException;
import com.personal.epf.exceptions.ErrorMessages;
import com.personal.epf.exceptions.ResourceHelperException;
import com.personal.epf.exceptions.ServiceException;
import com.personal.epf.factory.DaoFactory;
import com.personal.epf.model.Employee;
import com.personal.epf.service.EmployeeDetailsService;
import com.personal.epf.utils.Constants;
import com.personal.epf.utils.ResourceHelper;

/**
 * @author brahma
 * 
 *         The Class EmployeeDetailsServiceImpl. It handles transactions which
 *         operations is having the interaction with the data base
 */
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService {

	/** The Constant logger. */
	private static final Logger logger = Logger
			.getLogger(EmployeeDetailsServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.personal.epf.service.EmployeeDetailsService#insertEmployeeDetails
	 * (com.personal.epf.model.Employee)
	 */
	@Override
	public int insertEmployeeDetails(Employee employee) throws ServiceException {
		int empId = Constants.DEFAULT_ID;
		Connection connection = null;
		try {
			connection = ResourceHelper.getConnection();
			connection.setAutoCommit(Boolean.FALSE);
			EmployeeDetailsDao employeeDao = DaoFactory.getEmployeeDetailsDao();
			empId = employeeDao.insertEmployeeDetails(employee);
			connection.commit();
		} catch (ResourceHelperException e) {
			DbUtils.rollbackAndCloseQuietly(connection);
			logger.error(Constants.INSERT_ERROR_MSG);
			throw new ServiceException(e.getMessage());
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(connection);
			logger.error(Constants.INSERT_ERROR_MSG);
			throw new ServiceException(ErrorMessages.INTERNAL_ERROR);
		} catch (DaoException e) {
			logger.error(Constants.INSERT_ERROR_MSG);
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error(Constants.INSERT_ERROR_MSG);
			throw new ServiceException(ErrorMessages.INTERNAL_ERROR);
		} finally {
			DbUtils.closeQuietly(connection);
		}
		return empId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.personal.epf.service.EmployeeDetailsService#retrieveEmployeeDetails
	 * (int)
	 */
	@Override
	public Employee retrieveEmployeeDetails(int empid) throws ServiceException {
		Employee employee = null;
		Connection connection = null;
		try {
			connection = ResourceHelper.getConnection();
			connection.setAutoCommit(Boolean.FALSE);
			EmployeeDetailsDao employeeDao = DaoFactory.getEmployeeDetailsDao();
			employee = employeeDao.retrieveEmployeeDetails(empid);
			connection.commit();
		} catch (ResourceHelperException e) {
			DbUtils.rollbackAndCloseQuietly(connection);
			logger.error(Constants.RETRIEVE_ERROR_MSG);
			throw new ServiceException(e.getMessage());
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(connection);
			logger.error(Constants.RETRIEVE_ERROR_MSG);
			throw new ServiceException(ErrorMessages.INTERNAL_ERROR);
		} catch (DaoException e) {
			logger.error(Constants.RETRIEVE_ERROR_MSG);
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error(Constants.RETRIEVE_ERROR_MSG);
			throw new ServiceException(ErrorMessages.INTERNAL_ERROR);
		} finally {
			DbUtils.closeQuietly(connection);
		}
		return employee;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.personal.epf.service.EmployeeDetailsService#calculateEPF(int)
	 */
	@Override
	public double calculateEPF(int empid) throws ServiceException {
		double empPf = Constants.DEFAULT_PF;
		Connection connection = null;
		try {
			connection = ResourceHelper.getConnection();
			connection.setAutoCommit(Boolean.FALSE);
			EmployeeDetailsDao employeeDao = DaoFactory.getEmployeeDetailsDao();
			empPf = employeeDao.calculateEmployeePF(empid);
			connection.commit();
		} catch (ResourceHelperException e) {
			DbUtils.rollbackAndCloseQuietly(connection);
			logger.error(Constants.RETRIEVE_ERROR_MSG);
			throw new ServiceException(e.getMessage());
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(connection);
			logger.error(Constants.RETRIEVE_ERROR_MSG);
			throw new ServiceException(ErrorMessages.INTERNAL_ERROR);
		} catch (DaoException e) {
			logger.error(Constants.RETRIEVE_ERROR_MSG);
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error(Constants.RETRIEVE_ERROR_MSG);
			throw new ServiceException(ErrorMessages.INTERNAL_ERROR);
		} finally {
			DbUtils.closeQuietly(connection);
		}
		return empPf;
	}

}
