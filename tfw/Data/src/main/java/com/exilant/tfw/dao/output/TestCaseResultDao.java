package com.exilant.tfw.dao.output;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.output.TestCaseResult;

/**
 * The Interface TestCaseResultDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides TestCaseResult records by interacting with the
 * database
 */
public interface TestCaseResultDao {

	/**
	 * Insert test case result get key.
	 * 
	 * @param testCaseResult
	 *            the test case result
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertTestCaseResultGetKey(TestCaseResult testCaseResult) throws DataAccessException;

	/**
	 * Insert test case result.
	 * 
	 * @param testCaseResult
	 *            the test case result
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertTestCaseResult(TestCaseResult testCaseResult) throws DataAccessException;

	/**
	 * Update test case result.
	 * 
	 * @param testCaseResult
	 *            the test case result
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTestCaseResult(TestCaseResult testCaseResult) throws DataAccessException;

	/**
	 * This method fetches all rows from TestCaseResult table,.
	 * 
	 * @return TestCaseResult list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestCaseResult> getTestCaseResult() throws DataAccessException;

	/**
	 * Gets the test case result by id.
	 * 
	 * @param testCaseResultId
	 *            the test case result id
	 * @return the test case result by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	TestCaseResult getTestCaseResultById(int testCaseResultId) throws DataAccessException;

}
