package com.tfw.exilant;

import java.io.BufferedReader;
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

import com.exilant.tfw.pojo.input.TestPlan;
import com.exilant.tfw.service.InputService;
import com.exilant.tfw.utils.DataConstants;

/**
 * The Class AddTestPlanController.
 */
@Controller
public class AddTestPlanController {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(AddTestPlanController.class);

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
	 * Adds the test plan control.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the long
	 */
	@RequestMapping(value = "/addTestPlan", method = RequestMethod.POST)
	public @ResponseBody
	long addTestPlanControl(HttpServletRequest request, HttpServletResponse response) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestPlanControl(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		StringBuffer jb = new StringBuffer();
		String line = null;
		long i = 0;
		TestPlan tp = new TestPlan();
		ObjectMapper mapper = new ObjectMapper();
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			tp = mapper.readValue(jb.toString(), TestPlan.class);
			tp.setCreatedBy(DataConstants.DEFAULT_USER);
			tp.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			tp.setUpdatedBy(DataConstants.DEFAULT_USER);
			tp.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			i = inputService.insertTestPlanGetKey(tp);
		} catch (Exception e) {
			LOG.warn("addTestPlanControl(HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestPlanControl(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
		return i;
	}

	/**
	 * Edits the test plan control.
	 * 
	 * @param testPlan
	 *            the test plan
	 * @return the test plan
	 */
	@RequestMapping(value = "/editTestPlan", method = RequestMethod.POST)
	public @ResponseBody
	TestPlan editTestPlanControl(@RequestBody Map<String, Integer> testPlan) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("editTestPlanControl(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		TestPlan tPlan = new TestPlan();
		int testPlanID = 0;
		try {
			JSONObject jsonObject = new JSONObject(testPlan);
			testPlanID = (Integer) jsonObject.get("testPlanID");
			tPlan = inputService.getTestPlanById(testPlanID);
		} catch (Exception se) {
			LOG.warn("editTestPlanControl(Map<String,Integer>) - exception ignored", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("editTestPlanControl(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return tPlan;
	}

	/**
	 * Gets the all test plans for application.
	 * 
	 * @param appId
	 *            the app id
	 * @return the all test plans for application
	 */
	@RequestMapping(value = "/getTestPlansForApplication", method = RequestMethod.POST)
	public @ResponseBody
	List<TestPlan> getAllTestPlansForApplication(@RequestBody Map<String, Integer> appId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllTestPlansForApplication(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		List<TestPlan> testPlans = new ArrayList<TestPlan>();
		int applicationId = 0;
		try {
			JSONObject jsonObject = new JSONObject(appId);
			applicationId = (Integer) jsonObject.get("appID");
			testPlans = inputService.getTestPlanObjByAppId(applicationId);
		} catch (Exception e) {
			LOG.warn("getAllTestPlansForApplication(Map<String,Integer>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllTestPlansForApplication(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return testPlans;
	}

	/**
	 * Update test plan.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the long
	 */
	@RequestMapping(value = "/updateTestPlan", method = RequestMethod.POST)
	public @ResponseBody
	long updateTestPlan(HttpServletRequest request, HttpServletResponse response) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestPlan(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		StringBuffer jb = new StringBuffer();
		String line = null;
		long i = 0;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			TestPlan testPlan = getTestPlanFormData(jb);
			testPlan.setAppID(1);
			testPlan.setPreConditionGroupID(1);
			testPlan.setPostConditionGroupID(1);
			testPlan.setCreatedBy(DataConstants.DEFAULT_USER);
			testPlan.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			testPlan.setUpdatedBy(DataConstants.DEFAULT_USER);
			testPlan.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			i = inputService.updateTestPlanData(testPlan);
		} catch (Exception e) {
			LOG.warn("updateTestPlan(HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestPlan(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
		return i;
	}

	/**
	 * Gets the test plan form data.
	 * 
	 * @param jb
	 *            the jb
	 * @return the test plan form data
	 */
	public TestPlan getTestPlanFormData(StringBuffer jb) throws Exception {
		TestPlan tPlan = new TestPlan();
		ObjectMapper mapper = new ObjectMapper();
		try {
			tPlan = mapper.readValue(jb.toString(), TestPlan.class);
		} catch (Exception e) {
			LOG.warn("getTestPlanFormData(StringBuffer) - exception ignored", e); //$NON-NLS-1$
		}
		return tPlan;
	}

}