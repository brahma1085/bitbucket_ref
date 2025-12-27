/**
 * 
 */
package com.exilant.tfw.dao;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.UserRoles;
import com.exilant.tfw.pojo.Users;

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
