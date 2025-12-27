package edu.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.exceptions.ServiceException;
import edu.factory.ServiceFactory;
import edu.model.StudentDemo1;
import edu.services.StudentService;

public class StudentServlet extends HttpServlet {
	private StudentService studentService;

	public StudentServlet() {
		studentService = ServiceFactory.getStudentService();
	}

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		boolean flag = false;
		StudentDemo1 studentDemo1 = new StudentDemo1();
		studentDemo1.setStudentno(Integer.parseInt(request.getParameter("no")));
		studentDemo1.setStudentname(request.getParameter("name"));
		RequestDispatcher dispatcher = null;
		try {
			flag = studentService.insertStudentDemo1(studentDemo1);
			if (flag) {
				dispatcher = request.getRequestDispatcher("/success.jsp");
				dispatcher.forward(request, response);
			} else {
				dispatcher = request.getRequestDispatcher("/failure.jsp");
				dispatcher.forward(request, response);
			}
		} catch (ServiceException e) {
			dispatcher = request.getRequestDispatcher("/error.jsp");
			dispatcher.forward(request, response);
		}
	}

}
