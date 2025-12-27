/**
 * 
 */
package com.exilant.tfw.dao;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.Users;

/**
 * @author mohammedfirdos
 *
 */

/**
 * This interface provides Users records by interacting with the database
 */
public interface UsersDao {

	int insertUsersGetKey(Users users) throws DataAccessException;

	boolean checkAvailability(String username) throws DataAccessException;

	int getUserIdByUserName(String userName) throws DataAccessException;

	Users getUsersByName(String username) throws DataAccessException;
	
	Users getUsersByUserID(int userID) throws DataAccessException;
	
	String getPasswordByUsernameEmailID(String username, String emailID) throws DataAccessException;

	int getIncorrectPasswordCount(String username) throws DataAccessException;
	
	long updateIncorrectPasswordCount(String username, int incorrectPasswordCount) throws DataAccessException;
	
	String getEmailByUsername(String username) throws DataAccessException;
	
	long updatePasswordByUsername(String username, String newPassword) throws DataAccessException;

	List<Users> getAllUsers() throws DataAccessException;

	long updateUser(Users users) throws DataAccessException;
	
}