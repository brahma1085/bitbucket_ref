package edu.exceptions;

public class ServiceException extends Exception {

	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String message;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
		this.message = message;
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

}
