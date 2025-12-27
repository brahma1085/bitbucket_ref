package com.sssoft.isatt.data.dao.input;

import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.pojo.input.Param;

/**
 * The Interface ParamDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides Param records by interacting with the database
 */
public interface ParamDao {

	/**
	 * Insert param get key.
	 * 
	 * @param param
	 *            the param
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertParamGetKey(Param param) throws DataAccessException;

	/**
	 * Insert param.
	 * 
	 * @param param
	 *            the param
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertParam(Param param) throws DataAccessException;

	/**
	 * Update param.
	 * 
	 * @param param
	 *            the param
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateParam(Param param) throws DataAccessException;

	/**
	 * This method fetches all rows from Param table,.
	 * 
	 * @return Param list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<Param> getParam() throws DataAccessException;

	/**
	 * Gets the param by param group id.
	 * 
	 * @param paramGroupId
	 *            the param group id
	 * @return the param by param group id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<Param> getParamByParamGroupId(int paramGroupId) throws DataAccessException;

	/**
	 * Gets the param by id.
	 * 
	 * @param paramId
	 *            the param id
	 * @return the param by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	Param getParamById(int paramId) throws DataAccessException;

	/**
	 * Gets the param id by dependents.
	 * 
	 * @param param
	 *            the param
	 * @return the param id by dependents
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getParamIdByDependents(Param param) throws DataAccessException;

	/**
	 * Gets the param by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the param by app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<Param> getParamByAppId(int appId) throws DataAccessException;

	/**
	 * Update param details.
	 * 
	 * @param param
	 *            the param
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateParamDetails(Param param) throws DataAccessException;

}
