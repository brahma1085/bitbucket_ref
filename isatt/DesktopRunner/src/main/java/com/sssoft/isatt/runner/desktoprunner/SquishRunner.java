package com.sssoft.isatt.runner.desktoprunner;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.python.util.PythonInterpreter;

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
 * The Class SquishRunner.
 */
public class SquishRunner implements Runner {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(SquishRunner.class);
	
	/** The pyintr. */
	PythonInterpreter pyintr = new PythonInterpreter();
	
	/** The methods. */
	private static volatile Map<String, Method> methods = new HashMap<String, Method>(20);

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#startTestPlan(com.sssoft.isatt.data.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan)
	 */
	public void startTestPlan(Scheduler scheduler, TestPlan plan) {
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#startTestSuite(com.sssoft.isatt.data.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan, com.sssoft.isatt.data.pojo.input.TestSuite, com.sssoft.isatt.data.pojo.output.TestSuiteResult)
	 */
	public void startTestSuite(Scheduler scheduler, TestPlan plan, TestSuite suite, TestSuiteResult tstSmmryFromRemote) {
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#startTestCase(com.sssoft.isatt.data.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan, com.sssoft.isatt.data.pojo.input.TestSuite, com.sssoft.isatt.data.pojo.output.TestSuiteResult)
	 */
	public void startTestCase(Scheduler scheduler, TestPlan plan, TestSuite suite, TestSuiteResult suiteSumamryResult) {
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#testStep(com.sssoft.isatt.data.pojo.input.TestCase, com.sssoft.isatt.data.pojo.output.TestCaseResult, com.sssoft.isatt.data.pojo.input.TestStep, com.sssoft.isatt.data.pojo.output.TestStepResult, com.sssoft.isatt.data.pojo.input.Param)
	 */
	public void testStep(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String casename = testCase.getCaseName();
		String action = testStep.getActions().getActionName();
		String aaa = action.substring(0, 1).toUpperCase();
		action = action.replace(action.substring(0, 1), aaa);
		Method actionMethd = null;
		Objects object = null;
		String actionName = null;
		synchronized (methods) {
			object = getObjectsForAction(testStep, param);
			if (object != null) {
				actionMethd = methods.get(action);
				actionName = action;
				if (!("StartApp".equals(action) || "StopApp".equals(action))) {
					actionName = "pythonScript";
				}
				if (actionMethd == null) {
					try {
						actionMethd = this.getClass().getMethod(actionName, TestCase.class, TestCaseResult.class, TestStep.class, TestStepResult.class,
								Param.class, Objects.class);
						methods.put(actionName, actionMethd);
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
						actionMethd.invoke(this, testCase, testCaseResult, testStep, testStepResult, param, object);
					} catch (Exception e) {
						LOG.warn("Calling action Err :" + e, e);
						testStepResult.setComment("Bad action - ERR :" + e);
					}
				}
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#endTestCase(com.sssoft.isatt.data.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan, com.sssoft.isatt.data.pojo.input.TestSuite, com.sssoft.isatt.data.pojo.input.TestCase, com.sssoft.isatt.data.pojo.output.TestCaseResult, com.sssoft.isatt.data.pojo.output.TestStepResult)
	 */
	public void endTestCase(Scheduler scheduler, TestPlan plan, TestSuite suite, TestCase testCase, TestCaseResult caseResult, TestStepResult lastStep) {
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

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.Runner#clone2()
	 */
	public Runner clone2() throws CloneNotSupportedException {
		return null;
	}

	/** The rnt. */
	Runtime rnt = Runtime.getRuntime();
	
	/** The hm dir. */
	String hmDir = System.getProperty("user.home");

	/**
	 * Start app.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param1 the param1
	 * @param objects the objects
	 * @throws InterruptedException the interrupted exception
	 */
	public void StartApp(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param1, Objects objects)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("StartApp(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		try {
			this.rnt.exec(this.hmDir + "/tst_Scripts/SqDriver.py &");
			Thread.sleep(15000);
		} catch (FileNotFoundException fne) {
			LOG.error(" File not found  " + this.hmDir + "/tst_Scripts/SqDriver.py", fne);
		} catch (Exception e) {
			LOG.error("Exception occured " + e, e);
		}
		try {
			System.setProperty("python.path", this.hmDir + "/squishUtils/pythonmodules/");
			this.pyintr.exec("import sys");
			this.pyintr.exec("sys.path.append(\"" + this.hmDir + "/squishUtils/serpent-1.4\")");
			this.pyintr.exec("sys.path.append(\"" + this.hmDir + "/squishUtils/Pyro4-4.22/src\")");
			this.pyintr.exec("import Pyro4");
			this.pyintr.exec("sqListenerProxy = Pyro4.Proxy(\"PYRONAME:tfw.sqListener\")");
			SquishUtls.stepRslt(testStepResult, 1, "AUT Launched Successfully", null);
		} catch (Exception e) {
			LOG.error(" Exception occured  " + e, e);
			SquishUtls.stepRslt(testStepResult, 0, "Failure to launch AUT", null);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("StartApp(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Stop app.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param1 the param1
	 * @param objects the objects
	 */
	public void StopApp(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param1, Objects objects) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("StopApp(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		String result = null;
		List<String> resultList = new ArrayList<String>();
		try {
			String param = "NoSquishobject|-Positive|-Command+q|-N|-No UDEM|-PASS";
			Object pyo = this.pyintr.eval("sqListenerProxy.callActionScript(\"StopApp\",\"PressKey\",\"" + param + "\")");

			LOG.info(pyo.toString());
			result = pyo.toString();
			StringTokenizer stringTokenizer = new StringTokenizer(result, "|-");
			while (stringTokenizer.hasMoreTokens()) {
				String string = stringTokenizer.nextToken();
				resultList.add(string);
			}
			if ("PASS".equals((String) resultList.get(2)))
				SquishUtls.stepRslt(testStepResult, 1, (String) resultList.get(3), null);
			else {
				SquishUtls.stepRslt(testStepResult, 0, (String) resultList.get(3), null);
			}
			Thread.sleep(5000L);

			this.rnt.exec("killall python");
			this.rnt.exec("killall Python");
			this.rnt.exec("killall _squishserver");

			LOG.info("Killed all the process End of execution");
		} catch (Exception e) {
			LOG.error(" Exception occured  " + e, e);
			SquishUtls.stepRslt(testStepResult, 0, "Failure to launch AUT", null);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("StopApp(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Python script.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param1 the param1
	 * @param objects the objects
	 * @throws Throwable the throwable
	 */
	public void pythonScript(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param1,
			Objects objects) throws Throwable {
		if (LOG.isDebugEnabled()) {
			LOG.debug("pythonScript(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - start"); //$NON-NLS-1$
		}

		String result = null;
		List<String> resultList = new ArrayList<String>();
		try {
			String casename = testCase.getCaseName();

			String action = testStep.getActions().getActionName();
			String object = objects.getIdentifier();
			String inputParam = param1.getTestParamData().getParamValue();
			LOG.info("Calling action " + action);
			String param = object + "|-" + "Positive" + "|-" + inputParam + "|-" + "N" + "|-" + "No UDEM" + "|-" + "PASS";
			Object pyo = pyintr.eval("sqListenerProxy.callActionScript(\"" + casename + "\",\"" + action + "\"" + ",\"" + param + "\")");
			LOG.info("Result from action     " + pyo.toString());
			result = pyo.toString();
			StringTokenizer stringTokenizer = new StringTokenizer(result, "|-");
			while (stringTokenizer.hasMoreTokens()) {
				String string = stringTokenizer.nextToken();
				resultList.add(string);
			}
			if ("PASS".equals(resultList.get(2))) {
				SquishUtls.stepRslt(testStepResult, 1, resultList.get(3), null);
			} else {
				SquishUtls.stepRslt(testStepResult, 0, resultList.get(3), null);
			}
		} catch (Exception e) {
			LOG.error(" Exception occured  " + e, e);
			SquishUtls.stepRslt(testStepResult, 0, "Error occured " + e, null);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("pythonScript(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects) - end"); //$NON-NLS-1$
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

}