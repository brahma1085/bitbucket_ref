package com.personal.epf.exceptions;

/**
 * @author brahma
 * 
 *         The Class ServiceException. It handles exceptional situations in
 *         service layer
 */
public class ServiceException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The exception msg. */
	String exceptionMsg = "";

	/**
	 * Instantiates a new service exception.
	 */
	public ServiceException() {
		super();
	}

	/**
	 * Instantiates a new service exception.
	 * 
	 * @param exceptionMessage
	 *            the exception message
	 */
	public ServiceException(String exceptionMessage) {
		super(exceptionMessage);
		exceptionMsg = exceptionMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#toString()
	 */
	public String toString() {
		return exceptionMsg;
	}

}
