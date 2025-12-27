package com.tfw.verifiers;

import org.apache.log4j.Logger;

import com.tfw.verifiers.bean.CaptureProps;
import com.tfw.verifiers.bean.VerifyResult;
import com.tfw.verifiers.parsers.CommonParser;

/**
 * The Class CompareWSDLRequestValue.
 */
public class CompareWSDLRequestValue {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(CompareWSDLRequestValue.class);

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
		captureProps.setCompResponse(response);
		captureProps.setCompRequest(null);
		verifyResult = CommonParser.fetchTagValue(response, captureProps);
		String resTagValue = verifyResult.getRequiredResult();
		captureProps.setCompResponse(null);
		captureProps.setCompRequest(request);
		verifyResult = CommonParser.fetchTagValue(request, captureProps);
		String reqTagValue = verifyResult.getRequiredResult();
		String actualKey = captureProps.getActionName();
		if (request != null) {
			stringBuffer.append("\n" + " value to be compared from file : " + request + ". ");
		}
		if (resTagValue != null && reqTagValue != null) {
			stringBuffer.append("\n" + "value in response : " + resTagValue + ". ");
			if (resTagValue.equals(reqTagValue)) {
				verifyResult.setResult(true);
				stringBuffer.append("\n" + reqTagValue + " in both request and response. ");
			}
		} else if (resTagValue == null) {
			LOG.error(actualKey + " is not present at given xPATH");
			if (actualKey != null) {
				stringBuffer.append("\n" + actualKey + "is not present at given xPATH. ");
			} else {
				stringBuffer.append("\n" + "required value is not present at given xPATH. ");
			}
		} else if (reqTagValue == null) {
			LOG.error(actualKey + " is not present at given request");
			if (actualKey != null) {
				stringBuffer.append("\n" + actualKey + "is not present at given request. ");
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