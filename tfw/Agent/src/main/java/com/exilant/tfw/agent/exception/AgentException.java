package com.exilant.tfw.agent.exception;

/**
 * The Class AgentException.
 */
public class AgentException extends Exception {

	/** Generated serial version id. */
	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor for this exception type.
	 */
	public AgentException() {
		super();
	}

	/**
	 * Provide the proper error message.
	 * 
	 * @param msg
	 *            the String
	 */
	public AgentException(String msg) {
		super(msg);
	}

	/**
	 * provide the proper error message and the exception type which should be
	 * handled by the caller.
	 * 
	 * @param msg
	 *            the String
	 * @param e
	 *            the Throwable
	 */
	public AgentException(String msg, Throwable e) {
		super(msg, e);
	}

}
