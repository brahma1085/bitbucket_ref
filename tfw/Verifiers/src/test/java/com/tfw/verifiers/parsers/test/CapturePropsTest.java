package com.tfw.verifiers.parsers.test;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;

import com.tfw.verifiers.bean.CaptureProps;

/**
 * The Class CapturePropsTest.
 */
public class CapturePropsTest {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(CapturePropsTest.class);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		Properties prop = new Properties();
		prop.load(new FileInputStream("/data/tfw/config/apiPro/cancelOrder.properties"));
		String actionName = null;
		String typeName = null;
		String paramName = null;
		String instanceType = null;
		String insParamName = null;
		int actionCnt = 1;
		do {
			CaptureProps captureProps = new CaptureProps();
			String actionKey = "lookFor" + actionCnt;
			actionName = prop.getProperty(actionKey);
			captureProps.setActionName(actionName);
			captureProps.setResponseType(prop.getProperty("format"));
			captureProps.setIsArray(Integer.parseInt(prop.getProperty("array.enable")));
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

					String xPathMissed = "lookFor" + actionCnt + ".xPath.ifmissing";
					String xPthMisd = prop.getProperty(xPathMissed);
					captureProps.setxPathMissed(xPthMisd);

					if (typeName != null) {
						int insCount = 1;
						do {
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
										LOG.info(captureProps);
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
										LOG.info(captureProps);
									}
									paramCnt++;
								} while (paramName != null);
							}
							insCount++;
						} while (instanceType != null);
					}
					typeCnt++;
				} while (typeName != null);
			}
			actionCnt++;
		} while (actionName != null);

		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}
}
