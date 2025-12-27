package com.sssoft.isatt.adapters.verifiers.validator;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.sssoft.isatt.adapters.verifiers.bean.CaptureProps;
import com.sssoft.isatt.adapters.verifiers.bean.VerifyResult;
import com.sssoft.isatt.adapters.verifiers.parsers.CommonParser;

/**
 * The Class Numeric.
 */
public class Numeric {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(Numeric.class);

	/**
	 * Compare request.
	 *
	 * @param request the request
	 * @param response the response
	 * @param captureProps the capture props
	 * @param verifyResult the verify result
	 * @return the verify result
	 */
	public VerifyResult compareRequest(String request, String response, CaptureProps captureProps, VerifyResult verifyResult) {
		LOG.debug("received request : " + request);
		StringBuffer stringBuffer = new StringBuffer();
		verifyResult = CommonParser.fetchTagValue(response, captureProps);
		String tagValue = verifyResult.getRequiredResult();
		String actualKey = captureProps.getActionName();
		if (request != null) {
			stringBuffer.append("\n" + " value to be compared from file : " + request + ". ");
		}
		if (tagValue != null) {
			stringBuffer.append("\n" + "value in response : " + tagValue + ". ");
			String numeric = "^[0-9]*$";
			Pattern p = Pattern.compile(numeric);
			if (p.matcher(tagValue).matches()) {
				verifyResult.setResult(true);
				stringBuffer.append("\n" + "it is numeric. ");
			} else {
				stringBuffer.append("\n" + "it is not  numeric. ");
			}
		} else {
			LOG.error(actualKey + " is not present at given xPATH");
			if (actualKey != null) {
				stringBuffer.append("\n" + actualKey + "is not present at given xPATH. ");
			} else {
				stringBuffer.append("\n" + "required value is not present at given xPATH. ");
			}
		}
		verifyResult.setComment(verifyResult.getComment() + " " + stringBuffer.toString());

		if (LOG.isDebugEnabled()) {
			LOG.debug("compareRequest(String, String, CaptureProps, VerifyResult) - end"); //$NON-NLS-1$
		}
		return verifyResult;
	}

}