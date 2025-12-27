package com.exilant.tfw.util.https;

import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.apache.log4j.Logger;

/**
 * The Class HsHlp.
 */
public class HsHlp {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(HsHlp.class);

	/**
	 * Jks load for ssl.
	 *
	 * @param passphrase the passphrase
	 * @param certLocation the cert location
	 * @return the sSL socket factory
	 */
	public synchronized static SSLSocketFactory jksLoadForSsl(char[] passphrase, String certLocation) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("jksLoadForSsl(char[], String) - start"); //$NON-NLS-1$
		}

		if (certLocation == null || "".equals(certLocation)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("jksLoadForSsl(char[], String) - end"); //$NON-NLS-1$
			}
			return null;
		}
		if (passphrase == null || "".equals(passphrase)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("jksLoadForSsl(char[], String) - end"); //$NON-NLS-1$
			}
			return null;
		}
		try {
			LOG.debug("Jks load start ");

			KeyStore ts = KeyStore.getInstance("JKS");
			ts.load(new FileInputStream(certLocation), passphrase);
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(ts);
			LOG.debug("trust loaded");
			SSLContext ctx = SSLContext.getInstance("SSLv3");
			ctx.init(null, tmf.getTrustManagers(), null);
			SSLSocketFactory ssLSocketFactory = (SSLSocketFactory) ctx.getSocketFactory();
			LOG.debug("Done");
			return ssLSocketFactory;
		} catch (Exception e) {
			LOG.error("jksLoadForSsl(char[], String)", e); //$NON-NLS-1$

			LOG.fatal("jksLoadForSsl:" + e, e);
			return null;
		}
	}
}
