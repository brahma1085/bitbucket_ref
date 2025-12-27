package com.exilant.tfw.exception;

/**
 * The Class ServiceException.
 */
public class ServiceException extends Exception {

	/** Generated serial version id. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new service exception.
	 */
	public ServiceException() {
		super();
	}

	/**
	 * Provide the proper error message.
	 * 
	 * @param msg
	 *            the String
	 */
	public ServiceException(String msg) {
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
	public ServiceException(String msg, Throwable e) {
		super(msg, e);
	}

}
