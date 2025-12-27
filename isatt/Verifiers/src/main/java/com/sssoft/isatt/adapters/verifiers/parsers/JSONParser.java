package com.sssoft.isatt.adapters.verifiers.parsers;

import java.util.StringTokenizer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sssoft.isatt.adapters.verifiers.bean.VerifyResult;

/**
 * The Class JSONParser.
 *
 * @author brahma
 * 
 * This class is useful to parse the JSON strings and returns the
 * required results by parsing through the given JSON String
 */
public class JSONParser {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(JSONParser.class);

	static {
		BasicConfigurator.configure();
	}

	/**
	 * This method takes the JSON String to be parsed, the xPath which specifies
	 * the path to required element and the required element's key and returns
	 * the VerifyResult with data.
	 *
	 * @param json the String
	 * @param arrayObjKey the String
	 * @param actualKey the String
	 * @param instanceCount the instance count
	 * @param arrayCount the array count
	 * @param verifyResult the VerifyResult
	 * @return verifyResult
	 * @throws JSONException the jSON exception
	 */
	public static VerifyResult prepareJson(String json, String arrayObjKey, String actualKey, int instanceCount, int arrayCount, VerifyResult verifyResult)
			throws JSONException {
		LOG.info("received JSON String : " + json);
		LOG.info("need result for key : " + actualKey);
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
			verifyResult.setFormat(true);
		} catch (JSONException e) {
			verifyResult.setFormat(false);
			throw e;
		}
		JSONArray jsonArrayNames = jsonObject.names();
		String jArrNames = jsonArrayNames.toString();
		String resultVal = checkForArrays(instanceCount, arrayCount, actualKey, jsonArrayNames, jsonObject, verifyResult);
		if (resultVal != null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("prepareJson(String, String, String, int, int, VerifyResult) - end"); //$NON-NLS-1$
			}
			return verifyResult;
		}
		if (arrayObjKey != null && arrayObjKey.contains("/")) {
			arrayObjKey = arrayObjKey.substring(0, arrayObjKey.lastIndexOf("/"));
			if (arrayObjKey.contains("/")) {
				extractXpathValue(arrayObjKey, actualKey, verifyResult, jsonObject);
			} else {
				Object object = jsonObject.opt(arrayObjKey);
				if (object instanceof JSONObject) {
					verifyResult.setRequiredResult(parseJsonData(jsonObject, actualKey));
					verifyResult.setKeyPresent(true);
				} else if (object instanceof JSONArray) {
					JSONArray jsonArray = (JSONArray) object;
					String result = checkArrayInstances(instanceCount, actualKey, jsonArray, verifyResult);
					if (result != null) {
						if (LOG.isDebugEnabled()) {
							LOG.debug("prepareJson(String, String, String, int, int, VerifyResult) - end"); //$NON-NLS-1$
						}
						return verifyResult;
					} else {
						verifyResult.setRequiredResult(parseJsonArray(jsonArray, actualKey));
						verifyResult.setKeyPresent(true);
					}
				}
			}
		} else if (jArrNames.contains(actualKey)) {
			verifyResult.setRequiredResult(parseJsonData(jsonObject, actualKey));
			verifyResult.setKeyPresent(true);
		} else {
			JSONArray jsonArray = jsonObject.optJSONArray(arrayObjKey);
			if (jsonArray != null) {
				verifyResult.setRequiredResult(parseJsonArray(jsonArray, actualKey));
				verifyResult.setKeyPresent(true);
			}
			JSONObject jObject = jsonObject.optJSONObject(arrayObjKey);
			if (jObject != null) {
				verifyResult.setRequiredResult(parseJsonData(jObject, actualKey));
				verifyResult.setKeyPresent(true);
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("prepareJson(String, String, String, int, int, VerifyResult) - end"); //$NON-NLS-1$
		}
		return verifyResult;
	}

	/**
	 * Check array instances.
	 *
	 * @param instanceCount the instance count
	 * @param actualKey the actual key
	 * @param jsonArray the json array
	 * @param verifyResult the verify result
	 * @return the string
	 * @throws JSONException the jSON exception
	 */
	public static String checkArrayInstances(int instanceCount, String actualKey, JSONArray jsonArray, VerifyResult verifyResult) throws JSONException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("checkArrayInstances(int, String, JSONArray, VerifyResult) - start"); //$NON-NLS-1$
		}

		int arrLen = jsonArray.length();
		for (int i = 0; i < arrLen; i++) {
			if (i+1 == instanceCount) {
				Object object2 = jsonArray.get(instanceCount - 1);
				verifyResult.setRequiredResult(getObjectData(object2, actualKey));
			}
		}
		String returnString = verifyResult.getRequiredResult();
		if (LOG.isDebugEnabled()) {
			LOG.debug("checkArrayInstances(int, String, JSONArray, VerifyResult) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * This method takes all the keys of JSON Object and checks for array types.
	 *
	 * @param instanceCount the instance count
	 * @param arrayCnt the array cnt
	 * @param actualKey the actual key
	 * @param jArrNames the j arr names
	 * @param jsonObject the json object
	 * @param verifyResult the verify result
	 * @return the string
	 * @throws JSONException the jSON exception
	 */
	public static String checkForArrays(int instanceCount, int arrayCnt, String actualKey, JSONArray jArrNames, JSONObject jsonObject,
			VerifyResult verifyResult) throws JSONException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("checkForArrays(int, int, String, JSONArray, JSONObject, VerifyResult) - start"); //$NON-NLS-1$
		}

		int arrayCount = 0;
		int instanceCnt = 0;
		for (int i = 0; i < jArrNames.length(); i++) {
			Object obj = jArrNames.opt(i);
			if (obj != null) {
				Object object = jsonObject.get((String) obj);
				if (object instanceof JSONArray) {
					arrayCount++;
					if (arrayCount == arrayCnt) {
						instanceCnt++;
						if (instanceCnt == instanceCount) {
							JSONArray jsonArray = (JSONArray) object;
							verifyResult.setArrayInstanceCount(jsonArray.length());
							Object object2 = jsonArray.get(instanceCount - 1);
							verifyResult.setRequiredResult(getObjectData(object2, actualKey));
						}
					}
				}
			}
		}
		verifyResult.setArrayCount(arrayCount);
		String returnString = verifyResult.getRequiredResult();
		if (LOG.isDebugEnabled()) {
			LOG.debug("checkForArrays(int, int, String, JSONArray, JSONObject, VerifyResult) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * This method fetches the result from the given xpath.
	 *
	 * @param arrayObjKey the String
	 * @param actualKey the String
	 * @param verifyResult the VerifyResult
	 * @param jsonObject the JSONObject
	 * @throws JSONException the jSON exception
	 */
	private static void extractXpathValue(String arrayObjKey, String actualKey, VerifyResult verifyResult, JSONObject jsonObject) throws JSONException {
		LOG.info("XPath : " + arrayObjKey);
		StringTokenizer strTokenizer = new StringTokenizer(arrayObjKey, "/");
		int i = 1;
		int strCount = strTokenizer.countTokens();
		while (strTokenizer.hasMoreTokens()) {
			String arrObjKey = strTokenizer.nextToken();
			JSONArray jsonArray = jsonObject.optJSONArray(arrObjKey);
			if (jsonArray != null) {
				if (i == strCount) {
					verifyResult.setRequiredResult(parseJsonArray(jsonArray, actualKey));
					verifyResult.setKeyPresent(true);
				}
			}
			JSONObject jObject = jsonObject.optJSONObject(arrObjKey);
			if (jObject != null) {
				if (i == strCount) {
					verifyResult.setRequiredResult(parseJsonData((JSONObject) jObject, actualKey));
					verifyResult.setKeyPresent(true);
				}
				jsonObject = jObject;
			}
			i++;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("extractXpathValue(String, String, VerifyResult, JSONObject) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * This method is helpful to return the required value by parsing the JSON
	 * Object formats.
	 *
	 * @param jsonObject the JSONObject
	 * @param key the String
	 * @return required value the String
	 * @throws JSONException the jSON exception
	 */
	public static String parseJsonData(JSONObject jsonObject, String key) throws JSONException {
		LOG.info("received the JSONObject : " + jsonObject);
		String resultJson = null;
		Object object = jsonObject.get(key);
		if (object != null) {
			resultJson = getObjectData(object, key);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("parseJsonData(JSONObject, String) - end"); //$NON-NLS-1$
		}
		return resultJson;
	}

	/**
	 * This method is helpful to return the required value by parsing the JSON
	 * Array formats.
	 *
	 * @param jsonArray the JSONArray
	 * @param key the String
	 * @return required value the String
	 * @throws JSONException the jSON exception
	 */
	public static String parseJsonArray(JSONArray jsonArray, String key) throws JSONException {
		LOG.info("received the JSONArray : " + jsonArray);
		String resultJson = null;
		for (int i = 0; i < jsonArray.length(); i++) {
			Object object = jsonArray.get(i);
			if (object != null) {
				resultJson = getObjectData(object, key);
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("parseJsonArray(JSONArray, String) - end"); //$NON-NLS-1$
		}
		return resultJson;
	}

	/**
	 * This method is useful to fetch the specific type of result which
	 * internally invokes the respective parses.
	 *
	 * @param object the Object
	 * @param key the String
	 * @return required value the String
	 * @throws JSONException the jSON exception
	 */
	public static String getObjectData(Object object, String key) throws JSONException {
		LOG.info("received key : " + key);
		String resultJson = null;
		if (object instanceof JSONObject) {
			JSONObject jsonObj = (JSONObject) object;
			resultJson = parseJsonData(jsonObj, key);
		} else if (object instanceof JSONArray) {
			JSONArray jArray = (JSONArray) object;
			resultJson = parseJsonArray(jArray, key);
		} else if (object instanceof Long) {
			long jsonLong = (Long) object;
			resultJson = Long.toString(jsonLong);
		} else if (object instanceof Boolean) {
			boolean jsonBoolean = (Boolean) object;
			resultJson = Boolean.toString(jsonBoolean);
		} else if (object instanceof Integer) {
			int jsonInt = (Integer) object;
			resultJson = Integer.toString(jsonInt);
		} else if (object instanceof Double) {
			double jsonDouble = (Double) object;
			resultJson = Double.toString(jsonDouble);
		} else {
			resultJson = (String) object;
		}
		LOG.info("returning response : " + resultJson);
		return resultJson;
	}

	/**
	 * checks whether given string is following JSON format.
	 *
	 * @param jsonResponse the json response
	 * @return true/false
	 */
	public static boolean isJson(String jsonResponse) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("isJson(String) - start"); //$NON-NLS-1$
		}

		boolean flag = true;
		try {
			new JSONObject(jsonResponse);
		} catch (Exception e) {
			LOG.error("isJson(String)", e); //$NON-NLS-1$

			flag = false;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("isJson(String) - end"); //$NON-NLS-1$
		}
		return flag;
	}
}
