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

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.def.TestPlanUI;
import com.sssoft.isatt.data.pojo.input.PlanSuiteMapping;
import com.sssoft.isatt.data.pojo.input.TestPlan;
import com.sssoft.isatt.data.pojo.input.TestSuite;
import com.sssoft.isatt.data.service.InputService;
import com.sssoft.isatt.data.utils.DataConstants;

/**
 * The Class AddTestPlanController2.
 */
@Controller
public class AddTestPlanController2 {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AddTestPlanController2.class);

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
	 * Adds the test plan itap.
	 * 
	 * @param testPlanData
	 *            the test plan data
	 * @return the int
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addTestPlanITAP", method = RequestMethod.POST)
	public @ResponseBody
	int addTestPlanITAP(@RequestBody Map<String, String> testPlanData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestPlanITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}
		JSONObject jsonObject = new JSONObject(testPlanData);
		int testPlanId = 0;
		try {
			String testPlanName = (String) jsonObject.get("testPlanName");
			String description = (String) jsonObject.get("description");
			int appIDs = (Integer) jsonObject.get("appID");
			TestPlan testPlan = new TestPlan();
			testPlan.setPlanName(testPlanName);
			testPlan.setDescription(description);
			testPlan.setAppID(appIDs);
			testPlan.setCreatedBy(DataConstants.DEFAULT_USER);
			testPlan.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			testPlan.setUpdatedBy(DataConstants.DEFAULT_USER);
			testPlan.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			LOG.info("TestPlan Pojo String ::" + testPlan.toString());
			testPlanId = inputService.insertTestPlanGetKey(testPlan);
			List<List<Integer>> testSuiteIdFrompopUP = (List<List<Integer>>) jsonObject.get("linkedSuitespopUP");
			if (testSuiteIdFrompopUP != null) {
				for (List<Integer> testSuiteList : testSuiteIdFrompopUP) {
					LOG.info(" testSuiteList " + testSuiteList);
					for (int i = 0; i < testSuiteList.size(); i++) {
						LOG.info("  testSuiteList.get(i)  " + testSuiteList.get(i));
						int testSuite = testSuiteList.get(i);
						if (testSuite != 0) {
							PlanSuiteMapping planSuiteMapping = new PlanSuiteMapping();
							planSuiteMapping.setTestPlanID(testPlanId);
							planSuiteMapping.setTestSuiteID(testSuite);
							planSuiteMapping.setCreatedBy(DataConstants.DEFAULT_USER);
							planSuiteMapping.setCreatedDateTime(DataConstants.DEFAULT_DATE);
							planSuiteMapping.setUpdatedBy(DataConstants.DEFAULT_USER);
							planSuiteMapping.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
							inputService.insertPlanSuiteMapping(planSuiteMapping);
						}
					}
				}
				LOG.info("TestPlan ID :::::::::::" + testPlanId);
			}
		} catch (ServiceException e) {
			LOG.error("addTestPlanITAP(Map<String,String>)", e); //$NON-NLS-1$
		} catch (JSONException e) {
			LOG.error("addTestPlanITAP(Map<String,String>)", e); //$NON-NLS-1$
		}
		int returnint = (int) testPlanId;
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestPlanITAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return returnint;
	}

	/**
	 * Gets the test plan by app id.
	 * 
	 * @param appID
	 *            the app id
	 * @return the test plan by app id
	 */
	@RequestMapping(value = "/getTestPlanBoxByAppIdITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<TestPlan> getTestPlanByAppId(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanByAppId(String) - start"); //$NON-NLS-1$
		}
		int appId = Integer.parseInt(appID);
		List<TestPlan> testPlans = new ArrayList<TestPlan>();
		try {
			testPlans = inputService.getTestPlanObjByAppId(appId);
			LOG.info(testPlans.size() + "Plan list ::" + testPlans.toString());
		} catch (ServiceException se) {
			LOG.error("getTestPlanByAppId(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanByAppId(String) - end"); //$NON-NLS-1$
		}
		return testPlans;
	}

	/**
	 * Gets the test plan by app id with suite count.
	 * 
	 * @param appID
	 *            the app id
	 * @return the test plan by app id with suite count
	 */
	@RequestMapping(value = "/getTestPlanBoxByAppIdITAPWithSuiteCount", method = RequestMethod.POST)
	public @ResponseBody
	List<TestPlanUI> getTestPlanByAppIdWithSuiteCount(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanByAppIdWithSuiteCount(String) - start"); //$NON-NLS-1$
		}
		int appId = Integer.parseInt(appID);
		List<TestPlan> testPlans = new ArrayList<TestPlan>();
		List<TestPlanUI> testPlanUIList = new ArrayList<TestPlanUI>();
		List<PlanSuiteMapping> planSuiteID = new ArrayList<PlanSuiteMapping>();
		try {
			testPlans = inputService.getTestPlanObjByAppId(appId);
			for (TestPlan testplan : testPlans) {
				planSuiteID = inputService.getPlanSuiteMappingByPlanId(testplan.getTestPlanID());
				LOG.info("plan suite id count" + planSuiteID.size());
				TestPlanUI tp = new TestPlanUI();
				tp.setTestPlanDesc(testplan.getDescription());
				tp.setTestPlanId(testplan.getTestPlanID());
				tp.setTestPlanName(testplan.getPlanName());
				tp.setTestSuiteCount(planSuiteID.size());
				testPlanUIList.add(tp);
			}
		} catch (ServiceException se) {
			LOG.error("getTestPlanByAppIdWithSuiteCount(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanByAppIdWithSuiteCount(String) - end"); //$NON-NLS-1$
		}
		return testPlanUIList;
	}

	/**
	 * Gets the test plan i ds itap.
	 * 
	 * @param testPlanName
	 *            the test plan name
	 * @return the test plan i ds itap
	 */
	@RequestMapping(value = "/getTestPlanIDsITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<Integer> getTestPlanIDsITAP(@RequestBody Map<String, String> testPlanName) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanIDsITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}
		JSONObject jsonObject = new JSONObject(testPlanName);
		List<Integer> testPlanIDs = new ArrayList<Integer>();
		try {
			@SuppressWarnings("unchecked")
			List<String> testPlanNmes = (ArrayList<String>) jsonObject.get("Plans");
			for (int i = 0; i < testPlanNmes.size(); i++) {
				if(!testPlanNmes.get(i).equals("")){
				TestPlan testPlan = inputService.getTestPlanByName(testPlanNmes.get(i));
				testPlanIDs.add(testPlan.getTestPlanID());
				}
			}
		} catch (JSONException e) {
			LOG.warn("getTestPlanIDsITAP(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		} catch (ServiceException e) {
			LOG.warn("getTestPlanIDsITAP(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanIDsITAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return testPlanIDs;
	}

	/**
	 * Gets the test plan with no suite count.
	 * 
	 * @param appID
	 *            the app id
	 * @return the test plan with no suite count
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/getTestPlanWithNoSuiteCount", method = RequestMethod.POST)
	public @ResponseBody
	List<TestPlan> getTestPlanWithNoSuiteCount(@RequestBody String appID) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanWithNoSuiteCount(String) - start"); //$NON-NLS-1$
		}
		int appId = Integer.parseInt(appID);
		List<TestPlan> testPlans = new ArrayList<TestPlan>();
		List<TestPlan> testPlanList = new ArrayList<TestPlan>();
		List<PlanSuiteMapping> planSuiteID = new ArrayList<PlanSuiteMapping>();
		try {
			testPlans = inputService.getTestPlanObjByAppId(appId);
			for (TestPlan testplan : testPlans) {
				planSuiteID = inputService.getPlanSuiteMappingByPlanId(testplan.getTestPlanID());
				LOG.info("plan suite id count" + planSuiteID.size());
				if (planSuiteID.size() == 0) {
					testPlanList.add(testplan);
				}
			}
		} catch (ServiceException se) {
			LOG.error("getTestPlanWithNoSuiteCount(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanWithNoSuiteCount(String) - end"); //$NON-NLS-1$
		}
		return testPlanList;
	}

	/**
	 * Gets the test plan by plan id.
	 * 
	 * @param planID
	 *            the plan id
	 * @return the test plan by plan id
	 */
	@RequestMapping(value = "/editTestPlanBoxByPlanIdITAP", method = RequestMethod.POST)
	public @ResponseBody
	TestPlanUI getTestPlanByPlanId(@RequestBody String planID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanByPlanId(String) - start"); //$NON-NLS-1$
		}
		List<PlanSuiteMapping> planSuiteMap = new ArrayList<PlanSuiteMapping>();
		TestPlan testPlan = new TestPlan();
		TestPlanUI testPlanUI = new TestPlanUI();
		List<TestSuite> suiteList = new ArrayList<TestSuite>();
		try {
			int planId = Integer.parseInt(planID);
			testPlan = inputService.getTestPlanById(planId);
			planSuiteMap = inputService.getPlanSuiteMappingByPlanId(planId);
			testPlanUI.setTestPlanId(testPlan.getTestPlanID());
			testPlanUI.setTestPlanDesc(testPlan.getDescription());
			testPlanUI.setTestPlanName(testPlan.getPlanName());
			for (PlanSuiteMapping planSuit : planSuiteMap) {
				suiteList.add(planSuit.getTestSuite());
			}
			testPlanUI.setSuiteList(suiteList);
		} catch (ServiceException se) {
			LOG.error("getTestPlanByPlanId(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanByPlanId(String) - end"); //$NON-NLS-1$
		}
		return testPlanUI;
	}

	/**
	 * Update test plan itap.
	 * 
	 * @param testPlanData
	 *            the test plan data
	 * @return the int
	 */

	@RequestMapping(value = "/updateTestPlanITAP", method = RequestMethod.POST)
	public @ResponseBody
	int updateTestPlanITAP(@RequestBody Map<String, String> testPlanData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestPlanITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}
		JSONObject jsonObject = new JSONObject(testPlanData);
		long testPlanId = 0;
		try {
			String testPlanName = (String) jsonObject.get("testPlanName");
			String description = (String) jsonObject.get("description");
			int appIDs = (Integer) jsonObject.get("appID");
			int testPlanID = (Integer) jsonObject.get("testPlanId");
			TestPlan testPlan = new TestPlan();
			testPlan.setTestPlanID(testPlanID);
			testPlan.setPlanName(testPlanName);
			testPlan.setDescription(description);
			testPlan.setAppID(appIDs);
			testPlan.setUpdatedBy(DataConstants.DEFAULT_USER);
			testPlan.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			LOG.info("TestPlan Pojo String ::" + testPlan.toString());
			testPlanId = inputService.updateTestPlanData(testPlan);
			inputService.deletePlanSuiteMappingById(testPlanID);
			@SuppressWarnings("unchecked")
			List<List<Integer>> testSuiteIdFrompopUP = (List<List<Integer>>) jsonObject.get("linkedSuitespopUP");
			for (List<Integer> testSuiteList : testSuiteIdFrompopUP) {
				LOG.info(" testSuiteList " + testSuiteList);
				for (int i = 0; i < testSuiteList.size(); i++) {
					LOG.info("  testSuiteList.get(i)  " + testSuiteList.get(i));
					int testSuite = testSuiteList.get(i);
					if (testSuite != 0) {
						PlanSuiteMapping planSuiteMapping = new PlanSuiteMapping();
						planSuiteMapping.setTestPlanID(testPlanID);
						planSuiteMapping.setTestSuiteID(testSuite);
						planSuiteMapping.setCreatedBy(DataConstants.DEFAULT_USER);
						planSuiteMapping.setCreatedDateTime(DataConstants.DEFAULT_DATE);
						planSuiteMapping.setUpdatedBy(DataConstants.DEFAULT_USER);
						planSuiteMapping.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
						inputService.insertPlanSuiteMapping(planSuiteMapping);
					}
				}
			}
			LOG.info("TestPlan ID :::::::::::" + testPlanId);
		} catch (ServiceException e) {
			LOG.error("updateTestPlanITAP(Map<String,String>)", e);
		} catch (JSONException e) {
			LOG.error("updateTestPlanITAP(Map<String,String>)", e);
		}
		int returnint = (int) testPlanId;
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestPlanITAP(Map<String,String>) - end");
		}
		return returnint;
	}
}
