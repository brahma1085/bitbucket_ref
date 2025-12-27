package com.eris.exceptions;

public class DaoException extends Exception {

	private static final long serialVersionUID = 1L;
	String exceptionMsg = "";

	public DaoException() {
		super();
	}

	public DaoException(String exceptionMessage) {
		super(exceptionMessage);
		exceptionMsg = exceptionMessage;
	}

	public String toString() {
		return exceptionMsg;
	}

}
