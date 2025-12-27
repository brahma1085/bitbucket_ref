package com.tfw.verifiers;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.tfw.verifiers.bean.CaptureProps;
import com.tfw.verifiers.bean.VerifyResult;
import com.tfw.verifiers.parsers.CommonParser;

/**
 * The Class AlphaNumeric.
 */
public class AlphaNumeric {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AlphaNumeric.class);

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
			String alphaNum = "^[a-zA-Z0-9]*$";
			Pattern p = Pattern.compile(alphaNum);
			if (p.matcher(tagValue).matches()) {
				verifyResult.setResult(true);
				stringBuffer.append("\n" + "it is alpha numeric. ");
			} else {
				stringBuffer.append("\n" + "it is not alpha numeric. ");
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