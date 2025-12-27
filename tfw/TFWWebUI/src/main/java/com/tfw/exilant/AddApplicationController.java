package com.tfw.exilant;

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

import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.Application;
import com.exilant.tfw.service.MainService;
import com.exilant.tfw.utils.DataConstants;

/**
 * The Class AddApplicationController.
 *
 * @author mohammedfirdos
 */

/**
 * This is the Server Controller which handles the Request UI form has
 * dispatched for Add Application Pop up.
 */

@Controller
public class AddApplicationController {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(AddApplicationController.class);

	/** The main service. */
	private MainService mainService;

	/**
	 * Sets the main service.
	 * 
	 * @param mainService
	 *            the mainService to set
	 */
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	/**
	 * Constructor.
	 */
	public AddApplicationController() {
	}

	/**
	 * Gets the all applications.
	 * 
	 * @return the all applications
	 * @RequestBody and @ResponseBody They are annotations of the spring mvc
	 *              framework and can be used in a controller to implement smart
	 *              object serialization and deserialization. They help you
	 *              avoid boilerplate code by extracting the logic of message
	 *              conversion and making it an aspect. Other than that they
	 *              help you support multiple formats for a single REST resource
	 *              without duplication of code. If you annotate a method with
	 * @ResponseBody, spring will try to convert its return value and write it
	 *                to the http response automatically.
	 */
	@RequestMapping(value = "/getAllApplications", method = RequestMethod.GET)
	public @ResponseBody
	List<Application> getAllApplications() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllApplications() - start"); //$NON-NLS-1$
		}

		List<Application> applications = new ArrayList<Application>();
		try {
			applications = mainService.getApplication();
		} catch (ServiceException e) {
			LOG.warn("getAllApplications() - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllApplications() - end"); //$NON-NLS-1$
		}
		return applications;

	}

	/**
	 * Adds the application controller.
	 * 
	 * @param appData
	 *            the app data
	 * @return the long
	 * @RequestBody and @ResponseBody They are annotations of the spring mvc
	 *              framework and can be used in a controller to implement smart
	 *              object serialization and deserialization. They help you
	 *              avoid boilerplate code by extracting the logic of message
	 *              conversion and making it an aspect. Other than that they
	 *              help you support multiple formats for a single REST resource
	 *              without duplication of code. If you annotate a method with
	 * @ResponseBody, spring will try to convert its return value and write it
	 *                to the http response automatically.
	 */
	@RequestMapping(value = "/addApplication", method = RequestMethod.POST)
	public @ResponseBody
	long addApplicationController(@RequestBody Map<String, String> appData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addApplicationController(Map<String,String>) - start"); //$NON-NLS-1$
		}

		long i = 0;
		Application app = new Application();
		try {
			JSONObject jsonObj = new JSONObject(appData);
			try {
				app.setAppName((String) jsonObj.get("appName"));
				app.setDescription((String) jsonObj.get("description"));
			} catch (JSONException e) {
				LOG.warn("addApplicationController(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
			}
			app.setCreatedBy(DataConstants.DEFAULT_USER);
			app.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			app.setUpdatedBy(DataConstants.DEFAULT_USER);
			app.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			i = mainService.insertApplicationGetKey(app);
		} catch (Exception e) {
			LOG.warn("addApplicationController(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("addApplicationController(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return i;
	}

	/**
	 * Edits the application.
	 * 
	 * @param appID
	 *            the app id
	 * @return the application
	 * @RequestBody and @ResponseBody They are annotations of the spring mvc
	 *              framework and can be used in a controller to implement smart
	 *              object serialization and deserialization. They help you
	 *              avoid boilerplate code by extracting the logic of message
	 *              conversion and making it an aspect. Other than that they
	 *              help you support multiple formats for a single REST resource
	 *              without duplication of code. If you annotate a method with
	 * @ResponseBody, spring will try to convert its return value and write it
	 *                to the http response automatically.
	 */
	@RequestMapping(value = "/editApplication", method = RequestMethod.POST)
	public @ResponseBody
	Application editApplication(@RequestBody Map<String, Integer> appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("editApplication(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		Application application = new Application();
		try {
			JSONObject jsonObject = new JSONObject(appID);
			int applicationID = (Integer) jsonObject.get("appID");
			application = mainService.getApplicationById(applicationID);
		} catch (ServiceException se) {
			LOG.warn("editApplication(Map<String,Integer>) - exception ignored", se); //$NON-NLS-1$
		} catch (JSONException e) {
			LOG.warn("editApplication(Map<String,Integer>) - exception ignored", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("editApplication(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return application;
	}

	/**
	 * Update application.
	 * 
	 * @param appData
	 *            the app data
	 * @return the long
	 * @RequestBody and @ResponseBody They are annotations of the spring mvc
	 *              framework and can be used in a controller to implement smart
	 *              object serialization and deserialization. They help you
	 *              avoid boilerplate code by extracting the logic of message
	 *              conversion and making it an aspect. Other than that they
	 *              help you support multiple formats for a single REST resource
	 *              without duplication of code. If you annotate a method with
	 * @ResponseBody, spring will try to convert its return value and write it
	 *                to the http response automatically.
	 */
	@RequestMapping(value = "/updateApplication", method = RequestMethod.POST)
	public @ResponseBody
	long updateApplication(@RequestBody Map<String, String> appData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateApplication(Map<String,String>) - start"); //$NON-NLS-1$
		}
		long i = 0;
		Application app = new Application();
		try {
			JSONObject jsonObj = new JSONObject(appData);
			app.setAppName((String) jsonObj.get("appName"));
			app.setDescription((String) jsonObj.get("description"));
			app.setAppID((Integer) jsonObj.get("appID"));
			app.setUpdatedBy(DataConstants.DEFAULT_USER);
			app.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			i = mainService.updateApplication(app);
		} catch (Exception e) {
			LOG.warn("updateApplication(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateApplication(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return i;
	}

}