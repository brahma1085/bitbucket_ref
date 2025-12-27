package edu.exceptions;

public class DaoException extends Exception {

	private static final long serialVersionUID = 1L;
	private String msg = "";

	public DaoException(String msg) {
		this.msg = msg;
	}

	public DaoException() {
		super();
	}

	public String toString() {
		return msg;
	}

}
