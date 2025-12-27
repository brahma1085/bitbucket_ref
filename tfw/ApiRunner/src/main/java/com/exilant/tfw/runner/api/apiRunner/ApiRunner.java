package com.exilant.tfw.runner.api.apiRunner;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.exilant.grx.iTAP.plugin.DeviceID;
import com.exilant.tfw.bean.ActionCommand;
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
 * The Class ApiRunner.
 */
public class ApiRunner implements Runner {

	/** The api request. */
	ApiRequest apiRequest = new ApiRequest();
	
	/** The action command. */
	ActionCommand actionCommand = new ActionCommand();
	
	/** The values. */
	ArrayList<String> values = new ArrayList<String>();

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(ApiRunner.class);

	/** The device id. */
	private DeviceID deviceID = new DeviceID();

	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public DeviceID getDeviceID() {
		return deviceID;
	}

	/**
	 * Sets the device id.
	 *
	 * @param deviceID the new device id
	 */
	public void setDeviceID(DeviceID deviceID) {
		this.deviceID = deviceID;
	}

	/**
	 * Instantiates a new api runner.
	 */
	public ApiRunner() {

	}

	/**
	 * Gets the api request.
	 *
	 * @return the api request
	 */
	public ApiRequest getApiRequest() {
		return apiRequest;
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#testStep(com.exilant.tfw.pojo.input.TestCase, com.exilant.tfw.pojo.output.TestCaseResult, com.exilant.tfw.pojo.input.TestStep, com.exilant.tfw.pojo.output.TestStepResult, com.exilant.tfw.pojo.input.Param)
	 */
	public void testStep(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String action = testStep.getActions().getActionName();
		if ("genDeviceIDToCSV".equals(action) || "genOneDeviceID".equals(action)) {
			try {
				ApiRunner.this.getDeviceID().getClass()
						.getMethod(action, TestCase.class, TestCaseResult.class, TestStep.class, TestStepResult.class, Param.class)
						.invoke(ApiRunner.this.getDeviceID(), testCase, testCaseResult, testStep, testStepResult, param);
			} catch (IllegalArgumentException e) {
				LOG.error("Invalid Arguments for the action : " + e, e);
				testStepResult.setComment("Invalid Arguments for the action : " + e);
				testStepResult.setException("Could not process action :[" + action + "] invalid Arguments for the action.");
			} catch (SecurityException e) {
				LOG.error("Security Err " + e, e);
				testStepResult.setComment("Java security, bad setup ERR :" + e);
				testStepResult.setException("Could not process action :[" + action + "] java error.");
			} catch (IllegalAccessException e) {
				LOG.error("Not able to access the specified method : " + e, e);
				testStepResult.setComment("Not able to access the specified method : " + e);
				testStepResult.setException("Could not process action :[" + action + "] not able to access the specified method.");
			} catch (InvocationTargetException e) {
				LOG.error("Not able to invoke the specified method : " + e, e);
				testStepResult.setComment("Not able to invoke the specified method : " + e);
				testStepResult.setException("Could not process action :[" + action + "] not able to invoke the specified method.");
			} catch (NoSuchMethodException e) {
				LOG.error("No method Err :" + e, e);
				testStepResult.setComment("Bad action - no corresponding impl ERR :" + e);
				testStepResult.setException("Could not process action :[" + action + "] not implemented this.");
			} catch (Exception e) {
				LOG.error("Error due to : " + e, e);
				testStepResult.setComment("Error due to : " + e);
				testStepResult.setException("Could not process action :[" + action + "] due to internal errors.");
			}
		} else {
			actionCommand.setAction(action);
			actionCommand.setObjectid1(param.getObjects().getObjectName());

			try {
				Map<String, String> storedDeviceIds = deviceID.getStoredValues();
				String paramValue = param.getTestParamData().getParamValue();
				if (storedDeviceIds != null && storedDeviceIds.get(paramValue) != null) {
					param.getTestParamData().setParamValue(storedDeviceIds.get(paramValue));
				}
				LOG.info("Param Datas : " + param.getTestParamData().getParamValue());
				apiRequest.passValue(actionCommand, param.getTestParamData().getParamValue());

			} catch (Exception e) {
				LOG.error("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param)", e); //$NON-NLS-1$
			}

			if (actionCommand.isResult()) {
				testStepResult.setResult(1);
			} else {
				testStepResult.setResult(0);
			}
			testStepResult.setComment(actionCommand.getComment());
			testStepResult.setResponse(actionCommand.getDetailMsgs());
			testStepResult.setTestStepID(testStep.getTestStepID());
			testStepResult.setRequest(actionCommand.getRequest());
			testStepResult.setResponse(actionCommand.getResponse());
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

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public static final String getVersion() {
		return "1";
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

}
