package edu.exceptions;

public class ResourceHelperException extends Exception {

	private static final long serialVersionUID = 1L;
	private String msg;

	public ResourceHelperException(String msg) {
		this.msg = msg;
	}

	public ResourceHelperException() {
		super();
	}

	@Override
	public String toString() {
		return msg;
	}
}
