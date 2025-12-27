package edu.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.exceptions.StudentsExceptions;
import edu.factories.ServiceFactory;
import edu.models.Students;
import edu.services.StudentService;

public class StudentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Students students = new Students();
		students.setStudentNo(request.getParameter("studentNo"));
		students.setStudentName(request.getParameter("studentName"));
		StudentService studentService = ServiceFactory.getStudentService();
		RequestDispatcher dispatcher = null;
		boolean flag = false;
		try {
			flag = studentService.insertStudents(students);
			if (flag) {
				dispatcher = request
						.getRequestDispatcher("/WEB-INF/students/success.jsp");
				dispatcher.forward(request, response);
			} else {
				dispatcher = request
						.getRequestDispatcher("/WEB-INF/students/failure.jsp");
				dispatcher.forward(request, response);
			}
		} catch (StudentsExceptions e) {
			System.out.println("StudentsExceptions==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			dispatcher = request
					.getRequestDispatcher("/WEB-INF/students/error.jsp");
			dispatcher.forward(request, response);
		}
	}

}
