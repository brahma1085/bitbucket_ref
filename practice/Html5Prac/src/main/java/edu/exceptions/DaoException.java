package edu.exceptions;

public class DaoException extends Exception {

	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String message;

	public DaoException() {
		super();
	}

	public DaoException(String message) {
		super(message);
		this.message = message;
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

}
