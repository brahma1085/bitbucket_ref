package com.tfw.verifiers.parsers.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tfw.verifiers.parsers.JSONParser;

/**
 * The Class JsonArrayParser.
 */
public class JsonArrayParser {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(JSONParser.class);

	static {
		BasicConfigurator.configure();
	}

	/**
	 * Prepare json.
	 *
	 * @param json the json
	 * @param arrayObjKey the array obj key
	 * @param actualKey the actual key
	 * @param verifyResults the verify results
	 * @return the verify results
	 * @throws JSONException the jSON exception
	 */
	public static VerifyResults prepareJson(String json, String arrayObjKey, String actualKey, VerifyResults verifyResults) throws JSONException {
		LOG.info("received JSON String : " + json);
		LOG.info("need result for key : " + actualKey);
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
			verifyResults.setFormat(true);
		} catch (JSONException e) {
			verifyResults.setFormat(false);
			throw e;
		}
		JSONArray jsonArrayNames = jsonObject.names();
		verifyResults = fetchJsonArrayData(jsonArrayNames, jsonObject, verifyResults, actualKey);

		if (LOG.isDebugEnabled()) {
			LOG.debug("prepareJson(String, String, String, VerifyResults) - end"); //$NON-NLS-1$
		}
		return verifyResults;
	}

	/**
	 * Fetch json array data.
	 *
	 * @param jsonArrayNames the json array names
	 * @param jsonObject the json object
	 * @param verifyResults the verify results
	 * @param actualKey the actual key
	 * @return the verify results
	 * @throws JSONException the jSON exception
	 */
	private static VerifyResults fetchJsonArrayData(JSONArray jsonArrayNames, JSONObject jsonObject, VerifyResults verifyResults, String actualKey)
			throws JSONException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("fetchJsonArrayData(JSONArray, JSONObject, VerifyResults, String) - start"); //$NON-NLS-1$
		}

		List<String> reqResList = new ArrayList<String>();
		for (int i = 0; i < jsonArrayNames.length(); i++) {
			Object obj = jsonArrayNames.opt(i);
			LOG.info("fetchJsonArrayData => jsonArrayNames.obj => " + obj);
			Object object = jsonObject.opt((String) obj);
			if (object instanceof JSONArray) {
				JSONArray jsonArray = (JSONArray) object;
				String result = parseJsonArray(jsonArray, actualKey);
				LOG.info("fetchJsonArrayData => jsonArray.object2 => " + result);
			} else if (object instanceof JSONObject) {
				JSONObject jsObject = (JSONObject) object;
				Object object2 = jsObject.opt((String) obj);
				if (object2 != null && object2 instanceof JSONArray) {
					JSONArray jsonArray = (JSONArray) object;
					String result = parseJsonArray(jsonArray, actualKey);
					LOG.info("fetchJsonArrayData => jsonArray.object2 => " + result);
				} else {
					JSONArray jsonArrNames = jsObject.names();
					verifyResults = fetchJsonArrayData(jsonArrNames, jsonObject, verifyResults, actualKey);
					LOG.info("fetchJsonArrayData => jsObject.object2 => " + verifyResults);
				}
			} else {
				reqResList.add(getObjectData(object, actualKey));
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("fetchJsonArrayData(JSONArray, JSONObject, VerifyResults, String) - end"); //$NON-NLS-1$
		}
		return verifyResults;
	}

	/**
	 * Parses the json data.
	 *
	 * @param jsonObject the json object
	 * @param key the key
	 * @return the string
	 * @throws JSONException the jSON exception
	 */
	private static String parseJsonData(JSONObject jsonObject, String key) throws JSONException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("parseJsonData(JSONObject, String) - start"); //$NON-NLS-1$
		}

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
	 * Parses the json array.
	 *
	 * @param jsonArray the json array
	 * @param key the key
	 * @return the string
	 * @throws JSONException the jSON exception
	 */
	private static String parseJsonArray(JSONArray jsonArray, String key) throws JSONException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("parseJsonArray(JSONArray, String) - start"); //$NON-NLS-1$
		}

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
	 * Gets the object data.
	 *
	 * @param object the object
	 * @param key the key
	 * @return the object data
	 * @throws JSONException the jSON exception
	 */
	private static String getObjectData(Object object, String key) throws JSONException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectData(Object, String) - start"); //$NON-NLS-1$
		}

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
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		File folder = new File("/data/samples/");
		IOFileFilter filter = new IOFileFilter() {
			VerifyResults verifyResults = new VerifyResults();

			@Override
			public boolean accept(File dir, String name) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("$IOFileFilter.accept(File, String) - start"); //$NON-NLS-1$
				}

				if (LOG.isDebugEnabled()) {
					LOG.debug("$IOFileFilter.accept(File, String) - end"); //$NON-NLS-1$
				}
				return false;
			}

			@Override
			public boolean accept(File file) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("$IOFileFilter.accept(File) - start"); //$NON-NLS-1$
				}

				String[] names = file.getParentFile().list();
				for (String name : names) {
					if (name.endsWith(".json")) {
						String string = null;
						try {
							string = FileUtils.readFileToString(file);
							if (string != null && string.length() != 0 && string.startsWith("{")) {
								LOG.info("required result : " + prepareJson(string, "/deviceRequest/deviceId", "deviceId", verifyResults));
							}
						} catch (Exception e) {
							LOG.error("error : " + e, e);
						}

						if (LOG.isDebugEnabled()) {
							LOG.debug("$IOFileFilter.accept(File) - end"); //$NON-NLS-1$
						}
						return true;
					}
				}

				if (LOG.isDebugEnabled()) {
					LOG.debug("$IOFileFilter.accept(File) - end"); //$NON-NLS-1$
				}
				return false;
			}
		};
		FileUtils.iterateFiles(folder, filter, null);

		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}

}
