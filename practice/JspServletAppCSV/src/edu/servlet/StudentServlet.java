package edu.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.model.Student;
import edu.service.StudentService;

public class StudentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student = new Student();
		student.setSno(Integer.parseInt(request.getParameter("t1")));
		student.setSname(request.getParameter("t2"));
		RequestDispatcher rDispatcher = null;
		StudentService studentDao = new StudentService();
		try {
			studentDao.insertStudent(student);
			rDispatcher = request
					.getRequestDispatcher("/WEB-INF/student/success.jsp");
			rDispatcher.forward(request, response);
		} catch (Exception e) {
			rDispatcher = request
					.getRequestDispatcher("/WEB-INF/student/failure.jsp");
			rDispatcher.forward(request, response);
		}
	}
}
