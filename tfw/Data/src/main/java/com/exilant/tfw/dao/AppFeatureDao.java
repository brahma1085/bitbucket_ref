package com.exilant.tfw.dao;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.AppFeature;
import com.exilant.tfw.pojo.AppFunctionality;

/**
 * The Interface AppFeatureDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides AppFeatureDao records by interacting with the
 * database
 */
public interface AppFeatureDao {

	/**
	 * Insert feature get key.
	 *
	 * @param appFeature the app feature
	 * @return the int
	 * @throws DataAccessException the data access exception
	 */
	int insertFeatureGetKey(AppFeature appFeature) throws DataAccessException;

	/**
	 * This method will insert the shallow AppFeature data into the database.
	 *
	 * @param appFeature the app feature
	 * @return number of rows inserted
	 * @throws DataAccessException the data access exception
	 */
	long insertAppFeature(AppFeature appFeature) throws DataAccessException;

	/**
	 * This method will udpate the shallow AppFeature data into the database.
	 *
	 * @param appFeature the app feature
	 * @return number of rows updated
	 * @throws DataAccessException the data access exception
	 */
	long updateAppFeature(AppFeature appFeature) throws DataAccessException;

	/**
	 * This method fetches all rows from AppFeature table,.
	 *
	 * @return AppFeature list
	 * @throws DataAccessException the data access exception
	 */
	List<AppFeature> getAppFeature() throws DataAccessException;

	/**
	 * Gets the app feature by functional id.
	 *
	 * @param functionalId the functional id
	 * @return the app feature by functional id
	 * @throws DataAccessException the data access exception
	 */
	List<AppFeature> getAppFeatureByFunctionalId(int functionalId) throws DataAccessException;

	/**
	 * Gets the app feature by id.
	 *
	 * @param appFeatureId the app feature id
	 * @return the app feature by id
	 * @throws DataAccessException the data access exception
	 */
	AppFeature getAppFeatureById(int appFeatureId) throws DataAccessException;

	/**
	 * Gets the app feature id by name.
	 *
	 * @param featureName the feature name
	 * @param functionalId the functional id
	 * @return the app feature id by name
	 * @throws DataAccessException the data access exception
	 */
	int getAppFeatureIdByName(String featureName, int functionalId) throws DataAccessException;

	/**
	 * Gets the app feature filter by app functionality id.
	 *
	 * @param appFeatureID the app feature id
	 * @return the app feature filter by app functionality id
	 * @throws DataAccessException the data access exception
	 */
	List<AppFunctionality> getAppFeatureFilterByAppFunctionalityID(int appFeatureID) throws DataAccessException;

	/**
	 * Gets the app feature id by name.
	 *
	 * @param screenName the screen name
	 * @return the app feature id by name
	 * @throws DataAccessException the data access exception
	 */
	int getAppFeatureIDByName(String screenName) throws DataAccessException;

	/**
	 * Gets the app functional id by feature id.
	 *
	 * @param featureID the feature id
	 * @return the app functional id by feature id
	 * @throws DataAccessException the data access exception
	 */
	int getAppFunctionalIDByFeatureID(int featureID) throws DataAccessException;

	/**
	 * Gets the app feature id by name and functional id.
	 *
	 * @param appFeature the app feature
	 * @return the app feature id by name and functional id
	 * @throws DataAccessException the data access exception
	 */
	int getAppFeatureIDByNameAndFunctionalID(AppFeature appFeature) throws DataAccessException;
	
	/**
	 * Gets the all app features by functional id.
	 *
	 * @param appFunctionalityID the app functionality id
	 * @return the all app features by functional id
	 * @throws DataAccessException the data access exception
	 */
	List<AppFeature> getAllAppFeaturesByFunctionalId(int appFunctionalityID) throws DataAccessException;
	
	/**
	 * Gets the all feature names by test case id.
	 *
	 * @param appFeatureId the app feature id
	 * @return the all feature names by test case id
	 * @throws DataAccessException the data access exception
	 */
	List<AppFeature> getAllFeatureNamesByTestCaseId(int appFeatureId) throws DataAccessException ;
	
	/**
	 * Gets the app feature names by app feature id.
	 *
	 * @param appFeatureId the app feature id
	 * @return the app feature names by app feature id
	 * @throws DataAccessException the data access exception
	 */
	List<AppFeature> getAppFeatureNamesByAppFeatureId(int appFeatureId) throws DataAccessException;
	
	/**
	 * Update app feature data.
	 *
	 * @param appFeature the app feature
	 * @return the long
	 * @throws DataAccessException the data access exception
	 */
	long updateAppFeatureData(AppFeature appFeature) throws DataAccessException; 

}