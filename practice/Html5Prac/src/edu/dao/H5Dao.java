package edu.dao;

import java.sql.Connection;
import java.util.List;

import edu.bean.Contact;
import edu.bean.Users;
import edu.exceptions.DaoException;

public interface H5Dao {

	void setConnection(Connection connection);

	void insertUsers(Users users) throws DaoException;

	int updateUsers(Users users) throws DaoException;

	void insertContact(Contact contact) throws DaoException;

	int updateContact(Contact contact) throws DaoException;

	int deleteUsers(int id) throws DaoException;

	int deleteContact(int id) throws DaoException;

	List<Users> getUsers() throws DaoException;

	List<Contact> getContacts() throws DaoException;

	Users getUser(int id) throws DaoException;
}
