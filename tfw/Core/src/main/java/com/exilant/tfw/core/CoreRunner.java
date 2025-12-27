package com.exilant.tfw.core;

import java.awt.AWTException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.log4j.Logger;

import com.exilant.tfw.pojo.Scheduler;
import com.exilant.tfw.pojo.def.Runner;
import com.exilant.tfw.pojo.input.Param;
import com.exilant.tfw.pojo.input.TestCase;
import com.exilant.tfw.pojo.input.TestPlan;
import com.exilant.tfw.pojo.input.TestStep;
import com.exilant.tfw.pojo.input.TestSuite;
import com.exilant.tfw.pojo.output.TestCaseResult;
import com.exilant.tfw.pojo.output.TestStepResult;
import com.exilant.tfw.pojo.output.TestSuiteResult;
import com.exilant.tfw.util.RobotHelper;
import com.exilant.tfw.util.threads.TfwPools;
import com.exilant.tfw.utils.io.ReadStreamAsync;

/**
 * The Class CoreRunner.
 */
public class CoreRunner implements Runner {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(CoreRunner.class);
	
	/** The methods. */
	private static volatile Map<String, Method> methods = new HashMap<String, Method>(20);

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestPlan(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan)
	 */
	public void startTestPlan(Scheduler scheduler, TestPlan testPlan) {
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestSuite(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.output.TestSuiteResult)
	 */
	public void startTestSuite(Scheduler scheduler, TestPlan plan, TestSuite suite, TestSuiteResult tstSmmryFromRemote) {
		LOG.debug("not implemnted CoreRunner Plan run: " + plan.getPlanName());

		if (LOG.isDebugEnabled()) {
			LOG.debug("startTestSuite(Scheduler, TestPlan, TestSuite, TestSuiteResult) - end"); //$NON-NLS-1$
		}
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestCase(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.output.TestSuiteResult)
	 */
	public void startTestCase(Scheduler scheduler, TestPlan obj, TestSuite suite, TestSuiteResult tstSmmryFromCache) {
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#testStep(com.exilant.tfw.pojo.input.TestCase, com.exilant.tfw.pojo.output.TestCaseResult, com.exilant.tfw.pojo.input.TestStep, com.exilant.tfw.pojo.output.TestStepResult, com.exilant.tfw.pojo.input.Param)
	 */
	public void testStep(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		testStepResult.setResult(0);
		String action = testStep.getActions().getActionName();
		Method actionMethd = null;
		synchronized (methods) {
			actionMethd = methods.get(action);
			if (actionMethd == null) {
				try {
					actionMethd = this.getClass().getMethod(action, Scheduler.class, TestPlan.class, TestSuite.class, TestCase.class,
							TestCaseResult.class, TestStep.class, TestStepResult.class, TestSuiteResult.class);// <String,
					methods.put(action, actionMethd);
				} catch (SecurityException e) {
					LOG.warn("SE Err " + e, e);
					testStepResult.setComment("Java security, bad setup ERR :" + e);
					testStepResult.setException("Could not process action :[" + action + "] java error.");
				} catch (NoSuchMethodException e) {
					LOG.warn("No method Err :" + e, e);
					testStepResult.setComment("Bad action - no corresponding impl ERR :" + e);
					testStepResult.setException("Could not process action :[" + action + "] not implemented this.");
				}
			}
			if (actionMethd != null) {
				try {
					LOG.info("Action :" + action);
					actionMethd.invoke(this, testCase, testCaseResult, testStep, testStepResult, null);
				} catch (Exception e) {
					LOG.warn("Calling action Err :" + e, e);
					testStepResult.setComment("Bad action - ERR :" + e);
				}
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#endTestCase(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.input.TestCase, com.exilant.tfw.pojo.output.TestCaseResult, com.exilant.tfw.pojo.output.TestStepResult)
	 */
	public void endTestCase(Scheduler scheduler, TestPlan obj, TestSuite suite, TestCase testCase, TestCaseResult caseResult, TestStepResult testStep) {
	}

	/**
	 * Key press.
	 *
	 * @param scheduler the scheduler
	 * @param plan the plan
	 * @param suite the suite
	 * @param testCase the test case
	 * @param caseResult the case result
	 * @param step the step
	 * @param stepResult the step result
	 * @param tstSmmryFromCache the tst smmry from cache
	 */
	public void keyPress(Scheduler scheduler, TestPlan plan, TestSuite suite, TestCase testCase, TestCaseResult caseResult, TestStep step,
			TestStepResult stepResult, TestSuiteResult tstSmmryFromCache) {

	}

	/**
	 * Snooze.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 */
	public void snooze(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		LOG.debug("Calling Snooze action");
		int delay = 1000;
		try {
			delay = Integer.parseInt(testStep.getParamGroup().get(0).getParam().get(0).getParamName());
			Thread.sleep(delay);
			TfwCoreUtls.stepRslt(testStepResult, 1, "snoozed for " + delay + " secs", null);
		} catch (InterruptedException e) {
			LOG.warn("snooze Error : " + e, e);
			TfwCoreUtls.stepRslt(testStepResult, 0, "Interrupt occurred while snooze", null);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("snooze(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Run apple script for file upload.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 * @throws Exception the exception
	 */
	public void runAppleScriptForFileUpload(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param)
			throws Exception {
		LOG.info("Calling run Apple Script For File upload");
		String filePathToUpload = testStep.getParamGroup().get(0).getParam().get(1).getParamName();
		String filePathToAppleScript = testStep.getParamGroup().get(0).getParam().get(0).getParamName();
		try {
			String launchCmd = "osascript " + filePathToAppleScript + " " + filePathToUpload;
			Runtime.getRuntime().exec(launchCmd);
			TfwCoreUtls.stepRslt(testStepResult, 1, "Apple Script for file upload is started", null);
		}

		catch (Exception ex) {
			LOG.error("runAppleScriptForFileUpload(TestCase, TestCaseResult, TestStep, TestStepResult, Param)", ex); //$NON-NLS-1$

			TfwCoreUtls.stepRslt(testStepResult, 0, "Error in executing apple script " + ex, null);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("runAppleScriptForFileUpload(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Run apple script for clicking select button.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 * @throws Exception the exception
	 */
	public void runAppleScriptForClickingSelectButton(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult,
			Param param) throws Exception {
		LOG.info("Calling run Apple Script for clicking select button");
		try {
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("AppleScript");
			engine.eval("tell application \"SelectButton\" to launch");
			TfwCoreUtls.stepRslt(testStepResult, 1, "Apple Script for clicking select button is started", null);
		}

		catch (Exception ex) {
			LOG.error("runAppleScriptForClickingSelectButton(TestCase, TestCaseResult, TestStep, TestStepResult, Param)", ex); //$NON-NLS-1$

			TfwCoreUtls.stepRslt(testStepResult, 0, "Error in executing apple script " + ex, null);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("runAppleScriptForClickingSelectButton(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Run apple script for clicking link.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 * @throws Exception the exception
	 */
	public void runAppleScriptForClickingLink(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult,
			Param param) throws Exception {
		LOG.info("Calling run Apple Script for clicking link");
		try {
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("AppleScript");
			engine.eval("tell application \"WhereDoIFindLink\" to launch");
			TfwCoreUtls.stepRslt(testStepResult, 1, "Apple Script for clicking link is started", null);
		}

		catch (Exception ex) {
			LOG.error("runAppleScriptForClickingLink(TestCase, TestCaseResult, TestStep, TestStepResult, Param)", ex); //$NON-NLS-1$

			TfwCoreUtls.stepRslt(testStepResult, 0, "Error in executing apple script " + ex, null);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("runAppleScriptForClickingLink(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Run apple script for clicking upload button.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 * @throws Exception the exception
	 */
	public void runAppleScriptForClickingUploadButton(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult,
			Param param) throws Exception {
		LOG.info("Calling run Apple Script for clicking upload button");
		try {
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("AppleScript");
			engine.eval("tell application \"UploadButton\" to launch");
			TfwCoreUtls.stepRslt(testStepResult, 1, "Apple Script for clicking upload button is started", null);
		}

		catch (Exception ex) {
			LOG.error("runAppleScriptForClickingUploadButton(TestCase, TestCaseResult, TestStep, TestStepResult, Param)", ex); //$NON-NLS-1$

			TfwCoreUtls.stepRslt(testStepResult, 0, "Error in executing apple script " + ex, null);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("runAppleScriptForClickingUploadButton(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Run apple script for login.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 * @throws Exception the exception
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
			TfwCoreUtls.stepRslt(testStepResult, 1, "Apple Script for Login is started", null);
		}

		catch (Exception ex) {
			LOG.error("runAppleScriptForLogin(TestCase, TestCaseResult, TestStep, TestStepResult, Param)", ex); //$NON-NLS-1$

			TfwCoreUtls.stepRslt(testStepResult, 0, "Error in executing apple script", null);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("runAppleScriptForLogin(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Run apple script for alert.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 * @throws Exception the exception
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
			TfwCoreUtls.stepRslt(testStepResult, 1, "Apple Script execution is started", null);
		}

		catch (Exception ex) {
			LOG.error("runAppleScriptForAlert(TestCase, TestCaseResult, TestStep, TestStepResult, Param)", ex); //$NON-NLS-1$

			TfwCoreUtls.stepRslt(testStepResult, 2, "Error in executing apple script", null);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("runAppleScriptForAlert(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#getConfigPath()
	 */
	public String getConfigPath() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#setConfigPath(java.lang.String)
	 */
	public void setConfigPath(String configPath) {
	}

	/**
	 * If this works sometimes and not at other times : need to make sure
	 * control already has focus and currently browser window with your page has
	 * focus, this is a OS level key event so which ever window has focus will
	 * get the tab key.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 * @throws AWTException the aWT exception
	 * @throws InterruptedException the interrupted exception
	 */
	public void osTabOut(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param)
			throws AWTException, InterruptedException {
		LOG.info("calling tab out action 1 second sleep and then 10 seconds wait for object load");
		if (RobotHelper.tabOut()) {
			TfwCoreUtls.stepRslt(testStepResult, 1, "tabed out", null);
			LOG.info("Tabbed out in tabOutAction");
		} else {
			TfwCoreUtls.stepRslt(testStepResult, 0, "Failure to tabout", null);
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
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 * @throws AWTException the aWT exception
	 * @throws InterruptedException the interrupted exception
	 */
	public void osKeys(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param)
			throws AWTException, InterruptedException {
		LOG.debug("osKeys");
		int delay = 150;
		String keys = testStep.getParamGroup().get(0).getParam().get(0).getParamName();
		if (RobotHelper.sendKeys(keys, delay)) {
			TfwCoreUtls.stepRslt(testStepResult, 1, "osKeys", null);
			LOG.debug("Tabbed out in tabOutAction");
		} else {
			TfwCoreUtls.stepRslt(testStepResult, 0, "Failure osKeys", keys);
			LOG.info("Failure osKeys");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("osKeys(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Execute a OS dependent process.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 * @throws AWTException the aWT exception
	 * @throws InterruptedException the interrupted exception
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
			TfwCoreUtls.stepRslt(testStepResult, 1, "osKeys", null);
			LOG.trace("exec done");
			proc.waitFor();
		} catch (Exception e) {
			final String msg = "exec err :" + e + ", " + cmd;
			TfwCoreUtls.stepFillDetail(testStepResult, "exec was unsuccessfull", msg, e);
			LOG.warn(msg, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("exec(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.Runner#clone2()
	 */
	public Runner clone2() throws CloneNotSupportedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("clone2() - start"); //$NON-NLS-1$
		}

		Runner returnRunner = (Runner) clone();
		if (LOG.isDebugEnabled()) {
			LOG.debug("clone2() - end"); //$NON-NLS-1$
		}
		return returnRunner;
	}

}
