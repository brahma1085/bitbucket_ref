package com.exilant.tfw.dao;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.Actions;

/**
 * The Interface ActionsDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides Action records by interacting with the database
 */
public interface ActionsDao {

	/**
	 * Insert actions get key.
	 * 
	 * @param actions
	 *            the actions
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertActionsGetKey(Actions actions) throws DataAccessException;

	/**
	 * This method will insert the shallow Actions data into the database.
	 * 
	 * @param actions
	 *            the actions
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertActions(Actions actions) throws DataAccessException;

	/**
	 * This method will update the shallow Actions data into the database.
	 * 
	 * @param actions
	 *            the actions
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateActions(Actions actions) throws DataAccessException;

	/**
	 * This method fetches all rows from Actions table,.
	 * 
	 * @return Actions list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<Actions> getActions() throws DataAccessException;

	/**
	 * Gets the actions by id.
	 * 
	 * @param actionsId
	 *            the actions id
	 * @return the actions by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	Actions getActionsById(int actionsId) throws DataAccessException;

	/**
	 * Gets the actions id by name.
	 * 
	 * @param actionName
	 *            the action name
	 * @return the actions id by name
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getActionsIdByName(String actionName) throws DataAccessException;

}
