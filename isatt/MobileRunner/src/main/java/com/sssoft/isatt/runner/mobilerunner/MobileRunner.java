package com.sssoft.isatt.runner.mobilerunner;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.gorillalogic.monkeytalk.java.MonkeyTalkDriver;
import com.gorillalogic.monkeytalk.java.api.Application;
import com.sssoft.isatt.core.TfwCoreUtls;
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
 * The Class MobileRunner.
 */
public class MobileRunner implements Runner {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(MobileRunner.class);

	/** The methods. */
	private static volatile Map<String, Method> methods = new HashMap<String, Method>(20);

	/** The mtdriver. */
	private static MonkeyTalkDriver mtdriver;

	/** The app. */
	private static Application app;

	/** The all methods. */
	public static String[] allMethods;

	/** The stored values. */
	public static Map<String, String> storedValues = new HashMap<String, String>();

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
	public void testStep(TestCase testCase, TestCaseResult caseResult, TestStep step, TestStepResult stepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		stepResult.setResult(0);
		String action = step.getActions().getActionName();
		Method actionMethd = null;

		synchronized (methods) {
			actionMethd = methods.get(action);
			if (actionMethd == null) {
				try {
					actionMethd = this.getClass().getMethod(action, TestCase.class, TestCaseResult.class, TestStep.class, TestStepResult.class,
							Param.class);
					methods.put(action, actionMethd);
				} catch (SecurityException e) {
					LOG.error("SE Err " + e, e);
					stepResult.setException("Java security, bad setup ERR :" + e);
					stepResult.setComment("Could not process action :[" + action + "] java error.");
				} catch (NoSuchMethodException e) {
					LOG.error("No method Err :" + e, e);
					stepResult.setException("Bad action - no corresponding impl ERR :" + e);
					stepResult.setComment("Could not process action :[" + action + "] not implemented this.");
				}
			}
		}
		if (actionMethd != null) {
			try {
				LOG.info("Action :" + action);
				actionMethd.invoke(this, testCase, caseResult, step, stepResult, param);
			} catch (Exception e) {
				LOG.warn("Calling action Err :" + e, e);
				stepResult.setException("Bad action - ERR :" + e);
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the value big.
	 * 
	 * @param param
	 *            the param
	 * @return the value big
	 */
	private String getValueBig(Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getValueBig(Param) - start"); //$NON-NLS-1$
		}

		String returnString = param.getTestParamData().getValueBig();
		if (LOG.isDebugEnabled()) {
			LOG.debug("getValueBig(Param) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Gets the param value.
	 * 
	 * @param param
	 *            the param
	 * @return the param value
	 */
	private String getParamValue(Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamValue(Param) - start"); //$NON-NLS-1$
		}

		String returnString = param.getTestParamData().getParamValue();
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamValue(Param) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Gets the objects for action.
	 * 
	 * @param testStep
	 *            the test step
	 * @param param
	 *            the param
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

	/**
	 * Connect to device.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void connectToDevice(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("connectToDevice(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String deviceIP = getParamValue(param);
		String device = getValueBig(param);
		String deviceId = null;
		if ("iosSimulator".equalsIgnoreCase(device)) {
			mtdriver = new MonkeyTalkDriver(new File("."), "iOS");
		} else if ("androidEmulator".equalsIgnoreCase(device)) {
			mtdriver = new MonkeyTalkDriver(new File("."), "AndroidEmulator");
		} else if ("iosdevice".equalsIgnoreCase(device)) {
			mtdriver = new MonkeyTalkDriver(new File("."), "iOS", deviceIP);
		} else if ("androidDevice".equalsIgnoreCase(device)) {
			mtdriver = new MonkeyTalkDriver(new File("."), "AndroidEmulator", deviceIP);
		}
		app = mtdriver.app();
		try {
			deviceId = app.device().get("id");
			if (deviceId != null) {
				mtdriver.setThinktime(1000);
				mtdriver.setTimeout(5000);
				LOG.info("Successfully connected to the device/simulator: " + deviceId);
				TfwCoreUtls.stepRslt(stepResult, 1, "Successfully connected to the device/simulator: " + deviceId, null);
			}
		} catch (Exception e) {
			LOG.error("Could not connect to the device: " + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not connect to the device: " + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("connectToDevice(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Tap.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void tap(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("tap(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "tap";
		String data = getParamValue(param);
		String testDataSet[] = null;
		if (data != null) {
			data = data.replace("[", "");
			data = data.replace("]", "");
			testDataSet = data.split(",");
		}
		LOG.info("Performing tap action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				Method actionMethd1 = object.getClass().getMethod(action);
				LOG.info("actionMethd1 : " + actionMethd1.getName());
				actionMethd1.invoke(object);
			}
			LOG.info("Successfully tapped on the object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully tapped on the object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not tap on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not tap on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("tap(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Long press.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void longPress(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("longPress(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "longPress";
		String data = getParamValue(param);
		String testDataSet[] = null;
		if (data != null) {
			data = data.replace("[", "");
			data = data.replace("]", "");
			testDataSet = data.split(",");
		}
		LOG.info("Performing longPress action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				if (data == null || data.length() < 1) {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object);
				} else {
					Method actionMethd1 = object.getClass().getMethod(action, Integer.TYPE, Integer.TYPE);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, Integer.valueOf(testDataSet[0]), Integer.valueOf(testDataSet[1]));
				}
			}
			LOG.info("Successfully performed longPress action on the object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully performed longPress action on the object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not perform longPress action on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not perform longPress action on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("longPress(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the comp object.
	 * 
	 * @param component
	 *            the component
	 * @param monkeyID
	 *            the monkey id
	 * @return the comp object
	 * @throws SecurityException
	 *             the security exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 */
	public static Object getCompObject(String component, String monkeyID) throws SecurityException, NoSuchMethodException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getCompObject(String, String) - start"); //$NON-NLS-1$
		}

		Object object = null;
		Method[] methods = Application.class.getDeclaredMethods();
		String[] methodNames = new String[methods.length];
		Method actionMethd;
		int i = 0;
		for (Method method : methods) {
			methodNames[i] = method.getName();
			i++;
		}

		if (ArrayUtils.contains(methodNames, component)) {
			try {
				if ("NoObject".equalsIgnoreCase(monkeyID)) {
					actionMethd = app.getClass().getMethod(component);
					object = actionMethd.invoke(app);
				} else {
					actionMethd = app.getClass().getMethod(component, String.class);
					object = actionMethd.invoke(app, monkeyID);
				}
				Method[] methods1 = object.getClass().getDeclaredMethods();
				allMethods = new String[methods1.length];

				int i1 = 0;
				for (Method method : methods1) {
					allMethods[i1] = method.getName();
					i1++;
				}
			} catch (Exception e) {
				LOG.error("getCompObject(String, String)", e); //$NON-NLS-1$

			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getCompObject(String, String) - end"); //$NON-NLS-1$
		}
		return object;
	}

	/**
	 * Touch down.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws SecurityException
	 *             the security exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	public void touchDown(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("touchDown(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "touchDown";
		String data = getParamValue(param);
		String testDataSet[] = null;
		if (data != null) {
			data = data.replace("[", "");
			data = data.replace("]", "");
			testDataSet = data.split(",");
		}
		LOG.info("Performing touchDown action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				if (data == null || data.length() < 1) {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object);
				} else {
					Method actionMethd1 = object.getClass().getMethod(action, Integer.TYPE, Integer.TYPE);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, Integer.valueOf(testDataSet[0]), Integer.valueOf(testDataSet[1]));
				}
			}
			LOG.info("Successfully touched down on the object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully touched down on the object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not touch down on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not touch down on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("touchDown(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Touch move.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void touchMove(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("touchMove(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String data = getParamValue(param);
		List<Integer> testDataSet = new ArrayList<Integer>();

		if (data != null) {
			data = data.replace("[", "");
			data = data.replace("]", "");
			String[] dataset = data.split(",");
			for (int i = 0; i < dataset.length; i++) {
				testDataSet.add(Integer.valueOf(dataset[i]));
			}
		}
		String action = "touchMove";
		LOG.info("Performing touchMove action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				if (data == null || data.length() < 1) {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object);
				} else {
					Method actionMethd1 = object.getClass().getMethod(action, List.class);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, testDataSet);
				}
			}
			LOG.info("Successfully performed touchMove action on the object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully performed touchMove action on the object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not perform touch move on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not perform touch move on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("touchMove(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Touch up.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void touchUp(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("touchUp(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "touchUp";
		String data = getParamValue(param);
		String testDataSet[] = null;
		if (data != null) {
			data = data.replace("[", "");
			data = data.replace("]", "");
			testDataSet = data.split(",");
		}
		LOG.info("Performing touchUp action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				if (data == null || data.length() < 1) {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object);
				} else {
					Method actionMethd1 = object.getClass().getMethod(action, Integer.TYPE, Integer.TYPE);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, Integer.valueOf(testDataSet[0]), Integer.valueOf(testDataSet[1]));
				}
			}
			LOG.info("Successfully touched Up on the object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully touched Up on the object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not do touch Up on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not do touch Up on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("touchUp(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Pinch.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void pinch(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("pinch(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "pinch";
		String data = getParamValue(param);
		String testDataSet[] = null;
		if (data != null) {
			data = data.replace("[", "");
			data = data.replace("]", "");
			testDataSet = data.split(",");
		}
		LOG.info("Performing Pinch action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				if (data == null || data.length() < 1) {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object);
				} else {
					Method actionMethd1 = object.getClass().getMethod(action, Integer.TYPE, Integer.TYPE);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, Integer.valueOf(testDataSet[0]), Integer.valueOf(testDataSet[1]));
				}
			}
			LOG.info("Successfully pinched the object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully pinched the object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not pinch on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not pinch on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("pinch(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Swipe.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void swipe(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("swipe(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "swipe";
		String data = getParamValue(param);
		LOG.info("Performing swipe action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				if (data == null || data.length() < 1) {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object);
				} else {
					Method actionMethd1 = object.getClass().getMethod(action, String.class);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, data);
				}
			}
			LOG.info("Successfully swiped to the: " + data);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully swiped to the: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not swipe: " + data + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not swipe: " + data + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("swipe(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Drag.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void drag(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("drag(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String data = getParamValue(param);
		List<Integer> testDataSet = new ArrayList<Integer>();

		if (data != null) {
			data = data.replace("[", "");
			data = data.replace("]", "");
			String[] dataset = data.split(",");
			for (int i = 0; i < dataset.length; i++) {
				testDataSet.add(Integer.valueOf(dataset[i]));
			}
		}
		String action = "drag";
		LOG.info("Performing drag action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				if (data == null || data.length() < 1) {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object);
				} else {
					Method actionMethd1 = object.getClass().getMethod(action, List.class);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, testDataSet);
				}
			}
			LOG.info("Successfully performed drag action on the object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully performed drag action on the object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not perform drag action on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not perform drag action on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("drag(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Store value.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void storeValue(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("storeValue(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String data = getParamValue(param);
		String[] dataset = null;
		if (data != null) {
			data = data.replace("[", "");
			data = data.replace("]", "");
			dataset = data.split(",");
		}
		String property = dataset[0];
		String key = dataset[1];
		String propVal;
		String action = "get";
		LOG.info("Performing storeValue action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				Method actionMethd1 = object.getClass().getMethod(action, String.class);
				LOG.info("actionMethd1 : " + actionMethd1.getName());
				propVal = (String) actionMethd1.invoke(object, property);
				storedValues.put(key, propVal);
			}
			LOG.info("Successfully performed storeValue action on the object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully performed storeValue action on the object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not perform storeValue action on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not perform storeValue action on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("storeValue(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Select.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void select(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("select(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "select";
		String data = getParamValue(param);
		LOG.info("Performing select action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				if (data == null || data.length() < 1) {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object);
				} else if ("slider".equals(component)) {
					Method actionMethd1 = object.getClass().getMethod(action, Float.TYPE);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, Float.valueOf(data));
				} else {
					Method actionMethd1 = object.getClass().getMethod(action, String.class);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, data);
				}
			}
			LOG.info("Successfully performed select action on the Object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully performed select action on the Object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not perform select action on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not perform select action on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("select(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Long select.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void longSelect(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("longSelect(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "longSelect";
		String data = getParamValue(param);
		LOG.info("Performing longSelect action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				if (data == null || data.length() < 1) {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object);
				} else {
					Method actionMethd1 = object.getClass().getMethod(action, String.class);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, data);
				}
			}
			LOG.info("Successfully performed longSelect action on the Object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully performed longSelect action on the Object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not perform longSelect action on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not perform longSelect action on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("longSelect(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Select by index.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void selectByIndex(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("selectByIndex(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "selectIndex";
		String data = getParamValue(param);
		LOG.info("Performing selectByIndex action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				if (data == null || data.length() < 1) {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object);
				} else {
					Method actionMethd1 = object.getClass().getMethod(action, Integer.TYPE);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, Integer.valueOf(data));
				}
			}
			LOG.info("Successfully performed selectByIndex action on the Object: " + component + " and selected item at index: " + data);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully performed selectByIndex action on the Object: " + component
					+ " and selected item at index: " + data, null);
		} catch (Exception e) {
			LOG.error("Could not perform selectByIndex action on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not perform selectByIndex action on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("selectByIndex(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Enter date.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void enterDate(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("enterDate(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "enterDate";
		String data = getParamValue(param);
		LOG.info("Performing enterDate action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				if (data == null || data.length() < 1) {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object);
				} else {
					Method actionMethd1 = object.getClass().getMethod(action, String.class);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, data);
				}
			}
			LOG.info("Successfully performed enterDate action on the Object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully performed enterDate action on the Object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not perform enterDate action on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not perform enterDate action on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("enterDate(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Load url.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void loadUrl(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("loadUrl(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "open";
		String data = getParamValue(param);
		LOG.info("Performing loadUrl action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				if (data == null || data.length() < 1) {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object);
				} else {
					Method actionMethd1 = object.getClass().getMethod(action, String.class);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, data);
				}
			}
			LOG.info("Successfully performed loadUrl action on the Object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully performed loadUrl action on the Object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not perform loadUrl action on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not perform loadUrl action on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("loadUrl(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Navigate back.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void navigateBack(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("navigateBack(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "back";
		String data = getParamValue(param);
		LOG.info("Performing navigateBack action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				Method actionMethd1 = object.getClass().getMethod(action);
				LOG.info("actionMethd1 : " + actionMethd1.getName());
				actionMethd1.invoke(object);
			}
			LOG.info("Successfully performed navigateBack action on the Object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully performed navigateBack action on the Object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not perform navigateBack action on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not perform navigateBack action on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("navigateBack(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Navigate forward.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void navigateForward(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("navigateForward(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "forward";
		String data = getParamValue(param);
		LOG.info("Performing navigateForward action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				Method actionMethd1 = object.getClass().getMethod(action);
				LOG.info("actionMethd1 : " + actionMethd1.getName());
				actionMethd1.invoke(object);
			}
			LOG.info("Successfully performed navigateForward action on the Object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully performed navigateForward action on the Object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not perform navigateForward action on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not perform navigateForward action on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("navigateForward(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Toggle on.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void toggleOn(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("toggleOn(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "on";
		String data = getParamValue(param);
		LOG.info("Performing toggleOn action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				if (data == null || data.length() < 1) {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object);
				} else {
					Method actionMethd1 = object.getClass().getMethod(action, String.class);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, data);
				}
			}
			LOG.info("Successfully performed toggleOn action on the Object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully performed toggleOn action on the Object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not perform toggleOn action on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not perform toggleOn action on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("toggleOn(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Toggle off.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void toggleOff(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("toggleOff(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "off";
		String data = getParamValue(param);
		LOG.info("Performing toggleOff action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				if (data == null || data.length() < 1) {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object);
				} else {
					Method actionMethd1 = object.getClass().getMethod(action, String.class);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, data);
				}
			}
			LOG.info("Successfully performed toggleOff action on the Object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully performed toggleOff action on the Object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not perform toggleOff action on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not perform toggleOff action on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("toggleOff(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Enter text.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void enterText(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("enterText(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "enterText";
		String data = getParamValue(param);
		LOG.info("Performing enterText action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				if (data == null || data.length() < 1) {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object);
				} else {
					Method actionMethd1 = object.getClass().getMethod(action, String.class);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, data);
				}
			}
			LOG.info("Successfully performed enterText action on the Object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully performed enterText action on the Object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not perform enterText action on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not perform enterText action on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("enterText(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Clear text field.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void clearTextField(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("clearTextField(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "clear";
		String data = getParamValue(param);
		LOG.info("Performing clearTextField action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				if (data == null || data.length() < 1) {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object);
				} else {
					Method actionMethd1 = object.getClass().getMethod(action, String.class);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, data);
				}
			}
			LOG.info("Successfully performed clearTextField action on the Object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully performed clearTextField action on the Object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not perform clearTextField action on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not perform clearTextField action on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("clearTextField(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Scroll.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void scroll(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("scroll(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "scroll";
		String data = getParamValue(param);
		String testDataSet[] = null;
		if (data != null) {
			data = data.replace("[", "");
			data = data.replace("]", "");
			testDataSet = data.split(",");
		}
		LOG.info("Performing scroll action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				if (data == null || data.length() < 1) {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object);
				} else {
					Method actionMethd1 = object.getClass().getMethod(action, Integer.TYPE, Integer.TYPE);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, Integer.valueOf(testDataSet[0], Integer.valueOf(testDataSet[1])));
				}
			}
			LOG.info("Successfully performed scroll action on the Object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully performed scroll action on the Object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not perform scroll action on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not perform scroll action on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("scroll(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Verify object present.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void verifyObjectPresent(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("verifyObjectPresent(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "verify";
		String data = getParamValue(param);
		LOG.info("Performing verifyObjectPresent action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				Method actionMethd1 = object.getClass().getMethod(action);
				LOG.info("actionMethd1 : " + actionMethd1.getName());
				actionMethd1.invoke(object);
			}
			LOG.info("Successfully performed verifyObjectPresent action on the Object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully performed verifyObjectPresent action on the Object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not perform verifyObjectPresent action on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not perform verifyObjectPresent action on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("verifyObjectPresent(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Verify text.
	 * 
	 * @param testCase
	 *            the test case
	 * @param testCaseResult
	 *            the test case result
	 * @param testStep
	 *            the test step
	 * @param stepResult
	 *            the step result
	 * @param param
	 *            the param
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void verifyText(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult, Param param)
			throws InterruptedException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("verifyText(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String monkeyID = getObjectsForAction(testStep, param).getIdentifier();
		String component = getValueBig(param);
		String action = "verify";
		String data = getParamValue(param);
		String testDataSet[] = null;
		if (data != null) {
			data = data.replace("[", "");
			data = data.replace("]", "");
			testDataSet = data.split(",");
		}
		LOG.info("Performing verifyObjectPresent action on the object: " + component);
		try {
			Object object = getCompObject(component, monkeyID);
			if (ArrayUtils.contains(allMethods, action)) {
				if (data == null || data.length() < 1) {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object);
				} else {
					Method actionMethd1 = object.getClass().getMethod(action, String.class);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					actionMethd1.invoke(object, data);
				}
			}
			LOG.info("Successfully performed verifyObjectPresent action on the Object: " + component);
			TfwCoreUtls.stepRslt(stepResult, 1, "Successfully performed verifyObjectPresent action on the Object: " + component, null);
		} catch (Exception e) {
			LOG.error("Could not perform verifyObjectPresent action on the object: " + component + e, e);
			TfwCoreUtls.stepRslt(stepResult, 0, "Could not perform verifyObjectPresent action on the object: " + component + e, e.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("verifyText(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#startTestPlan(com.exilant
	 * .tfw.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan)
	 */
	@Override
	public void startTestPlan(Scheduler scheduler, TestPlan obj) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#startTestSuite(com.exilant
	 * .tfw.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan,
	 * com.sssoft.isatt.data.pojo.input.TestSuite,
	 * com.sssoft.isatt.data.pojo.output.TestSuiteResult)
	 */
	@Override
	public void startTestSuite(Scheduler scheduler, TestPlan obj, TestSuite suite, TestSuiteResult tstSmmryFromRemote) {
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
	@Override
	public void startTestCase(Scheduler scheduler, TestPlan obj, TestSuite suite, TestSuiteResult tstSmmryFromCache) {
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
	@Override
	public void endTestCase(Scheduler scheduler, TestPlan plan, TestSuite suite, TestCase testCase, TestCaseResult caseResult, TestStepResult lastStep) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#getConfigPath()
	 */
	@Override
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
	@Override
	public void setConfigPath(String configPath) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.pojo.def.Runner#clone2()
	 */
	@Override
	public Runner clone2() throws CloneNotSupportedException {
		return null;
	}

}
