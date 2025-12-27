package com.sssoft.isatt.data.exception;

/**
 * The Class CacheException.
 */
public class CacheException extends Exception {

	/** Generated serial version id. */
	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor for this exception type.
	 */
	public CacheException() {
		super();
	}

	/**
	 * Provide the proper error message.
	 * 
	 * @param msg
	 *            the String
	 */
	public CacheException(String msg) {
		super(msg);
	}

	/**
	 * provide the proper error message and the exception type which should be
	 * handled by the caller.
	 * 
	 * @param msg
	 *            the String
	 * @param e
	 *            the Throwable
	 */
	public CacheException(String msg, Throwable e) {
		super(msg, e);
	}

}
