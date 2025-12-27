package com.personal.epf.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.personal.epf.utils.Constants;
import com.personal.epf.utils.Helper;

/**
 * @author brahma
 * 
 *         The Class ForwardServlet. It handles dispatching mechanism to proper
 *         JSP based on user request
 */
public class ForwardServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(ForwardServlet.class);

	/** The Constant SUMMARY. */
	private static final String SUMMARY = "Summary";

	/** The Constant PF_DETAILS. */
	private static final String PF_DETAILS = "PF details";

	/** The Constant REGISTER. */
	private static final String REGISTER = "Register";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String value = request.getParameter(Constants.VALUE);
		try {
			if (value != null) {
				if (value.equalsIgnoreCase(REGISTER)) {
					request.getSession().setAttribute(Constants.VALUE,
							Constants.EMPLOYEEREGISTRATION_JSP);
					Helper.dispatch(request, response);
				} else if (value.equalsIgnoreCase(PF_DETAILS)) {
					request.getSession().setAttribute(Constants.VALUE,
							Constants.EMPLOYEEPFDETAILS_JSP);
					Helper.dispatch(request, response);
				} else if (value.equalsIgnoreCase(SUMMARY)) {
					request.getSession().setAttribute(Constants.VALUE,
							Constants.EMPLOYEESUMMARY_JSP);
					Helper.dispatch(request, response);
				} else {
					request.getSession().setAttribute(Constants.MESSAGE,
							Constants.FAILURE_MSG);
					Helper.dispatch(request, response);
				}
			}
		} catch (Exception e) {
			logger.error(Constants.INTERNAL_ERROR);
			request.getSession().setAttribute(Constants.MESSAGE,
					Constants.INTERNAL_ERROR);
			Helper.dispatch(request, response);
		}
	}

}
