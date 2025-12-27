/**
 * 
 */
package com.sssoft.isatt.data.dao;

import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.pojo.UserRoles;
import com.sssoft.isatt.data.pojo.Users;

/**
 * @author mohammedfirdos
 *
 */

/**
 * This interface provides UserRoles records by interacting with the database
 */
public interface UserRolesDao {

	int insertUserRolesGetKey(UserRoles userRoles) throws DataAccessException;

	List<String> getUserRoleById(int userId) throws DataAccessException;
	
	int getUserIdByRole(String roleName) throws DataAccessException;

	List<String> getUserRolesFilterByUserId(int userId)
			throws DataAccessException;

}
