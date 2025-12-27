/**
 * 
 */
package com.exilant.tfw.dao;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.UsersApplicationMapping;

/**
 * @author mohammedfirdos
 *
 */

/**
 * This interface provides UsersApplicationMapping records by interacting with
 * the database
 */
public interface UsersApplicationMappingDao {

	int insertUsersApplicationMappingGetKey(UsersApplicationMapping usersApplicationMapping) throws DataAccessException;

	/**
	 * This method fetches all rows from UsersApplicationMapping table,
	 * 
	 * @param
	 * @return Application list
	 * @throws DataAccessException
	 */
	List<UsersApplicationMapping> getApplicationByUserId(int userId) throws DataAccessException;

	List<Integer> getApplicationsByRoleAndUserID(int userID, String role) throws DataAccessException;

}
