package edu.exceptions;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	private String exceptionMsg = "";

	public ServiceException() {
		super();
	}

	public ServiceException(String exceptionMsg) {
		super(exceptionMsg);
		this.exceptionMsg = exceptionMsg;
	}

	public String toString() {
		return exceptionMsg;

	}
}
