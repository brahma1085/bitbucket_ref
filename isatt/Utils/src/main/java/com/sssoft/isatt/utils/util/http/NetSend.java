package com.sssoft.isatt.utils.util.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.sssoft.isatt.utils.util.Base64;
import com.sssoft.isatt.utils.util.https.JNetSslFactoory;
import com.sssoft.isatt.utils.util.https.UtlHostNameVerifier;

/**
 * Send data to other JVM using HTTP post or Http Get or other custom data send
 * over ssl or plain Socket. Ligther version of Apache HttpComponents which is
 * has more features. We can add support to send via HttpComponents as the send
 * method specified in HttpData.
 * 
 * @author Tushar Kapila
 * 
 */
public class NetSend {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(NetSend.class);

	/** The content type. */
	private static String contentType = "application/x-www-form-urlencoded; charset=utf-8";
	
	/** The utl ssl. */
	private static JNetSslFactoory utlSsl = null;
	
	/** The nm v. */
	private static UtlHostNameVerifier nmV = new UtlHostNameVerifier();
	static {
		try {
			utlSsl = new JNetSslFactoory(null, true);
		} catch (Exception e) {
			LOG.warn("init :" + e, e);
		}
	}

	/**
	 * Send obj.
	 *
	 * @param srlzblObj the srlzbl obj
	 * @param jvmMode the jvm mode
	 * @param hDat the h dat
	 * @param appObjTyp the app obj typ
	 * @return the object
	 * @throws Exception the exception
	 */
	public static Object sendObj(Serializable srlzblObj, boolean jvmMode, HttpData hDat, String appObjTyp) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("sendObj(Serializable, boolean, HttpData, String) - start"); //$NON-NLS-1$
		}

		Object returnObject = sendObjects(appObjTyp, jvmMode, hDat, srlzblObj);
		if (LOG.isDebugEnabled()) {
			LOG.debug("sendObj(Serializable, boolean, HttpData, String) - end"); //$NON-NLS-1$
		}
		return returnObject;
	}

	/*
	 * common.http.NetSend, HttpParam, SslParam, factory GenericParameters .java
	 * and CSR jsp common.threads TheadPool - 4 classes
	 */

	/**
	 * Send objects.
	 *
	 * @param appObjTyp the app obj typ
	 * @param jvmMode the jvm mode
	 * @param hDat the h dat
	 * @param srlzblObjs the srlzbl objs
	 * @return the object
	 * @throws Exception the exception
	 */
	public static Object sendObjects(String appObjTyp, boolean jvmMode, HttpData hDat, Serializable... srlzblObjs) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("sendObjects(String, boolean, HttpData, Serializable) - start"); //$NON-NLS-1$
		}

		int options = 0;
		if (hDat == null) {
			LOG.log(Level.WARN, "sendObj no http");

			if (LOG.isDebugEnabled()) {
				LOG.debug("sendObjects(String, boolean, HttpData, Serializable) - end"); //$NON-NLS-1$
			}
			return null;
		}
		// if(hDat.getSendType() == SendMethod.)
		try {

			StringBuilder data = new StringBuilder().append("typ=");
			data.append(appObjTyp);
			data.append("&jvmMode=").append(jvmMode);
			String dat = null;
			int i = 1;
			String addIndex = "";
			String reply;
			for (Serializable sro : srlzblObjs) {
				if (sro == null) {
					dat = "";
				} else {
					dat = Base64.encodeObject(sro, options);
				}
				addIndex = "" + i;
				data.append("&obj" + addIndex + "=").append(java.net.URLEncoder.encode(dat, "UTF-8"));
				i++;
			}

			reply = send(hDat, data.toString());
			if (reply != null && reply.length() > 4) {
				reply = removeHttpHeader(reply);
				if (reply != null && reply.length() > 4) {
					String objDataFromServletParam = reply.substring(4);
					Object obj = Base64.decodeToObject(objDataFromServletParam, options, null);

					if (LOG.isDebugEnabled()) {
						LOG.debug("sendObjects(String, boolean, HttpData, Serializable) - end"); //$NON-NLS-1$
					}
					return obj;
				}
			}
		} catch (IOException e) {
			LOG.warn("obj send:" + e, e);
			throw e;
		} catch (ClassNotFoundException e) {
			LOG.warn("obj send:" + e, e);
			LOG.fatal("obj send srlzblObj:" + srlzblObjs);
			throw e;
		} catch (Exception e) {
			LOG.warn("obj send:" + e, e);
			LOG.fatal("obj send srlzblObj:" + srlzblObjs);
			throw e;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("sendObjects(String, boolean, HttpData, Serializable) - end"); //$NON-NLS-1$
		}
		return null;
	}

	/**
	 * Send.
	 *
	 * @param hDat the h dat
	 * @param dat the dat
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String send(HttpData hDat, String dat) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("send(HttpData, String) - start"); //$NON-NLS-1$
		}

		String returnString = send(hDat, dat, null);
		if (LOG.isDebugEnabled()) {
			LOG.debug("send(HttpData, String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Send.
	 *
	 * @param hDat the h dat
	 * @param dat the dat
	 * @param headers the headers
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String send(HttpData hDat, String dat, Map<String, String> headers) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("send(HttpData, String, Map<String,String>) - start"); //$NON-NLS-1$
		}

		Socket sockt = null;
		StringBuffer sb = new StringBuffer();
		InputStream in = null;
		OutputStream out = null;

		try {
			String sUrl = hDat.getUrl();
			int timeout = hDat.getTimeoutMilli();
			URL url = new URL(sUrl);
			final boolean sslReq = sUrl.toLowerCase().startsWith("https");
			int port = url.getPort() == -1 ? (sslReq ? 443 : 80) : url.getPort();
			// LOG.trace("Connecting :");
			// String error = null;
			SSLSocketFactory ssLSocketFactory = utlSsl;
			Exception connEx = null;
			for (int i = 0; i < 3; i++) {
				try {
					if (sslReq) {
						LOG.trace("sSL...........");
						// sockt = (SSLSocket)
						// ssLSocketFactory.createSocket(url.getHost(), port);

						HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
						conn.setHostnameVerifier(nmV);
						conn.setConnectTimeout(timeout);
						conn.setReadTimeout(timeout);
						conn.setSSLSocketFactory(ssLSocketFactory);
						conn.setDoOutput(true);
						out = conn.getOutputStream();
						in = conn.getInputStream();
					} else {
						LOG.trace("send url :" + sUrl + "; d:" + dat + ".");
						sockt = new Socket(url.getHost(), port);

						in = sockt.getInputStream();
						out = sockt.getOutputStream();
						sockt.setSoTimeout(timeout);
					}

					break;
				} catch (ConnectException e) {
					LOG.info("Connect Exception failed: " + e + ", try :" + (i + 1));
					connEx = e;
					continue;
				}
			}
			if (in == null) {
				throw connEx;
			}

			LOG.info("Connected!");

			byte toSend[] = null;
			if (url.getQuery() != null && url.getQuery().length() != 0) {
				dat = url.getQuery() + "&" + dat;
			}

			sb.append("POST ").append(url.getPath()).append(" HTTP/1.1\r\n");
			if (headers == null) {
				headers = Collections.emptyMap();
			}
			final String USR_AGNT_H = "User-Agent";
			final String CON_TYP_H = "Content-type";
			String ctype = null;
			if (headers.containsKey(CON_TYP_H)) {
				ctype = headers.get(CON_TYP_H);
				headers.remove(CON_TYP_H);
			}
			if (ctype == null) {
				// if not set or val is null take default
				ctype = contentType;
			}
			String usrAg = null;
			if (headers.containsKey(USR_AGNT_H)) {
				usrAg = headers.get(USR_AGNT_H);
				headers.remove(USR_AGNT_H);
			}
			if (usrAg == null) {
				usrAg = "TFW Exilant";
			}

			final String Accept_H = "Accept";
			String saccpt = null;
			if (headers.containsKey(Accept_H)) {
				saccpt = headers.get(Accept_H);
				headers.remove(Accept_H);
			}
			if (saccpt == null) {
				saccpt = "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2";
			}

			sb.append(CON_TYP_H).append(": ").append(ctype).append("\r\n");

			sb.append(USR_AGNT_H).append(": ").append(usrAg).append("\r\n");
			sb.append("Host: ").append(url.getHost()).append(":").append(port).append("\r\n");
			sb.append(Accept_H).append(": ").append(saccpt).append("\r\n");

			// sb.append("Connection: keep-alive\r\n");
			toSend = dat.getBytes("UTF-8");
			// LOG.trace("-----encode 1----"+new String(toSend, "utf-8"));

			// toSend = java.net.URLEncoder.encode(xml.getBytes());
			// LOG.trace("-----encode 2----"+toSend);//CONTENT-LENGTH:
			final String contLen = "Content-Length: ";
			sb.append(contLen).append(toSend.length).append("\r\n");

			Set<Map.Entry<String, String>> headerS = headers.entrySet();
			for (Map.Entry<String, String> en : headerS) {
				sb.append(en.getKey()).append(": ").append(en.getValue()).append("\r\n");
			}

			sb.append("\r\n");
			byte headerToSend[] = sb.toString().getBytes("UTF-8");
			LOG.trace("Header To be sent: " + sb.toString());// for debug

			out.write(headerToSend, 0, headerToSend.length);
			out.write(toSend, 0, toSend.length);
			out.flush();
			LOG.trace("Data sent!");
			BufferedInputStream bin = new BufferedInputStream(in);
			byte temp[] = null;
			sb.setLength(0);
			String t1 = null;
			int looper = 0;
			do {
				temp = readInputStream(bin, hDat);// br.readLine();
				if (temp != null) {
					t1 = new String(temp, "UTF-8");
					sb.append(t1);
				} else {
					// LOG.trace("read was null ");
					if (sb.length() == 0 && temp == null) {
						throw new IOException("Lost Connection - App");
					}
				}

				// all data read?
				String fullHttp = null;

				// do
				{
					fullHttp = sb.toString();
					LOG.trace("Outer k Http: " + fullHttp.length() + ", looper" + looper + ", read :" + hDat.getReadByteCnt());
					String sep = "\r\n\r\n";
					int i = fullHttp.indexOf(sep);
					looper++;
					if (looper % 10 == 0) {
						LOG.trace("fullHttp is :" + fullHttp + "\n---");
					}
					if (i != -1 && looper < 110) {
						// LOG.trace("Got header....");
						String header = fullHttp.substring(0, i);
						// LOG.trace("Only header ->"+header+"<-");
						fullHttp = fullHttp.substring(i + sep.length());
						// LOG.trace("rest data ->"+fullHttp+"<-");
						if (fullHttp.startsWith("HTTP/ ") && fullHttp.indexOf(sep) != -1 && looper < 100) {
							// LOG.trace("second header was found!");
							sb.setLength(0);
							sb.append(fullHttp);
							looper++;
							continue;// re-process
						}
						i = header.toUpperCase().indexOf(contLen);
						LOG.trace("read some");
						if (i != -1) {
							LOG.trace("got Content-Length :" + header);
							int j = header.indexOf("\r\n", i + 2 + 15);
							if (j != -1) {
								String len = header.substring(i + 2 + 15, j).trim();
								LOG.trace("hlen: " + len);
								LOG.trace("data len: " + fullHttp.length());
								int lenCL = Integer.parseInt(len);
								LOG.trace("lenCL: " + lenCL);
								if (lenCL <= fullHttp.length()) {
									temp = null;// so it breaks out of main loop
									LOG.trace("break of main loop");
									break;
								}
							}
						}
						break;
					}
				}// while (looper < 110 && hDat.getReadByteCnt() > 0);
			} while (temp != null && hDat.getReadByteCnt() > 0);
		} catch (ConnectException e) {
			LOG.fatal("ConnectException: " + e);
			throw e;
		} catch (Exception e) {
			LOG.error("Error: " + e, e);
			throw e;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception ee) {
					LOG.warn("send(HttpData, String, Map<String,String>) - exception ignored", ee); //$NON-NLS-1$
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception ee) {
					LOG.warn("send(HttpData, String, Map<String,String>) - exception ignored", ee); //$NON-NLS-1$
				}
			}
			if (sockt != null) {
				try {
					sockt.close();
				} catch (Exception ee) {
					LOG.warn("send(HttpData, String, Map<String,String>) - exception ignored", ee); //$NON-NLS-1$
				}
			}
		}

		// check for HTTP/1.1 200 OK
		final String reply = sb.toString();

		if (reply != null && reply.length() != 0) {
			int i = reply.indexOf("\r\n");
			if (i != -1) {
				String header = reply.substring(0, i);
				if (header.endsWith("200 OK") == false) {
					throw new IOException("Bad HTTP Response! " + header);
				}
			}
		}
		final String reply1 = removeHttpHeader(reply);

		if (LOG.isDebugEnabled()) {
			LOG.debug("send(HttpData, String, Map<String,String>) - end"); //$NON-NLS-1$
		}
		return reply1;
	}

	/**
	 * Read input stream.
	 *
	 * @param bis the bis
	 * @param hp the hp
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] readInputStream(BufferedInputStream bis, HttpData hp) throws IOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("readInputStream(BufferedInputStream, HttpData) - start"); //$NON-NLS-1$
		}

		hp.setReadByteCnt(0);
		if (bis == null) {
			LOG.warn("readInputStream bis was null ! ");
			return null;
		}
		byte data[] = null;
		LOG.trace("readIs Waiting for read...");
		int s = bis.read();
		if (s == -1) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("readInputStream(BufferedInputStream, HttpData) - end"); //$NON-NLS-1$
			}
			return null; // Connection lost
		}
		hp.setReadByteCnt(1);
		int alength = bis.available();
		LOG.trace("readIs Data available: " + alength);
		if (alength > 0) {
			data = new byte[alength + 1];
			data[0] = (byte) s;
			int read = bis.read(data, 1, alength);
			if (read != alength) {
				byte[] data2 = new byte[read + 1];
				System.arraycopy(data, 0, data2, 0, read + 1);
				data = data2;
			}
			hp.setReadByteCnt(read + 1);
		} else {
			data = new byte[1];
			data[0] = (byte) s;
		}
		LOG.trace("readIs2: ");
		return data;
	}

	/**
	 * Removes the http header.
	 *
	 * @param data the data
	 * @return the string
	 */
	public static String removeHttpHeader(String data) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("removeHttpHeader(String) - start"); //$NON-NLS-1$
		}

		if (data == null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("removeHttpHeader(String) - end"); //$NON-NLS-1$
			}
			return null;
		}
		int i = data.indexOf("\r\n\r\n");
		if (i > -1) {
			String returnString = data.substring(i + 4);
			if (LOG.isDebugEnabled()) {
				LOG.debug("removeHttpHeader(String) - end"); //$NON-NLS-1$
			}
			return returnString;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("removeHttpHeader(String) - end"); //$NON-NLS-1$
		}
		return data;
	}

}
