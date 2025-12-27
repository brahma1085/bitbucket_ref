package com.exilant.tfw.dao.output;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.output.TestPlanResult;

/**
 * The Interface TestPlanResultDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides TestPlanResult records by interacting with the
 * database
 */
public interface TestPlanResultDao {

	/**
	 * Insert test plan result get key.
	 * 
	 * @param testPlanResult
	 *            the test plan result
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertTestPlanResultGetKey(TestPlanResult testPlanResult) throws DataAccessException;

	/**
	 * Insert test plan result.
	 * 
	 * @param testPlanResult
	 *            the test plan result
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertTestPlanResult(TestPlanResult testPlanResult) throws DataAccessException;

	/**
	 * Update test plan result.
	 * 
	 * @param testPlanResult
	 *            the test plan result
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTestPlanResult(TestPlanResult testPlanResult) throws DataAccessException;

	/**
	 * This method fetches all rows from TestPlanResult table,.
	 * 
	 * @return TestPlanResult list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestPlanResult> getTestPlanResult() throws DataAccessException;

	/**
	 * Gets the test plan result by id.
	 * 
	 * @param testPlanResultId
	 *            the test plan result id
	 * @return the test plan result by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	TestPlanResult getTestPlanResultById(int testPlanResultId) throws DataAccessException;

}
