package com.exilant.tfw.dao;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.SchedulerLaneDetails;

/**
 * The Interface SchedulerLaneDetailsDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides SchedulerLaneDetails records by interacting with the
 * database
 */
public interface SchedulerLaneDetailsDao {

	/**
	 * Insert scheduler lane details get key.
	 *
	 * @param schedulerLaneDetails the scheduler lane details
	 * @return the int
	 * @throws DataAccessException the data access exception
	 */
	int insertSchedulerLaneDetailsGetKey(SchedulerLaneDetails schedulerLaneDetails) throws DataAccessException;

	/**
	 * Insert scheduler lane details.
	 *
	 * @param schedulerLaneDetails the scheduler lane details
	 * @return number of rows inserted
	 * @throws DataAccessException the data access exception
	 */
	long insertSchedulerLaneDetails(SchedulerLaneDetails schedulerLaneDetails) throws DataAccessException;

	/**
	 * Update scheduler lane details.
	 *
	 * @param schedulerLaneDetails the scheduler lane details
	 * @return number of rows updated
	 * @throws DataAccessException the data access exception
	 */
	long updateSchedulerLaneDetails(SchedulerLaneDetails schedulerLaneDetails) throws DataAccessException;

	/**
	 * This method fetches all rows from SchedulerLaneDetails table,.
	 *
	 * @return SchedulerLaneDetails list
	 * @throws DataAccessException the data access exception
	 */
	List<SchedulerLaneDetails> getSchedulerLaneDetails() throws DataAccessException;

	/**
	 * Gets the scheduler lane details by id.
	 *
	 * @param schedulerLaneId the scheduler lane id
	 * @return the scheduler lane details by id
	 * @throws DataAccessException the data access exception
	 */
	SchedulerLaneDetails getSchedulerLaneDetailsById(int schedulerLaneId) throws DataAccessException;
	
	/**
	 * Gets the scheduler lane details by schedule id.
	 *
	 * @param scheduleId the schedule id
	 * @return the scheduler lane details by schedule id
	 * @throws DataAccessException the data access exception
	 */
	List<SchedulerLaneDetails> getSchedulerLaneDetailsByScheduleId(int scheduleId) throws DataAccessException;

	/**
	 * Gets the scheduler lane details by app id.
	 *
	 * @param AppId the app id
	 * @return the scheduler lane details by app id
	 * @throws DataAccessException the data access exception
	 */
	List<SchedulerLaneDetails> getSchedulerLaneDetailsByAppId(int appId) throws DataAccessException;
}
