package com.sssoft.isatt.utils.util.https;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * The Class JNetSslFactoory.
 */
public class JNetSslFactoory extends SSLSocketFactory {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(JNetSslFactoory.class);
	
	/** The ssl context. */
	private SSLContext sslContext = null;
	
	/** The fact. */
	private SSLSocketFactory fact = null;

	/**
	 * Instantiates a new j net ssl factoory.
	 */
	public JNetSslFactoory() {

	}

	/**
	 * Instantiates a new j net ssl factoory.
	 *
	 * @param truststore the truststore
	 * @param acceptAll the accept all
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws KeyManagementException the key management exception
	 * @throws KeyStoreException the key store exception
	 * @throws UnrecoverableKeyException the unrecoverable key exception
	 */
	public JNetSslFactoory(KeyStore truststore, boolean acceptAll) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException,
			UnrecoverableKeyException {
		super();
		sslContext = SSLContext.getInstance("TLS");
		UtlTrustManager tm = new UtlTrustManager(null);

		sslContext.init(null, new TrustManager[] { tm }, null);
		fact = (SSLSocketFactory) SSLSocketFactory.getDefault();

		if (acceptAll) {
			// Object aa;
			// setHostnameVerifier(aa);
		}
	}

	/**
	 * Creates the socket.
	 *
	 * @param sock the sock
	 * @param arg1 the arg1
	 * @param arg2 the arg2
	 * @param arg3 the arg3
	 * @return the socket
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Socket createSocket(Socket sock, String arg1, int arg2, boolean arg3) throws IOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("createSocket(Socket, String, int, boolean) - start"); //$NON-NLS-1$
		}

		Socket returnSocket = fact.createSocket(sock, arg1, arg2, arg3);
		if (LOG.isDebugEnabled()) {
			LOG.debug("createSocket(Socket, String, int, boolean) - end"); //$NON-NLS-1$
		}
		return returnSocket;
	}

	/* (non-Javadoc)
	 * @see javax.net.ssl.SSLSocketFactory#getDefaultCipherSuites()
	 */
	public String[] getDefaultCipherSuites() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getDefaultCipherSuites() - start"); //$NON-NLS-1$
		}

		String[] returnStringArray = fact.getDefaultCipherSuites();
		if (LOG.isDebugEnabled()) {
			LOG.debug("getDefaultCipherSuites() - end"); //$NON-NLS-1$
		}
		return returnStringArray;
	}

	/* (non-Javadoc)
	 * @see javax.net.ssl.SSLSocketFactory#getSupportedCipherSuites()
	 */
	public String[] getSupportedCipherSuites() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSupportedCipherSuites() - start"); //$NON-NLS-1$
		}

		String[] returnStringArray = fact.getSupportedCipherSuites();
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSupportedCipherSuites() - end"); //$NON-NLS-1$
		}
		return returnStringArray;
	}

	/* (non-Javadoc)
	 * @see javax.net.SocketFactory#createSocket(java.lang.String, int)
	 */
	public Socket createSocket(String arg0, int arg1) throws IOException, UnknownHostException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("createSocket(String, int) - start"); //$NON-NLS-1$
		}

		Socket returnSocket = fact.createSocket(arg0, arg1);
		if (LOG.isDebugEnabled()) {
			LOG.debug("createSocket(String, int) - end"); //$NON-NLS-1$
		}
		return returnSocket;
	}

	/* (non-Javadoc)
	 * @see javax.net.SocketFactory#createSocket(java.net.InetAddress, int)
	 */
	public Socket createSocket(InetAddress arg0, int arg1) throws IOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("createSocket(InetAddress, int) - start"); //$NON-NLS-1$
		}

		Socket returnSocket = fact.createSocket(arg0, arg1);
		if (LOG.isDebugEnabled()) {
			LOG.debug("createSocket(InetAddress, int) - end"); //$NON-NLS-1$
		}
		return returnSocket;
	}

	/* (non-Javadoc)
	 * @see javax.net.SocketFactory#createSocket(java.lang.String, int, java.net.InetAddress, int)
	 */
	public Socket createSocket(String arg0, int arg1, InetAddress arg2, int arg3) throws IOException, UnknownHostException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("createSocket(String, int, InetAddress, int) - start"); //$NON-NLS-1$
		}

		Socket returnSocket = fact.createSocket(arg0, arg1, arg2, arg3);
		if (LOG.isDebugEnabled()) {
			LOG.debug("createSocket(String, int, InetAddress, int) - end"); //$NON-NLS-1$
		}
		return returnSocket;
	}

	/* (non-Javadoc)
	 * @see javax.net.SocketFactory#createSocket(java.net.InetAddress, int, java.net.InetAddress, int)
	 */
	public Socket createSocket(InetAddress arg0, int arg1, InetAddress arg2, int arg3) throws IOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("createSocket(InetAddress, int, InetAddress, int) - start"); //$NON-NLS-1$
		}

		Socket returnSocket = fact.createSocket(arg0, arg1, arg2, arg3);
		if (LOG.isDebugEnabled()) {
			LOG.debug("createSocket(InetAddress, int, InetAddress, int) - end"); //$NON-NLS-1$
		}
		return returnSocket;
	}

}
