/**
 * 
 */
package com.sssoft.isatt.data.dao;

import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.Roles;

/**
 * @author mohammedfirdos
 *
 */

/**
 * This interface provides RolesDao records by interacting with the database
 */
public interface RolesDao {

	/**
	 * This method fetches all rows from Roles table,
	 * 
	 * @param
	 * @return Roles list
	 * @throws DataAccessException
	 */
	List<Roles> getRoles() throws DataAccessException;
	
	int insertRolesGetKey(Roles roles) throws DataAccessException;

	Roles getRoleByID(int roleID)throws DataAccessException;

	long updateRole(Roles roles) throws DataAccessException;

}
