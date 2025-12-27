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
import com.exilant.tfw.pojo.input.ReplacementStrings;
import com.exilant.tfw.service.InputService;

/**
 * The Class AddReplacementStringsController.
 */
@Controller
public class AddReplacementStringsController {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(AddReplacementStringsController.class);

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
	 * Adds the replace strings.
	 * 
	 * @param ReplaceStringsData
	 *            the replace strings data
	 */
	@RequestMapping(value = "/addReplaceStringsData", method = RequestMethod.POST)
	public @ResponseBody
	void addReplaceStrings(@RequestBody Map<String, String> ReplaceStringsData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addReplaceStrings(Map<String,String>) - start"); //$NON-NLS-1$
		}
		ReplacementStrings replaceStr = new ReplacementStrings();
		try {
			JSONObject jsonObj = new JSONObject(ReplaceStringsData);
			String name = (String) jsonObj.get("name");
			String value = (String) jsonObj.get("value");
			String levels = (String) jsonObj.get("level");
			String foreign = (String) jsonObj.get("foreign_ID");
			int appId = (Integer) jsonObj.get("appID");
			replaceStr.setName(name);
			replaceStr.setValue(value);
			replaceStr.setForeignID(foreign);
			replaceStr.setLevel(levels);
			replaceStr.setAppID(appId);
			inputService.insertReplacementStrings(replaceStr);
		} catch (Exception e) {
			LOG.warn("addReplaceStrings(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addReplaceStrings(Map<String,String>) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the replacement strings by app id.
	 * 
	 * @param appID
	 *            the app id
	 * @return the replacement strings by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/getReplacementStringsByAppID", method = RequestMethod.POST)
	public @ResponseBody
	List<ReplacementStrings> getReplacementStringsByAppID(@RequestBody String appID) throws ServiceException {
		int appId = Integer.parseInt(appID);
		List<ReplacementStrings> AppId = new ArrayList<ReplacementStrings>();
		try {
			AppId = inputService.getReplacementStringsByAppID(appId);
		} catch (ServiceException se) {
			LOG.warn("getReplacementStringsByAppID(String) - exception ignored", se); //$NON-NLS-1$
		}
		return AppId;
	}

	/**
	 * Gets the replacement strings by id.
	 * 
	 * @param replacementId
	 *            the replacement id
	 * @return the replacement strings by id
	 */
	@RequestMapping(value = "/editReplaceStrings", method = RequestMethod.POST)
	public @ResponseBody
	ReplacementStrings getReplacementStringsById(@RequestBody Map<String, Integer> replacementId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getReplacementStringsById(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		ReplacementStrings tReplacementStr = new ReplacementStrings();
		try {
			JSONObject jsonObject = new JSONObject(replacementId);
			int replacementIds = (Integer) jsonObject.get("id");
			tReplacementStr = inputService.getReplacementStringsById(replacementIds);
		} catch (Exception se) {
			LOG.warn("getReplacementStringsById(Map<String,Integer>) - exception ignored", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getReplacementStringsById(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return tReplacementStr;
	}

	/**
	 * Update replacement strings.
	 * 
	 * @param replacementDatas
	 *            the replacement datas
	 * @return the long
	 */
	@RequestMapping(value = "/updateReplaceStringsType", method = RequestMethod.POST)
	public @ResponseBody
	long updateReplacementStrings(@RequestBody Map<String, String> replacementDatas) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateReplacementStrings(Map<String,String>) - start"); //$NON-NLS-1$
		}
		long update = 0;
		try {
			JSONObject jsonObj = new JSONObject(replacementDatas);
			String name = (String) jsonObj.get("name");
			String value = (String) jsonObj.get("value");
			String levels = (String) jsonObj.get("level");
			int repIDs = (Integer) jsonObj.get("id");
			ReplacementStrings replaceStr = new ReplacementStrings();
			replaceStr.setName(name);
			replaceStr.setValue(value);
			replaceStr.setID(repIDs);
			replaceStr.setLevel(levels);
			update = inputService.updateReplacementStrings(replaceStr);
		} catch (Exception e) {
			LOG.warn("updateReplacementStrings(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateReplacementStrings(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return update;
	}

}