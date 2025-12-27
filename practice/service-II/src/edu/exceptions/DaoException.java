package edu.exceptions;

public class DaoException extends Exception {

	private static final long serialVersionUID = 1L;
	private String exceptionString = "";

	public DaoException() {
		super();
	}

	public DaoException(String exString) {
		super(exString);
		exceptionString = exString;
	}

	public String toString() {
		return exceptionString;

	}
}
