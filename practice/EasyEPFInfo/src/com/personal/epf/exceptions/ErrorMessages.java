package com.personal.epf.exceptions;

import java.io.Serializable;

/**
 * @author brahma
 * 
 *         The Enum ErrorMessages. The error messages are defined to display
 *         proper error messages to the user
 */
public enum ErrorMessages implements Serializable {
	;

	/** The Constant EMPLOYEE_ID_IS_NOT_VALID. */
	public static final String EMPLOYEE_ID_IS_NOT_VALID = "employee id is not valid..please enter valid details";

	/** The Constant INTERNAL_ERROR. */
	public static final String INTERNAL_ERROR = "internal error has occured..please try after some time";

	/** The Constant UNABLE_TO_GET_CONNECTION. */
	public static final String UNABLE_TO_GET_CONNECTION = "internal server error..please try later";

}
