package com.personal.epf.exceptions;

/**
 * @author brahma
 * 
 *         The Class DaoException. It handles exceptional situations in DAO
 *         layer
 */
public class DaoException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The exception msg. */
	String exceptionMsg = "";

	/**
	 * Instantiates a new dao exception.
	 */
	public DaoException() {
		super();
	}

	/**
	 * Instantiates a new dao exception.
	 * 
	 * @param exceptionMessage
	 *            the exception message
	 */
	public DaoException(String exceptionMessage) {
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
