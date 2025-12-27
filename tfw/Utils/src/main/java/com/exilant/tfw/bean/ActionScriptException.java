package com.exilant.tfw.bean;

/**
 * The Class ActionScriptException.
 */
public class ActionScriptException extends Exception {

	/** ActionScript exception. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new action script exception.
	 */
	public ActionScriptException() {
		super();
	}

	/**
	 * Instantiates a new action script exception.
	 * 
	 * @param msg
	 *            the msg
	 */
	public ActionScriptException(String msg) {
		super(msg);
	}

	/**
	 * Instantiates a new action script exception.
	 * 
	 * @param msg
	 *            the msg
	 * @param e
	 *            the e
	 */
	public ActionScriptException(String msg, Exception e) {
		super(msg, e);
	}

}
