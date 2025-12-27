package com.sssoft.isatt.data.service;

import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.AppFeature;
import com.sssoft.isatt.data.pojo.AppFunctionality;
import com.sssoft.isatt.data.pojo.def.TestCaseUI;
import com.sssoft.isatt.data.pojo.def.TestScenarioUI;
import com.sssoft.isatt.data.pojo.def.TestSuiteUI;
import com.sssoft.isatt.data.pojo.input.CaseStepMapping;
import com.sssoft.isatt.data.pojo.input.ConditionGroup;
import com.sssoft.isatt.data.pojo.input.Conditions;
import com.sssoft.isatt.data.pojo.input.IdentifierType;
import com.sssoft.isatt.data.pojo.input.ObjectGroup;
import com.sssoft.isatt.data.pojo.input.ObjectType;
import com.sssoft.isatt.data.pojo.input.Objects;
import com.sssoft.isatt.data.pojo.input.Param;
import com.sssoft.isatt.data.pojo.input.ParamGroup;
import com.sssoft.isatt.data.pojo.input.PlanSuiteMapping;
import com.sssoft.isatt.data.pojo.input.ReplacementStrings;
import com.sssoft.isatt.data.pojo.input.ScenarioCaseMapping;
import com.sssoft.isatt.data.pojo.input.SuiteScenarioMapping;
import com.sssoft.isatt.data.pojo.input.Task;
import com.sssoft.isatt.data.pojo.input.TestCase;
import com.sssoft.isatt.data.pojo.input.TestConditionData;
import com.sssoft.isatt.data.pojo.input.TestData;
import com.sssoft.isatt.data.pojo.input.TestParamData;
import com.sssoft.isatt.data.pojo.input.TestPlan;
import com.sssoft.isatt.data.pojo.input.TestScenario;
import com.sssoft.isatt.data.pojo.input.TestStep;
import com.sssoft.isatt.data.pojo.input.TestSuite;

/**
 * Input Service class.
 */

/**
 * 
 * @author mohammedfirdos
 * 
 */
public interface InputService {

	/**
	 * Gets the condition group.
	 * 
	 * @return the condition group
	 * @throws ServiceException
	 *             the service exception
	 */
	List<ConditionGroup> getConditionGroup() throws ServiceException;

	/**
	 * Gets the conditions.
	 * 
	 * @return the conditions
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Conditions> getConditions() throws ServiceException;

	/**
	 * Gets the identifier type.
	 * 
	 * @return the identifier type
	 * @throws ServiceException
	 *             the service exception
	 */
	List<IdentifierType> getIdentifierType() throws ServiceException;

	/**
	 * Gets the object group.
	 * 
	 * @return the object group
	 * @throws ServiceException
	 *             the service exception
	 */
	List<ObjectGroup> getObjectGroup() throws ServiceException;

	/**
	 * Gets the objects.
	 * 
	 * @return the objects
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Objects> getObjects() throws ServiceException;

	/**
	 * Gets the object type.
	 * 
	 * @return the object type
	 * @throws ServiceException
	 *             the service exception
	 */
	List<ObjectType> getObjectType() throws ServiceException;

	/**
	 * Gets the param.
	 * 
	 * @return the param
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Param> getParam() throws ServiceException;

	/**
	 * Gets the param group.
	 * 
	 * @return the param group
	 * @throws ServiceException
	 *             the service exception
	 */
	List<ParamGroup> getParamGroup() throws ServiceException;

	/**
	 * Gets the replacement strings.
	 * 
	 * @return the replacement strings
	 * @throws ServiceException
	 *             the service exception
	 */
	List<ReplacementStrings> getReplacementStrings() throws ServiceException;

	/**
	 * Gets the task.
	 * 
	 * @return the task
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Task> getTask() throws ServiceException;

	/**
	 * Insert test case.
	 * 
	 * @param testCases
	 *            the test cases
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertTestCase(TestCase testCases) throws ServiceException;

	/**
	 * Insert param group get key.
	 * 
	 * @param paramGroup
	 *            the param group
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertParamGroupGetKey(ParamGroup paramGroup) throws ServiceException;

	/**
	 * Insert param get key.
	 * 
	 * @param param
	 *            the param
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertParamGetKey(Param param) throws ServiceException;

	/**
	 * Gets the test case.
	 * 
	 * @return the test case
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestCase> getTestCase() throws ServiceException;

	/**
	 * Gets the test condition data.
	 * 
	 * @return the test condition data
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestConditionData> getTestConditionData() throws ServiceException;

	/**
	 * Gets the test data.
	 * 
	 * @return the test data
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestData> getTestData() throws ServiceException;

	/**
	 * Insert test param data get key.
	 * 
	 * @param testParamData
	 *            the test param data
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertTestParamDataGetKey(TestParamData testParamData) throws ServiceException;

	/**
	 * Gets the test param data.
	 * 
	 * @return the test param data
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestParamData> getTestParamData() throws ServiceException;

	/**
	 * Gets the test plan.
	 * 
	 * @return the test plan
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestPlan> getTestPlan() throws ServiceException;

	/**
	 * Insert test scenario.
	 * 
	 * @param testScenario
	 *            the test scenario
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertTestScenario(TestScenario testScenario) throws ServiceException;

	/**
	 * Gets the test scenario.
	 * 
	 * @return the test scenario
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestScenario> getTestScenario() throws ServiceException;

	/**
	 * Gets the test step.
	 * 
	 * @return the test step
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestStep> getTestStep() throws ServiceException;

	/**
	 * Gets the test suite.
	 * 
	 * @return the test suite
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestSuite> getTestSuite() throws ServiceException;

	/**
	 * Insert plan suite mapping.
	 * 
	 * @param planSuiteMapping
	 *            the plan suite mapping
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertPlanSuiteMapping(PlanSuiteMapping planSuiteMapping) throws ServiceException;
	
	long deletePlanSuiteMappingById(final int planId) throws ServiceException; 
	
	long deletePlanSuiteMappingBySuiteId(final int suiteId) throws ServiceException;

	/**
	 * Insert suite scenario mapping.
	 * 
	 * @param suiteScenarioMapping
	 *            the suite scenario mapping
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertSuiteScenarioMapping(SuiteScenarioMapping suiteScenarioMapping) throws ServiceException;

	/**
	 * Insert scenario case mapping.
	 * 
	 * @param scenarioCaseMapping
	 *            the scenario case mapping
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertScenarioCaseMapping(ScenarioCaseMapping scenarioCaseMapping) throws ServiceException;

	/**
	 * Insert case step mapping.
	 * 
	 * @param caseStepMapping
	 *            the case step mapping
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertCaseStepMapping(CaseStepMapping caseStepMapping) throws ServiceException;

	/**
	 * Insert test plan.
	 * 
	 * @param testPlan
	 *            the test plan
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertTestPlan(TestPlan testPlan) throws ServiceException;

	/**
	 * Insert test suite get key.
	 * 
	 * @param testSuite
	 *            the test suite
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertTestSuiteGetKey(TestSuite testSuite) throws ServiceException;

	/**
	 * Gets the test suite filter bytest scenario id.
	 * 
	 * @param testScenarioId
	 *            the test scenario id
	 * @return the test suite filter bytest scenario id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<String> getTestSuiteFilterBytestScenarioId(int testScenarioId) throws ServiceException;

	/**
	 * Gets the test plan by id.
	 * 
	 * @param testPlanId
	 *            the test plan id
	 * @return the test plan by id
	 * @throws ServiceException
	 *             the service exception
	 */
	TestPlan getTestPlanById(int testPlanId) throws ServiceException;
	
	TestPlan getTestPlanByName(String testPlanName) throws ServiceException;

	/**
	 * Gets the test scenario by id.
	 * 
	 * @param testScenarioId
	 *            the test scenario id
	 * @return the test scenario by id
	 * @throws ServiceException
	 *             the service exception
	 */
	TestScenario getTestScenarioById(int testScenarioId) throws ServiceException;
	
	TestScenario getTestScenarioByName(String testScenarioName) throws ServiceException;

	/**
	 * Update test plan.
	 * 
	 * @param testPlan
	 *            the test plan
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateTestPlan(TestPlan testPlan) throws ServiceException;

	/**
	 * Insert param.
	 * 
	 * @param param
	 *            the param
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertParam(Param param) throws ServiceException;

	/**
	 * Insert app functionality get key.
	 * 
	 * @param appFunctionality
	 *            the app functionality
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertAppFunctionalityGetKey(AppFunctionality appFunctionality) throws ServiceException;

	/**
	 * Gets the all test plans.
	 * 
	 * @return the all test plans
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestPlan> getAllTestPlans() throws ServiceException;

	/**
	 * Gets the all test suite.
	 * 
	 * @return the all test suite
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestSuite> getAllTestSuite() throws ServiceException;

	List<SuiteScenarioMapping> getSuiteScenarioMappingByPlanId(int planId) throws ServiceException;
	
	List<SuiteScenarioMapping> getSuiteScenarioMappingBySuiteId(int suiteId) throws ServiceException;
	
	/**
	 * Gets the test plan name filter by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the test plan name filter by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestPlan> getTestPlanNameFilterByAppId(int appId) throws ServiceException;

	/**
	 * Gets the test data description filter by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the test data description filter by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestData> getTestDataDescriptionFilterByAppId(int appId) throws ServiceException;

	/**
	 * Insert object group get key.
	 * 
	 * @param objGrp
	 *            the obj grp
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertObjectGroupGetKey(ObjectGroup objGrp) throws ServiceException;

	/**
	 * Insert test step.
	 * 
	 * @param testStep
	 *            the test step
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertTestStep(TestStep testStep) throws ServiceException;

	/**
	 * Insert test step get key.
	 * 
	 * @param testStep
	 *            the test step
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertTestStepGetKey(TestStep testStep) throws ServiceException;

	/**
	 * Update test scenario.
	 * 
	 * @param testScenario
	 *            the test scenario
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateTestScenario(TestScenario testScenario) throws ServiceException;

	/**
	 * Insert test data get key.
	 * 
	 * @param testData
	 *            the test data
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertTestDataGetKey(final TestData testData) throws ServiceException;

	/**
	 * Insert test param data.
	 * 
	 * @param testParamData
	 *            the test param data
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertTestParamData(TestParamData testParamData) throws ServiceException;

	/**
	 * Gets the test data by id.
	 * 
	 * @param testDataId
	 *            the test data id
	 * @return the test data by id
	 * @throws ServiceException
	 *             the service exception
	 */
	TestData getTestDataById(int testDataId) throws ServiceException;

	/**
	 * Update test data.
	 * 
	 * @param testData
	 *            the test data
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateTestData(final TestData testData) throws ServiceException;

	/**
	 * Gets the test param data by test data id.
	 * 
	 * @param testDataId
	 *            the test data id
	 * @return the test param data by test data id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestParamData> getTestParamDataByTestDataId(int testDataId) throws ServiceException;

	/**
	 * Gets the all test suites.
	 * 
	 * @return the all test suites
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestSuite> getAllTestSuites() throws ServiceException;

	/**
	 * Gets the test suite by id.
	 * 
	 * @param testSuiteId
	 *            the test suite id
	 * @return the test suite by id
	 * @throws ServiceException
	 *             the service exception
	 */
	TestSuite getTestSuiteById(int testSuiteId) throws ServiceException;
	
	TestSuite getTestSuiteByName(String testSuiteName) throws ServiceException;


	/**
	 * Insert object type get key.
	 * 
	 * @param objType
	 *            the obj type
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertObjectTypeGetKey(ObjectType objType) throws ServiceException;

	/**
	 * Gets the all obj grps.
	 * 
	 * @return the all obj grps
	 * @throws ServiceException
	 *             the service exception
	 */
	List<ObjectGroup> getAllObjGrps() throws ServiceException;

	/**
	 * Gets the all obj types.
	 * 
	 * @return the all obj types
	 * @throws ServiceException
	 *             the service exception
	 */
	List<ObjectType> getAllObjTypes() throws ServiceException;

	/**
	 * Gets the all identifier types.
	 * 
	 * @return the all identifier types
	 * @throws ServiceException
	 *             the service exception
	 */
	List<IdentifierType> getAllIdentifierTypes() throws ServiceException;

	/**
	 * Insert objects get key.
	 * 
	 * @param obj
	 *            the obj
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertObjectsGetKey(Objects obj) throws ServiceException;

	/**
	 * Gets the object group by id.
	 * 
	 * @param objGrpId
	 *            the obj grp id
	 * @return the object group by id
	 * @throws ServiceException
	 *             the service exception
	 */
	ObjectGroup getObjectGroupById(int objGrpId) throws ServiceException;

	/**
	 * Update object group data.
	 * 
	 * @param objGrp
	 *            the obj grp
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateObjectGroupData(ObjectGroup objGrp) throws ServiceException;

	/**
	 * Update test plan data.
	 * 
	 * @param testPlan
	 *            the test plan
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateTestPlanData(TestPlan testPlan) throws ServiceException;

	/**
	 * Update test scenario data.
	 * 
	 * @param testScenario
	 *            the test scenario
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateTestScenarioData(TestScenario testScenario) throws ServiceException;

	/**
	 * Update test suite data.
	 * 
	 * @param testSuite
	 *            the test suite
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateTestSuiteData(TestSuite testSuite) throws ServiceException;

	/**
	 * Insert case step mapping get key.
	 * 
	 * @param caseStepMapping
	 *            the case step mapping
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertCaseStepMappingGetKey(CaseStepMapping caseStepMapping) throws ServiceException;

	/**
	 * Insert identifier type get key.
	 * 
	 * @param identifierType
	 *            the identifier type
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertIdentifierTypeGetKey(IdentifierType identifierType) throws ServiceException;

	/**
	 * Gets the all function names by test case id.
	 * 
	 * @param testCaseId
	 *            the test case id
	 * @return the all function names by test case id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<AppFunctionality> getAllFunctionNamesByTestCaseId(int testCaseId) throws ServiceException;

	/**
	 * Gets the all feature names by test case id.
	 * 
	 * @param testCaseId
	 *            the test case id
	 * @return the all feature names by test case id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<AppFeature> getAllFeatureNamesByTestCaseId(int testCaseId) throws ServiceException;

	/**
	 * Gets the param group names by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the param group names by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<ParamGroup> getParamGroupNamesByAppId(int appId) throws ServiceException;

	/**
	 * Gets the condition group names by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the condition group names by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<ConditionGroup> getConditionGroupNamesByAppId(int appId) throws ServiceException;

	/**
	 * Gets the all test cases by test scenario id.
	 * 
	 * @param testScenarioId
	 *            the test scenario id
	 * @return the all test cases by test scenario id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestCase> getAllTestCasesByTestScenarioId(int testScenarioId) throws ServiceException;

	/**
	 * Gets the all test steps by test case id.
	 * 
	 * @param testCaseId
	 *            the test case id
	 * @return the all test steps by test case id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestStep> getAllTestStepsByTestCaseId(int testCaseId) throws ServiceException;

	/**
	 * Gets the test scenario by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the test scenario by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestScenario> getTestScenarioByAppId(int appId) throws ServiceException;

	/**
	 * Gets the test suite by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the test suite by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestSuite> getTestSuiteByAppId(int appId) throws ServiceException;

	/**
	 * Insert scenario and suite scenario get key.
	 * 
	 * @param testScenario
	 *            the test scenario
	 * @param suiteName
	 *            the suite name
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertScenarioAndSuiteScenarioGetKey(TestScenario testScenario, String suiteName) throws ServiceException;

	/**
	 * Insert case and scenario case get key.
	 * 
	 * @param testCase
	 *            the test case
	 * @param caseName
	 *            the case name
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertCaseAndScenarioCaseGetKey(TestCase testCase, String caseName) throws ServiceException;

	/**
	 * Gets the test databy app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the test databy app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestData> getTestDatabyAppId(int appId) throws ServiceException;

	/**
	 * Gets the param by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the param by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Param> getParamByAppId(int appId) throws ServiceException;

	/**
	 * Gets the param group by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the param group by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<ParamGroup> getParamGroupByAppId(int appId) throws ServiceException;

	/**
	 * Insert test case get key.
	 * 
	 * @param testCase
	 *            the test case
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertTestCaseGetKey(TestCase testCase) throws ServiceException;

	/**
	 * Insert scenario case mapping get key.
	 * 
	 * @param scenarioCaseMapping
	 *            the scenario case mapping
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertScenarioCaseMappingGetKey(ScenarioCaseMapping scenarioCaseMapping) throws ServiceException;

	/**
	 * Insert test scenario get key.
	 * 
	 * @param testScenario
	 *            the test scenario
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertTestScenarioGetKey(TestScenario testScenario) throws ServiceException;

	/**
	 * Insert suite scenario mapping get key.
	 * 
	 * @param suiteScenarioMapping
	 *            the suite scenario mapping
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertSuiteScenarioMappingGetKey(SuiteScenarioMapping suiteScenarioMapping) throws ServiceException;

	/**
	 * Gets the test case by id.
	 * 
	 * @param testCaseId
	 *            the test case id
	 * @return the test case by id
	 * @throws ServiceException
	 *             the service exception
	 */
	TestCase getTestCaseById(int testCaseId) throws ServiceException;
	
	TestCase getTestCaseByName(String testCaseName) throws ServiceException;

	/**
	 * Update test case.
	 * 
	 * @param testCase
	 *            the test case
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateTestCase(TestCase testCase) throws ServiceException;

	/**
	 * Gets the test step by id.
	 * 
	 * @param testStepId
	 *            the test step id
	 * @return the test step by id
	 * @throws ServiceException
	 *             the service exception
	 */
	TestStep getTestStepById(int testStepId) throws ServiceException;

	/**
	 * Update test step.
	 * 
	 * @param testStep
	 *            the test step
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateTestStep(TestStep testStep) throws ServiceException;

	/**
	 * Gets the obj grps by screen id.
	 * 
	 * @param screenId
	 *            the screen id
	 * @return the obj grps by screen id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<ObjectGroup> getObjGrpsByScreenID(int screenId) throws ServiceException;

	/**
	 * Gets the obj grps by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the obj grps by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<ObjectGroup> getObjGrpsByAppID(int appId) throws ServiceException;

	/**
	 * Gets the plan suite mapping by plan id.
	 * 
	 * @param testPlanId
	 *            the test plan id
	 * @return the plan suite mapping by plan id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<PlanSuiteMapping> getPlanSuiteMappingByPlanId(int testPlanId) throws ServiceException;
	
	List<PlanSuiteMapping> getPlanSuiteMappingBySuiteId(int testSuiteId) throws ServiceException;

	/**
	 * Insert test plan get key.
	 * 
	 * @param testPlan
	 *            the test plan
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertTestPlanGetKey(TestPlan testPlan) throws ServiceException;

	/**
	 * Gets the test plan obj by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the test plan obj by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestPlan> getTestPlanObjByAppId(int appId) throws ServiceException;

	/**
	 * Gets the objects for obj grp.
	 * 
	 * @param objGrpId
	 *            the obj grp id
	 * @return the objects for obj grp
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Objects> getObjectsForObjGrp(int objGrpId) throws ServiceException;

	/**
	 * Gets the identifier type by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the identifier type by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<IdentifierType> getIdentifierTypeByAppID(int appId) throws ServiceException;

	/**
	 * Gets the objects by id.
	 * 
	 * @param objId
	 *            the obj id
	 * @return the objects by id
	 * @throws ServiceException
	 *             the service exception
	 */
	Objects getObjectsById(int objId) throws ServiceException;

	/**
	 * Update objects details.
	 * 
	 * @param objects
	 *            the objects
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateObjectsDetails(Objects objects) throws ServiceException;

	/**
	 * Gets the test param data by id.
	 * 
	 * @param testParamDataId
	 *            the test param data id
	 * @return the test param data by id
	 * @throws ServiceException
	 *             the service exception
	 */
	TestParamData getTestParamDataById(int testParamDataId) throws ServiceException;

	/**
	 * Update test param data.
	 * 
	 * @param testParamData
	 *            the test param data
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateTestParamData(TestParamData testParamData) throws ServiceException;

	/**
	 * Insert condition group get key.
	 * 
	 * @param conditionGrp
	 *            the condition grp
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertConditionGroupGetKey(ConditionGroup conditionGrp) throws ServiceException;

	/**
	 * Gets the condition group by id.
	 * 
	 * @param conditionGroupId
	 *            the condition group id
	 * @return the condition group by id
	 * @throws ServiceException
	 *             the service exception
	 */
	ConditionGroup getConditionGroupById(int conditionGroupId) throws ServiceException;

	/**
	 * Update condition group data.
	 * 
	 * @param conGroup
	 *            the con group
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateConditionGroupData(ConditionGroup conGroup) throws ServiceException;

	/**
	 * Gets the condition by condition group id.
	 * 
	 * @param conditionGroupId
	 *            the condition group id
	 * @return the condition by condition group id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Conditions> getConditionByConditionGroupId(int conditionGroupId) throws ServiceException;

	/**
	 * Insert conditions get key.
	 * 
	 * @param conditions
	 *            the conditions
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertConditionsGetKey(Conditions conditions) throws ServiceException;

	/**
	 * Gets the conditions by id.
	 * 
	 * @param conditionsId
	 *            the conditions id
	 * @return the conditions by id
	 * @throws ServiceException
	 *             the service exception
	 */
	Conditions getConditionsById(int conditionsId) throws ServiceException;

	/**
	 * Update conditions data.
	 * 
	 * @param conditions
	 *            the conditions
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateConditionsData(Conditions conditions) throws ServiceException;

	/**
	 * Insert replacement strings get key.
	 * 
	 * @param replacementStrings
	 *            the replacement strings
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertReplacementStringsGetKey(ReplacementStrings replacementStrings) throws ServiceException;

	/**
	 * Insert replacement strings.
	 * 
	 * @param replacementStrings
	 *            the replacement strings
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertReplacementStrings(ReplacementStrings replacementStrings) throws ServiceException;

	/**
	 * Gets the identifier type by id.
	 * 
	 * @param identifierTypeId
	 *            the identifier type id
	 * @return the identifier type by id
	 * @throws ServiceException
	 *             the service exception
	 */
	IdentifierType getIdentifierTypeById(int identifierTypeId) throws ServiceException;

	/**
	 * Update identifier type.
	 * 
	 * @param identifierType
	 *            the identifier type
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateIdentifierType(IdentifierType identifierType) throws ServiceException;

	/**
	 * Gets the param by param grp id.
	 * 
	 * @param paramGrpId
	 *            the param grp id
	 * @return the param by param grp id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Param> getParamByParamGrpId(int paramGrpId) throws ServiceException;

	/**
	 * Gets the param grp details by id.
	 * 
	 * @param paramGrpId
	 *            the param grp id
	 * @return the param grp details by id
	 * @throws ServiceException
	 *             the service exception
	 */
	ParamGroup getParamGrpDetailsById(int paramGrpId) throws ServiceException;

	/**
	 * Update param group data.
	 * 
	 * @param paramGroup
	 *            the param group
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateParamGroupData(ParamGroup paramGroup) throws ServiceException;

	/**
	 * Gets the param by id.
	 * 
	 * @param paramId
	 *            the param id
	 * @return the param by id
	 * @throws ServiceException
	 *             the service exception
	 */
	Param getParamById(int paramId) throws ServiceException;

	/**
	 * Update param details.
	 * 
	 * @param param
	 *            the param
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateParamDetails(Param param) throws ServiceException;

	/**
	 * Gets the replacement strings by id.
	 * 
	 * @param replacementId
	 *            the replacement id
	 * @return the replacement strings by id
	 * @throws ServiceException
	 *             the service exception
	 */
	ReplacementStrings getReplacementStringsById(int replacementId) throws ServiceException;

	/**
	 * Update replacement strings.
	 * 
	 * @param replacementStrings
	 *            the replacement strings
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateReplacementStrings(ReplacementStrings replacementStrings) throws ServiceException;

	/**
	 * Gets the replacement strings by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the replacement strings by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<ReplacementStrings> getReplacementStringsByAppID(int appId) throws ServiceException;

	/**
	 * Gets the param group by id.
	 * 
	 * @param paramGroupId
	 *            the param group id
	 * @return the param group by id
	 * @throws ServiceException
	 *             the service exception
	 */
	ParamGroup getParamGroupById(int paramGroupId) throws ServiceException;

	/**
	 * Gets the param by param group id.
	 * 
	 * @param paramGroupId
	 *            the param group id
	 * @return the param by param group id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Param> getParamByParamGroupId(int paramGroupId) throws ServiceException;

	List<ScenarioCaseMapping> getScenarioCaseMappingsByScenarioId(
			int testScenarioId) throws ServiceException;

	List<TestScenarioUI> getTestScenarioUIByAppID(int appId) throws ServiceException;
	
	List<TestSuiteUI> getTestSuiteUIByAppID(int appId) throws ServiceException;

	List<TestCaseUI> getTestCaseUIByAppID(int testCaseId) throws ServiceException;

	List<SuiteScenarioMapping> getSuiteScenarioMappingByScenarioId(
			int scenarioId) throws ServiceException;

	List<Objects> getObjectsByGroupId(int objectGroupId)
			throws ServiceException;

	long updateObjects(Objects objects) throws ServiceException;

	List<ScenarioCaseMapping> getScenarioCaseMappingsByCaseId(int caseId) throws ServiceException;

	List<CaseStepMapping> getCaseStepMappingsByCaseId(int caseId)
			throws ServiceException;

	List<TestStep> getTestStepByStepsId(int testStepId) throws ServiceException;

	long deleteScenarioCaseMappingById(int scenarioId) throws ServiceException;

	long deleteSuiteScenarioMappingById(int scenarioId) throws ServiceException;
	
	long deleteSuiteScenarioMappingBySuiteId(int suiteId) throws ServiceException;

	List<Integer> getTestCaseIdByName(String testCaseName) throws ServiceException;

	long deleteScenarioCaseMappingByCaseId(int caseId) throws ServiceException;

}