package com.exceptions;

public class DataProcessingException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;

	@Override
	public String toString() {
		message = "problem in processing your request.....please try after some time";
		return message;
	}

	public DataProcessingException() {
		super();
	}

	public DataProcessingException(String message) {
		super(message);
		this.message = message;
	}
}
