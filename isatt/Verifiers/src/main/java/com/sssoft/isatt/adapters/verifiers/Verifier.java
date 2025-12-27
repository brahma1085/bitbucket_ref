package com.sssoft.isatt.adapters.verifiers;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.sssoft.isatt.adapters.verifiers.bean.CaptureProps;
import com.sssoft.isatt.adapters.verifiers.bean.VerifyResult;
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
import com.sssoft.isatt.utils.bean.UtlConf;

/**
 * The Class Verifier.
 */
public class Verifier implements Runner {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(Verifier.class);

	static {
		BasicConfigurator.configure();
	}

	/**
	 * Gets the utl conf.
	 *
	 * @param s the s
	 * @param testCase the test case
	 * @return the utl conf
	 */
	public String getUtlConf(String s, TestCase testCase) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUtlConf(String, TestCase) - start"); //$NON-NLS-1$
		}

		String prj = testCase.getCaseName();
		s = s.replace("{r}", UtlConf.getApiConfigsRoot(prj).trim());
		String s1 = s.replace(" ", "");

		if (LOG.isDebugEnabled()) {
			LOG.debug("getUtlConf(String, TestCase) - end"); //$NON-NLS-1$
		}
		return s1;
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#startTestPlan(com.sssoft.isatt.data.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan)
	 */
	@Override
	public void startTestPlan(Scheduler scheduler, TestPlan plan) {
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#testStep(com.sssoft.isatt.data.pojo.input.TestCase, com.sssoft.isatt.data.pojo.output.TestCaseResult, com.sssoft.isatt.data.pojo.input.TestStep, com.sssoft.isatt.data.pojo.output.TestStepResult, com.sssoft.isatt.data.pojo.input.Param)
	 */
	@Override
	public void testStep(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		testStepResult.setResult(0);// make it true in each method if it
									// passes
		String action = testStep.getActions().getActionName();
		try {
			Method method = Verifier.class.getDeclaredMethod(action, Scheduler.class, TestPlan.class, TestSuite.class, TestCase.class,
					TestCaseResult.class, TestStep.class, TestStepResult.class, TestSuiteResult.class);

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
	 * Gets the param value.
	 *
	 * @param testStep the test step
	 * @return the param value
	 */
	private String getParamValue(TestStep testStep) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamValue(TestStep) - start"); //$NON-NLS-1$
		}

		String result = null;
		List<ParamGroup> paramGroups = testStep.getParamGroup();
		Iterator<ParamGroup> paramGroupItr = paramGroups.iterator();
		while (paramGroupItr.hasNext()) {
			ParamGroup paramGroup = (ParamGroup) paramGroupItr.next();
			List<Param> params = paramGroup.getParam();
			Iterator<Param> paramItr = params.iterator();
			while (paramItr.hasNext()) {
				Param param = (Param) paramItr.next();
				result = param.getTestParamData().getParamValue();
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamValue(TestStep) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Verify.
	 *
	 * @param testCase the test case
	 * @param caseResult the case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 */
	public void verify(TestCase testCase, TestCaseResult caseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		LOG.info("In verifiers");
		StringBuffer stringBuffer = new StringBuffer();
		String propertyPath = getParamValue(testStep);
		String abPath = getUtlConf(propertyPath, testCase);
		LOG.info("property file path : " + abPath);
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(abPath));
			String actionName = null;
			String typeName = null;
			String paramName = null;
			String instanceType = null;
			String insParamName = null;
			int actionCnt = 1;
			List<TestStepResult> testStepResults = new ArrayList<TestStepResult>();
			do {
				CaptureProps captureProps = new CaptureProps();
				String actionKey = "lookFor" + actionCnt;
				actionName = prop.getProperty(actionKey);
				captureProps.setActionName(actionName);
				captureProps.setResponseType(prop.getProperty("format"));
				String isArray = prop.getProperty("array.enable");
				if (isArray != null) {
					captureProps.setIsArray(Integer.parseInt(isArray));
				}
				captureProps.setArrayCount(prop.getProperty("array.count"));
				if (actionName != null) {
					int typeCnt = 1;
					do {
						String typeKey = actionKey + ".typ" + typeCnt;
						typeName = prop.getProperty(typeKey);
						captureProps.setActionType(typeName);

						String xPath = "lookFor" + actionCnt + ".xPath";
						String xPth = prop.getProperty(xPath);
						captureProps.setxPath(xPth);

						String reqXpath = "lookFor" + actionCnt + ".reqXpath";
						String reqXpth = prop.getProperty(reqXpath);
						captureProps.setReqXpath(reqXpth);

						String xPathMissed = "lookFor" + actionCnt + ".xPath.ifmissing";
						String xPthMisd = prop.getProperty(xPathMissed);
						captureProps.setxPathMissed(xPthMisd);

						String arrayGoto = "lookFor" + actionCnt + ".array.goto";
						String arrGoto = prop.getProperty(arrayGoto);
						if (arrGoto != null && arrGoto.length() > 0) {
							captureProps.setArrayGoto(Integer.parseInt(arrGoto));
						}

						if (typeName != null) {
							String callVerifiers1 = callVerifiers(captureProps, testCase, caseResult, testStep, testStepResult, param);
							if (callVerifiers1 != null) {
								stringBuffer.append(callVerifiers1);
								if (testStepResult.getResult() == 1) {
									testStepResults.add(testStepResult);
								} else {
									break;
								}
							}
							int insCount = 1;
							do {
								captureProps.setArrayInsCount(insCount);
								String instanceKey = typeKey + ".array.ins" + insCount;
								instanceType = prop.getProperty(instanceKey);
								captureProps.setArrayInsType(instanceType);
								if (instanceType != null) {
									int insParamCnt = 1;
									do {
										String insParamKey = instanceKey + ".p" + insParamCnt;
										insParamName = prop.getProperty(insParamKey);
										captureProps.setArratInsParam(insParamName);
										if (insParamName != null) {
											String callVerifiers = callVerifiers(captureProps, testCase, caseResult, testStep, testStepResult, param);
											if (callVerifiers != null) {
												stringBuffer.append(callVerifiers);
											}
											if (testStepResult.getResult() == 1) {
												testStepResults.add(testStepResult);
											} else {
												break;
											}
										}
										insParamCnt++;
									} while (insParamName != null);
								} else {
									int paramCnt = 1;
									do {
										String paramKey = typeKey + ".p" + paramCnt;
										paramName = prop.getProperty(paramKey);
										captureProps.setParamFormat(paramName);
										if (paramName != null) {
											String callVerifiers = callVerifiers(captureProps, testCase, caseResult, testStep, testStepResult, param);
											if (callVerifiers != null) {
												stringBuffer.append(callVerifiers);
											}
											if (testStepResult.getResult() == 1) {
												testStepResults.add(testStepResult);
											} else {
												break;
											}
										}
										paramCnt++;
									} while (paramName != null);
								}
								if (testStepResult.getResult() == 1) {
									testStepResults.add(testStepResult);
								} else {
									break;
								}
								insCount++;
							} while (instanceType != null);
						}
						if (testStepResult.getResult() == 1) {
							testStepResults.add(testStepResult);
						} else {
							break;
						}
						typeCnt++;
					} while (typeName != null);
				}
				if (testStepResult.getResult() == 1) {
					testStepResults.add(testStepResult);
				} else {
					break;
				}
				actionCnt++;
			} while (actionName != null);
			caseResult.setTestStepResultsList(testStepResults);
			if (testStepResult.getComment() != null) {
				stringBuffer.append(testStepResult.getComment());
			}
			testStepResult.setComment(stringBuffer.toString());
		} catch (IOException ex) {
			LOG.error("verify(TestCase, TestCaseResult, TestStep, TestStepResult, Param)", ex); //$NON-NLS-1$

			testStepResult.setComment("Property file not found");
			testStepResult.setException("Property file not found, please give the correct path and name of propery file");
			testStepResult.setResult(0);
		} catch (Exception e) {
			LOG.error("verify(TestCase, TestCaseResult, TestStep, TestStepResult, Param)", e); //$NON-NLS-1$

			testStepResult.setComment("error occured");
			testStepResult.setException("error occured, while processing the properties file");
			testStepResult.setResult(0);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("verify(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Json text match.
	 *
	 * @param obj the obj
	 * @param suite the suite
	 * @param tcase the tcase
	 * @param caseResult the case result
	 * @param step the step
	 * @param stepResult the step result
	 * @param suiteSumamryResult the suite sumamry result
	 */
	public void jsonTextMatch(TestPlan obj, TestSuite suite, TestCase tcase, TestCaseResult caseResult, TestStep step, TestStepResult stepResult,
			TestSuiteResult suiteSumamryResult) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("jsonTextMatch(TestPlan, TestSuite, TestCase, TestCaseResult, TestStep, TestStepResult, TestSuiteResult) - start"); //$NON-NLS-1$
		}

		String value = step.getParamGroup().get(0).getParam().get(0).getParamName();
		step.getParamGroup().get(0).getParam().get(0).getParamName();
		String key = step.getStepParam();
		String response = caseResult.getResponse();
		String valOfKey;
		JSONObject json;
		try {
			json = new JSONObject(response);
			valOfKey = json.getString(key);
			if (valOfKey.equals(value)) {
				stepResult.setComment("response is matching with expected value");
				stepResult.setException("response is matching with expected value");
				stepResult.setResult(1);
			} else {
				if (valOfKey.length() > 0) {
					stepResult.setComment("response is  NOT matching with expected value and has the value   " + valOfKey);
					stepResult.setException("response is  NOT matching with expected value and has the value   " + valOfKey);
				} else {
					stepResult.setComment(key + " is  having null value");
					stepResult.setException(key + " is  having null value");
				}
				stepResult.setResult(0);
			}
		} catch (JSONException e) {
			LOG.error("jsonTextMatch(TestPlan, TestSuite, TestCase, TestCaseResult, TestStep, TestStepResult, TestSuiteResult)", e); //$NON-NLS-1$

			stepResult.setException(key + " is  NOT present in response");
			stepResult.setComment(key + " is  NOT present in response");
			stepResult.setResult(0);
		} catch (Exception e) {
			LOG.error("jsonTextMatch(TestPlan, TestSuite, TestCase, TestCaseResult, TestStep, TestStepResult, TestSuiteResult)", e); //$NON-NLS-1$

			stepResult.setException("error occured while processing jsontextmatch action");
			stepResult.setComment("error occured while processing jsontextmatch action");
			stepResult.setResult(0);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("jsonTextMatch(TestPlan, TestSuite, TestCase, TestCaseResult, TestStep, TestStepResult, TestSuiteResult) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Xml text match.
	 *
	 * @param obj the obj
	 * @param suite the suite
	 * @param tcase the tcase
	 * @param caseResult the case result
	 * @param step the step
	 * @param stepResult the step result
	 * @param suiteSumamryResult the suite sumamry result
	 */
	public void xmlTextMatch(TestPlan obj, TestSuite suite, TestCase tcase, TestCaseResult caseResult, TestStep step, TestStepResult stepResult,
			TestSuiteResult suiteSumamryResult) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("xmlTextMatch(TestPlan, TestSuite, TestCase, TestCaseResult, TestStep, TestStepResult, TestSuiteResult) - start"); //$NON-NLS-1$
		}

		String value = step.getParamGroup().get(0).getParam().get(0).getParamName();
		String key = step.getStepParam();
		String response = caseResult.getResponse();
		int count = 0;
		if (!response.contains(key)) {
			stepResult.setException(key + " is  NOT present in response");
			stepResult.setComment(key + " is  NOT present in response");
			stepResult.setResult(0);
		}
		String endloc = new StringBuilder(key).insert(0, "/").toString();
		String[] split = response.split(endloc);
		for (String value1 : split) {
			if (value1.contains(key)) {
				String[] split1 = value1.split(key);
				for (String s1 : split1) {
					if (count == 1) {
						if (s1.length() > 2) {
							String valueOfResponse = s1.substring(1, s1.length()).replace("<", "");
							if (valueOfResponse.equals(value)) {
								stepResult.setException("response is matching with expected value");
								stepResult.setComment("response is matching with expected value");
								stepResult.setResult(1);
							} else {
								stepResult.setComment("response is  NOT matching with expected value and has the value   " + value);
								stepResult.setException("response is  NOT matching with expected value and has the value   " + value);
								stepResult.setResult(0);
							}
						} else {
							stepResult.setComment("tagname is  having null value");
							stepResult.setException("tagname is  having null value");
							stepResult.setResult(0);
						}
						break;
					}
					count++;
				}
			}
			break;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("xmlTextMatch(TestPlan, TestSuite, TestCase, TestCaseResult, TestStep, TestStepResult, TestSuiteResult) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Text match.
	 *
	 * @param obj the obj
	 * @param suite the suite
	 * @param tcase the tcase
	 * @param caseResult the case result
	 * @param step the step
	 * @param stepResult the step result
	 * @param suiteSumamryResult the suite sumamry result
	 */
	public void textMatch(TestPlan obj, TestSuite suite, TestCase tcase, TestCaseResult caseResult, TestStep step, TestStepResult stepResult,
			TestSuiteResult suiteSumamryResult) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("textMatch(TestPlan, TestSuite, TestCase, TestCaseResult, TestStep, TestStepResult, TestSuiteResult) - start"); //$NON-NLS-1$
		}

		try {
			String value = step.getParamGroup().get(0).getParam().get(0).getParamName();
			String key = step.getStepParam();
			String response = caseResult.getResponse();
			stepResult.setResult(0);
			if (response.contains(key) && response.contains(value) && (key != null) && (value != null)) {
				stepResult.setComment("both stepParam  and paramData values is present in response");
				stepResult.setException("both stepParam  and paramData valuesis present in response");
				stepResult.setResult(1);
			} else if (response.contains(key) && !response.contains(value)) {
				stepResult.setComment("stepParam value is present in response, paramData value is Not present in response");
				stepResult.setException("stepParam value is present in response, paramData value is Not present in response");
			} else if (!response.contains(key) && response.contains(value)) {
				stepResult.setComment("stepParam value is Not present in response and paramData value is present in response");
				stepResult.setException("stepParam value is Not present in response and paramData value is present in response");
			} else {
				stepResult.setComment("both stepParam  and paramData values is NOT present in response");
				stepResult.setException("both stepParam  and paramData values is NOT present in response");
			}
		} catch (Exception e) {
			LOG.error("textMatch(TestPlan, TestSuite, TestCase, TestCaseResult, TestStep, TestStepResult, TestSuiteResult)", e); //$NON-NLS-1$

			stepResult.setException("error occured while processing textmatch action");
			stepResult.setComment("error occured while processing textmatch action");
			stepResult.setResult(0);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("textMatch(TestPlan, TestSuite, TestCase, TestCaseResult, TestStep, TestStepResult, TestSuiteResult) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Call verifiers.
	 *
	 * @param captureProps the capture props
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 * @return the string
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String callVerifiers(CaptureProps captureProps, TestCase testCase, TestCaseResult testCaseResult, TestStep testStep,
			TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("callVerifiers(CaptureProps, TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		StringBuffer stringBuffer = new StringBuffer();
		VerifyResult verifyResult = new VerifyResult();
		String response = testCaseResult.getResponse();
		String compareRequest = testCaseResult.getRequest();
		String pactionName = captureProps.getActionName();
		String ptype = captureProps.getActionType();
		String pparam = captureProps.getParamFormat();
		Class forName = null;
		try {
			if ((("FixedValues".equals(ptype) || "DateFormat".equals(ptype) || "EmailVerify".equals(ptype)) && pparam == null)) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("callVerifiers(CaptureProps, TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
				}
				return null;
			}
			if (pactionName != null && response.contains(pactionName.trim())) {
				stringBuffer.append("\n" + pactionName + " is present in response \n" + " and verifing for : " + ptype + ". ");
				try {
					if ("Verifier".equalsIgnoreCase(ptype)) {
						forName = Class.forName("com.sssoft.isatt.adapters.verifiers." + ptype);
					} else {
						forName = Class.forName("com.sssoft.isatt.adapters.verifiers.validator." + ptype);
					}
				} catch (ClassNotFoundException e) {
					LOG.error("callVerifiers(CaptureProps, TestCase, TestCaseResult, TestStep, TestStepResult, Param)", e); //$NON-NLS-1$

					stringBuffer.append("validation action not found");
					LOG.info("Verification action not found : " + e, e);
					return stringBuffer.toString();
				}
				LOG.info("class to be invoked : " + forName);
				testStep.getParamGroup().get(0).getParam().get(0).setParamName(pactionName);
				Object obj = forName.newInstance();
				Method method = forName.getMethod("compareRequest", String.class, String.class, CaptureProps.class, VerifyResult.class);
				testStep.setDescription(pparam);
				if (ptype != null && ("CompareJsonRequestValue".equalsIgnoreCase(ptype) || "CompareWSDLRequestValue".equalsIgnoreCase(ptype))) {
					if (compareRequest != null) {
						verifyResult = (VerifyResult) method.invoke(obj, compareRequest, response, captureProps, verifyResult);
					} else {
						stringBuffer.append("\n" + ptype + " has received invalid request as " + compareRequest + ". ");
					}
				} else {
					verifyResult = (VerifyResult) method.invoke(obj, testStep.getDescription(), response, captureProps, verifyResult);
				}
			} else {
				if (pactionName != null && ptype != null) {
					stringBuffer.append("\n" + pactionName + "  is  not present in response \n" + "so cannot verify for : " + ptype + ". ");
				} else {
					stringBuffer.append("\n" + "the required values are not present in response. ");
				}
			}
			String comment = verifyResult.getComment();
			if (comment != null) {
				stringBuffer.append("\n" + comment + ". ");
				testStepResult.setComment("\n" + comment);
			}
			if (verifyResult.isResult()) {
				testStepResult.setResult(1);
			} else {
				testStepResult.setResult(0);
			}
			LOG.debug("test step result after verification : " + testStepResult.getResult());
		} catch (InvocationTargetException e) {
			LOG.info("configuration issue. not able to find the dependencies" + e, e);
			stringBuffer.append("configuration issue. not able to find the dependencies");
		} catch (Exception e) {
			LOG.info("Verification action not found : " + e, e);
		}
		String returnString = stringBuffer.toString();
		if (LOG.isDebugEnabled()) {
			LOG.debug("callVerifiers(CaptureProps, TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#endTestCase(com.sssoft.isatt.data.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan, com.sssoft.isatt.data.pojo.input.TestSuite, com.sssoft.isatt.data.pojo.input.TestCase, com.sssoft.isatt.data.pojo.output.TestCaseResult, com.sssoft.isatt.data.pojo.output.TestStepResult)
	 */
	@Override
	public void endTestCase(Scheduler scheduler, TestPlan plan, TestSuite suite, TestCase testCase, TestCaseResult caseResult, TestStepResult lastStep) {

	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#getConfigPath()
	 */
	@Override
	public String getConfigPath() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#setConfigPath(java.lang.String)
	 */
	@Override
	public void setConfigPath(String configPath) {

	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.Runner#clone2()
	 */
	@Override
	public Runner clone2() throws CloneNotSupportedException {
		return null;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public static String getVersion() {
		return "1.5.6";
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#startTestSuite(com.sssoft.isatt.data.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan, com.sssoft.isatt.data.pojo.input.TestSuite, com.sssoft.isatt.data.pojo.output.TestSuiteResult)
	 */
	@Override
	public void startTestSuite(Scheduler scheduler, TestPlan testPlan, TestSuite testSuite, TestSuiteResult testSuiteResult) {
	}

	/* (non-Javadoc)
	 * @see com.sssoft.isatt.data.pojo.def.TfwRemoteInterface#startTestCase(com.sssoft.isatt.data.pojo.Scheduler, com.sssoft.isatt.data.pojo.input.TestPlan, com.sssoft.isatt.data.pojo.input.TestSuite, com.sssoft.isatt.data.pojo.output.TestSuiteResult)
	 */
	@Override
	public void startTestCase(Scheduler scheduler, TestPlan testPlan, TestSuite testSuite, TestSuiteResult testSuiteResult) {
	}
}
