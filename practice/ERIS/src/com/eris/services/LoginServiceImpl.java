package com.eris.services;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import com.eris.daos.LoginDao;
import com.eris.exceptions.DaoException;
import com.eris.exceptions.ResourceHelperException;
import com.eris.exceptions.ServiceException;
import com.eris.factories.DaoFactory;
import com.eris.model.Login;
import com.eris.utils.ResourceHelper;

public class LoginServiceImpl implements LoginService {

	public boolean insertUserDetails(Login login) throws ServiceException {
		boolean flag = false;
		Connection connection = null;
		try {
			connection = ResourceHelper.getConnection();
			connection.setAutoCommit(false);
			LoginDao loginDao = DaoFactory.getLoginDao();
			flag = loginDao.insertUserDetails(login);
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
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
			throw new ServiceException();
		} finally {
			DbUtils.closeQuietly(connection);
			try {
				if(connection.isClosed()){
					System.out.println("Log inConnection Closed");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}

	public boolean isAuthenticate(Login login) throws ServiceException {
		boolean flag = false;
		Connection connection = null;
		try {
			connection = ResourceHelper.getConnection();
			connection.setAutoCommit(false);
			LoginDao loginDao = DaoFactory.getLoginDao();
			flag = loginDao.isAuthenticate(login);
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
		}
		return flag;
	}
}
