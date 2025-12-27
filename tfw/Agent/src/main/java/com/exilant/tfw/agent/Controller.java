package com.exilant.tfw.agent;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.exilant.tfw.bean.UtlConf;
import com.exilant.tfw.excelreader.ReadReplacementStrings;
import com.exilant.tfw.pojo.Scheduler;
import com.exilant.tfw.pojo.def.AgentConfiguration;
import com.exilant.tfw.pojo.def.AgentInterface;
import com.exilant.tfw.pojo.def.AgentRunResult;
import com.exilant.tfw.pojo.def.ReplacementString;
import com.exilant.tfw.pojo.def.RunResult;
import com.exilant.tfw.pojo.def.Runner;
import com.exilant.tfw.pojo.input.Param;
import com.exilant.tfw.pojo.input.TestCase;
import com.exilant.tfw.pojo.input.TestParamData;
import com.exilant.tfw.pojo.input.TestPlan;
import com.exilant.tfw.pojo.input.TestStep;
import com.exilant.tfw.pojo.input.TestSuite;
import com.exilant.tfw.pojo.output.TaskResult;
import com.exilant.tfw.pojo.output.TestCaseResult;
import com.exilant.tfw.pojo.output.TestStepResult;
import com.exilant.tfw.pojo.output.TestSuiteResult;
import com.exilant.tfw.util.CipherUtil;
import com.exilant.tfw.util.LangUtils;
import com.exilant.tfw.util.http.HttpData;
import com.exilant.tfw.util.http.NetSend;

/**
 * The Class Controller.
 */
public class Controller implements AgentInterface {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(Controller.class);

	/** The app clear key. */
	private static String appClearKey;

	/** The app ciper. */
	private static CipherUtil appCiper;

	/** The url to. */
	private static String urlTo = "http://localhost:8080/tfwCore/TestStepGet.jsp";
	
	/** The options. */
	private static int options = 0;
	
	/** The h dat. */
	private static HttpData hDat = new HttpData(urlTo, options, null);
	
	/** The agent runner id. */
	private static String agentRunnerId = System.getProperty("Agent.self.id", "1");

	/** The runners. */
	private Map<String, Runner> runners = new HashMap<String, Runner>(2, 1);

	/** The current app. */
	private BigInteger currentApp = null;
	
	/** The current func. */
	private BigInteger currentFunc = null;

	/** key made by core jsut before. */
	// private Map<String, TestPlan> runningPlans = new HashMap<String,
	// TestPlan>();

	private static boolean mockRunner = false;

	/** The d cnt. */
	private int dCnt;

	/**
	 * Refresh cipher.
	 */
	public static void refreshCipher() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("refreshCipher() - start"); //$NON-NLS-1$
		}

		String appEncKey = UtlConf.getProperty("appEncKey");
		try {
			Controller.setReplacementMap(ReadReplacementStrings.getReplacementString());
			appCiper = CipherUtil.getInstance();
			appClearKey = appCiper.decrypt(appEncKey);
			appCiper.encrypt(appClearKey);
		} catch (Exception e) {
			LOG.error("Error in Agent2 refresh method " + e, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("refreshCipher() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Instantiates a new controller.
	 */
	public Controller() {
		Runner runner = null;
		String className = null;
		try {
			LOG.info("aaa");
			Class c = null;
			int cnt = 1;
			String nm = "agent" + agentRunnerId + "runner" + cnt + ".name";
			String runnerName = UtlConf.getProperty(nm, null);

			LOG.info("runnerName before while   " + runnerName);
			while (runnerName != null) {
				String active = "agent" + agentRunnerId + "runner" + cnt + ".active";
				LOG.info("active runner >>" + active);

				active = UtlConf.getProperty(active, null);

				LOG.info("active runner getting from property >>" + active);
				className = "agent" + agentRunnerId + "runner" + cnt + ".className";
				className = UtlConf.getProperty(className, null);
				LOG.info("Runner class : " + className + ", active " + active + ", Sl no :" + agentRunnerId);
				if (className != null && "1".equals(active)) {
					className = className.trim();
					try {
						c = Class.forName(className);
						Object object = c.newInstance();
						LOG.info("Class name " + object.getClass());
						runner = (Runner) object;
						runners.put(runnerName, runner);
					} catch (Exception e) {
						LOG.error("Agent instance :[" + e + "] classname:" + className + "] agentName [" + runnerName + "]", e);
					}
				} else if (className == null) {
					LOG.error("Runner class not loaded. className == null");
				}
				cnt++;
				nm = "agent" + agentRunnerId + "runner" + cnt + ".name";
				runnerName = UtlConf.getProperty(nm, null);
				LOG.info("runnerName -->>" + runnerName);
				LOG.info("runners Map >>" + runners.toString());

			}
		} catch (Exception e) {
			LOG.error("Controller()", e); //$NON-NLS-1$

			LOG.log(Level.WARN, "Agent cont :" + e + "] classname:" + className + "]", e);
		}

	}

	/**
	 * Sets the core url.
	 *
	 * @param s the new core url
	 */
	public static void setCoreUrl(String s) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("setCoreUrl(String) - start"); //$NON-NLS-1$
		}

		hDat.setUrl(s);

		if (LOG.isDebugEnabled()) {
			LOG.debug("setCoreUrl(String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the core url.
	 *
	 * @return the core url
	 */
	public static HttpData getCoreUrl() {
		return hDat;
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestPlan(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan)
	 */
	public void startTestPlan(Scheduler scheduler, TestPlan testPlan) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("startTestPlan(Scheduler, TestPlan) - start"); //$NON-NLS-1$
		}

		BigInteger planAppId = null;
		if (currentApp == null) {
			currentApp = planAppId;
		}
		if (currentFunc == null) {
			currentApp = planAppId;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("startTestPlan(Scheduler, TestPlan) - end"); //$NON-NLS-1$
		}
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestSuite(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.output.TestSuiteResult)
	 */
	public void startTestSuite(Scheduler scheduler, TestPlan testPlan, TestSuite testSuite, TestSuiteResult testSuiteResult) {
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestCase(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.output.TestSuiteResult)
	 */
	public void startTestCase(Scheduler scheduler, TestPlan testPlan, TestSuite suite, TestSuiteResult testSuiteResult) {
	}

	/** The replacement map. */
	private static Map<String, ReplacementString> replacementMap = new HashMap<String, ReplacementString>();

	/**
	 * Gets the replacement map.
	 *
	 * @return the replacement map
	 */
	public static Map<String, ReplacementString> getReplacementMap() {
		return replacementMap;
	}

	/**
	 * Sets the replacement map.
	 *
	 * @param replacementMap the replacement map
	 */
	public static void setReplacementMap(Map<String, ReplacementString> replacementMap) {
		Controller.replacementMap = replacementMap;
	}

	/**
	 * Replace param data.
	 *
	 * @param testStep the test step
	 */
	public void replaceParamData(TestStep testStep) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("replaceParamData(TestStep) - start"); //$NON-NLS-1$
		}

		List<TestParamData> paramDatas = testStep.getParamGroup().get(0).getTestParamData();

		List<TestParamData> testParamDatas = new ArrayList<TestParamData>();
		TestParamData paramData = new TestParamData();
		// for (TestParamData paramData : paramDatas) {
		for (int i = 0; i < paramDatas.size(); i++) {
			Param param = paramData.getParam();
			// Param param = (Param) testStep.getParamGroup().get(i).getParam();
			String actionParam = param.getParamName();
			// LOG.info("encrypted param name : " + actionParam);
			if (actionParam.contains("[[[")) {
				String strToFind = null;
				try {
					int strtIndex = actionParam.indexOf("[[[");
					int endIndex = actionParam.indexOf("]]]");
					if ((strtIndex > -1) && (endIndex > strtIndex)) {
						strToFind = actionParam.substring(strtIndex + 3, endIndex);
						Map<String, ReplacementString> map = Controller.getReplacementMap();
						ReplacementString rString = map.get(strToFind);
						if (rString.getEncrypted() == 1) {
							String decryptedVal = appCiper.decrypt(rString.getValue());
							rString.setValue(decryptedVal);
						}
						actionParam = actionParam.substring(0, strtIndex) + rString.getValue() + actionParam.substring(endIndex + 3);
					}
				} catch (Exception e) {
					LOG.error("Error occured while encryptindg the value :" + e, e);
				}
			}
			if (actionParam.length() > 0) {
				param.setParamName(actionParam);
			}
			paramData.setParam(param);
			testParamDatas.add(paramData);
		}
		testParamDatas.addAll(paramDatas);
		testStep.getParamGroup().get(0).setTestParamData(testParamDatas);
		// return testStep;

		if (LOG.isDebugEnabled()) {
			LOG.debug("replaceParamData(TestStep) - end"); //$NON-NLS-1$
		}
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#testStep(com.exilant.tfw.pojo.input.TestCase, com.exilant.tfw.pojo.output.TestCaseResult, com.exilant.tfw.pojo.input.TestStep, com.exilant.tfw.pojo.output.TestStepResult, com.exilant.tfw.pojo.input.Param)
	 */
	public void testStep(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		LOG.info("<< inside agent2 testStep >>");
		dCnt++;
		long timeStart = System.currentTimeMillis();
		LOG.info("<< isMockRunner " + isMockRunner());

		if (mockRunner) {
			LOG.info("Calling Mock");
			LOG.debug("mock run ");
			testStepResult.setComment("c " + dCnt);
			testStepResult.setResult(1);// dCnt % 6 == 0
			Random rnd = new Random();
			int t = rnd.nextInt(230) + 40;
			LangUtils.sleep(t);
		} else {
			LOG.info("Calling Real Runner");
			Runner runner = null;
			String runnerFrom = "Test Case";
			String runnerName = testCase.getRunner().getRunnerName();
			LOG.info("runnerName " + runnerName);
			if (testStep.getRunner() != null && testStep.getRunner().getRunnerName() != null && testStep.getRunner().getRunnerName().length() != 0) {
				runnerName = testStep.getRunner().getRunnerName();
				LOG.debug("Using step runner :" + runnerName + "]");
				runnerFrom = "Test Step";
			}
			runner = runners.get(runnerName);
			if (runner == null) {
				LOG.info("runner not found will put fail msg :" + runnerName + "]");
				testStepResult.setResult(0);
				testStepResult.setComment("Runner not configured : " + runnerName);
				testStepResult.setException("Runner from " + runnerFrom);
			} else {
				LOG.info("runner >>>>" + runner.toString());
				String replaceEnabled = UtlConf.getProperty("agent.replaceEnabled", "0");
				if ("1".equals(replaceEnabled)) {
					replaceParamData(testStep);
				}
				runner.testStep(testCase, testCaseResult, testStep, testStepResult, param);
				LOG.debug("result :" + testStepResult.getResult() + ", comment :" + testStepResult.getComment() + ", detailed message : "
						+ testStepResult.getException());
			}
		}
		long timeEnd = System.currentTimeMillis();
		final long tt = timeEnd - timeStart;
		testStepResult.setTimeTaken(tt);
		LOG.debug("Step time taken " + tt);
		String actionName = null;
		if (testStep != null && testStep.getActions() != null && testStep.getActions().getActionName() != null) {
			actionName = testStep.getActions().getActionName();
			if ("parameter".equals(actionName) && tt > 10) {
				LOG.trace("long action");
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * End test case.
	 *
	 * @param scheduler the scheduler
	 * @param suite the suite
	 * @param testCase the test case
	 * @param caseResult the case result
	 * @param lastStep the last step
	 */
	public void endTestCase(Scheduler scheduler, TestSuite suite, TestCase testCase, TestCaseResult caseResult, TestStepResult lastStep) {
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
	 * @see com.exilant.tfw.pojo.def.AgentInterface#setAgentConfig(com.exilant.tfw.pojo.def.AgentConfiguration)
	 */
	public void setAgentConfig(AgentConfiguration agentConfiguration) {
	}

	/**
	 * End plan.
	 *
	 * @param scheduler the scheduler
	 * @param plan the plan
	 */
	public void endPlan(Scheduler scheduler, TestPlan plan) {
	}

	/**
	 * Checks if is mock runner.
	 *
	 * @return true, if is mock runner
	 */
	public static boolean isMockRunner() {
		return mockRunner;
	}

	/**
	 * Sets the mock runner.
	 *
	 * @param mockRunner the new mock runner
	 */
	public static void setMockRunner(boolean mockRunner) {
		Controller.mockRunner = mockRunner;
	}

	/**
	 * End plan.
	 *
	 * @param agentRunResult the agent run result
	 */
	public void endPlan(AgentRunResult agentRunResult) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("endPlan(AgentRunResult) - start"); //$NON-NLS-1$
		}

		if (hDat.getUrl() != null) {
			Object rtn = null;
			try {
				rtn = NetSend.sendObj((Serializable) agentRunResult, false, hDat, "planEnd");
			} catch (Exception e) {
				LOG.warn("endPlan(AgentRunResult) - exception ignored", e); //$NON-NLS-1$
			}
			LOG.log(Level.INFO, "Send plan end " + agentRunResult.getTestPlan().getPlanName() + ", from " + agentRunResult.getAgentName() + "-"
					+ agentRunResult.getIp() + ":" + agentRunResult.getPort() + "-" + agentRunResult.getRunBy());
			LOG.log(Level.INFO, "Rtn " + rtn);
		} else {
			LOG.info("Core Url null, not posting back");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("endPlan(AgentRunResult) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * End plan detail.
	 *
	 * @param tsakResult the tsak result
	 */
	public void endPlanDetail(TaskResult tsakResult) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("endPlanDetail(TaskResult) - start"); //$NON-NLS-1$
		}

		if (hDat.getUrl() != null) {
			Object rtn = null;
			try {
				rtn = NetSend.sendObj((Serializable) tsakResult, false, hDat, "planEnd");
			} catch (Exception e) {
				LOG.warn("endPlanDetail(TaskResult) - exception ignored", e); //$NON-NLS-1$
			}
			LOG.log(Level.INFO, "Rtn " + rtn);
		} else {
			LOG.info("Core Url null, not posting back");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("endPlanDetail(TaskResult) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * End plan detailed.
	 *
	 * @param rResult the r result
	 * @param tsakResult the tsak result
	 */
	public void endPlanDetailed(RunResult rResult, TaskResult tsakResult) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("endPlanDetailed(RunResult, TaskResult) - start"); //$NON-NLS-1$
		}

		if (hDat.getUrl() != null) {
			Object rtn = null;
			try {
				rtn = NetSend.sendObjects("planEnd", false, hDat, (Serializable) tsakResult, (Serializable) rResult);
			} catch (Exception e) {
				LOG.warn("endPlanDetailed(RunResult, TaskResult) - exception ignored", e); //$NON-NLS-1$
			}
			LOG.log(Level.INFO, "Rtn " + rtn);
		} else {
			LOG.info("Core Url null, not posting back");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("endPlanDetailed(RunResult, TaskResult) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the agent runner id.
	 *
	 * @return the agent runner id
	 */
	public static String getAgentRunnerId() {
		return agentRunnerId;
	}

	/**
	 * Sets the agent runner id.
	 *
	 * @param agentRunnerId the new agent runner id
	 */
	public static void setAgentRunnerId(String agentRunnerId) {
		Controller.agentRunnerId = agentRunnerId;
	}

	/**
	 * Runners info.
	 *
	 * @return the string
	 */
	public String runnersInfo() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("runnersInfo() - start"); //$NON-NLS-1$
		}

		StringBuilder sb = new StringBuilder().append("");
		Iterator<String> it = runners.keySet().iterator();
		while (it.hasNext()) {
			sb.append(it.next());
			if (it.hasNext()) {
				sb.append(",");
			}
		}
		String returnString = sb.toString();
		if (LOG.isDebugEnabled()) {
			LOG.debug("runnersInfo() - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Fill agent data.
	 *
	 * @param agentRunResult the agent run result
	 */
	public void fillAgentData(AgentRunResult agentRunResult) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("fillAgentData(AgentRunResult) - start"); //$NON-NLS-1$
		}

		String agentName = UtlConf.getProperty("agent" + agentRunnerId + "name", "unknown");
		agentRunResult.setAgentName(agentName);
		String ip = UtlConf.getProperty("agent" + agentRunnerId + "ip", "ip.0");
		agentRunResult.setIp(ip);
		String port = UtlConf.getProperty("agent" + agentRunnerId + "port", "65122");
		try {
			agentRunResult.setPort(Integer.parseInt(port));
		} catch (NumberFormatException e) {
			LOG.info("fillAgentData - port not paseable as int :" + port + ", " + e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("fillAgentData(AgentRunResult) - end"); //$NON-NLS-1$
		}
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#endTestCase(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.input.TestCase, com.exilant.tfw.pojo.output.TestCaseResult, com.exilant.tfw.pojo.output.TestStepResult)
	 */
	public void endTestCase(Scheduler scheduler, TestPlan testPlan, TestSuite testSuite, TestCase testCase, TestCaseResult testCaseResult,
			TestStepResult testStepResult) {
	}

}
