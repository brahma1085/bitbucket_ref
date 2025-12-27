package com.sssoft.isatt.data.dao.input;

import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.pojo.input.TestPlan;

/**
 * The Interface TestPlanDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides TestPlan records by interacting with the database
 */
public interface TestPlanDao {

	/**
	 * Insert test plan get key.
	 * 
	 * @param testPlan
	 *            the test plan
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertTestPlanGetKey(TestPlan testPlan) throws DataAccessException;

	/**
	 * Insert test plan.
	 * 
	 * @param testPlan
	 *            the test plan
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertTestPlan(TestPlan testPlan) throws DataAccessException;

	/**
	 * Update test plan.
	 * 
	 * @param testPlan
	 *            the test plan
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTestPlan(TestPlan testPlan) throws DataAccessException;

	/**
	 * This method fetches all rows from TestPlan table,.
	 * 
	 * @return TestPlan list
	 * @throws DataAccessException
	 *             the data access exception
	 */

	List<TestPlan> getTestPlan() throws DataAccessException;

	/**
	 * Gets the test plan by id.
	 * 
	 * @param testPlanId
	 *            the test plan id
	 * @return the test plan by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	TestPlan getTestPlanById(int testPlanId) throws DataAccessException;
	
	TestPlan getTestPlanByName(String testPlanName) throws DataAccessException;

	/**
	 * Gets the test plan by app id.
	 * 
	 * @param appID
	 *            the app id
	 * @return the test plan by app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestPlanByAppId(int appID) throws DataAccessException;

	/**
	 * Gets the all test plans.
	 * 
	 * @return the all test plans
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestPlan> getAllTestPlans() throws DataAccessException;

	/**
	 * Gets the test plan name filter by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the test plan name filter by app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestPlan> getTestPlanNameFilterByAppId(int appId) throws DataAccessException;

	/**
	 * Gets the test plan by name and app id.
	 * 
	 * @param planName
	 *            the plan name
	 * @param appID
	 *            the app id
	 * @return the test plan by name and app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestPlanByNameAndAppId(String planName, int appID) throws DataAccessException;

	/**
	 * Update test plan for excel.
	 * 
	 * @param testPlan
	 *            the test plan
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTestPlanForExcel(TestPlan testPlan) throws DataAccessException;

	/**
	 * Update test plan data.
	 * 
	 * @param testPlan
	 *            the test plan
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTestPlanData(TestPlan testPlan) throws DataAccessException;

	/**
	 * Gets the test plan obj by app id.
	 * 
	 * @param appID
	 *            the app id
	 * @return the test plan obj by app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestPlan> getTestPlanObjByAppId(int appID) throws DataAccessException;

	/**
	 * Gets the test plan names by test plan id.
	 * 
	 * @param planId
	 *            the plan id
	 * @return the test plan names by test plan id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestPlan> getTestPlanNamesByTestPlanId(int planId) throws DataAccessException;

	/**
	 * Gets the plan name by id.
	 * 
	 * @param testPlanId
	 *            the test plan id
	 * @return the plan name by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	String getPlanNameById(int testPlanId) throws DataAccessException;

}