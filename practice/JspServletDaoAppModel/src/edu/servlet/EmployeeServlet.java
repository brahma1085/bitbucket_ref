package edu.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.dao.EmployeeDao;
import edu.exception.EmployeeException;
import edu.model.Employee;

public class EmployeeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String EMPLOYEE = "employee";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Employee employee = new Employee();
		employee.setEmployeeNo(new Long(request.getParameter("employeeno")));
		employee.setEmployeeName(request.getParameter("employeename"));
		EmployeeDao employeeDao = new EmployeeDao();
		RequestDispatcher rDispatcher = null;
		try {
			employeeDao.insertStudent(employee);
			request.setAttribute(EmployeeServlet.EMPLOYEE, employee);
			rDispatcher = request.getRequestDispatcher("./j3");
			rDispatcher.forward(request, response);
		} catch (EmployeeException e) {
			rDispatcher = request.getRequestDispatcher("./j5");
			rDispatcher.forward(request, response);
		}
	}
}
