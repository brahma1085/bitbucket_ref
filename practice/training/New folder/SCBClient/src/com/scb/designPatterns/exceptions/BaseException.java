package com.scb.designPatterns.exceptions;
import java.io.PrintStream;
import java.io.PrintWriter;
public class BaseException extends Exception {
	/**
	 * By giving <code>BaseException</code> a reference to a Throwable object,
	 * exception chaining can be enforced easily.
	 */
	private Throwable previousThrowable = null;
	public BaseException() {
	}
	public BaseException(String inMessage) {
		super(inMessage);
	}
	public BaseException(String inMessage, Throwable inThrowable) {
		super(inMessage);
		this.previousThrowable = inThrowable;
	}
	public BaseException(Throwable inThrowable) {
		this.previousThrowable = inThrowable;
	}

}