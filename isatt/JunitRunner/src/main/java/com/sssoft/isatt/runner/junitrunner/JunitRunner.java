/**
 * @author soumyamohanty
 */

package com.sssoft.isatt.runner.junitrunner;

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

import com.sssoft.isatt.data.pojo.Scheduler;
import com.sssoft.isatt.data.pojo.def.Runner;
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
 * The Class JunitRunner.
 */
public class JunitRunner implements Runner {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(JunitRunner.class);

	/** The methods. */
	private static volatile Map<String, Method> methods = new HashMap<String, Method>(20);

	/** The junit action. */
	JunitAction junitAction = new JunitAction();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#startTestSuite(com.exilant
	 * .tfw.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan,
	 * com.sssoft.isatt.data.pojo.input.TestSuite,
	 * com.sssoft.isatt.data.pojo.output.TestSuiteResult)
	 */
	public void startTestSuite(Scheduler scheduler, TestPlan plan, TestSuite suite, TestSuiteResult tstSmmryFromRemote) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#startTestCase(com.exilant
	 * .tfw.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan,
	 * com.sssoft.isatt.data.pojo.input.TestSuite,
	 * com.sssoft.isatt.data.pojo.output.TestSuiteResult)
	 */
	public void startTestCase(Scheduler scheduler, TestPlan plan, TestSuite suite, TestSuiteResult suiteSumamryResult) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#testStep(com.exilant.tfw.
	 * pojo.input.TestCase, com.sssoft.isatt.data.pojo.output.TestCaseResult,
	 * com.sssoft.isatt.data.pojo.input.TestStep,
	 * com.sssoft.isatt.data.pojo.output.TestStepResult,
	 * com.sssoft.isatt.data.pojo.input.Param)
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
	 */
	public void sample(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("sample(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String userName = null;
		String svnUrl = null;
		String password = null;
		String targetDir = null;
		String targetAndGoals = null;
		String scriptPath = null;
		String sourceDirectory = null;
		int buildResult = 0;

		try {
			Properties prop = new Properties();
			prop.load(JunitRunner.class.getClassLoader().getResourceAsStream("Junit.properties"));

			/*
			 * All values to be fetched from TestStep object. Code to be checked
			 * out if the URL is SVN URL and build is performed on the whole
			 * project. If build success, then go for powermock generation else
			 * Build failed is reported in Cobertura report. Powermock inputs
			 * are source directory and target directory.
			 */

			List<Param> params = getParams(testStep);

			for (Param stepParam : params) {
				if (stepParam.getParamName().equals(prop.getProperty("SVN_Param_Name"))) {
					svnUrl = stepParam.getTestParamData().getParamValue();
				} else if (stepParam.getParamName().equals(prop.getProperty("USERNAME_Param_name"))) {
					userName = stepParam.getTestParamData().getParamValue();
				} else if (stepParam.getParamName().equals(prop.getProperty("PASSWORD_Param_name"))) {
					password = stepParam.getTestParamData().getParamValue();
				} else if (stepParam.getParamName().equals(prop.getProperty("TARGETDIR_Param_Name"))) {
					targetDir = stepParam.getTestParamData().getParamValue();
				} else if (stepParam.getParamName().equals(prop.getProperty("SRCDIR_Param_Name"))) {
					sourceDirectory = stepParam.getTestParamData().getParamValue();
				}
			}

			scriptPath = sourceDirectory;
			/*
			 * If Source Directory is null, then checkout code first using the
			 * SVN URL and credentials
			 */
			if (sourceDirectory == null) {
				junitAction.checkOutCode(svnUrl, userName, password, prop.getProperty("SOURCE_DIRECTORY"));
				LOG.info("Code checked out in " + sourceDirectory);
			}

			String projectType = junitAction.getProjectType(sourceDirectory);

			/*
			 * Clean and Build the code first to generate the .class files. Then
			 * generate Powermock and Cobertura report.
			 */
			if ("ant".equals(projectType)) {
				Process p;
				p = Runtime.getRuntime().exec("ant -file " + sourceDirectory + "/build.xml");
				BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null) {
					LOG.info(line);
				}
			} else {
				MavenCli cli = new MavenCli();
				cli.doMain(new String[] { "clean" }, sourceDirectory, System.out, System.err);
				buildResult = cli.doMain(new String[] { "install" }, sourceDirectory, System.out, System.err);
				LOG.info(buildResult);
			}
			if (buildResult == 0) {
				junitAction.generatePowerMock(sourceDirectory, "");
				LOG.info("Powermock Generated");
				if ("ant".equalsIgnoreCase(projectType) && "Y".equalsIgnoreCase(prop.getProperty("IS_GENERATE_COBERTURA"))) {
					junitAction.generateCoberturaReport(scriptPath, targetAndGoals);
				}
			} else {
				LOG.info("Build Failure.Check log.Fix the Build and upload again");
			}
		} catch (FileNotFoundException e1) {
			LOG.error("sample(TestCase, TestCaseResult, TestStep, TestStepResult, Param)", e1); //$NON-NLS-1$
			LOG.info("Could not locate JUnit Properties file");
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
		return junitAction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#endTestCase(com.exilant.tfw
	 * .pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan,
	 * com.sssoft.isatt.data.pojo.input.TestSuite,
	 * com.sssoft.isatt.data.pojo.input.TestCase,
	 * com.sssoft.isatt.data.pojo.output.TestCaseResult,
	 * com.sssoft.isatt.data.pojo.output.TestStepResult)
	 */
	public void endTestCase(Scheduler scheduler, TestPlan plan, TestSuite suite, TestCase testCase, TestCaseResult caseResult, TestStepResult lastStep) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#getConfigPath()
	 */
	public String getConfigPath() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#setConfigPath(java.lang.String
	 * )
	 */
	public void setConfigPath(String configPath) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.pojo.def.Runner#clone2()
	 */
	public Runner clone2() throws CloneNotSupportedException {
		return null;
	}

	/**
	 * Start test plan.
	 * 
	 * @param testPlan
	 *            the test plan
	 */
	public void startTestPlan(com.sssoft.isatt.data.pojo.input.TestPlan testPlan) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#startTestPlan(com.exilant
	 * .tfw.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan)
	 */
	public void startTestPlan(Scheduler scheduler, TestPlan testPlan) {
	}

	/**
	 * Gets the params.
	 * 
	 * @param testStep
	 *            the test step
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
