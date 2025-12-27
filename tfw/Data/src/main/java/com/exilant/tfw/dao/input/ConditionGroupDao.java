package com.exilant.tfw.dao.input;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.input.ConditionGroup;

/**
 * The Interface ConditionGroupDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides ConditionGroup records by interacting with the
 * database
 */
public interface ConditionGroupDao {

	/**
	 * Insert condition group get key.
	 * 
	 * @param conditionGroup
	 *            the condition group
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertConditionGroupGetKey(ConditionGroup conditionGroup) throws DataAccessException;

	/**
	 * Insert condition group.
	 * 
	 * @param conditionGroup
	 *            the condition group
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertConditionGroup(ConditionGroup conditionGroup) throws DataAccessException;

	/**
	 * Update condition group.
	 * 
	 * @param conditionGroup
	 *            the condition group
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateConditionGroup(ConditionGroup conditionGroup) throws DataAccessException;

	/**
	 * This method fetches all rows from ConditionGroup table,.
	 * 
	 * @return ConditionGroup list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<ConditionGroup> getConditionGroup() throws DataAccessException;

	/**
	 * Gets the condition group by id.
	 * 
	 * @param conditionGroupId
	 *            the condition group id
	 * @return the condition group by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	ConditionGroup getConditionGroupById(int conditionGroupId) throws DataAccessException;

	/**
	 * Gets the condition group id by name by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @param cgName
	 *            the cg name
	 * @return the condition group id by name by app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getConditionGroupIdByNameByAppId(int appId, String cgName) throws DataAccessException;

	/**
	 * Gets the condition group names by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the condition group names by app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<ConditionGroup> getConditionGroupNamesByAppId(int appId) throws DataAccessException;

	/**
	 * Update condition group data.
	 * 
	 * @param conditionGroup
	 *            the condition group
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateConditionGroupData(ConditionGroup conditionGroup) throws DataAccessException;

}
