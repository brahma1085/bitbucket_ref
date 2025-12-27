package com.sssoft.isatt.data.dao;

import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.Application;

/**
 * The Interface ApplicationDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides Application records by interacting with the database
 */
public interface ApplicationDao {

	/**
	 * Insert application get key.
	 *
	 * @param application the application
	 * @return the int
	 * @throws DataAccessException the data access exception
	 */
	int insertApplicationGetKey(Application application) throws DataAccessException;

	/**
	 * Insert application.
	 *
	 * @param application the application
	 * @return number of rows inserted
	 * @throws DataAccessException the data access exception
	 */
	long insertApplication(Application application) throws DataAccessException;

	/**
	 * Update application.
	 *
	 * @param application the application
	 * @return number of rows updated
	 * @throws DataAccessException the data access exception
	 */
	long updateApplication(Application application) throws DataAccessException;

	/**
	 * This method fetches all rows from Application table,.
	 *
	 * @return Application list
	 * @throws DataAccessException the data access exception
	 */
	List<Application> getApplication() throws DataAccessException;

	/**
	 * Gets the app id by app name.
	 *
	 * @param applicationName the application name
	 * @return the app id by app name
	 * @throws DataAccessException the data access exception
	 */
	int getAppIdByAppName(String applicationName) throws DataAccessException;

	/**
	 * Gets the application by id.
	 *
	 * @param appId the app id
	 * @return the application by id
	 * @throws DataAccessException the data access exception
	 */
	Application getApplicationById(int appId) throws DataAccessException;
	
	List<Application> getAllApps() throws DataAccessException;
}
