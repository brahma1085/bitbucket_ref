package com.exilant.tfw.dao;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.SchedulerRunDetails;

/**
 * The Interface SchedulerRunDetailsDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides SchedulerRunDetails records by interacting with the
 * database
 */
public interface SchedulerRunDetailsDao {

	/**
	 * Insert scheduler run details get key.
	 *
	 * @param schedulerRunDetails the scheduler run details
	 * @return the int
	 * @throws DataAccessException the data access exception
	 */
	int insertSchedulerRunDetailsGetKey(SchedulerRunDetails schedulerRunDetails) throws DataAccessException;

	/**
	 * Insert scheduler run details.
	 *
	 * @param schedulerRunDetails the scheduler run details
	 * @return number of rows inserted
	 * @throws DataAccessException the data access exception
	 */
	long insertSchedulerRunDetails(SchedulerRunDetails schedulerRunDetails) throws DataAccessException;

	/**
	 * Update scheduler run details.
	 *
	 * @param schedulerRunDetails the scheduler run details
	 * @return number of rows updated
	 * @throws DataAccessException the data access exception
	 */
	long updateSchedulerRunDetails(SchedulerRunDetails schedulerRunDetails) throws DataAccessException;

	/**
	 * This method fetches all rows from SchedulerRunDetails table,.
	 *
	 * @return SchedulerRunDetails list
	 * @throws DataAccessException the data access exception
	 */
	List<SchedulerRunDetails> getSchedulerRunDetails() throws DataAccessException;

	/**
	 * Gets the scheduler run details by id.
	 *
	 * @param testRunId the test run id
	 * @return the scheduler run details by id
	 * @throws DataAccessException the data access exception
	 */
	List<SchedulerRunDetails> getSchedulerRunDetailsById(int testRunId,String type) throws DataAccessException;

	/**
	 * Gets the all scheduler run details.
	 *
	 * @return the all scheduler run details
	 * @throws DataAccessException the data access exception
	 */
	List<SchedulerRunDetails> getAllSchedulerRunDetails() throws DataAccessException;

}
