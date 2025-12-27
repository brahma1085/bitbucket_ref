package com.tfw.verifiers.parsers.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.minidev.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.jayway.jsonpath.JsonPath;

/**
 * The Class JsonPathParserTest.
 */
public class JsonPathParserTest {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(JsonPathParserTest.class);

	static {
		BasicConfigurator.configure();
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

		try {
			String filePath = JsonPathParserTest.class.getClassLoader().getResource("samplejson3.json").getPath();
			String jsonResp = FileUtils.readFileToString(new File(filePath));
			List<Object> objects = fetchJsonPath(jsonResp, "$.deviceRequest[*]", "[?(@.deviceId=='F17L1140F8GH')]", "deviceId");
			for (Object object : objects) {
				LOG.info("resultJson : " + object);
			}
		} catch (Exception e) {
			LOG.error("error thrown : " + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Fetch json path.
	 *
	 * @param jsonResp the json resp
	 * @param expression the expression
	 * @param expression1 the expression1
	 * @param reqValue the req value
	 * @return the list
	 */
	private static List<Object> fetchJsonPath(Object jsonResp, String expression, String expression1, String reqValue) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("fetchJsonPath(Object, String, String, String) - start"); //$NON-NLS-1$
		}

		List<Object> objects = new ArrayList<Object>();
		Object object = JsonPath.read(String.valueOf(jsonResp), expression);
		LOG.info("requiredVal : " + object);
		if (object instanceof Collection<?>) {
			Collection<?> collection = (Collection<?>) object;
			Iterator<?> iterator = collection.iterator();
			while (iterator.hasNext()) {
				Object object2 = (Object) iterator.next();
				LOG.info("object2 : " + object2);
				if (object2 != null) {
					if (object2 instanceof Collection<?>) {
						objects.add(fetchJsonPath(object2, expression1, null, reqValue));
					} else if (object2 instanceof JSONObject) {
						JSONObject jsonObject = (JSONObject) object2;
						objects.add(jsonObject.get(reqValue));
					} else {
						objects.add(object2);
					}
				}
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("fetchJsonPath(Object, String, String, String) - end"); //$NON-NLS-1$
		}
		return objects;
	}

}