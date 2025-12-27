package com.exilant.tfw.dao.input;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.input.IdentifierType;

/**
 * The Interface IdentifierTypeDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides IdentifierType records by interacting with the
 * database
 */
public interface IdentifierTypeDao {

	/**
	 * Insert identifier type get key.
	 * 
	 * @param identifierType
	 *            the identifier type
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertIdentifierTypeGetKey(IdentifierType identifierType) throws DataAccessException;

	/**
	 * Insert identifier type.
	 * 
	 * @param identifierType
	 *            the identifier type
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertIdentifierType(IdentifierType identifierType) throws DataAccessException;

	/**
	 * Update identifier type.
	 * 
	 * @param identifierType
	 *            the identifier type
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateIdentifierType(IdentifierType identifierType) throws DataAccessException;

	/**
	 * This method fetches all rows from IdentifierType table,.
	 * 
	 * @return IdentifierType list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<IdentifierType> getIdentifierType() throws DataAccessException;

	/**
	 * Gets the identifier type by id.
	 * 
	 * @param identifierTypeId
	 *            the identifier type id
	 * @return the identifier type by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	IdentifierType getIdentifierTypeById(int identifierTypeId) throws DataAccessException;

	/**
	 * Gets the identifier id by name.
	 * 
	 * @param identifierName
	 *            the identifier name
	 * @return the identifier id by name
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getIdentifierIdByName(String identifierName) throws DataAccessException;

	/**
	 * Gets the identifier type id by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the identifier type id by app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getIdentifierTypeIdByAppID(int appId) throws DataAccessException;

	/**
	 * Gets the identifier id by name and app id.
	 * 
	 * @param identifierTypeName
	 *            the identifier type name
	 * @param appId
	 *            the app id
	 * @return the identifier id by name and app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getIdentifierIdByNameAndAppId(String identifierTypeName, int appId) throws DataAccessException;

	/**
	 * Gets the all identifier types.
	 * 
	 * @return the all identifier types
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<IdentifierType> getAllIdentifierTypes() throws DataAccessException;

	/**
	 * Gets the identifier type by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the identifier type by app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<IdentifierType> getIdentifierTypeByAppID(int appId) throws DataAccessException;

}
