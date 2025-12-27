package com.sssoft.isatt.ui2.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.input.ConditionGroup;
import com.sssoft.isatt.data.pojo.input.Conditions;
import com.sssoft.isatt.data.service.InputService;
import com.sssoft.isatt.data.utils.DataConstants;

/**
 * The Class AddConditionGroupController2.
 */
@Controller
public class AddConditionGroupController2 {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AddConditionGroupController2.class);

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
	 * Gets the condition grp itap.
	 * 
	 * @param appID
	 *            the app id
	 * @return the condition grp itap
	 */
	@RequestMapping(value = "/getConditionGrpITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<ConditionGroup> getConditionGrpITAP(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionGrpITAP(String) - start"); //$NON-NLS-1$
		}

		List<ConditionGroup> conditionGrp = new ArrayList<ConditionGroup>();
		int appId = Integer.parseInt(appID);
		try {
			conditionGrp = inputService.getConditionGroupNamesByAppId(appId);
		} catch (Exception e) {
			LOG.error("getConditionGrpITAP(String)", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionGrpITAP(String) - end"); //$NON-NLS-1$
		}
		return conditionGrp;
	}

	/**
	 * Adds the condition group itap.
	 * 
	 * @param condGrpData
	 *            the cond grp data
	 * @return the int
	 */
	@RequestMapping(value = "/addConditionGroupITAP", method = RequestMethod.POST)
	public @ResponseBody
	int addConditionGroupITAP(@RequestBody Map<String, String> condGrpData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addConditionGroupITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}

		long conditionGroupID = 0;
		int conditionId = 0;
		try {
			JSONObject jsonObject = new JSONObject(condGrpData);

			String conditionGroupName = (String) jsonObject.get("conditionGroupName");
			String description = (String) jsonObject.get("description");
			int appIDs = (Integer) jsonObject.get("appID");
			ConditionGroup conditionGroup = new ConditionGroup();
			conditionGroup.setConditionGroupName(conditionGroupName);
			conditionGroup.setDescription(description);
			conditionGroup.setAppID(appIDs);
			conditionGroup.setCreatedBy(DataConstants.DEFAULT_USER);
			conditionGroup.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			conditionGroup.setUpdatedBy(DataConstants.DEFAULT_USER);
			conditionGroup.setUpdatedDateTime(DataConstants.DEFAULT_DATE);

			conditionGroupID = inputService.insertConditionGroupGetKey(conditionGroup);

			ArrayList<ArrayList<String>> conditions = (ArrayList<ArrayList<String>>) jsonObject.get("conditions");
			for (int i = 1; i < conditions.size(); i++) {
				ArrayList<String> conditionData = (ArrayList<String>) conditions.get(i);
				String parts = conditionData.toString();
				String[] conditionDetails = parts.split(",");

				LOG.info("Conditions:: " + conditionData);
				Conditions condition = new Conditions();
				condition.setConditionGroupID((int) conditionGroupID);
				condition.setConditionName(conditionDetails[0].replace("[", "").trim());
				condition.setDescription(conditionDetails[1].trim());
				condition.setExpression(conditionDetails[2].replace("]", "").trim());
				condition.setCreatedBy(DataConstants.DEFAULT_USER);
				condition.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				condition.setUpdatedBy(DataConstants.DEFAULT_USER);
				condition.setUpdatedDateTime(DataConstants.DEFAULT_DATE);

				conditionId = inputService.insertConditionsGetKey(condition);

			}

		} catch (ServiceException se) {
			LOG.error("addConditionGroupITAP(Map<String,String>)", se); //$NON-NLS-1$
			LOG.info(se.getMessage());
		} catch (JSONException e) {
			LOG.error("addConditionGroupITAP(Map<String,String>)", e); //$NON-NLS-1$
			LOG.info(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("addConditionGroupITAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return conditionId;

	}

	/**
	 * Gets the conditions by id itap.
	 * 
	 * @param conID
	 *            the con id
	 * @return the conditions by id itap
	 */
	@RequestMapping(value = "/getConditionsByIdITAP", method = RequestMethod.POST)
	public @ResponseBody
	ConditionGroup getConditionsByIdITAP(@RequestBody String conID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionsByIdITAP(String) - start"); //$NON-NLS-1$
		}

		ConditionGroup conditions = new ConditionGroup();

		int condGrpId = Integer.parseInt(conID);
		LOG.info("conID to retrive :: " + conID);
		try {

			conditions = inputService.getConditionGroupById(condGrpId);
			LOG.info("conditions Group :: " + conditions);

		} catch (Exception e) {
			LOG.error("getConditionsByIdITAP(String)", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionsByIdITAP(String) - end"); //$NON-NLS-1$
		}
		return conditions;
	}

	/**
	 * Update condition grp.
	 * 
	 * @param conditionGrpData
	 *            the condition grp data
	 * @return the int
	 */
	@RequestMapping(value = "/updateconditionGrpITAP", method = RequestMethod.POST)
	public @ResponseBody
	int updateConditionGrp(@RequestBody Map<String, String> conditionGrpData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateConditionGrp(Map<String,String>) - start"); //$NON-NLS-1$
		}

		long update = 0;
		int contGrpID = 0;

		try {
			JSONObject jsonObj = new JSONObject(conditionGrpData);

			String name = (String) jsonObj.get("conditionGroupName");
			String desc = (String) jsonObj.get("conditionGroupdescription");
			int conGrpId = (Integer) jsonObj.get("conditionGroupID");
			int appId = (Integer) jsonObj.get("appID");

			ConditionGroup conditions = new ConditionGroup();
			conditions.setAppID(appId);
			conditions.setConditionGroupID(conGrpId);
			conditions.setDescription(desc);
			conditions.setConditionGroupName(name);
			conditions.setUpdatedBy(DataConstants.DEFAULT_USER);
			conditions.setUpdatedDateTime(DataConstants.DEFAULT_DATE);

			update = inputService.updateConditionGroupData(conditions);

			contGrpID = (int) update;

			Conditions condition = new Conditions();
			Conditions newCond = new Conditions();
			ArrayList<ArrayList<String>> multiConddata = (ArrayList<ArrayList<String>>) jsonObj.get("updateCondGrpArr");
			LOG.info("Persistence completed for Param Grp" + multiConddata);
			for (ArrayList<String> multiCond : multiConddata) {
				if (multiCond.size() > 3) {
					if (multiCond.size() == 4) {
						condition.setConditionGroupID(conGrpId);
						condition.setConditionName(multiCond.get(0));
						condition.setDescription(multiCond.get(1));
						condition.setExpression(multiCond.get(2));
						int cndId = Integer.parseInt(multiCond.get(3));
						condition.setConditionID(cndId);
						condition.setUpdatedBy(DataConstants.DEFAULT_USER);
						condition.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
						inputService.updateConditionsData(condition);
						LOG.info("condition details update: " + condition);
					} else {
						newCond.setConditionGroupID(conGrpId);
						newCond.setConditionName(multiCond.get(3));
						newCond.setDescription(multiCond.get(4));
						newCond.setExpression(multiCond.get(5));
						newCond.setUpdatedBy(DataConstants.DEFAULT_USER);
						newCond.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
						newCond.setCreatedBy(DataConstants.DEFAULT_USER);
						newCond.setCreatedDateTime(DataConstants.DEFAULT_DATE);
						inputService.insertConditionsGetKey(newCond);
						LOG.info("condition new details update: " + newCond);

					}
				}
			}
		} catch (Exception e) {
			LOG.error("updateConditionGrp(Map<String,String>)", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("updateConditionGrp(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return contGrpID;
	}
}
