package com.exilant.tfw.dao;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.AppFeature;
import com.exilant.tfw.pojo.Screen;

/**
 * The Interface ScreenDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides Screen records by interacting with the database
 */
public interface ScreenDao {

	/**
	 * Insert screen get key.
	 *
	 * @param screen the screen
	 * @return the int
	 * @throws DataAccessException the data access exception
	 */
	int insertScreenGetKey(Screen screen) throws DataAccessException;

	/**
	 * Insert screen.
	 *
	 * @param Screen the screen
	 * @return number of rows inserted
	 * @throws DataAccessException the data access exception
	 */
	long insertScreen(Screen screen) throws DataAccessException;

	/**
	 * Update screen.
	 *
	 * @param screen the screen
	 * @return number of rows updated
	 * @throws DataAccessException the data access exception
	 */
	long updateScreen(Screen screen) throws DataAccessException;

	/**
	 * This method fetches all rows from Screen table,.
	 *
	 * @return Screen list
	 * @throws DataAccessException the data access exception
	 */
	List<Screen> getScreen() throws DataAccessException;

	/**
	 * Gets the screen by id.
	 *
	 * @param screenId the screen id
	 * @return the screen by id
	 * @throws DataAccessException the data access exception
	 */
	Screen getScreenById(int screenId) throws DataAccessException;

	/**
	 * Gets the screen filter by app feature id.
	 *
	 * @param screenId the screen id
	 * @return the screen filter by app feature id
	 * @throws DataAccessException the data access exception
	 */
	AppFeature getScreenFilterByAppFeatureId(int screenId) throws DataAccessException;

	/**
	 * Gets the screen id by name and feature id.
	 *
	 * @param screenName the screen name
	 * @param featureID the feature id
	 * @return the screen id by name and feature id
	 * @throws DataAccessException the data access exception
	 */
	int getScreenIDByNameAndFeatureID(String screenName, int featureID) throws DataAccessException;

	/**
	 * Gets the screen id by name.
	 *
	 * @param objectGroupName the object group name
	 * @return the screen id by name
	 * @throws DataAccessException the data access exception
	 */
	int getScreenIDByName(String objectGroupName) throws DataAccessException;

	/**
	 * Gets the all screens.
	 *
	 * @return the all screens
	 * @throws DataAccessException the data access exception
	 */
	List<Screen> getAllScreens() throws DataAccessException;
	
	/**
	 * Update screen data.
	 *
	 * @param screen the screen
	 * @return the long
	 * @throws DataAccessException the data access exception
	 */
	long updateScreenData(Screen screen) throws DataAccessException;
	
	/**
	 * Gets the screens by app id.
	 *
	 * @param appId the app id
	 * @return the screens by app id
	 * @throws DataAccessException the data access exception
	 */
	List<Screen> getScreensByAppId(int appId)throws DataAccessException;

	List<Screen> getScreensByAppIdAndFeatureID(int appId, int featureId) throws DataAccessException;

	List<Screen> getScreensByFeatureId(int featureID) throws DataAccessException;

}
