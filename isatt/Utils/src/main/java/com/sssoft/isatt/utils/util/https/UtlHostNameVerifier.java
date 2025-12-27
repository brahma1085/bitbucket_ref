package com.sssoft.isatt.utils.util.https;

import org.apache.log4j.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * The Class UtlHostNameVerifier.
 */
public class UtlHostNameVerifier implements HostnameVerifier {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(UtlHostNameVerifier.class);

	/* (non-Javadoc)
	 * @see javax.net.ssl.HostnameVerifier#verify(java.lang.String, javax.net.ssl.SSLSession)
	 */
	public boolean verify(String hostname, SSLSession session) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("verify(String, SSLSession) - start"); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("verify(String, SSLSession) - end"); //$NON-NLS-1$
		}
		return true;
	}

}
