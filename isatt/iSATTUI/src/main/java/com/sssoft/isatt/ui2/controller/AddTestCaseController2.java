package com.sssoft.isatt.ui2.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minidev.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.AppFeature;
import com.sssoft.isatt.data.pojo.AppFunctionality;
import com.sssoft.isatt.data.pojo.Runner;
import com.sssoft.isatt.data.pojo.def.TestCaseUI;
import com.sssoft.isatt.data.pojo.input.CaseStepMapping;
import com.sssoft.isatt.data.pojo.input.ScenarioCaseMapping;
import com.sssoft.isatt.data.pojo.input.TestCase;
import com.sssoft.isatt.data.pojo.input.TestScenario;
import com.sssoft.isatt.data.pojo.input.TestStep;
import com.sssoft.isatt.data.service.InputService;
import com.sssoft.isatt.data.service.MainService;
import com.sssoft.isatt.data.utils.DataConstants;

/**
 * The Class AddTestCaseController2.
 */
@Controller
public class AddTestCaseController2 {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AddTestCaseController2.class);

	/** The input service. */
	private InputService inputService;

	/** The test cases id. */
	private int testCasesId;

	/** The main service. */
	private MainService mainService;

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
	 * Gets the main service.
	 * 
	 * @return the main service
	 */
	public MainService getMainService() {
		return mainService;
	}

	/**
	 * Sets the main service.
	 * 
	 * @param mainService
	 *            the new main service
	 */
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	/**
	 * Gets the test case by app id.
	 * 
	 * @param appID
	 *            the app id
	 * @return the test case by app id
	 */
	@RequestMapping(value = "/getTestCaseBoxByAppIdITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<TestCase> getTestCaseByAppId(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseByAppId(String) - start"); //$NON-NLS-1$
		}
		List<TestScenario> testScenarios = new ArrayList<TestScenario>();
		List<ScenarioCaseMapping> scenarioCaseMappings = new ArrayList<ScenarioCaseMapping>();
		List<TestCase> testCases = new ArrayList<TestCase>();
		try {
			LOG.info("Inside getTestCaseByAppId method");
			int appId = Integer.parseInt(appID);
			LOG.info("appID to retrive :: " + appId);
			testScenarios = inputService.getTestScenarioByAppId(appId);
			for (TestScenario testScenario : testScenarios) {
				testScenario.getTestScenarioID();
				LOG.info("scenario IDS************" + testScenario.getTestScenarioID());
				scenarioCaseMappings = inputService.getScenarioCaseMappingsByScenarioId(testScenario.getTestScenarioID());
				for (ScenarioCaseMapping scenarioCaseMapping : scenarioCaseMappings) {
					LOG.info("TestCase IDS************" + scenarioCaseMapping.getTestCaseID());
					TestCase testCase = inputService.getTestCaseById(scenarioCaseMapping.getTestCaseID());
					testCases.add(testCase);
				}
			}
			LOG.info("inside getTestCaseByAppId at appId ::::");
			LOG.info("Case list ::" + testCases.toString());

			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestCaseByAppId(String) - end"); //$NON-NLS-1$
			}
		} catch (Exception e) {
			LOG.error("error occured.", e);
		}
		return testCases;

	}

	/**
	 * Gets the test casewith no step count.
	 * 
	 * @param appID
	 *            the app id
	 * @return the test casewith no step count
	 */
	@RequestMapping(value = "/getTestCasewithNoStepCount", method = RequestMethod.POST)
	public @ResponseBody
	List<TestCaseUI> getTestCasewithNoStepCount(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCasewithNoStepCount(String) - start"); //$NON-NLS-1$
		}
		int appId = Integer.parseInt(appID);
		LOG.info("appID to retrive :: " + appId);
		List<TestCaseUI> caseId = new ArrayList<TestCaseUI>();
		List<TestCaseUI> caseIds = new ArrayList<TestCaseUI>();
		List<TestScenario> testScenarios = new ArrayList<TestScenario>();
		List<ScenarioCaseMapping> scenarioCaseMappings = new ArrayList<ScenarioCaseMapping>();
		try {
			testScenarios = inputService.getTestScenarioByAppId(appId);
			for (TestScenario testScenario : testScenarios) {
				scenarioCaseMappings = inputService.getScenarioCaseMappingsByScenarioId(testScenario.getTestScenarioID());
				for (ScenarioCaseMapping scenarioCaseMapping : scenarioCaseMappings) {
					caseIds = inputService.getTestCaseUIByAppID(scenarioCaseMapping.getTestCaseID());
					for (TestCaseUI testCaseUI : caseIds) {
						if (testCaseUI.getTestStepsCount() == 0) {
							caseId.addAll(caseIds);
						}
					}
				}
			}
			LOG.info("Case list from getCaseListFilterByAppIdGetCount ::" + caseId.toString());
		} catch (Exception se) {
			LOG.error("getTestCasewithNoStepCount(String)", se); //$NON-NLS-1$
		}
		Map<String, TestCaseUI> listToMap = new LinkedHashMap<String, TestCaseUI>();
		for (int i = 0; i < caseId.size(); i++) {
			listToMap.put(caseId.get(i).getCaseName(), caseId.get(i));
		}
		List<TestCaseUI> caseList = new ArrayList<TestCaseUI>(listToMap.values());
		LOG.info("Case list without duplicate ::" + caseList.toString());
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCasewithNoStepCount(String) - end"); //$NON-NLS-1$
		}
		return caseList;
	}

	/**
	 * Gets the test case list filter by app id get count.
	 * 
	 * @param appID
	 *            the app id
	 * @return the test case list filter by app id get count
	 */
	@RequestMapping(value = "/getCaseListFilterByAppIdGetCount", method = RequestMethod.POST)
	public @ResponseBody
	List<TestCaseUI> getTestCaseListFilterByAppIdGetCount(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseListFilterByAppIdGetCount(String) - start"); //$NON-NLS-1$
		}
		int appId = Integer.parseInt(appID);
		LOG.info("appID to retrive :: " + appId);
		List<TestCaseUI> caseId = new ArrayList<TestCaseUI>();
		List<TestCaseUI> caseIds = new ArrayList<TestCaseUI>();
		List<TestScenario> testScenarios = new ArrayList<TestScenario>();
		List<ScenarioCaseMapping> scenarioCaseMappings = new ArrayList<ScenarioCaseMapping>();
		try {
			testScenarios = inputService.getTestScenarioByAppId(appId);
			for (TestScenario testScenario : testScenarios) {
				scenarioCaseMappings = inputService.getScenarioCaseMappingsByScenarioId(testScenario.getTestScenarioID());
				for (ScenarioCaseMapping scenarioCaseMapping : scenarioCaseMappings) {
					caseIds = inputService.getTestCaseUIByAppID(scenarioCaseMapping.getTestCaseID());
					caseId.addAll(caseIds);
				}
			}
			LOG.info("Case list from getCaseListFilterByAppIdGetCount ::" + caseId.toString());
		} catch (ServiceException se) {
			LOG.error("getTestCaseListFilterByAppIdGetCount(String)", se); //$NON-NLS-1$
		}
		Map<String, TestCaseUI> listToMap = new LinkedHashMap<String, TestCaseUI>();
		for (int i = 0; i < caseId.size(); i++) {
			listToMap.put(caseId.get(i).getCaseName(), caseId.get(i));
		}
		List<TestCaseUI> caseList = new ArrayList<TestCaseUI>(listToMap.values());
		LOG.info("Case list without duplicate ::" + caseList.toString());
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseListFilterByAppIdGetCount(String) - end"); //$NON-NLS-1$
		}
		return caseList;
	}

	/**
	 * Adds the test cases itap.
	 * 
	 * @param testCasesData
	 *            the test cases data
	 * @return the int
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addTestCasesITAP", method = RequestMethod.POST)
	public @ResponseBody
	int addTestCasesITAP(@RequestBody Map<String, String> testCasesData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestCasesITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}
		LOG.info("Entering addTestCases controller !!!");
		try {
			JSONObject jsonObject = new JSONObject(testCasesData);
			String testCaseNames = (String) jsonObject.get("caseName");
			String description = (String) jsonObject.get("description");
			String active = (String) jsonObject.get("active");
			String positive = (String) jsonObject.get("positive");
			String classiTag = (String) jsonObject.get("classificationTag");
			int sortOrders = (Integer) jsonObject.get("sortOrder");
			int functionId = (Integer) jsonObject.get("functionalID");
			int featuId = (Integer) jsonObject.get("featureID");
			int conditGroupIDs = (Integer) jsonObject.get("conditionGroupID");
			int runnerNameIDs = (Integer) jsonObject.get("runnerID");
			TestCase testCases = new TestCase();
			testCases.setCaseName(testCaseNames);
			testCases.setDescription(description);
			testCases.setActive(active);
			testCases.setClassificationTag(classiTag);
			testCases.setPositive(positive);
			testCases.setFunctionalID(functionId);
			testCases.setFeatureID(featuId);
			testCases.setSortOrder(sortOrders);
			testCases.setConditionGroupID(conditGroupIDs);
			testCases.setRunnerID(runnerNameIDs);
			testCases.setCreatedBy(DataConstants.DEFAULT_USER);
			testCases.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			testCases.setUpdatedBy(DataConstants.DEFAULT_USER);
			testCases.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			LOG.info("TestCases Pojo String ::" + testCases.toString());
			testCasesId = inputService.insertTestCaseGetKey(testCases);
			LOG.info("testCases ID ::" + testCasesId);
			ScenarioCaseMapping scenarioCase = new ScenarioCaseMapping();
			CaseStepMapping caseSteps = new CaseStepMapping();
			List<Integer> testStepsIds = new ArrayList<Integer>();
			testStepsIds = (ArrayList<Integer>) jsonObject.get("linkedSteps");
			List<List<Integer>> scenarioSelectedfromPopUP = (List<List<Integer>>) jsonObject.get("scenarioSelectedfromPopUP");
			if (scenarioSelectedfromPopUP != null) {
				for (List<Integer> testScenarioList : scenarioSelectedfromPopUP) {
					LOG.info(" testScenarioList " + testScenarioList);
					for (int i = 0; i < testScenarioList.size(); i++) {
						LOG.info("  testScenarioList.get(i)  " + testScenarioList.get(i));
						int testScenario = testScenarioList.get(i);
						if (testScenario != 0) {
							scenarioCase.setTestCaseID(testCasesId);
							scenarioCase.setTestScenarioID(testScenario);
							scenarioCase.setCreatedBy(DataConstants.DEFAULT_USER);
							scenarioCase.setCreatedDateTime(DataConstants.DEFAULT_DATE);
							scenarioCase.setUpdatedBy(DataConstants.DEFAULT_USER);
							scenarioCase.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
							LOG.info("ScenarioCaseMapping Pojo String ::" + scenarioCase.toString());
							inputService.insertScenarioCaseMappingGetKey(scenarioCase);
						}
					}
				}
			}
			if (!testStepsIds.isEmpty()) {
				if (testStepsIds.get(0) != null && testStepsIds.size() > 0) {
					for (int i = 0; i < testStepsIds.size(); i++) {
						if (testStepsIds.get(i) != null) {
							caseSteps.setTestCaseID(testCasesId);
							String value = String.valueOf(testStepsIds.get(i));
							value = value.trim();
							int testStepsParse = Integer.valueOf(value);
							caseSteps.setTestStepID(testStepsParse);

							caseSteps.setCreatedBy(DataConstants.DEFAULT_USER);
							caseSteps.setCreatedDateTime(DataConstants.DEFAULT_DATE);
							caseSteps.setUpdatedBy(DataConstants.DEFAULT_USER);
							caseSteps.setUpdatedDateTime(DataConstants.DEFAULT_DATE);

							LOG.info("CaseStepMapping Pojo String ::" + caseSteps.toString());
							inputService.insertCaseStepMappingGetKey(caseSteps);
						}
					}
				}
			}

		} catch (Exception e) {
			LOG.error("addTestCasesITAP(Map<String,String>)", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestCasesITAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return testCasesId;
	}

	/**
	 * Gets the test case i ds itap.
	 * 
	 * @param testCaseName
	 *            the test case name
	 * @return the test case i ds itap
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTestCaseIDsITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<Integer> getTestCaseIDsITAP(@RequestBody Map<String, String> testCaseName) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseIDsITAP(Map<String,String>) - start");
		}
		JSONObject jsonObject = new JSONObject(testCaseName);
		List<Integer> testCaseIDs = new ArrayList<Integer>();
		try {
			List<String> testCasesNmes = (ArrayList<String>) jsonObject.get("Cases");
			for (int i = 0; i < testCasesNmes.size(); i++) {
				List<Integer> testCaseID = inputService.getTestCaseIdByName(testCasesNmes.get(i));
				testCaseIDs.addAll(testCaseID);
			}
		} catch (ServiceException e) {
			LOG.warn("getTestCaseIDsITAP(Map<String,String>) - exception ignored", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseIDsITAP(Map<String,String>) - end");
		}
		return testCaseIDs;
	}

	/**
	 * Gets the app functionality filter by app id.
	 * 
	 * @param appID
	 *            the app id
	 * @return the app functionality filter by app id
	 */
	@RequestMapping(value = "/getAppFunctionalNameByAppIdITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<AppFunctionality> getAppFunctionalityFilterByAppId(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionalityFilterByAppId(String) - start"); //$NON-NLS-1$
		}
		LOG.info("Inside getAppFunctionalityFilterByAppId method");
		int appId = Integer.parseInt(appID);
		List<AppFunctionality> appIds = new ArrayList<AppFunctionality>();
		try {
			appIds = mainService.getAppFunctionalityFilterByAppId(appId);
			LOG.info("Functional List" + appIds.toString());
		} catch (ServiceException se) {
			LOG.error("getAppFunctionalityFilterByAppId(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionalityFilterByAppId(String) - end"); //$NON-NLS-1$
		}
		return appIds;
	}

	/**
	 * Gets the all app features by functional id.
	 * 
	 * @param functionalID
	 *            the functional id
	 * @return the all app features by functional id
	 */
	@RequestMapping(value = "/getAllAppFeaturesListByFunctionalIdITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<AppFeature> getAllAppFeaturesByFunctionalId(@RequestBody String functionalID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllAppFeaturesByFunctionalId(String) - start"); //$NON-NLS-1$
		}
		int functionalId = Integer.parseInt(functionalID);
		LOG.info("functionalId" + functionalId);
		List<AppFeature> appFeatures = new ArrayList<AppFeature>();
		try {
			appFeatures = mainService.getAllAppFeaturesByFunctionalId(functionalId);
		} catch (ServiceException se) {
			LOG.error("getAllAppFeaturesByFunctionalId(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllAppFeaturesByFunctionalId(String) - end"); //$NON-NLS-1$
		}
		return appFeatures;
	}

	/**
	 * Gets the runner.
	 * 
	 * @return the runner
	 */
	@RequestMapping(value = "/getRunnerNameListITAP", method = RequestMethod.GET)
	public @ResponseBody
	List<Runner> getRunner() {
		LOG.info("Inside getRunner method");
		List<Runner> runners = new ArrayList<Runner>();
		try {
			runners = mainService.getRunner();
		} catch (ServiceException e) {
			LOG.error("getRunner()", e); //$NON-NLS-1$
		}
		return runners;
	}

	/**
	 * Edits the test cases control.
	 * 
	 * @param testCase
	 *            the test case
	 * @return the test case ui
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/editTestCaseITAP", method = RequestMethod.POST)
	public @ResponseBody
	TestCaseUI editTestCasesControl(@RequestBody Map<String, Integer> testCase) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("editTestCasesControl(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		LOG.info("Printing data recieved from client :: " + testCase);
		JSONObject jsonObject = new JSONObject(testCase);
		int testCaseIDs = (Integer) jsonObject.get("testCaseID");
		LOG.info("testCase ID to edit :: " + testCaseIDs);
		TestCaseUI testCaseUI = new TestCaseUI();
		TestCase tCases = new TestCase();
		try {
			tCases = inputService.getTestCaseById(testCaseIDs);
			LOG.info("TestCases Name:: " + tCases.getCaseName());
			testCaseUI.setTestCaseID(tCases.getTestCaseID());
			testCaseUI.setCaseName(tCases.getCaseName());
			testCaseUI.setDescription(tCases.getDescription());
			testCaseUI.setActive(tCases.getActive());
			testCaseUI.setClassificationTag(tCases.getClassificationTag());
			testCaseUI.setConditionGroupID(tCases.getConditionGroupID());
			testCaseUI.setFeatureID(tCases.getFeatureID());
			testCaseUI.setFunctionalID(tCases.getFunctionalID());
			testCaseUI.setPositive(tCases.getPositive());
			testCaseUI.setRunnerID(tCases.getRunnerID());
			testCaseUI.setSortOrder(tCases.getSortOrder());
			List<TestScenario> testSceList = new ArrayList<TestScenario>();
			List<ScenarioCaseMapping> scenarioCaseMap = inputService.getScenarioCaseMappingsByCaseId(testCaseIDs);
			for (ScenarioCaseMapping scenarioCaseMapping : scenarioCaseMap) {
				testSceList.add(scenarioCaseMapping.getTestScenario());
			}
			testCaseUI.setScenarioList(testSceList);
			List<TestStep> testStepList = new ArrayList<TestStep>();
			List<CaseStepMapping> caseStepMap = inputService.getCaseStepMappingsByCaseId(testCaseIDs);
			for (CaseStepMapping caseStepMapping : caseStepMap) {
				testStepList.add(caseStepMapping.getTestStep());
			}
			testCaseUI.setStepList(testStepList);
		} catch (ServiceException se) {
			LOG.error("editTestCasesControl(Map<String,Integer>)", se); //$NON-NLS-1$
		}
		LOG.info("testCaseUI List inside edit case :: " + testCaseUI.toString());
		if (LOG.isDebugEnabled()) {
			LOG.debug("editTestCasesControl(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return testCaseUI;
	}

	/**
	 * Update test cases itap.
	 * 
	 * @param testCaseDatas
	 *            the test case datas
	 * @return the long
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateTestCasesITAP", method = RequestMethod.POST)
	public @ResponseBody
	long updateTestCasesITAP(@RequestBody Map<String, String> testCaseDatas) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestCasesITAP(Map<String,String>) - start");
		}
		long update = 0;
		try {
			JSONObject jsonObject = new JSONObject(testCaseDatas);
			String testCaseNames = (String) jsonObject.get("caseName");
			String description = (String) jsonObject.get("description");
			String active = (String) jsonObject.get("active");
			String positive = (String) jsonObject.get("positive");
			String classiTag = (String) jsonObject.get("classificationTag");
			int sortOrders = (Integer) jsonObject.get("sortOrder");
			int caseIDs = (Integer) jsonObject.get("testCaseID");
			int functionId = (Integer) jsonObject.get("functionalID");
			int featuId = (Integer) jsonObject.get("featureID");
			int conditGroupIDs = (Integer) jsonObject.get("conditionGroupID");
			int runnerNameIDs = (Integer) jsonObject.get("runnerID");
			TestCase testCases = new TestCase();
			testCases.setCaseName(testCaseNames);
			testCases.setDescription(description);
			testCases.setActive(active);
			testCases.setClassificationTag(classiTag);
			testCases.setSortOrder(sortOrders);
			testCases.setTestCaseID(caseIDs);
			testCases.setPositive(positive);
			testCases.setFunctionalID(functionId);
			testCases.setFeatureID(featuId);
			testCases.setConditionGroupID(conditGroupIDs);
			testCases.setRunnerID(runnerNameIDs);
			testCases.setUpdatedBy(DataConstants.DEFAULT_USER);
			testCases.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			update = inputService.updateTestCase(testCases);
			inputService.deleteScenarioCaseMappingByCaseId(caseIDs);
			ScenarioCaseMapping scenarioCase = new ScenarioCaseMapping();
			CaseStepMapping caseSteps = new CaseStepMapping();
			List<Integer> testStepsIds = new ArrayList<Integer>();
			testStepsIds = (ArrayList<Integer>) jsonObject.get("linkedSteps");
			List<List<Integer>> scenarioSelectedfromPopUP = (List<List<Integer>>) jsonObject.get("scenarioSelectedfromPopUP");
			if (scenarioSelectedfromPopUP != null) {
				for (List<Integer> testScenarioList : scenarioSelectedfromPopUP) {
					LOG.info(" testScenarioList " + testScenarioList);
					for (int i = 0; i < testScenarioList.size(); i++) {
						LOG.info("  testScenarioList.get(i)  " + testScenarioList.get(i));
						int testScenario = testScenarioList.get(i);
						if (testScenario != 0) {
							scenarioCase.setTestCaseID(caseIDs);
							scenarioCase.setTestScenarioID(testScenario);
							scenarioCase.setCreatedBy(DataConstants.DEFAULT_USER);
							scenarioCase.setCreatedDateTime(DataConstants.DEFAULT_DATE);
							scenarioCase.setUpdatedBy(DataConstants.DEFAULT_USER);
							scenarioCase.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
							LOG.info("ScenarioCaseMapping Pojo String ::" + scenarioCase.toString());
							inputService.insertScenarioCaseMappingGetKey(scenarioCase);
						}
					}
				}
			}
			if (!testStepsIds.isEmpty()) {
				if (testStepsIds.get(0) != null && testStepsIds.size() > 0) {
					for (int i = 0; i < testStepsIds.size(); i++) {
						if (testStepsIds.get(i) != null) {
							caseSteps.setTestCaseID(caseIDs);
							String value = String.valueOf(testStepsIds.get(i));
							value = value.trim();
							int testStepsParse = Integer.valueOf(value);
							caseSteps.setTestStepID(testStepsParse);
							caseSteps.setCreatedBy(DataConstants.DEFAULT_USER);
							caseSteps.setCreatedDateTime(DataConstants.DEFAULT_DATE);
							caseSteps.setUpdatedBy(DataConstants.DEFAULT_USER);
							caseSteps.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
							LOG.info("CaseStepMapping Pojo String ::" + caseSteps.toString());
							inputService.insertCaseStepMappingGetKey(caseSteps);
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.error("updateTestCasesITAP(Map<String,String>)", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestCasesITAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return update;
	}

}
