package com.eris.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.eris.daos.EmployeeDao;
import com.eris.exceptions.DaoException;
import com.eris.exceptions.ResourceHelperException;
import com.eris.exceptions.ServiceException;
import com.eris.factories.DaoFactory;
import com.eris.model.Employee;
import com.eris.utils.ResourceHelper;

public class EmployeeServiceImpl implements EmployeeService {

	public boolean insertEmployeeDetails(Employee employee)
			throws ServiceException {
		boolean flag = false;
		Connection connection = null;
		try {
			connection = ResourceHelper.getConnection();
			connection.setAutoCommit(false);
			EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
			flag = employeeDao.insertEmployeeDetails(employee);
			connection.commit();
		} catch (ResourceHelperException e) {
			System.out.println("ResourceHelperException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
			DbUtils.rollbackAndCloseQuietly(connection);
			throw new ServiceException();
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			DbUtils.rollbackAndCloseQuietly(connection);
			throw new ServiceException();
		} catch (DaoException e) {
			System.out.println("DaoException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			throw new ServiceException();
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
			throw new ServiceException();
		} finally {
			DbUtils.closeQuietly(connection);
		}
		return flag;
	}

	public List retrieveClientid() throws ServiceException {
		List list = new ArrayList();
		Connection connection = null;
		try {
			connection = ResourceHelper.getConnection();
			connection.setAutoCommit(false);
			EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
			list = employeeDao.retrieveClientid();
			connection.commit();
		} catch (ResourceHelperException e) {
			System.out.println("ResourceHelperException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
			DbUtils.rollbackAndCloseQuietly(connection);
			throw new ServiceException();
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			DbUtils.rollbackAndCloseQuietly(connection);
			throw new ServiceException();
		} catch (DaoException e) {
			System.out.println("DaoException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			throw new ServiceException();
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
			throw new ServiceException();
		} finally {
			DbUtils.closeQuietly(connection);
			try {
				if(connection.isClosed()){
					System.out.println("Connection Closed");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	public long retrieveEmpid() throws ServiceException {
		long empid = 0;
		Connection connection = null;
		try {
			connection = ResourceHelper.getConnection();
			connection.setAutoCommit(false);
			EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
			empid = employeeDao.retrieveEmpid();
			connection.commit();
		} catch (ResourceHelperException e) {
			System.out.println("ResourceHelperException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
			DbUtils.rollbackAndCloseQuietly(connection);
			throw new ServiceException();
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			DbUtils.rollbackAndCloseQuietly(connection);
			throw new ServiceException();
		} catch (DaoException e) {
			System.out.println("DaoException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			throw new ServiceException();
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
			throw new ServiceException();
		} finally {
			DbUtils.closeQuietly(connection);
		}
		return empid;
	}
}
