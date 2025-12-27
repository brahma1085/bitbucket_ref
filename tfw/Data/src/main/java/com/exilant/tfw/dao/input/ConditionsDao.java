package com.exilant.tfw.dao.input;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.input.Conditions;

/**
 * The Interface ConditionsDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides ConditionGroup records by interacting with the
 * database
 */
public interface ConditionsDao {

	/**
	 * Insert conditions get key.
	 * 
	 * @param conditions
	 *            the conditions
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertConditionsGetKey(Conditions conditions) throws DataAccessException;

	/**
	 * Insert conditions.
	 * 
	 * @param conditions
	 *            the conditions
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertConditions(Conditions conditions) throws DataAccessException;

	/**
	 * Update conditions.
	 * 
	 * @param conditions
	 *            the conditions
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateConditions(Conditions conditions) throws DataAccessException;

	/**
	 * This method fetches all rows from Conditions table,.
	 * 
	 * @return Conditions list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<Conditions> getConditions() throws DataAccessException;

	/**
	 * Gets the condition by condition group id.
	 * 
	 * @param conditionGroupId
	 *            the condition group id
	 * @return the condition by condition group id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<Conditions> getConditionByConditionGroupId(int conditionGroupId) throws DataAccessException;

	/**
	 * Gets the conditions by id.
	 * 
	 * @param conditionsId
	 *            the conditions id
	 * @return the conditions by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	Conditions getConditionsById(int conditionsId) throws DataAccessException;

	/**
	 * Gets the conditions by condition group id.
	 * 
	 * @param conditionGroupId
	 *            the condition group id
	 * @return the conditions by condition group id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	Conditions getConditionsByConditionGroupId(int conditionGroupId) throws DataAccessException;

	/**
	 * Gets the condition id by name by condition group id.
	 * 
	 * @param conditionName
	 *            the condition name
	 * @param conditionGroupId
	 *            the condition group id
	 * @return the condition id by name by condition group id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getConditionIdByNameByConditionGroupId(String conditionName, int conditionGroupId) throws DataAccessException;

	/**
	 * Update conditions data.
	 * 
	 * @param conditions
	 *            the conditions
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateConditionsData(Conditions conditions) throws DataAccessException;
}
