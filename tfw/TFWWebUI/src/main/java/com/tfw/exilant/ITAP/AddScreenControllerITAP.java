package com.tfw.exilant.ITAP;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minidev.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.Screen;
import com.exilant.tfw.service.MainService;
import com.exilant.tfw.utils.DataConstants;

/**
 * The Class AddScreenControllerITAP.
 */
@Controller
public class AddScreenControllerITAP {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AddScreenControllerITAP.class);

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
	 * Adds the screen itap.
	 * 
	 * @param testScreenData
	 *            the test screen data
	 * @return the long
	 */
	@RequestMapping(value = "/addScreenITAP", method = RequestMethod.POST)
	public @ResponseBody
	long addScreenITAP(@RequestBody Map<String, String> testScreenData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addScreenITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}
		LOG.info("Entering addScreenCases controller !!!" + testScreenData);
		long screenID = 0;
		try {
			JSONObject jsonObject = new JSONObject(testScreenData);
			String screenName = (String) jsonObject.get("screenName");
			String description = (String) jsonObject.get("description");
			int appIDs = (Integer) jsonObject.get("appID");
			int featureIDs = (Integer) jsonObject.get("featureID");
			Screen screen = new Screen();
			screen.setScreenName(screenName);
			screen.setDescription(description);
			screen.setAppID(appIDs);
			screen.setFeatureID(featureIDs);
			screen.setCreatedBy(DataConstants.DEFAULT_USER);
			screen.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			screen.setUpdatedBy(DataConstants.DEFAULT_USER);
			screen.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			screenID = mainService.insertScreenGetKey(screen);
		} catch (ServiceException se) {
			LOG.error("addScreenITAP(Map<String,String>)", se); //$NON-NLS-1$
			LOG.info(se.getMessage());
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addScreenITAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return screenID;

	}

	/**
	 * Gets the screen filter by feature id and app iditap.
	 * 
	 * @param testScreenData
	 *            the test screen data
	 * @return the screen filter by feature id and app iditap
	 */
	@RequestMapping(value = "/getScreenFilterByFeatureIdAndAppIDITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<Screen> getScreenFilterByFeatureIdAndAppIDITAP(@RequestBody Map<String, String> testScreenData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreenFilterByFeatureIdAndAppIDITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}
		List<Screen> screenList = new ArrayList<Screen>();
		try {
			JSONObject jsonObject = new JSONObject(testScreenData);
			int appIDs = (Integer) jsonObject.get("appID");
			int featureIDs = (Integer) jsonObject.get("featureID");
			LOG.info("inside getTestSuiteFilterByAppId at appId");
			screenList = mainService.getScreensByAppIdAndFeatureID(appIDs, featureIDs);
			LOG.info("Screen list ::" + screenList.toString());
		} catch (ServiceException se) {
			LOG.error("getScreenFilterByFeatureIdAndAppIDITAP(Map<String,String>)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreenFilterByFeatureIdAndAppIDITAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return screenList;
	}

	/**
	 * Gets the screens.
	 * 
	 * @param appID
	 *            the app id
	 * @return the screens
	 */
	@RequestMapping(value = "/getScreensForApplicationITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<Screen> getScreens(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreens(String) - start"); //$NON-NLS-1$
		}
		List<Screen> screens = new ArrayList<Screen>();
		int appId = Integer.parseInt(appID);
		LOG.info("Inside getAllScreens");
		try {
			screens = mainService.getScreensByAppId(appId);
			LOG.info("Fetched Screens " + screens.size());
		} catch (ServiceException se) {
			LOG.error("getScreens(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreens(String) - end"); //$NON-NLS-1$
		}
		return screens;
	}

	/**
	 * Gets the screen by screen id.
	 * 
	 * @param screenID
	 *            the screen id
	 * @return the screen by screen id
	 */
	@RequestMapping(value = "/editScreenITAP", method = RequestMethod.POST)
	public @ResponseBody
	Screen getScreenByScreenId(@RequestBody String screenID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreenByScreenId(String) - start"); //$NON-NLS-1$
		}
		LOG.info("Inside getScreenByScreenId method");
		Screen screen = new Screen();
		try {
			int screenId = Integer.parseInt(screenID);
			screen = mainService.getScreenById(screenId);
		} catch (ServiceException se) {
			LOG.error("getScreenByScreenId(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreenByScreenId(String) - end"); //$NON-NLS-1$
		}
		return screen;
	}

	/**
	 * Update screen.
	 * 
	 * @param screenData
	 *            the screen data
	 * @return the long
	 */
	@RequestMapping(value = "/updateScreenITAP", method = RequestMethod.POST)
	public @ResponseBody
	long updateScreen(@RequestBody Map<String, String> screenData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateScreen(Map<String,String>) - start"); //$NON-NLS-1$
		}
		long update = 0;
		try {
			JSONObject jsonObj = new JSONObject(screenData);
			String name = (String) jsonObj.get("screenName");
			String desc = (String) jsonObj.get("screenDescription");
			int screenId = (Integer) jsonObj.get("screenID");
			int appID = (Integer) jsonObj.get("appID");
			Screen screen = new Screen();
			screen.setScreenName(name);
			screen.setDescription(desc);
			screen.setAppID(appID);
			screen.setScreenID(screenId);
			screen.setUpdatedBy(DataConstants.DEFAULT_USER);
			screen.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			update = mainService.updateScreenData(screen);
		} catch (Exception e) {
			LOG.error("updateScreen(Map<String,String>)", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateScreen(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return update;
	}

}