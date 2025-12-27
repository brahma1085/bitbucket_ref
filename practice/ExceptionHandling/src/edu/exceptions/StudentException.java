package edu.exceptions;

public class StudentException extends Exception {

	private static final long serialVersionUID = 1L;
	private static String exmsg;

	public StudentException(String msg) {
		super(exmsg);
		exmsg = msg;
	}

	public StudentException() {
		super();
	}

	public String toString() {
		return exmsg;
	}

}
