package edu.exceptions;

public class StudentException extends Exception {

	private static final long serialVersionUID = 1L;
	private String exceptionMsg = "";

	public StudentException() {
		super();
	}

	public StudentException(String message) {
		super(message);
		exceptionMsg = message;
	}

	@Override
	public String toString() {
		return "StudentException [exceptionMsg=" + exceptionMsg + "]";
	}

}
