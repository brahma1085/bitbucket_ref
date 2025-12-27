package com.exilant.grx.iTAP.plugin;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.exilant.tfw.bean.UtlProps;
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
 * The Class DeviceID.
 */
public class DeviceID implements Runner {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(DeviceID.class);

	/** The stored values. */
	private Map<String, String> storedValues = new HashMap<String, String>();
	
	/** The device id gen. */
	public DeviceIdGenerator deviceIdGen = new DeviceIdGenerator();

	/**
	 * Gets the stored values.
	 *
	 * @return the stored values
	 */
	public Map<String, String> getStoredValues() {
		return storedValues;
	}

	/**
	 * Sets the stored values.
	 *
	 * @param storedValues the stored values
	 */
	public void setStoredValues(Map<String, String> storedValues) {
		this.storedValues = storedValues;
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#testStep(com.exilant.tfw.pojo.input.TestCase, com.exilant.tfw.pojo.output.TestCaseResult, com.exilant.tfw.pojo.input.TestStep, com.exilant.tfw.pojo.output.TestStepResult, com.exilant.tfw.pojo.input.Param)
	 */
	@Override
	public void testStep(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		testStepResult.setResult(0);
		String action = testStep.getActions().getActionName();
		try {
			Method method = DeviceID.class.getDeclaredMethod(action, TestCase.class, TestCaseResult.class, TestStep.class, TestStepResult.class,
					Param.class);
			method.invoke(this, testCase, testCaseResult, testStep, testStepResult, param);
		} catch (Exception e) {
			LOG.info("No method found" + e, e);
			testStepResult.setResult(0);
			testStepResult.setComment("No such action");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gen device id to csv.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 * @throws InvalidFormatException the invalid format exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SQLException the sQL exception
	 */
	public void genDeviceIDToCSV(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param)
			throws InvalidFormatException, IOException, SQLException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("genDeviceIDToCSV(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String csvFilePath = param.getTestParamData().getParamValue();
		String propertyFile = param.getTestParamData().getValueBig();
		try {
			UtlProps props = deviceIdGen.readPropertyFile(propertyFile);
			if (props.getProps().isEmpty()) {
				LOG.error("Could not read property file");
				TfwCoreUtls.stepRslt(testStepResult, 0, "Could not read property file", null);
			}
			// dig.connectToDB();
			List<String[]> devIDList = deviceIdGen.genBulkDeviceID(csvFilePath);

			if (devIDList.size() != 0) {
				LOG.info("Device Id generated");
				TfwCoreUtls.stepRslt(testStepResult, 1, "Device Id's are generated into the CSV file", null);
			}
			// dig.connectToDB().close();
		} catch (Exception e) {
			TfwCoreUtls.stepRslt(testStepResult, 0, "Could not generate the device Id's Error: " + e, null);
			LOG.error("Could not generate Device ids to csv file" + e, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("genDeviceIDToCSV(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gen one device id.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 * @throws SQLException the sQL exception
	 */
	public void genOneDeviceID(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param)
			throws SQLException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("genOneDeviceID(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String key = param.getTestParamData().getParamValue();
		String propertyFile = param.getTestParamData().getValueBig();
		try {
			UtlProps props = deviceIdGen.readPropertyFile(propertyFile);
			if (props.getProps().isEmpty()) {
				LOG.error("Could not read property file");
				TfwCoreUtls.stepRslt(testStepResult, 0, "Could not read property file", null);
			}
			// dig.connectToDB();
			String devID;
			devID = deviceIdGen.getOneDeviceID();
			String ss = "Did :" + devID + ", key " + key + ".";
			LOG.trace(ss);
			storedValues.put(key, devID);
			this.setStoredValues(storedValues);
			if (devID.length() < 8) {
				TfwCoreUtls.stepRslt(testStepResult, 0, "All suffixes might be inacive Please add suffixes before proceeding further", null);
			}
			LOG.info("Device Id generated");
			TfwCoreUtls.stepRslt(testStepResult, 1, "Device Id generated", null);
			// dig.connectToDB().close();
		} catch (IOException e) {
			TfwCoreUtls.stepRslt(testStepResult, 0, "Could not generate the device Id's Error: " + e, null);
			LOG.error("Could not generate Device id" + e, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("genOneDeviceID(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestPlan(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan)
	 */
	@Override
	public void startTestPlan(Scheduler scheduler, TestPlan testPlan) {
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestSuite(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.output.TestSuiteResult)
	 */
	@Override
	public void startTestSuite(Scheduler scheduler, TestPlan testPlan, TestSuite testSuite, TestSuiteResult testSuiteResult) {
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestCase(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.output.TestSuiteResult)
	 */
	@Override
	public void startTestCase(Scheduler scheduler, TestPlan testPlan, TestSuite testSuite, TestSuiteResult testSuiteResult) {
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#endTestCase(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.input.TestCase, com.exilant.tfw.pojo.output.TestCaseResult, com.exilant.tfw.pojo.output.TestStepResult)
	 */
	@Override
	public void endTestCase(Scheduler scheduler, TestPlan testPlan, TestSuite testSuite, TestCase testCase, TestCaseResult testCaseResult,
			TestStepResult testStepResult) {
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
