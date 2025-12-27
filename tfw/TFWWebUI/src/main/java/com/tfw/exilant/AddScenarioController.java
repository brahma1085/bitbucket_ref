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
import com.exilant.tfw.pojo.input.SuiteScenarioMapping;
import com.exilant.tfw.pojo.input.TestCase;
import com.exilant.tfw.pojo.input.TestScenario;
import com.exilant.tfw.pojo.input.TestSuite;
import com.exilant.tfw.service.InputService;
import com.exilant.tfw.utils.DataConstants;

/**
 * The Class AddScenarioController.
 */
@Controller
public class AddScenarioController {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(AddScenarioController.class);

	/** The jb. */
	static StringBuffer jb = new StringBuffer();

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
	 * Adds the test scenario.
	 * 
	 * @param testScenarioData
	 *            the test scenario data
	 */
	@RequestMapping(value = "/addTestScenario", method = RequestMethod.POST)
	public @ResponseBody
	void addTestScenario(@RequestBody Map<String, String> testScenarioData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestScenario(Map<String,String>) - start");
		}
		try {
			JSONObject jsonObject = new JSONObject(testScenarioData);
			String testScenarioName = (String) jsonObject.get("testScenarioName");
			String description = (String) jsonObject.get("description");
			int sortOrders = (Integer) jsonObject.get("sortOrder");
			int appIDs = (Integer) jsonObject.get("appID");
			TestScenario testSce = new TestScenario();
			testSce.setTestScenarioName(testScenarioName);
			testSce.setDescription(description);
			testSce.setAppID(appIDs);
			testSce.setSortOrder(sortOrders);
			testSce.setCreatedBy(DataConstants.DEFAULT_USER);
			testSce.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			testSce.setUpdatedBy(DataConstants.DEFAULT_USER);
			testSce.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			long scenarioId = inputService.insertTestScenarioGetKey(testSce);
			SuiteScenarioMapping suiteSce = new SuiteScenarioMapping();
			int scenarioIds = (int) scenarioId;
			String testSuiteid = (String) jsonObject.get("testSuiteID");
			int testSuiteids = Integer.parseInt(testSuiteid);
			suiteSce.setTestScenarioID(scenarioIds);
			suiteSce.setTestSuiteID(testSuiteids);
			suiteSce.setCreatedBy(DataConstants.DEFAULT_USER);
			suiteSce.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			suiteSce.setUpdatedBy(DataConstants.DEFAULT_USER);
			suiteSce.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			inputService.insertSuiteScenarioMappingGetKey(suiteSce);
		} catch (Exception e) {
			LOG.warn("addTestScenario(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestScenario(Map<String,String>) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Edits the test scenarios control.
	 * 
	 * @param testScenario
	 *            the test scenario
	 * @return the test scenario
	 */
	@RequestMapping(value = "/editTestScenarios", method = RequestMethod.POST)
	public @ResponseBody
	TestScenario editTestScenariosControl(@RequestBody Map<String, Integer> testScenario) {
		JSONObject jsonObject = new JSONObject(testScenario);
		int testScenarioID = (Integer) jsonObject.get("testScenarioID");
		TestScenario tScenarios = new TestScenario();
		try {
			tScenarios = inputService.getTestScenarioById(testScenarioID);
		} catch (ServiceException se) {
			LOG.warn("editTestScenariosControl(Map<String,Integer>) - exception ignored", se); //$NON-NLS-1$
		}
		return tScenarios;
	}

	/**
	 * Gets the all test scenarios for application.
	 * 
	 * @return the all test scenarios for application
	 */
	@RequestMapping(value = "/getTestScenariosForApplication", method = RequestMethod.GET)
	public @ResponseBody
	List<TestScenario> getAllTestScenariosForApplication() {
		List<TestScenario> testScenarios = new ArrayList<TestScenario>();
		try {
			testScenarios = inputService.getTestScenario();
		} catch (ServiceException e) {
			LOG.warn("getAllTestScenariosForApplication() - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllTestScenariosForApplication() - end"); //$NON-NLS-1$
		}
		return testScenarios;
	}

	/**
	 * Update test scenarios.
	 * 
	 * @param testScenarioDatas
	 *            the test scenario datas
	 * @return the long
	 */
	@RequestMapping(value = "/updateTestScenarios", method = RequestMethod.POST)
	public @ResponseBody
	long updateTestScenarios(@RequestBody Map<String, String> testScenarioDatas) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestScenarios(Map<String,String>) - start"); //$NON-NLS-1$
		}
		long update = 0;
		try {
			JSONObject jsonObj = new JSONObject(testScenarioDatas);
			String testScenarioName = (String) jsonObj.get("testScenarioName");
			String description = (String) jsonObj.get("description");
			int scenarioIDs = (Integer) jsonObj.get("testScenarioID");
			TestScenario testSce = new TestScenario();
			testSce.setTestScenarioName(testScenarioName);
			testSce.setDescription(description);
			testSce.setTestScenarioID(scenarioIDs);
			testSce.setUpdatedBy(DataConstants.DEFAULT_USER);
			testSce.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			update = inputService.updateTestScenarioData(testSce);
		} catch (Exception e) {
			LOG.warn("updateTestScenarios(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestScenarios(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return update;
	}

	/**
	 * Gets the all test cases by test scenario id.
	 * 
	 * @param testScenarioID
	 *            the test scenario id
	 * @return the all test cases by test scenario id
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/getParameterForTestCaseTable", method = RequestMethod.POST)
	public @ResponseBody
	List<TestCase> getAllTestCasesByTestScenarioId(@RequestBody String testScenarioID) throws ServiceException {
		int testScenarioId = Integer.parseInt(testScenarioID);
		List<TestCase> testCases = inputService.getAllTestCasesByTestScenarioId(testScenarioId);
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllTestCasesByTestScenarioId(String) - end"); //$NON-NLS-1$
		}
		return testCases;
	}

	/**
	 * Gets the test suite by app id.
	 * 
	 * @param appID
	 *            the app id
	 * @return the test suite by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/getTestSuitesListFilterByAppId", method = RequestMethod.POST)
	public @ResponseBody
	List<TestSuite> getTestSuiteByAppId(@RequestBody String appID) throws ServiceException {
		int appId = Integer.parseInt(appID);
		List<TestSuite> AppId = new ArrayList<TestSuite>();
		try {
			AppId = inputService.getTestSuiteByAppId(appId);
		} catch (ServiceException se) {
			LOG.warn("getTestSuiteByAppId(String) - exception ignored", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteByAppId(String) - end"); //$NON-NLS-1$
		}
		return AppId;
	}

	/**
	 * Gets the test scenario by app id.
	 * 
	 * @param appID
	 *            the app id
	 * @return the test scenario by app id
	 */
	@RequestMapping(value = "/getTestScenariosBoxByAppId", method = RequestMethod.POST)
	public @ResponseBody
	List<TestScenario> getTestScenarioByAppId(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioByAppId(String) - start"); //$NON-NLS-1$
		}
		int appId = Integer.parseInt(appID);
		List<TestScenario> AppId = new ArrayList<TestScenario>();
		try {
			AppId = inputService.getTestScenarioByAppId(appId);
		} catch (Exception se) {
			LOG.warn("getTestScenarioByAppId(String) - exception ignored", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioByAppId(String) - end"); //$NON-NLS-1$
		}
		return AppId;
	}

}