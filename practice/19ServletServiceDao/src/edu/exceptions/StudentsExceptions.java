package edu.exceptions;

public class StudentsExceptions extends Exception {

	private static final long serialVersionUID = 1L;
	private String exceptionMsg;

	public StudentsExceptions(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	public StudentsExceptions() {
		super();
	}

	@Override
	public String toString() {
		return exceptionMsg;
	}

}
