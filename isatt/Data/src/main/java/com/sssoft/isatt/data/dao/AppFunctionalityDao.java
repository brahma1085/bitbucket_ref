package com.sssoft.isatt.data.dao;

import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.pojo.AppFunctionality;

/**
 * The Interface AppFunctionalityDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides AppFunctionality records by interacting with the
 * database
 */
public interface AppFunctionalityDao {

	/**
	 * Insert app functionality get key.
	 *
	 * @param appFunctionality the app functionality
	 * @return the int
	 * @throws DataAccessException the data access exception
	 */
	int insertAppFunctionalityGetKey(AppFunctionality appFunctionality) throws DataAccessException;

	/**
	 * This method will insert the shallow AppFunctionality data into the
	 * database.
	 *
	 * @param appFunctionality the app functionality
	 * @return number of rows inserted
	 * @throws DataAccessException the data access exception
	 */
	long insertAppFunctionality(AppFunctionality appFunctionality) throws DataAccessException;

	/**
	 * This method will udpate the shallow AppFunctionality data into the
	 * database.
	 *
	 * @param appFunctionality the app functionality
	 * @return number of rows updated
	 * @throws DataAccessException the data access exception
	 */
	long updateAppFunctionality(AppFunctionality appFunctionality) throws DataAccessException;

	/**
	 * This method fetches all rows from AppFunctionality table,.
	 *
	 * @return AppFunctionality list
	 * @throws DataAccessException the data access exception
	 */
	List<AppFunctionality> getAppFunctionality() throws DataAccessException;

	/**
	 * Gets the app functionality id by name and app id.
	 *
	 * @param appFunctionality the app functionality
	 * @return the app functionality id by name and app id
	 * @throws DataAccessException the data access exception
	 */
	int getAppFunctionalityIdByNameAndAppID(AppFunctionality appFunctionality) throws DataAccessException;

	/**
	 * Gets the app functionality by id.
	 *
	 * @param functionalId the functional id
	 * @return the app functionality by id
	 * @throws DataAccessException the data access exception
	 */
	AppFunctionality getAppFunctionalityById(int functionalId) throws DataAccessException;

	/**
	 * Gets the app functionality by app id.
	 *
	 * @param appId the app id
	 * @return the app functionality by app id
	 * @throws DataAccessException the data access exception
	 */
	List<AppFunctionality> getAppFunctionalityByAppId(int appId) throws DataAccessException;

	/**
	 * Gets the app functionality filter by app id.
	 *
	 * @param appId the app id
	 * @return the app functionality filter by app id
	 * @throws DataAccessException the data access exception
	 */
	List<AppFunctionality> getAppFunctionalityFilterByAppId(int appId) throws DataAccessException;

	/**
	 * Gets the app functionality id by name.
	 *
	 * @param appId the app id
	 * @param functionalName the functional name
	 * @return the app functionality id by name
	 * @throws DataAccessException the data access exception
	 */
	int getAppFunctionalityIdByName(int appId, String functionalName) throws DataAccessException;
	
	/**
	 * Gets the app function names by app function id.
	 *
	 * @param appFunctionId the app function id
	 * @return the app function names by app function id
	 * @throws DataAccessException the data access exception
	 */
	List<AppFunctionality> getAppFunctionNamesByAppFunctionId(int appFunctionId) throws DataAccessException;
	
	/**
	 * Gets the all function names by test case id.
	 *
	 * @param appFunctionId the app function id
	 * @return the all function names by test case id
	 * @throws DataAccessException the data access exception
	 */
	List<AppFunctionality> getAllFunctionNamesByTestCaseId(int appFunctionId) throws DataAccessException ;
	
	/**
	 * Update app functionality data.
	 *
	 * @param appFunctionality the app functionality
	 * @return the long
	 * @throws DataAccessException the data access exception
	 */
	long updateAppFunctionalityData(AppFunctionality appFunctionality) throws DataAccessException;
	
	/**
	 * Gets the app functionality obj by app id.
	 *
	 * @param appID the app id
	 * @return the app functionality obj by app id
	 * @throws DataAccessException the data access exception
	 */
	List<AppFunctionality> getAppFunctionalityObjByAppId(int appID) throws DataAccessException;

}
