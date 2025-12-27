package com.exilant.tfw.dao;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.GenericData;

/**
 * The Interface GenericDataDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides GenericData records by interacting with the database
 */
public interface GenericDataDao {

	/**
	 * Insert generic data get key.
	 * 
	 * @param genericData
	 *            the generic data
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertGenericDataGetKey(GenericData genericData) throws DataAccessException;

	/**
	 * Insert generic data.
	 * 
	 * @param genericData
	 *            the generic data
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertGenericData(GenericData genericData) throws DataAccessException;

	/**
	 * Update generic data.
	 * 
	 * @param genericData
	 *            the generic data
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateGenericData(GenericData genericData) throws DataAccessException;

	/**
	 * This method fetches all rows from GenericData table,.
	 * 
	 * @return GenericData list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<GenericData> getGenericData() throws DataAccessException;

	/**
	 * Gets the generic data by id.
	 * 
	 * @param genericDataId
	 *            the generic data id
	 * @return the generic data by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	GenericData getGenericDataById(int genericDataId) throws DataAccessException;
}
