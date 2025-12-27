/**
t * The  Class Action Script is driven by the agent
 * Performs the defined actions over the application.
 * It will Invoke different types of browsers.
 * Validates the UI input fields.
 * Appends the result after validation to the driver.
 **/

package com.sssoft.isatt.runner.webrunner;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sssoft.isatt.data.pojo.Scheduler;
import com.sssoft.isatt.data.pojo.input.Objects;
import com.sssoft.isatt.data.pojo.input.Param;
import com.sssoft.isatt.data.pojo.input.ParamGroup;
import com.sssoft.isatt.data.pojo.input.TestCase;
import com.sssoft.isatt.data.pojo.input.TestPlan;
import com.sssoft.isatt.data.pojo.input.TestStep;
import com.sssoft.isatt.data.pojo.input.TestSuite;
import com.sssoft.isatt.data.pojo.output.TestCaseResult;
import com.sssoft.isatt.data.pojo.output.TestStepResult;
import com.sssoft.isatt.data.pojo.output.TestSuiteResult;
import com.sssoft.isatt.runner.webruner.util.ActionUtils;
import com.sssoft.isatt.utils.bean.ActionScriptException;
import com.sssoft.isatt.utils.io.ReadStreamAsync;
import com.sssoft.isatt.utils.util.RobotHelper;
import com.sssoft.isatt.utils.util.threads.TfwPools;

/**
 * The Class ActionScript.
 */
public class ActionScript {
	/** The LOG. */
	private static final Logger LOG = Logger.getLogger(ActionScript.class);

	/** The param from ext. */
	private ParamFromTstGenClz paramFromExt = null;

	/** The replace strs. */
	private Map<String, String> replaceStrs = new HashMap<String, String>();

	/** The o browser. */
	private WebDriver oBrowser;

	/** The win handle before. */
	private String winHandleBefore = null;

	/** The capture. */
	String capture;

	/** The robot. */
	RobotHelper robot = new RobotHelper();

	/**
	 * Invokes the type of browser, specified by the user.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	public void launchBrowser(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException, MalformedURLException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("launchBrowser(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		try {
			LOG.info("Calling Launch Browser Action");
			String browserName = param.getTestParamData().getParamValue();
			LOG.info("browserName  " + browserName);
			String profilePath = param.getTestParamData().getValueBig();
			File profileDirectory = null;
			if (profilePath != null) {
				profileDirectory = new File(profilePath);
			}
			boolean foundBrowser = false;
			boolean profBrowser = false;
			if ("FireFox".equalsIgnoreCase(browserName)) {
				if (isProfilePathExists(profileDirectory)) {
					FirefoxProfile profile = new FirefoxProfile(profileDirectory);
					setoBrowser(new FirefoxDriver(profile));
					foundBrowser = true;
				} else {
					LOG.error("Firefox Browserr could not launch.Please verify whether you a firefox browser supporting selenium ");
					profBrowser = true;
				}
			} else if ("Safari".equalsIgnoreCase(browserName)) {
				setoBrowser(new SafariDriver());
				foundBrowser = true;

			} else if ("chrome".equalsIgnoreCase(browserName)) {

				if (isProfilePathExists(profileDirectory)) {
					System.setProperty("webdriver.chrome.driver", param.getTestParamData().getValueBig());
					setoBrowser(new ChromeDriver());
					foundBrowser = true;
				} else {
					LOG.error("Chrome Browser could not launch.Please verify whether you a chrome browser supporting selenium ");
					profBrowser = true;
				}
			}
			if (profBrowser) {
				stepRslt(stepResult, 0, browserName + " browser could not launch.'" + profileDirectory + "' path does not exists", null, null);
				LOG.error(browserName + " browser could not launch.Please verify whether " + profileDirectory + " profile directory path");
			} else {
				if (foundBrowser == false) {

					stepRslt(stepResult, 0, browserName + "   not supported provide a valid browser", null, null);
					LOG.error(browserName + "   not supported provide a valid browser");
				} else if (getoBrowser().getWindowHandle().isEmpty()) {
					stepRslt(stepResult, 0, "Browser not found", null, null);
					LOG.error("Browser not supported.Provide a valid browser");

				} else {
					stepRslt(stepResult, 1, browserName + "  launched successfully ", null, null);
					LOG.info(browserName + " browser  launched successfully");
				}
			}
		} catch (Exception ex) {
			stepRslt(stepResult, 0, "Exception occured", ex.getMessage(), null);
			LOG.error("Exception occured" + ex.getMessage(), ex);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("launchBrowser(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Checks if is profile path exists.
	 * 
	 * @param profileDirectory
	 *            the profile directory
	 * @return true, if is profile path exists
	 */
	private boolean isProfilePathExists(File profileDirectory) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("isProfilePathExists(File) - start"); //$NON-NLS-1$
		}

		boolean isExists = false;
		if (profileDirectory.exists()) {
			isExists = true;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("isProfilePathExists(File) - end"); //$NON-NLS-1$
		}
		return isExists;
	}

	/**
	 * Passes the url to url tab, specified by the user.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void launchApplication(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException {

		LOG.info("Calling loadUrl Action");
		String url = iterParam(testStep).get(0);
		try {
			getoBrowser().get(url);
			stepRslt(stepResult, 1, "Navigated to the " + "  " + url + " " + "  URL", null, null);
			LOG.info("Browser navigated to URL");
			waitForPageToLoad(20);

		} catch (org.openqa.selenium.WebDriverException e) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "URL not found", null, screen);
			LOG.error("Excepton occured" + e.getMessage(), e);
			LOG.info("The url mentioned is not found.Please pass a valid url");
		} catch (NullPointerException e) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "verify url/verify the browser launched", null, screen);
			LOG.info("verify url/verify the browser launched");
			LOG.error("Excepton occured" + e.getMessage(), e);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("launchApplication(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * High light by id.
	 * 
	 * @param id
	 *            the id
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void highLightById(String id) throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("highLightById(String) - start"); //$NON-NLS-1$
		}

		JavascriptExecutor js = (JavascriptExecutor) getoBrowser();

		WebElement element = getoBrowser().findElement(By.id(id));
		js.executeScript("arguments[0].style.border='2px solid red'", element);
		Thread.sleep(100);
		js.executeScript("arguments[0].style.border=''", element);

		if (LOG.isDebugEnabled()) {
			LOG.debug("highLightById(String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * High light by xpath.
	 * 
	 * @param xpath
	 *            the xpath
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void highLightByXpath(String xpath) throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("highLightByXpath(String) - start"); //$NON-NLS-1$
		}

		JavascriptExecutor js = (JavascriptExecutor) getoBrowser();

		WebElement element = getoBrowser().findElement(By.xpath(xpath));
		js.executeScript("arguments[0].style.border='2px solid red'", element);
		Thread.sleep(2000);
		js.executeScript("arguments[0].style.border=''", element);

		if (LOG.isDebugEnabled()) {
			LOG.debug("highLightByXpath(String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Verify button enabled.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void verifyButtonEnabled(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws InterruptedException, ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("verifyButtonEnabled(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		String objectPath = null;
		try {
			objectPath = objects.getIdentifier();
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			if (ele.isEnabled()) {
				stepRslt(stepResult, 1, "Button Enabled", null, null);
				LOG.info("Button enabled");
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Button Disabled", null, screen);
				LOG.error("Button is not enabled");
			}

		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Button not found", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("verifyButtonEnabled(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Verify text drop down.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void verifyTextDropDown(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws InterruptedException, ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("verifyTextDropDown(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		waitForPageToLoad(2000);
		String objectPath = null;
		String textValue = null;
		try {
			objectPath = objects.getIdentifier();
			textValue = param.getTestParamData().getParamValue();
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			Select select = new Select(ele);
			List<WebElement> options = select.getOptions();
			for (WebElement we : options) {
				if (textValue.equals(we.getText())) {
					stepRslt(stepResult, 1, "Drop down text verified", null, null);
					LOG.info("Drop down text verified");
					break;
				} else {
					String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
					stepRslt(stepResult, 0, "Drop down text not verified", null, screen);
					LOG.error("Drop down text not verified");
				}
			}
		} catch (org.openqa.selenium.NoSuchElementException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Drop down text not verified", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("verifyTextDropDown(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Upload files.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void uploadFiles(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException, InterruptedException {

		LOG.info("Calling Upload Action");
		String objectPath = null;
		String value = null;

		try {
			objectPath = objects.getIdentifier();
			value = param.getTestParamData().getParamValue();
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			boolean bool = RobotHelper.sendKeys(value, 100, false, false, true, false, 'G', false);
			LOG.info("  result from bool " + bool);
			stepRslt(stepResult, 1, "File has been uploaded", null, null);
			LOG.info("File has been uploaded");

		} catch (org.openqa.selenium.NoSuchElementException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Enter button is not clciked", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("uploadFiles(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Select text drop down.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void selectTextDropDown(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws InterruptedException, ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("selectTextDropDown(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		waitForPageToLoad(2000);
		String objectPath = null;
		String textValue = null;
		boolean foundElement = false;

		try {
			objectPath = objects.getIdentifier();
			textValue = param.getTestParamData().getParamValue();
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			Select select = new Select(ele);
			List<WebElement> options = select.getOptions();
			for (WebElement wElement : options) {
				LOG.info(" textValue " + textValue + "   wElement.getText()  " + wElement.getText());
				if (textValue.equals(wElement.getText().trim())) {
					select.selectByVisibleText(textValue);
					stepRslt(stepResult, 1, "Drop down text selected", null, null);
					LOG.info("Drop down text selected");
					foundElement = true;
					break;
				}
			}
			if (!foundElement) {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Text not present in the dropdown ", null, screen);
				LOG.error("text not present in the dropdown ");
			}

		} catch (org.openqa.selenium.NoSuchElementException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("text not present in the dropdown ", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("selectTextDropDown(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Verify button disabled.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void verifyButtonDisabled(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws InterruptedException, ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("verifyButtonDisabled(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		waitForPageToLoad(2000);
		String objectPath = null;
		try {
			objectPath = objects.getIdentifier();
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			if (!(ele.isEnabled())) {
				stepRslt(stepResult, 1, "Button Disabled", null, null);
				LOG.info("Button Disabled");
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Button is not Disabled", null, screen);
				LOG.error("Button is not Disabled");
			}
		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Button not found", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("verifyButtonDisabled(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Clear field value.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void clearFieldValue(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws InterruptedException, ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("clearFieldValue(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		waitForPageToLoad(5000);
		String objectPath = null;
		try {
			objectPath = objects.getIdentifier();
			highLightById(objectPath);
			JavascriptExecutor js = (JavascriptExecutor) getoBrowser();
			Thread.sleep(1000);
			js.executeScript("document.getElementById('" + objectPath + "').value='';");
			Thread.sleep(500);
			String placevalue1 = (String) js.executeScript("return document.getElementById('" + objectPath + "').value;");
			if ("".equals(placevalue1)) {
				stepRslt(stepResult, 1, "Field Cleared", null, null);
				LOG.error("Field Cleared");
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Field not Cleared", null, screen);
				LOG.error("Field not Cleared");
			}
		} catch (org.openqa.selenium.NoSuchElementException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Invalid object or Object not found ", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("clearFieldValue(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Move back.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void moveBack(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("moveBack(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		try {
			getoBrowser().navigate().back();
			stepRslt(stepResult, 1, "Moved back a single item.", null, null);
			LOG.info("Moved back a single item.");
		} catch (Exception e) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Browser not navigated Back", null, screen);
			LOG.error("Browser not navigated Back", e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("moveBack(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Move forward.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void moveForward(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("moveForward(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		try {
			getoBrowser().navigate().forward();
			stepRslt(stepResult, 1, "Moved forward a single item.", null, null);
			LOG.info("Moved forward a single item.");
		} catch (Exception e) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Browser not navigated forward", null, screen);
			LOG.error("Browser not navigated forward", e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("moveForward(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Snooze.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void snooze(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException {

		LOG.fatal(" snooze function deprecated in selenium runner use in Core but put delay in testStep param and blank in object param, will remove in next version from selenium");

		int delay = Integer.parseInt(param.getTestParamData().getParamValue());
		try {
			Thread.sleep(delay);
			stepRslt(stepResult, 1, "Page snoozed", null, null);
			LOG.info("Page snoozed");

		} catch (InterruptedException interupt) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Interrupt occurred while snooze", null, screen);
			LOG.error("Interrupt occurred while snooze", interupt);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("snooze(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * If this works sometimes and not at other times : need to make sure
	 * control already has focus and currently browser window with your page has
	 * focus, this is a OS level key event so which ever window has focus will
	 * get the tab key.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws AWTException
	 *             the aWT exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */

	public void tabOutAction(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws AWTException, InterruptedException {
		LOG.info("calling tab out action 1 second sleep and then 10 seconds wait for object load");
		if (RobotHelper.tabOut()) {
			stepRslt(stepResult, 1, "tabed out", null, null);
			LOG.info("Tabbed out in tabOutAction");
		} else {
			stepRslt(stepResult, 0, "Failure to tabout", null, null);
			LOG.info("Failure to tabout");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("tabOutAction(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Text button value.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void textButtonValue(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws InterruptedException, ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("textButtonValue(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		waitForPageToLoad(50000);
		String objectPath = null;
		String buttonValue = null;
		try {
			objectPath = objects.getIdentifier();
			buttonValue = param.getTestParamData().getParamValue();
			String buttonVal = getObjectValue(objectPath);
			if (buttonVal.equals(buttonValue)) {
				stepRslt(stepResult, 1, "Value in button  matched", null, null);
				LOG.info("Value in button  matched");
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Value in button not matched", null, screen);
				LOG.error("Value in button not matched");
			}
		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Button not found", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("textButtonValue(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Specifies the amount of time the driver should wait when searching for an
	 * element if it is not immediately present.
	 * 
	 * @param timesecs
	 *            the timesecs
	 */

	public void waitForPageToLoad(int timesecs) {
		LOG.info("Calling wait For Page To Load Action");

		getoBrowser().manage().timeouts().implicitlyWait(timesecs, TimeUnit.SECONDS);

		if (LOG.isDebugEnabled()) {
			LOG.debug("waitForPageToLoad(int) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Passes value to text box.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void textBox(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException, InterruptedException {
		LOG.info("Calling textBox Action");
		String objectPath = null;
		waitForPageToLoad(30);

		try {
			objectPath = objects.getIdentifier();
			String value = param.getTestParamData().getParamValue();
			if (value.length() != 0) {
				org.openqa.selenium.WebElement ele = findElement(objectPath);
				ele.clear();
				ele.sendKeys(value);
				// getoBrowser().findElement(By.id(objectPath)).clear();
				// getoBrowser().findElement(By.id(objectPath)).sendKeys((value));
				stepRslt(stepResult, 1, "Value passed to Textbox", null, null);
				LOG.info("Value passed to Textbox");
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Value is null", null, screen);
				LOG.info("Value is missing");
			}
		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found", ex.getMessage(), screen);
			LOG.error("The textbox not is present in the specified location", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("textBox(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Text box2.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void textBox2(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException, InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("textBox2(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		String objectPath = null;
		objectPath = objects.getIdentifier();
		if (paramFromExt == null) {
			paramFromExt = new ParamFromTstGenClz();
		}
		paramFromExt.testStep(testCase, testCaseResult, testStep, stepResult, param, objects, replaceStrs);
		String value = replaceStrs.get("deviceId");
		try {
			if (!(value.length() == 0)) {
				org.openqa.selenium.WebElement ele = findElement(objectPath);
				ele.clear();
				ele.sendKeys(value);
				stepRslt(stepResult, 1, "Value passed to Textbox", null, null);
				LOG.info("Value passed to Textbox");
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Value is null", null, screen);
				LOG.info("Value is missing");
			}
		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found", ex.getMessage(), screen);
			LOG.error("The textbox not is present in the specified location", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("textBox2(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * For scrolling on the screen.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void scrollBar(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("scrollBar(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		try {
			JavascriptExecutor jsx = (JavascriptExecutor) getoBrowser();
			jsx.executeScript("window.scrollBy(0,2000)", "");
			stepRslt(stepResult, 1, "Scrolled", null, null);
			LOG.info("Scrolling ");
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("scrollBar(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Causes a new page to load. Checking the field element currently enabled
	 * or not and performs click operation.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void click(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException, InterruptedException {
		LOG.info("Calling click Action");
		String objectPath = null;
		try {
			objectPath = objects.getIdentifier();
			String alertMessage = null;
			waitForPageToLoad(30);
			if ("fileNameInput".equals(objectPath)) {
				LOG.info(" sdkasd");
			}
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			if (ele.isEnabled()) {
				ele.click();
				stepRslt(stepResult, 1, "Object Found,Button Enabled and Clicked on button", null, null);
				LOG.info("Button enabled & clicked");
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Object Found ,Button Disabled and  not clicked on button ", null, screen);
				LOG.error("Button is disabled");
			}

		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Button to be clicked is not found", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("click(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * spanClick Checking the field element currently enabled or not and
	 * performs click operation.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void clickOnSpanElement(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException, InterruptedException {
		LOG.info("Calling spanclick Action");
		String objectPath = null;
		waitForPageToLoad(50);

		try {
			objectPath = objects.getIdentifier();
			waitForPageToLoad(50);
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			if (ele.isEnabled()) {
				ele.click();
				stepRslt(stepResult, 1, "Object Found,Button Enabled and Clicked on button", null, null);
				LOG.info("Button enabled & clicked");

			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Object Found ,Button Disabled and  not clicked on button ", null, screen);
				LOG.error("Button is disabled");
			}

		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Invalid object or Object not found ", ex);
			waitForPageToLoad(10);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("clickOnSpanElement(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Click based on span id.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void clickBasedOnSpanId(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws InterruptedException, ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("clickBasedOnSpanId(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		String objectPath = null;
		try {
			objectPath = objects.getIdentifier();
			Thread.sleep(3000);
			JavascriptExecutor js = (JavascriptExecutor) getoBrowser();
			js.executeScript("return document.getElementById('" + objectPath + "').click();");
			stepRslt(stepResult, 1, "Cliked on element", null, null);
			LOG.info("Cliked on element");
		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Button to be clicked is not found", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("clickBasedOnSpanId(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Checking the field element currently enabled or not and selects the value
	 * in dropdown. And checks if the text is displayed
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void dropDown(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException {
		LOG.info("Calling Drop down Action");
		String objectPath = null;
		String value = param.getTestParamData().getParamValue();
		try {
			objectPath = objects.getIdentifier();
			if (!"".equals(value)) {
				org.openqa.selenium.WebElement ele = findElement(objectPath);
				if (ele.isEnabled()) {
					WebElement dropdownElement = ele;
					Select select = new Select(dropdownElement);
					select.selectByVisibleText(value);
					stepRslt(stepResult, 1, "Object Found,Button Enabled and Clicked on button", null, null);
					LOG.info("button is enable and it is clicked");

				} else {

					String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
					stepRslt(stepResult, 0, "Object Found ,Disabled & not selected", null, screen);
					LOG.error("Button is disabled");

				}
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Value is null", null, screen);
				LOG.error("Value is null");
			}

		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Button to be clicked is not found", ex);

		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("dropDown(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Check box. for checking action in the check box
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void checkBox(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException, InterruptedException {
		LOG.info("Calling Check box  Action");
		String objectPath = null;
		try {
			objectPath = objects.getIdentifier();
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			if (ele.isEnabled()) {
				ele.click();
				stepRslt(stepResult, 1, "Object Found,Check Box value enabled and Selected the option ", null, null);
				LOG.info("Check Box is enabled and checked");
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Object Found and CheckBox value Disabled and not Selected the option ", null, screen);
				LOG.error("Check Box is disabled");

			}

		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Checkbox object is not present", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("checkBox(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Handle alert box. Handles the alert IF you�ve triggered an action that
	 * opens a popup.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void handleAlertBox(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException {

		LOG.info("Calling Alert Action");

		String value = param.getTestParamData().getParamValue();
		if (value.length() == 0) {

			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "invalid/No data has been passed", null, screen);
			LOG.error("Incorrect value is not mentioned for alert");
		}
		try {
			Alert alert = getoBrowser().switchTo().alert();
			String alrtMsg = alert.getText();
			if ("ok".equalsIgnoreCase(value)) {
				alert.accept();
			} else if ("cancel".equalsIgnoreCase(value)) {
				alert.dismiss();
			}
			stepRslt(stepResult, 1, "'" + alrtMsg + "' alert message is displayed", null, null);
			LOG.info("Alert is handled");
		} catch (org.openqa.selenium.UnhandledAlertException e) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Alert is not handled", null, screen);
			LOG.error("Unhandled Alert", e);
		} catch (NoAlertPresentException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "No popup messages", null, screen);
			LOG.error("Popup is not present", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("handleAlertBox(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Clicks the radio button based on user input.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void radioButton(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("radioButton(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		try {
			LOG.info("Calling Radio Button Action");
			String objectPath = null;
			objectPath = objects.getIdentifier();
			String value = param.getTestParamData().getParamValue();
			Integer option = Integer.parseInt(value);
			List<WebElement> radioList = getoBrowser().findElements(By.name(objectPath));

			for (int i = 0; i < radioList.size(); i++) {
				if (radioList.get(i).isEnabled()) {
					radioList.get(option).click();
					if (radioList.get(option).isSelected()) {
						stepRslt(stepResult, 1, "Value Clicked & selected", null, null);
						LOG.info("Value Clicked & selected");
					} else {
						String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
						stepRslt(stepResult, 0, "Value Clicked & not selected", null, screen);
						LOG.info("Value Clicked & not selected");
					}
				} else {
					String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
					stepRslt(stepResult, 0, "Radio Button Disabled", null, screen);
					LOG.error("Radio Button Disabled");
				}
			}
		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Checkbox object is not present", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("radioButton(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Captures the values and label from the UI And it can be re-used.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void captureUIObject(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException, InterruptedException {

		LOG.info("Calling Capture UI Object Action");
		String objectPath = null;
		waitForPageToLoad(20);
		try {
			objectPath = objects.getIdentifier();
			waitForPageToLoad(20);
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			capture = ele.getAttribute("value");
			if (capture == null) {
				capture = ele.getText();
			}
			if (capture != null) {
				stepRslt(stepResult, 1, "Captured Value :" + capture, null, null);
				LOG.info("The value is captured");
			} else {

				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Value is not Captured", null, screen);
				LOG.error("The value is not captured");
			}
		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Object's value to be captured is not present", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("captureUIObject(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the title of the current page, with leading and trailing whitespace
	 * stripped.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void currentTitleOfPage(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("currentTitleOfPage(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		try {
			waitForPageToLoad(120);
			LOG.info("Calling Current Title Action");

			String value = param.getTestParamData().getParamValue();
			String titleval = getoBrowser().getTitle();

			if (value.equals(titleval)) {
				stepRslt(stepResult, 1, "Title matched", null, null);
				LOG.info("Title matched");
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Title differs", null, screen);
				LOG.error("Title differs");

			}
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("currentTitleOfPage(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Reuses the data whenever required.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void reUse(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException, InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("reUse(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		String objectPath = null;
		LOG.info("Calling Reuse  Action");

		try {
			objectPath = objects.getIdentifier();
			if (capture != null) {
				org.openqa.selenium.WebElement ele = findElement(objectPath);
				ele.sendKeys(capture);
				// getoBrowser().findElement(By.id(objectPath)).sendKeys(capture);
				stepRslt(stepResult, 1, "Captured value is reused", null, null);
				LOG.info("The captured value is reused");
			} else {

				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "No value found", null, screen);
				LOG.error("The captured value is not found");
			}
		} catch (org.openqa.selenium.WebDriverException ex) {

			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Invalid object or Object not found ", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("reUse(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * To verify any particular label to be present in UI.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void verification(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException, InterruptedException {

		LOG.info("Calling Verification Action");
		// waitForPageToLoad(100);
		String objectPath = null;
		String value = param.getTestParamData().getParamValue().trim();
		try {
			objectPath = objects.getIdentifier();
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			String idText = ele.getText().trim();
			// String idText =
			// getoBrowser().findElement(By.xpath(objectPath)).getText();
			if (idText.equals(value)) {
				stepRslt(stepResult, 1, "Verified", null, null);
				LOG.info("Text verified");
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Text not matched & not verified", null, screen);
				LOG.info("Text not matched & not verified");
			}

		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found", ex.getMessage(), screen);
			LOG.error("The object to be verified is not present", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("verification(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * To verify any particular label to be present in UI.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void verificationByIds(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException, InterruptedException {

		LOG.info("Calling Verification Action");
		String objectPath = null;
		String value = param.getTestParamData().getParamValue();
		if (value != null) {
			value = value.trim();
		}
		try {
			objectPath = objects.getIdentifier();
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			String idText = ele.getText().trim();
			if (idText.equals(value)) {
				stepRslt(stepResult, 1, "Verified", null, null);
				LOG.info("Text verified");
			} else {

				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Text not matched & not verified", idText + " string appears in the ui", screen);
				LOG.info("Text not matched & not verified");
			}

		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Invalid object or Object not found ", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("verificationByIds(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Find element.
	 * 
	 * @param objectPath
	 *            the object path
	 * @return the web element
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	private WebElement findElement(String objectPath) throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("findElement(String) - start"); //$NON-NLS-1$
		}

		if (objectPath == null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("findElement(String) - end"); //$NON-NLS-1$
			}
			return null;
		} else if (objectPath.contains("/")) {
			highLightByXpath(objectPath);
			WebElement ele = getoBrowser().findElement(By.xpath(objectPath));

			if (LOG.isDebugEnabled()) {
				LOG.debug("findElement(String) - end"); //$NON-NLS-1$
			}
			return ele;
		} else {
			highLightById(objectPath);
			WebElement ele = getoBrowser().findElement(By.id(objectPath));

			if (LOG.isDebugEnabled()) {
				LOG.debug("findElement(String) - end"); //$NON-NLS-1$
			}
			return ele;
		}
	}

	/**
	 * To verify shadow text,placeholder value in all textboxes.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @return the list
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void textBoxShadowTextDivId(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException, InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("textBoxShadowTextDivId(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		waitForPageToLoad(10);
		String placevalue = null;
		String value = param.getTestParamData().getParamValue();
		String objectPath = null;
		try {
			objectPath = objects.getIdentifier();
			JavascriptExecutor js = (JavascriptExecutor) getoBrowser();
			placevalue = (String) js.executeScript("return document.getElementById('" + objectPath + "').innerHTML;");
			if (placevalue.equals(value)) {
				stepRslt(stepResult, 1, "Verified", null, null);
				LOG.info("Text verified");
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Text not matched & not verified", null, screen);
				LOG.info("Text not matched & not verified");
			}
		} catch (org.openqa.selenium.WebDriverException ex) {

			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Invalid object or Object not found ", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("textBoxShadowTextDivId(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * To verify shadow text,placeholder value in all textboxes.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @return the list
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void textBoxShadowTextVerification(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("textBoxShadowTextVerification(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		waitForPageToLoad(10);
		String placevalue = null;
		String objectPath = null;
		String value = param.getTestParamData().getParamValue();
		objectPath = objects.getIdentifier();
		try {
			getoBrowser().getCurrentUrl();

			JavascriptExecutor js = (JavascriptExecutor) getoBrowser();
			placevalue = (String) js.executeScript("return document.getElementById('" + objectPath + "').placeholder;");
			if (placevalue.equals(value)) {
				stepRslt(stepResult, 1, "Verified", null, null);
				LOG.info("Text verified");
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Text not matched & not verified", null, screen);
				LOG.info("Text not matched & not verified");
			}
		} catch (org.openqa.selenium.WebDriverException ex) {

			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Invalid object or Object not found ", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("textBoxShadowTextVerification(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Select image for cp.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void selectImageForCP(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws InterruptedException, ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("selectImageForCP(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		String objectPath = null;
		WebElement ele = null;
		String value = param.getTestParamData().getParamValue();
		objectPath = objects.getIdentifier();
		boolean flag = false;
		try {
			WebElement table_element = findElement(objectPath);
			List<WebElement> tr_collection = table_element.findElements(By.xpath("id('" + objectPath + "')/tbody/tr"));
			LOG.info("Number of rows in this table = " + tr_collection.size());
			int row_num, col_num;
			row_num = 1;
			looprow: for (WebElement trElement : tr_collection) {
				List<WebElement> td_collection = trElement.findElements(By.tagName("td"));
				LOG.info("Number of columns = " + td_collection.size());
				col_num = 1;
				stepRslt(stepResult, 0, " Unable to click on the selected row ", null, null);
				for (WebElement tdElement : td_collection) {
					LOG.info("row # " + row_num + ", col # " + col_num + "");
					ele = tdElement.findElement(By.tagName("img"));
					if (ele.isDisplayed() && ele.isEnabled()) {
						col_num++;
						break;
					} else {
						tdElement.click();
						flag = true;
					}
					if (flag) {
						stepRslt(stepResult, 1, " Clicked on the selected row ", null, null);
						break looprow;

					}
				}
			}
		} catch (org.openqa.selenium.WebDriverException ex) {

			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Invalid object or Object not found ", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("selectImageForCP(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Quits this driver, closing every associated window. As creating a Driver
	 * instance starts a session, yet it has to be terminated explicitly with a
	 * call to quit().
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 */
	public void closeBrowser(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("closeBrowser(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		try {
			LOG.info("Calling Close Browser Action");
			getoBrowser().close();
			getoBrowser().quit();
			stepRslt(stepResult, 1, "Browser is closed", null, null);
			LOG.info("Browser is closed");
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("closeBrowser(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Press enter key.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void keyPressEnter(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException {
		LOG.info("Calling press enter key Action");
		String objectPath = null;

		try {
			objectPath = objects.getIdentifier();
			getoBrowser().findElement(By.id(objectPath)).sendKeys(Keys.RETURN);
			Actions builder = new Actions(getoBrowser());
			builder.keyDown(getoBrowser().findElement(By.id(objectPath)), Keys.ENTER).perform();
			stepRslt(stepResult, 1, "Pressed Enter key", null, null);
			LOG.info("pressed Enter key");
		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Checkbox object is not present", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("keyPressEnter(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Press down arrow key.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void keyPressArrowDown(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException {

		LOG.info("Calling press Down Arrow Action");
		String objectPath = null;
		try {
			objectPath = objects.getIdentifier();
			((RemoteWebDriver) getoBrowser()).getKeyboard().pressKey(Keys.DOWN);
			stepRslt(stepResult, 1, "Down arrow Key pressed", null, null);
			LOG.info("Down arrow Key pressed");
		} catch (org.openqa.selenium.NoSuchElementException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Down arrow button is not pressed", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("keyPressArrowDown(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Press up arrow key.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void keyPressArrowUp(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException, InterruptedException {

		LOG.info("Calling press Up Arrow Action");
		String objectPath = null;
		try {
			objectPath = objects.getIdentifier();
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			ele.sendKeys(Keys.ARROW_UP);
			stepRslt(stepResult, 1, "Up arrow key pressed", null, null);
			LOG.info("Pressed up arrow key");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Up arrow key not pressed", null, screen);
			LOG.error("Up arrow key is not clciked", e);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("keyPressArrowUp(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Press right arrow key.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void keyPressArrowRight(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException, InterruptedException {

		LOG.info("Calling press Right Arrow Action");
		String objectPath = null;
		try {
			objectPath = objects.getIdentifier();
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			ele.sendKeys(Keys.ARROW_RIGHT);
			stepRslt(stepResult, 1, "Right arrow key pressed", null, null);
			LOG.info("Right arrow key pressed");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Right arrow button not pressed", null, screen);
			LOG.error("Right arrow key is not clciked", e);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("keyPressArrowRight(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Press left arrow key.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void keyPressArrowLeft(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException, InterruptedException {

		LOG.info("Calling press Left Arrow Action");
		String objectPath = null;
		try {
			objectPath = objects.getIdentifier();
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			ele.sendKeys(Keys.ARROW_LEFT);
			stepRslt(stepResult, 1, "Left arrow key pressed", null, null);
			LOG.info("Pressed left arrow key");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Left arrow key is not pressed", null, screen);
			LOG.error("Left arrow key is not pressed", e);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("keyPressArrowLeft(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/*
	 * 
	 * To run a javascript
	 */

	/**
	 * Java script put.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void javaScriptPut(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("javaScriptPut(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		try {
			JavascriptExecutor js = (JavascriptExecutor) getoBrowser();
			String aa = param.getTestParamData().getParamValue();
			js.executeScript(aa);
			stepRslt(stepResult, 1, "Javascript Executed", null, null);
			LOG.info("Javascript able to perform a action");
		} catch (Exception e) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Javascript  not Executed", null, screen);
			LOG.error("Javascript unable to perform a action", e);
			try {
				screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			} catch (ActionScriptException e1) {
				stepRslt(stepResult, 0, "Javascript falied and screenshot not taken", null, screen);
				LOG.error("Javascript falied and screenshot not taken", e1);
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("javaScriptPut(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Close browser when failed.
	 * 
	 * @param scheduler
	 *            the scheduler
	 * @param plan
	 *            the plan
	 * @param suite
	 *            the suite
	 * @param testCase
	 *            the test case
	 * @param caseResult
	 *            the case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param suiteSummaryResult
	 *            the suite summary result
	 */
	public void closeBrowserWhenFailed(Scheduler scheduler, TestPlan plan, TestSuite suite, TestCase testCase, TestCaseResult caseResult,
			TestStep testStep, TestStepResult stepResult, TestSuiteResult suiteSummaryResult) {

		LOG.info("Calling Close Browser when test case gets failed");
		getoBrowser().quit();

		if (LOG.isDebugEnabled()) {
			LOG.debug("closeBrowserWhenFailed(Scheduler, TestPlan, TestSuite, TestCase, TestCaseResult, TestStep, TestStepResult, TestSuiteResult) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Mouse over an element.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void mouseOverAnElement(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException {

		LOG.info("Calling mouse over an element Action");
		String objectPath = null;
		try {

			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', 1, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			objectPath = objects.getIdentifier();
			JavascriptExecutor js = (JavascriptExecutor) getoBrowser();
			WebElement element = getoBrowser().findElement(By.className(objectPath));
			js.executeScript(mouseOverScript, element);
			stepRslt(stepResult, 1, "Mouse over an element is viewed", null, null);
			LOG.info("Mouse over an element is viewed");

		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("mouseOverAnElement(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the current url.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 */
	public void refreshPage(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects) {

		LOG.info("Calling refresh Action");
		// waitForPageToLoad(20);
		try {
			String currentUrl = getoBrowser().getCurrentUrl();
			Thread.sleep(2000);
			currentUrl = currentUrl + " ";
			getoBrowser().get(currentUrl);
			stepRslt(stepResult, 1, "Current url  " + currentUrl + "  is loaded", null, null);
			LOG.info("Current url  " + currentUrl + "  is loaded");
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("refreshPage(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Customized date picker via txt.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void customizedDatePickerViaTxt(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException, InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("customizedDatePickerViaTxt(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		String dat = null;
		String objectPath = null;
		String value = param.getTestParamData().getParamValue();
		if (value.charAt(0) == '$') {
			dat = value.substring(1, value.length() - 1);
		}
		try {
			objectPath = objects.getIdentifier();
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			ele.clear();
			ele.sendKeys((dat));
			org.openqa.selenium.WebElement ele1 = findElement("//*[contains(text(),'Done')]");
			ele1.click();
			stepRslt(stepResult, 1, value + " is passed to datepicker", null, null);
			LOG.info("Value passed to Textbox");
		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Invalid object or Object not found", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("customizedDatePickerViaTxt(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * picks date from date picker.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void customizedDatePicker(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException, InterruptedException {

		LOG.info(" Calling customized date picker action");
		customizedDatePickerViaTxt(testCase, testCaseResult, testStep, stepResult, param, objects);

		if (LOG.isDebugEnabled()) {
			LOG.debug("customizedDatePicker(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * picks current date from date picker.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void currentDatePicker(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException, InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("currentDatePicker(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		try {
			String objectPath = null;
			objectPath = objects.getIdentifier();
			String datePattern = "";
			SimpleDateFormat ft = null;
			List<String> inputListdate = new ArrayList<String>();

			Date date = new Date();
			ft = new SimpleDateFormat(datePattern);
			String strDateFormat = ft.format(date);
			for (String retval : strDateFormat.split("/")) {
				inputListdate.add(retval);
			}
			String dt = formatDate(inputListdate, objectPath);
			if (strDateFormat.equals(dt)) {
				stepRslt(stepResult, 1, "Date is selected successfully", null, null);
				LOG.info("Date is selected successfully");
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Date selection was unsuccessful", null, screen);
				LOG.error("Date selection was unsuccessful");
			}
		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Checkbox object is not present", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("currentDatePicker(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Format date.
	 * 
	 * @param inputList
	 *            the input list
	 * @param objectPath
	 *            the object path
	 * @return true, if successful
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public String formatDate(List<String> inputList, String objectPath) throws ActionScriptException, InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("formatDate(List<String>, String) - start"); //$NON-NLS-1$
		}

		String date = null;
		// Calendar Month and Year
		String appMonth = null;
		String appYear = null;
		int month;
		int year;
		boolean dateNotExist;

		List<String> monthList = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
				"November", "December");
		org.openqa.selenium.WebElement ele = findElement(objectPath);
		getoBrowser().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		ele.click();
		dateNotExist = true;
		month = Integer.parseInt(inputList.get(0));
		year = Integer.parseInt(inputList.get(2));
		date = inputList.get(1);
		Thread.sleep(1000);
		while (dateNotExist) {
			appMonth = getoBrowser().findElement(By.className("ui-datepicker-month")).getText();
			appYear = getoBrowser().findElement(By.className("ui-datepicker-year")).getText();

			LOG.log(Level.INFO, "appMonth  " + appMonth + " appYear " + appYear);
			if (monthList.indexOf(appMonth) + 1 == month && (year == Integer.parseInt(appYear))) {
				selectDate(date);
				dateNotExist = false;
			} else if (monthList.indexOf(appMonth) + 1 < month && (year == Integer.parseInt(appYear)) || year > Integer.parseInt(appYear)) {
				getoBrowser().findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/a[2]/span")).click();
			} else if (monthList.indexOf(appMonth) + 1 > month && (year == Integer.parseInt(appYear)) || year < Integer.parseInt(appYear)) {
				getoBrowser().findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/a[1]/span")).click();
			}
		}

		Thread.sleep(10000);
		JavascriptExecutor js = (JavascriptExecutor) getoBrowser();
		String placevalue = (String) js.executeScript("return document.getElementById('" + objectPath + "').value;");

		if (LOG.isDebugEnabled()) {
			LOG.debug("formatDate(List<String>, String) - end"); //$NON-NLS-1$
		}
		return placevalue;
	}

	/**
	 * Select date.
	 * 
	 * @param date
	 *            the date
	 */
	public void selectDate(String date) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("selectDate(String) - start"); //$NON-NLS-1$
		}

		String selDate = null;
		@SuppressWarnings("unused")
		WebElement dateSetter;
		List<WebElement> columns;
		dateSetter = getoBrowser().findElement(By.id("ui-datepicker-div"));
		List<WebElement> rows = dateSetter.findElements(By.tagName("tr"));
		columns = dateSetter.findElements(By.tagName("td"));
		if (date.startsWith("0")) {
			selDate = date.substring(1);
		} else {
			selDate = date;
		}

		for (WebElement cell : columns) {
			if (cell.getText().equals(selDate)) {
				cell.findElement(By.linkText(selDate)).click();
				break;
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("selectDate(String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Takes screenshot when a testcase fails.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @return the string
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public String snapShot(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("snapShot(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		String snaps = null;

		waitForPageToLoad(100);
		String snapName = "Snapshot";
		Date date = new Date();
		String name = String.format("%s.%s", snapName, date);
		snaps = name.replace(" ", "").replace(":", "_") + ".jpg";
		File fsnap = new File(ActionScript.class.getClassLoader() + "snapShots/", snaps);
		try {
			File scrFile = ((TakesScreenshot) getoBrowser()).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, fsnap);
			LOG.info("snapShot is captured for failed testcase");
		} catch (UnreachableBrowserException e) {
			getoBrowser().getCurrentUrl();
			try {
				File scrFile = ((TakesScreenshot) getoBrowser()).getScreenshotAs(OutputType.FILE);
				LOG.log(Level.INFO, "snap src [" + scrFile + "]");
				FileUtils.copyFile(scrFile, fsnap);
			} catch (Exception e1) {
				LOG.error("snapShot is not captured for failed testcase :" + e1, e1);
			}
			LOG.error("snapShot is captured for failed testcase", e);
		} catch (Exception ex) {
			LOG.error("snapShot is not captured for failed testcase" + ex, ex);
		}
		LOG.log(Level.INFO, "snap src [" + snaps + "]" + " file [" + fsnap + "] exists " + fsnap.exists());

		if (LOG.isDebugEnabled()) {
			LOG.debug("snapShot(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
		return snaps;
	}

	/**
	 * Slider action.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void sliderButton(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException, InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("sliderButton(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		try {

			String objectPath = null;
			objectPath = objects.getIdentifier();
			String value = param.getTestParamData().getParamValue();

			getoBrowser().switchTo().frame(value);
			org.openqa.selenium.WebElement slider = findElement(objectPath);
			// WebElement slider =
			// getoBrowser().findElement(By.xpath(objectPath));
			if (slider.isEnabled()) {
				Actions move = new Actions(getoBrowser());
				Action action = (Action) move.dragAndDropBy(slider, 85, 50).build();
				stepRslt(stepResult, 1, "Slider is enabled and dragged", null, null);
				LOG.info("Slider Dragged");
				action.perform();
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Slider disabled unable to drag", null, screen);
				LOG.info("Slider disabled cannot drag");
			}
		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Checkbox object is not present", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("sliderButton(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Toggle action.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void toggleButton(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException, InterruptedException {

		LOG.info("Calling Toggle action");
		String objectPath = null;
		try {
			objectPath = objects.getIdentifier();
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			if (ele.isEnabled()) {
				ele.click();
				stepRslt(stepResult, 1, "Displayed Element", null, null);
				LOG.info("Displayed Element");
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Element not Displayed", null, screen);
				LOG.error("Element not Displayed");
			}
		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Invalid object or Object not found ", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("toggleButton(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Drags an element and drops to specified location.
	 * 
	 * @param scheduler
	 *            the scheduler
	 * @param plan
	 *            the plan
	 * @param suite
	 *            the suite
	 * @param testCase
	 *            the test case
	 * @param caseResult
	 *            the case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param suiteSummaryResult
	 *            the suite summary result
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 */

	public void checkDefaultSelectedValue(Scheduler scheduler, TestPlan plan, TestSuite suite, TestCase testCase, TestCaseResult caseResult,
			TestStep testStep, TestStepResult stepResult, TestSuiteResult suiteSummaryResult, Objects objects) throws ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("checkDefaultSelectedValue(Scheduler, TestPlan, TestSuite, TestCase, TestCaseResult, TestStep, TestStepResult, TestSuiteResult, Objects) - start"); //$NON-NLS-1$
		}

		String objectPath = null;
		objectPath = objects.getIdentifier();
		WebElement thisElement = getoBrowser().findElement(By.id(objectPath));
		if (!thisElement.isSelected() && thisElement.isEnabled()) {
			stepRslt(stepResult, 1, "Value not selected by default", null, null);
			LOG.info("Button is enabled and not selected");
		} else {
			String screen = ActionUtils.snapShot(getoBrowser());
			stepRslt(stepResult, 0, "Value selected by default", null, screen);
			LOG.info("Value selected by default");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("checkDefaultSelectedValue(Scheduler, TestPlan, TestSuite, TestCase, TestCaseResult, TestStep, TestStepResult, TestSuiteResult, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Double click.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void doubleClick(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws InterruptedException, ActionScriptException {
		LOG.info("Calling double click Action");

		waitForPageToLoad(30);
		String objectPath = null;
		try {
			objectPath = objects.getIdentifier();
			Actions builder = new Actions(getoBrowser());
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			if (ele.isEnabled()) {
				org.openqa.selenium.interactions.Action doubleClick = builder.doubleClick(ele).build();
				doubleClick.perform();
				stepRslt(stepResult, 1, "Button enabled & clicked", null, null);
				LOG.info("Button enabled & clicked");
			} else {
				String screen = ActionUtils.snapShot(getoBrowser());
				stepRslt(stepResult, 0, "Object Found ,Element Disabled and  not clicked on element ", null, screen);
				LOG.error("Button is disabled");
			}

		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = ActionUtils.snapShot(getoBrowser());
			stepRslt(stepResult, 0, "Invalid object or Object not found", ex.getMessage(), screen);
			LOG.error("Invalid object or Object not found" + ex.getMessage(), ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("doubleClick(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Verifies whether the image is loaded in UI.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void verifyImageLoad(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException, InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("verifyImageLoad(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		try {
			String objectPath = null;
			objectPath = objects.getIdentifier();
			WebElement image = findElement(objectPath);
			Boolean imageLoaded1 = (Boolean) ((JavascriptExecutor) getoBrowser()).executeScript(
					"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", image);
			if (!imageLoaded1) {
				stepRslt(stepResult, 1, "image is present", null, null);
			} else {
				String screen = ActionUtils.snapShot(getoBrowser());
				stepRslt(stepResult, 0, "image is not present", null, screen);
			}
		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Checkbox object is not present", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("verifyImageLoad(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * * Method which verifies the UI field holding the current date in the
	 * specified format date format specified by the user Date value from the
	 * objects.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void verifyCurrentDateValue(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException {

		LOG.info("Calling verify current date Action");
		String idValue;
		String objectPath = null;
		try {
			waitForPageToLoad(50000);
			objectPath = objects.getIdentifier();
			String value = "";
			java.util.Date dNow = new Date();
			SimpleDateFormat ft = new SimpleDateFormat(value);
			String formattodayDate = ft.format(dNow);
			idValue = getObjectValue(objectPath);
			if (idValue.equals(formattodayDate)) {
				stepRslt(stepResult, 1, "Current date is displayed", null, null);
				LOG.info("Current date is displayed");
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Current date is not displayed", null, screen);
				LOG.info("Current date is not displayed");
			}
		} catch (org.openqa.selenium.WebDriverException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Checkbox object is not present", ex);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("verifyCurrentDateValue(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Method which returns the value of UI fields Accepts the parameter in form
	 * of strings (eg., xpath,id)
	 * 
	 * @param objectPath
	 *            the object path
	 * @return the object value
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public String getObjectValue(String objectPath) throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectValue(String) - start"); //$NON-NLS-1$
		}

		highLightById(objectPath);
		JavascriptExecutor js = (JavascriptExecutor) getoBrowser();
		Thread.sleep(10000);
		String placevalue = (String) js.executeScript("return document.getElementById('" + objectPath + "').value;");

		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectValue(String) - end"); //$NON-NLS-1$
		}
		return placevalue;
	}

	/**
	 * Gets the o browser.
	 * 
	 * @return the o browser
	 */
	public WebDriver getoBrowser() {
		return oBrowser;
	}

	/**
	 * Sets the o browser.
	 * 
	 * @param oBrowser
	 *            the new o browser
	 */
	public void setoBrowser(WebDriver oBrowser) {
		this.oBrowser = oBrowser;
	}

	/**
	 * Clear text box value.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void clearTextBoxValue(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws InterruptedException, ActionScriptException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("clearTextBoxValue(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		waitForPageToLoad(5000);
		String objectPath = null;
		try {
			objectPath = objects.getIdentifier();
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			ele.clear();
			if (ele.getText().length() == 0) {
				stepRslt(stepResult, 1, "Text Field Cleared", null, null);
				LOG.error("Text Field Cleared");
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Text Field not Cleared", null, screen);
				LOG.error("Text Field not Cleared");
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Object not found or Invalid object", null, screen);
			LOG.error("Object not found or Invalid object", e);
		} catch (Exception e) {
			stepRslt(stepResult, 0, "Excepton occured", e.getMessage(), null);
			LOG.error("Excepton occured" + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("clearTextBoxValue(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Os comm.
	 * 
	 * @param cmd
	 *            the cmd
	 * @return the string
	 */
	public static String osComm(String cmd) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("osComm(String) - start"); //$NON-NLS-1$
		}

		try {

			Process Results = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", cmd });
			// Results.waitFor(); //- Add this line if and only if you aren't
			// collecting any data but need the command line stuff to have
			// finished before continuing.
			// If you don't need either then you can remove 'Process Results='
			// from the line above.
			BufferedReader br = new BufferedReader(new InputStreamReader(Results.getInputStream()));
			String s = br.readLine();
			br.close();
			br = null;
			Results = null;

			if (LOG.isDebugEnabled()) {
				LOG.debug("osComm(String) - end"); //$NON-NLS-1$
			}
			return s;
		} catch (Exception e) {
			LOG.error("osComm(String)", e); //$NON-NLS-1$
			return "Error with UNIX Command";
		}
	}

	/**
	 * Store parent browser window.
	 * 
	 * @param scheduler
	 *            the scheduler
	 * @param plan
	 *            the plan
	 * @param suite
	 *            the suite
	 * @param testCase
	 *            the test case
	 * @param caseResult
	 *            the case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param suiteSummaryResult
	 *            the suite summary result
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void storeParentBrowserWindow(Scheduler scheduler, TestPlan plan, TestSuite suite, TestCase testCase, TestCaseResult caseResult,
			TestStep testStep, TestStepResult stepResult, TestSuiteResult suiteSummaryResult, Objects objects) throws ActionScriptException,
			InterruptedException {
		LOG.info("Calling SwitchBrowser click Action");
		try {
			setWinHandleBefore(getoBrowser().getWindowHandle());
			stepRslt(stepResult, 1, "Stored parent window", null, null);
		} catch (Exception ex) {
			LOG.error(
					"storeParentBrowserWindow(Scheduler, TestPlan, TestSuite, TestCase, TestCaseResult, TestStep, TestStepResult, TestSuiteResult, Objects)", ex); //$NON-NLS-1$

			stepRslt(stepResult, 0, "Unable to store parent window", null, null);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("storeParentBrowserWindow(Scheduler, TestPlan, TestSuite, TestCase, TestCaseResult, TestStep, TestStepResult, TestSuiteResult, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Switch to child window.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void switchToChildWindow(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException, InterruptedException {
		LOG.info("Calling SwitchBrowser click Action");
		try {
			for (String winHandle : getoBrowser().getWindowHandles()) {
				LOG.info("Title before switching " + getoBrowser().getTitle());
				if (!getWinHandleBefore().equals(winHandle)) {
					getoBrowser().switchTo().window(winHandle);
					LOG.info("Title after switching " + getoBrowser().getTitle());
					break;
				}
			}
			LOG.info("Title " + getoBrowser().getTitle());
			stepRslt(stepResult, 1, "switched to Child window", null, null);
			LOG.info("switch to new window");
		} catch (Exception ex) {
			stepRslt(stepResult, 0, "Excepton occured", ex.getMessage(), null);
			LOG.error("Failed to switch between browser", ex);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("switchToChildWindow(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Parent browser window.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void parentBrowserWindow(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws ActionScriptException, InterruptedException {
		LOG.info("Calling SwitchBrowser click Action");
		try {
			if (getWinHandleBefore() != null) {

				getoBrowser().switchTo().window(getWinHandleBefore());
				stepRslt(stepResult, 1, "Switched to parent window", null, null);
				LOG.info("Switched to parent window");
			} else {
				stepRslt(stepResult, 0, "call storeParentBrowserWindow  action before calling parentBrowserwindow", null, null);
				LOG.error("call storeParentBrowserWindow  action before calling parentBrowserwindow");
			}
		} catch (Exception ex) {
			stepRslt(stepResult, 0, "Unable to switch to parent window", ex.getMessage(), null);
			LOG.error("Unable to switch to parent window", ex);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("parentBrowserWindow(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Wait for element to load.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ActionScriptException
	 *             the action script exception
	 */
	public void waitForElementToLoad(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param,
			Objects objects) throws InterruptedException, ActionScriptException {
		LOG.info("Calling waitForElementToLoad Action");
		waitForPageToLoad(30);
		String objectPath = null;
		try {
			objectPath = objects.getIdentifier();
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			WebDriverWait wait = new WebDriverWait(oBrowser, 30);
			wait.until(ExpectedConditions.visibilityOf(ele));
			if (ele.isDisplayed()) {
				stepRslt(stepResult, 1, "Element found in the page", null, null);
				LOG.info("Element found");
			} else {
				String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
				stepRslt(stepResult, 0, "Fail to find the element in the page", null, screen);
				LOG.error("Fail to find the element in the page");
			}
		} catch (org.openqa.selenium.NoSuchElementException ex) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Invalid object or Object not found ", ex.getMessage(), screen);
			LOG.error("Element to be found in not available in the page", ex);
		} catch (Exception ex) {
			stepRslt(stepResult, 0, "Excepton occured", ex.getMessage(), null);
			LOG.error("Failed to switch between browser", ex);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("waitForElementToLoad(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Step rslt.
	 * 
	 * @param stepResult
	 *            the step result
	 * @param relt
	 *            the relt
	 * @param summary
	 *            the summary
	 * @param exception
	 *            the exception
	 * @param screen
	 *            the screen
	 */
	public static void stepRslt(TestStepResult stepResult, int relt, String summary, String exception, String screen) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("stepRslt(TestStepResult, int, String, String, String) - start"); //$NON-NLS-1$
		}

		stepResult.setResult(relt);
		stepResult.setComment(summary);
		if (screen != null) {
			stepResult.setSnapShot(screen);
		}

		if (exception != null) {
			StringBuilder sb = new StringBuilder();
			String gg = stepResult.getComment();
			if (gg != null) {
				sb.append(gg).append("\n");
			}
			sb.append(exception);
			stepResult.setException(sb.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("stepRslt(TestStepResult, int, String, String, String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Checks if is alert present.
	 * 
	 * @return the string
	 */
	public String isAlertPresent() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("isAlertPresent() - start"); //$NON-NLS-1$
		}

		String isAlertExists = null;
		try {
			// Check the presence of alert
			Alert alert = getoBrowser().switchTo().alert();
			// Alert present; set the flag
			isAlertExists = alert.getText();
			// if present consume the alert
			alert.accept();

			if (LOG.isDebugEnabled()) {
				LOG.debug("isAlertPresent() - end"); //$NON-NLS-1$
			}
			return isAlertExists;
		} catch (NoAlertPresentException ex) {
			LOG.error("isAlertPresent()", ex); //$NON-NLS-1$
			return null;
		}

	}

	/**
	 * Download file.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @param objects
	 *            the objects
	 * @throws ActionScriptException
	 *             the action script exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void downloadFile(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects)
			throws ActionScriptException, InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("downloadFile(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		String propPath = param.getTestParamData().getParamValue();
		LOG.info("property file path : " + propPath);
		File propFilePath = new File(propPath);
		String objectPath = null;
		com.sssoft.isatt.utils.bean.UtlProps prop = new com.sssoft.isatt.utils.bean.UtlProps(propFilePath);
		try {
			objectPath = objects.getIdentifier();
			String downloadDir = null;
			String filePrefix = null;
			String filePostFix = null;
			String waitFor1 = null;
			String waitFor2 = null;
			String minSizeInBytes = null;
			String maxIterate = null;

			downloadDir = prop.getProperty("downloadDir");
			filePrefix = prop.getProperty("filePrefix");
			filePostFix = prop.getProperty("filePostFix");
			waitFor1 = prop.getProperty("waitFor1");
			waitFor2 = prop.getProperty("waitFor2");
			minSizeInBytes = prop.getProperty("minSizeInBytes");
			maxIterate = prop.getProperty("maxIterate");

			File folder = new File(downloadDir);
			File[] listOfFiles = folder.listFiles();
			List<File> allFiles = new ArrayList<File>();
			int filesCountBeforeDownloading = listOfFiles.length;

			for (int i = 0; i < filesCountBeforeDownloading; i++) {
				allFiles.add(listOfFiles[i]);
				LOG.info("File " + listOfFiles[i].getName());
			}
			org.openqa.selenium.WebElement ele = findElement(objectPath);
			ele.click();
			Thread.sleep(Integer.parseInt(waitFor1) * 1000);
			File[] listOfFilesAfterDownloading = folder.listFiles();
			int filesCountAfterDownloading = listOfFilesAfterDownloading.length;
			List<File> allFilesAfterDownloading = new ArrayList<File>();
			if (filesCountAfterDownloading > filesCountBeforeDownloading) {

				if (filePrefix.length() == 0) {

					for (int i = 0; i < filesCountAfterDownloading; i++) {
						if (listOfFilesAfterDownloading[i].getName().endsWith(filePostFix)) {
							allFilesAfterDownloading.add(listOfFilesAfterDownloading[i]);
							LOG.info("File " + listOfFiles[i].getName());
						}
					}
				} else {
					for (int i = 0; i < filesCountAfterDownloading; i++) {
						if (listOfFilesAfterDownloading[i].getName().startsWith(filePrefix)
								&& listOfFilesAfterDownloading[i].getName().endsWith(filePostFix)) {
							allFilesAfterDownloading.add(listOfFilesAfterDownloading[i]);
							LOG.info("File " + listOfFilesAfterDownloading[i].getName());
						}
					}
				}
				File lastModifiedFile = allFilesAfterDownloading.get(0);
				for (int i = 1; i < allFilesAfterDownloading.size(); i++) {
					if (lastModifiedFile.lastModified() < allFilesAfterDownloading.get(i).lastModified()) {
						lastModifiedFile = allFilesAfterDownloading.get(i);
					}
				}
				LOG.info(" Last modified file is " + lastModifiedFile);
				double fileSizeInBytes = lastModifiedFile.length();
				if (fileSizeInBytes > Integer.parseInt(minSizeInBytes)) {
					stepRslt(stepResult, 1, "File  " + lastModifiedFile + " exists and size is also more than the expected size", null, null);
				} else {
					int iteratorCounter = 1;
					boolean sizeNotMatched = false;
					while (iteratorCounter != Integer.parseInt(maxIterate)) {
						Thread.sleep(Integer.parseInt(waitFor2) * 1000);
						if (fileSizeInBytes > Integer.parseInt(minSizeInBytes)) {
							stepRslt(stepResult, 1, "File  " + lastModifiedFile + " exists in the specified folder", null, null);
							sizeNotMatched = true;
							break;
						}
						iteratorCounter++;
					}
					if (!sizeNotMatched) {
						stepRslt(stepResult, 1, "File " + lastModifiedFile + " downloaded and size is less than the expected file size ", null, null);
					}
				}

			} else {
				stepRslt(stepResult, 0, "File not found in the specified folder", null, null);
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			String screen = snapShot(testCase, testCaseResult, testStep, stepResult, param, objects);
			stepRslt(stepResult, 0, "Object not found or Invalid object", null, screen);
			LOG.error("Object not found or Invalid object", e);
		} catch (Exception ex) {
			stepRslt(stepResult, 0, "Excepton occured", ex.getMessage(), null);
			LOG.error("Failed to switch between browser", ex);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("downloadFile(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Iter param.
	 * 
	 * @param testStep
	 *            the test step
	 * @return the list
	 */
	private List<String> iterParam(TestStep testStep) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("iterParam(TestStep) - start"); //$NON-NLS-1$
		}

		List<String> paramValues = new ArrayList<String>();
		List<ParamGroup> paramGroupList = testStep.getParamGroup();
		LOG.info(" ParamGroup Size " + paramGroupList.size());
		Iterator<ParamGroup> iterParamGroup = paramGroupList.iterator();
		while (iterParamGroup.hasNext()) {
			ParamGroup paramGroup = (ParamGroup) iterParamGroup.next();
			List<Param> paramList = paramGroup.getParam();
			Iterator<Param> paramItr = paramList.iterator();
			while (paramItr.hasNext()) {
				Param param = (Param) paramItr.next();
				paramValues.add(param.getTestParamData().getParamValue());
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("iterParam(TestStep) - end"); //$NON-NLS-1$
		}
		return paramValues;
	}

	/**
	 * Run apple script for file upload.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param testStepResult
	 *            the test step result
	 * @param param
	 *            the param
	 * @throws Exception
	 *             the exception
	 */
	public void runAppleScriptForFileUpload(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param)
			throws Exception {
		LOG.info("Calling run Apple Script For File upload");
		String filePathToUpload = testStep.getParamGroup().get(0).getParam().get(1).getParamName();
		String filePathToAppleScript = testStep.getParamGroup().get(0).getParam().get(0).getParamName();
		try {
			String launchCmd = "osascript " + filePathToAppleScript + " " + filePathToUpload;
			Runtime.getRuntime().exec(launchCmd);
			stepRslt(testStepResult, 1, "Apple Script for file upload is started", null, null);

		} catch (Exception ex) {
			LOG.error("runAppleScriptForFileUpload(TestCase, TestCaseResult, TestStep, TestStepResult, Param)", ex); //$NON-NLS-1$
			stepRslt(testStepResult, 0, "Error in executing apple script " + ex, null, null);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("runAppleScriptForFileUpload(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Run apple script for clicking select button.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param testStepResult
	 *            the test step result
	 * @param param
	 *            the param
	 * @throws Exception
	 *             the exception
	 */
	public void runAppleScriptForClickingSelectButton(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult,
			Param param) throws Exception {
		LOG.info("Calling run Apple Script for clicking select button");
		try {
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("AppleScript");
			engine.eval("tell application \"SelectButton\" to launch");
			stepRslt(testStepResult, 1, "Apple Script for clicking select button is started", null, null);
		} catch (Exception ex) {
			LOG.error("runAppleScriptForClickingSelectButton(TestCase, TestCaseResult, TestStep, TestStepResult, Param)", ex); //$NON-NLS-1$
			stepRslt(testStepResult, 0, "Error in executing apple script " + ex, null, null);
		}
	}

	/**
	 * Run apple script for clicking link.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param testStepResult
	 *            the test step result
	 * @param param
	 *            the param
	 * @throws Exception
	 *             the exception
	 */
	public void runAppleScriptForClickingLink(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult,
			Param param) throws Exception {
		LOG.info("Calling run Apple Script for clicking link");
		try {
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("AppleScript");
			engine.eval("tell application \"WhereDoIFindLink\" to launch");
			stepRslt(testStepResult, 1, "Apple Script for clicking link is started", null, null);
		} catch (Exception ex) {
			LOG.error("runAppleScriptForClickingLink(TestCase, TestCaseResult, TestStep, TestStepResult, Param)", ex); //$NON-NLS-1$
			stepRslt(testStepResult, 0, "Error in executing apple script " + ex, null, null);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("runAppleScriptForClickingLink(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Run apple script for clicking upload button.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param testStepResult
	 *            the test step result
	 * @param param
	 *            the param
	 * @throws Exception
	 *             the exception
	 */
	public void runAppleScriptForClickingUploadButton(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult,
			Param param) throws Exception {
		LOG.info("Calling run Apple Script for clicking upload button");
		try {
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("AppleScript");
			engine.eval("tell application \"UploadButton\" to launch");
			stepRslt(testStepResult, 1, "Apple Script for clicking upload button is started", null, null);
		} catch (Exception ex) {
			LOG.error("runAppleScriptForClickingUploadButton(TestCase, TestCaseResult, TestStep, TestStepResult, Param)", ex); //$NON-NLS-1$
			stepRslt(testStepResult, 0, "Error in executing apple script " + ex, null, null);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("runAppleScriptForClickingUploadButton(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Run apple script for login.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param testStepResult
	 *            the test step result
	 * @param param
	 *            the param
	 * @throws Exception
	 *             the exception
	 */
	public void runAppleScriptForLogin(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param)
			throws Exception {
		LOG.info("Calling run Apple Script For Login action");
		String scriptPath = null;
		try {
			scriptPath = testStep.getParamGroup().get(0).getParam().get(1).getParamName();
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("AppleScript");

			engine.eval("tell application \"Login\" to launch");
			stepRslt(testStepResult, 1, "Apple Script for Login is started", null, null);
		} catch (Exception ex) {
			LOG.error("runAppleScriptForLogin(TestCase, TestCaseResult, TestStep, TestStepResult, Param)", ex); //$NON-NLS-1$
			stepRslt(testStepResult, 0, "Error in executing apple script", null, null);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("runAppleScriptForLogin(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Run apple script for alert.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param testStepResult
	 *            the test step result
	 * @param param
	 *            the param
	 * @throws Exception
	 *             the exception
	 */
	public void runAppleScriptForAlert(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param)
			throws Exception {
		LOG.info(" Calling run apple script action");
		String action = testStep.getParamGroup().get(0).getParam().get(1).getParamName();
		try {
			ScriptEngineManager mgr = new ScriptEngineManager();
			LOG.info("Action  " + action);
			ScriptEngine engine = mgr.getEngineByName("AppleScript");
			if ("ok".equals(action)) {
				engine.eval("tell application \"ApplescriptToClickOk\" to launch");
			} else if ("cancel".equals(action)) {
				engine.eval("tell application \"ApplescriptToClickCancel\" to launch");
			}
			stepRslt(testStepResult, 1, "Apple Script execution is started", null, null);
		} catch (Exception ex) {
			LOG.error("runAppleScriptForAlert(TestCase, TestCaseResult, TestStep, TestStepResult, Param)", ex); //$NON-NLS-1$
			stepRslt(testStepResult, 2, "Error in executing apple script", null, null);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("runAppleScriptForAlert(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the config path.
	 * 
	 * @return the config path
	 */
	public String getConfigPath() {
		return null;
	}

	/**
	 * Sets the config path.
	 * 
	 * @param configPath
	 *            the new config path
	 */
	public void setConfigPath(String configPath) {
	}

	/**
	 * If this works sometimes and not at other times : need to make sure
	 * control already has focus and currently browser window with your page has
	 * focus, this is a OS level key event so which ever window has focus will
	 * get the tab key.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param testStepResult
	 *            the test step result
	 * @param param
	 *            the param
	 * @throws AWTException
	 *             the aWT exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void osTabOut(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param)
			throws AWTException, InterruptedException {
		LOG.info("calling tab out action 1 second sleep and then 10 seconds wait for object load");
		if (RobotHelper.tabOut()) {
			stepRslt(testStepResult, 1, "tabed out", null, null);
			LOG.info("Tabbed out in tabOutAction");
		} else {
			stepRslt(testStepResult, 0, "Failure to tabout", null, null);
			LOG.info("Failure to tabout");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("osTabOut(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * If this works sometimes and not at other times : need to make sure
	 * control already has focus and currently browser window with your page has
	 * focus, this is a OS level key event so which ever window has focus will
	 * get the tab key.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param testStepResult
	 *            the test step result
	 * @param param
	 *            the param
	 * @throws AWTException
	 *             the aWT exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void osKeys(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param)
			throws AWTException, InterruptedException {
		LOG.debug("osKeys");
		int delay = 150;
		String keys = testStep.getParamGroup().get(0).getParam().get(0).getParamName();
		if (RobotHelper.sendKeys(keys, delay)) {
			stepRslt(testStepResult, 1, "osKeys", null, null);
			LOG.debug("Tabbed out in tabOutAction");
		} else {
			stepRslt(testStepResult, 0, "Failure osKeys", keys, null);
			LOG.info("Failure osKeys");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("osKeys(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Execute a OS dependent process.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param testStepResult
	 *            the test step result
	 * @param param
	 *            the param
	 * @throws AWTException
	 *             the aWT exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void exec(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) throws AWTException,
			InterruptedException {
		LOG.debug("exec");
		String cmd = testStep.getParamGroup().get(0).getParam().get(0).getParamName();
		try {
			LOG.trace("cmd " + cmd);
			ProcessBuilder pb = new ProcessBuilder(cmd);
			Process proc = pb.start();
			InputStream is = proc.getInputStream();
			ReadStreamAsync rsaIn = new ReadStreamAsync(is, TfwPools.DEFAULT_POOL, false, 1, false, null);
			InputStream isE = proc.getErrorStream();
			ReadStreamAsync rsaErr = new ReadStreamAsync(isE, "default", false, 1, false, null);
			stepRslt(testStepResult, 1, "osKeys", null, null);
			LOG.trace("exec done");
			proc.waitFor();
		} catch (Exception e) {
			final String msg = "exec err :" + e + ", " + cmd;
			stepFillDetail(testStepResult, "exec was unsuccessfull", msg, e);
			LOG.warn(msg, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("exec(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Step fill detail.
	 * 
	 * @param stepResult
	 *            the step result
	 * @param comment
	 *            the comment
	 * @param msg
	 *            the msg
	 * @param e
	 *            the e
	 */
	public void stepFillDetail(TestStepResult stepResult, String comment, String msg, Throwable e) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("stepFillDetail(TestStepResult, String, String, Throwable) - start"); //$NON-NLS-1$
		}

		stepResult.setComment(comment);
		StringBuilder sb = new StringBuilder();
		if (msg == null) {
			msg = "";
		}
		if (comment == null) {
			sb.append(msg);
		} else {
			sb.append(comment);
			sb.append("\n");
			sb.append(msg);
		}
		if (e != null) {
			java.io.ByteArrayOutputStream bis = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(bis);
			LOG.warn(ps);
			String sq = new String(bis.toByteArray());
			sb.append("\nTrace:\n");
			sb.append(sq);
		}
		stepResult.setException(sb.toString());

		if (LOG.isDebugEnabled()) {
			LOG.debug("stepFillDetail(TestStepResult, String, String, Throwable) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the version.
	 * 
	 * @return the version
	 */
	public static String getVersion() {
		return "1.5.3";
	}

	/**
	 * Gets the win handle before.
	 * 
	 * @return the win handle before
	 */
	public String getWinHandleBefore() {
		return winHandleBefore;
	}

	/**
	 * Sets the win handle before.
	 * 
	 * @param winHandleBefore
	 *            the new win handle before
	 */
	public void setWinHandleBefore(String winHandleBefore) {
		this.winHandleBefore = winHandleBefore;
	}

}