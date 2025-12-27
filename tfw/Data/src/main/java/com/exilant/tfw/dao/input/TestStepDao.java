package com.exilant.tfw.dao.input;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.input.TestStep;

/**
 * The Interface TestStepDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides TestStep records by interacting with the database
 */
public interface TestStepDao {

	/**
	 * Insert test step get key.
	 * 
	 * @param testStep
	 *            the test step
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertTestStepGetKey(TestStep testStep) throws DataAccessException;

	/**
	 * Insert test step.
	 * 
	 * @param testStep
	 *            the test step
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertTestStep(TestStep testStep) throws DataAccessException;

	/**
	 * Update test step.
	 * 
	 * @param testStep
	 *            the test step
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTestStep(TestStep testStep) throws DataAccessException;

	/**
	 * This method fetches all rows from TestStep table,.
	 * 
	 * @return TestStep list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestStep> getTestStep() throws DataAccessException;

	/**
	 * Gets the test step by id.
	 * 
	 * @param testStepId
	 *            the test step id
	 * @return the test step by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	TestStep getTestStepById(int testStepId) throws DataAccessException;

	/**
	 * Gets the test steps by case id.
	 * 
	 * @param testCaseId
	 *            the test case id
	 * @return the test steps by case id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestStep> getTestStepsByCaseId(int testCaseId) throws DataAccessException;

	/**
	 * Gets the test step id by step name and test case id.
	 * 
	 * @param testStepName
	 *            the test step name
	 * @param testCaseID
	 *            the test case id
	 * @return the test step id by step name and test case id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestStepIDByStepNameAndTestCaseID(String testStepName, int testCaseID) throws DataAccessException;

	/**
	 * Gets the test step ids by dependents.
	 * 
	 * @param testStep
	 *            the test step
	 * @return the test step ids by dependents
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestStepIdsByDependents(TestStep testStep) throws DataAccessException;

	/**
	 * Delete duplicate data.
	 * 
	 * @param startIndexId
	 *            the start index id
	 * @param lastIndexId
	 *            the last index id
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long deleteDuplicateData(int startIndexId, int lastIndexId) throws DataAccessException;

	/**
	 * Gets the all test steps by test case id.
	 * 
	 * @param testCaseId
	 *            the test case id
	 * @return the all test steps by test case id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestStep> getAllTestStepsByTestCaseId(int testCaseId) throws DataAccessException;

	List<TestStep> getTestStepByStepsId(int testStepId)
			throws DataAccessException;

}
