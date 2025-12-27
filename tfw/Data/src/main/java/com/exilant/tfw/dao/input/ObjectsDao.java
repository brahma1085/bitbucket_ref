package com.exilant.tfw.dao.input;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.input.Objects;

/**
 * The Interface ObjectsDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides Objects records by interacting with the database
 */
public interface ObjectsDao {

	/**
	 * Insert objects get key.
	 * 
	 * @param objects
	 *            the objects
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertObjectsGetKey(Objects objects) throws DataAccessException;

	/**
	 * Insert objects.
	 * 
	 * @param objects
	 *            the objects
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertObjects(Objects objects) throws DataAccessException;

	/**
	 * Update objects.
	 * 
	 * @param objects
	 *            the objects
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateObjects(Objects objects) throws DataAccessException;

	/**
	 * This method fetches all rows from Objects table,.
	 * 
	 * @return Objects list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<Objects> getObjects() throws DataAccessException;

	/**
	 * Gets the objects by id.
	 * 
	 * @param objectId
	 *            the object id
	 * @return the objects by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	Objects getObjectsById(int objectId) throws DataAccessException;

	/**
	 * Gets the objects by group id.
	 * 
	 * @param objectGroupId
	 *            the object group id
	 * @return the objects by group id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<Objects> getObjectsByGroupId(int objectGroupId) throws DataAccessException;

	/**
	 * Gets the object id by name.
	 * 
	 * @param objectName
	 *            the object name
	 * @return the object id by name
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getObjectIDByName(String objectName) throws DataAccessException;

	/**
	 * Gets the object id by name by obj groupid by obj typeid by iden typeid.
	 * 
	 * @param objects
	 *            the objects
	 * @return the object id by name by obj groupid by obj typeid by iden typeid
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getObjectIdByNameByObjGroupidByObjTypeidByIdenTypeid(Objects objects) throws DataAccessException;

	/**
	 * Update objects details.
	 * 
	 * @param objects
	 *            the objects
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateObjectsDetails(Objects objects) throws DataAccessException;

}
