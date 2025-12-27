package com.eris.exceptions;

public class ResourceHelperException extends Exception {

	private static final long serialVersionUID = 1L;
	String exceptionMsg = "";

	public ResourceHelperException() {
		super();
	}

	public ResourceHelperException(String exceptionMessage) {
		super(exceptionMessage);
		exceptionMsg = exceptionMessage;
	}

	public String toString() {
		return exceptionMsg;
	}

}
