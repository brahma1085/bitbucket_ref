package edu.exceptions;

public class DaoException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String msg;

	@Override
	public String toString() {
		return msg;
	}

	public DaoException() {
		super();
	}

	public DaoException(String msg) {
		this.msg = msg;
	}
}
