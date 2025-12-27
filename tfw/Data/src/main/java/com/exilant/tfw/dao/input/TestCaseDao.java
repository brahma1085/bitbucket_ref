package com.exilant.tfw.dao.input;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.AppFeature;
import com.exilant.tfw.pojo.AppFunctionality;
import com.exilant.tfw.pojo.def.TestCaseUI;
import com.exilant.tfw.pojo.input.TestCase;

/**
 * The Interface TestCaseDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides TestCase records by interacting with the database
 */
public interface TestCaseDao {

	/**
	 * Insert test case get key.
	 * 
	 * @param testCase
	 *            the test case
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertTestCaseGetKey(TestCase testCase) throws DataAccessException;

	/**
	 * Insert test case.
	 * 
	 * @param testCase
	 *            the test case
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertTestCase(TestCase testCase) throws DataAccessException;

	/**
	 * Update test case.
	 * 
	 * @param testCase
	 *            the test case
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTestCase(TestCase testCase) throws DataAccessException;

	/**
	 * This method fetches all rows from TestCase table,.
	 * 
	 * @return TestCase list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestCase> getTestCase() throws DataAccessException;

	/**
	 * Gets the test case by test scenario id.
	 * 
	 * @param testScenarioId
	 *            the test scenario id
	 * @return the test case by test scenario id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestCase> getTestCaseByTestScenarioId(int testScenarioId) throws DataAccessException;

	/**
	 * Gets the test case by id.
	 * 
	 * @param testCaseId
	 *            the test case id
	 * @return the test case by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	TestCase getTestCaseById(int testCaseId) throws DataAccessException;

	TestCase getTestCaseByName(String testCaseName) throws DataAccessException;

	/**
	 * Gets the test case id by test scenario id.
	 * 
	 * @param testScenarioId
	 *            the test scenario id
	 * @return the test case id by test scenario id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestCaseIdByTestScenarioId(int testScenarioId) throws DataAccessException;

	/**
	 * Gets the test case by id by name.
	 * 
	 * @param caseName
	 *            the case name
	 * @return the test case by id by name
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestCaseByIdByName(String caseName) throws DataAccessException;

	/**
	 * Gets the test case by id by name.
	 * 
	 * @param caseName
	 *            the case name
	 * @param testScenarioID
	 *            the test scenario id
	 * @return the test case by id by name
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<Integer> getTestCaseByIdByName(String caseName, int testScenarioID) throws DataAccessException;

	/**
	 * Gets the test case by id by name and scenario id.
	 * 
	 * @param caseName
	 *            the case name
	 * @param testScenarioID
	 *            the test scenario id
	 * @return the test case by id by name and scenario id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestCaseByIdByNameAndScenarioID(String caseName, int testScenarioID) throws DataAccessException;

	/**
	 * Gets the test case id by name by feature id by classification tag.
	 * 
	 * @param testCase
	 *            the test case
	 * @return the test case id by name by feature id by classification tag
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestCaseIdByNameByFeatureIdByClassificationTag(TestCase testCase) throws DataAccessException;

	/**
	 * Gets the test case id by name by feature id by classification tag by cg
	 * id.
	 * 
	 * @param testCase
	 *            the test case
	 * @return the test case id by name by feature id by classification tag by
	 *         cg id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestCaseIdByNameByFeatureIdByClassificationTagByCgId(TestCase testCase) throws DataAccessException;

	/**
	 * Gets the all function names by test case id.
	 * 
	 * @param testCaseId
	 *            the test case id
	 * @return the all function names by test case id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<AppFunctionality> getAllFunctionNamesByTestCaseId(int testCaseId) throws DataAccessException;

	/**
	 * Gets the all feature names by test case id.
	 * 
	 * @param testCaseId
	 *            the test case id
	 * @return the all feature names by test case id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<AppFeature> getAllFeatureNamesByTestCaseId(int testCaseId) throws DataAccessException;

	/**
	 * Gets the all test cases by test scenario id.
	 * 
	 * @param testScenarioId
	 *            the test scenario id
	 * @return the all test cases by test scenario id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestCase> getAllTestCasesByTestScenarioId(int testScenarioId) throws DataAccessException;

	List<TestCaseUI> getTestCaseUIByAppID(int testCaseId) throws DataAccessException;

	List<TestCase> getTestCasesForFlowChart(int scenarioId) throws DataAccessException;

	List<Integer> getTestCaseIdByName(String testCaseName) throws DataAccessException;

}
