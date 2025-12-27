package com.tfw.exilant.ITAP;

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
import com.exilant.tfw.pojo.Application;
import com.exilant.tfw.pojo.UserRoles;
import com.exilant.tfw.pojo.Users;
import com.exilant.tfw.pojo.UsersApplicationMapping;
import com.exilant.tfw.pojo.def.UserUI;
import com.exilant.tfw.service.MainService;
import com.exilant.tfw.util.CipherUtil;

@Controller
public class UsersControllerITAP {

	private static final Logger LOG = Logger
			.getLogger(UsersControllerITAP.class);

	/** The main service. */
	private MainService mainService;

	public CipherUtil mainCrpt;

	/**
	 * Gets the main service.
	 * 
	 * @return the main service
	 */
	public MainService getMainService() {
		return mainService;
	}

	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public @ResponseBody
	List<Users> getAllUsers() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllUsers() - start"); //$NON-NLS-1$
		}

		List<Users> users = new ArrayList<Users>();
		try {
			users = mainService.getAllUsers();
		} catch (ServiceException e) {
			LOG.warn("getAllRoles() - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllRoles() - end"); //$NON-NLS-1$
		}
		return users;
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public @ResponseBody
	long addUsersController(@RequestBody Map<String, String> usersData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addUsersController(Map<String,String>) - start"); //$NON-NLS-1$
		}
		int userID = 0;
		Users users = new Users();
		try {
			JSONObject jsonObj = new JSONObject(usersData);
			String username = (String) jsonObj.get("username");
			String password = (String) jsonObj.get("password");
			String emailID = (String) jsonObj.get("emailID");
			@SuppressWarnings("unchecked")
			List<Map<String, String>> roleApps = (List<Map<String, String>>) jsonObj
					.get("roleApps");
			users.setUsername(username);
			mainCrpt = CipherUtil.getInstance();
			String encryptedFormPassword = this.enc(password);
			users.setPassword(encryptedFormPassword);
			users.setEnabled(true);
			users.setEmailID(emailID);
			users.setPasswordCount(0);
			userID = mainService.insertUsersGetKey(users);
			UserRoles userRoles = new UserRoles();
			String role = "";
			for (Map<String, String> roleAppsMap : roleApps) {
				if (!role.equals(roleAppsMap.get("group"))) {
					role = roleAppsMap.get("group");
					userRoles.setAuthority(role);
					userRoles.setUserID(userID);
					mainService.insertUserRolesGetKey(userRoles);
				}
				String appId = roleAppsMap.get("apps");
				int appID = Integer.parseInt(appId);
				UsersApplicationMapping usersApplicationMapping = new UsersApplicationMapping();
				usersApplicationMapping.setAuthority(role);
				usersApplicationMapping.setAppID(appID);
				usersApplicationMapping.setUserID(userID);
				mainService
						.insertUsersApplicationMappingGetKey(usersApplicationMapping);
			}
		} catch (ServiceException e) {
			LOG.warn("addUser() - exception ignored", e); //$NON-NLS-1$
		} catch (Exception e) {
			LOG.warn("addUser() - exception ignored", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addUsersController(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return userID;
	}

	public String enc(String s) throws Exception {
		return mainCrpt.encrypt(s);

	}

	public String dec(String s) throws Exception {
		return mainCrpt.decrypt(s);

	}

	@RequestMapping(value = "/getUserByUserID", method = RequestMethod.POST)
	public @ResponseBody
	UserUI getUserByUserID(@RequestBody String userID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUserByUserID(String) - start"); //$NON-NLS-1$
		}
		UserUI userUI = new UserUI();
		try {
			int userIDs = Integer.parseInt(userID);
			Users users = mainService.getUserByUserId(userIDs);
			List<String> userRoles = mainService
					.getUserRoleFilterByUserId(userIDs);
			String password = users.getPassword();
			mainCrpt = CipherUtil.getInstance();
			String encryptedFormPassword = this.dec(password);
			users.setPassword(encryptedFormPassword);
			userUI.setUserID(users.getUserID());
			userUI.setUsername(users.getUsername());
			userUI.setPassword(users.getPassword());
			userUI.setEmailID(users.getEmailID());
			userUI.setUserRoles(userRoles);
		} catch (ServiceException e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUserByUserID(String) - end"); //$NON-NLS-1$
		}
		return userUI;
	}

	@RequestMapping(value = "/getApplicationsByRoleAndUserID", method = RequestMethod.POST)
	public @ResponseBody
	List<String> getApplicationsByRoleAndUserID(
			@RequestBody Map<String, String> usersData){
		if (LOG.isDebugEnabled()) {
			LOG.debug("getApplicationsByRoleAndUserID(Map) - start"); //$NON-NLS-1$
		}
		List<String> applications = new ArrayList<String>();
		try {
			JSONObject jsonObj = new JSONObject(usersData);
			String role = (String) jsonObj.get("role");
			// int userID = Integer.parseInt((String) jsonObj.get("userID"));
			int userID = (Integer) jsonObj.get("userID");
			List<Integer> appIds = mainService.getApplicationsByRoleAndUserID(
					userID, role);

			for (Integer appId : appIds) {
				Application application = mainService.getApplicationById(appId);
				applications.add(application.getAppName());
			}
		} catch (ServiceException e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getApplicationsByRoleAndUserID(Map) - end"); //$NON-NLS-1$
		}
		return applications;
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public @ResponseBody
	long updateUserController(@RequestBody Map<String, String> usersData){
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateUser(usersData) - start"); //$NON-NLS-1$
		}
		Users users = new Users();
		long userID = 0;
		try {
			JSONObject jsonObj = new JSONObject(usersData);
			int userIDs = (Integer) jsonObj.get("userID");
			String username = (String) jsonObj.get("username");
			String password = (String) jsonObj.get("password");
			String emailID = (String) jsonObj.get("emailID");
			List<Map<String, String>> roleApps = (List<Map<String, String>>) jsonObj
					.get("roleApps");
			users.setUsername(username);
			mainCrpt = CipherUtil.getInstance();
			String encryptedFormPassword = this.enc(password);
			users.setPassword(encryptedFormPassword);
			users.setEnabled(true);
			users.setEmailID(emailID);
			users.setPasswordCount(0);
			userID = mainService.updateUser(users);
			UserRoles userRoles = new UserRoles();
			String role = "";
			if (roleApps.size() > 0) {
				for (Map<String, String> roleAppsMap : roleApps) {
					if (!role.equals(roleAppsMap.get("group"))) {
						role = roleAppsMap.get("group");
						userRoles.setAuthority(role);
						userRoles.setUserID(userIDs);
						mainService.insertUserRolesGetKey(userRoles);
					}
					String appName = roleAppsMap.get("apps");
					if (appName != null) {
						int appID = mainService.getApplicationIDByName(appName);
						UsersApplicationMapping usersApplicationMapping = new UsersApplicationMapping();
						usersApplicationMapping.setAuthority(role);
						usersApplicationMapping.setAppID(appID);
						usersApplicationMapping.setUserID(userIDs);
						mainService
								.insertUsersApplicationMappingGetKey(usersApplicationMapping);
					}
				}
			}
		} catch (ServiceException e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateUser(usersData) - end"); //$NON-NLS-1$
		}
		return userID;
	}

}
