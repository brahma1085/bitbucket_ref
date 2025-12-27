package com.exilant.tfw.runner.http;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.exilant.tfw.core.TfwCoreUtls;
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

/**
 * The Class ParamFromTstGenClz.
 */
public class ParamFromTstGenClz implements Runner {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(ParamFromTstGenClz.class);

	/** The beans. */
	private static Map<String, Object> beans = new HashMap<String, Object>();
	
	/** The utl props. */
	private final com.exilant.tfw.bean.UtlProps utlProps = new com.exilant.tfw.bean.UtlProps();
	
	/** The object remote. */
	private Object objectRemote = null;
	
	/** The object mehod. */
	private Method objectMehod;

	/**
	 * Inits the.
	 *
	 * @param props the props
	 */
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
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#testStep(com.exilant.tfw.pojo.input.TestCase, com.exilant.tfw.pojo.output.TestCaseResult, com.exilant.tfw.pojo.input.TestStep, com.exilant.tfw.pojo.output.TestStepResult, com.exilant.tfw.pojo.input.Param)
	 */
	@Override
	public void testStep(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {

	}

	/**
	 * Test step.
	 *
	 * @param tcase the tcase
	 * @param caseResult the case result
	 * @param step the step
	 * @param stepResult the step result
	 * @param param the param
	 * @param putin the putin
	 */
	public void testStep(TestCase tcase, TestCaseResult caseResult, TestStep step, TestStepResult stepResult, Param param, Map<String, String> putin) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Map<String,String>) - start"); //$NON-NLS-1$
		}

		String nm = null;
		try {

			String path = step.getStepName();

			init(path);
			nm = utlProps.getProperty("toParam");
			// String v = getStepFirstObjParamValue(step);
			String val = (String) objectMehod.invoke(objectRemote);
			putin.put(nm, val);
			com.exilant.tfw.runner.http.HttpRunner.stepRslt(stepResult, 1, "To param :" + nm + ", put :" + val + ".+", "+ ");
		} catch (Exception e) {
			LOG.warn("testStep putin  :" + e, e);
			final String err = "param from class err :" + e + ", To param :" + nm + ", from class :" + utlProps.getProperty("clz", "") + ", Method "
					+ utlProps.getProperty("method", "") + ".";
			TfwCoreUtls.stepFillDetail(stepResult, "", err, e);

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param, Map<String,String>) - end"); //$NON-NLS-1$
		}
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestPlan(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan)
	 */
	@Override
	public void startTestPlan(Scheduler scheduler, TestPlan plan) {
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestSuite(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.output.TestSuiteResult)
	 */
	@Override
	public void startTestSuite(Scheduler scheduler, TestPlan plan, TestSuite suite, TestSuiteResult tstSmmryFromRemote) {
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestCase(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.output.TestSuiteResult)
	 */
	@Override
	public void startTestCase(Scheduler scheduler, TestPlan plan, TestSuite suite, TestSuiteResult suiteSumamryResult) {
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#endTestCase(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.input.TestCase, com.exilant.tfw.pojo.output.TestCaseResult, com.exilant.tfw.pojo.output.TestStepResult)
	 */
	@Override
	public void endTestCase(Scheduler scheduler, TestPlan plan, TestSuite suite, TestCase testCase, TestCaseResult caseResult, TestStepResult lastStep) {
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#getConfigPath()
	 */
	@Override
	public String getConfigPath() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#setConfigPath(java.lang.String)
	 */
	@Override
	public void setConfigPath(String configPath) {
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.Runner#clone2()
	 */
	@Override
	public Runner clone2() throws CloneNotSupportedException {
		return null;
	}

}
