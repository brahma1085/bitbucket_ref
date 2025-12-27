package com.exceptions;

public class AccountNotFound extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;

	public AccountNotFound() {
		super();
	}

	public AccountNotFound(String message) {
		super(message);
		this.message = message;
	}

	@Override
	public String toString() {
		message = "You bloody.....this account is not yet created";
		return message;
	}
}
