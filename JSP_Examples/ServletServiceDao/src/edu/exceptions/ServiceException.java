package edu.exceptions;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	private String msg = "";

	public String toString() {
		return msg;
	}

	public ServiceException(String msg) {
		this.msg = msg;
	}

	public ServiceException() {
		super();
	}

}
