package edu.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edu.bean.Contact;
import edu.bean.Users;
import edu.dao.H5Dao;
import edu.exceptions.DaoException;
import edu.util.Constants;

public class H5DaoImpl implements H5Dao {

	private static final Logger LOG = Logger.getLogger(H5DaoImpl.class);

	private Connection connection;

	@Override
	public void insertUsers(Users users) throws DaoException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(Constants.INSERT_USERS);
			statement.setInt(1, users.getId());
			statement.setString(2, users.getName());
			statement.setString(3, users.getPassword());
			statement.setString(4, users.getDescription());
			statement.setDate(5, users.getCreatedDate());
			statement.setString(6, users.getCreatedBy());
			statement.executeUpdate();
			LOG.info("data inserted : " + users);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					LOG.error("error occured while closing the statement " + e.getMessage());
				}
			}
		}
	}

	@Override
	public int updateUsers(Users users) throws DaoException {
		int count = 0;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(Constants.UPDATE_USER);
			statement.setInt(3, users.getId());
			statement.setString(1, users.getName());
			statement.setString(2, users.getPassword());
			count = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					LOG.error("error occured while closing the statement " + e.getMessage());
				}
			}
		}
		LOG.info("returning the count : " + count);
		return count;
	}

	@Override
	public void insertContact(Contact contact) throws DaoException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(Constants.INSERT_CONTACT);
			statement.setInt(1, contact.getId());
			statement.setString(2, contact.getName());
			statement.setString(3, contact.getDesignation());
			statement.setString(4, contact.getInfo());
			statement.setString(5, contact.getDescription());
			statement.setDate(6, contact.getCreatedDate());
			statement.setString(7, contact.getCreatedBy());
			statement.executeUpdate();
			LOG.info("data inserted : " + contact);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					LOG.error("error occured while closing the statement " + e.getMessage());
				}
			}
		}
	}

	@Override
	public int updateContact(Contact contact) throws DaoException {
		int count = 0;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(Constants.UPDATE_CONTACT);
			statement.setInt(4, contact.getId());
			statement.setString(1, contact.getName());
			statement.setString(2, contact.getDesignation());
			statement.setString(3, contact.getInfo());
			count = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					LOG.error("error occured while closing the statement " + e.getMessage());
				}
			}
		}
		LOG.info("returning the count : " + count);
		return count;
	}

	@Override
	public int deleteUsers(int id) throws DaoException {
		int count = 0;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(Constants.DELETE_USER);
			statement.setInt(1, id);
			count = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					LOG.error("error occured while closing the statement " + e.getMessage());
				}
			}
		}
		LOG.info("returning the count : " + count);
		return count;
	}

	@Override
	public int deleteContact(int id) throws DaoException {
		int count = 0;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(Constants.DELETE_CONTACT);
			statement.setInt(1, id);
			count = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					LOG.error("error occured while closing the statement " + e.getMessage());
				}
			}
		}
		LOG.info("returning the count : " + count);
		return count;
	}

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Users> getUsers() throws DaoException {
		List<Users> users = new ArrayList<Users>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Constants.GET_ALL_USERS);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Users user = new Users();
				user.setId(resultSet.getInt("ID"));
				user.setName(resultSet.getString("NAME"));
				user.setPassword(resultSet.getString("PASSWORD"));
				user.setDescription(resultSet.getString("DESCRIPTION"));
				user.setCreatedDate(resultSet.getDate("CREATED_DATE"));
				user.setCreatedBy(resultSet.getString("CREATED_BY"));
				users.add(user);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					LOG.error("error occured while closing the statement " + e.getMessage());
				}
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					LOG.error("error occured while closing the resultset " + e.getMessage());
				}
			}
		}
		LOG.info("returning the details : " + users);
		return users;
	}

	@Override
	public List<Contact> getContacts() throws DaoException {
		List<Contact> contacts = new ArrayList<Contact>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Constants.GET_ALL_CONTACT_INFO);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Contact contact = new Contact();
				contact.setId(resultSet.getInt("ID"));
				contact.setName(resultSet.getString("NAME"));
				contact.setDesignation(resultSet.getString("DESIGNATION"));
				contact.setInfo(resultSet.getString("INFO"));
				contact.setDescription(resultSet.getString("DESCRIPTIOM"));
				contact.setCreatedDate(resultSet.getDate("CREATED_DATE"));
				contact.setCreatedBy(resultSet.getString("CREATED_BY"));
				contacts.add(contact);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					LOG.error("error occured while closing the statement " + e.getMessage());
				}
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					LOG.error("error occured while closing the resultset " + e.getMessage());
				}
			}
		}
		LOG.info("returning the details : " + contacts);
		return contacts;
	}

	@Override
	public Users getUser(int id) throws DaoException {
		Users users = new Users();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Constants.GET_USER);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				users.setId(resultSet.getInt("ID"));
				users.setName(resultSet.getString("NAME"));
				users.setPassword(resultSet.getString("PASSWORD"));
				users.setDescription(resultSet.getString("DESCRIPTION"));
				users.setCreatedDate(resultSet.getDate("CREATED_DATE"));
				users.setCreatedBy(resultSet.getString("CREATED_BY"));
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					LOG.error("error occured while closing the statement " + e.getMessage());
				}
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					LOG.error("error occured while closing the resultset " + e.getMessage());
				}
			}
		}
		LOG.info("returning the details : " + users);
		return users;
	}
}
