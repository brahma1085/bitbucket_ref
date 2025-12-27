package com.sssoft.isatt.data.dao.output;

import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.pojo.output.TestSuiteResult;

/**
 * The Interface TestSuiteResultDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides TestSuiteResult records by interacting with the
 * database
 */
public interface TestSuiteResultDao {

	/**
	 * Insert test suite result get key.
	 * 
	 * @param testSuiteResult
	 *            the test suite result
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertTestSuiteResultGetKey(TestSuiteResult testSuiteResult) throws DataAccessException;

	/**
	 * Insert test suite result.
	 * 
	 * @param testSuiteResult
	 *            the test suite result
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertTestSuiteResult(TestSuiteResult testSuiteResult) throws DataAccessException;

	/**
	 * Update test suite result.
	 * 
	 * @param testSuiteResult
	 *            the test suite result
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTestSuiteResult(TestSuiteResult testSuiteResult) throws DataAccessException;

	/**
	 * This method fetches all rows from TestSuiteResult table,.
	 * 
	 * @return TestSuiteResult list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestSuiteResult> getTestSuiteResult() throws DataAccessException;

	/**
	 * Gets the test suite result by id.
	 * 
	 * @param testSuiteResultId
	 *            the test suite result id
	 * @return the test suite result by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	TestSuiteResult getTestSuiteResultById(int testSuiteResultId) throws DataAccessException;

}
