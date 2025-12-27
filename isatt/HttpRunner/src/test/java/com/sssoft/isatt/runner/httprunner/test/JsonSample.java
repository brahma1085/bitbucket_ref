package com.sssoft.isatt.runner.httprunner.test;

import org.apache.log4j.Logger;

import org.json.JSONObject;

public class JsonSample {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(JsonSample.class);

	public static void main(String[] args) {

		try {
			String find = "deviceEligibilityResponse";
			String responseStr = "{\n	\"deviceEligibilityResponse\":\n	{\n		\"coverageDuration\":2,\n		\"deviceDateOfPurchase\":\"01/10/10\",\n		\"deviceId\":\"85901231Y8K\",\n		\"partDescription\":\"APP for iphone -US\",\n		\"productStatement\":\"Product will be covered for stated no of days\",\n		\"secondaryDisplay\":0\n	},\n	\"marketFieldLabelDescription\":\"Test\",\n	\"pocType\":1\n}	";
			JSONObject json = new JSONObject(responseStr);
			JSONObject v = json.getJSONObject(find);
			LOG.info("responseStr " + responseStr);
			LOG.info("\n\nval " + v);
			LOG.info("\n\nval typ " + v.getClass().getCanonicalName());

			String val;
			find = "pocType";
			val = json.getString(find);
			LOG.info("responseStr " + responseStr);
			LOG.info("\n\nval " + val);
			LOG.info("\n\nval typ " + val.getClass().getCanonicalName());

		} catch (Throwable e) {
		}
	}
}
