package com.sssoft.isatt.adapters.verifiers.validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;

import com.sssoft.isatt.adapters.verifiers.bean.CaptureProps;
import com.sssoft.isatt.adapters.verifiers.bean.VerifyResult;
import com.sssoft.isatt.adapters.verifiers.parsers.CommonParser;

/**
 * The Class EmailVerify.
 */
public class EmailVerify {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(EmailVerify.class);

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
		LOG.info("request to be compared : " + request);
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
			stringBuffer.append("\n" + email(tagValue, request, verifyResult));
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

	/**
	 * Email.
	 *
	 * @param email the email
	 * @param type the type
	 * @param verifyResult the verify result
	 * @return the string
	 */
	private String email(String email, String type, VerifyResult verifyResult) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("email(String, String, VerifyResult) - start"); //$NON-NLS-1$
		}

		verifyResult.setResult(true);
		StringBuffer stringBuffer = new StringBuffer();
		if ("TYPE_APACHE".equalsIgnoreCase(type)) {
			if (email == null) {
				stringBuffer.append("Email is null. ");
				String returnString = stringBuffer.toString();
				if (LOG.isDebugEnabled()) {
					LOG.debug("email(String, String, VerifyResult) - end"); //$NON-NLS-1$
				}
				return returnString;
			}
			if (EmailValidator.getInstance().isValid(email)) {
				String returnString = stringBuffer.append("email is in correct format. ").toString();
				if (LOG.isDebugEnabled()) {
					LOG.debug("email(String, String, VerifyResult) - end"); //$NON-NLS-1$
				}
				return returnString;
			} else {
				String returnString = stringBuffer.append("email is not in correct format. ").toString();
				if (LOG.isDebugEnabled()) {
					LOG.debug("email(String, String, VerifyResult) - end"); //$NON-NLS-1$
				}
				return returnString;
			}
		}
		if ("TYPE_SIMPLE".equalsIgnoreCase(type)) {
			if (email == null) {
				stringBuffer.append("Email Null. ");
				verifyResult.setResult(false);
				String returnString = stringBuffer.toString();
				if (LOG.isDebugEnabled()) {
					LOG.debug("email(String, String, VerifyResult) - end"); //$NON-NLS-1$
				}
				return returnString;
			}
			if (email.length() < 6) {
				stringBuffer.append(" Email Len < 6 : " + email.length() + ". ");
				verifyResult.setResult(false);
				String returnString = stringBuffer.toString();
				if (LOG.isDebugEnabled()) {
					LOG.debug("email(String, String, VerifyResult) - end"); //$NON-NLS-1$
				}
				return returnString;
			}
			String s = email.replace('@', 'a');
			s = s.replace('.', 'a');
			s = s.replace('_', 'a');
			s = s.replace('-', 'a');
			if (!StringUtils.isAlphanumeric(s)) {
				stringBuffer.append(" Email as special characters other than . - _ @ ");
				verifyResult.setResult(false);
				String returnString = stringBuffer.toString();
				if (LOG.isDebugEnabled()) {
					LOG.debug("email(String, String, VerifyResult) - end"); //$NON-NLS-1$
				}
				return returnString;
			}
			int atLoc = email.indexOf('@');
			if (atLoc < 1) {// should have at least 1 char before @
				stringBuffer.append(" Email should have at least 1 char before @ ");
				verifyResult.setResult(false);
				String returnString = stringBuffer.toString();
				if (LOG.isDebugEnabled()) {
					LOG.debug("email(String, String, VerifyResult) - end"); //$NON-NLS-1$
				}
				return returnString;
			}
			int atLoc2 = email.indexOf('@', atLoc + 1);
			if (atLoc2 > 0) {
				verifyResult.setResult(false);
				stringBuffer.append(" Email has more than one at.");
				String returnString = stringBuffer.toString();
				if (LOG.isDebugEnabled()) {
					LOG.debug("email(String, String, VerifyResult) - end"); //$NON-NLS-1$
				}
				return returnString;
			}
			int dotAfter = email.indexOf('.', atLoc + 1);
			if (dotAfter < 0) {
				verifyResult.setResult(false);
				stringBuffer.append(" Email cannot have \".\" at last ");
				String returnString = stringBuffer.toString();
				if (LOG.isDebugEnabled()) {
					LOG.debug("email(String, String, VerifyResult) - end"); //$NON-NLS-1$
				}
				return returnString;
			}
			if (verifyResult.isResult()) {
				stringBuffer.append("email is in correct format. ");
			}
		}
		String returnString = stringBuffer.toString();
		if (LOG.isDebugEnabled()) {
			LOG.debug("email(String, String, VerifyResult) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

}