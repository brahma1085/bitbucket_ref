package com.exilant.tfw.raft;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.python.util.PythonInterpreter;

import com.exilant.tfw.pojo.Scheduler;
import com.exilant.tfw.pojo.def.Runner;
import com.exilant.tfw.pojo.input.ObjectGroup;
import com.exilant.tfw.pojo.input.Objects;
import com.exilant.tfw.pojo.input.Param;
import com.exilant.tfw.pojo.input.ParamGroup;
import com.exilant.tfw.pojo.input.TestCase;
import com.exilant.tfw.pojo.input.TestPlan;
import com.exilant.tfw.pojo.input.TestStep;
import com.exilant.tfw.pojo.input.TestSuite;
import com.exilant.tfw.pojo.output.TestCaseResult;
import com.exilant.tfw.pojo.output.TestStepResult;
import com.exilant.tfw.pojo.output.TestSuiteResult;

/**
 * The Class RaftRunner.
 */
public class RaftRunner implements Runner {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(RaftRunner.class);
	
	/** The methods. */
	private static volatile Map<String, Method> methods = new HashMap<String, Method>(20);
	
	/** The pyintr. */
	PythonInterpreter pyintr = new PythonInterpreter();

	/** The rnt. */
	Runtime rnt = Runtime.getRuntime();
	
	/** The hm dir. */
	String hmDir = System.getProperty("user.home");

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestPlan(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan)
	 */
	public void startTestPlan(Scheduler scheduler, TestPlan testPlan) {
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestSuite(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.output.TestSuiteResult)
	 */
	public void startTestSuite(Scheduler scheduler, TestPlan testPlan, TestSuite testSuite, TestSuiteResult testSuiteResult) {
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestCase(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.output.TestSuiteResult)
	 */
	public void startTestCase(Scheduler scheduler, TestPlan testPlan, TestSuite testSuite, TestSuiteResult testSuiteResult) {
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#endTestCase(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.input.TestCase, com.exilant.tfw.pojo.output.TestCaseResult, com.exilant.tfw.pojo.output.TestStepResult)
	 */
	public void endTestCase(Scheduler scheduler, TestPlan testPlan, TestSuite testSuite, TestCase testCase, TestCaseResult testCaseResult,
			TestStepResult testStepResult) {
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#testStep(com.exilant.tfw.pojo.input.TestCase, com.exilant.tfw.pojo.output.TestCaseResult, com.exilant.tfw.pojo.input.TestStep, com.exilant.tfw.pojo.output.TestStepResult, com.exilant.tfw.pojo.input.Param)
	 */
	public void testStep(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String action = testStep.getActions().getActionName();
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
								Param.class, Objects.class);// <String,
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

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.Runner#clone2()
	 */
	public Runner clone2() throws CloneNotSupportedException {
		return null;
	}

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
			this.rnt.exec(this.hmDir + "/tst_Scripts/RaftDriver.py &");
			Thread.sleep(30000);
		} catch (IOException e) {
			LOG.error(" File not found  " + this.hmDir + "/tst_Scripts/RaftDriver.py", e);
		}

		try {
			System.setProperty("python.path", this.hmDir + "/squishUtils/pythonmodules/");
			this.pyintr.exec("import sys");
			this.pyintr.exec("sys.path.append(\"" + this.hmDir + "/squishUtils/serpent-1.4\")");
			this.pyintr.exec("sys.path.append(\"" + this.hmDir + "/squishUtils/Pyro4-4.22/src\")");
			this.pyintr.exec("import Pyro4");
			this.pyintr.exec("rftListenerProxy = Pyro4.Proxy(\"PYRONAME:tfw.rftListener\")");
			RaftUtils.stepRslt(testStepResult, 1, "Started the Raft runner Successfully", null);
		} catch (Exception e) {
			LOG.error("StartApp(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects)", e); //$NON-NLS-1$

			RaftUtils.stepRslt(testStepResult, 0, "Failure to start the Raft runner", null);
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

		try {
			this.rnt.exec("killall python");
			this.rnt.exec("killall Python");
			this.rnt.exec("killall raft");
			RaftUtils.stepRslt(testStepResult, 1, "Killed all the process", null);
			LOG.info("Killed all the process End of execution");
		} catch (IOException e) {
			LOG.error("StopApp(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects)", e); //$NON-NLS-1$

			RaftUtils.stepRslt(testStepResult, 0, "Failed to kill all the process", null);
		}

		LOG.info("End of execution");
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
	 */
	public void pythonScript(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param1,
			Objects objects) {
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
			String param = object + "|-" + inputParam + "|-" + "N" + "|-" + "No UDEM" + "|-" + "PASS";
			Object pyo = pyintr.eval("rftListenerProxy.callActionScript(\"" + casename + "\",\"" + action + "\"" + ",\"" + param + "\")");
			LOG.info("Result from action     " + pyo.toString());
			result = pyo.toString();
			StringTokenizer stringTokenizer = new StringTokenizer(result, "|-");
			while (stringTokenizer.hasMoreTokens()) {
				String string = stringTokenizer.nextToken();
				resultList.add(string);
			}
			if ("Next".equals(resultList.get(0)))
				RaftUtils.stepRslt(testStepResult, 1, resultList.get(3), null);
			else
				RaftUtils.stepRslt(testStepResult, 0, resultList.get(3), null);

		} catch (Exception e) {
			LOG.error("pythonScript(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects)", e); //$NON-NLS-1$

			RaftUtils.stepRslt(testStepResult, 0, "Error occured " + e, null);
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