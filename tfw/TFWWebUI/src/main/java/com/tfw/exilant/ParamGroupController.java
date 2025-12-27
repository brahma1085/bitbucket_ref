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

import com.exilant.tfw.pojo.input.Param;
import com.exilant.tfw.pojo.input.ParamGroup;
import com.exilant.tfw.service.InputService;
import com.exilant.tfw.utils.DataConstants;

/**
 * The Class ParamGroupController.
 */
@Controller
public class ParamGroupController {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(ParamGroupController.class);

	/** The input service. */
	private InputService inputService;

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
	 * Adds the param grp details.
	 * 
	 * @param paramGrpData
	 *            the param grp data
	 * @return the long
	 */
	@RequestMapping(value = "/addNewParamGrp", method = RequestMethod.POST)
	public @ResponseBody
	long addParamGrpDetails(@RequestBody Map<String, String> paramGrpData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addParamGrpDetails(Map<String,String>) - start"); //$NON-NLS-1$
		}
		long i = 0;
		ParamGroup paramGroup = new ParamGroup();
		try {
			JSONObject jsonObj = new JSONObject(paramGrpData);
			paramGroup.setParamGroupName((String) jsonObj.get("paramGroupName"));
			paramGroup.setDescription((String) jsonObj.get("description"));
			paramGroup.setTag((String) jsonObj.get("tag"));
			String id = (String) jsonObj.get("objectGroupID");
			int appID = (Integer) jsonObj.get("appID");
			paramGroup.setAppID(appID);
			paramGroup.setObjectGroupID(Integer.parseInt(id));
			paramGroup.setCreatedBy(DataConstants.DEFAULT_USER);
			paramGroup.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			paramGroup.setUpdatedBy(DataConstants.DEFAULT_USER);
			paramGroup.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			i = inputService.insertParamGroupGetKey(paramGroup);
		} catch (Exception e) {
			LOG.warn("addParamGrpDetails(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addParamGrpDetails(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return i;
	}

	/**
	 * Gets the param grps for app.
	 * 
	 * @param appID
	 *            the app id
	 * @return the param grps for app
	 */
	@RequestMapping(value = "/getParamGrpsForApp", method = RequestMethod.POST)
	public @ResponseBody
	List<ParamGroup> getParamGrpsForApp(@RequestBody Map<String, Integer> appID) {
		int id = 0;
		List<ParamGroup> paramGroup = new ArrayList<ParamGroup>();
		try {
			JSONObject jsonObj = new JSONObject(appID);
			id = (Integer) jsonObj.get("appID");
			paramGroup = inputService.getParamGroupByAppId(id);
		} catch (Exception e) {
			LOG.warn("getParamGrpsForApp(Map<String,Integer>) - exception ignored", e); //$NON-NLS-1$
		}
		return paramGroup;
	}

	/**
	 * Gets the param data for grp.
	 * 
	 * @param paramGrpID
	 *            the param grp id
	 * @return the param data for grp
	 */
	@RequestMapping(value = "/getParamDataForGrp", method = RequestMethod.POST)
	public @ResponseBody
	List<Param> getParamDataForGrp(@RequestBody Map<String, Integer> paramGrpID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamDataForGrp(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		int id = 0;
		List<Param> param = new ArrayList<Param>();
		try {
			JSONObject jsonObj = new JSONObject(paramGrpID);
			id = (Integer) jsonObj.get("paramGroupID");
			param = inputService.getParamByParamGrpId(id);
		} catch (Exception e) {
			LOG.warn("getParamDataForGrp(Map<String,Integer>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamDataForGrp(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return param;
	}

	/**
	 * Gets the param grp details for id.
	 * 
	 * @param paramGrpID
	 *            the param grp id
	 * @return the param grp details for id
	 */
	@RequestMapping(value = "/getParamGrpDetailsForID", method = RequestMethod.POST)
	public @ResponseBody
	ParamGroup getParamGrpDetailsForID(@RequestBody Map<String, Integer> paramGrpID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGrpDetailsForID(Map<String,Integer>) - start"); //$NON-NLS-1$
		}

		int id = 0;
		ParamGroup param = new ParamGroup();
		try {
			JSONObject jsonObj = new JSONObject(paramGrpID);
			try {
				id = (Integer) jsonObj.get("paramGroupID");
			} catch (JSONException e) {
				LOG.warn("getParamGrpDetailsForID(Map<String,Integer>) - exception ignored", e); //$NON-NLS-1$
			}
			param = inputService.getParamGrpDetailsById(id);
		} catch (Exception e) {
			LOG.warn("getParamGrpDetailsForID(Map<String,Integer>) - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGrpDetailsForID(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return param;
	}

	/**
	 * Update param grp.
	 * 
	 * @param paramGrpData
	 *            the param grp data
	 * @return the long
	 */
	@RequestMapping(value = "/updateParamGrp", method = RequestMethod.POST)
	public @ResponseBody
	long updateParamGrp(@RequestBody Map<String, String> paramGrpData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateParamGrp(Map<String,String>) - start"); //$NON-NLS-1$
		}
		long i = 0;
		ParamGroup paramGroup = new ParamGroup();
		try {
			JSONObject jsonObj = new JSONObject(paramGrpData);
			int paramGrpId = (Integer) jsonObj.get("paramGroupID");
			paramGroup.setParamGroupID(paramGrpId);
			paramGroup.setParamGroupName((String) jsonObj.get("paramGroupName"));
			paramGroup.setDescription((String) jsonObj.get("description"));
			paramGroup.setTag((String) jsonObj.get("tag"));
			String id = (String) jsonObj.get("objectGroupID");
			paramGroup.setObjectGroupID(Integer.parseInt(id));
			paramGroup.setUpdatedBy(DataConstants.DEFAULT_USER);
			paramGroup.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			i = inputService.updateParamGroupData(paramGroup);
		} catch (Exception e) {
			LOG.warn("updateParamGrp(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateParamGrp(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return i;
	}

	/**
	 * Adds the new param.
	 * 
	 * @param paramData
	 *            the param data
	 * @return the long
	 */
	@RequestMapping(value = "/addNewParam", method = RequestMethod.POST)
	public @ResponseBody
	long addNewParam(@RequestBody Map<String, String> paramData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addNewParam(Map<String,String>) - start"); //$NON-NLS-1$
		}
		long i = 0;
		Param param = new Param();
		try {
			JSONObject jsonObj = new JSONObject(paramData);
			param.setParamName((String) jsonObj.get("paramName"));
			param.setDescription((String) jsonObj.get("description"));
			String sOrder = (String) jsonObj.get("sortOrder");
			int id = (Integer) jsonObj.get("paramGroupID");
			String objId = (String) jsonObj.get("objectID");
			param.setSortOrder(Integer.parseInt(sOrder));
			param.setObjectID(Integer.parseInt(objId));
			param.setParamGroupID(id);
			param.setCreatedBy(DataConstants.DEFAULT_USER);
			param.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			param.setUpdatedBy(DataConstants.DEFAULT_USER);
			param.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			i = inputService.insertParamGetKey(param);
		} catch (Exception e) {
			LOG.warn("addNewParam(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addNewParam(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return i;
	}

	/**
	 * Gets the param details to update.
	 * 
	 * @param paramId
	 *            the param id
	 * @return the param details to update
	 */
	@RequestMapping(value = "/getParamDetailsToUpdate", method = RequestMethod.POST)
	public @ResponseBody
	Param getParamDetailsToUpdate(@RequestBody Map<String, Integer> paramId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamDetailsToUpdate(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		Param param = new Param();
		int id = 0;
		try {
			JSONObject jsonObj = new JSONObject(paramId);
			id = (Integer) jsonObj.get("paramID");
			param = inputService.getParamById(id);
		} catch (Exception e) {
			LOG.warn("getParamDetailsToUpdate(Map<String,Integer>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamDetailsToUpdate(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return param;
	}

	/**
	 * Update param.
	 * 
	 * @param paramDetails
	 *            the param details
	 * @return the long
	 */
	@RequestMapping(value = "/updateParam", method = RequestMethod.POST)
	public @ResponseBody
	long updateParam(@RequestBody Map<String, String> paramDetails) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateParam(Map<String,String>) - start"); //$NON-NLS-1$
		}
		Param param = new Param();
		long i = 0;
		try {
			JSONObject jsonObj = new JSONObject(paramDetails);
			param.setParamName((String) jsonObj.get("paramName"));
			param.setDescription((String) jsonObj.get("description"));
			String sOrder = (String) jsonObj.get("sortOrder");
			String id = (String) jsonObj.get("paramGroupID");
			String objId = (String) jsonObj.get("objectID");
			param.setSortOrder(Integer.parseInt(sOrder));
			param.setObjectID(Integer.parseInt(objId));
			param.setParamGroupID(Integer.parseInt(id));
			param.setParamID((Integer) jsonObj.get("paramID"));
			param.setUpdatedBy(DataConstants.DEFAULT_USER);
			param.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			i = inputService.updateParamDetails(param);
		} catch (Exception e) {
			LOG.warn("updateParam(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateParam(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return i;
	}

}
