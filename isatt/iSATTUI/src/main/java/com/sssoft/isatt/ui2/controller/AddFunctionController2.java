package com.sssoft.isatt.ui2.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.sssoft.isatt.data.pojo.AppFeature;
import com.sssoft.isatt.data.pojo.AppFunctionality;
import com.sssoft.isatt.data.pojo.def.AppFunctionalUI;
import com.sssoft.isatt.data.service.MainService;
import com.sssoft.isatt.data.utils.DataConstants;

/**
 * The Class AddFunctionController2.
 */
@Controller
public class AddFunctionController2 {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AddFunctionController2.class);

	/** The main service. */
	private MainService mainService;

	/**
	 * Gets the main service.
	 * 
	 * @return the main service
	 */
	public MainService getMainService() {
		return mainService;
	}

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
	 * Adds the function itap.
	 * 
	 * @param functionData
	 *            the function data
	 * @return the int
	 */
	@RequestMapping(value = "/addFunctionITAP", method = RequestMethod.POST)
	public @ResponseBody
	int addFunctionITAP(@RequestBody Map<String, String> functionData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addFunctionITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}

		JSONObject jsonObject = new JSONObject(functionData);
		int functionId = 0;
		int featureId = 0;
		try {
			String functionName = (String) jsonObject.get("testFunction");
			String description = (String) jsonObject.get("description");
			int appIDs = (Integer) jsonObject.get("appID");
			AppFunctionality appFunctionality = new AppFunctionality();
			appFunctionality.setFunctionalName(functionName);
			appFunctionality.setDescription(description);
			appFunctionality.setAppID(appIDs);
			appFunctionality.setCreatedBy(DataConstants.DEFAULT_USER);
			appFunctionality.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			appFunctionality.setUpdatedBy(DataConstants.DEFAULT_USER);
			appFunctionality.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			LOG.info("Function Pojo String ::" + appFunctionality.toString());

			functionId = mainService.insertAppFunctionalityGetKey(appFunctionality);
			LOG.info("FunctionId ::" + functionId);

			@SuppressWarnings("unchecked")
			List<HashMap<String, String>> newfeatures = (ArrayList<HashMap<String, String>>) jsonObject.get("newfeature");

			for (HashMap<String, String> map : newfeatures) {
				for (java.util.Map.Entry<String, String> entry : map.entrySet()) {
					AppFeature appFeature = new AppFeature();
					appFeature.setFunctionalID(functionId);
					appFeature.setFeatureName(entry.getKey());
					appFeature.setDescription(entry.getValue());
					// appFeature.setAppID(appIDs);
					appFeature.setCreatedBy(DataConstants.DEFAULT_USER);
					appFeature.setCreatedDateTime(DataConstants.DEFAULT_DATE);
					appFeature.setUpdatedBy(DataConstants.DEFAULT_USER);
					appFeature.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
					featureId = mainService.insertAppFeatureGetKey(appFeature);
					LOG.info("FeatureId ::" + featureId);

				}
			}

		} catch (JSONException e) {
			LOG.error("addFunctionITAP(Map<String,String>)", e); //$NON-NLS-1$

		} catch (ServiceException e) {
			LOG.error("addFunctionITAP(Map<String,String>)", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("addFunctionITAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return functionId;
	}

	//
	/**
	 * Gets the function by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the function by app id
	 */
	@RequestMapping(value = "/getFunctionFilterByAppIdITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<AppFunctionalUI> getFunctionByAppId(@RequestBody String appId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getFunctionByAppId(String) - start"); //$NON-NLS-1$
		}
		LOG.info("Inside getFunctionByAppId method");
		int applicationId = Integer.parseInt(appId);
		List<AppFunctionality> appFunctionality = null;
		List<AppFunctionalUI> appFunctionalUIList = new ArrayList<AppFunctionalUI>();
		try {
			LOG.info("inside getFunctionByAppId at appId");
			appFunctionality = mainService.getAppFunctionalityObjByAppId(applicationId);
			LOG.info(appFunctionality.size() + "Function list ::" + appFunctionality.toString());
			for (AppFunctionality appFun : appFunctionality) {
				AppFunctionalUI appFunctionalUI = new AppFunctionalUI();
				appFunctionalUI.setAppFunctionId(appFun.getFunctionalID());
				appFunctionalUI.setAppFunctionDesc(appFun.getDescription());
				appFunctionalUI.setAppFunctionName(appFun.getFunctionalName());
				List<AppFeature> appFeatureList = mainService.getAppFeatureByFunctioanlityID(appFun.getFunctionalID());
				appFunctionalUI.setAppFeatureCount(appFeatureList.size());
				appFunctionalUIList.add(appFunctionalUI);
			}
		} catch (ServiceException se) {
			LOG.error("getFunctionByAppId(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getFunctionByAppId(String) - end"); //$NON-NLS-1$
		}
		return appFunctionalUIList;
	}

	/**
	 * Edits the function itap.
	 * 
	 * @param functionalID
	 *            the functional id
	 * @return the app functional ui
	 */
	@RequestMapping(value = "/editFunctionITAP", method = RequestMethod.POST)
	public @ResponseBody
	AppFunctionalUI editFunctionITAP(@RequestBody Map<String, Integer> functionalID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("editFunctionITAP(Map<String,Integer>) - start"); //$NON-NLS-1$
		}

		LOG.info("editFunction()");
		int functionID = 0;
		AppFunctionalUI appFunctionalUIList = new AppFunctionalUI();
		LOG.info("Function ID to edit: " + functionID);
		AppFunctionality appFunctionality = new AppFunctionality();
		try {
			JSONObject jsonObject = new JSONObject(functionalID);
			try {
				functionID = (Integer) jsonObject.get("functionalID");
			} catch (JSONException e) {
				LOG.error("editFunctionITAP(Map<String,Integer>)", e); //$NON-NLS-1$

			}

			appFunctionality = mainService.getAppFunctionalityById(functionID);
			LOG.info("Function Name:: " + appFunctionality.getFunctionalName());

			appFunctionalUIList.setAppFunctionId(appFunctionality.getFunctionalID());
			appFunctionalUIList.setAppFunctionName(appFunctionality.getFunctionalName());
			appFunctionalUIList.setAppFunctionDesc(appFunctionality.getDescription());

			List<AppFeature> appFeatureList = mainService.getAppFeatureByFunctioanlityID(functionID);
			appFunctionalUIList.setAppFeature(appFeatureList);

		} catch (ServiceException se) {
			LOG.error("editFunctionITAP(Map<String,Integer>)", se); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("editFunctionITAP(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return appFunctionalUIList;
	}

	/**
	 * Update function itap.
	 * 
	 * @param functionData
	 *            the function data
	 * @return the long
	 */
	@RequestMapping(value = "/updateFunctionITAP", method = RequestMethod.POST)
	public @ResponseBody
	long updateFunctionITAP(@RequestBody Map<String, String> functionData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateFunctionITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}

		JSONObject jsonObject = new JSONObject(functionData);
		long update = 0;
		int featureId = 0;
		try {
			String functionName = (String) jsonObject.get("testFunction");
			String description = (String) jsonObject.get("description");
			int functionalIDs = (Integer) jsonObject.get("functionalID");
			// int appIDs = (Integer) jsonObject.get("appID");
			AppFunctionality appFunctionality = new AppFunctionality();
			appFunctionality.setFunctionalName(functionName);
			appFunctionality.setDescription(description);
			appFunctionality.setFunctionalID(functionalIDs);
			appFunctionality.setUpdatedBy(DataConstants.DEFAULT_USER);
			appFunctionality.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			LOG.info("Update Function Pojo String ::" + appFunctionality.toString());

			update = mainService.updateAppFunctionalData(appFunctionality);

			@SuppressWarnings("unchecked")
			List<HashMap<String, String>> newfeatures = (ArrayList<HashMap<String, String>>) jsonObject.get("newfeature");

			for (HashMap<String, String> map : newfeatures) {
				for (java.util.Map.Entry<String, String> entry : map.entrySet()) {
					AppFeature appFeature = new AppFeature();
					appFeature.setFunctionalID(functionalIDs);
					appFeature.setFeatureName(entry.getKey());
					appFeature.setDescription(entry.getValue());
					// appFeature.setAppID(appIDs);
					appFeature.setCreatedBy(DataConstants.DEFAULT_USER);
					appFeature.setCreatedDateTime(DataConstants.DEFAULT_DATE);
					appFeature.setUpdatedBy(DataConstants.DEFAULT_USER);
					appFeature.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
					featureId = mainService.insertAppFeatureGetKey(appFeature);
					LOG.info("FeatureId ::" + featureId);

				}
			}

		} catch (JSONException e) {
			LOG.error("updateFunctionITAP(Map<String,String>)", e); //$NON-NLS-1$

		} catch (ServiceException e) {
			LOG.error("updateFunctionITAP(Map<String,String>)", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("updateFunctionITAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return update;
	}

}
