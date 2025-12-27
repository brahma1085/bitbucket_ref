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
import com.exilant.tfw.pojo.def.ObjectsUI;
import com.exilant.tfw.pojo.input.Objects;
import com.exilant.tfw.pojo.input.Param;
import com.exilant.tfw.pojo.input.ParamGroup;
import com.exilant.tfw.service.InputService;
import com.exilant.tfw.utils.DataConstants;

/**
 * The Class AddParamGroupControllerITAP.
 */
@Controller
public class AddParamGroupControllerITAP {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AddParamGroupControllerITAP.class);

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
	 * Gets the param group names by app id.
	 * 
	 * @param appID
	 *            the app id
	 * @return the param group names by app id
	 */
	@RequestMapping(value = "/getParamGroupNamesByAppIdITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<ParamGroup> getParamGroupNamesByAppId(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGroupNamesByAppId(String) - start"); //$NON-NLS-1$
		}
		LOG.info("Inside getParamGroupNamesByAppId method");
		int appId = Integer.parseInt(appID);
		LOG.info("appID to retrive :: " + appId);
		List<ParamGroup> ParamName = new ArrayList<ParamGroup>();
		try {
			LOG.info("inside getParamGroupNamesByAppId at appId");
			ParamName = inputService.getParamGroupNamesByAppId(appId);
		} catch (ServiceException se) {
			LOG.error("getParamGroupNamesByAppId(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGroupNamesByAppId(String) - end"); //$NON-NLS-1$
		}
		return ParamName;
	}

	/**
	 * Gets the param names by app id.
	 * 
	 * @param appID
	 *            the app id
	 * @return the param names by app id
	 */
	@RequestMapping(value = "/getParamNamesByAppIdITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<ObjectsUI> getParamNamesByAppId(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamNamesByAppId(String) - start"); //$NON-NLS-1$
		}
		LOG.info("Inside getParamNamesByAppId method");
		int appId = Integer.parseInt(appID);
		LOG.info("appID to retrive :: " + appId);
		List<Param> paramName = new ArrayList<Param>();
		List<ObjectsUI> objectsUIList = new ArrayList<ObjectsUI>();
		try {
			LOG.info("inside getParamGroupNamesByAppId at appId");
			paramName = inputService.getParamByAppId(appId);
			for (Param pram : paramName) {
				ObjectsUI objectsUI = new ObjectsUI();
				Objects objects = new Objects();
				if (pram.getObjectID() != 0) {
					objects = inputService.getObjectsById(pram.getObjectID());
					objectsUI.setObjectName(objects.getObjectName());
					objectsUI.setParamDesc(pram.getDescription());
					objectsUI.setParamId(pram.getParamID());
					objectsUI.setParamName(pram.getParamName());
					objectsUI.setSortOrder(pram.getSortOrder());
					objectsUIList.add(objectsUI);
				}
			}
		} catch (ServiceException se) {
			LOG.error("getParamNamesByAppId(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamNamesByAppId(String) - end"); //$NON-NLS-1$
		}
		return objectsUIList;
	}

	/**
	 * Gets the param names by param group name.
	 * 
	 * @param paramGroupID
	 *            the param group id
	 * @return the param names by param group name
	 */
	@RequestMapping(value = "/getParamNamesByParamGroupIDITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<Param> getParamNamesByParamGroupName(@RequestBody String paramGroupID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamNamesByParamGroupName(String) - start"); //$NON-NLS-1$
		}
		if (paramGroupID.contains("\"")) {
			paramGroupID = paramGroupID.substring(1, paramGroupID.length() - 1);
		}
		LOG.info("Inside getParamNamesByParamGroupIDITAP method" + paramGroupID);
		int paramGrpID = Integer.parseInt(paramGroupID);
		LOG.info("paramGrpID to retrive :: " + paramGrpID);
		List<Param> paramList = new ArrayList<Param>();
		try {
			LOG.info("inside getParamGroupNamesByAppId at appId");
			paramList = inputService.getParamByParamGrpId(paramGrpID);
		} catch (ServiceException se) {
			LOG.error("getParamNamesByParamGroupName(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamNamesByParamGroupName(String) - end"); //$NON-NLS-1$
		}
		return paramList;
	}

	/**
	 * Adds the param group itap.
	 * 
	 * @param testParamData
	 *            the test param data
	 * @return the int
	 */
	@RequestMapping(value = "/addParamGroupITAP", method = RequestMethod.POST)
	public @ResponseBody
	int addParamGroupITAP(@RequestBody Map<String, String> testParamData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addParamGroupITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}

		int paramGroupId = 0;
		LOG.info("outside addParamGroupITAP");
		try {
			ParamGroup paramGroup = new ParamGroup();
			JSONObject jsonObject = new JSONObject(testParamData);
			String paramGroupName = (String) jsonObject.get("paramGroupName");
			String description = (String) jsonObject.get("description");
			String tagName = (String) jsonObject.get("tagName");
			int objectId = (Integer) jsonObject.get("objectGroupID");
			int appIDs = (Integer) jsonObject.get("appID");

			paramGroup.setParamGroupName(paramGroupName);
			paramGroup.setDescription(description);
			paramGroup.setTag(tagName);
			paramGroup.setObjectGroupID(objectId);
			paramGroup.setAppID(appIDs);
			paramGroup.setCreatedBy(DataConstants.DEFAULT_USER);
			paramGroup.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			paramGroup.setUpdatedBy(DataConstants.DEFAULT_USER);
			paramGroup.setUpdatedDateTime(DataConstants.DEFAULT_DATE);

			paramGroupId = inputService.insertParamGroupGetKey(paramGroup);
			LOG.info("paramGroup Id :::::::::::" + paramGroupId);

			Param param = new Param();
			@SuppressWarnings("unchecked")
			ArrayList<ArrayList<String>> multiparamdata = (ArrayList<ArrayList<String>>) jsonObject.get("paramArr");
			LOG.info("Persistence completed for Param Grp" + multiparamdata);
			for (int i = 1; i < multiparamdata.size(); i++) {
				ArrayList<String> paramData = (ArrayList<String>) multiparamdata.get(i);
				String parts = paramData.toString();
				String[] paramDetails = parts.split(",");
				param.setParamName((paramDetails[0].replace("[", "").trim()));
				param.setDescription(paramDetails[1].trim());
				String paramdet = paramDetails[3].replace("]", "");
				int sortOrder = 0;
				if (paramdet != null && paramdet.length() > 0) {
					sortOrder = Integer.parseInt(paramdet.trim());
				}
				int objectID = Integer.parseInt(paramDetails[2].trim());
				param.setSortOrder(sortOrder);
				param.setObjectID(objectID);
				param.setParamGroupID(paramGroupId);
				param.setCreatedBy(DataConstants.DEFAULT_USER);
				param.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				param.setUpdatedBy(DataConstants.DEFAULT_USER);
				param.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
				inputService.insertParamGetKey(param);
				LOG.info(" param id .....: " + param);
			}
		} catch (JSONException e) {
			LOG.error("addParamGroupITAP(Map<String,String>)", e);
		} catch (ServiceException e) {
			LOG.error("addParamGroupITAP(Map<String,String>)", e);
		}
		int returnint = (int) paramGroupId;
		if (LOG.isDebugEnabled()) {
			LOG.debug("addParamGroupITAP(Map<String,String>) - end");
		}
		return returnint;
	}

	/**
	 * Edits the param grp itap.
	 * 
	 * @param paramGrpid
	 *            the param grpid
	 * @return the param group
	 */
	@RequestMapping(value = "/editParamGrpITAP", method = RequestMethod.POST)
	public @ResponseBody
	ParamGroup editParamGrpITAP(@RequestBody Map<String, Integer> paramGrpid) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("editParamGrpITAP(Map<String,Integer>) - start");
		}
		ParamGroup paramGroup = new ParamGroup();
		LOG.info("Inside editParamGrpITAP method");
		try {
			JSONObject jsonObject = new JSONObject(paramGrpid);
			int paramGroupid = (Integer) jsonObject.get("paramGrpid");
			paramGroup = inputService.getParamGroupById(paramGroupid);
			LOG.info("paramGroup Name:: " + paramGroupid);
		} catch (ServiceException se) {
			LOG.error("editParamGrpITAP(Map<String,Integer>)", se);
		} catch (JSONException e) {
			LOG.error("editParamGrpITAP(Map<String,Integer>)", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("editParamGrpITAP(Map<String,Integer>) - end");
		}
		return paramGroup;
	}

	/**
	 * Update param grp itap.
	 * 
	 * @param paramGrp
	 *            the param grp
	 * @return the long
	 */
	@RequestMapping(value = "/updateParamGrpITAP", method = RequestMethod.POST)
	public @ResponseBody
	long updateParamGrpITAP(@RequestBody Map<String, String> paramGrp) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateParamGrpITAP(Map<String,String>) - start");
		}
		long update = 0;
		try {
			JSONObject jsonObject = new JSONObject(paramGrp);
			ParamGroup paramGroup = new ParamGroup();
			LOG.info("iobj id: " + jsonObject.get("objectGroupID"));
			String paramGroupName = (String) jsonObject.get("paramGroupName");
			String description = (String) jsonObject.get("description");
			String tagName = (String) jsonObject.get("tagName");
			int objectId = (Integer) jsonObject.get("objectGroupID");
			int appIDs = (Integer) jsonObject.get("appID");
			int paramGrpId = (Integer) jsonObject.get("editParanGrpID");
			LOG.info("param grp id :" + paramGrpId);
			paramGroup.setParamGroupID(paramGrpId);
			paramGroup.setParamGroupName(paramGroupName);
			paramGroup.setDescription(description);
			paramGroup.setTag(tagName);
			paramGroup.setObjectGroupID(objectId);
			paramGroup.setAppID(appIDs);
			paramGroup.setUpdatedBy(DataConstants.DEFAULT_USER);
			paramGroup.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			update = inputService.updateParamGroupData(paramGroup);
			LOG.info("paramGroup Id :::::::::::" + update);
			@SuppressWarnings("unchecked")
			ArrayList<ArrayList<String>> multiparamdata = (ArrayList<ArrayList<String>>) jsonObject.get("updateparamArr");
			LOG.info("multiparamdata values :" + multiparamdata);
			Param param = new Param();
			Param paramNew = new Param();
			for (ArrayList<String> paramdata : multiparamdata) {
				LOG.info("param values :" + paramdata);
				if (paramdata.size() > 3) {
					LOG.info("param size :" + paramdata.size());
					if (paramdata.size() == 5) {
						int paramId = Integer.parseInt(paramdata.get(3));
						int sortOrder = Integer.parseInt(paramdata.get(2));
						int objtId = Integer.parseInt(paramdata.get(4));
						param.setParamGroupID(paramGrpId);
						param.setParamID(paramId);
						param.setParamName(paramdata.get(0));
						param.setDescription(paramdata.get(1));
						param.setSortOrder(sortOrder);
						param.setObjectID(objtId);
						param.setUpdatedBy(DataConstants.DEFAULT_USER);
						param.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
						inputService.updateParamDetails(param);
						LOG.info("param details update: " + param);
					} else {
						paramNew.setParamGroupID(paramGrpId);
						paramNew.setParamName(paramdata.get(3));
						paramNew.setDescription(paramdata.get(4));
						int sortOrder = Integer.parseInt(paramdata.get(6));
						paramNew.setSortOrder(sortOrder);
						int objtId = Integer.parseInt(paramdata.get(5));
						paramNew.setObjectID(objtId);
						paramNew.setUpdatedBy(DataConstants.DEFAULT_USER);
						paramNew.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
						paramNew.setCreatedBy(DataConstants.DEFAULT_USER);
						paramNew.setCreatedDateTime(DataConstants.DEFAULT_DATE);
						inputService.insertParamGetKey(paramNew);
						LOG.info("param details new: " + paramNew);
					}
				}
			}
		} catch (Exception e) {
			LOG.error("updateParamGrpITAP(Map<String,String>)", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateParamGrpITAP(Map<String,String>) - end");
		}
		return update;
	}

}
