package com.sssoft.isatt.data.dao;

import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.pojo.AgentDetails;

/**
 * The Interface AgentDetailsDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides AgentDetails records by interacting with the database
 */
public interface AgentDetailsDao {

	/**
	 * Insert agent details get key.
	 *
	 * @param agentDetails the agent details
	 * @return the int
	 * @throws DataAccessException the data access exception
	 */
	int insertAgentDetailsGetKey(AgentDetails agentDetails) throws DataAccessException;

	/**
	 * This method will insert the shallow AgentDetails data into the database.
	 *
	 * @param agentDetails the agent details
	 * @return number of rows inserted
	 * @throws DataAccessException the data access exception
	 */
	long insertAgentDetails(AgentDetails agentDetails) throws DataAccessException;

	/**
	 * This method will udpate the shallow AgentDetails data into the database.
	 *
	 * @param agentDetails the agent details
	 * @return number of rows updated
	 * @throws DataAccessException the data access exception
	 */
	long updateAgentDetails(AgentDetails agentDetails) throws DataAccessException;

	/**
	 * This method fetches all rows from AgentDetails table,.
	 *
	 * @return AgentDetails list
	 * @throws DataAccessException the data access exception
	 */
	List<AgentDetails> getAgentDetails() throws DataAccessException;

	/**
	 * Gets the agent details by id.
	 *
	 * @param agentDetailsId the agent details id
	 * @return the agent details by id
	 * @throws DataAccessException the data access exception
	 */
	AgentDetails getAgentDetailsById(int agentDetailsId) throws DataAccessException;
}
