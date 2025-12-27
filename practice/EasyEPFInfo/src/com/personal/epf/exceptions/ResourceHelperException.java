package com.personal.epf.exceptions;

/**
 * @author brahma
 * 
 *         The Class ResourceHelperException. It handles exceptional situations
 *         in data base helper classes
 */
public class ResourceHelperException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The exception message. */
	String exceptionMsg = "";

	/**
	 * Instantiates a new resource helper exception.
	 */
	public ResourceHelperException() {
		super();
	}

	/**
	 * Instantiates a new resource helper exception.
	 * 
	 * @param exceptionMessage
	 *            the exception message
	 */
	public ResourceHelperException(String exceptionMessage) {
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
