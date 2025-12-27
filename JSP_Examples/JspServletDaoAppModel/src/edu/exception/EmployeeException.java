package edu.exception;

public class EmployeeException extends Exception {

	private static final long serialVersionUID = 1L;
	private String exceptionMsg;

	@Override
	public String toString() {
		return "EmployeeException [exceptionMsg=" + exceptionMsg + "]";
	}

	public EmployeeException(String exceptionMsg) {
		super();
		this.exceptionMsg = exceptionMsg;
	}

	public EmployeeException() {
		super();
	}
}
