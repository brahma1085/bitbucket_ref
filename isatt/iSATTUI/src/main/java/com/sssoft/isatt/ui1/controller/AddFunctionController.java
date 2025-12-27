package com.sssoft.isatt.ui1.controller;

import java.io.BufferedReader;
import java.io.EOFException;
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

import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.AppFeature;
import com.sssoft.isatt.data.pojo.AppFunctionality;
import com.sssoft.isatt.data.service.MainService;
import com.sssoft.isatt.data.utils.DataConstants;

/**
 * The Class AddFunctionController.
 *
 * @author mohammedfirdos
 */

/**
 * This is the Server Controller which handles the Request UI form has
 * dispatched for Add Function Pop up.
 */

@Controller
public class AddFunctionController {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(AddFunctionController.class);

	/** The main service. */
	private MainService mainService;

	/**
	 * Sets the main service.
	 * 
	 * @param mainService
	 *            the mainService to set
	 */
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	/**
	 * Constructor.
	 */
	public AddFunctionController() {
	}

	/**
	 * Gets the all functions.
	 * 
	 * @param appId
	 *            the app id
	 * @return the all functions
	 * @RequestBody and @ResponseBody They are annotations of the spring mvc
	 *              framework and can be used in a controller to implement smart
	 *              object serialization and deserialization. They help you
	 *              avoid boilerplate code by extracting the logic of message
	 *              conversion and making it an aspect. Other than that they
	 *              help you support multiple formats for a single REST resource
	 *              without duplication of code. If you annotate a method with
	 * @ResponseBody, spring will try to convert its return value and write it
	 *                to the http response automatically.
	 */
	@RequestMapping(value = "/getFunctionsForApplication", method = RequestMethod.POST)
	public @ResponseBody
	List<AppFunctionality> getAllFunctions(@RequestBody Map<String, Integer> appId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllFunctions(Map<String,Integer>) - start"); //$NON-NLS-1$
		}

		int applicationId = 0;
		List<AppFunctionality> appFunctions = new ArrayList<AppFunctionality>();
		try {
			JSONObject jsonObject = new JSONObject(appId);
			try {
				applicationId = (Integer) jsonObject.get("appID");
			} catch (JSONException e) {
				LOG.warn("getAllFunctions(Map<String,Integer>) - exception ignored", e); //$NON-NLS-1$
			}
			appFunctions = mainService.getAppFunctionalityObjByAppId(applicationId);
		} catch (ServiceException e) {
			LOG.warn("getAllFunctions(Map<String,Integer>) - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllFunctions(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return appFunctions;
	}

	/**
	 * Adds the function controller.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @RequestBody and @ResponseBody They are annotations of the spring mvc
	 *              framework and can be used in a controller to implement smart
	 *              object serialization and deserialization. They help you
	 *              avoid boilerplate code by extracting the logic of message
	 *              conversion and making it an aspect. Other than that they
	 *              help you support multiple formats for a single REST resource
	 *              without duplication of code. If you annotate a method with
	 * @ResponseBody, spring will try to convert its return value and write it
	 *                to the http response automatically.
	 */
	@RequestMapping(value = "/addFunction", method = RequestMethod.POST)
	public void addFunctionController(HttpServletRequest request, HttpServletResponse response) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addFunctionController(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		String line = null;
		StringBuffer bufferData = new StringBuffer();
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				bufferData.append(line);
			}
			AppFunctionality appFunctionality = convertJsonFormDataToPojo(bufferData);
			java.util.Date date = new java.util.Date();
			java.sql.Date currentDate = new java.sql.Date(date.getTime());
			appFunctionality.setCreatedBy("Firdos");
			appFunctionality.setCreatedDateTime(currentDate);
			appFunctionality.setUpdatedBy("Firdos");
			appFunctionality.setUpdatedDateTime(currentDate);
			mainService.insertAppFunctionalityGetKey(appFunctionality);
		} catch (Exception e) {
			LOG.warn("addFunctionController(HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addFunctionController(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Edits the function.
	 * 
	 * @param functionalID
	 *            the functional id
	 * @return the app functionality
	 * @RequestBody and @ResponseBody They are annotations of the spring mvc
	 *              framework and can be used in a controller to implement smart
	 *              object serialization and deserialization. They help you
	 *              avoid boilerplate code by extracting the logic of message
	 *              conversion and making it an aspect. Other than that they
	 *              help you support multiple formats for a single REST resource
	 *              without duplication of code. If you annotate a method with
	 * @ResponseBody, spring will try to convert its return value and write it
	 *                to the http response automatically.
	 */
	@RequestMapping(value = "/editFunction", method = RequestMethod.POST)
	public @ResponseBody
	AppFunctionality editFunction(@RequestBody Map<String, Integer> functionalID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("editFunction(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		int functionID = 0;
		AppFunctionality appFunctionality = new AppFunctionality();
		try {
			JSONObject jsonObject = new JSONObject(functionalID);
			functionID = (Integer) jsonObject.get("functionalID");
			appFunctionality = mainService.getAppFunctionalityById(functionID);
		} catch (Exception se) {
			LOG.warn("editFunction(Map<String,Integer>) - exception ignored", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("editFunction(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return appFunctionality;
	}

	/**
	 * Update function.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @RequestBody and @ResponseBody They are annotations of the spring mvc
	 *              framework and can be used in a controller to implement smart
	 *              object serialization and deserialization. They help you
	 *              avoid boilerplate code by extracting the logic of message
	 *              conversion and making it an aspect. Other than that they
	 *              help you support multiple formats for a single REST resource
	 *              without duplication of code. If you annotate a method with
	 * @ResponseBody, spring will try to convert its return value and write it
	 *                to the http response automatically.
	 */
	@RequestMapping(value = "/updateFunction", method = RequestMethod.POST)
	public void updateFunction(HttpServletRequest request, HttpServletResponse response) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateFunction(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		String line = null;
		StringBuffer bufferData = new StringBuffer();
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				bufferData.append(line);
			}
			AppFunctionality appFunctionality = convertJsonFormDataToPojo(bufferData);
			java.util.Date date = new java.util.Date();
			java.sql.Date currentDate = new java.sql.Date(date.getTime());
			appFunctionality.setCreatedBy("Firdos");
			appFunctionality.setCreatedDateTime(currentDate);
			appFunctionality.setUpdatedBy("Firdos");
			appFunctionality.setUpdatedDateTime(currentDate);
			mainService.updateAppFunctionalData(appFunctionality);
		} catch (Exception e) {
			LOG.warn("updateFunction(HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateFunction(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the all app features by functional id.
	 * 
	 * @param functionalID
	 *            the functional id
	 * @return the all app features by functional id
	 * @RequestBody and @ResponseBody They are annotations of the spring mvc
	 *              framework and can be used in a controller to implement smart
	 *              object serialization and deserialization. They help you
	 *              avoid boilerplate code by extracting the logic of message
	 *              conversion and making it an aspect. Other than that they
	 *              help you support multiple formats for a single REST resource
	 *              without duplication of code. If you annotate a method with
	 * @ResponseBody, spring will try to convert its return value and write it
	 *                to the http response automatically.
	 */
	@RequestMapping(value = "/getAllAppFeaturesByFunctionalId", method = RequestMethod.POST)
	public @ResponseBody
	List<AppFeature> getAllAppFeaturesByFunctionalId(@RequestBody Map<String, Integer> functionalID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllAppFeaturesByFunctionalId(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		int functionalId = 0;
		List<AppFeature> appFeature = new ArrayList<AppFeature>();
		try {
			JSONObject jsonObj = new JSONObject(functionalID);
			functionalId = (Integer) jsonObj.get("functionalID");
			appFeature = mainService.getAllAppFeaturesByFunctionalId(functionalId);
		} catch (Exception se) {
			LOG.warn("getAllAppFeaturesByFunctionalId(Map<String,Integer>) - exception ignored", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllAppFeaturesByFunctionalId(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return appFeature;
	}

	/**
	 * This method will convert json form data values to pojo.
	 * 
	 * @param bufferData
	 *            the buffer data
	 * @return converted form data values from json to pojo
	 * @throws EOFException
	 *             the eOF exception
	 */
	public AppFunctionality convertJsonFormDataToPojo(StringBuffer bufferData) {
		AppFunctionality appFunction = new AppFunctionality();
		ObjectMapper mapper = new ObjectMapper();
		try {
			appFunction = mapper.readValue(bufferData.toString(), AppFunctionality.class);
		} catch (Exception e) {
			LOG.warn("convertJsonFormDataToPojo(StringBuffer) - exception ignored", e); //$NON-NLS-1$
		}
		return appFunction;
	}

	/**
	 * Adds the app feature.
	 * 
	 * @param featureData
	 *            the feature data
	 * @return the long
	 */
	@RequestMapping(value = "/addAppFeature", method = RequestMethod.POST)
	public @ResponseBody
	long addAppFeature(@RequestBody Map<String, String> featureData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addAppFeature(Map<String,String>) - start"); //$NON-NLS-1$
		}
		AppFeature appFeature = new AppFeature();
		String functionalId = "";
		String featureName = "";
		String description = "";
		long i = 0;
		try {
			JSONObject jsonObj = new JSONObject(featureData);
			functionalId = (String) jsonObj.get("functionalID");
			int id = Integer.parseInt(functionalId);
			featureName = (String) jsonObj.get("featureName");
			description = (String) jsonObj.get("description");
			appFeature.setFeatureName(featureName);
			appFeature.setDescription(description);
			appFeature.setFunctionalID(id);
			appFeature.setCreatedBy(DataConstants.DEFAULT_USER);
			appFeature.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			appFeature.setUpdatedBy(DataConstants.DEFAULT_USER);
			appFeature.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			i = mainService.insertAppFeatureGetKey(appFeature);
		} catch (Exception e) {
			LOG.warn("addAppFeature(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addAppFeature(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return i;
	}

	/**
	 * Edits the app feature.
	 * 
	 * @param featureData
	 *            the feature data
	 * @return the app feature
	 */
	@RequestMapping(value = "/editAppFeature", method = RequestMethod.POST)
	public @ResponseBody
	AppFeature editAppFeature(@RequestBody Map<String, Integer> featureData) {
		AppFeature appFeature = new AppFeature();
		int featureID = 0;
		try {
			JSONObject jsonObject = new JSONObject(featureData);
			featureID = (Integer) jsonObject.get("featureID");
			appFeature = mainService.getAppFeatureById(featureID);
		} catch (Exception se) {
			LOG.warn("editAppFeature(Map<String,Integer>) - exception ignored", se); //$NON-NLS-1$
		}
		return appFeature;
	}

	/**
	 * Update app feature.
	 * 
	 * @param appFeature
	 *            the app feature
	 * @return the long
	 */
	@RequestMapping(value = "/updateAppFeature", method = RequestMethod.POST)
	public @ResponseBody
	long updateAppFeature(@RequestBody Map<String, String> appFeature) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateAppFeature(Map<String,String>) - start"); //$NON-NLS-1$
		}
		AppFeature feature = new AppFeature();
		int featureId = 0;
		long i = 0;
		String featureName = "", description = "", functionId = "";
		try {
			JSONObject jsonObj = new JSONObject(appFeature);
			featureName = (String) jsonObj.get("featureName");
			description = (String) jsonObj.getString("description");
			featureId = (Integer) jsonObj.get("featureID");
			functionId = (String) jsonObj.get("functionalID");
			int id = Integer.parseInt(functionId);
			feature.setFeatureName(featureName);
			feature.setDescription(description);
			feature.setFeatureID(featureId);
			feature.setFunctionalID(id);
			feature.setUpdatedBy(DataConstants.DEFAULT_USER);
			feature.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			i = mainService.updateAppFeatureData(feature);
		} catch (Exception e) {
			LOG.warn("updateAppFeature(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateAppFeature(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return i;
	}

}