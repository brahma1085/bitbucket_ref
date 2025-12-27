package com.sssoft.isatt.data.dao.output;

import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.pojo.output.TestScenarioResult;

/**
 * This interface fetch TestScenario Results from database.
 */
public interface TestScenarioResultDao {

	/**
	 * Insert test scenario result get key.
	 * 
	 * @param testScenarioResult
	 *            the test scenario result
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertTestScenarioResultGetKey(TestScenarioResult testScenarioResult) throws DataAccessException;

	/**
	 * Insert test scenario result.
	 * 
	 * @param testScenarioResult
	 *            the test scenario result
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertTestScenarioResult(TestScenarioResult testScenarioResult) throws DataAccessException;

	/**
	 * Update test scenario result.
	 * 
	 * @param testScenarioResult
	 *            the test scenario result
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTestScenarioResult(TestScenarioResult testScenarioResult) throws DataAccessException;

	/**
	 * Gets the test scenario result by suite result id.
	 * 
	 * @param testSuiteResultId
	 *            the test suite result id
	 * @return the test scenario result by suite result id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestScenarioResult> getTestScenarioResultBySuiteResultId(int testSuiteResultId) throws DataAccessException;

	/**
	 * Gets the test scenario result by scheduler result id.
	 * 
	 * @param schedulerResultId
	 *            the scheduler result id
	 * @return the test scenario result by scheduler result id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestScenarioResult> getTestScenarioResultBySchedulerResultId(int schedulerResultId) throws DataAccessException;

}
