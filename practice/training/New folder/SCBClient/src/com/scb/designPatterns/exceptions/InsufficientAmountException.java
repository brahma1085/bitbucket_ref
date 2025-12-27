package com.scb.designPatterns.exceptions;

public class InsufficientAmountException extends ApplicationException {
	
	public InsufficientAmountException(String inMessage, Throwable inThrowable) {
		super(inMessage, inThrowable);
	}

}
