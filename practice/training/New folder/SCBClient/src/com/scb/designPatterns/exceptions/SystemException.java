package com.scb.designPatterns.exceptions;

public class SystemException extends BaseException {
	public SystemException(String message) {
		super(message);
	}
	public SystemException(String inMessage, Throwable inThrowable) {
		super(inMessage, inThrowable);
	}
}