package edu.exception;

public class StudentException extends Exception {

	public StudentException() {
		super();
	}

	public StudentException(String exceptionMsg) {
		super();
		this.exceptionMsg = exceptionMsg;
	}

	@Override
	public String toString() {
		return "StudentException [exceptionMsg=" + exceptionMsg + "]";
	}

	private static final long serialVersionUID = 1L;
	private String exceptionMsg;

}
