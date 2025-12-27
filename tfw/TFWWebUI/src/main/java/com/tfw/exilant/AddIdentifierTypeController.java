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
import com.exilant.tfw.pojo.input.IdentifierType;
import com.exilant.tfw.service.InputService;
import com.exilant.tfw.utils.DataConstants;

/**
 * The Class AddIdentifierTypeController.
 */
@Controller
public class AddIdentifierTypeController {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(AddIdentifierTypeController.class);

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
	@RequestMapping(value = "/addIdentifierTypes", method = RequestMethod.POST)
	public @ResponseBody
	int addIdentifierTypes(@RequestBody Map<String, String> IdentifierData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addIdentifierTypes(Map<String,String>) - start"); //$NON-NLS-1$
		}
		int i = 0;
		IdentifierType idenType = new IdentifierType();
		try {
			JSONObject jsonObj = new JSONObject(IdentifierData);
			String name = (String) jsonObj.get("identifierTypeName");
			String desc = (String) jsonObj.get("description");
			int appId = (Integer) jsonObj.get("appID");
			idenType.setIdentifierTypeName(name);
			idenType.setDescription(desc);
			idenType.setAppID(appId);
			idenType.setCreatedBy(DataConstants.DEFAULT_USER);
			idenType.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			idenType.setUpdatedBy(DataConstants.DEFAULT_USER);
			idenType.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			i = inputService.insertIdentifierTypeGetKey(idenType);
		} catch (Exception e) {
			LOG.warn("addIdentifierTypes(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
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
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/getIdentifierTypeByAppID", method = RequestMethod.POST)
	public @ResponseBody
	List<IdentifierType> getIdentifierTypeByAppID(@RequestBody String appID) throws ServiceException {
		int appId = Integer.parseInt(appID);
		List<IdentifierType> AppId = new ArrayList<IdentifierType>();
		try {
			AppId = inputService.getIdentifierTypeByAppID(appId);
		} catch (ServiceException se) {
			LOG.warn("getIdentifierTypeByAppID(String) - exception ignored", se); //$NON-NLS-1$
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
	@RequestMapping(value = "/editIdentifierType", method = RequestMethod.POST)
	public @ResponseBody
	IdentifierType getIdentifierTypeById(@RequestBody Map<String, Integer> identifierType) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierTypeById(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		IdentifierType tIdentifierType = new IdentifierType();
		try {
			JSONObject jsonObject = new JSONObject(identifierType);
			int identifierTypeId = (Integer) jsonObject.get("identifierTypeID");
			tIdentifierType = inputService.getIdentifierTypeById(identifierTypeId);
		} catch (Exception se) {
			LOG.warn("getIdentifierTypeById(Map<String,Integer>) - exception ignored", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierTypeById(Map<String,Integer>) - end"); //$NON-NLS-1$
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
	@RequestMapping(value = "/updateIdentifierType", method = RequestMethod.POST)
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
			LOG.warn("updateIdentifierType(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateIdentifierType(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return update;
	}

}