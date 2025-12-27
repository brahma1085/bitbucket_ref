package com.scb.designPatterns.exceptions;

public class EJBCreateException extends ApplicationException {
	/**
	 * @param inMessage - descriptive error message indicating the source of the problem
	 * @param inThrowable - the caught exception to be wrapped
	 */
	public EJBCreateException(String inMessage, Throwable inThrowable) {
		super(inMessage, inThrowable);
	}
}