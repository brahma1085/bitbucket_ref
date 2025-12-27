package com.tfw.exilant.ITAP;

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
import com.exilant.tfw.pojo.input.IdentifierType;
import com.exilant.tfw.service.InputService;
import com.exilant.tfw.utils.DataConstants;

/**
 * The Class AddIdentifierTypeControllerITAP.
 */
@Controller
public class AddIdentifierTypeControllerITAP {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AddIdentifierTypeControllerITAP.class);

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
	 * Adds the identifier types.
	 * 
	 * @param IdentifierData
	 *            the identifier data
	 * @return the int
	 */
	@RequestMapping(value = "/addIdentifierTypesITAP", method = RequestMethod.POST)
	public @ResponseBody
	int addIdentifierTypes(@RequestBody Map<String, String> IdentifierData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addIdentifierTypes(Map<String,String>) - start"); //$NON-NLS-1$
		}

		int i = 0;
		IdentifierType idenType = new IdentifierType();
		try {
			JSONObject jsonObj = new JSONObject(IdentifierData);
			try {
				String name = (String) jsonObj.get("identifierTypeName");
				String desc = (String) jsonObj.get("description");
				int appId = (Integer) jsonObj.get("appID");

				// int id = Integer.parseInt(appId);

				idenType.setIdentifierTypeName(name);
				idenType.setDescription(desc);
				idenType.setAppID(appId);
				idenType.setCreatedBy(DataConstants.DEFAULT_USER);
				idenType.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				idenType.setUpdatedBy(DataConstants.DEFAULT_USER);
				idenType.setUpdatedDateTime(DataConstants.DEFAULT_DATE);

				i = inputService.insertIdentifierTypeGetKey(idenType);
			} catch (JSONException e) {
				LOG.error("addIdentifierTypes(Map<String,String>)", e); //$NON-NLS-1$

			}
		} catch (Exception e) {
			LOG.error("addIdentifierTypes(Map<String,String>)", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("addIdentifierTypes(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return i;
	}

	/**
	 * Gets the identifier type by app id.
	 * 
	 * @param appID
	 *            the app id
	 * @return the identifier type by app id
	 */
	@RequestMapping(value = "/getIdentifierTypeByAppIDITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<IdentifierType> getIdentifierTypeByAppID(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierTypeByAppID(String) - start"); //$NON-NLS-1$
		}
		LOG.info("Inside getTestScenarioByAppId method");
		int appId = Integer.parseInt(appID);
		LOG.info("appID to retrive :: " + appId);
		List<IdentifierType> AppId = new ArrayList<IdentifierType>();
		try {
			LOG.info("inside getIdentifierTypeByAppID at appId");
			AppId = inputService.getIdentifierTypeByAppID(appId);
		} catch (ServiceException se) {
			LOG.error("getIdentifierTypeByAppID(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierTypeByAppID(String) - end"); //$NON-NLS-1$
		}
		return AppId;
	}

	/**
	 * Gets the identifier type by id.
	 * 
	 * @param identifierType
	 *            the identifier type
	 * @return the identifier type by id
	 */
	@RequestMapping(value = "/editIdentifierTypeITAP", method = RequestMethod.POST)
	public @ResponseBody
	IdentifierType getIdentifierTypeById(@RequestBody Map<String, Integer> identifierType) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierTypeById(Map<String,Integer>) - start");
		}
		IdentifierType tIdentifierType = new IdentifierType();
		try {
			LOG.info("Inside getIdentifierTypeById method");
			LOG.info("Printing data recieved from client :: " + identifierType);
			JSONObject jsonObject = new JSONObject(identifierType);

			int identifierTypeId = (Integer) jsonObject.get("identifierTypeID");
			LOG.info("identifierType ID to edit :: " + identifierTypeId);
			tIdentifierType = inputService.getIdentifierTypeById(identifierTypeId);
			LOG.info("IdentifierType Name:: " + tIdentifierType.getIdentifierTypeName());
		} catch (ServiceException se) {
			LOG.error("getIdentifierTypeById(Map<String,Integer>)", se);
		} catch (JSONException e) {
			LOG.error("getIdentifierTypeById(Map<String,Integer>)", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierTypeById(Map<String,Integer>) - end");
		}
		return tIdentifierType;

	}

	/**
	 * Update identifier type.
	 * 
	 * @param identifierTypeDatas
	 *            the identifier type datas
	 * @return the long
	 */
	@RequestMapping(value = "/updateIdentifierTypeITAP", method = RequestMethod.POST)
	public @ResponseBody
	long updateIdentifierType(@RequestBody Map<String, String> identifierTypeDatas) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateIdentifierType(Map<String,String>) - start"); //$NON-NLS-1$
		}

		long update = 0;

		try {
			JSONObject jsonObj = new JSONObject(identifierTypeDatas);

			String name = (String) jsonObj.get("identifierTypeName");
			String desc = (String) jsonObj.get("description");
			int identifierId = (Integer) jsonObj.get("identifierTypeID");

			IdentifierType idenType = new IdentifierType();

			idenType.setIdentifierTypeName(name);
			idenType.setDescription(desc);
			idenType.setIdentifierTypeID(identifierId);
			idenType.setUpdatedBy(DataConstants.DEFAULT_USER);
			idenType.setUpdatedDateTime(DataConstants.DEFAULT_DATE);

			update = inputService.updateIdentifierType(idenType);

		} catch (Exception e) {
			LOG.error("updateIdentifierType(Map<String,String>)", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("updateIdentifierType(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return update;
	}

}
