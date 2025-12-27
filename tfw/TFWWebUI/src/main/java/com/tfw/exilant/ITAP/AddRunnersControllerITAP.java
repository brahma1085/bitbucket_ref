package com.tfw.exilant.ITAP;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.AppFeature;
import com.exilant.tfw.pojo.AppFunctionality;
import com.exilant.tfw.pojo.Application;
import com.exilant.tfw.pojo.Runner;
import com.exilant.tfw.pojo.UsersApplicationMapping;
import com.exilant.tfw.pojo.def.TestPlanUI;
import com.exilant.tfw.pojo.input.PlanSuiteMapping;
import com.exilant.tfw.pojo.input.TestPlan;
import com.exilant.tfw.pojo.input.TestSuite;
import com.exilant.tfw.service.MainService;
import com.exilant.tfw.utils.DataConstants;

/**
 * The Class AddApplicationControllerITAP.
 */
@Controller
public class AddRunnersControllerITAP {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AddApplicationControllerITAP.class);

	/** The main service. */
	private MainService mainService;
	
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
	 * @param mainService the new main service
	 */
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	/**
	 * Gets the all applications.
	 *
	 * @param guestName the guest name
	 * @param request the request
	 * @return the all applications
	 */
	@RequestMapping(value = "/getAllRunnerITAP", method = RequestMethod.GET)
	public @ResponseBody
	List<Runner> getAllRunnerITAP() {
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllRunnerITAP(String) - start"); //$NON-NLS-1$
		}

		List<Runner> runner = new ArrayList<Runner>();
		try {
				 runner = mainService.getRunner();
				
		} catch (com.exilant.tfw.exception.ServiceException e) {
			LOG.error("getAllRunnerITAP(String)", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllRunnerITAP(String) - end"); //$NON-NLS-1$
		}
		return runner;

	}
	
	
	@RequestMapping(value = "/getRunnerById", method = RequestMethod.POST)
	public @ResponseBody
	Runner getRunnerById(@RequestBody String runnerID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getRunnerById(String) - start"); //$NON-NLS-1$
		}
		 int runnerIDs = Integer.parseInt(runnerID);
		Runner runner = new Runner();
		try {
			runner = mainService.getRunnerById(runnerIDs);
		} catch (ServiceException se) {
			LOG.error("getRunnerById(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getRunnerById(String) - end"); //$NON-NLS-1$
		}
		return runner;
	}

	
	@RequestMapping(value = "/addRunnerITAP", method = RequestMethod.POST)
	public @ResponseBody
	long addRunnerITAP(@RequestBody Map<String, String> runnerData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addRunnerITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}

		JSONObject jsonObject = new JSONObject(runnerData);
		long runnerId = 0;
		try {
			String runnerName = (String) jsonObject.get("runnerName");
			String description = (String) jsonObject.get("runnerDescription");
			Runner runner = new Runner();
		
			runner.setRunnerName(runnerName);
			runner.setDescription(description);
			runner.setCreatedBy(DataConstants.DEFAULT_USER);
			runner.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			runner.setUpdatedBy(DataConstants.DEFAULT_USER);
			runner.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			LOG.info("Application Pojo String ::" + runner.toString());

			runnerId = mainService.insertRunner(runner);

		} catch (JSONException e) {
			LOG.error("addRunnerITAP(Map<String,String>)", e); //$NON-NLS-1$

		} catch (ServiceException e) {
			LOG.error("addRunnerITAP(Map<String,String>)", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("addRunnerITAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return runnerId;
	}
	
	
	@RequestMapping(value = "/updateRunnerTAP", method = RequestMethod.POST)
	public @ResponseBody
	long updateRunnerTAP(@RequestBody Map<String, String> applicationData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateRunnerTAP(Map<String,String>) - start"); //$NON-NLS-1$
		}

		JSONObject jsonObject = new JSONObject(applicationData);
		long runnerID = 0;
		try {
			String runnerName = (String) jsonObject.get("runnerName");
			String description = (String) jsonObject.get("runnerDescription");
			Runner runner = new Runner();
			if(jsonObject.get("runnerID") instanceof String){
			String runnerID1 = (String) jsonObject.get("runnerID");
			int runnerIDS = Integer.parseInt(runnerID1);
			runner.setRunnerID(runnerIDS);
			} else {
				runner.setRunnerID((Integer)jsonObject.get("runnerID"));				
			}
			
			runner.setRunnerName(runnerName);
			runner.setDescription(description);
			runner.setCreatedBy(DataConstants.DEFAULT_USER);
			runner.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			runner.setUpdatedBy(DataConstants.DEFAULT_USER);
			runner.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			LOG.info("Runer Pojo String ::" + runner.toString());

			runnerID = mainService.updateRunner(runner);
			LOG.info("runnerID ::" + runnerID);

		} catch (JSONException e) {
			LOG.error("updateRunnerTAP(Map<String,String>)", e); //$NON-NLS-1$

		} catch (ServiceException e) {
			LOG.error("updateRunnerTAP(Map<String,String>)", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("updateRunnerTAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return runnerID;
	}
}
