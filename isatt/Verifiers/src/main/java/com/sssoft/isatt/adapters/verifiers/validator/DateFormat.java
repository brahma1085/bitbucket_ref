package com.sssoft.isatt.adapters.verifiers.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.sssoft.isatt.adapters.verifiers.bean.CaptureProps;
import com.sssoft.isatt.adapters.verifiers.bean.VerifyResult;
import com.sssoft.isatt.adapters.verifiers.parsers.CommonParser;
import com.sssoft.isatt.utils.util.LangUtils;

/**
 * The Class DateFormat.
 */
public class DateFormat {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(DateFormat.class);

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
			if (tagValue.contains("\\")) {
				tagValue = tagValue.replaceAll("\\", "");
			}
			stringBuffer.append("\n" + "value in response : " + tagValue + ". ");
			SimpleDateFormat sdf = LangUtils.getSdfForParse(request);
			try {
				sdf.parse(tagValue);
				verifyResult.setResult(true);
				stringBuffer.append("\n" + " date is in expected format. ");
			} catch (ParseException e) {
				LOG.error("compareRequest(String, String, CaptureProps, VerifyResult)", e); //$NON-NLS-1$

				verifyResult.setResult(false);
				stringBuffer.append("\n" + " date is not in expected format. ");
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