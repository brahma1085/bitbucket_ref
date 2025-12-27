package com.tfw.exilant;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.Actions;
import com.exilant.tfw.pojo.AppFeature;
import com.exilant.tfw.pojo.Screen;
import com.exilant.tfw.pojo.input.IdentifierType;
import com.exilant.tfw.pojo.input.ObjectGroup;
import com.exilant.tfw.pojo.input.ObjectType;
import com.exilant.tfw.pojo.input.Objects;
import com.exilant.tfw.service.InputService;
import com.exilant.tfw.service.MainService;
import com.exilant.tfw.utils.DataConstants;

/**
 * The Class ObjectController.
 * 
 * @author ramyaramesh
 */
@Controller
public class ObjectController {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(ObjectController.class);

	/**
	 * Instantiates a new object controller.
	 */
	public ObjectController() {
	}

	/** The input service. */
	private InputService inputService;

	/** The main service. */
	private MainService mainService;

	/**
	 * Sets the main service.
	 * 
	 * @param mainService
	 *            the new main service
	 */
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
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
	 * Gets the screens.
	 * 
	 * @param appId
	 *            the app id
	 * @return the screens
	 */
	@RequestMapping(value = "/getScreensForApplication", method = RequestMethod.POST)
	public @ResponseBody
	List<Screen> getScreens(@RequestBody Map<String, Integer> appId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreens(Map<String,Integer>) - start"); //$NON-NLS-1$
		}

		List<Screen> screens = new ArrayList<Screen>();
		int applicationId = 0;
		try {
			JSONObject jsonObj = new JSONObject(appId);
			try {
				applicationId = (Integer) jsonObj.get("appID");
			} catch (JSONException e) {
				LOG.warn("getScreens(Map<String,Integer>) - exception ignored", e); //$NON-NLS-1$
			}
			screens = mainService.getScreensByAppId(applicationId);
		} catch (ServiceException se) {
			LOG.warn("getScreens(Map<String,Integer>) - exception ignored", se); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreens(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return screens;
	}

	/**
	 * Adds the object group controller.
	 * 
	 * @param objectGrp
	 *            the object grp
	 * @return the int
	 */
	@RequestMapping(value = "/addObjGrp", method = RequestMethod.POST)
	public @ResponseBody
	int addObjectGroupController(@RequestBody Map<String, String> objectGrp) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addObjectGroupController(Map<String,String>) - start"); //$NON-NLS-1$
		}
		ObjectGroup objGrp = new ObjectGroup();
		int objGrpId = 0;
		try {
			JSONObject jsonObj = new JSONObject(objectGrp);
			String scrId = (String) jsonObj.get("screenID");
			objGrp.setScreenID(Integer.parseInt(scrId));
			objGrp.setObjectGroupName((String) jsonObj.get("objGrpName"));
			objGrp.setDescription((String) jsonObj.get("objGrpDesc"));
			objGrp.setAppID((Integer) jsonObj.get("appID"));
			objGrp.setCreatedBy(DataConstants.DEFAULT_USER);
			objGrp.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			objGrp.setUpdatedBy(DataConstants.DEFAULT_USER);
			objGrp.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			objGrpId = inputService.insertObjectGroupGetKey(objGrp);
		} catch (Exception e) {
			LOG.warn("addObjectGroupController(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addObjectGroupController(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return objGrpId;
	}

	/**
	 * Adds the action controller.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the int
	 */
	@RequestMapping(value = "/addAction", method = RequestMethod.POST)
	public @ResponseBody
	int addActionController(HttpServletRequest request, HttpServletResponse response) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addActionController(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		StringBuffer jb = new StringBuffer();
		String line = null;
		int i = 0;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			Actions actions = getFormData(jb);
			actions.setCreatedBy("System");
			actions.setCreatedDateTime(new Date(0));
			actions.setUpdatedBy("System");
			actions.setUpdatedDateTime(new Date(0));
			i = mainService.insertActionsGetKey(actions);
		} catch (Exception e) {
			LOG.warn("addActionController(HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addActionController(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
		return i;
	}

	/**
	 * Gets the form data.
	 * 
	 * @param jb
	 *            the jb
	 * @return the form data
	 */
	public Actions getFormData(StringBuffer jb) {
		Actions acts = new Actions();
		ObjectMapper mapper = new ObjectMapper();
		try {
			acts = mapper.readValue(jb.toString(), Actions.class);
		} catch (IOException e) {
			LOG.warn("getFormData(StringBuffer) - exception ignored", e);
		}
		return acts;
	}

	/**
	 * Gets the actions.
	 * 
	 * @return the actions
	 */
	@RequestMapping(value = "/getActions", method = RequestMethod.GET)
	public @ResponseBody
	List<Actions> getActions() {
		List<Actions> acts = new ArrayList<Actions>();
		try {
			acts = mainService.getAllActions();
		} catch (ServiceException se) {
			LOG.warn("getActions() - exception ignored", se);
		}
		return acts;
	}

	/**
	 * Adds the object type controller.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the int
	 */
	@RequestMapping(value = "/addObjType", method = RequestMethod.POST)
	public @ResponseBody
	int addObjectTypeController(HttpServletRequest request, HttpServletResponse response) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addObjectTypeController(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		StringBuffer jb = new StringBuffer();
		String line = null;
		int i = 0;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			ObjectType objType = getObjTypeFormData(jb);
			objType.setCreatedBy("System");
			objType.setCreatedDateTime(new Date(0));
			objType.setUpdatedBy("System");
			objType.setUpdatedDateTime(new Date(0));
			i = inputService.insertObjectTypeGetKey(objType);
		} catch (Exception e) {
			LOG.warn("addObjectTypeController(HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addObjectTypeController(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
		return i;
	}

	/**
	 * Gets the obj type form data.
	 * 
	 * @param jb
	 *            the jb
	 * @return the obj type form data
	 */
	public ObjectType getObjTypeFormData(StringBuffer jb) {
		ObjectType objTypes = new ObjectType();
		ObjectMapper mapper = new ObjectMapper();
		try {
			objTypes = mapper.readValue(jb.toString(), ObjectType.class);
		} catch (IOException e) {
			LOG.warn("getObjTypeFormData(StringBuffer) - exception ignored", e); //$NON-NLS-1$
		}
		return objTypes;
	}

	/**
	 * Gets the obj grps.
	 * 
	 * @param screenID
	 *            the screen id
	 * @return the obj grps
	 */
	@RequestMapping(value = "/getObjGrpsForScreen", method = RequestMethod.POST)
	public @ResponseBody
	List<ObjectGroup> getObjGrps(@RequestBody Map<String, Integer> screenID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjGrps(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		List<ObjectGroup> objGrps = new ArrayList<ObjectGroup>();
		int scrId = 0;
		try {
			JSONObject jsonObj = new JSONObject(screenID);
			scrId = (Integer) jsonObj.get("screenID");
			objGrps = inputService.getObjGrpsByScreenID(scrId);
		} catch (Exception se) {
			LOG.warn("getObjGrps(Map<String,Integer>) - exception ignored", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjGrps(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return objGrps;
	}

	/**
	 * Gets the obj grps for app.
	 * 
	 * @param appID
	 *            the app id
	 * @return the obj grps for app
	 */
	@RequestMapping(value = "/getObjGrpsForApplication", method = RequestMethod.POST)
	public @ResponseBody
	List<ObjectGroup> getObjGrpsForApp(@RequestBody Map<String, Integer> appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjGrpsForApp(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		List<ObjectGroup> objGrps = new ArrayList<ObjectGroup>();
		int appId = 0;
		try {
			JSONObject jsonObj = new JSONObject(appID);
			appId = (Integer) jsonObj.get("appID");
			objGrps = inputService.getObjGrpsByAppID(appId);
		} catch (Exception se) {
			LOG.warn("getObjGrpsForApp(Map<String,Integer>) - exception ignored", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjGrpsForApp(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return objGrps;
	}

	/**
	 * Gets the obj types.
	 * 
	 * @return the obj types
	 */
	@RequestMapping(value = "/getObjTypes", method = RequestMethod.GET)
	public @ResponseBody
	List<ObjectType> getObjTypes() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjTypes() - start"); //$NON-NLS-1$
		}
		List<ObjectType> objTypes = new ArrayList<ObjectType>();
		try {
			objTypes = inputService.getAllObjTypes();
		} catch (ServiceException se) {
			LOG.warn("getObjTypes() - exception ignored", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjTypes() - end"); //$NON-NLS-1$
		}
		return objTypes;
	}

	/**
	 * Gets the identifier types.
	 * 
	 * @return the identifier types
	 */
	@RequestMapping(value = "/getIdentifierTypes", method = RequestMethod.GET)
	public @ResponseBody
	List<IdentifierType> getIdentifierTypes() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierTypes() - start"); //$NON-NLS-1$
		}
		List<IdentifierType> identifierTypes = new ArrayList<IdentifierType>();
		try {
			identifierTypes = inputService.getAllIdentifierTypes();
		} catch (ServiceException se) {
			LOG.warn("getIdentifierTypes() - exception ignored", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierTypes() - end"); //$NON-NLS-1$
		}
		return identifierTypes;
	}

	/**
	 * Adds the object details controller.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the int
	 */
	@RequestMapping(value = "/addObjectDetails", method = RequestMethod.POST)
	public @ResponseBody
	int addObjectDetailsController(HttpServletRequest request, HttpServletResponse response) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addObjectDetailsController(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		StringBuffer jb = new StringBuffer();
		String line = null;
		int i = 0;
		Objects objs = new Objects();
		ObjectMapper mapper = new ObjectMapper();
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			objs = mapper.readValue(jb.toString(), Objects.class);
			objs.setAppID(1);
			objs.setCreatedBy("System");
			objs.setCreatedDateTime(new Date(0));
			objs.setUpdatedBy("System");
			objs.setUpdatedDateTime(new Date(0));
			i = inputService.insertObjectsGetKey(objs);
		} catch (Exception e) {
			LOG.warn("addObjectDetailsController(HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addObjectDetailsController(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
		return i;
	}

	/**
	 * Edits the object group.
	 * 
	 * @param objGrpId
	 *            the obj grp id
	 * @return the object group
	 */
	@RequestMapping(value = "/editObjGrp", method = RequestMethod.POST)
	public @ResponseBody
	ObjectGroup editObjectGroup(@RequestBody Map<String, Integer> objGrpId) {
		int id = (Integer) objGrpId.get("objectGroupID");
		ObjectGroup objGroup = new ObjectGroup();
		try {
			objGroup = inputService.getObjectGroupById(id);
		} catch (ServiceException se) {
			LOG.warn("editObjectGroup(Map<String,Integer>) - exception ignored", se); //$NON-NLS-1$
		}
		return objGroup;
	}

	/**
	 * Update obj grp.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the long
	 */
	@RequestMapping(value = "/updateObjGrp", method = RequestMethod.POST)
	public @ResponseBody
	long updateObjGrp(HttpServletRequest request, HttpServletResponse response) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateObjGrp(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		StringBuffer jb = new StringBuffer();
		String line = null;
		long i = 0;
		ObjectGroup objGrp = new ObjectGroup();
		ObjectMapper mapper = new ObjectMapper();
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			objGrp = mapper.readValue(jb.toString(), ObjectGroup.class);
			objGrp.setUpdatedBy("system");
			objGrp.setUpdatedDateTime(new Date(0));
			i = inputService.updateObjectGroupData(objGrp);
		} catch (Exception e) {
			LOG.warn("updateObjGrp(HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateObjGrp(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
		return i;
	}

	/**
	 * Edits the screen details.
	 * 
	 * @param screenId
	 *            the screen id
	 * @return the screen
	 */
	@RequestMapping(value = "/editScreenDetails", method = RequestMethod.POST)
	public @ResponseBody
	Screen editScreenDetails(@RequestBody Map<String, Integer> screenId) {
		int id = (Integer) screenId.get("screenID");
		Screen screen = new Screen();
		try {
			screen = mainService.getScreenById(id);
		} catch (ServiceException se) {
			LOG.warn("editScreenDetails(Map<String,Integer>) - exception ignored", se); //$NON-NLS-1$
		}
		return screen;
	}

	/**
	 * Gets the appd features.
	 * 
	 * @return the appd features
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/getFeatures", method = RequestMethod.GET)
	public @ResponseBody
	List<AppFeature> getAppdFeatures() throws ServiceException {
		return mainService.getAppFeature();
	}

	/**
	 * Update screen details.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/updateScreenDetails", method = RequestMethod.POST)
	public @ResponseBody
	long updateScreenDetails(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateScreenDetails(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		StringBuffer jb = new StringBuffer();
		String line = null;
		long i = 0;
		Screen screen = new Screen();
		ObjectMapper mapper = new ObjectMapper();
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			screen = mapper.readValue(jb.toString(), Screen.class);
			screen.setUpdatedBy("system");
			screen.setUpdatedDateTime(new Date(0));
			i = mainService.updateScreenData(screen);
		} catch (Exception e) {
			LOG.warn("updateScreenDetails(HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateScreenDetails(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
		return i;
	}

	/**
	 * Adds the new screen.
	 * 
	 * @param screenData
	 *            the screen data
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/addNewScreen", method = RequestMethod.POST)
	public @ResponseBody
	long addNewScreen(@RequestBody Map<String, String> screenData) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addNewScreen(Map<String,String>) - start"); //$NON-NLS-1$
		}
		long i = 0;
		int appId = 0;
		String featureId = "", screenName = "", description = "";
		Screen screen = new Screen();
		try {
			JSONObject jsonObj = new JSONObject(screenData);
			featureId = (String) jsonObj.get("featureID");
			screenName = (String) jsonObj.get("screenName");
			description = (String) jsonObj.get("description");
			appId = (Integer) jsonObj.get("appID");
			int id = Integer.parseInt(featureId);
			java.util.Date date = new java.util.Date();
			java.sql.Date currentDate = new java.sql.Date(date.getTime());
			screen.setScreenName(screenName);
			screen.setDescription(description);
			screen.setFeatureID(id);
			screen.setAppID(appId);
			screen.setCreatedBy("system");
			screen.setCreatedDateTime(currentDate);
			screen.setUpdatedBy("system");
			screen.setUpdatedDateTime(currentDate);
			i = mainService.insertScreenGetKey(screen);
		} catch (Exception e) {
			LOG.warn("addNewScreen(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addNewScreen(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return i;
	}

	/**
	 * Gets the objects for object grp.
	 * 
	 * @param objGrpId
	 *            the obj grp id
	 * @return the objects for object grp
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/getObjectsForObjectGrp", method = RequestMethod.POST)
	public @ResponseBody
	List<Objects> getObjectsForObjectGrp(@RequestBody Map<String, Integer> objGrpId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectsForObjectGrp(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		List<Objects> objs = new ArrayList<Objects>();
		int objGrpID = 0;
		try {
			JSONObject jsonObj = new JSONObject(objGrpId);
			objGrpID = (Integer) jsonObj.get("objectGroupID");
			objs = inputService.getObjectsForObjGrp(objGrpID);
		} catch (Exception e) {
			LOG.warn("getObjectsForObjectGrp(Map<String,Integer>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectsForObjectGrp(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return objs;
	}

	/**
	 * Gets the identifiers for application.
	 * 
	 * @param appId
	 *            the app id
	 * @return the identifiers for application
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/getIdentifiersForApplication", method = RequestMethod.POST)
	public @ResponseBody
	List<IdentifierType> getIdentifiersForApplication(@RequestBody Map<String, Integer> appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifiersForApplication(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		List<IdentifierType> idTypes = new ArrayList<IdentifierType>();
		int appID = 0;
		try {
			JSONObject jsonObj = new JSONObject(appId);
			appID = (Integer) jsonObj.get("appID");
			idTypes = inputService.getIdentifierTypeByAppID(appID);
		} catch (Exception e) {
			LOG.warn("getIdentifiersForApplication(Map<String,Integer>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifiersForApplication(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return idTypes;
	}

	/**
	 * Edits the object.
	 * 
	 * @param objId
	 *            the obj id
	 * @return the objects
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/editObject", method = RequestMethod.POST)
	public @ResponseBody
	Objects editObject(@RequestBody Map<String, Integer> objId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("editObject(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		Objects obj = new Objects();
		int objID = 0;
		try {
			JSONObject jsonObj = new JSONObject(objId);
			objID = (Integer) jsonObj.get("objectID");
			obj = inputService.getObjectsById(objID);
		} catch (Exception e) {
			LOG.warn("editObject(Map<String,Integer>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("editObject(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return obj;
	}

	/**
	 * Update object.
	 * 
	 * @param objId
	 *            the obj id
	 * @return the objects
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/updateObject", method = RequestMethod.POST)
	public @ResponseBody
	Objects updateObject(@RequestBody Map<String, String> objId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateObject(Map<String,String>) - start"); //$NON-NLS-1$
		}
		Objects obj = new Objects();
		int objID = 0, objGrpId = 0, objTypeId = 0, idType = 0;
		String objName = "", description = "";
		try {
			JSONObject jsonObj = new JSONObject(objId);
			objID = (Integer) jsonObj.get("objectID");
			objGrpId = (Integer) jsonObj.get("objectID");
			objTypeId = (Integer) jsonObj.get("objectID");
			idType = (Integer) jsonObj.get("objectID");
			objName = (String) jsonObj.get("objectName");
			description = (String) jsonObj.get("description");
			obj.setObjectID(objID);
			obj.setObjectName(objName);
			obj.setObjectGroupID(objGrpId);
			obj.setObjectTypeID(objTypeId);
			obj.setIdentifierTypeID(idType);
			obj.setDescription(description);
			obj.setUpdatedBy(DataConstants.DEFAULT_USER);
			obj.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			inputService.updateObjectsDetails(obj);
			obj = inputService.getObjectsById(objID);
		} catch (Exception e) {
			LOG.warn("updateObject(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateObject(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return obj;
	}

}