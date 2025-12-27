package com.sssoft.isatt.adapters.verifiers.validator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.sssoft.isatt.adapters.verifiers.bean.VerifyResult;
import com.sssoft.isatt.utils.bean.UtlConf;

/**
 * The Class Previlege.
 */
public class Previlege {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(Previlege.class);

	/** The verify. */
	private VerifyResult verify = new VerifyResult();

	/**
	 * Compare request.
	 *
	 * @param values the values
	 * @param response the response
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void compareRequest(String values, String response) throws FileNotFoundException, IOException {
		LOG.info("response to be compared : " + response);
		Properties prop = new Properties();
		prop.load(new FileInputStream(UtlConf.getProperty("fileVerifier.propsPath")));
		String comp = prop.getProperty(values);
		String checkResponse = checkResponse(comp, response);
		verify.setComment(checkResponse);

		if (LOG.isDebugEnabled()) {
			LOG.debug("compareRequest(String, String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Check response.
	 *
	 * @param comp the comp
	 * @param response the response
	 * @return the string
	 */
	private String checkResponse(String comp, String response) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("checkResponse(String, String) - start"); //$NON-NLS-1$
		}

		StringBuffer sf = new StringBuffer();
		if (response == null) {
			sf.append("Response is not passes to verifier");
			verify.setResult(false);
			String returnString = sf.toString();
			if (LOG.isDebugEnabled()) {
				LOG.debug("checkResponse(String, String) - end"); //$NON-NLS-1$
			}
			return returnString;
		}
		String[] split = comp.split(",");
		for (String s : split) {
			if (response.contains(s)) {
				verify.setResult(true);
				sf.append(s + " is present in response " + "\n");
			} else {
				verify.setResult(false);
				sf.append(s + " is not present in response" + "\n");
			}
		}
		String returnString = sf.toString();
		if (LOG.isDebugEnabled()) {
			LOG.debug("checkResponse(String, String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

}