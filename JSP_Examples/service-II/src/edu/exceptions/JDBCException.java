package edu.exceptions;

public class JDBCException extends Exception {

	private static final long serialVersionUID = 1L;
	private String exceptionMsg = "";

	public JDBCException() {
		super();
	}

	public JDBCException(String exceptionMsg) {
		super(exceptionMsg);
		this.exceptionMsg = exceptionMsg;
	}

	public String toString() {
		return exceptionMsg;

	}
}
