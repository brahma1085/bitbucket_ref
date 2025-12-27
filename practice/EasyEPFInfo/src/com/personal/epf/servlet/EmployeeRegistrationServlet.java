package com.personal.epf.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.personal.epf.exceptions.ServiceException;
import com.personal.epf.factory.ServiceFactory;
import com.personal.epf.model.Employee;
import com.personal.epf.service.EmployeeDetailsService;
import com.personal.epf.utils.Constants;
import com.personal.epf.utils.Helper;

/**
 * @author brahma
 * 
 *         The Class EmployeeRegistrationServlet. It inserts the employee
 *         details and returns employee id and dispatches to proper JSP
 */
public class EmployeeRegistrationServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = Logger
			.getLogger(EmployeeRegistrationServlet.class);

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
		int empid = Constants.DEFAULT_ID;
		Employee employee = new Employee();
		setEmployeeForm(request, response, employee);
		EmployeeDetailsService employeeService = ServiceFactory
				.getEmployeeDetailsService();
		try {
			empid = employeeService.insertEmployeeDetails(employee);
			if (empid != Constants.DEFAULT_ID) {
				request.getSession().setAttribute(Constants.EMP_KEY, empid);
				request.getSession().setAttribute(Constants.VALUE,
						Constants.EMPLOYEEREGISTRATION_JSP);
				request.setAttribute(Constants.MESSAGE, Constants.SUCCESS_MSG);
				Helper.dispatch(request, response);
			} else {
				request.getSession().setAttribute(Constants.VALUE,
						Constants.EMPLOYEEREGISTRATION_JSP);
				request.setAttribute(Constants.MESSAGE, Constants.FAILURE_MSG);
				Helper.dispatch(request, response);
			}
		} catch (ServiceException e) {
			logger.error(Constants.INSERT_ERROR_MSG);
			doErrorDispatch(request, response);
		} catch (Exception e) {
			logger.error(Constants.INTERNAL_ERROR);
			doErrorDispatch(request, response);
		}
	}

	/**
	 * Sets the employee form.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param employee
	 *            the employee
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void setEmployeeForm(HttpServletRequest request,
			HttpServletResponse response, Employee employee)
			throws ServletException, IOException {
		String firstname = request.getParameter(Constants.FIRSTNAME);
		String pwd = request.getParameter(Constants.PWD);
		String zip = request.getParameter(Constants.ZIP);
		String salary = request.getParameter(Constants.SALARY);

		if (firstname != null && !firstname.trim().equals("")) {
			employee.setFirstname(firstname);
		} else {
			doDispatch(request, response);
		}
		employee.setLastname(request.getParameter(Constants.LASTNAME));
		employee.setDob(request.getParameter(Constants.DOB));
		employee.setMaritalstatus(request.getParameter(Constants.MARITALSTATUS));
		employee.setGender(request.getParameter(Constants.GENDER));
		employee.setDesignation(request.getParameter(Constants.DESIGNATION));
		if (pwd != null && !pwd.trim().equals("")) {
			employee.setPwd(pwd);
		} else {
			doDispatch(request, response);
		}
		employee.setAddress(request.getParameter(Constants.ADDRESS));
		employee.setCity(request.getParameter(Constants.CITY));
		if (zip != null && !zip.equals("")) {
			employee.setZip(Integer.parseInt(zip));
		} else {
			employee.setZip(Constants.DEFAULT_ID);
		}
		employee.setState(request.getParameter(Constants.STATE));
		employee.setCountry(request.getParameter(Constants.COUNTRY));
		employee.setDayphone(request.getParameter(Constants.DAYPHONE));
		employee.setMobile(request.getParameter(Constants.MOBILE));
		employee.setEmail(request.getParameter(Constants.EMAIL));
		employee.setMiddlename(request.getParameter(Constants.MIDDLENAME));
		if (salary != null && !salary.trim().equals("")) {
			employee.setSalary(salary);
		} else {
			doDispatch(request, response);
		}
		employee.setPfvalue(request.getParameter(Constants.PFVALUE));
	}

	/**
	 * Do dispatch.
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
	private void doDispatch(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute(Constants.VALUE,
				Constants.EMPLOYEEREGISTRATION_JSP);
		request.setAttribute(Constants.MESSAGE, Constants.REQUIRED_FIELDS);
		Helper.dispatch(request, response);
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
				Constants.EMPLOYEEREGISTRATION_JSP);
		request.setAttribute(Constants.MESSAGE, Constants.INSERT_ERROR_MSG);
		Helper.dispatch(request, response);
	}

}