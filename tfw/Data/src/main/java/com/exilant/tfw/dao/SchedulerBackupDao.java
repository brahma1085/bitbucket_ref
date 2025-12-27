package com.exilant.tfw.dao;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.SchedulerBackup;

/**
 * The Interface SchedulerBackupDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides SchedulerBackup records by interacting with the
 * database
 */
public interface SchedulerBackupDao {

	/**
	 * Insert scheduler get key.
	 *
	 * @param schedulerBackup the scheduler backup
	 * @return number of rows inserted
	 * @throws DataAccessException the data access exception
	 */
	int insertSchedulerGetKey(SchedulerBackup schedulerBackup) throws DataAccessException;

	/**
	 * Update scheduler.
	 *
	 * @param schedulerBackup the scheduler backup
	 * @return number of rows updated
	 * @throws DataAccessException the data access exception
	 */
	long updateScheduler(SchedulerBackup schedulerBackup) throws DataAccessException;

	/**
	 * This method fetches all rows from SchedulerBackup table,.
	 *
	 * @return SchedulerBackup list
	 * @throws DataAccessException the data access exception
	 */
	List<SchedulerBackup> getSchedulerBackup() throws DataAccessException;

	/**
	 * Gets the scheduler backup by id.
	 *
	 * @param schedulerBackupId the scheduler backup id
	 * @return the scheduler backup by id
	 * @throws DataAccessException the data access exception
	 */
	SchedulerBackup getSchedulerBackupById(int schedulerBackupId) throws DataAccessException;

}