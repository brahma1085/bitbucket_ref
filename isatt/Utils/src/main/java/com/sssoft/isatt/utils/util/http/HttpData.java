package com.sssoft.isatt.utils.util.http;

import org.apache.log4j.Logger;

import java.util.Map;

/**
 * The Class HttpData.
 */
public class HttpData {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(HttpData.class);
	
	/** The url. */
	private String url;
	
	/** The compre options. */
	private int compreOptions;
	
	/** The params. */
	private Map<String, Object> params;
	
	/** The send type. */
	private SendVia sendType = SendVia.DIRECT;

	/** The send method. */
	private HttpSendMethod sendMethod = HttpSendMethod.POST;
	
	/** The timeout milli. */
	private int timeoutMilli = 30000;

	/** The headers to save. */
	private Map<String, String> headersToSave;
	
	/** The saved headers. */
	private Map<String, String> savedHeaders;
	
	/** The headers for subsequnt requests. */
	private Map<String, String> headersForSubsequntRequests;

	/** The send saved headers. */
	private boolean sendSavedHeaders;

	/** The read byte cnt. */
	private int readByteCnt;

	/**
	 * Instantiates a new http data.
	 */
	public HttpData() {

	}

	/**
	 * Instantiates a new http data.
	 *
	 * @param url the url
	 */
	public HttpData(String url) {
		super();
		this.url = url;
	}

	/**
	 * Instantiates a new http data.
	 *
	 * @param url the url
	 * @param params the params
	 */
	public HttpData(String url, Map<String, Object> params) {
		super();
		this.url = url;
		this.params = params;
	}

	/**
	 * Instantiates a new http data.
	 *
	 * @param url the url
	 * @param compreOptions the compre options
	 * @param params the params
	 */
	public HttpData(String url, int compreOptions, Map<String, Object> params) {
		super();
		this.url = url;
		this.compreOptions = compreOptions;
		this.params = params;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the compre options.
	 *
	 * @return the compre options
	 */
	public int getCompreOptions() {
		return compreOptions;
	}

	/**
	 * Sets the compre options.
	 *
	 * @param compreOptions the new compre options
	 */
	public void setCompreOptions(int compreOptions) {
		this.compreOptions = compreOptions;
	}

	/**
	 * Gets the params.
	 *
	 * @return the params
	 */
	public Map<String, Object> getParams() {
		return params;
	}

	/**
	 * Sets the params.
	 *
	 * @param params the params
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 * Gets the send type.
	 *
	 * @return the send type
	 */
	public SendVia getSendType() {
		return sendType;
	}

	/**
	 * Sets the send type.
	 *
	 * @param sendType the new send type
	 */
	public void setSendType(SendVia sendType) {
		this.sendType = sendType;
	}

	/**
	 * Gets the send method.
	 *
	 * @return the send method
	 */
	public HttpSendMethod getSendMethod() {
		return sendMethod;
	}

	/**
	 * Sets the send method.
	 *
	 * @param sendMethod the new send method
	 */
	public void setSendMethod(HttpSendMethod sendMethod) {
		this.sendMethod = sendMethod;
	}

	/**
	 * Sets the timeout milli.
	 *
	 * @param timeout the new timeout milli
	 */
	public void setTimeoutMilli(int timeout) {
		this.timeoutMilli = timeout;
	}

	/**
	 * Gets the timeout milli.
	 *
	 * @return the timeout milli
	 */
	public int getTimeoutMilli() {

		return timeoutMilli;
	}

	/**
	 * Gets the headers to save.
	 *
	 * @return the headers to save
	 */
	public final Map<String, String> getHeadersToSave() {
		return headersToSave;
	}

	/**
	 * Gets the header to save.
	 *
	 * @param s the s
	 * @return the header to save
	 */
	public final String getHeaderToSave(String s) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getHeaderToSave(String) - start"); //$NON-NLS-1$
		}

		String returnString = headersToSave.get(s);
		if (LOG.isDebugEnabled()) {
			LOG.debug("getHeaderToSave(String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Sets the header to save.
	 *
	 * @param name the name
	 * @param vl the vl
	 */
	public final void setHeaderToSave(String name, String vl) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("setHeaderToSave(String, String) - start"); //$NON-NLS-1$
		}

		this.headersToSave.put(name, vl);

		if (LOG.isDebugEnabled()) {
			LOG.debug("setHeaderToSave(String, String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Sets the headers to save.
	 *
	 * @param headersToSave the headers to save
	 */
	public final void setHeadersToSave(Map<String, String> headersToSave) {
		this.headersToSave = headersToSave;
	}

	/**
	 * Gets the saved headers.
	 *
	 * @return the saved headers
	 */
	public final Map<String, String> getSavedHeaders() {
		return savedHeaders;
	}

	/**
	 * Gets the saved header.
	 *
	 * @param s the s
	 * @return the saved header
	 */
	public final String getSavedHeader(String s) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSavedHeader(String) - start"); //$NON-NLS-1$
		}

		String returnString = savedHeaders.get(s);
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSavedHeader(String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Sets the saved header.
	 *
	 * @param name the name
	 * @param vl the vl
	 */
	public final void setSavedHeader(String name, String vl) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("setSavedHeader(String, String) - start"); //$NON-NLS-1$
		}

		this.savedHeaders.put(name, vl);

		if (LOG.isDebugEnabled()) {
			LOG.debug("setSavedHeader(String, String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Sets the saved headers.
	 *
	 * @param savedHeaders the saved headers
	 */
	public final void setSavedHeaders(Map<String, String> savedHeaders) {
		this.savedHeaders = savedHeaders;
	}

	/**
	 * Gets the header for subsequnt requests.
	 *
	 * @param s the s
	 * @return the header for subsequnt requests
	 */
	public final String getHeaderForSubsequntRequests(String s) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getHeaderForSubsequntRequests(String) - start"); //$NON-NLS-1$
		}

		String returnString = savedHeaders.get(s);
		if (LOG.isDebugEnabled()) {
			LOG.debug("getHeaderForSubsequntRequests(String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Sets the header for subsequnt requests.
	 *
	 * @param name the name
	 * @param vl the vl
	 */
	public final void setHeaderForSubsequntRequests(String name, String vl) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("setHeaderForSubsequntRequests(String, String) - start"); //$NON-NLS-1$
		}

		this.savedHeaders.put(name, vl);

		if (LOG.isDebugEnabled()) {
			LOG.debug("setHeaderForSubsequntRequests(String, String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the headers for subsequnt requests.
	 *
	 * @return the headers for subsequnt requests
	 */
	public final Map<String, String> getHeadersForSubsequntRequests() {
		return headersForSubsequntRequests;
	}

	/**
	 * Sets the headers for subsequnt requests.
	 *
	 * @param headersForSubsequntRequests the headers for subsequnt requests
	 */
	public final void setHeadersForSubsequntRequests(Map<String, String> headersForSubsequntRequests) {
		this.headersForSubsequntRequests = headersForSubsequntRequests;
	}

	/**
	 * Checks if is send saved headers.
	 *
	 * @return true, if is send saved headers
	 */
	public final boolean isSendSavedHeaders() {
		return sendSavedHeaders;
	}

	/**
	 * Sets the send saved headers.
	 *
	 * @param sendSavedHeaders the new send saved headers
	 */
	public final void setSendSavedHeaders(boolean sendSavedHeaders) {
		this.sendSavedHeaders = sendSavedHeaders;
	}

	/**
	 * Gets the read byte cnt.
	 *
	 * @return the read byte cnt
	 */
	public int getReadByteCnt() {
		return readByteCnt;
	}

	/**
	 * Sets the read byte cnt.
	 *
	 * @param readByteCnt the new read byte cnt
	 */
	public void setReadByteCnt(int readByteCnt) {
		this.readByteCnt = readByteCnt;
	}

}
