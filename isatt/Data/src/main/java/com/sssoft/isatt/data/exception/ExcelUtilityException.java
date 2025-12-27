package com.sssoft.isatt.data.exception;

/**
 * The Class ExcelUtilityException.
 */
public class ExcelUtilityException extends Exception {

	/** Generated serial version id. */
	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor for this exception type.
	 */
	public ExcelUtilityException() {
		super();
	}

	/**
	 * Provide the proper error message.
	 * 
	 * @param msg
	 *            the String
	 */
	public ExcelUtilityException(String msg) {
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
	public ExcelUtilityException(String msg, Throwable e) {
		super(msg, e);
	}

}
