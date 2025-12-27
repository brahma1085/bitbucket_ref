package com.sssoft.isatt.ui.exception;

/**
 * The Class ControllerException.
 */
public class ControllerException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor for this exception type.
	 */
	public ControllerException() {
		super();
	}

	/**
	 * Provide the proper error message.
	 * 
	 * @param msg
	 *            the String
	 */
	public ControllerException(String msg) {
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
	public ControllerException(String msg, Throwable e) {
		super(msg, e);
	}

}
