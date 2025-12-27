package com.eris.exceptions;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	String exceptionMsg = "";

	public ServiceException() {
		super();
	}

	public ServiceException(String exceptionMessage) {
		super(exceptionMessage);
		exceptionMsg = exceptionMessage;
	}

	public String toString() {
		return exceptionMsg;
	}

}
