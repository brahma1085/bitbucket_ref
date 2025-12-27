package com.sssoft.isatt.ui1.controller;

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

import com.sssoft.isatt.data.pojo.input.ConditionGroup;
import com.sssoft.isatt.data.pojo.input.Conditions;
import com.sssoft.isatt.data.service.InputService;
import com.sssoft.isatt.data.utils.DataConstants;

/**
 * The Class ConditionGroupController.
 */
@Controller
public class ConditionGroupController {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(ConditionGroupController.class);

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
	 * Adds the condition group details.
	 * 
	 * @param conditionGroupData
	 *            the condition group data
	 * @return the long
	 */
	@RequestMapping(value = "/addConditionGroupDetails", method = RequestMethod.POST)
	public @ResponseBody
	long addConditionGroupDetails(@RequestBody Map<String, String> conditionGroupData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addConditionGroupDetails(Map<String,String>) - start"); //$NON-NLS-1$
		}
		long i = 0;
		ConditionGroup conditionGrp = new ConditionGroup();
		try {
			JSONObject jsonObj = new JSONObject(conditionGroupData);
			String name = (String) jsonObj.get("conditionGroupName");
			String desc = (String) jsonObj.get("description");
			int appId = (Integer) jsonObj.get("appID");
			conditionGrp.setConditionGroupName(name);
			conditionGrp.setDescription(desc);
			conditionGrp.setAppID(appId);
			conditionGrp.setCreatedBy(DataConstants.DEFAULT_USER);
			conditionGrp.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			conditionGrp.setUpdatedBy(DataConstants.DEFAULT_USER);
			conditionGrp.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			i = inputService.insertConditionGroupGetKey(conditionGrp);
		} catch (Exception e) {
			LOG.warn("addConditionGroupDetails(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addConditionGroupDetails(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return i;
	}

	/**
	 * Gets the condition grp.
	 * 
	 * @param appId
	 *            the app id
	 * @return the condition grp
	 */
	@RequestMapping(value = "/getConditionGrp", method = RequestMethod.POST)
	public @ResponseBody
	List<ConditionGroup> getConditionGrp(@RequestBody Map<String, Integer> appId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionGrp(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		List<ConditionGroup> conditionGrp = new ArrayList<ConditionGroup>();
		int appID = 0;
		try {
			JSONObject jsonObj = new JSONObject(appId);
			appID = (Integer) jsonObj.get("appID");
			conditionGrp = inputService.getConditionGroupNamesByAppId(appID);
		} catch (Exception e) {
			LOG.warn("getConditionGrp(Map<String,Integer>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionGrp(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return conditionGrp;
	}

	/**
	 * Gets the condition grp by id.
	 * 
	 * @param conditionGrpId
	 *            the condition grp id
	 * @return the condition grp by id
	 */
	@RequestMapping(value = "/getConditionGroupById", method = RequestMethod.POST)
	public @ResponseBody
	ConditionGroup getConditionGrpById(@RequestBody Map<String, Integer> conditionGrpId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionGrpById(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		ConditionGroup conditionGrp = new ConditionGroup();
		int id = 0;
		try {
			JSONObject jsonObj = new JSONObject(conditionGrpId);
			id = (Integer) jsonObj.get("conditionGroupID");
			conditionGrp = inputService.getConditionGroupById(id);
		} catch (Exception e) {
			LOG.warn("getConditionGrpById(Map<String,Integer>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionGrpById(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return conditionGrp;
	}

	/**
	 * Update condition group details.
	 * 
	 * @param conGrpData
	 *            the con grp data
	 * @return the long
	 */
	@RequestMapping(value = "/updateConditionGroupDetails", method = RequestMethod.POST)
	public @ResponseBody
	long updateConditionGroupDetails(@RequestBody Map<String, String> conGrpData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateConditionGroupDetails(Map<String,String>) - start"); //$NON-NLS-1$
		}
		long i = 0;
		ConditionGroup conditionGrp = new ConditionGroup();
		try {
			JSONObject jsonObj = new JSONObject(conGrpData);
			String name = (String) jsonObj.get("conditionGroupName");
			String desc = (String) jsonObj.get("description");
			int conditionGrpId = (Integer) jsonObj.get("conditionGroupID");
			conditionGrp.setConditionGroupName(name);
			conditionGrp.setDescription(desc);
			conditionGrp.setConditionGroupID(conditionGrpId);
			conditionGrp.setUpdatedBy(DataConstants.DEFAULT_USER);
			conditionGrp.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			i = inputService.updateConditionGroupData(conditionGrp);
		} catch (Exception e) {
			LOG.warn("updateConditionGroupDetails(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateConditionGroupDetails(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return i;
	}

	/**
	 * Gets the conditions for condition grp.
	 * 
	 * @param conditionGrpId
	 *            the condition grp id
	 * @return the conditions for condition grp
	 */
	@RequestMapping(value = "/getConditionsForConditionGrp", method = RequestMethod.POST)
	public @ResponseBody
	List<Conditions> getConditionsForConditionGrp(@RequestBody Map<String, Integer> conditionGrpId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionsForConditionGrp(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		List<Conditions> conditions = new ArrayList<Conditions>();
		int id = 0;
		try {
			JSONObject jsonObj = new JSONObject(conditionGrpId);
			id = (Integer) jsonObj.get("conditionGroupID");
			conditions = inputService.getConditionByConditionGroupId(id);
		} catch (Exception e) {
			LOG.warn("getConditionsForConditionGrp(Map<String,Integer>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionsForConditionGrp(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return conditions;
	}

	/**
	 * Adds the condition data.
	 * 
	 * @param conData
	 *            the con data
	 * @return the int
	 */
	@RequestMapping(value = "/addConditionData", method = RequestMethod.POST)
	public @ResponseBody
	int addConditionData(@RequestBody Map<String, String> conData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addConditionData(Map<String,String>) - start"); //$NON-NLS-1$
		}
		int i = 0;
		Conditions conditions = new Conditions();
		try {
			JSONObject jsonObj = new JSONObject(conData);
			String name = (String) jsonObj.get("conditionName");
			String desc = (String) jsonObj.get("description");
			String expression = (String) jsonObj.get("expression");
			String conditionGrpId = (String) jsonObj.get("conditionGroupID");
			int id = Integer.parseInt(conditionGrpId);
			conditions.setConditionName(name);
			conditions.setDescription(desc);
			conditions.setConditionGroupID(id);
			conditions.setExpression(expression);
			conditions.setCreatedBy(DataConstants.DEFAULT_USER);
			conditions.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			conditions.setUpdatedBy(DataConstants.DEFAULT_USER);
			conditions.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			i = inputService.insertConditionsGetKey(conditions);
		} catch (Exception e) {
			LOG.warn("addConditionData(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addConditionData(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return i;
	}

	/**
	 * Gets the condition details by id.
	 * 
	 * @param conditionId
	 *            the condition id
	 * @return the condition details by id
	 */
	@RequestMapping(value = "/getConditionDetailsById", method = RequestMethod.POST)
	public @ResponseBody
	Conditions getConditionDetailsById(@RequestBody Map<String, Integer> conditionId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionDetailsById(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		Conditions conditions = new Conditions();
		int id = 0;
		try {
			JSONObject jsonObj = new JSONObject(conditionId);
			id = (Integer) jsonObj.get("conditionID");
			conditions = inputService.getConditionsById(id);
		} catch (Exception e) {
			LOG.warn("getConditionDetailsById(Map<String,Integer>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionDetailsById(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return conditions;
	}

	/**
	 * Update condition data details.
	 * 
	 * @param conditionData
	 *            the condition data
	 * @return the long
	 */
	@RequestMapping(value = "/updateConditionDataDetails", method = RequestMethod.POST)
	public @ResponseBody
	long updateConditionDataDetails(@RequestBody Map<String, String> conditionData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateConditionDataDetails(Map<String,String>) - start"); //$NON-NLS-1$
		}
		long i = 0;
		Conditions conditions = new Conditions();
		try {
			JSONObject jsonObj = new JSONObject(conditionData);
			String name = (String) jsonObj.get("conditionName");
			String desc = (String) jsonObj.get("description");
			String expression = (String) jsonObj.get("expression");
			int conditionId = (Integer) jsonObj.get("conditionID");
			String conditionGrpId = (String) jsonObj.get("conditionGrpID");
			int id = Integer.parseInt(conditionGrpId);
			conditions.setConditionID(conditionId);
			conditions.setConditionName(name);
			conditions.setDescription(desc);
			conditions.setConditionGroupID(id);
			conditions.setExpression(expression);
			conditions.setUpdatedBy(DataConstants.DEFAULT_USER);
			conditions.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			i = inputService.updateConditionsData(conditions);
		} catch (Exception e) {
			LOG.warn("updateConditionDataDetails(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateConditionDataDetails(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return i;
	}

}