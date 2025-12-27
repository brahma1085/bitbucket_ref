package edu.service;

import java.util.List;

import edu.bean.Contact;
import edu.bean.Users;
import edu.exceptions.ServiceException;

public interface H5Service {

	void insertUsers(Users users) throws ServiceException;

	int updateUsers(Users users) throws ServiceException;

	void insertContact(Contact contact) throws ServiceException;

	int updateContact(Contact contact) throws ServiceException;

	int deleteUsers(int id) throws ServiceException;

	int deleteContact(int id) throws ServiceException;

	List<Users> getUsers() throws ServiceException;

	List<Contact> getContacts() throws ServiceException;

	Users getUser(int id) throws ServiceException;
}
