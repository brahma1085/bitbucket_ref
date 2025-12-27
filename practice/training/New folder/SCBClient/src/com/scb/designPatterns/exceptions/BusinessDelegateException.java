package com.scb.designPatterns.exceptions;

/**
 * User: Sreenivas
 * Date: Oct 14, 2007
 * Time: 10:58:35 AM
 *
 */
public class BusinessDelegateException extends ApplicationException {
       
        public BusinessDelegateException(String message) {
		super(message);
	    }
	    public BusinessDelegateException(String inMessage, Throwable inThrowable) {
		super(inMessage, inThrowable);
	    }

}
