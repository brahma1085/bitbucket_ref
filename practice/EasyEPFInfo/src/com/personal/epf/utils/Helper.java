package com.personal.epf.utils;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author brahma
 * 
 *         The Class Helper. It provides request dispatching mechanism
 */
public class Helper implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Request Dispatching mechanism has been implemented to provide code
	 * re-usability
	 * 
	 * @param http
	 *            servlet request the request object
	 * @param http
	 *            servlet response the response object
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void dispatch(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request
				.getRequestDispatcher(Constants.CONTENT);
		dispatcher.forward(request, response);
	}
}
