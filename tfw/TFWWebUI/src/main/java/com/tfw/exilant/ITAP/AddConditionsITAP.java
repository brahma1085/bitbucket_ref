package com.tfw.exilant.ITAP;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exilant.tfw.pojo.input.ConditionGroup;
import com.exilant.tfw.pojo.input.Conditions;
import com.exilant.tfw.service.InputService;

/**
 * The Class AddConditionsITAP.
 */
@Controller
public class AddConditionsITAP {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AddConditionsITAP.class);

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
	 * Gets the conditions itap.
	 * 
	 * @param appID
	 *            the app id
	 * @return the conditions itap
	 */
	@RequestMapping(value = "/getConditionsITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<Conditions> getConditionsITAP(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionsITAP(String) - start"); //$NON-NLS-1$
		}

		List<Conditions> conditions = new ArrayList<Conditions>();
		List<ConditionGroup> conditionGrp = new ArrayList<ConditionGroup>();
		int appId = Integer.parseInt(appID);
		LOG.info("appID to retrive :: " + appId);
		try {

			conditionGrp = inputService.getConditionGroupNamesByAppId(appId);
			for (ConditionGroup conditionGroup : conditionGrp) {
				List<Conditions> conditions2 = inputService.getConditionByConditionGroupId(conditionGroup.getConditionGroupID());
				conditions.addAll(conditions2);
			}

		} catch (Exception e) {
			LOG.error("getConditionsITAP(String)", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionsITAP(String) - end"); //$NON-NLS-1$
		}
		return conditions;
	}

	/**
	 * Gets the conditions by condition grp iditap.
	 * 
	 * @param CondGrpId
	 *            the cond grp id
	 * @return the conditions by condition grp iditap
	 */
	@RequestMapping(value = "/getConditionsByConditionGrpIDITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<Conditions> getConditionsByConditionGrpIDITAP(@RequestBody String CondGrpId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionsITAP(String) - start"); //$NON-NLS-1$
		}

		List<Conditions> conditions = new ArrayList<Conditions>();

		int condGrpIds = Integer.parseInt(CondGrpId);
		LOG.info("Condition GrpID to retrive :: " + condGrpIds);
		try {

			conditions = inputService.getConditionByConditionGroupId(condGrpIds);
			LOG.info("Condition Grp List ::" + conditions);

		} catch (Exception e) {
			LOG.error("getConditionsITAP(String)", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionsITAP(String) - end"); //$NON-NLS-1$
		}
		return conditions;
	}

}
