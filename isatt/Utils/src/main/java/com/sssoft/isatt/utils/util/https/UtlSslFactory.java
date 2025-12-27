package com.sssoft.isatt.utils.util.https;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

/**
 * A factory for creating UtlSsl objects.
 */
public class UtlSslFactory extends SSLSocketFactory {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(UtlSslFactory.class);

	/** The ssl context. */
	SSLContext sslContext = SSLContext.getInstance("TLS");

	/**
	 * Creates a new SSL Socket Factory with the given KeyStore.
	 * 
	 * @param truststore
	 *            A KeyStore to create the SSL Socket Factory in context of
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws KeyManagementException
	 *             the key management exception
	 * @throws KeyStoreException
	 *             the key store exception
	 * @throws UnrecoverableKeyException
	 *             the unrecoverable key exception
	 */
	public UtlSslFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
		super(truststore);

		X509TrustManager tm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sslContext.init(null, new TrustManager[] { tm }, null);
	}

	/**
	 * Instantiates a new utl ssl factory.
	 * 
	 * @param truststore
	 *            the truststore
	 * @param acceptAll
	 *            the accept all
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws KeyManagementException
	 *             the key management exception
	 * @throws KeyStoreException
	 *             the key store exception
	 * @throws UnrecoverableKeyException
	 *             the unrecoverable key exception
	 */
	@SuppressWarnings("deprecation")
	public UtlSslFactory(KeyStore truststore, boolean acceptAll) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException,
			UnrecoverableKeyException {
		super(truststore);

		UtlTrustManager tm = new UtlTrustManager(null);

		sslContext.init(null, new TrustManager[] { tm }, null);
		if (acceptAll) {
			setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.http.conn.ssl.SSLSocketFactory#createSocket(java.net.Socket,
	 * java.lang.String, int, boolean)
	 */
	@Override
	public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("createSocket(Socket, String, int, boolean) - start"); //$NON-NLS-1$
		}

		Socket returnSocket = sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
		if (LOG.isDebugEnabled()) {
			LOG.debug("createSocket(Socket, String, int, boolean) - end"); //$NON-NLS-1$
		}
		return returnSocket;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.http.conn.ssl.SSLSocketFactory#createSocket()
	 */
	@Override
	public Socket createSocket() throws IOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("createSocket() - start"); //$NON-NLS-1$
		}

		Socket returnSocket = sslContext.getSocketFactory().createSocket();
		if (LOG.isDebugEnabled()) {
			LOG.debug("createSocket() - end"); //$NON-NLS-1$
		}
		return returnSocket;
	}

	/**
	 * Makes HttpsURLConnection trusts a set of certificates specified by the
	 * KeyStore.
	 */
	public void fixHttpsURLConnection() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("fixHttpsURLConnection() - start"); //$NON-NLS-1$
		}

		HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

		if (LOG.isDebugEnabled()) {
			LOG.debug("fixHttpsURLConnection() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets a KeyStore containing the Certificate.
	 * 
	 * @param cert
	 *            InputStream of the Certificate
	 * @return KeyStore
	 */
	public static KeyStore getKeystoreOfCA(InputStream cert) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getKeystoreOfCA(InputStream) - start"); //$NON-NLS-1$
		}

		// Load CAs from an InputStream
		InputStream caInput = null;
		Certificate ca = null;
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			caInput = new BufferedInputStream(cert);
			ca = (Certificate) cf.generateCertificate(caInput);
		} catch (CertificateException e1) {
			LOG.error("SSL Certificate exception : " + e1.getMessage(), e1);
		} finally {
			try {
				caInput.close();
			} catch (IOException e) {
				LOG.error("I/O Exception : " + e.getMessage(), e);
			}
		}

		// Create a KeyStore containing our trusted CAs
		String keyStoreType = KeyStore.getDefaultType();
		KeyStore keyStore = null;
		try {
			keyStore = KeyStore.getInstance(keyStoreType);
			keyStore.load(null, null);
			keyStore.setCertificateEntry("ca", (java.security.cert.Certificate) ca);
		} catch (Exception e) {
			LOG.error("Exception due to : " + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getKeystoreOfCA(InputStream) - end"); //$NON-NLS-1$
		}
		return keyStore;
	}

	/**
	 * Gets a Default KeyStore.
	 * 
	 * @return KeyStore
	 */
	public static KeyStore getKeystore() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getKeystore() - start"); //$NON-NLS-1$
		}

		KeyStore trustStore = null;
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
		} catch (Exception e) {
			LOG.error("Exception due to : " + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getKeystore() - end"); //$NON-NLS-1$
		}
		return trustStore;
	}

	/**
	 * Returns a SSlSocketFactory which trusts all certificates.
	 * 
	 * @return the fixed socket factory
	 */
	@SuppressWarnings("deprecation")
	public static SSLSocketFactory getFixedSocketFactory() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getFixedSocketFactory() - start"); //$NON-NLS-1$
		}

		SSLSocketFactory socketFactory;
		try {
			socketFactory = new UtlSslFactory(getKeystore());
			socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		} catch (Exception e) {
			LOG.error("Exception due to : " + e.getMessage(), e);
			socketFactory = SSLSocketFactory.getSocketFactory();
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getFixedSocketFactory() - end"); //$NON-NLS-1$
		}
		return socketFactory;
	}

	/**
	 * Gets a DefaultHttpClient which trusts a set of certificates specified by
	 * the KeyStore.
	 * 
	 * @param keyStore
	 *            the key store
	 * @return the new http client
	 */
	@SuppressWarnings("deprecation")
	public static DefaultHttpClient getNewHttpClient(KeyStore keyStore) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getNewHttpClient(KeyStore) - start"); //$NON-NLS-1$
		}

		try {
			SSLSocketFactory sf = new UtlSslFactory(keyStore);
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

			DefaultHttpClient returnDefaultHttpClient = new DefaultHttpClient(ccm, params);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getNewHttpClient(KeyStore) - end"); //$NON-NLS-1$
			}
			return returnDefaultHttpClient;
		} catch (Exception e) {
			LOG.error("getNewHttpClient(KeyStore)", e); //$NON-NLS-1$

			DefaultHttpClient returnDefaultHttpClient = new DefaultHttpClient();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getNewHttpClient(KeyStore) - end"); //$NON-NLS-1$
			}
			return returnDefaultHttpClient;
		}
	}
}
