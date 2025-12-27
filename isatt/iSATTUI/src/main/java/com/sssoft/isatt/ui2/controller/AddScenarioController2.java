package com.sssoft.isatt.ui2.controller;

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

import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.def.TestScenarioUI;
import com.sssoft.isatt.data.pojo.input.ScenarioCaseMapping;
import com.sssoft.isatt.data.pojo.input.SuiteScenarioMapping;
import com.sssoft.isatt.data.pojo.input.TestCase;
import com.sssoft.isatt.data.pojo.input.TestScenario;
import com.sssoft.isatt.data.pojo.input.TestSuite;
import com.sssoft.isatt.data.service.InputService;
import com.sssoft.isatt.data.utils.DataConstants;

/**
 * The Class AddScenarioController2.
 */
@Controller
public class AddScenarioController2 {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AddScenarioController2.class);

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
	 * @return the int
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addTestScenarioITAP", method = RequestMethod.POST)
	public @ResponseBody
	int addTestScenario(@RequestBody Map<String, String> testScenarioData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestScenario(Map<String,String>) - start"); //$NON-NLS-1$
		}

		long scenarioId = 0;
		LOG.info("Entering addTestScenario controller !!!");
		try {

			LOG.info("Printing data recieved from client :: " + testScenarioData);
			JSONObject jsonObject = new JSONObject(testScenarioData);

			String testScenarioName = (String) jsonObject.get("testScenarioName");
			String description = (String) jsonObject.get("description");
			String sortOrder = (String) jsonObject.get("sortOrder");
			int sOrder = Integer.parseInt(sortOrder);
			// int sortOrders = (Integer) jsonObject.get("sortOrder");
			int appIDs = (Integer) jsonObject.get("appID");
			TestScenario testSce = new TestScenario();

			testSce.setTestScenarioName(testScenarioName);
			testSce.setDescription(description);
			testSce.setAppID(appIDs);
			testSce.setSortOrder(sOrder);
			testSce.setCreatedBy(DataConstants.DEFAULT_USER);
			testSce.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			testSce.setUpdatedBy(DataConstants.DEFAULT_USER);
			testSce.setUpdatedDateTime(DataConstants.DEFAULT_DATE);

			LOG.info("TestScenario Pojo String ::" + testSce.toString());
			scenarioId = inputService.insertTestScenarioGetKey(testSce);
			LOG.info("scenario ID ::" + scenarioId);

			int scenarioIds = (int) scenarioId;

			List<List<Integer>> linkedSuitesfromPopUp = (List<List<Integer>>) jsonObject.get("linkedSuitesfromPopUp");
			List<List<Integer>> CasesSelectedfromPopUP = (List<List<Integer>>) jsonObject.get("linkedCasesfromPopUp");
			if (linkedSuitesfromPopUp != null) {
				for (List<Integer> testSuiteList : linkedSuitesfromPopUp) {
					LOG.info(" testSuiteList " + testSuiteList);
					for (int i = 0; i < testSuiteList.size(); i++) {
						LOG.info("  testSuiteList.get(i)  " + testSuiteList.get(i));
						int testSuite = testSuiteList.get(i);
						if (testSuite != 0) {
							SuiteScenarioMapping suiteSce = new SuiteScenarioMapping();
							suiteSce.setTestScenarioID(scenarioIds);
							suiteSce.setTestSuiteID(testSuite);
							suiteSce.setCreatedBy(DataConstants.DEFAULT_USER);
							suiteSce.setCreatedDateTime(DataConstants.DEFAULT_DATE);
							suiteSce.setUpdatedBy(DataConstants.DEFAULT_USER);
							suiteSce.setUpdatedDateTime(DataConstants.DEFAULT_DATE);

							LOG.info("SuiteScenarioMapping Pojo String ::" + suiteSce.toString());
							inputService.insertSuiteScenarioMappingGetKey(suiteSce);
						}
					}
				}
			}
			if (CasesSelectedfromPopUP != null) {
				for (List<Integer> testCaseList : CasesSelectedfromPopUP) {
					LOG.info(" testCaseList " + testCaseList);
					for (int i = 0; i < testCaseList.size(); i++) {
						LOG.info("  testCaseList.get(i)  " + testCaseList.get(i));
						int testCase = testCaseList.get(i);
						if (testCase != 0) {
							ScenarioCaseMapping scenarioCase = new ScenarioCaseMapping();

							scenarioCase.setTestScenarioID(scenarioIds);
							scenarioCase.setTestCaseID(testCase);

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
		} catch (Exception e) {
			LOG.error("addTestScenario(Map<String,String>)", e); //$NON-NLS-1$

		}
		int returnint = (int) scenarioId;
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestScenario(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return returnint;
	}

	/**
	 * Gets the test scenario by app id.
	 * 
	 * @param appID
	 *            the app id
	 * @return the test scenario by app id
	 */
	@RequestMapping(value = "/getTestScenariosBoxByAppIdITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<TestScenario> getTestScenarioByAppId(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioByAppId(String) - start"); //$NON-NLS-1$
		}
		LOG.info("Inside getTestScenarioByAppId method");
		int appId = Integer.parseInt(appID);
		LOG.info("appID to retrive :: " + appId);
		List<TestScenario> AppId = new ArrayList<TestScenario>();
		try {
			LOG.info("inside getTestScenarioByAppId at appId");
			AppId = inputService.getTestScenarioByAppId(appId);
			LOG.info("scenario list ::" + AppId.toString());
		} catch (ServiceException se) {
			LOG.error("getTestScenarioByAppId(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioByAppId(String) - end"); //$NON-NLS-1$
		}
		return AppId;
	}

	/**
	 * Gets the test suite by app id itap.
	 * 
	 * @param appID
	 *            the app id
	 * @return the test suite by app id itap
	 */
	@RequestMapping(value = "/getTestSuitesListFilterByAppIdITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<TestSuite> getTestSuiteByAppIdITAP(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteByAppIdITAP(String) - start"); //$NON-NLS-1$
		}
		LOG.info("Inside getTestSuiteFilterByAppId method");
		int appId = Integer.parseInt(appID);
		LOG.info("appID to retrive :: " + appId);
		List<TestSuite> AppId = new ArrayList<TestSuite>();
		try {
			LOG.info("inside getTestSuiteFilterByAppId at appId");
			AppId = inputService.getTestSuiteByAppId(appId);
			LOG.info("TestSuite list ::" + AppId.toString());
		} catch (ServiceException se) {
			LOG.error("getTestSuiteByAppIdITAP(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteByAppIdITAP(String) - end"); //$NON-NLS-1$
		}
		return AppId;
	}

	/**
	 * Gets the test scenario list filter by app id get count.
	 * 
	 * @param appID
	 *            the app id
	 * @return the test scenario list filter by app id get count
	 */
	@RequestMapping(value = "/getTestScenariosListsFilterByAppIdAndGetCount", method = RequestMethod.POST)
	public @ResponseBody
	List<TestScenarioUI> getTestScenarioListFilterByAppIdGetCount(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioListFilterByAppIdGetCount(String) - start"); //$NON-NLS-1$
		}
		int appId = Integer.parseInt(appID);
		LOG.info("appID to retrive :: " + appId);
		List<TestScenarioUI> AppId = new ArrayList<TestScenarioUI>();
		try {
			AppId = inputService.getTestScenarioUIByAppID(appId);
		} catch (ServiceException se) {
			LOG.error("getTestScenarioListFilterByAppIdGetCount(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioListFilterByAppIdGetCount(String) - end"); //$NON-NLS-1$
		}
		return AppId;
	}

	/**
	 * Gets the test scenarios with no case count.
	 * 
	 * @param appID
	 *            the app id
	 * @return the test scenarios with no case count
	 */
	@RequestMapping(value = "/getTestScenariosWithNoCaseCount", method = RequestMethod.POST)
	public @ResponseBody
	List<TestScenario> getTestScenariosWithNoCaseCount(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenariosWithNoCaseCount(String) - start"); //$NON-NLS-1$
		}
		int appId = Integer.parseInt(appID);
		List<TestScenario> scenList = new ArrayList<TestScenario>();
		try {
			List<TestScenario> scenId = inputService.getTestScenarioByAppId(appId);
			for (TestScenario scen : scenId) {
				List<ScenarioCaseMapping> scenarioCaseIDs = inputService.getScenarioCaseMappingsByScenarioId(scen.getTestScenarioID());
				List<SuiteScenarioMapping> suiteScenarioIDs = inputService.getSuiteScenarioMappingByScenarioId(scen.getTestScenarioID());
				if (scenarioCaseIDs.size() == 0 || suiteScenarioIDs.size() == 0) {
					scenList.add(scen);
				}
			}
		} catch (Exception se) {
			LOG.error("getTestScenariosWithNoCaseCount(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenariosWithNoCaseCount(String) - end"); //$NON-NLS-1$
		}
		return scenList;
	}

	/**
	 * Edits the test scenarios control.
	 * 
	 * @param testScenario
	 *            the test scenario
	 * @return the test scenario ui
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/editTestScenariosITAP", method = RequestMethod.POST)
	public @ResponseBody
	TestScenarioUI editTestScenariosControl(@RequestBody Map<String, Integer> testScenario) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("editTestScenariosControl(Map<String,Integer>) - start"); //$NON-NLS-1$
		}

		LOG.info("Inside editTestScenarios method");
		LOG.info("Printing data recieved from client :: " + testScenario);
		JSONObject jsonObject = new JSONObject(testScenario);
		int testScenarioID = (Integer) jsonObject.get("testScenarioID");
		LOG.info("TestScenario ID to edit :: " + testScenarioID);
		TestScenarioUI testScenarioUI = new TestScenarioUI();
		TestScenario tScenarios = new TestScenario();
		try {
			tScenarios = inputService.getTestScenarioById(testScenarioID);
			LOG.info("TestScenarios Name:: " + tScenarios.getTestScenarioName());

			testScenarioUI.setTestScenarioID(tScenarios.getTestScenarioID());
			testScenarioUI.setTestScenarioName(tScenarios.getTestScenarioName());
			testScenarioUI.setDescription(tScenarios.getDescription());
			testScenarioUI.setSortOrder(tScenarios.getSortOrder());

			List<TestSuite> testSceList = new ArrayList<TestSuite>();
			List<SuiteScenarioMapping> suiteSceMap = inputService.getSuiteScenarioMappingByScenarioId(testScenarioID);
			for (SuiteScenarioMapping suiteScenarioMapping : suiteSceMap) {
				testSceList.add(suiteScenarioMapping.getTestSuite());
			}
			testScenarioUI.setSuiteList(testSceList);

			List<TestCase> testCaseList = new ArrayList<TestCase>();
			List<ScenarioCaseMapping> scenarioCaseMap = inputService.getScenarioCaseMappingsByScenarioId(testScenarioID);
			for (ScenarioCaseMapping scenarioCaseMapping : scenarioCaseMap) {
				testCaseList.add(scenarioCaseMapping.getTestCase());
			}

			testScenarioUI.setCaseList(testCaseList);

		} catch (ServiceException se) {
			LOG.error("editTestScenariosControl(Map<String,Integer>)", se); //$NON-NLS-1$

		}
		LOG.info("testScenarioUI List inside edit scenario :: " + testScenarioUI.toString());

		if (LOG.isDebugEnabled()) {
			LOG.debug("editTestScenariosControl(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return testScenarioUI;

	}

	/**
	 * Update test scenarios.
	 * 
	 * @param testScenarioDatas
	 *            the test scenario datas
	 * @return the long
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateTestScenariosITAP", method = RequestMethod.POST)
	public @ResponseBody
	long updateTestScenarios(@RequestBody Map<String, String> testScenarioDatas) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestScenarios(Map<String,String>) - start"); //$NON-NLS-1$
		}

		long update = 0;

		try {
			JSONObject jsonObject = new JSONObject(testScenarioDatas);

			String testScenarioName = (String) jsonObject.get("testScenarioName");
			String description = (String) jsonObject.get("description");
			int scenarioIDs = (Integer) jsonObject.get("testScenarioID");

			int sOrder = (Integer) jsonObject.get("sortOrder");

			TestScenario testSce = new TestScenario();
			testSce.setTestScenarioName(testScenarioName);
			testSce.setDescription(description);
			testSce.setSortOrder(sOrder);
			testSce.setTestScenarioID(scenarioIDs);
			testSce.setUpdatedBy(DataConstants.DEFAULT_USER);
			testSce.setUpdatedDateTime(DataConstants.DEFAULT_DATE);

			update = inputService.updateTestScenarioData(testSce);

			inputService.deleteScenarioCaseMappingById(scenarioIDs);
			inputService.deleteSuiteScenarioMappingById(scenarioIDs);

			List<List<Integer>> linkedSuitesfromPopUp = (List<List<Integer>>) jsonObject.get("linkedSuitesfromPopUp");
			List<List<Integer>> CasesSelectedfromPopUP = (List<List<Integer>>) jsonObject.get("linkedCasesfromPopUp");
			for (List<Integer> testSuiteList : linkedSuitesfromPopUp) {
				LOG.info(" testSuiteList " + testSuiteList);
				for (int i = 0; i < testSuiteList.size(); i++) {
					LOG.info("  testSuiteList.get(i)  " + testSuiteList.get(i));
					int testSuite = testSuiteList.get(i);
					if (testSuite != 0) {
						SuiteScenarioMapping suiteSce = new SuiteScenarioMapping();
						suiteSce.setTestScenarioID(scenarioIDs);
						suiteSce.setTestSuiteID(testSuite);
						suiteSce.setCreatedBy(DataConstants.DEFAULT_USER);
						suiteSce.setCreatedDateTime(DataConstants.DEFAULT_DATE);
						suiteSce.setUpdatedBy(DataConstants.DEFAULT_USER);
						suiteSce.setUpdatedDateTime(DataConstants.DEFAULT_DATE);

						LOG.info("SuiteScenarioMapping Pojo String ::" + suiteSce.toString());
						inputService.insertSuiteScenarioMappingGetKey(suiteSce);
					}
				}
			}
			for (List<Integer> testCaseList : CasesSelectedfromPopUP) {
				LOG.info(" testCaseList " + testCaseList);
				for (int i = 0; i < testCaseList.size(); i++) {
					LOG.info("  testCaseList.get(i)  " + testCaseList.get(i));
					int testCase = testCaseList.get(i);
					if (testCase != 0) {
						ScenarioCaseMapping scenarioCase = new ScenarioCaseMapping();

						scenarioCase.setTestScenarioID(scenarioIDs);
						scenarioCase.setTestCaseID(testCase);

						scenarioCase.setCreatedBy(DataConstants.DEFAULT_USER);
						scenarioCase.setCreatedDateTime(DataConstants.DEFAULT_DATE);
						scenarioCase.setUpdatedBy(DataConstants.DEFAULT_USER);
						scenarioCase.setUpdatedDateTime(DataConstants.DEFAULT_DATE);

						LOG.info("ScenarioCaseMapping Pojo String ::" + scenarioCase.toString());
						inputService.insertScenarioCaseMappingGetKey(scenarioCase);
					}
				}
			}

		} catch (Exception e) {
			LOG.error("updateTestScenarios(Map<String,String>)", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestScenarios(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return update;
	}

	/**
	 * Gets the test scenario i ds itap.
	 * 
	 * @param testScenarioName
	 *            the test scenario name
	 * @return the test scenario i ds itap
	 */
	@RequestMapping(value = "/getTestScenarioIDsITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<Integer> getTestScenarioIDsITAP(@RequestBody Map<String, String> testScenarioName) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioIDsITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}
		JSONObject jsonObject = new JSONObject(testScenarioName);
		List<Integer> testScenarioIDs = new ArrayList<Integer>();
		try {
			@SuppressWarnings("unchecked")
			List<String> testScenarioNmes = (ArrayList<String>) jsonObject.get("Scenario");
			for (int i = 0; i < testScenarioNmes.size(); i++) {
				if (!"".equals(testScenarioNmes.get(i))) {
					TestScenario testScenario = inputService.getTestScenarioByName(testScenarioNmes.get(i));
					testScenarioIDs.add(testScenario.getTestScenarioID());
				}
			}
		} catch (ServiceException e) {
			LOG.warn("getTestScenarioIDsITAP(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioIDsITAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return testScenarioIDs;
	}

}
