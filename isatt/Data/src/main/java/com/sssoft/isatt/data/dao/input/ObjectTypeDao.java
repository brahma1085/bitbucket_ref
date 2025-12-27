package com.sssoft.isatt.data.dao.input;

import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.pojo.input.ObjectType;

/**
 * The Interface ObjectTypeDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides ObjectType records by interacting with the database
 */
public interface ObjectTypeDao {

	/**
	 * Insert object type get key.
	 * 
	 * @param objectType
	 *            the object type
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertObjectTypeGetKey(ObjectType objectType) throws DataAccessException;

	/**
	 * Insert object type.
	 * 
	 * @param objectType
	 *            the object type
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertObjectType(ObjectType objectType) throws DataAccessException;

	/**
	 * Update object type.
	 * 
	 * @param objectType
	 *            the object type
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateObjectType(ObjectType objectType) throws DataAccessException;

	/**
	 * This method fetches all rows from ObjectType table,.
	 * 
	 * @return ObjectType list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<ObjectType> getObjectType() throws DataAccessException;

	/**
	 * Gets the object type by id.
	 * 
	 * @param objectTypeId
	 *            the object type id
	 * @return the object type by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	ObjectType getObjectTypeById(int objectTypeId) throws DataAccessException;

	/**
	 * Gets the object type by action id.
	 * 
	 * @param actionId
	 *            the action id
	 * @return the object type by action id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<ObjectType> getObjectTypeByActionId(int actionId) throws DataAccessException;

	/**
	 * Gets the object type id by name and action id.
	 * 
	 * @param actionId
	 *            the action id
	 * @param objectTypeName
	 *            the object type name
	 * @return the object type id by name and action id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getObjectTypeIdByNameAndActionId(int actionId, String objectTypeName) throws DataAccessException;

	/**
	 * Gets the object type by action id.
	 * 
	 * @param actionId
	 *            the action id
	 * @param objectTypeName
	 *            the object type name
	 * @return the object type by action id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<ObjectType> getObjectTypeByActionId(int actionId, String objectTypeName) throws DataAccessException;

	/**
	 * Gets the object type id by name.
	 * 
	 * @param objectName
	 *            the object name
	 * @return the object type id by name
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getObjectTypeIDByName(String objectName) throws DataAccessException;

	/**
	 * Gets the all object types.
	 * 
	 * @return the all object types
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<ObjectType> getAllObjectTypes() throws DataAccessException;

}
