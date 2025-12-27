package com.sssoft.isatt.data.dao;

import java.sql.Timestamp;
import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.pojo.Scheduler;
import com.sssoft.isatt.data.pojo.def.DataSets;
import com.sssoft.isatt.data.pojo.def.ScheduledJobs;
import com.sssoft.isatt.data.pojo.input.TestPlan;

/**
 * The Interface SchedulerDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides Scheduler records by interacting with the database
 */
public interface SchedulerDao {

	/**
	 * Insert scheduler get key.
	 *
	 * @param scheduler the scheduler
	 * @return the int
	 * @throws DataAccessException the data access exception
	 */
	int insertSchedulerGetKey(Scheduler scheduler) throws DataAccessException;

	/**
	 * Insert scheduler.
	 *
	 * @param scheduler the scheduler
	 * @return number of rows inserted
	 * @throws DataAccessException the data access exception
	 */
	long insertScheduler(Scheduler scheduler) throws DataAccessException;

	/**
	 * Update scheduler.
	 *
	 * @param scheduler the scheduler
	 * @return number of rows updated
	 * @throws DataAccessException the data access exception
	 */
	long updateScheduler(Scheduler scheduler) throws DataAccessException;

	/**
	 * This method fetches all rows from Scheduler table,.
	 *
	 * @return Scheduler list
	 * @throws DataAccessException the data access exception
	 */
	List<Scheduler> getScheduler() throws DataAccessException;

	/**
	 * Gets the scheduler by id.
	 *
	 * @param schedulerId the scheduler id
	 * @return the scheduler by id
	 * @throws DataAccessException the data access exception
	 */
	Scheduler getSchedulerById(int schedulerId) throws DataAccessException;

	/**
	 * Update scheduler time.
	 *
	 * @param scheduler the scheduler
	 * @return the long
	 * @throws DataAccessException the data access exception
	 */
	long updateSchedulerTime(Scheduler scheduler) throws DataAccessException;

	/**
	 * Fetch scheduler by time.
	 *
	 * @param scheduledTime the scheduled time
	 * @return the list
	 * @throws DataAccessException the data access exception
	 */
	List<Scheduler> fetchSchedulerByTime(Timestamp scheduledTime) throws DataAccessException;

	/**
	 * Gets the scheduler by app id.
	 *
	 * @param appId the app id
	 * @return the scheduler by app id
	 * @throws DataAccessException the data access exception
	 */
	Scheduler getSchedulerByAppId(int appId) throws DataAccessException;

	/**
	 * Gets the scheduler list by app id.
	 *
	 * @param appId the app id
	 * @return the scheduler list by app id
	 * @throws DataAccessException the data access exception
	 */
	List<TestPlan> getSchedulerListByAppId(int appId) throws DataAccessException;

	/**
	 * Gets the data sets by scheduler id by app id.
	 *
	 * @param appId the app id
	 * @return the data sets by scheduler id by app id
	 * @throws DataAccessException the data access exception
	 */
	List<DataSets> getDataSetsBySchedulerIdByAppId(int appId) throws DataAccessException;

	/**
	 * Gets the scheduler filter by app id.
	 *
	 * @param appId the app id
	 * @return the scheduler filter by app id
	 * @throws DataAccessException the data access exception
	 */
	List<Scheduler> getSchedulerFilterByAppID(int appId) throws DataAccessException;

	/**
	 * Update scheduler status.
	 *
	 * @param scheduler the scheduler
	 * @return the long
	 * @throws DataAccessException the data access exception
	 */
	long updateSchedulerStatus(Scheduler scheduler) throws DataAccessException;

	/**
	 * Update scheduler failure count with time.
	 *
	 * @param scheduler the scheduler
	 * @return the long
	 * @throws DataAccessException the data access exception
	 */
	long updateSchedulerFailureCountWithTime(Scheduler scheduler) throws DataAccessException;

	/**
	 * Gets the plan names by schedule id.
	 *
	 * @param scheduleIds the schedule ids
	 * @return the plan names by schedule id
	 * @throws DataAccessException the data access exception
	 */
	List<DataSets> getPlanNamesByScheduleId(List<Integer> scheduleIds) throws DataAccessException;

	/**
	 * Delete scheduler by id.
	 *
	 * @param schedulerId the scheduler id
	 * @return the long
	 * @throws DataAccessException the data access exception
	 */
	long deleteSchedulerById(int schedulerId) throws DataAccessException;

	/**
	 * Gets the scheduler by status.
	 *
	 * @return the scheduler by status
	 * @throws DataAccessException the data access exception
	 */
	List<Scheduler> getSchedulerByStatus() throws DataAccessException;

	/**
	 * Gets the scheduler by id status fail count.
	 *
	 * @param schedulerId the scheduler id
	 * @return the scheduler by id status fail count
	 * @throws DataAccessException the data access exception
	 */
	Scheduler getSchedulerByIdStatusFailCount(int schedulerId) throws DataAccessException;
	
	/**
	 * Gets the scheduled jobs for app.
	 *
	 * @param appId the app id
	 * @return the scheduled jobs for app
	 * @throws DataAccessException the data access exception
	 */
	List<ScheduledJobs> getScheduledJobsForApp(int appId) throws DataAccessException;
	
	/**
	 * Gets the scheduled running jobs for app.
	 *
	 * @param appId the app id
	 * @return the scheduled running jobs for app
	 * @throws DataAccessException the data access exception
	 */
	List<ScheduledJobs> getScheduledRunningJobsForApp(int appId) throws DataAccessException;
	
	/**
	 * Gets the scheduled not run jobs for app.
	 *
	 * @param appId the app id
	 * @return the scheduled not run jobs for app
	 * @throws DataAccessException the data access exception
	 */
	List<ScheduledJobs> getScheduledNotRunJobsForApp(int appId) throws DataAccessException;
	
	/**
	 * Gets the scheduled completed jobs for app.
	 *
	 * @param appId the app id
	 * @return the scheduled completed jobs for app
	 * @throws DataAccessException the data access exception
	 */
	List<ScheduledJobs> getScheduledCompletedJobsForApp(int appId) throws DataAccessException;
}
