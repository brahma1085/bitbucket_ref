package edu.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.dao.StudentDao;
import edu.exception.StudentException;
import edu.model.Student;

public class StudentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String STUDENT = "student";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Student student = new Student();
		student.setStudentNo(new Long(request.getParameter("studentno")));
		student.setStudentName(request.getParameter("studentname"));
		StudentDao studentDao = new StudentDao();
		RequestDispatcher rDispatcher = null;
		try {
			studentDao.insertStudent(student);
			request.setAttribute(StudentServlet.STUDENT, student);
			rDispatcher = request.getRequestDispatcher("./j4");
			rDispatcher.forward(request, response);
		} catch (StudentException e) {
			rDispatcher = request.getRequestDispatcher("./j5");
			rDispatcher.forward(request, response);
		}
	}
}
