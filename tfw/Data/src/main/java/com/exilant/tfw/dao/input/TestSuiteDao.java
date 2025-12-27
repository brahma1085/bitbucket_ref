package com.exilant.tfw.dao.input;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.def.TestSuiteUI;
import com.exilant.tfw.pojo.input.TestSuite;

/**
 * The Interface TestSuiteDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides TestSuite records by interacting with the database
 */
public interface TestSuiteDao {

	/**
	 * Insert test suite get key.
	 * 
	 * @param testSuite
	 *            the test suite
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertTestSuiteGetKey(TestSuite testSuite) throws DataAccessException;

	/**
	 * Insert test suite.
	 * 
	 * @param testSuite
	 *            the test suite
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertTestSuite(TestSuite testSuite) throws DataAccessException;

	/**
	 * Update test suite.
	 * 
	 * @param testSuite
	 *            the test suite
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTestSuite(TestSuite testSuite) throws DataAccessException;

	/**
	 * This method fetches all rows from TestSuite table,.
	 * 
	 * @return TestSuite list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestSuite> getTestSuite() throws DataAccessException;

	/**
	 * Gets the test suite by id.
	 * 
	 * @param testSuiteId
	 *            the test suite id
	 * @return the test suite by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	TestSuite getTestSuiteById(int testSuiteId) throws DataAccessException;
	
	TestSuite getTestSuiteByName(String testSuiteName) throws DataAccessException;

	/**
	 * Gets the test suite id by name by app id.
	 * 
	 * @param appID
	 *            the app id
	 * @param suiteName
	 *            the suite name
	 * @return the test suite id by name by app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestSuiteIdByNameByAppId(int appID, String suiteName) throws DataAccessException;

	/**
	 * Gets the test suites by plan id.
	 * 
	 * @param testPlanId
	 *            the test plan id
	 * @return the test suites by plan id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestSuite> getTestSuitesByPlanId(int testPlanId) throws DataAccessException;

	/**
	 * Gets the test suite filter by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the test suite filter by app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<String> getTestSuiteFilterByAppId(int appId) throws DataAccessException;

	/**
	 * Gets the all test suites.
	 * 
	 * @return the all test suites
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestSuite> getAllTestSuites() throws DataAccessException;

	/**
	 * Gets the test suite names by test suite id.
	 * 
	 * @param testSuiteId
	 *            the test suite id
	 * @return the test suite names by test suite id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestSuite> getTestSuiteNamesByTestSuiteId(int testSuiteId) throws DataAccessException;

	/**
	 * Update test suite data.
	 * 
	 * @param testSuite
	 *            the test suite
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTestSuiteData(TestSuite testSuite) throws DataAccessException;

	/**
	 * Gets the test suite by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the test suite by app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestSuite> getTestSuiteByAppId(int appId) throws DataAccessException;

	List<TestSuiteUI> getTestSuiteUIByAppID(int appId) throws DataAccessException;
	
	List<TestSuite> getTestSuitesForFlowChart(int testPlanId) throws DataAccessException;
}