package com.sssoft.isatt.data.dao.input;

import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.pojo.input.ObjectGroup;

/**
 * The Interface ObjectGroupDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides ObjectGroup records by interacting with the database
 */
public interface ObjectGroupDao {

	/**
	 * Insert object group get key.
	 * 
	 * @param objectGroup
	 *            the object group
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertObjectGroupGetKey(ObjectGroup objectGroup) throws DataAccessException;

	/**
	 * Insert object group.
	 * 
	 * @param objectGroup
	 *            the object group
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertObjectGroup(ObjectGroup objectGroup) throws DataAccessException;

	/**
	 * Update object group.
	 * 
	 * @param objectGroup
	 *            the object group
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateObjectGroup(ObjectGroup objectGroup) throws DataAccessException;

	/**
	 * This method fetches all rows from ObjectGroup table,.
	 * 
	 * @return ObjectGroup list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<ObjectGroup> getObjectGroup() throws DataAccessException;

	/**
	 * Gets the object group by id.
	 * 
	 * @param objectGroupId
	 *            the object group id
	 * @return the object group by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	ObjectGroup getObjectGroupById(int objectGroupId) throws DataAccessException;

	/**
	 * Gets the object group id by name and screen id.
	 * 
	 * @param objectGroupName
	 *            the object group name
	 * @param screenID
	 *            the screen id
	 * @return the object group id by name and screen id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getObjectGroupIDByNameAndScreenId(String objectGroupName, int screenID) throws DataAccessException;

	/**
	 * Gets the all object groups.
	 * 
	 * @return the all object groups
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<ObjectGroup> getAllObjectGroups() throws DataAccessException;

	/**
	 * Update object group data.
	 * 
	 * @param objectGroup
	 *            the object group
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateObjectGroupData(ObjectGroup objectGroup) throws DataAccessException;

	/**
	 * Gets the obj grps by screen id.
	 * 
	 * @param scrId
	 *            the scr id
	 * @return the obj grps by screen id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<ObjectGroup> getObjGrpsByScreenID(int scrId) throws DataAccessException;

	/**
	 * Gets the obj grps by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the obj grps by app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<ObjectGroup> getObjGrpsByAppID(int appId) throws DataAccessException;

}
