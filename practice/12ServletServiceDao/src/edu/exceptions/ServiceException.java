package edu.exceptions;

public class ServiceException extends Exception {
	private static final long serialVersionUID = 1L;
	private String msg;

	@Override
	public String toString() {
		return msg;
	}

	public ServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServiceException(String msg) {
		super();
		this.msg = msg;
	}
}
