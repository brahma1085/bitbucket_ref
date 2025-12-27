package com.exilant.tfw.dao.input;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.input.ParamGroup;

/**
 * The Interface ParamGroupDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides ParamGroup records by interacting with the database
 */
public interface ParamGroupDao {

	/**
	 * Insert param group get key.
	 * 
	 * @param paramGroup
	 *            the param group
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertParamGroupGetKey(ParamGroup paramGroup) throws DataAccessException;

	/**
	 * Insert param group.
	 * 
	 * @param paramGroup
	 *            the param group
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertParamGroup(ParamGroup paramGroup) throws DataAccessException;

	/**
	 * Update param group.
	 * 
	 * @param paramGroup
	 *            the param group
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateParamGroup(ParamGroup paramGroup) throws DataAccessException;

	/**
	 * This method fetches all rows from ParamGroup table,.
	 * 
	 * @return ParamGroup list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<ParamGroup> getParamGroup() throws DataAccessException;

	/**
	 * Gets the param group by id.
	 * 
	 * @param paramGroupId
	 *            the param group id
	 * @return the param group by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	ParamGroup getParamGroupById(int paramGroupId) throws DataAccessException;

	/**
	 * Gets the param group by object group id.
	 * 
	 * @param objectGroupId
	 *            the object group id
	 * @return the param group by object group id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	ParamGroup getParamGroupByObjectGroupId(int objectGroupId) throws DataAccessException;

	/**
	 * Gets the param group id by name by object grp id.
	 * 
	 * @param paramGroupName
	 *            the param group name
	 * @param objectGroupID
	 *            the object group id
	 * @return the param group id by name by object grp id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getParamGroupIdByNameByObjectGrpId(String paramGroupName, int objectGroupID) throws DataAccessException;

	/**
	 * Gets the param group names by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the param group names by app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<ParamGroup> getParamGroupNamesByAppId(int appId) throws DataAccessException;

	/**
	 * Gets the param group by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the param group by app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<ParamGroup> getParamGroupByAppId(int appId) throws DataAccessException;

	/**
	 * Update param group data.
	 * 
	 * @param paramGroup
	 *            the param group
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateParamGroupData(ParamGroup paramGroup) throws DataAccessException;

}
