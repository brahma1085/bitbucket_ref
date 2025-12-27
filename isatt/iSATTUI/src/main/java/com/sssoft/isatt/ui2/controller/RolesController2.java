package com.sssoft.isatt.ui2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;

import net.minidev.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.Roles;
import com.sssoft.isatt.data.pojo.Screen;
import com.sssoft.isatt.data.service.MainService;

@Controller
public class RolesController2 {

	private static final Logger LOG = Logger.getLogger(RolesController2.class);

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
	
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}
	
	@RequestMapping(value = "/getAllRole", method = RequestMethod.GET)
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

	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	public @ResponseBody long addRolesController(@RequestBody Map<String, String> roleData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addRolesController(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		Roles roles = new Roles();
		long roleID = 0;
		try {
		JSONObject jsonObject = new JSONObject(roleData);
		String roleName= (String)jsonObject.get("roleName");
		String roleDescription= (String)jsonObject.get("roleDescription");
		roles.setAuthority(roleName);
		roles.setRolesDescription(roleDescription);
		roleID = mainService.insertRolesGetKey(roles);
		}  catch (ServiceException e) {
			e.printStackTrace();
		}if (LOG.isDebugEnabled()) {
			LOG.debug("addRolesController(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
		return roleID;
	}
	
	@RequestMapping(value = "/getRoleByRoleID", method = RequestMethod.POST)
	public @ResponseBody Roles getRoleByRoleID(@RequestBody String roleID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addRolesController(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		Roles roles = null;
		try {
		int roleIDs = Integer.parseInt(roleID);
		roles = mainService.getRoleByID(roleIDs);
		}  catch (ServiceException e) {
			e.printStackTrace();
		}if (LOG.isDebugEnabled()) {
			LOG.debug("addRolesController(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
		return roles;
	}
	
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	public @ResponseBody long updateRolesController(@RequestBody Map<String, String> roleData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addRolesController(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		Roles roles = new Roles();
		long roleID = 0;
		try {
		JSONObject jsonObject = new JSONObject(roleData);
		String roleName= (String)jsonObject.get("roleName");
		String roleDescription= (String)jsonObject.get("roleDescription");
		int roleIDs = (Integer) jsonObject.get("roleID");
//		int roleIDs = Integer.parseInt(jRoleID);
		roles.setRoleID(roleIDs);
		roles.setAuthority(roleName);
		roles.setRolesDescription(roleDescription);
		roleID = mainService.updateRoles(roles);
		}  catch (ServiceException e) {
			e.printStackTrace();
		}if (LOG.isDebugEnabled()) {
			LOG.debug("addRolesController(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
		return roleID;
	}
}
