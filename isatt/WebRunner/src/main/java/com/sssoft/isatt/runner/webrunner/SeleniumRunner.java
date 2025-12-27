package com.sssoft.isatt.runner.webrunner;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sssoft.isatt.data.pojo.Scheduler;
import com.sssoft.isatt.data.pojo.def.Runner;
import com.sssoft.isatt.data.pojo.input.ObjectGroup;
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

/**
 * The Class SeleniumRunner.
 */
public class SeleniumRunner implements Runner {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(SeleniumRunner.class);
	
	/** The methods. */
	private static volatile Map<String, Method> methods = new HashMap<String, Method>(20);
	
	/** The action script. */
	ActionScript actionScript = new ActionScript();

	/**
	 * Instantiates a new selenium runner.
	 */
	public SeleniumRunner() {
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#startTestPlan(com.sssoft.isatt.data.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan)
	 */
	public void startTestPlan(Scheduler scheduler, TestPlan obj) {
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#startTestSuite(com.sssoft.isatt.data.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan, com.sssoft.isatt.data.pojo.input.TestSuite, com.sssoft.isatt.data.pojo.output.TestSuiteResult)
	 */
	public void startTestSuite(Scheduler scheduler, TestPlan obj, TestSuite testsuite, TestSuiteResult tstSmmryFromRemote) {
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#startTestCase(com.sssoft.isatt.data.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan, com.sssoft.isatt.data.pojo.input.TestSuite, com.sssoft.isatt.data.pojo.output.TestSuiteResult)
	 */
	public void startTestCase(Scheduler scheduler, TestPlan obj, TestSuite testsuite, TestSuiteResult tstSmmryFromCache) {
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#testStep(com.sssoft.isatt.data.pojo.input.TestCase, com.sssoft.isatt.data.pojo.output.TestCaseResult, com.sssoft.isatt.data.pojo.input.TestStep, com.sssoft.isatt.data.pojo.output.TestStepResult, com.sssoft.isatt.data.pojo.input.Param)
	 */
	public void testStep(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		testStepResult.setResult(0);// make it true in each method if it
		// passes
		String action = testStep.getActions().getActionName();
		Method actionMethd = null;
		Objects object = null;
		// testcaseResult.setValidationByAgent(true);
		synchronized (methods) {
			object = getObjectsForAction(testStep, param);
			if (object != null) {
				actionMethd = methods.get(action);
				if (actionMethd == null) {
					try {
						actionMethd = SeleniumRunner.this.getActionScript().getClass()
								.getMethod(action, TestCase.class, TestCaseResult.class, TestStep.class, TestStepResult.class, Param.class, Objects.class);// <String,
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
						actionMethd.invoke(SeleniumRunner.this.getActionScript(), testCase, testCaseResult, testStep, testStepResult, param, object);
					} catch (Exception e) {
						LOG.warn("Calling action Err :" + e, e);
						testStepResult.setComment("Exception occured in " + actionMethd);
						testStepResult.setException("Bad action - ERR :" + e);
					}
				}
			} else {
				LOG.info("No Objects for the TestStep");
				testStepResult.setComment("No Objects for the TestStep");
				testStepResult.setResult(0);
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the objects for action.
	 *
	 * @param testStep the test step
	 * @param param the param
	 * @return the objects for action
	 */
	private Objects getObjectsForAction(TestStep testStep, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectsForAction(TestStep, Param) - start"); //$NON-NLS-1$
		}

		Objects realObject = null;
		List<ParamGroup> paramGroupList = testStep.getParamGroup();
		if (paramGroupList.size() > 0) {
			Iterator<ParamGroup> iterParamGroup = paramGroupList.iterator();
			while (iterParamGroup.hasNext()) {
				ParamGroup paramGroup = (ParamGroup) iterParamGroup.next();
				ObjectGroup objectGroup = paramGroup.getObjectGroup();
				List<Objects> objectsList = objectGroup.getObjects();
				Iterator<Objects> iterObjects = objectsList.iterator();
				while (iterObjects.hasNext()) {
					Objects objects = (Objects) iterObjects.next();
					if (objects.getObjectID() == param.getObjects().getObjectID()) {
						realObject = objects;
						break;
					}
				}
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectsForAction(TestStep, Param) - end"); //$NON-NLS-1$
		}
		return realObject;
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#getConfigPath()
	 */
	public String getConfigPath() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#setConfigPath(java.lang.String)
	 */
	public void setConfigPath(String configPath) {
	}

	/**
	 * Gets the action script.
	 *
	 * @return the action script
	 */
	public ActionScript getActionScript() {
		return actionScript;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public static final String getVersion() {
		return "1";
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#endTestCase(com.sssoft.isatt.data.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan, com.sssoft.isatt.data.pojo.input.TestSuite, com.sssoft.isatt.data.pojo.input.TestCase, com.sssoft.isatt.data.pojo.output.TestCaseResult, com.sssoft.isatt.data.pojo.output.TestStepResult)
	 */
	public void endTestCase(Scheduler scheduler, TestPlan plan, TestSuite testsuite, TestCase testCase, TestCaseResult testcaseResult,
			TestStepResult testStepResult) {
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.Runner#clone2()
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
