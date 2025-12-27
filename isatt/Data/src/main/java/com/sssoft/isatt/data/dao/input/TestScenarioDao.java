package com.sssoft.isatt.data.dao.input;

import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.pojo.def.TestScenarioUI;
import com.sssoft.isatt.data.pojo.input.TestScenario;

/**
 * The Interface TestScenarioDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides TestScenario records by interacting with the database
 */
public interface TestScenarioDao {

	/**
	 * Insert test scenario get key.
	 * 
	 * @param testScenario
	 *            the test scenario
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertTestScenarioGetKey(TestScenario testScenario) throws DataAccessException;

	/**
	 * Insert test scenario.
	 * 
	 * @param testScenario
	 *            the test scenario
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertTestScenario(TestScenario testScenario) throws DataAccessException;

	/**
	 * Update test scenario.
	 * 
	 * @param testScenario
	 *            the test scenario
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTestScenario(TestScenario testScenario) throws DataAccessException;

	/**
	 * Gets the test scenario by suite id.
	 * 
	 * @param suiteId
	 *            the suite id
	 * @return the test scenario by suite id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestScenario> getTestScenarioBySuiteId(int suiteId) throws DataAccessException;

	/**
	 * This method fetches all rows from TestScenario table,.
	 * 
	 * @return TestScenario list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestScenario> getTestScenario() throws DataAccessException;

	/**
	 * Gets the test scenario by id.
	 * 
	 * @param testScenarioId
	 *            the test scenario id
	 * @return the test scenario by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	TestScenario getTestScenarioById(int testScenarioId) throws DataAccessException;

	TestScenario getTestScenarioByName(String testScenarioName) throws DataAccessException;

	/**
	 * Gets the test scenario id.
	 * 
	 * @param appID
	 *            the app id
	 * @param testScenarioName
	 *            the test scenario name
	 * @return the test scenario id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestScenarioId(int appID, String testScenarioName) throws DataAccessException;

	/**
	 * Gets the test suite filter bytest scenario id.
	 * 
	 * @param testScenarioId
	 *            the test scenario id
	 * @return the test suite filter bytest scenario id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<String> getTestSuiteFilterBytestScenarioId(int testScenarioId) throws DataAccessException;

	/**
	 * Gets the test scenario id by name and suite id.
	 * 
	 * @param scenarioName
	 *            the scenario name
	 * @param testSuiteID
	 *            the test suite id
	 * @return the test scenario id by name and suite id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestScenarioIdByNameAndSuiteID(String scenarioName, int testSuiteID) throws DataAccessException;

	/**
	 * Update test scenario data.
	 * 
	 * @param testScenario
	 *            the test scenario
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTestScenarioData(TestScenario testScenario) throws DataAccessException;

	/**
	 * Gets the test scenario by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the test scenario by app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestScenario> getTestScenarioByAppId(int appId) throws DataAccessException;

	/**
	 * Gets the test scenario id by name.
	 * 
	 * @param scenarioName
	 *            the scenario name
	 * @return the test scenario id by name
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestScenarioIdByName(String scenarioName) throws DataAccessException;

	List<TestScenarioUI> getTestScenarioUIByAppID(int appId) throws DataAccessException;

	List<TestScenario> getTestScenariosForFlowChart(int suiteId) throws DataAccessException;

}