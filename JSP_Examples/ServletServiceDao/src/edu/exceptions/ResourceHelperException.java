package edu.exceptions;

public class ResourceHelperException extends Exception {

	private static final long serialVersionUID = 1L;
	private String msg = "";

	public ResourceHelperException() {
		super();
	}

	public ResourceHelperException(String message) {
		msg = message;
	}

	public String toString() {
		return msg;
	}

}
