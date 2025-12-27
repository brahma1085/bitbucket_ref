package com.scb.designPatterns.exceptions;

/**
 * This exception indicate an application error the end user may be able to
 * recover from. The web tie should generally return control back to the 
 * input page and display a user friendly message to the end user.
*/
public class ApplicationException extends BaseException {
	public ApplicationException(String message) {
		super(message);
	}
	public ApplicationException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
