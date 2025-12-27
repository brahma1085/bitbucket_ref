package com.tfw.exilant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.Application;
import com.exilant.tfw.pojo.Roles;
import com.exilant.tfw.pojo.UserRoles;
import com.exilant.tfw.pojo.Users;
import com.exilant.tfw.pojo.UsersApplicationMapping;
import com.exilant.tfw.service.MainService;
import com.exilant.tfw.util.CipherUtil;

/**
 * The Class RolesController.
 * 
 * @author mohammedfirdos
 */

@Controller
public class RolesController {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(RolesController.class);

	/** The main service. */
	private MainService mainService;

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
	 * Constructor.
	 */
	public RolesController() {
	}

	/**
	 * Gets the all roles.
	 * 
	 * @return the all roles
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
	@RequestMapping(value = "/getAllRoles", method = RequestMethod.GET)
	public @ResponseBody
	List<Roles> getAllRoles() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllRoles() - start"); //$NON-NLS-1$
		}

		List<Roles> roles = new ArrayList<Roles>();
		try {
			roles = mainService.getRoles();
		} catch (ServiceException e) {
			LOG.warn("getAllRoles() - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllRoles() - end"); //$NON-NLS-1$
		}
		return roles;
	}

	/**
	 * Gets the all apps.
	 * 
	 * @return the all apps
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
	@RequestMapping(value = "/getAllApps", method = RequestMethod.GET)
	public @ResponseBody
	List<Application> getAllApps() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllApps() - start"); //$NON-NLS-1$
		}

		List<Application> apps = new ArrayList<Application>();
		try {
			apps = mainService.getAllApps();
		} catch (com.exilant.tfw.exception.ServiceException e) {
			LOG.warn("getAllApps() - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllApps() - end"); //$NON-NLS-1$
		}
		return apps;
	}

	/**
	 * Adds the users controller.
	 * 
	 * @param usersData
	 *            the users data
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
	@RequestMapping(value = "/addUsers", method = RequestMethod.POST)
	public @ResponseBody
	void addUsersController(@RequestBody Map<String, String> usersData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addUsersController(Map<String,String>) - start"); //$NON-NLS-1$
		}
		int i = 0;
		Users users = new Users();
		try {
			JSONObject jsonObj = new JSONObject(usersData);
			users.setUsername((String) jsonObj.get("username"));
			CipherUtil appCiper = CipherUtil.getInstance();
			String encryptedFormPassword = appCiper.encrypt((String) jsonObj.get("password"));
			users.setPassword(encryptedFormPassword);
			users.setEnabled(true);
			Boolean checkUser = mainService.checkAvailability(users.getUsername());
			if (checkUser) {
				i = mainService.insertUsersGetKey(users);
				String authoritys = (String) jsonObj.get("authority");
				UserRoles userRoles = new UserRoles();
				userRoles.setUserID(i);
				userRoles.setAuthority(authoritys);
				mainService.insertUserRolesGetKey(userRoles);
				String applicationID = (String) jsonObj.get("appName");
				int appIDs = Integer.parseInt(applicationID);
				UsersApplicationMapping usersApplicationMapping = new UsersApplicationMapping();
				usersApplicationMapping.setUserID(i);
				usersApplicationMapping.setAppID(appIDs);
				usersApplicationMapping.setAuthority(authoritys);
				mainService.insertUsersApplicationMappingGetKey(usersApplicationMapping);
			}
		} catch (Exception e) {
			LOG.warn("addUsersController(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addUsersController(Map<String,String>) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Adds the apps controller.
	 * 
	 * @param usersData
	 *            the users data
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
	@RequestMapping(value = "/addApps", method = RequestMethod.POST)
	public @ResponseBody
	void addAppsController(@RequestBody Map<String, String> usersData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addAppsController(Map<String,String>) - start"); //$NON-NLS-1$
		}
		try {
			JSONObject jsonObj = new JSONObject(usersData);
			String authoritys = (String) jsonObj.get("authority");
			String applicationID = (String) jsonObj.get("appName");
			int appIDs = Integer.parseInt(applicationID);
			UsersApplicationMapping usersApplicationMapping = new UsersApplicationMapping();
			int userId = mainService.getUserIdByUserName((String) jsonObj.get("username"));
			usersApplicationMapping.setUserID(userId);
			usersApplicationMapping.setAppID(appIDs);
			usersApplicationMapping.setAuthority(authoritys);
			mainService.insertUsersApplicationMappingGetKey(usersApplicationMapping);
		} catch (Exception e) {
			LOG.warn("addAppsController(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addAppsController(Map<String,String>) - end"); //$NON-NLS-1$
		}
	}

}