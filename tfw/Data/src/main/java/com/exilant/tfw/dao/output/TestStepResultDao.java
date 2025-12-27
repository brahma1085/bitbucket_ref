package com.exilant.tfw.dao.output;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.output.TestStepResult;

/**
 * The Interface TestStepResultDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides TestStepResult records by interacting with the
 * database
 */
public interface TestStepResultDao {

	/**
	 * Insert test step result get key.
	 * 
	 * @param testStepResult
	 *            the test step result
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertTestStepResultGetKey(TestStepResult testStepResult) throws DataAccessException;

	/**
	 * Insert test step result.
	 * 
	 * @param testStepResult
	 *            the test step result
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertTestStepResult(TestStepResult testStepResult) throws DataAccessException;

	/**
	 * Update test step result.
	 * 
	 * @param testStepResult
	 *            the test step result
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTestStepResult(TestStepResult testStepResult) throws DataAccessException;

	/**
	 * This method fetches all rows from TestStepResult table,.
	 * 
	 * @return TestStepResult list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestStepResult> getTestStepResult() throws DataAccessException;

	/**
	 * Gets the test step result by id.
	 * 
	 * @param testStepResultId
	 *            the test step result id
	 * @return the test step result by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	TestStepResult getTestStepResultById(int testStepResultId) throws DataAccessException;

}
