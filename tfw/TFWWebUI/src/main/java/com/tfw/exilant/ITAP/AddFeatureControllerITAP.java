package com.tfw.exilant.ITAP;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.AppFeature;
import com.exilant.tfw.pojo.Screen;
import com.exilant.tfw.pojo.def.AppFeatureUI;

import com.exilant.tfw.service.MainService;
import com.exilant.tfw.utils.DataConstants;

/**
 * The Class AddFeatureControllerITAP.
 */
@Controller
public class AddFeatureControllerITAP {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AddFeatureControllerITAP.class);

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
	 * Gets the feature filter by functional id itap.
	 * 
	 * @param functionalID
	 *            the functional id
	 * @return the feature filter by functional id itap
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/getFeatureFilterByFunctionalIdITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<AppFeatureUI> getFeatureFilterByFunctionalIdITAP(@RequestBody String functionalID) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getFeatureFilterByFunctionalIdITAP(String) - start"); //$NON-NLS-1$
		}

		LOG.info(" Functional Id " + functionalID);

		int funId = Integer.parseInt(functionalID);
		List<AppFeature> appFeaturesList = new ArrayList<AppFeature>();
		List<AppFeatureUI> appFeatureUIs = new ArrayList<AppFeatureUI>();
		try {
			LOG.info("inside getTestSuiteFilterByAppId at appId");
			appFeaturesList = mainService.getAllAppFeaturesByFunctionalId(funId);
			LOG.info("FeatureList list ::" + appFeaturesList.toString());
			for (AppFeature appFea : appFeaturesList) {
				AppFeatureUI appFeatureUI = new AppFeatureUI();
				appFeatureUI.setAppFeatureId(appFea.getFeatureID());
				appFeatureUI.setAppFeatureDesc(appFea.getDescription());
				appFeatureUI.setAppFeatureName(appFea.getFeatureName());
				LOG.info(" App ID ---" + appFea.getAppID());
				List<Screen> screenList = mainService.getScreensFeatureID(appFea.getFeatureID());
				appFeatureUI.setAppscreensCount(screenList.size());
				appFeatureUIs.add(appFeatureUI);
			}
		} catch (ServiceException se) {
			LOG.error("getFeatureFilterByFunctionalIdITAP(String)", se); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getFeatureFilterByFunctionalIdITAP(String) - end"); //$NON-NLS-1$
		}
		return appFeatureUIs;

	}

	/**
	 * Adds the feature itap.
	 * 
	 * @param featureData
	 *            the feature data
	 * @return the int
	 */
	@RequestMapping(value = "/addFeatureITAP", method = RequestMethod.POST)
	public @ResponseBody
	int addFeatureITAP(@RequestBody Map<String, String> featureData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addFeatureITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}

		JSONObject jsonObject = new JSONObject(featureData);
		int featureId = 0;

		try {
			String featureName = (String) jsonObject.get("testFeature");
			String description = (String) jsonObject.get("description");
			int appIDs = (Integer) jsonObject.get("appID");
			int functionId = (Integer) jsonObject.get("functionId");
			AppFeature appFeature = new AppFeature();
			appFeature.setFeatureName(featureName);
			appFeature.setDescription(description);
			appFeature.setAppID(appIDs);
			appFeature.setFunctionalID(functionId);
			appFeature.setCreatedBy(DataConstants.DEFAULT_USER);
			appFeature.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			appFeature.setUpdatedBy(DataConstants.DEFAULT_USER);
			appFeature.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			LOG.info("Feature Pojo String ::" + appFeature.toString());

			featureId = mainService.insertAppFeatureGetKey(appFeature);
			LOG.info("FeatureId ::" + featureId);
			List<HashMap<String, String>> screenVals = (ArrayList<HashMap<String, String>>) jsonObject.get("screenArr");
			if (screenVals.size() > 0) {
				List<Integer> screenIds = new ArrayList<Integer>();
				for (HashMap<String, String> map : screenVals) {
					Screen screen = new Screen();
					String screenName = null;
					for (java.util.Map.Entry<String, String> entry : map.entrySet()) {
						screenName = entry.getKey();
						description = entry.getValue();
					}
					screen.setScreenName(screenName);
					screen.setDescription(description);
					screen.setAppID(appIDs);
					screen.setFeatureID(featureId);
					screen.setCreatedBy(DataConstants.DEFAULT_USER);
					screen.setCreatedDateTime(DataConstants.DEFAULT_DATE);
					screen.setUpdatedBy(DataConstants.DEFAULT_USER);
					screen.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
					int screenId = mainService.insertScreenGetKey(screen);
					screenIds.add(screenId);

				}
				LOG.info("screens  " + screenIds.size());

			}
		} catch (Exception e) {
			LOG.error("addFeatureITAP(Map<String,String>)", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("addFeatureITAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return featureId;
	}

	/**
	 * Edits the feature itap.
	 * 
	 * @param featureID
	 *            the feature id
	 * @return the app feature ui
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/editFeatureITAP", method = RequestMethod.POST)
	public @ResponseBody
	AppFeatureUI editFeatureITAP(@RequestBody String featureID) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("editFeatureITAP(String) - start"); //$NON-NLS-1$
		}

		LOG.info(" featureIDID Id " + featureID);

		int featureId = Integer.parseInt(featureID);
		AppFeature appFea = new AppFeature();
		AppFeatureUI appFeatureUI = new AppFeatureUI();
		try {
			LOG.info("inside editFeatureITAP at appId");
			appFea = mainService.getAppFeatureById(featureId);
			appFeatureUI.setAppFeatureId(appFea.getFeatureID());
			appFeatureUI.setAppFeatureDesc(appFea.getDescription());
			appFeatureUI.setAppFeatureName(appFea.getFeatureName());
			List<Screen> screenList = mainService.getScreensFeatureID(appFea.getFeatureID());
			appFeatureUI.setScreen(screenList);

		} catch (ServiceException se) {
			LOG.error("editFeatureITAP(String)", se); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("editFeatureITAP(String) - end"); //$NON-NLS-1$
		}
		return appFeatureUI;

	}

	/**
	 * Update feature itap.
	 * 
	 * @param featureData
	 *            the feature data
	 * @return the int
	 */
	@RequestMapping(value = "/updateFeatureITAP", method = RequestMethod.POST)
	public @ResponseBody
	int updateFeatureITAP(@RequestBody Map<String, String> featureData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateFeatureITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}

		JSONObject jsonObject = new JSONObject(featureData);
		int featureId = 0;

		try {
			String featureName = (String) jsonObject.get("testFeatureName");
			String description = (String) jsonObject.get("description");
			int appIDs = (Integer) jsonObject.get("appID");
			int functionId = (Integer) jsonObject.get("functionId");
			featureId = (Integer) jsonObject.get("testFeatureID");
			AppFeature appFeature = new AppFeature();
			appFeature.setFeatureName(featureName);
			appFeature.setDescription(description);
			appFeature.setFunctionalID(functionId);
			appFeature.setAppID(appIDs);
			appFeature.setFeatureID(featureId);
			appFeature.setUpdatedBy(DataConstants.DEFAULT_USER);
			appFeature.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			LOG.info("Feature Pojo String ::" + appFeature.toString());

			mainService.updateAppFeatureData(appFeature);
			LOG.info("FeatureId ::" + featureId);
			List<HashMap<String, String>> screenVals = (ArrayList<HashMap<String, String>>) jsonObject.get("newScreenArr");
			if (screenVals.size() > 0) {
				List<Integer> screenIds = new ArrayList<Integer>();
				for (HashMap<String, String> map : screenVals) {
					Screen screen = new Screen();
					String screenName = null;
					for (java.util.Map.Entry<String, String> entry : map.entrySet()) {
						screenName = entry.getKey();
						description = entry.getValue();
					}
					screen.setScreenName(screenName);
					screen.setDescription(description);
					screen.setAppID(appIDs);
					screen.setFeatureID(featureId);
					screen.setCreatedBy(DataConstants.DEFAULT_USER);
					screen.setCreatedDateTime(DataConstants.DEFAULT_DATE);
					screen.setUpdatedBy(DataConstants.DEFAULT_USER);
					screen.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
					int screenId = mainService.insertScreenGetKey(screen);
					screenIds.add(screenId);

				}
				LOG.info("screens  " + screenIds.size());

			}
		} catch (Exception e) {
			LOG.error("updateFeatureITAP(Map<String,String>)", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("updateFeatureITAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return featureId;
	}
}
