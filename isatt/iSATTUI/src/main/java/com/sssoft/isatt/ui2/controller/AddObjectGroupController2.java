package com.sssoft.isatt.ui2.controller;

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

import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.input.ObjectGroup;
import com.sssoft.isatt.data.pojo.input.ObjectType;
import com.sssoft.isatt.data.pojo.input.Objects;
import com.sssoft.isatt.data.service.InputService;
import com.sssoft.isatt.data.utils.DataConstants;

/**
 * The Class AddObjectGroupController2.
 */
@Controller
public class AddObjectGroupController2 {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AddObjectGroupController2.class);

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
	 * Gets the object group box by app id itap.
	 * 
	 * @param appID
	 *            the app id
	 * @return the object group box by app id itap
	 */
	@RequestMapping(value = "/getObjectGroupBoxByAppIdITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<ObjectGroup> getObjectGroupBoxByAppIdITAP(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectGroupBoxByAppIdITAP(String) - start"); //$NON-NLS-1$
		}
		LOG.info("Inside getObjectGroupBoxByAppIdITAP method");
		int appId = Integer.parseInt(appID);
		LOG.info("appID to retrive :: " + appId);
		List<ObjectGroup> objectGroups = new ArrayList<ObjectGroup>();
		try {
			objectGroups = inputService.getObjGrpsByAppID(appId);
			LOG.info("Object Groups " + objectGroups.toString());
		} catch (ServiceException se) {
			LOG.error("getObjectGroupBoxByAppIdITAP(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectGroupBoxByAppIdITAP(String) - end"); //$NON-NLS-1$
		}
		return objectGroups;
	}

	/**
	 * Adds the object group controller.
	 * 
	 * @param objectGrp
	 *            the object grp
	 * @return the int
	 */
	@RequestMapping(value = "/addObjGrpITAP", method = RequestMethod.POST)
	public @ResponseBody
	int addObjectGroupController(@RequestBody Map<String, String> objectGrp) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addObjectGroupController(Map<String,String>) - start"); //$NON-NLS-1$
		}

		ObjectGroup objGrp = new ObjectGroup();
		int objGrpId = 0;
		try {
			JSONObject jsonObj = new JSONObject(objectGrp);
			try {
				int scrId = (Integer) jsonObj.get("screenID");
				objGrp.setScreenID(scrId);
				objGrp.setObjectGroupName((String) jsonObj.get("objGrpName"));
				objGrp.setDescription((String) jsonObj.get("objGrpDesc"));

				objGrp.setAppID((Integer) jsonObj.get("appID"));
				objGrp.setCreatedBy(DataConstants.DEFAULT_USER);
				objGrp.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				objGrp.setUpdatedBy(DataConstants.DEFAULT_USER);
				objGrp.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			} catch (JSONException e) {
				LOG.error("addObjectGroupController(Map<String,String>)", e); //$NON-NLS-1$

			}

			objGrpId = inputService.insertObjectGroupGetKey(objGrp);
			int objGrpIds = (int) objGrpId;
			Objects object = new Objects();

			@SuppressWarnings("unchecked")
			ArrayList<ArrayList<String>> multiobjectdata = (ArrayList<ArrayList<String>>) jsonObj.get("objectArr");

			for (int i = 1; i < multiobjectdata.size(); i++) {
				ArrayList<String> objData = (ArrayList<String>) multiobjectdata.get(i);
				String parts = objData.toString();
				String[] objDetails = parts.split(",");

				int objType = Integer.parseInt(objDetails[2].trim());
				int identifierType = Integer.parseInt(objDetails[3].replace("]", "").trim());

				object.setObjectName(objDetails[0].replace("[", "").trim());
				object.setDescription(objDetails[1].trim());
				object.setAppID((Integer) jsonObj.get("appID"));
				object.setObjectTypeID(objType);
				object.setIdentifierTypeID(identifierType);
				object.setObjectGroupID(objGrpIds);
				object.setCreatedBy(DataConstants.DEFAULT_USER);
				object.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				object.setUpdatedBy(DataConstants.DEFAULT_USER);
				object.setUpdatedDateTime(DataConstants.DEFAULT_DATE);

				inputService.insertObjectsGetKey(object);
			}

			LOG.info("Persistence completed for Object Grp");
		} catch (Exception e) {
			LOG.error("addObjectGroupController(Map<String,String>)", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("addObjectGroupController(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return objGrpId;
	}

	/**
	 * Gets the obj types.
	 * 
	 * @return the obj types
	 */
	@RequestMapping(value = "/getObjTypesITAP", method = RequestMethod.GET)
	public @ResponseBody
	List<ObjectType> getObjTypes() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjTypes() - start"); //$NON-NLS-1$
		}

		List<ObjectType> objTypes = new ArrayList<ObjectType>();
		LOG.info("Inside getAllObjTypes");
		try {
			objTypes = inputService.getAllObjTypes();
			LOG.info("Fetched objTypes " + objTypes.size());
		} catch (ServiceException se) {
			LOG.error("getObjTypes()", se); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjTypes() - end"); //$NON-NLS-1$
		}
		return objTypes;
	}

	/**
	 * Gets the objs.
	 * 
	 * @param objGrpID
	 *            the obj grp id
	 * @return the objs
	 */
	@RequestMapping(value = "/getObjsITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<Objects> getObjs(@RequestBody String objGrpID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjs(String) - start"); //$NON-NLS-1$
		}

		List<Objects> objs = new ArrayList<Objects>();
		LOG.info("object IIIIII   " + objGrpID);
		int objGrpId = Integer.parseInt(objGrpID);
		try {
			objs = inputService.getObjectsForObjGrp(objGrpId);
			LOG.info("Fetched objTypes " + objs.size());
		} catch (ServiceException se) {
			LOG.error("getObjs(String)", se); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjs(String) - end"); //$NON-NLS-1$
		}
		return objs;
	}

	/**
	 * Gets the object group by id.
	 * 
	 * @param objectGroup
	 *            the object group
	 * @return the object group by id
	 */
	@RequestMapping(value = "/editObjectGroupITAP", method = RequestMethod.POST)
	public @ResponseBody
	ObjectGroup getObjectGroupById(@RequestBody Map<String, Integer> objectGroup) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectGroupById(Map<String,Integer>) - start");
		}
		ObjectGroup objectGroupIDs = new ObjectGroup();
		LOG.info("Inside getObjectGroupById method");
		LOG.info("Printing data recieved from client :: " + objectGroup);
		try {
			JSONObject jsonObject = new JSONObject(objectGroup);
			int objectGroupID = (Integer) jsonObject.get("objectGroupID");
			LOG.info("objectGroupID to edit :: " + objectGroupID);
			objectGroupIDs = inputService.getObjectGroupById(objectGroupID);
			LOG.info("ObjectGroup Name:: " + objectGroupIDs.getObjectGroupName());
		} catch (ServiceException se) {
			LOG.error("getObjectGroupById(Map<String,Integer>)", se);
		} catch (JSONException e) {
			LOG.error("getObjectGroupById(Map<String,Integer>)", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectGroupById(Map<String,Integer>) - end");
		}
		return objectGroupIDs;

	}

	/**
	 * Gets the object by object group id.
	 * 
	 * @param object
	 *            the object
	 * @return the object by object group id
	 */
	@RequestMapping(value = "/editObjectITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<Objects> getObjectByObjectGroupId(@RequestBody String object) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectByObjectGroupId(String) - start");
		}
		LOG.info("Inside getObjectByObjectGroupId method");
		LOG.info("Printing data recieved from client :: " + object);
		int objID = Integer.parseInt(object);
		LOG.info("objectGroupID to edit :: " + objID);
		List<Objects> objectIDs = new ArrayList<Objects>();
		try {
			objectIDs = inputService.getObjectsByGroupId(objID);
			LOG.info("object List By Object Group ID:: " + objectIDs.toString());
		} catch (ServiceException se) {
			LOG.error("getObjectByObjectGroupId(String)", se);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectByObjectGroupId(String) - end");
		}
		return objectIDs;
	}

	/**
	 * Update object group itap.
	 * 
	 * @param ObjectGroupData
	 *            the object group data
	 * @return the long
	 */
	@RequestMapping(value = "/updateObjectGroupITAP", method = RequestMethod.POST)
	public @ResponseBody
	long updateObjectGroupITAP(@RequestBody Map<String, String> ObjectGroupData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateObjectGroupITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}
		long update = 0;
		try {
			JSONObject jsonObj = new JSONObject(ObjectGroupData);
			String objGrpName = (String) jsonObj.get("objectGroupName");
			String desc = (String) jsonObj.get("description");
			int objGrpId = (Integer) jsonObj.get("objectGroupID");
			int screenID = (Integer) jsonObj.get("screenID");
			ObjectGroup objGrp = new ObjectGroup();
			objGrp.setObjectGroupName(objGrpName);
			objGrp.setDescription(desc);
			objGrp.setObjectGroupID(objGrpId);
			objGrp.setScreenID(screenID);
			objGrp.setUpdatedBy(DataConstants.DEFAULT_USER);
			objGrp.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			update = inputService.updateObjectGroupData(objGrp);
			Objects object = new Objects();
			Objects objects = new Objects();
			@SuppressWarnings("unchecked")
			ArrayList<ArrayList<String>> multiobjectdata = (ArrayList<ArrayList<String>>) jsonObj.get("updateObjectArr");
			for (int i = 1; i < multiobjectdata.size(); i++) {
				ArrayList<String> objData = (ArrayList<String>) multiobjectdata.get(i);
				int ObjArraySize = objData.size();
				if (ObjArraySize == 5) {
					String parts = objData.toString();
					String[] objDetails = parts.split(",");
					int objId = Integer.parseInt(objDetails[2].trim());
					int objType = Integer.parseInt(objDetails[3].trim());
					int identifierType = Integer.parseInt(objDetails[4].replace("]", "").trim());
					object.setObjectID(objId);
					object.setObjectName(objDetails[0].replace("[", "").trim());
					object.setDescription(objDetails[1].trim());
					object.setObjectTypeID(objType);
					object.setIdentifierTypeID(identifierType);
					object.setUpdatedBy(DataConstants.DEFAULT_USER);
					object.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
					inputService.updateObjects(object);
					LOG.info("update value of Objects::" + object.toString());
				} else {
					String parts = objData.toString();
					String[] objDetails = parts.split(",");
					int objType = Integer.parseInt(objDetails[4].trim());
					int identifierType = Integer.parseInt(objDetails[5].replace("]", "").trim());
					objects.setObjectName(objDetails[2].trim());
					objects.setDescription(objDetails[3].trim());
					objects.setAppID((Integer) jsonObj.get("appID"));
					objects.setObjectTypeID(objType);
					objects.setIdentifierTypeID(identifierType);
					objects.setObjectGroupID(objGrpId);
					objects.setCreatedBy(DataConstants.DEFAULT_USER);
					objects.setCreatedDateTime(DataConstants.DEFAULT_DATE);
					objects.setUpdatedBy(DataConstants.DEFAULT_USER);
					objects.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
					inputService.insertObjectsGetKey(objects);
					LOG.info("insert value of Objects::" + objects.toString());
				}
			}
		} catch (Exception e) {
			LOG.error("updateObjectGroupITAP(Map<String,String>)", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateObjectGroupITAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return update;
	}

	/**
	 * Gets the obj grp by iditap.
	 * 
	 * @param objGrpID
	 *            the obj grp id
	 * @return the obj grp by iditap
	 */
	@RequestMapping(value = "/getObjGrpByIDITAP", method = RequestMethod.POST)
	public @ResponseBody
	ObjectGroup getObjGrpByIDITAP(@RequestBody String objGrpID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjGrpByIDITAP(String) - start"); //$NON-NLS-1$
		}

		ObjectGroup objGrp = new ObjectGroup();
		LOG.info("object group " + objGrpID);
		int objGrpId = Integer.parseInt(objGrpID);
		try {
			objGrp = inputService.getObjectGroupById(objGrpId);

		} catch (ServiceException se) {
			LOG.error("getObjGrpByIDITAP(String)", se); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjGrpByIDITAP(String) - end"); //$NON-NLS-1$
		}
		return objGrp;
	}
}
