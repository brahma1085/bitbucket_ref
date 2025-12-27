package com.exilant.tfw.dao.input;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.input.Task;

/**
 * The Interface TaskDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides Task records by interacting with the database
 */
public interface TaskDao {

	/**
	 * Insert task get key.
	 * 
	 * @param task
	 *            the task
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertTaskGetKey(Task task) throws DataAccessException;

	/**
	 * Insert task.
	 * 
	 * @param task
	 *            the task
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertTask(Task task) throws DataAccessException;

	/**
	 * Update task.
	 * 
	 * @param task
	 *            the task
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTask(Task task) throws DataAccessException;

	/**
	 * This method fetches all rows from Task table,.
	 * 
	 * @return Task list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<Task> getTask() throws DataAccessException;

	/**
	 * Gets the task by id.
	 * 
	 * @param taskId
	 *            the task id
	 * @return the task by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	Task getTaskById(int taskId) throws DataAccessException;
}
