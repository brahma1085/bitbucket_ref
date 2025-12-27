package com.personal.epf.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.personal.epf.exceptions.ErrorMessages;
import com.personal.epf.exceptions.ServiceException;
import com.personal.epf.factory.ServiceFactory;
import com.personal.epf.model.Employee;
import com.personal.epf.service.EmployeeDetailsService;
import com.personal.epf.utils.Constants;
import com.personal.epf.utils.Helper;

/**
 * @author brahma
 * 
 *         The Class EmployeeDetailsServlet. It fetches the employee details
 *         based on the employee id and dispatches to proper JSP
 */
public class EmployeeDetailsServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = Logger
			.getLogger(EmployeeDetailsServlet.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType(Constants.CONTENT_TYPE);
		Employee employee = null;
		EmployeeDetailsService employeeService = ServiceFactory
				.getEmployeeDetailsService();
		try {
			if (request.getParameter(Constants.EMP_KEY) != null
					&& !request.getParameter(Constants.EMP_KEY).equals("")) {
				employee = employeeService.retrieveEmployeeDetails(Integer
						.parseInt(request.getParameter(Constants.EMP_KEY)));
				if (employee != null) {
					request.getSession().setAttribute(Constants.EMP_OBJ,
							employee);
					request.getSession().setAttribute(Constants.VALUE,
							Constants.EMPLOYEESUMMARY_JSP);
					request.getSession().setAttribute(Constants.MESSAGE,
							Constants.SUCCESS_MSG);
					Helper.dispatch(request, response);
				} else {
					request.getSession().setAttribute(Constants.VALUE,
							Constants.EMPLOYEESUMMARY_JSP);
					request.getSession().setAttribute(Constants.MESSAGE,
							Constants.FAILURE_MSG);
					Helper.dispatch(request, response);
				}
			} else {
				throw new Exception(ErrorMessages.EMPLOYEE_ID_IS_NOT_VALID);
			}
		} catch (ServiceException e) {
			logger.error(Constants.RETRIEVE_ERROR_MSG);
			doErrorDispatch(request, response);
		} catch (Exception e) {
			logger.error(Constants.RETRIEVE_ERROR_MSG);
			doErrorDispatch(request, response);
		}
	}

	/**
	 * Do error dispatch.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void doErrorDispatch(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute(Constants.VALUE,
				Constants.EMPLOYEESUMMARY_JSP);
		request.getSession().setAttribute(Constants.MESSAGE,
				Constants.RETRIEVE_ERROR_MSG);
		Helper.dispatch(request, response);
	}

}
