package com.sssoft.isatt.adapters.verifiers.parsers.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.sssoft.isatt.adapters.verifiers.bean.VerifyResult;
import com.sssoft.isatt.adapters.verifiers.parsers.JSONPathParser;

/**
 * The Class JSONPathParseTest.
 */
public class JSONPathParseTest {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(JSONPathParseTest.class);

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

		VerifyResult verifyResult = new VerifyResult();
		String filePath = JsonPathParserTest.class.getClassLoader().getResource("samplejson3.json").getPath();
		try {
			String jsonResp = FileUtils.readFileToString(new File(filePath));
			verifyResult = JSONPathParser.parseJson(jsonResp, "$['appleCareSalesDate']", verifyResult);
			if (verifyResult != null) {
				String tagValue = verifyResult.getRequiredResult();
				if (tagValue != null && tagValue.contains("\\")) {
					tagValue = tagValue.replaceAll("\\", "");
					LOG.info("date : " + tagValue);
				}
			}
		} catch (IOException e) {
			LOG.error("error occured : " + e.getMessage(), e);
		} catch (Exception e) {
			LOG.error("error occured : " + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}
}