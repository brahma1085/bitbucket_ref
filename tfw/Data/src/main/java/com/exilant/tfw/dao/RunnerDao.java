package com.exilant.tfw.dao;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.Runner;

/**
 * The Interface RunnerDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides Runner records by interacting with the database
 */
public interface RunnerDao {

	/**
	 * Insert runner get key.
	 * 
	 * @param runner
	 *            the runner
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertRunnerGetKey(Runner runner) throws DataAccessException;

	/**
	 * Insert runner.
	 * 
	 * @param runner
	 *            the runner
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertRunner(Runner runner) throws DataAccessException;

	/**
	 * Update runner.
	 * 
	 * @param runner
	 *            the runner
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateRunner(Runner runner) throws DataAccessException;

	/**
	 * This method fetches all rows from Runner table,.
	 * 
	 * @return Runner list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<Runner> getRunner() throws DataAccessException;

	/**
	 * Gets the runner by id.
	 * 
	 * @param runnerId
	 *            the runner id
	 * @return the runner by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	Runner getRunnerById(int runnerId) throws DataAccessException;

	/**
	 * Gets the runner id from runner name.
	 * 
	 * @param runnerName
	 *            the runner name
	 * @return the runner id from runner name
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getRunnerIDFromRunnerName(String runnerName) throws DataAccessException;

}
