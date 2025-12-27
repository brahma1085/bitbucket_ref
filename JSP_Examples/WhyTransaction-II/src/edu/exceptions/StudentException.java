package edu.exceptions;

public class StudentException extends Exception {

	private static final long serialVersionUID = 1L;
	private String exceptionString = "";

	public StudentException() {
		super();
	}

	public StudentException(String exceptionString) {
		super(exceptionString);
		this.exceptionString = exceptionString;
	}

	public String toString() {
		return exceptionString;

	}
}
