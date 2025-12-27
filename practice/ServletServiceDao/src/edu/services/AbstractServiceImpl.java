package edu.services;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import edu.daos.StudentDao;
import edu.exceptions.DaoException;
import edu.exceptions.ResourceHelperException;
import edu.exceptions.ServiceException;
import edu.factory.DaoFactory;
import edu.models.Details;
import edu.utils.ResourceHelper;

public class AbstractServiceImpl implements AbstractService {
	private Connection connection;

	public boolean pushDetails(Details details) throws ServiceException {
		boolean flag = false;
		try {
			connection = ResourceHelper.getConnection();
			connection.setAutoCommit(false);
			StudentDao factory = DaoFactory.getAbstractDao();
			flag = factory.insertDetails(details);
			connection.commit();
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			DbUtils.rollbackAndCloseQuietly(connection);
			throw new ServiceException();
		} catch (DaoException e) {
			System.out.println("DaoException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			throw new ServiceException();
		} catch (ResourceHelperException e) {
			System.out.println("ResourceHelperException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
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
