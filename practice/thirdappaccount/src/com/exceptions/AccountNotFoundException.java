package com.exceptions;

public class AccountNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;

	public AccountNotFoundException() {
		super();
	}

	public AccountNotFoundException(String message) {
		super(message);
		this.message = message;
	}

	@Override
	public String toString() {
		message = "You bloody.....this account is not yet created";
		return message;
	}
}
