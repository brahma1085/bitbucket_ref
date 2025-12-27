package com.exilant.tfw.runner.http;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.exilant.grx.iTAP.plugin.DeviceID;
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
import com.exilant.tfw.util.LangUtils;
import com.exilant.tfw.util.https.UtlSslFactory;
import com.tfw.verifierRunner.Verifier;

/**
 * The Class HttpRunner.
 */
@SuppressWarnings("deprecation")
public class HttpRunner implements Runner {
	//
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(HttpRunner.class);

	/** The methods. */
	private static volatile Map<String, Method> methods = new HashMap<String, Method>(20);
	
	/** The saved global strs. */
	private static volatile Map<String, String> savedGlobalStrs = new HashMap<String, String>();
	
	/** The template. */
	private String template;
	
	/** The replace strs. */
	private Map<String, String> replaceStrs = new HashMap<String, String>();

	/** The http req base. */
	private HttpEntityEnclosingRequestBase httpReqBase;
	
	/** The http client. */
	private HttpClient httpClient;
	
	/** The local context. */
	private HttpContext localContext;
	
	/** The response. */
	private HttpResponse response;

	/** The method. */
	private String method = "POST";
	
	/** The param from ext. */
	private ParamFromTstGenClz paramFromExt = null;

	// Agent2 agent2 = new Agent2();
	/** The http ver. */
	private ProtocolVersion httpVer = HttpVersion.HTTP_1_1;

	/** The response str. */
	private String responseStr;

	/** The saved strs. */
	private Map<String, String> savedStrs = new HashMap<String, String>();

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestPlan(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan)
	 */
	public void startTestPlan(Scheduler scheduler, TestPlan obj) {

	}

	/** The verifier. */
	private Verifier verifier = new Verifier();
	
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
	 * Gets the verifier.
	 *
	 * @return the verifier
	 */
	public Verifier getVerifier() {
		return this.verifier;
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestSuite(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.output.TestSuiteResult)
	 */
	public void startTestSuite(Scheduler scheduler, TestPlan obj, TestSuite suite, TestSuiteResult tstSmmryFromRemote) {

	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#startTestCase(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.output.TestSuiteResult)
	 */
	public void startTestCase(Scheduler scheduler, TestPlan obj, TestSuite suite, TestSuiteResult tstSmmryFromCache) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("startTestCase(Scheduler, TestPlan, TestSuite, TestSuiteResult) - start"); //$NON-NLS-1$
		}

		replaceStrs.clear();

		if (LOG.isDebugEnabled()) {
			LOG.debug("startTestCase(Scheduler, TestPlan, TestSuite, TestSuiteResult) - end"); //$NON-NLS-1$
		}
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#testStep(com.exilant.tfw.pojo.input.TestCase, com.exilant.tfw.pojo.output.TestCaseResult, com.exilant.tfw.pojo.input.TestStep, com.exilant.tfw.pojo.output.TestStepResult, com.exilant.tfw.pojo.input.Param)
	 */
	public void testStep(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		testStepResult.setResult(0);
		String action = testStep.getActions().getActionName();
		Method actionMethd = null;
		synchronized (methods) {
			actionMethd = methods.get(action);
			if (actionMethd == null) {
				try {
					if ("verify".equals(action)) {
						actionMethd = HttpRunner.this.getVerifier().getClass()
								.getMethod(action, TestCase.class, TestCaseResult.class, TestStep.class, TestStepResult.class, Param.class);
					} else if ("genDeviceIDToCSV".equals(action) || "genOneDeviceID".equals(action)) {
						actionMethd = HttpRunner.this.getDeviceID().getClass()
								.getMethod(action, TestCase.class, TestCaseResult.class, TestStep.class, TestStepResult.class, Param.class);
					} else {
						actionMethd = this.getClass().getMethod(action, TestCase.class, TestCaseResult.class, TestStep.class, TestStepResult.class,
								Param.class);
					}
					methods.put(action, actionMethd);
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
					if ("verify".equals(action)) {
						actionMethd.invoke(HttpRunner.this.getVerifier(), testCase, testCaseResult, testStep, testStepResult, param);
					} else if ("genDeviceIDToCSV".equals(action) || "genOneDeviceID".equals(action)) {
						actionMethd.invoke(HttpRunner.this.getDeviceID(), testCase, testCaseResult, testStep, testStepResult, param);
					} else {
						Map<String, String> storedDeviceIds = deviceID.getStoredValues();
						String paramValue = param.getTestParamData().getParamValue();
						if (storedDeviceIds != null && storedDeviceIds.get(paramValue) != null) {
							param.getTestParamData().setParamValue(storedDeviceIds.get(paramValue));
						}
						actionMethd.invoke(this, testCase, testCaseResult, testStep, testStepResult, param);
					}
				} catch (Exception e) {
					LOG.warn("Calling action Err :" + e, e);
					testStepResult.setComment("Bad action - ERR :" + e);
				}
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
	 * @param obj the obj
	 * @param suite the suite
	 * @param testCase the test case
	 * @param tstSmmryFromCache the tst smmry from cache
	 * @param testStep the test step
	 */
	public void endTestCase(Scheduler scheduler, TestPlan obj, TestSuite suite, TestCase testCase, TestSuiteResult tstSmmryFromCache,
			TestStepResult testStep) {
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
	 * Inits the.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 */
	public void init(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("init(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		httpReqBase = new HttpPost();
		stepRslt(testStepResult, 1, "", null);

		if (LOG.isDebugEnabled()) {
			LOG.debug("init(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Inits the context.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 */
	public void initContext(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("initContext(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		localContext = new BasicHttpContext();
		stepRslt(testStepResult, 1, "", null);

		if (LOG.isDebugEnabled()) {
			LOG.debug("initContext(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Cookie jar init.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 */
	public void cookieJarInit(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("cookieJarInit(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		stepRslt(testStepResult, 1, "", null);

		if (LOG.isDebugEnabled()) {
			LOG.debug("cookieJarInit(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Noop.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 */
	public void noop(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("noop(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		try {
			stepRslt(testStepResult, 1, "", null);
		} catch (Exception e) {
			LOG.warn("noop:" + e, e);
			TfwCoreUtls.stepFillDetail(testStepResult, "noop was unsuccessfull ", "noop err :" + e, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("noop(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Cookie put.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 */
	public void cookiePut(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("cookiePut(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		try {
			stepRslt(testStepResult, 1, "", null);
		} catch (Exception e) {
			LOG.warn("cookiePut :" + e, e);
			TfwCoreUtls.stepFillDetail(testStepResult, "cookiePut was unsuccessfull", "cookiePut err :" + e, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("cookiePut(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Header put.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 */
	public void headerPut(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("headerPut(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		try {
			final int tagSplLen = 4;
			String name = param.getTestParamData().getValueBig();
			String v = param.getTestParamData().getParamValue();
			if (v == null || name == null) {
				TfwCoreUtls.stepFillDetail(testStepResult, "header put null value or name.", null, null);
			}
			int startX = v.indexOf("{|--");
			int endX = -1;
			if (startX > -1) {
				endX = v.indexOf("--|}", startX + tagSplLen);
			}

			while (endX > -1) {
				String key = v.substring(startX + tagSplLen, endX);
				String vv = replaceStrs.get(key);
				v = v.substring(0, startX) + vv + v.substring(endX + tagSplLen);
				startX = v.indexOf("{|--", endX + tagSplLen);
				if (startX > -1) {
					endX = v.indexOf("--|}", startX + tagSplLen);
				} else {
					endX = -1;
				}
			}
			LOG.info("val :" + v + ";");
			httpReqBase.setHeader(name, v);
			final String m1 = "Name :" + name + ", value :" + v + ".";
			stepRslt(testStepResult, 1, m1, null);
		} catch (Exception e) {
			LOG.warn("headerPut :" + e, e);
			TfwCoreUtls.stepFillDetail(testStepResult, "header put was unsuccessfull", "headerPut err :" + e, e);

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("headerPut(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Template.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 */
	public void template(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("template(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		try {
			this.template = param.getTestParamData().getValueBig();
			stepRslt(testStepResult, 1, "", null);
		} catch (Exception e) {
			LOG.warn("template :" + e, e);
			TfwCoreUtls.stepFillDetail(testStepResult, "template was unsuccessfull", "template err :" + e, e);

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("template(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Parameter.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 */
	public void parameter(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("parameter(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		try {
			String nm = param.getTestParamData().getValueBig();
			String v = param.getTestParamData().getParamValue();
			replaceStrs.put(nm, v);
			stepRslt(testStepResult, 1, "", null);
		} catch (Exception e) {
			LOG.warn("parameter :" + e, e);
			TfwCoreUtls.stepFillDetail(testStepResult, "parameter was unsuccessfull", "parameter err :" + e, e);

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("parameter(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Submit req.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 */
	public void submitReq(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("submitReq(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		StringBuilder detCmt = new StringBuilder();
		try {
			final String urls = param.getTestParamData().getValueBig();
			AbstractHttpEntity enReq = null;
			final String sendType = param.getTestParamData().getParamValue();
			if ("r".equals(sendType)) {
				LOG.info("raw");
				String re = makeRawReq(testStep);
				enReq = new StringEntity(re, "UTF8");
				enReq.setContentEncoding("UTF8");
				enReq.setContentType("text/xml");
			}
			LOG.info("--- sendType " + sendType + " ---");
			String sReq = EntityUtils.toString(enReq);
			detCmt.append("Request:\n");
			detCmt.append(sReq);
			detCmt.append("\n");
			LOG.info(sReq);
			LOG.info("---");
			httpReqBase.setEntity(enReq);
			URI uri = new URI(urls);
			httpReqBase.setURI(uri);

			URL urlTo = new URL(urls);
			String hostNm = urlTo.getHost();
			int port = urlTo.getPort();
			String sche = urlTo.getProtocol();
			LOG.info("scheme/ proto :" + sche + ", url :" + urlTo);
			HttpHost httpHost = new HttpHost(hostNm, port, sche);
			response = this.httpClient.execute(httpHost, httpReqBase, localContext);
			LOG.info("After req");
			responseStr = EntityUtils.toString(response.getEntity());
			LOG.info("re :" + responseStr);

			detCmt.append("Response:\n");
			detCmt.append(responseStr);
			detCmt.append("\n");

			testCaseResult.setResponse(responseStr);
			testCaseResult.setRequest(sReq);
			stepRslt(testStepResult, 1, "", null);

		} catch (Exception e) {
			LOG.warn("submitReq :" + e, e);// stepResult.setDetailMsgs
			TfwCoreUtls.stepFillDetail(testStepResult, "submitReq was unsuccessfull", "submitReq err :" + e, e);

		}
		// if there was an error after request creation, would see error first
		// then request.
		// TfwCoreUtls.stepFillDetail(stepResult, detCmt.toString(), null);

		if (LOG.isDebugEnabled()) {
			LOG.debug("submitReq(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Make raw req.
	 *
	 * @param step the step
	 * @return the string
	 */
	public String makeRawReq(final TestStep step) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("makeRawReq(TestStep) - start"); //$NON-NLS-1$
		}

		final StringBuilder req = new StringBuilder().append(template);
		for (final String key : replaceStrs.keySet()) {
			final String fnd = "{|--" + key + "--|}";
			int i = req.indexOf(fnd);
			while (i > -1) {
				final String val = replaceStrs.get(key);
				if (val != null) {
					req.replace(i, i + fnd.length(), val);
					i = req.indexOf(fnd, i + val.length());
				} else {
					i = -1;
					LOG.warn("replace val not found for :" + key);
				}
			}
		}
		String returnString = req.toString();
		if (LOG.isDebugEnabled()) {
			LOG.debug("makeRawReq(TestStep) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Inits the ssl.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 */
	public void initSsl(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("initSsl(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);

			UtlSslFactory sf = new UtlSslFactory(trustStore, true);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

			this.httpClient = new DefaultHttpClient(ccm, params);
			stepRslt(testStepResult, 1, "Ssl accept all (only for lan & trusted sites use)", null);
		} catch (Exception e) {
			LOG.warn("initSsl :" + e, e);// stepResult.setDetailMsgs
			TfwCoreUtls.stepFillDetail(testStepResult, "initSsl was unsuccessfull", "initSsl err :" + e, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("initSsl(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Step rslt.
	 *
	 * @param stepResult the step result
	 * @param relt the relt
	 * @param summary the summary
	 * @param detail the detail
	 */
	public static void stepRslt(TestStepResult stepResult, int relt, String summary, String detail) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("stepRslt(TestStepResult, int, String, String) - start"); //$NON-NLS-1$
		}

		stepResult.setResult(relt);
		stepResult.setComment(summary);
		if (detail != null) {
			StringBuilder sb = new StringBuilder();
			String gg = stepResult.getComment();
			if (gg != null) {
				sb.append(gg).append("\n");
			}
			sb.append(detail);
			stepResult.setComment(sb.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("stepRslt(TestStepResult, int, String, String) - end"); //$NON-NLS-1$
		}
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
	 * @see com.exilant.tfw.pojo.def.TfwRemoteInterface#endTestCase(com.exilant.tfw.pojo.Scheduler, com.exilant.tfw.pojo.input.TestPlan, com.exilant.tfw.pojo.input.TestSuite, com.exilant.tfw.pojo.input.TestCase, com.exilant.tfw.pojo.output.TestCaseResult, com.exilant.tfw.pojo.output.TestStepResult)
	 */
	public void endTestCase(Scheduler scheduler, TestPlan plan, TestSuite suite, TestCase testCase, TestCaseResult caseResult, TestStepResult lastStep) {
	}

	/**
	 * Output param.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 */
	public void outputParam(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("outputParam(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		try {
			stepRslt(testStepResult, 1, "", null);
		} catch (Exception e) {
			LOG.warn("outputParam:" + e, e);
			TfwCoreUtls.stepFillDetail(testStepResult, "outputParam was unsuccessfull", "outputParam err :" + e, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("outputParam(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Apply saved.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 */
	public void applySaved(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("applySaved(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		String sp = param.getTestParamData().getValueBig();
		String op = param.getTestParamData().getParamValue();
		try {
			StringTokenizer st = new StringTokenizer(sp, "|");
			String from = null;
			if (st.countTokens() > 1) {
				from = st.nextToken();
			}
			if (from == null) {
				from = "l";
			}
			String key = st.nextToken();
			// String s = replaceStrs.get(sp);
			String val = null;
			if ("g".equals(from)) {
				val = savedGlobalStrs.get(key);
			} else {
				val = savedStrs.get(key);
			}
			String old = replaceStrs.put(op, val);
			final String log1 = "applySaved For param :" + op + "; got " + val + "; old :" + old + "; key :" + key + " from " + from;
			LOG.info(log1);
			stepRslt(testStepResult, 1, "", log1);
		} catch (Exception e) {
			// log details so even if test plan is changed we have detail params
			final String log2 = "applySaved:" + e + ", sp :" + sp + ", op :" + op;
			LOG.warn(log2, e);
			TfwCoreUtls.stepFillDetail(testStepResult, "applySaved was unsuccessfull", log2, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("applySaved(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	// savedStrs
	/**
	 * Save response.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 */
	public void saveResponse(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("saveResponse(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		try {
			String stpPrm = param.getTestParamData().getValueBig();
			String objParam = param.getTestParamData().getParamValue();
			StringTokenizer st = new StringTokenizer(stpPrm, "|");
			List<String> toks = new ArrayList<String>();
			while (st.hasMoreTokens()) {
				toks.add(st.nextToken());
			}
			int x1 = 0, x2 = 0, x3 = 0;
			String val = null;
			String endTag = null;
			String first = null;
			String find = null;
			String ele1 = null;
			boolean saved = false;
			String saveTo = null;
			if (toks.size() >= 2) {
				first = toks.get(0);
				find = toks.get(1);
				if (toks.size() >= 3) {
					saveTo = toks.get(2);
				}
				if ("x".equals(first)) {
					ele1 = "<" + find;
					x1 = responseStr.indexOf(find);
					if (x1 > -1) {
						x2 = responseStr.indexOf(">", x1 + find.length());
						if (x2 > -1) {
							endTag = "</" + find;
							x3 = responseStr.indexOf(endTag, x2 + 1);
							if (x3 > x2) {
								val = responseStr.substring(x2 + 1, x3);
								saved = true;
							}
						}
					}
				} else if ("j".equals(first)) {
					try {
						LOG.info("trying json save");
						JSONObject json = new JSONObject(responseStr);
						val = json.getString(find);
						saved = true;
					} catch (Exception e) {
						LOG.warn("Json fail (will try mnaul " + e, e);
						ele1 = "\"" + find + "\"";
						x1 = responseStr.indexOf(ele1);
						if (x1 > -1) {
							x2 = responseStr.indexOf("\n", x1 + 1);
							if (x2 > (x1 + ele1.length())) {
								String line = responseStr.substring(x1 + ele1.length(), x2);
								x2 = line.indexOf('\"');
								x3 = line.lastIndexOf('\"');
								if (x2 > -1 && x3 > x2) {
									val = line.substring(x2 + 1, x3);
									saved = true;
								}
							}
						}
					}
				}
			}

			if (saved) {
				if (saveTo == null) {
					saveTo = "l";
				}
				if ("g".equals(saveTo)) {
					savedGlobalStrs.put(objParam, val);
				} else {
					savedStrs.put(objParam, val);
				}
				LOG.info("saved key :" + objParam + "; v :" + val + "; save to :" + saveTo);
				stepRslt(testStepResult, 1, "okay :" + val, null);
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append("not saved sp :" + stpPrm + "]");
				sb.append("op :" + objParam + "]");

				sb.append("endTag :" + endTag + "]");
				sb.append("first :" + first + "]");
				sb.append("find :" + find + "]");

				sb.append("x1 :" + x1 + "]");
				sb.append("x2 :" + x2 + "]");
				sb.append("x3 :" + x3 + "]");
				sb.append("ele1 :" + ele1 + "]");
				LOG.info(sb.toString());
				stepRslt(testStepResult, 0, "Not found", sb.toString());
			}
		} catch (Exception e) {
			LOG.warn("saveResponse :" + e, e);
			TfwCoreUtls.stepFillDetail(testStepResult, "saveResponse was unsuccessfull", "saveResponse err :" + e, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("saveResponse(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Param from generator.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 */
	public void paramFromGenerator(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("paramFromGenerator(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		if (paramFromExt == null) {
			paramFromExt = new ParamFromTstGenClz();
		}
		paramFromExt.testStep(testCase, testCaseResult, testStep, testStepResult, param, replaceStrs);

		if (LOG.isDebugEnabled()) {
			LOG.debug("paramFromGenerator(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Snooze.
	 *
	 * @param testCase the test case
	 * @param testCaseResult the test case result
	 * @param testStep the test step
	 * @param testStepResult the test step result
	 * @param param the param
	 */
	public void snooze(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("snooze(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - start"); //$NON-NLS-1$
		}

		try {
			String s = param.getTestParamData().getParamValue();
			if (s != null && s.length() != 0) {
				int i = Integer.parseInt(s);
				LangUtils.sleep(i);
			}
			stepRslt(testStepResult, 1, "okay", null);
		} catch (Exception e) {
			LOG.warn("snooze:" + e, e);
			TfwCoreUtls.stepFillDetail(testStepResult, "snooze was unsuccessfull", "snooze err :" + e, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("snooze(TestCase, TestCaseResult, TestStep, TestStepResult, Param) - end"); //$NON-NLS-1$
		}
	}

}