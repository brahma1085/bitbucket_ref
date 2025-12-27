package com.eris.services;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import com.eris.daos.ClientDao;
import com.eris.exceptions.DaoException;
import com.eris.exceptions.ResourceHelperException;
import com.eris.exceptions.ServiceException;
import com.eris.factories.DaoFactory;
import com.eris.model.Client;
import com.eris.utils.ResourceHelper;

public class ClientServiceImpl implements ClientService {

	public boolean insertClientDetails(Client client) throws ServiceException {
		boolean flag = false;
		Connection connection = null;
		try {
			connection = ResourceHelper.getConnection();
			connection.setAutoCommit(false);
			ClientDao clientDao = DaoFactory.getClientDao();
			flag = clientDao.insertClientDetails(client);
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
}