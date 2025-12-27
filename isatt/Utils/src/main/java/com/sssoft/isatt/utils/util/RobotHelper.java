package com.sssoft.isatt.utils.util;

import java.awt.AWTException;
import java.awt.event.KeyEvent;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.sssoft.isatt.utils.bean.UtlConf;

/**
 * The Class RobotHelper.
 */
public class RobotHelper {
	
	/** The LOG. */
	private static Logger LOG = Logger.getLogger(RobotHelper.class);
	
	/** The robot. */
	private static java.awt.Robot robot;

	static {
		try {
			LOG.log(Level.DEBUG, "awt Robot init ");
			robot = new java.awt.Robot();
			LOG.log(Level.INFO, "awt Robot created ");
		} catch (AWTException e) {
			LOG.log(Level.ERROR, "awt Robot not created, non window system ? " + e, e);

		}
	}

	/**
	 * Send keys.
	 *
	 * @param s the s
	 * @param delay the delay
	 * @param shiftKey the shift key
	 * @param altKey the alt key
	 * @param enterKey the enter key
	 * @param otherKeyTrigger the other key trigger
	 * @param otherKey the other key
	 * @param metaKey the meta key
	 * @return true, if successful
	 */
	public static boolean sendKeys(String s, int delay, boolean shiftKey, boolean altKey, boolean enterKey, boolean otherKeyTrigger, char otherKey,
			boolean metaKey) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("sendKeys(String, int, boolean, boolean, boolean, boolean, char, boolean) - start"); //$NON-NLS-1$
		}

		try {
			Thread.sleep(100);
			
			if (shiftKey) {
				robot.keyPress(KeyEvent.VK_SHIFT);
			}

			if (metaKey) {
				robot.keyPress(KeyEvent.VK_META);
			}

			if (otherKeyTrigger) {
				robot.keyPress(otherKey);
			}

			if (altKey) {
				robot.keyPress(KeyEvent.VK_ALT);
			}
			sendKeys(s, delay);

			if (enterKey) {
				robot.keyPress(KeyEvent.VK_ENTER);
				Thread.sleep(delay);
				robot.keyPress(KeyEvent.VK_ENTER);
			}

			if (shiftKey) {
				robot.keyRelease(KeyEvent.VK_SHIFT);
			}

			if (otherKeyTrigger) {
				robot.keyRelease(otherKey);
			}

			if (metaKey) {
				robot.keyRelease(KeyEvent.VK_META);
			}

			if (enterKey) {
				robot.keyRelease(KeyEvent.VK_ENTER);
			}

			if (altKey) {
				robot.keyRelease(KeyEvent.VK_ALT);
			}
			sendEnterKey();

		} catch (Exception e) {
			LOG.error("sendKeys(String, int, boolean, boolean, boolean, boolean, char, boolean)", e); //$NON-NLS-1$

			if (LOG.isDebugEnabled()) {
				LOG.debug("sendKeys(String, int, boolean, boolean, boolean, boolean, char, boolean) - end"); //$NON-NLS-1$
			}
			return false;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("sendKeys(String, int, boolean, boolean, boolean, boolean, char, boolean) - end"); //$NON-NLS-1$
		}
		return true;

	}

	/**
	 * Send keys.
	 *
	 * @param s the s
	 * @param delay the delay
	 * @return true, if successful
	 * @throws InterruptedException the interrupted exception
	 */
	public static boolean sendKeys(String s, int delay) throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("sendKeys(String, int) - start"); //$NON-NLS-1$
		}

		if (s == null){
			if (LOG.isDebugEnabled()) {
				LOG.debug("sendKeys(String, int) - end"); //$NON-NLS-1$
			}
			return true;
		}
		try {
			Thread.sleep(100);
			for (int i = 0; i < s.length(); i++) {
				char ch = s.charAt(i);
				char c = Character.toUpperCase(ch);
				switch (c) {
				case '\n':
					robot.keyPress(KeyEvent.VK_ENTER);
					Thread.sleep(delay);
					robot.keyRelease(KeyEvent.VK_ENTER);
					break;

				case '\t':
					robot.keyPress(KeyEvent.VK_TAB);
					Thread.sleep(delay);
					robot.keyRelease(KeyEvent.VK_TAB);
					break;
				default:
					robot.keyPress(c);
					Thread.sleep(delay);
					robot.keyRelease(c);
					break;
				}
			}

		} catch (Exception e) {
			LOG.error("sendKeys(String, int)", e); //$NON-NLS-1$

			if (LOG.isDebugEnabled()) {
				LOG.debug("sendKeys(String, int) - end"); //$NON-NLS-1$
			}
			return false;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("sendKeys(String, int) - end"); //$NON-NLS-1$
		}
		return true;
	}

	/**
	 * Send enter key.
	 *
	 * @return the int
	 */
	public static int sendEnterKey() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("sendEnterKey() - start"); //$NON-NLS-1$
		}

		try {

			robot.keyPress(KeyEvent.VK_ENTER);
			// LangUtils.sleep(delay);
			robot.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(5000);
		} catch (Exception e) {
			LOG.error("sendEnterKey()", e); //$NON-NLS-1$

			LOG.log(Level.ERROR, "Robot sendKeys :" + e, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("sendEnterKey() - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/**
	 * Tab out.
	 *
	 * @return true, if successful
	 */
	public static boolean tabOut() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("tabOut() - start"); //$NON-NLS-1$
		}

		try {
			robot.keyPress(KeyEvent.VK_TAB);
			String sd = UtlConf.getProperty("robot.tabOutAction.sleep", "250");
			int delay = Integer.parseInt(sd);//some sleep between keys is a good idea		
			LangUtils.sleep(delay);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (LOG.isDebugEnabled()) {
				LOG.debug("tabOut() - end"); //$NON-NLS-1$
			}
			return true;
		} catch (Exception e) {
			LOG.error("tabOut()", e); //$NON-NLS-1$

			LOG.log(Level.ERROR, "Robot sendKeys :" + e, e);

			if (LOG.isDebugEnabled()) {
				LOG.debug("tabOut() - end"); //$NON-NLS-1$
			}
			return false;
		}
		
	}

}
