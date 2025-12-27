package com.sssoft.isatt.ui1.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.input.PlanSuiteMapping;
import com.sssoft.isatt.data.pojo.input.TestSuite;
import com.sssoft.isatt.data.service.InputService;

/**
 * The Class AddTestSuiteController.
 * 
 * @author ramyaramesh
 */
@Controller
public class AddTestSuiteController {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(AddTestSuiteController.class);

	/**
	 * Instantiates a new adds the test suite controller.
	 */
	public AddTestSuiteController() {
	}

	/** The input service. */
	private InputService inputService;

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
	 * Adds the test suite control.
	 * 
	 * @param testSuiteData
	 *            the test suite data
	 * @return the long
	 */
	@RequestMapping(value = "/addTestSuite", method = RequestMethod.POST)
	public @ResponseBody
	long addTestSuiteControl(@RequestBody Map<String, String> testSuiteData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestSuiteControl(Map<String,String>) - start"); //$NON-NLS-1$
		}
		TestSuite testSuite = new TestSuite();
		PlanSuiteMapping planSuiteMapping = new PlanSuiteMapping();
		long tSuiteId = 0;
		long planSuiteId = 0;
		String testSuiteName = "";
		String description = "";
		int testPlanId = 0;
		int appId = 0;
		String sortOrder = "";
		try {
			JSONObject jsonObject = new JSONObject(testSuiteData);
			testSuiteName = (String) jsonObject.get("suiteName");
			description = (String) jsonObject.get("description");
			testPlanId = (Integer) jsonObject.get("testPlanID");
			appId = (Integer) jsonObject.get("appID");
			sortOrder = (String) jsonObject.get("sortOrder");
			int sOrder = Integer.parseInt(sortOrder);
			testSuite.setSuiteName(testSuiteName);
			testSuite.setDescription(description);
			testSuite.setSortOrder(sOrder);
			testSuite.setAppID(appId);
			tSuiteId = inputService.insertTestSuiteGetKey(testSuite);
			int suiteId = (int) tSuiteId;
			planSuiteMapping.setTestPlanID(testPlanId);
			planSuiteMapping.setTestSuiteID(suiteId);
			planSuiteMapping.setCreatedBy("System");
			planSuiteMapping.setCreatedDateTime(new Date(0));
			planSuiteMapping.setUpdatedBy("System");
			planSuiteMapping.setUpdatedDateTime(new Date(0));
			planSuiteId = inputService.insertPlanSuiteMapping(planSuiteMapping);
		} catch (Exception e) {
			LOG.warn("addTestSuiteControl(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestSuiteControl(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return planSuiteId;
	}

	/**
	 * Gets the form data.
	 * 
	 * @param jb
	 *            the jb
	 * @return the form data
	 */
	public TestSuite getFormData(StringBuffer jb) {
		TestSuite tSuite = new TestSuite();
		ObjectMapper mapper = new ObjectMapper();
		try {
			tSuite = mapper.readValue(jb.toString(), TestSuite.class);
		} catch (IOException e) {
			LOG.warn("getFormData(StringBuffer) - exception ignored", e); //$NON-NLS-1$
		}
		return tSuite;
	}

	/**
	 * Gets the test suites for test plan.
	 * 
	 * @param testPlanId
	 *            the test plan id
	 * @return the test suites for test plan
	 */
	@RequestMapping(value = "/getTestSuitesForplan", method = RequestMethod.POST)
	public @ResponseBody
	List<PlanSuiteMapping> getTestSuitesForTestPlan(@RequestBody Map<String, Integer> testPlanId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuitesForTestPlan(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		int id = 0;
		List<PlanSuiteMapping> planSuiteMapping = new ArrayList<PlanSuiteMapping>();
		try {
			JSONObject jsonObj = new JSONObject(testPlanId);
			id = (Integer) jsonObj.get("testPlanID");
			planSuiteMapping = inputService.getPlanSuiteMappingByPlanId(id);
		} catch (Exception se) {
			LOG.warn("getTestSuitesForTestPlan(Map<String,Integer>) - exception ignored", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuitesForTestPlan(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return planSuiteMapping;
	}

	/**
	 * Edits the test suite.
	 * 
	 * @param testSuite
	 *            the test suite
	 * @return the test suite
	 */
	@RequestMapping(value = "/editTestSuite", method = RequestMethod.POST)
	public @ResponseBody
	TestSuite editTestSuite(@RequestBody Map<String, Integer> testSuite) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("editTestSuite(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		int id = (Integer) testSuite.get("testSuiteID");
		TestSuite tSuite = new TestSuite();
		try {
			tSuite = inputService.getTestSuiteById(id);
		} catch (ServiceException se) {
			LOG.warn("editTestSuite(Map<String,Integer>) - exception ignored", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("editTestSuite(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return tSuite;
	}

	/**
	 * Update test suite.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	@RequestMapping(value = "/updateTestSuite", method = RequestMethod.POST)
	public @ResponseBody
	void updateTestSuite(HttpServletRequest request, HttpServletResponse response) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestSuite(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			TestSuite testSuite = new TestSuite();
			ObjectMapper mapper = new ObjectMapper();
			testSuite = mapper.readValue(jb.toString(), TestSuite.class);
			testSuite.setUpdatedBy("system");
			testSuite.setUpdatedDateTime(new Date(0));
			inputService.updateTestSuiteData(testSuite);
		} catch (Exception e) {
			LOG.warn("updateTestSuite(HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestSuite(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
	}
}