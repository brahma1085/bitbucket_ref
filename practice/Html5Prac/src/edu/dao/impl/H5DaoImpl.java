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
		try {
			PreparedStatement statement = connection.prepareStatement(Constants.INSERT_USERS);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public int updateUsers(Users users) throws DaoException {
		try {
			PreparedStatement statement = connection.prepareStatement(Constants.UPDATE_USER);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return 0;
	}

	@Override
	public void insertContact(Contact contact) throws DaoException {
		try {
			PreparedStatement statement = connection.prepareStatement(Constants.INSERT_CONTACT);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public int updateContact(Contact contact) throws DaoException {
		try {
			PreparedStatement statement = connection.prepareStatement(Constants.UPDATE_CONTACT);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return 0;
	}

	@Override
	public int deleteUsers(int id) throws DaoException {
		try {
			PreparedStatement statement = connection.prepareStatement(Constants.DELETE_USER);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return 0;
	}

	@Override
	public int deleteContact(int id) throws DaoException {
		try {
			PreparedStatement statement = connection.prepareStatement(Constants.DELETE_CONTACT);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return 0;
	}

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Users> getUsers() throws DaoException {
		List<Users> users = new ArrayList<Users>();
		try {
			PreparedStatement statement = connection.prepareStatement(Constants.GET_ALL_USERS);
			ResultSet resultSet = statement.executeQuery();
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
		}
		return users;
	}

	@Override
	public List<Contact> getContacts() throws DaoException {
		try {
			PreparedStatement statement = connection.prepareStatement(Constants.GET_ALL_CONTACT_INFO);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return null;
	}

	@Override
	public Users getUser(int id) throws DaoException {
		Users users = new Users();
		try {
			PreparedStatement statement = connection.prepareStatement(Constants.GET_USER);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
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
		}
		return users;
	}
}
