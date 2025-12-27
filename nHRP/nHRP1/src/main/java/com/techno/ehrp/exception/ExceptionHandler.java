package com.techno.ehrp.exception;

import javax.swing.JOptionPane;

/**
 * The Class ExceptionHandler.
 * 
 * @author brahma
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang.Thread,
	 * java.lang.Throwable)
	 */
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		JOptionPane.showMessageDialog(null, e.getMessage(), "General Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Register exception handler.
	 */
	public static void registerExceptionHandler() {
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
		System.setProperty("sun.awt.exception.handler", ExceptionHandler.class.getName());
	}
}
