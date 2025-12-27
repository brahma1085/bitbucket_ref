package com.tfw.exilant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minidev.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.AppFeature;
import com.exilant.tfw.pojo.AppFunctionality;
import com.exilant.tfw.pojo.Runner;
import com.exilant.tfw.pojo.input.ScenarioCaseMapping;
import com.exilant.tfw.pojo.input.TestCase;
import com.exilant.tfw.pojo.input.TestScenario;
import com.exilant.tfw.pojo.input.TestStep;
import com.exilant.tfw.service.InputService;
import com.exilant.tfw.service.MainService;
import com.exilant.tfw.utils.DataConstants;

/**
 * The Class AddTestCaseController.
 */
@Controller
public class AddTestCaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(AddTestCaseController.class);

	/** The jb. */
	static StringBuffer jb = new StringBuffer();

	/** The input service. */
	private InputService inputService;

	/** The main service. */
	private MainService mainService;

	/** The test cases id. */
	public int testCasesId;

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
	 * Adds the test cases.
	 * 
	 * @param testCasesData
	 *            the test cases data
	 * @return the int
	 */
	@RequestMapping(value = "/addTestCases", method = RequestMethod.POST)
	public @ResponseBody
	int addTestCases(@RequestBody Map<String, String> testCasesData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestCases(Map<String,String>) - start"); //$NON-NLS-1$
		}
		try {
			JSONObject jsonObject = new JSONObject(testCasesData);
			String testCaseNames = (String) jsonObject.get("caseName");
			String description = (String) jsonObject.get("description");
			String actives = (String) jsonObject.get("active");
			String classificationTags = (String) jsonObject.get("classificationTag");
			String positives = (String) jsonObject.get("positive");
			int sortOrders = (Integer) jsonObject.get("sortOrder");
			String functionalIDs = (String) jsonObject.get("functionalID");
			int functionId = Integer.parseInt(functionalIDs);
			String featureIDs = (String) jsonObject.get("featureID");
			int featuId = Integer.parseInt(featureIDs);
			String conditionGroupIDs = (String) jsonObject.get("conditionGroupID");
			int conditGroupIDs = Integer.parseInt(conditionGroupIDs);
			String runnerIDs = (String) jsonObject.get("runnerID");
			int runnerNameIDs = Integer.parseInt(runnerIDs);
			TestCase testCases = new TestCase();
			testCases.setCaseName(testCaseNames);
			testCases.setDescription(description);
			testCases.setActive(actives);
			testCases.setClassificationTag(classificationTags);
			testCases.setPositive(positives);
			testCases.setFunctionalID(functionId);
			testCases.setFeatureID(featuId);
			testCases.setSortOrder(sortOrders);
			testCases.setConditionGroupID(conditGroupIDs);
			testCases.setRunnerID(runnerNameIDs);
			testCases.setCreatedBy(DataConstants.DEFAULT_USER);
			testCases.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			testCases.setUpdatedBy(DataConstants.DEFAULT_USER);
			testCases.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			testCasesId = inputService.insertTestCaseGetKey(testCases);
			ScenarioCaseMapping scenarioCase = new ScenarioCaseMapping();
			String testScenarioIDs = (String) jsonObject.get("testScenarioID");
			int ScenaId = Integer.parseInt(testScenarioIDs);
			scenarioCase.setTestCaseID(testCasesId);
			scenarioCase.setTestScenarioID(ScenaId);
			scenarioCase.setCreatedBy(DataConstants.DEFAULT_USER);
			scenarioCase.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			scenarioCase.setUpdatedBy(DataConstants.DEFAULT_USER);
			scenarioCase.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			inputService.insertScenarioCaseMappingGetKey(scenarioCase);
		} catch (Exception e) {
			LOG.warn("addTestCases(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestCases(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return testCasesId;
	}

	/**
	 * Gets the app functionality filter by app id.
	 * 
	 * @param appID
	 *            the app id
	 * @return the app functionality filter by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/getAppFunctionalNameByAppId", method = RequestMethod.POST)
	public @ResponseBody
	List<AppFunctionality> getAppFunctionalityFilterByAppId(@RequestBody String appID) throws ServiceException {
		int appId = Integer.parseInt(appID);
		List<AppFunctionality> AppId = new ArrayList<AppFunctionality>();
		try {
			AppId = mainService.getAppFunctionalityFilterByAppId(appId);
		} catch (ServiceException se) {
			LOG.warn("getAppFunctionalityFilterByAppId(String) - exception ignored", se);
		}
		return AppId;
	}

	/**
	 * Gets the all app features by functional id.
	 * 
	 * @param functionalID
	 *            the functional id
	 * @return the all app features by functional id
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/getAllAppFeaturesListByFunctionalId", method = RequestMethod.POST)
	public @ResponseBody
	List<AppFeature> getAllAppFeaturesByFunctionalId(@RequestBody String functionalID) throws ServiceException {
		int functionalId = Integer.parseInt(functionalID);
		List<AppFeature> appFeatures = mainService.getAllAppFeaturesByFunctionalId(functionalId);
		return appFeatures;
	}

	/**
	 * Gets the all test steps by test case id.
	 * 
	 * @param testCaseID
	 *            the test case id
	 * @return the all test steps by test case id
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/getParameterForTestStepsTable", method = RequestMethod.POST)
	public @ResponseBody
	List<TestStep> getAllTestStepsByTestCaseId(@RequestBody String testCaseID) throws ServiceException {
		int testCaseId = Integer.parseInt(testCaseID);
		List<TestStep> testSteps = inputService.getAllTestStepsByTestCaseId(testCaseId);
		return testSteps;
	}

	/**
	 * Gets the test scenario by app id.
	 * 
	 * @param appID
	 *            the app id
	 * @return the test scenario by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/getTestScenariosListByAppId", method = RequestMethod.POST)
	public @ResponseBody
	List<TestScenario> getTestScenarioByAppId(@RequestBody String appID) throws ServiceException {
		int appId = Integer.parseInt(appID);
		List<TestScenario> AppId = new ArrayList<TestScenario>();
		try {
			AppId = inputService.getTestScenarioByAppId(appId);
		} catch (ServiceException se) {
			LOG.warn("getTestScenarioByAppId(String) - exception ignored", se); //$NON-NLS-1$
		}
		return AppId;
	}

	/**
	 * Edits the test cases control.
	 * 
	 * @param testCase
	 *            the test case
	 * @return the test case
	 */
	@RequestMapping(value = "/editTestCases", method = RequestMethod.POST)
	public @ResponseBody
	TestCase editTestCasesControl(@RequestBody Map<String, Integer> testCase) {
		JSONObject jsonObject = new JSONObject(testCase);
		int testCaseId = (Integer) jsonObject.get("testCaseID");
		TestCase tCases = new TestCase();
		try {
			tCases = inputService.getTestCaseById(testCaseId);
		} catch (ServiceException se) {
			LOG.warn("editTestCasesControl(Map<String,Integer>) - exception ignored", se); //$NON-NLS-1$
		}
		return tCases;
	}

	/**
	 * Update test cases.
	 * 
	 * @param testCaseDatas
	 *            the test case datas
	 * @return the long
	 */
	@RequestMapping(value = "/updateTestCases", method = RequestMethod.POST)
	public @ResponseBody
	long updateTestCases(@RequestBody Map<String, String> testCaseDatas) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestCases(Map<String,String>) - start"); //$NON-NLS-1$
		}
		long update = 0;
		try {
			JSONObject jsonObject = new JSONObject(testCaseDatas);
			String testCaseNames = (String) jsonObject.get("caseName");
			String description = (String) jsonObject.get("description");
			String actives = (String) jsonObject.get("active");
			String classificationTags = (String) jsonObject.get("classificationTag");
			int sortOrders = (Integer) jsonObject.get("sortOrder");
			int caseIDs = (Integer) jsonObject.get("testCaseID");
			TestCase testCases = new TestCase();
			testCases.setCaseName(testCaseNames);
			testCases.setDescription(description);
			testCases.setActive(actives);
			testCases.setClassificationTag(classificationTags);
			testCases.setSortOrder(sortOrders);
			testCases.setTestCaseID(caseIDs);
			testCases.setUpdatedBy(DataConstants.DEFAULT_USER);
			testCases.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			update = inputService.updateTestCase(testCases);
		} catch (Exception e) {
			LOG.warn("updateTestCases(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestCases(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return update;
	}

	/**
	 * Gets the runner.
	 * 
	 * @return the runner
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/getRunnerNameList", method = RequestMethod.GET)
	public @ResponseBody
	List<Runner> getRunner() throws ServiceException {
		return mainService.getRunner();
	}

}
