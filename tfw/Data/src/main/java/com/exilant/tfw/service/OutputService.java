package com.exilant.tfw.service;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.SchedulerRunDetails;
import com.exilant.tfw.pojo.input.TestPlan;
import com.exilant.tfw.pojo.output.LaneResults;
import com.exilant.tfw.pojo.output.TaskResult;
import com.exilant.tfw.pojo.output.TestCaseResult;
import com.exilant.tfw.pojo.output.TestPlanResult;
import com.exilant.tfw.pojo.output.TestScenarioResult;
import com.exilant.tfw.pojo.output.TestStepResult;
import com.exilant.tfw.pojo.output.TestSuiteResult;

/**
 * Output Service class.
 */

/**
 * 
 * @author mohammedfirdos
 * 
 */
public interface OutputService {

	/**
	 * Gets the lane results.
	 * 
	 * @return the lane results
	 * @throws ServiceException
	 *             the service exception
	 */
	List<LaneResults> getLaneResults() throws ServiceException;

	/**
	 * Gets the task result.
	 * 
	 * @return the task result
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TaskResult> getTaskResult() throws ServiceException;

	/**
	 * Gets the test case result.
	 * 
	 * @return the test case result
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestCaseResult> getTestCaseResult() throws ServiceException;

	/**
	 * Gets the test plan result.
	 * 
	 * @return the test plan result
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestPlanResult> getTestPlanResult() throws ServiceException;

	/**
	 * Gets the test step result.
	 * 
	 * @return the test step result
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestStepResult> getTestStepResult() throws ServiceException;

	/**
	 * Gets the test suite result.
	 * 
	 * @return the test suite result
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestSuiteResult> getTestSuiteResult() throws ServiceException;

	/**
	 * Gets the scheduler run details.
	 * 
	 * @param testRunId
	 *            the test run id
	 * @return the scheduler run details
	 * @throws ServiceException
	 *             the service exception
	 */
	List<SchedulerRunDetails> getSchedulerRunDetails(int testRunId,String type) throws ServiceException;

	/**
	 * Insert lane results.
	 * 
	 * @param laneResults
	 *            the lane results
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertLaneResults(LaneResults laneResults) throws ServiceException;

	/**
	 * Insert task result.
	 * 
	 * @param taskResult
	 *            the task result
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertTaskResult(TaskResult taskResult) throws ServiceException;

	/**
	 * Insert test case result.
	 * 
	 * @param testCaseResult
	 *            the test case result
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertTestCaseResult(TestCaseResult testCaseResult) throws ServiceException;

	/**
	 * Insert test plan result.
	 * 
	 * @param testPlanResult
	 *            the test plan result
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertTestPlanResult(TestPlanResult testPlanResult) throws ServiceException;

	/**
	 * Insert test step result.
	 * 
	 * @param testStepResult
	 *            the test step result
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertTestStepResult(TestStepResult testStepResult) throws ServiceException;

	/**
	 * Insert test suite result.
	 * 
	 * @param testSuiteResult
	 *            the test suite result
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertTestSuiteResult(TestSuiteResult testSuiteResult) throws ServiceException;

	/**
	 * Insert test scenario result.
	 * 
	 * @param testScenarioResult
	 *            the test scenario result
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertTestScenarioResult(TestScenarioResult testScenarioResult) throws ServiceException;

	/**
	 * Insert test plan result get key.
	 * 
	 * @param testPlanResult
	 *            the test plan result
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertTestPlanResultGetKey(TestPlanResult testPlanResult) throws ServiceException;

	/**
	 * Insert test suite result get key.
	 * 
	 * @param testSuiteResult
	 *            the test suite result
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertTestSuiteResultGetKey(TestSuiteResult testSuiteResult) throws ServiceException;

	/**
	 * Insert test scenario result get key.
	 * 
	 * @param testScenarioResult
	 *            the test scenario result
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertTestScenarioResultGetKey(TestScenarioResult testScenarioResult) throws ServiceException;

	/**
	 * Insert test case result get key.
	 * 
	 * @param testCaseResult
	 *            the test case result
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertTestCaseResultGetKey(TestCaseResult testCaseResult) throws ServiceException;

	/**
	 * Insert test step result get key.
	 * 
	 * @param testStepResult
	 *            the test step result
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertTestStepResultGetKey(TestStepResult testStepResult) throws ServiceException;

	/**
	 * Update test scenario result.
	 * 
	 * @param testScenarioResult
	 *            the test scenario result
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateTestScenarioResult(TestScenarioResult testScenarioResult) throws ServiceException;

	/**
	 * Gets the all scheduler run details.
	 * 
	 * @return the all scheduler run details
	 * @throws ServiceException
	 *             the service exception
	 */
	List<SchedulerRunDetails> getAllSchedulerRunDetails() throws ServiceException;

	/**
	 * Gets the scheduler list by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the scheduler list by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestPlan> getSchedulerListByAppId(int appId) throws ServiceException;

}
