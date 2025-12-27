/**
 * 
 */
package com.exilant.tfw.dao;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.Roles;

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
