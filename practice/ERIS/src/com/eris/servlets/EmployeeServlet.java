package com.eris.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eris.exceptions.ServiceException;
import com.eris.factories.ServiceFactory;
import com.eris.model.Employee;
import com.eris.services.EmployeeService;

public class EmployeeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		Employee employee = new Employee();
		employee.setEmpid(Integer.parseInt(request.getParameter("employeeid")));
		employee.setFirstname(request.getParameter("firstname"));
		employee.setLastname(request.getParameter("lastname"));
		employee.setInitial(request.getParameter("initial"));
		employee.setDob(request.getParameter("dateofbirth"));
		employee.setMarstatus(request.getParameter("maritalstatus"));
		employee.setGender(request.getParameter("gender"));
		employee.setDestination(request.getParameter("destination"));
		employee.setRole(request.getParameter("role"));
		employee.setPassword(request.getParameter("password"));
		employee
				.setClientid(Integer.parseInt(request.getParameter("clientid")));
		employee.setAddress(request.getParameter("address"));
		employee.setCity(request.getParameter("city"));
		employee.setZip(request.getParameter("zip"));
		employee.setState(request.getParameter("state"));
		employee.setCountry(request.getParameter("country"));
		employee.setDayphone(request.getParameter("dayphone"));
		employee.setExtension(request.getParameter("ext"));
		employee.setEvenphone(request.getParameter("eveningphone"));
		employee.setMobilephone(request.getParameter("mobilephone"));
		employee.setEmail(request.getParameter("email"));
		EmployeeService employeeService = ServiceFactory.getEmployeeService();
		long empid = 0;
		List list = new ArrayList();
		boolean flag = false;
		RequestDispatcher dispatcher = null;
		try {
			empid = employeeService.retrieveEmpid();
			if (empid != 0) {
				request.getSession().setAttribute("empid", empid);
			}
			list = employeeService.retrieveClientid();
			if (list != null) {
				request.getSession().setAttribute("clientid", list);
			}
			flag = employeeService.insertEmployeeDetails(employee);
			if (flag) {
				dispatcher = request
						.getRequestDispatcher("/WebRoot/WEB-INF/administrator/success.jsp");
				dispatcher.forward(request, response);
			} else {
				dispatcher = request
						.getRequestDispatcher("/WEB-INF/administrator/employeefailure.jsp");
				dispatcher.forward(request, response);
			}
		} catch (ServiceException e) {
			System.out.println("ServiceException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			dispatcher = request.getRequestDispatcher("/error.jsp");
			dispatcher.forward(request, response);
		}
	}
}
