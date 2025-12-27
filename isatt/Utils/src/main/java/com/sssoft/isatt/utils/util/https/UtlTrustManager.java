package com.sssoft.isatt.utils.util.https;

import org.apache.log4j.Logger;

import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * The Class UtlTrustManager.
 */
public class UtlTrustManager implements X509TrustManager {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(UtlTrustManager.class);

	/** The ks. */
	private KeyStore ks;

	/**
	 * Instantiates a new utl trust manager.
	 *
	 * @param ks the ks
	 */
	public UtlTrustManager(KeyStore ks) {
		this.ks = ks;
	}

	/* (non-Javadoc)
	 * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
	 */
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	/* (non-Javadoc)
	 * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
	 */
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	/* (non-Javadoc)
	 * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
	 */
	public X509Certificate[] getAcceptedIssuers() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAcceptedIssuers() - start"); //$NON-NLS-1$
		}

		if (ks == null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAcceptedIssuers() - end"); //$NON-NLS-1$
			}
			return null;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAcceptedIssuers() - end"); //$NON-NLS-1$
		}
		return null;
	}
}
