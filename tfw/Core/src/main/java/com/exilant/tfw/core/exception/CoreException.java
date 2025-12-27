package com.exilant.tfw.core.exception;

/**
 * The Class CoreException.
 */
public class CoreException extends Exception {

	/** Generated serial version id. */
	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor for this exception type.
	 */
	public CoreException() {
		super();
	}

	/**
	 * Provide the proper error message.
	 *
	 * @param msg the String
	 */
	public CoreException(String msg) {
		super(msg);
	}

	/**
	 * provide the proper error message and the exception type which should be
	 * handled by the caller.
	 *
	 * @param msg the String
	 * @param e the Throwable
	 */
	public CoreException(String msg, Throwable e) {
		super(msg, e);
	}

}
