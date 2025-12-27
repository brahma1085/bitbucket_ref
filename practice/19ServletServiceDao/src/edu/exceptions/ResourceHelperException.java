package edu.exceptions;

public class ResourceHelperException extends Exception {

	private static final long serialVersionUID = 1L;
	private String exceptionMsg;

	public ResourceHelperException() {
		super();
	}

	public ResourceHelperException(String message) {
		exceptionMsg = message;
	}

	@Override
	public String toString() {
		return exceptionMsg;
	}

}
