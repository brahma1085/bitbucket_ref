package com.exilant.runner.JavaAPIRunner;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.maven.cli.MavenCli;

import com.exilant.runner.JavaAPIRunner.Utils.JavaAPIClass;
import com.exilant.tfw.pojo.Scheduler;
import com.exilant.tfw.pojo.def.Runner;
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
 * The Class JavaAPIRunner.
 */
public class JavaAPIRunner implements Runner {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(JavaAPIRunner.class);
	
	/** The methods. */
	private static volatile Map<String, Method> methods = new HashMap<String, Method>(20);
	
	/** The java api class. */
	JavaAPIClass javaAPIClass = new JavaAPIClass();

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestSuite(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.output.TestSuiteResult)
	 */
	public void startTestSuite(Scheduler scheduler, TestPlan plan, TestSuite suite, TestSuiteResult tstSmmryFromRemote) {
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestCase(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.output.TestSuiteResult)
	 */
	public void startTestCase(Scheduler scheduler, TestPlan plan, TestSuite suite, TestSuiteResult suiteSumamryResult) {
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#testStep(com.exilant.tfw.pojo.input.TestCase, com.exilant.tfw.pojo.output.TestCaseResult, com.exilant.tfw.pojo.input.TestStep, com.exilant.tfw.pojo.output.TestStepResult, com.exilant.tfw.pojo.input.Param)
	 */
	public void testStep(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		sample(testCase, testCaseResult, testStep, testStepResult, param);

		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Sample.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 */
	public void sample(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("sample(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String jsonData = null;
		String className = null;
		String sourceDirectory = null;
		int buildResult = 0;

		try {
			Properties prop = new Properties();
			prop.load(JavaAPIRunner.class.getClassLoader().getResourceAsStream("Junit.properties"));

			/*
			 * All values to be fetched from TestStep object. Code to be checked
			 * out if the URL is SVN URL and build is performed on the whole
			 * project. If build success, then go for powermock generation else
			 * Build failed is reported in Cobertura report. Powermock inputs
			 * are source directory and target directory.
			 */

			List<Param> params = getParams(testStep);

			for (Param stepParam : params) {
				if (stepParam.getParamName().equals(prop.getProperty("className"))) {
					className = stepParam.getTestParamData().getParamValue();
				} else if (stepParam.getParamName().equals(prop.getProperty("jsonData"))) {
					jsonData = stepParam.getTestParamData().getValueBig();
				} else if (stepParam.getParamName().equals(prop.getProperty("SRCDIR_Param_Name"))) {
					sourceDirectory = stepParam.getTestParamData().getParamValue();
				}
			}

			String projectType = javaAPIClass.getProjectType(sourceDirectory);

			/*
			 * Clean and Build the code first to generate the .class files. Then
			 * generate Powermock and Cobertura report.
			 */
			if (projectType != null && "ant".equals(projectType)) {
				Process p;
				p = Runtime.getRuntime().exec("ant -file " + sourceDirectory + "/build.xml");
				BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null) {
					LOG.info(line);
				}
			} else if (projectType != null && "maven".equals(projectType)) {
				MavenCli cli = new MavenCli();
				cli.doMain(new String[] { "clean" }, sourceDirectory, System.out, System.err);
				buildResult = cli.doMain(new String[] { "install" }, sourceDirectory, System.out, System.err);
				LOG.info(buildResult);
			} else {
				sourceDirectory = sourceDirectory + "/bin/";
				buildResult = 0;
			}
			if (buildResult == 0) {
				JavaAPIClass.javaApiValidation(sourceDirectory, className, jsonData);
			} else {
				LOG.info("Build Failure.Check log.Fix the Build and upload again");
			}
		} catch (FileNotFoundException e1) {
			LOG.error("sample(TestCase, TestCaseResult, TestStep, TestStepResult, Param)", e1); //$NON-NLS-1$
			LOG.info("Could not locate  Properties file");
		} catch (Exception e) {
			LOG.warn("sample(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("sample(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the action script.
	 *
	 * @return the action script
	 */
	private Object getActionScript() {
		return javaAPIClass;
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#endTestCase(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.input.TestCase, com.exilant.tfw.pojo.output.TestCaseResult, com.exilant.tfw.pojo.output.TestStepResult)
	 */
	public void endTestCase(Scheduler scheduler, TestPlan plan, TestSuite suite, TestCase testCase, TestCaseResult caseResult, TestStepResult lastStep) {
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
	 * Start test plan.
	 *
	 * @param testPlan the test plan
	 */
	public void startTestPlan(com.exilant.tfw.pojo.input.TestPlan testPlan) {
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestPlan(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan)
	 */
	public void startTestPlan(Scheduler scheduler, TestPlan testPlan) {
	}

	/**
	 * Gets the params.
	 *
	 * @param testStep the test step
	 * @return the params
	 */
	private List<Param> getParams(TestStep testStep) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParams(TestStep) - start"); //$NON-NLS-1$
		}

		List<Param> params = null;
		List<ParamGroup> paramGroups = testStep.getParamGroup();
		Iterator<ParamGroup> paramGroupItr = paramGroups.iterator();
		while (paramGroupItr.hasNext()) {
			ParamGroup paramGroup = (ParamGroup) paramGroupItr.next();
			params = paramGroup.getParam();

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getParams(TestStep) - end"); //$NON-NLS-1$
		}
		return params;
	}

}
