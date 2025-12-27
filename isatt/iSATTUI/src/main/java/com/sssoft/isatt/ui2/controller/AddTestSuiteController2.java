package com.sssoft.isatt.ui2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.def.TestSuiteUI;
import com.sssoft.isatt.data.pojo.input.PlanSuiteMapping;
import com.sssoft.isatt.data.pojo.input.SuiteScenarioMapping;
import com.sssoft.isatt.data.pojo.input.TestPlan;
import com.sssoft.isatt.data.pojo.input.TestScenario;
import com.sssoft.isatt.data.pojo.input.TestSuite;
import com.sssoft.isatt.data.service.InputService;
import com.sssoft.isatt.data.utils.DataConstants;

/**
 * The Class AddTestSuiteController2.
 */
@Controller
public class AddTestSuiteController2 {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AddTestSuiteController2.class);

	// starts from shilpa

	/** The input service. */
	private InputService inputService;

	/**
	 * Gets the input service.
	 * 
	 * @return the input service
	 */
	public InputService getInputService() {
		return inputService;
	}

	/**
	 * Sets the input service.
	 * 
	 * @param inputService
	 *            the new input service
	 */
	public void setInputService(InputService inputService) {
		this.inputService = inputService;
	}

	/**
	 * Adds the test suite.
	 * 
	 * @param testPlanData
	 *            the test plan data
	 * @return the int
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addTestSuiteITAP", method = RequestMethod.POST)
	public @ResponseBody
	int addTestSuite(@RequestBody Map<String, String> testPlanData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestSuite(Map<String,String>) - start"); //$NON-NLS-1$
		}

		JSONObject jsonObject = new JSONObject(testPlanData);
		long testSuiteId = 0;
		try {
			String testSuiteName = (String) jsonObject.get("testSuiteName");
			String description = (String) jsonObject.get("description");
			String sortOrder = (String) jsonObject.get("sortOrder");
			int sOrder = Integer.parseInt(sortOrder);
			int appIDs = (Integer) jsonObject.get("appID");
			TestSuite testSuite = new TestSuite();
			testSuite.setSuiteName(testSuiteName);
			testSuite.setDescription(description);
			testSuite.setSortOrder(sOrder);
			testSuite.setAppID(appIDs);
			testSuite.setCreatedBy(DataConstants.DEFAULT_USER);
			testSuite.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			testSuite.setUpdatedBy(DataConstants.DEFAULT_USER);
			testSuite.setUpdatedDateTime(DataConstants.DEFAULT_DATE);

			System.out.println("TestPlan Pojo String ::" + testSuite.toString());
			testSuiteId = inputService.insertTestSuiteGetKey(testSuite);

			int testSuiteids = (int) testSuiteId;

			List<List<Integer>> scenarioSelectedfromPopUP = (List<List<Integer>>) jsonObject.get("scenarioSelectedfromPopUP");
			List<List<Integer>> planSelectedfromPopUP = (List<List<Integer>>) jsonObject.get("planSelectedfromPopUP");
			if (planSelectedfromPopUP != null) {
				for (List<Integer> testPlanList : planSelectedfromPopUP) {
					LOG.info(" testPlanList " + testPlanList);
					for (int i = 0; i < testPlanList.size(); i++) {
						LOG.info("  testPlanList.get(i)  " + testPlanList.get(i));
						int testPlan = testPlanList.get(i);
						if (testPlan != 0) {
							PlanSuiteMapping planSuiteMapping = new PlanSuiteMapping();
							planSuiteMapping.setTestSuiteID(testSuiteids);
							planSuiteMapping.setTestPlanID(testPlan);
							planSuiteMapping.setCreatedBy(DataConstants.DEFAULT_USER);
							planSuiteMapping.setCreatedDateTime(DataConstants.DEFAULT_DATE);
							planSuiteMapping.setUpdatedBy(DataConstants.DEFAULT_USER);
							planSuiteMapping.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
							LOG.info("PlanSuiteMapping Pojo String ::" + planSuiteMapping.toString());
							inputService.insertPlanSuiteMapping(planSuiteMapping);
						}
					}
				}
			}
			if (scenarioSelectedfromPopUP != null) {
				for (List<Integer> testscenarioList : scenarioSelectedfromPopUP) {
					LOG.info(" testscenarioList " + testscenarioList);
					for (int i = 0; i < testscenarioList.size(); i++) {
						LOG.info("  testscenarioList.get(i)  " + testscenarioList.get(i));
						int testScenario = testscenarioList.get(i);
						if (testScenario != 0) {
							SuiteScenarioMapping suiteScenarioMapping = new SuiteScenarioMapping();
							suiteScenarioMapping.setTestScenarioID(testScenario);
							suiteScenarioMapping.setTestSuiteID(testSuiteids);
							suiteScenarioMapping.setCreatedBy(DataConstants.DEFAULT_USER);
							suiteScenarioMapping.setCreatedDateTime(DataConstants.DEFAULT_DATE);
							suiteScenarioMapping.setUpdatedBy(DataConstants.DEFAULT_USER);
							suiteScenarioMapping.setUpdatedDateTime(DataConstants.DEFAULT_DATE);

							System.out.println("SuiteScenarioMapping Pojo String ::" + suiteScenarioMapping.toString());
							inputService.insertSuiteScenarioMapping(suiteScenarioMapping);
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.error("addTestSuite(Map<String,String>)", e); //$NON-NLS-1$

		}
		int returnint = (int) testSuiteId;
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestSuite(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return returnint;
	}

	/**
	 * Gets the test scenario list filter by app id get count.
	 * 
	 * @return the test scenario list filter by app id get count
	 */
	@RequestMapping(value = "/getTestScenarioListFilterByAppIdGetCount", method = RequestMethod.GET)
	public @ResponseBody
	List<TestSuiteUI> getTestScenarioListFilterByAppIdGetCount() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioListFilterByAppIdGetCount() - start"); //$NON-NLS-1$
		}
		List<TestSuiteUI> AppId = new ArrayList<TestSuiteUI>();
		try {
			AppId = inputService.getTestSuiteUIByAppID(234);
		} catch (ServiceException se) {
			LOG.error("getTestScenarioListFilterByAppIdGetCount()", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioListFilterByAppIdGetCount() - end"); //$NON-NLS-1$
		}
		return AppId;
	}

	/**
	 * Gets the suite box by app id itap with planand scenario count.
	 * 
	 * @param appID
	 *            the app id
	 * @return the suite box by app id itap with planand scenario count
	 */
	@RequestMapping(value = "/getSuiteBoxByAppIdITAPWithPlanandScenarioCount", method = RequestMethod.POST)
	public @ResponseBody
	List<TestSuiteUI> getSuiteBoxByAppIdITAPWithPlanandScenarioCount(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteBoxByAppIdITAPWithPlanandScenarioCount(String) - start"); //$NON-NLS-1$
		}
		List<TestSuiteUI> testSuiteUIList = new ArrayList<TestSuiteUI>();
		int appId = Integer.parseInt(appID);
		try {
			List<TestSuite> testSuites = inputService.getTestSuiteByAppId(appId);
			for (TestSuite testSuite : testSuites) {
				List<PlanSuiteMapping> planSuiteIDs = inputService.getPlanSuiteMappingBySuiteId(testSuite.getTestSuiteID());
				List<SuiteScenarioMapping> suiteScenarioIDs = inputService.getSuiteScenarioMappingBySuiteId(testSuite.getTestSuiteID());
				LOG.info("plan suite id count" + planSuiteIDs.size());
				TestSuiteUI testSuiteUI = new TestSuiteUI();
				testSuiteUI.setTestSuiteDesc(testSuite.getDescription());
				testSuiteUI.setTestSuiteId(testSuite.getTestSuiteID());
				testSuiteUI.setTestSuiteName(testSuite.getSuiteName());
				testSuiteUI.setTestPlanCount(planSuiteIDs.size());
				testSuiteUI.setTestScenariosCount(suiteScenarioIDs.size());
				testSuiteUIList.add(testSuiteUI);
			}
			LOG.info("testSuiteUIList  " + testSuiteUIList);
		} catch (ServiceException se) {
			LOG.error("getSuiteBoxByAppIdITAPWithPlanandScenarioCount(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteBoxByAppIdITAPWithPlanandScenarioCount(String) - end"); //$NON-NLS-1$
		}
		return testSuiteUIList;
	}

	/**
	 * Gets the suite with no scenario count.
	 * 
	 * @param appID
	 *            the app id
	 * @return the suite with no scenario count
	 */
	@RequestMapping(value = "/getSuiteWithNoScenarioCount", method = RequestMethod.POST)
	public @ResponseBody
	List<TestSuite> getSuiteWithNoScenarioCount(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteWithNoScenarioCount(String) - start"); //$NON-NLS-1$
		}
		List<TestSuite> testSuiteList = new ArrayList<TestSuite>();
		int appId = Integer.parseInt(appID);
		try {
			List<TestSuite> testSuites = inputService.getTestSuiteByAppId(appId);
			for (TestSuite testSuite : testSuites) {
				List<PlanSuiteMapping> planSuiteIDs = inputService.getPlanSuiteMappingBySuiteId(testSuite.getTestSuiteID());
				List<SuiteScenarioMapping> suiteScenarioIDs = inputService.getSuiteScenarioMappingBySuiteId(testSuite.getTestSuiteID());
				if (suiteScenarioIDs.size() == 0 || planSuiteIDs.size() == 0) {
					testSuiteList.add(testSuite);
				}
			}
		} catch (ServiceException se) {
			LOG.error("getSuiteWithNoScenarioCount(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteWithNoScenarioCount(String) - end"); //$NON-NLS-1$
		}
		return testSuiteList;
	}

	/**
	 * Gets the suite by suite id.
	 * 
	 * @param testSuiteId
	 *            the test suite id
	 * @return the suite by suite id
	 */
	@RequestMapping(value = "/getSuiteBySuiteID", method = RequestMethod.POST)
	public @ResponseBody
	TestSuiteUI getSuiteBySuiteID(@RequestBody String testSuiteId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteBySuiteID(String) - start"); //$NON-NLS-1$
		}

		int suiteId = Integer.parseInt(testSuiteId);
		TestSuiteUI testSuiteUI = new TestSuiteUI();

		try {
			TestSuite testSuite = inputService.getTestSuiteById(suiteId);
			testSuiteUI.setTestSuiteId(testSuite.getTestSuiteID());
			testSuiteUI.setTestSuiteName(testSuite.getSuiteName());
			testSuiteUI.setTestSuiteDesc(testSuite.getDescription());
			testSuiteUI.setSortOrder(testSuite.getSortOrder());
			List<TestPlan> testPlansList = new ArrayList<TestPlan>();
			List<PlanSuiteMapping> planSuiteList = inputService.getPlanSuiteMappingBySuiteId(suiteId);
			for (PlanSuiteMapping planSuiteMapping : planSuiteList) {
				testPlansList.add(planSuiteMapping.getTestPlan());
			}
			testSuiteUI.setPlansList(testPlansList);
			List<TestScenario> scenariosList = new ArrayList<TestScenario>();
			List<SuiteScenarioMapping> suiteScenarioMappingList = inputService.getSuiteScenarioMappingBySuiteId(suiteId);
			for (SuiteScenarioMapping suiteScenarioMapping : suiteScenarioMappingList) {
				scenariosList.add(suiteScenarioMapping.getTestScenario());
			}
			testSuiteUI.setScenarioList(scenariosList);
		} catch (ServiceException se) {
			LOG.error("getSuiteBySuiteID(String)", se); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteBySuiteID(String) - end"); //$NON-NLS-1$
		}
		return testSuiteUI;

	}

	/**
	 * Gets the test suite i ds itap.
	 * 
	 * @param testsuiteName
	 *            the testsuite name
	 * @return the test suite i ds itap
	 */
	@RequestMapping(value = "/getTestSuiteIDsITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<Integer> getTestSuiteIDsITAP(@RequestBody Map<String, String> testsuiteName) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteIDsITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}
		JSONObject jsonObject = new JSONObject(testsuiteName);
		List<Integer> testSuiteIDs = new ArrayList<Integer>();
		try {
			@SuppressWarnings("unchecked")
			List<String> testSuiteNmes = (ArrayList<String>) jsonObject.get("Suites");
			for (int i = 0; i < testSuiteNmes.size(); i++) {
				if(!testSuiteNmes.get(i).equals("")){
				TestSuite testSuite = inputService.getTestSuiteByName(testSuiteNmes.get(i));
				testSuiteIDs.add(testSuite.getTestSuiteID());
				}
			}
		} catch (JSONException e) {
			LOG.warn("getTestSuiteIDsITAP(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		} catch (ServiceException e) {
			LOG.warn("getTestSuiteIDsITAP(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteIDsITAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return testSuiteIDs;
	}

	/**
	 * Update test suite.
	 * 
	 * @param testPlanData
	 *            the test plan data
	 * @return the int
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateTestSuiteITAP", method = RequestMethod.POST)
	public @ResponseBody
	int updateTestSuite(@RequestBody Map<String, String> testPlanData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestSuite(Map<String,String>) - start"); //$NON-NLS-1$
		}
		JSONObject jsonObject = new JSONObject(testPlanData);
		long testSuiteId = 0;
		try {
			String testSuiteName = (String) jsonObject.get("testSuiteName");
			String description = (String) jsonObject.get("description");
			String sortOrder = (String) jsonObject.get("sortOrder");
			int testSuiteID = (Integer) jsonObject.get("testSuiteID");
			int sOrder = Integer.parseInt(sortOrder);
			int appIDs = (Integer) jsonObject.get("appID");
			TestSuite testSuite = new TestSuite();
			testSuite.setTestSuiteID(testSuiteID);
			testSuite.setSuiteName(testSuiteName);
			testSuite.setDescription(description);
			testSuite.setSortOrder(sOrder);
			testSuite.setAppID(appIDs);
			testSuite.setCreatedBy(DataConstants.DEFAULT_USER);
			testSuite.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			testSuite.setUpdatedBy(DataConstants.DEFAULT_USER);
			testSuite.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			testSuiteId = inputService.updateTestSuiteData(testSuite);
			inputService.deletePlanSuiteMappingBySuiteId(testSuiteID);
			inputService.deleteSuiteScenarioMappingBySuiteId(testSuiteID);
			List<List<Integer>> scenarioSelectedfromPopUP = (List<List<Integer>>) jsonObject.get("scenarioSelectedfromPopUP");
			List<List<Integer>> planSelectedfromPopUP = (List<List<Integer>>) jsonObject.get("planSelectedfromPopUP");
			for (List<Integer> testPlanList : planSelectedfromPopUP) {
				LOG.info(" testPlanList " + testPlanList);
				for (int i = 0; i < testPlanList.size(); i++) {
					LOG.info("  testPlanList.get(i)  " + testPlanList.get(i));
					int testPlan = testPlanList.get(i);
					if (testPlan != 0) {
						PlanSuiteMapping planSuiteMapping = new PlanSuiteMapping();
						planSuiteMapping.setTestPlanID(testPlan);
						planSuiteMapping.setTestSuiteID(testSuiteID);
						planSuiteMapping.setCreatedBy(DataConstants.DEFAULT_USER);
						planSuiteMapping.setCreatedDateTime(DataConstants.DEFAULT_DATE);
						planSuiteMapping.setUpdatedBy(DataConstants.DEFAULT_USER);
						planSuiteMapping.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
						inputService.insertPlanSuiteMapping(planSuiteMapping);
					}
				}
			}
			for (List<Integer> testScenarioList : scenarioSelectedfromPopUP) {
				LOG.info(" testScenarioList " + testScenarioList);
				for (int i = 0; i < testScenarioList.size(); i++) {
					LOG.info("  testScenarioList.get(i)  " + testScenarioList.get(i));
					int testScenario = testScenarioList.get(i);
					if (testScenario != 0) {
						SuiteScenarioMapping suiteScenarioMapping = new SuiteScenarioMapping();
						suiteScenarioMapping.setTestSuiteID(testSuiteID);
						int testScenarioIDParse = Integer.valueOf(testScenario);
						suiteScenarioMapping.setTestScenarioID(testScenarioIDParse);
						suiteScenarioMapping.setCreatedBy(DataConstants.DEFAULT_USER);
						suiteScenarioMapping.setCreatedDateTime(DataConstants.DEFAULT_DATE);
						suiteScenarioMapping.setUpdatedBy(DataConstants.DEFAULT_USER);
						suiteScenarioMapping.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
						inputService.insertSuiteScenarioMapping(suiteScenarioMapping);
					}
				}
			}
		} catch (Exception e) {
			LOG.error("updateTestSuite(Map<String,String>)", e); //$NON-NLS-1$
		}
		int returnint = (int) testSuiteId;
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestSuite(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return returnint;
	}

}