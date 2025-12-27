package com.scb.designPatterns.exceptions;

import javax.naming.NamingException;

/**
 * User: Sreenivas
 * Date: Oct 14, 2007
 * Time: 10:47:34 AM
 *
 */
public class ServiceLocatorException extends SystemException {
   public ServiceLocatorException(String message) {
		super(message);
	}
	public ServiceLocatorException(String inMessage, Throwable inThrowable) {
		super(inMessage, inThrowable);
	}
    
}
