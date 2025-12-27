package com.exilant.tfw.dao.output;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.output.TaskResult;

/**
 * The Interface TaskResultDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides TaskResult records by interacting with the database
 */
public interface TaskResultDao {

	/**
	 * Insert task result get key.
	 * 
	 * @param taskResult
	 *            the task result
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertTaskResultGetKey(TaskResult taskResult) throws DataAccessException;

	/**
	 * Insert task result.
	 * 
	 * @param taskResult
	 *            the task result
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertTaskResult(TaskResult taskResult) throws DataAccessException;

	/**
	 * Update task result.
	 * 
	 * @param taskResult
	 *            the task result
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTaskResult(TaskResult taskResult) throws DataAccessException;

	/**
	 * This method fetches all rows from TaskResult table,.
	 * 
	 * @return TaskResult list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TaskResult> getTaskResult() throws DataAccessException;

	/**
	 * Gets the task result by id.
	 * 
	 * @param taskResultId
	 *            the task result id
	 * @return the task result by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	TaskResult getTaskResultById(int taskResultId) throws DataAccessException;

}
