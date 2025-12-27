package com.sssoft.isatt.ui.utils;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * The Class ObjectToJsonFormatter.
 */
public class ObjectToJsonFormatter {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(ObjectToJsonFormatter.class);

	/**
	 * Convert to json.
	 * 
	 * @param object
	 *            the object
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public static String convertToJson(Object object) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("convertToJson(Object) - start"); //$NON-NLS-1$
		}

		Gson gson = new Gson();
		String json = gson.toJson(object);

		if (LOG.isDebugEnabled()) {
			LOG.debug("convertToJson(Object) - end"); //$NON-NLS-1$
		}
		return json;
	}

}
