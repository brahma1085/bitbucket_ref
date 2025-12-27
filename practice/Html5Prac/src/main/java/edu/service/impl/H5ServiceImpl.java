package edu.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import edu.bean.Contact;
import edu.bean.Users;
import edu.dao.H5Dao;
import edu.exceptions.DaoException;
import edu.exceptions.ServiceException;
import edu.service.H5Service;

public class H5ServiceImpl implements H5Service {

	private static final Logger LOG = Logger.getLogger(H5ServiceImpl.class);

	private H5Dao dao;
	private Connection connection;

	public void setDao(H5Dao dao) {
		this.dao = dao;
	}

	public H5Dao getDao() {
		return dao;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insertUsers(Users users) throws ServiceException {
		try {
			connection.setAutoCommit(false);
			dao.setConnection(connection);
			dao.insertUsers(users);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Received Details : " + users.toString());
			}
			connection.commit();
		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error("error occured while closing the connection. " + e.getMessage());
			}
		}
	}

	@Override
	public int updateUsers(Users users) throws ServiceException {
		int count = 0;
		try {
			connection.setAutoCommit(false);
			dao.setConnection(connection);
			count = dao.updateUsers(users);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Received Details : " + users.toString());
			}
			LOG.info("number of records updated : " + count);
			connection.commit();
		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error("error occured while closing the connection. " + e.getMessage());
			}
		}
		return count;
	}

	@Override
	public void insertContact(Contact contact) throws ServiceException {
		try {
			connection.setAutoCommit(false);
			dao.setConnection(connection);
			dao.insertContact(contact);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Received Details : " + contact.toString());
			}
			connection.commit();
		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error("error occured while closing the connection. " + e.getMessage());
			}
		}
	}

	@Override
	public int updateContact(Contact contact) throws ServiceException {
		int count = 0;
		try {
			connection.setAutoCommit(false);
			dao.setConnection(connection);
			count = dao.updateContact(contact);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Received Details : " + contact.toString());
			}
			LOG.info("number of records updated : " + count);
			connection.commit();
		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error("error occured while closing the connection. " + e.getMessage());
			}
		}
		return count;
	}

	@Override
	public int deleteUsers(int id) throws ServiceException {
		int count = 0;
		try {
			connection.setAutoCommit(false);
			dao.setConnection(connection);
			count = dao.deleteUsers(id);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Received Details : " + id);
			}
			LOG.info("number of records updated : " + count);
			connection.commit();
		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error("error occured while closing the connection. " + e.getMessage());
			}
		}
		return count;
	}

	@Override
	public int deleteContact(int id) throws ServiceException {
		int count = 0;
		try {
			connection.setAutoCommit(false);
			dao.setConnection(connection);
			count = dao.deleteContact(id);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Received Details : " + id);
			}
			LOG.info("number of records updated : " + count);
			connection.commit();
		} catch (SQLException e) {
			throw new ServiceException(e.getMessage());
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error("error occured while closing the connection. " + e.getMessage());
			}
		}
		return count;
	}

	@Override
	public List<Users> getUsers() throws ServiceException {
		try {
			dao.setConnection(connection);
			return dao.getUsers();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error("error occured while closing the connection. " + e.getMessage());
			}
		}
	}

	@Override
	public List<Contact> getContacts() throws ServiceException {
		try {
			dao.setConnection(connection);
			return dao.getContacts();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error("error occured while closing the connection. " + e.getMessage());
			}
		}
	}

	@Override
	public Users getUser(int id) throws ServiceException {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Received Details : " + id);
			}
			dao.setConnection(connection);
			return dao.getUser(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error("error occured while closing the connection. " + e.getMessage());
			}
		}
	}
}
