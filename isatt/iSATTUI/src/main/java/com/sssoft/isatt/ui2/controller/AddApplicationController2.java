package com.sssoft.isatt.ui2.controller;

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

import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.AppFeature;
import com.sssoft.isatt.data.pojo.AppFunctionality;
import com.sssoft.isatt.data.pojo.Application;
import com.sssoft.isatt.data.pojo.UsersApplicationMapping;
import com.sssoft.isatt.data.pojo.def.TestPlanUI;
import com.sssoft.isatt.data.pojo.input.PlanSuiteMapping;
import com.sssoft.isatt.data.pojo.input.TestPlan;
import com.sssoft.isatt.data.pojo.input.TestSuite;
import com.sssoft.isatt.data.service.MainService;
import com.sssoft.isatt.data.utils.DataConstants;

/**
 * The Class AddApplicationController2.
 */
@Controller
public class AddApplicationController2 {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AddApplicationController2.class);

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
	@RequestMapping(value = "/getAllApplicationsITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<Application> getAllApplications(@RequestBody String guestName, HttpServletRequest request) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllApplications(String) - start"); //$NON-NLS-1$
		}
		List<UsersApplicationMapping> usersApplicationMapping = new ArrayList<UsersApplicationMapping>();
		List<Application> applications = new ArrayList<Application>();
		try {
			int userId = mainService.getUserIdByUserName((String)request.getSession().getAttribute("username"));
			usersApplicationMapping = mainService.getApplicationByUserId(userId); 

			Iterator it = usersApplicationMapping.iterator();
			while (it.hasNext()) {
				UsersApplicationMapping uamap = (UsersApplicationMapping) it.next();
				Application application = mainService.getApplicationById(uamap.getAppID());
				applications.add(application);
			}

		} catch (com.sssoft.isatt.data.exception.ServiceException e) {
			LOG.error("getAllApplications(String)", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllApplications(String) - end"); //$NON-NLS-1$
		}
		return applications;

	}

	@RequestMapping(value = "/getApplications", method = RequestMethod.POST)
	public @ResponseBody
	List<Application> getApplications(@RequestBody String guestName, HttpServletRequest request) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllApplications(String) - start"); //$NON-NLS-1$
		}
		List<Application> applications = new ArrayList<Application>();
		try {
			applications = mainService.getAllApps();
		} catch (com.sssoft.isatt.data.exception.ServiceException e) {
			LOG.error("getAllApplications(String)", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllApplications(String) - end"); //$NON-NLS-1$
		}
		return applications;

	}
	
	
	@RequestMapping(value = "/addApplicationITAP", method = RequestMethod.POST)
	public @ResponseBody
	int addAapplicationITAP(@RequestBody Map<String, String> applicationData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addAapplicationITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}

		JSONObject jsonObject = new JSONObject(applicationData);
		int applicationId = 0;
		try {
			String appName = (String) jsonObject.get("appName");
			String description = (String) jsonObject.get("description");
			Application application = new Application();
		
			application.setAppName(appName);
			application.setDescription(description);
			application.setCreatedBy(DataConstants.DEFAULT_USER);
			application.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			application.setUpdatedBy(DataConstants.DEFAULT_USER);
			application.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			LOG.info("Application Pojo String ::" + application.toString());

			applicationId = mainService.insertApplicationGetKey(application);
			int userId =  mainService.getUserIdByRole("ROLE_ADMIN");
			
			UsersApplicationMapping uam = new UsersApplicationMapping();
			uam.setUserID(userId);
			uam.setAppID(applicationId);
			uam.setAuthority("ADMIN");
			mainService.insertUsersApplicationMappingGetKey(uam);
			LOG.info("applicationId ::" + applicationId);

		} catch (JSONException e) {
			LOG.error("addAapplicationITAP(Map<String,String>)", e); //$NON-NLS-1$

		} catch (ServiceException e) {
			LOG.error("addAapplicationITAP(Map<String,String>)", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("addAapplicationITAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return applicationId;
	}
	
	
	@RequestMapping(value = "/updateApplicationITAP", method = RequestMethod.POST)
	public @ResponseBody
	long updateAapplicationITAP(@RequestBody Map<String, String> applicationData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateAapplicationITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}

		JSONObject jsonObject = new JSONObject(applicationData);
		long applicationId = 0;
		try {
			String appName = (String) jsonObject.get("appName");
			String description = (String) jsonObject.get("description");
			Application application = new Application();
			if(jsonObject.get("appID") instanceof String){
			String appID = (String) jsonObject.get("appID");
			int appIDS = Integer.parseInt(appID);
			application.setAppID(appIDS);
			} else {
				application.setAppID((Integer)jsonObject.get("appID"));				
			}
			
			application.setAppName(appName);
			application.setDescription(description);
			application.setCreatedBy(DataConstants.DEFAULT_USER);
			application.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			application.setUpdatedBy(DataConstants.DEFAULT_USER);
			application.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			LOG.info("Function Pojo String ::" + application.toString());

			applicationId = mainService.updateApplication(application);
			LOG.info("applicationId ::" + applicationId);

		} catch (JSONException e) {
			LOG.error("updateAapplicationITAP(Map<String,String>)", e); //$NON-NLS-1$

		} catch (ServiceException e) {
			LOG.error("updateAapplicationITAP(Map<String,String>)", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("updateAapplicationITAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return applicationId;
	}
	
	@RequestMapping(value = "/editApplicationiTAP", method = RequestMethod.POST)
	public @ResponseBody
	Application getAppByAppId(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppByAppId(String) - start"); //$NON-NLS-1$
		}
		 int appIDs = Integer.parseInt(appID);
		Application application = new Application();
		try {
			application = mainService.getApplicationById(appIDs);
		} catch (ServiceException se) {
			LOG.error("getAppByAppId(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppByAppId(String) - end"); //$NON-NLS-1$
		}
		return application;
	}

}
