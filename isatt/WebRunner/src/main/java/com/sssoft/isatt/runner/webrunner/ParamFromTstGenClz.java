package com.sssoft.isatt.runner.webrunner;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.sssoft.isatt.data.pojo.Scheduler;
import com.sssoft.isatt.data.pojo.def.Runner;
import com.sssoft.isatt.data.pojo.input.Objects;
import com.sssoft.isatt.data.pojo.input.Param;
import com.sssoft.isatt.data.pojo.input.TestCase;
import com.sssoft.isatt.data.pojo.input.TestPlan;
import com.sssoft.isatt.data.pojo.input.TestStep;
import com.sssoft.isatt.data.pojo.input.TestSuite;
import com.sssoft.isatt.data.pojo.output.TestCaseResult;
import com.sssoft.isatt.data.pojo.output.TestStepResult;
import com.sssoft.isatt.data.pojo.output.TestSuiteResult;

//
/**
 * The Class ParamFromTstGenClz.
 */
public class ParamFromTstGenClz implements Runner {
	//
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(ParamFromTstGenClz.class);

	/** The utl props. */
	private final com.sssoft.isatt.utils.bean.UtlProps utlProps = new com.sssoft.isatt.utils.bean.UtlProps();
	
	/** The object remote. */
	private Object objectRemote = null;
	
	/** The object mehod. */
	private Method objectMehod;

	/**
	 * Inits the.
	 *
	 * @param props the props
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void init(String props) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("init(String) - start"); //$NON-NLS-1$
		}

		if (objectRemote != null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("init(String) - end"); //$NON-NLS-1$
			}
			return;
		}
		File filePath = new File(props);
		utlProps.initialize(filePath);
		LOG.info(filePath);
		try {
			// utlProps.getProperty("clz", "") + " Method " +
			// utlProps.getProperty("method", "")
			String clzNm = utlProps.getProperty("clz", "");
			Class c = Class.forName(clzNm);
			String methodName = utlProps.getProperty("method", "");
			objectMehod = c.getMethod(methodName);
			objectRemote = c.newInstance();
			Method p = c.getMethod("setProps", Properties.class);
			p.invoke(objectRemote, utlProps.getProps());
		} catch (Exception e) {
			LOG.warn("ParamFromExternalCode init :" + e, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("init(String) - end"); //$NON-NLS-1$
		}
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#testStep(com.sssoft.isatt.data.pojo.input.TestCase, com.sssoft.isatt.data.pojo.output.TestCaseResult, com.sssoft.isatt.data.pojo.input.TestStep, com.sssoft.isatt.data.pojo.output.TestStepResult, com.sssoft.isatt.data.pojo.input.Param)
	 */
	public void testStep(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {

	}

	/**
	 * Test step.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param stepResult the step result
	 * @param param the param
	 * @param objects the objects
	 * @param putin the putin
	 */
	public void testStep(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param, Objects objects,
			Map<String, String> putin) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects, Map<String,String>) - start"); //$NON-NLS-1$
		}

		String nm = null;
		try {

			String path = param.getTestParamData().getParamValue();

			init(path);
			nm = utlProps.getProperty("toParam");
			// String v = getStepFirstObjParamValue(step);
			String val = (String) objectMehod.invoke(objectRemote);
			putin.put(nm, val);
			com.sssoft.isatt.runner.webrunner.ActionScript.stepRslt(stepResult, 1, "To param :" + nm + ", put :" + val + ".", "", null);
		} catch (Exception e) {
			LOG.warn("testStep putin  :" + e, e);
			final String err = "param from class err :" + e + ", To param :" + nm + ", from class :" + utlProps.getProperty("clz", "") + ", Method "
					+ utlProps.getProperty("method", "") + ".";
			com.sssoft.isatt.runner.webrunner.ActionScript.stepRslt(stepResult, 0, "Error in Device Id generator ", err, null);

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Objects, Map<String,String>) - end"); //$NON-NLS-1$
		}
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#startTestPlan(com.sssoft.isatt.data.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan)
	 */
	public void startTestPlan(Scheduler scheduler, TestPlan plan) {
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#startTestSuite(com.sssoft.isatt.data.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan, com.sssoft.isatt.data.pojo.input.TestSuite, com.sssoft.isatt.data.pojo.output.TestSuiteResult)
	 */
	public void startTestSuite(Scheduler scheduler, TestPlan testPlan, TestSuite testSuite, TestSuiteResult testSuiteResult) {
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#startTestCase(com.sssoft.isatt.data.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan, com.sssoft.isatt.data.pojo.input.TestSuite, com.sssoft.isatt.data.pojo.output.TestSuiteResult)
	 */
	public void startTestCase(Scheduler scheduler, TestPlan testPlan, TestSuite testSuite, TestSuiteResult testSuiteResult) {
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#endTestCase(com.sssoft.isatt.data.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan, com.sssoft.isatt.data.pojo.input.TestSuite, com.sssoft.isatt.data.pojo.input.TestCase, com.sssoft.isatt.data.pojo.output.TestCaseResult, com.sssoft.isatt.data.pojo.output.TestStepResult)
	 */
	public void endTestCase(Scheduler scheduler, TestPlan testPlan, TestSuite testSuite, TestCase testCase, TestCaseResult testCaseResult,
			TestStepResult testStepResult) {
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

}
