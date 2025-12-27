package com.sssoft.isatt.adapters.verifiers.parsers;

import org.apache.log4j.Logger;

import com.jayway.jsonpath.JsonPath;
import com.sssoft.isatt.adapters.verifiers.bean.VerifyResult;

/**
 * The Class JSONPathParser.
 */
public class JSONPathParser {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(JSONPathParser.class);

	/**
	 * Parses the json.
	 *
	 * @param jsonResp the json resp
	 * @param expression the expression
	 * @param verifyResult the verify result
	 * @return the verify result
	 * @throws Exception the exception
	 */
	public static VerifyResult parseJson(String jsonResp, String expression, VerifyResult verifyResult) throws Exception {
		LOG.info("received json response : " + jsonResp);
		LOG.info("received expression to parse json : " + expression);
		Object result = JsonPath.read(jsonResp, expression);
		if (result != null) {
			verifyResult.setFormat(true);
			verifyResult.setKeyPresent(true);
			verifyResult.setRequiredResult(String.valueOf(result));
		}
		LOG.info("verifyResult : " + verifyResult);
		return verifyResult;
	}
}
