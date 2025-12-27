package com.exilant.tfw.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.exilant.tfw.constants.Constants;
import com.exilant.tfw.bean.ActionScriptException;

/**
 * ActionUtil classes.
 */

public class ActionUtils {

	/** The LOG. */
	private static Logger LOG = Logger.getLogger(ActionUtils.class);

	/**
	 * Snap shot. *
	 *
	 * @param inputParams the input params * @return the list
	 * @param oBrowser the o browser
	 * @return the list
	 * @throws ActionScriptException the action script exception
	 */
	public static List<String> snapShots(String inputParams, WebDriver oBrowser)
			throws ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("snapShots(String, WebDriver) - start"); //$NON-NLS-1$
		}

		List<String> list = ActionUtils.getList();
		try {
			Date date = new Date();
			String name = String.format("%s.%s", Constants.SNAPSHOT, date);
			String snaps = "./snaps/" + name + ".png";
			File scrFile = ((TakesScreenshot) oBrowser)
					.getScreenshotAs(OutputType.FILE);
			if (scrFile != null){
				FileUtils.copyFile(scrFile, new File(snaps));
			}
			list.add(Constants.SNAP_SHOT_CAPTURED);
			LOG.info(Constants.SNAP_SHOT_CAPTURED);
		} catch (IOException ex) {
			LOG.error("snapShot is not captured for failed testcase", ex);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("snapShots(String, WebDriver) - end"); //$NON-NLS-1$
		}
		return list;
	}

	/**
	 * Snap shot.
	 *
	 * @param oBrowser the o browser
	 * @return the string
	 * @throws ActionScriptException the action script exception
	 */
	public static String snapShot(WebDriver oBrowser)
			throws ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("snapShot(WebDriver) - start"); //$NON-NLS-1$
		}

		String snaps = null;
		try {
			Date date = new Date();
			String name = String.format("%s.%s", Constants.SNAPSHOT, date);
			snaps = "src/Failshots/snaps/"
					+ name.replace(" ", "").replace(":", "_") + ".jpg";
			File scrFile = ((TakesScreenshot) oBrowser)
					.getScreenshotAs(OutputType.FILE);
			if (scrFile != null){
				FileUtils.copyFile(scrFile, new File(snaps));
			}
			LOG.info("snapShot is captured for failed testcase");
		} catch (IOException ex) {
			LOG.error("snapShot is not captured for failed testcase", ex);

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("snapShot(WebDriver) - end"); //$NON-NLS-1$
		}
		return snaps;
	}

	/**
	 * This method defines the waiting period to load a page.
	 *
	 * @param timesecs the timesecs
	 * @param oBrowser the o browser
	 */
	public static void waitForPageToLoad(int timesecs, WebDriver oBrowser) {
		LOG.info("Calling wait For Page To Load Action");
		oBrowser.manage().timeouts().implicitlyWait(timesecs, TimeUnit.SECONDS);

		if (LOG.isDebugEnabled()) {
			LOG.debug("waitForPageToLoad(int, WebDriver) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * This will return an empty list for the caller.
	 *
	 * @return List<String>
	 */
	public static List<String> getList() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getList() - start"); //$NON-NLS-1$
		}

		List<String> list = new ArrayList<String>();

		if (LOG.isDebugEnabled()) {
			LOG.debug("getList() - end"); //$NON-NLS-1$
		}
		return list;
	}

	/**
	 * Compare local and remote resources.
	 *
	 * @param localImg the local img
	 * @param remoteUrl the remote url
	 * @return the int
	 */
	public static int compareLocalAndRemoteResources(File localImg,
			URL remoteUrl) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("compareLocalAndRemoteResources(File, URL) - start"); //$NON-NLS-1$
		}

		InputStream isLocal = null;
		InputStream remoteis = null;
		int ret = 1;
		boolean same = false;
		try {
			// take buffer data from botm image files //

			isLocal = new BufferedInputStream(new FileInputStream(localImg));

			remoteis = new BufferedInputStream(remoteUrl.openStream());
			int remoteInt = -1;
			int localInt = -1;
			same = true;
			while (remoteis.available() > 0 && isLocal.available() > 0) {
				localInt = isLocal.read();
				remoteInt = remoteis.read();
				if (localInt != remoteInt) {
					same = false;
					break;
				}
			}
		} catch (FileNotFoundException e) {
			LOG.error("Failed to compare image files ..."+e, e);
		} catch (IOException e) {
			LOG.error("Failed to compare image files ..."+e, e);
		} finally {
			try {
				isLocal.close();
			} catch (Exception e) {
				LOG.error("Failed to compare image files ...", e);
			}
			try {
				remoteis.close();
			} catch (Exception e) {
				LOG.error("Failed to compare image files ...", e);
			}
		}
		LOG.info("same ..." + same);
		return ret;
	}

//	public static void tabOut() {
//		com.exilant.selenium.util.RobotHelper.sendKeys("\t", 100);
//	}

}
