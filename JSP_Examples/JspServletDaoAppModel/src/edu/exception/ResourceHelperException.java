package edu.exception;

public class ResourceHelperException extends Exception {

	private static final long serialVersionUID = 1L;
	private String exceptionMsg;

	public ResourceHelperException() {
		super();
	}

	public ResourceHelperException(String exceptionMsg) {
		super();
		this.exceptionMsg = exceptionMsg;
	}

	@Override
	public String toString() {
		return "ResourceHelperException [exceptionMsg=" + exceptionMsg + "]";
	}
}
