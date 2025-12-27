package edu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.factory.ServiceFactory;
import edu.model.Student001;
import edu.service.StudentService;

public class StudentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		Student001 student001 = new Student001();
		student001.setStudentno(Long.parseLong(request.getParameter("no")));
		student001.setStudentname(request.getParameter("name"));
		StudentService service = null;
		try {
			service = ServiceFactory.getStudentService();
			service.insertStudent(student001);
			request.getRequestDispatcher("/success.jsp").forward(request,
					response);
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
			request.getRequestDispatcher("/failure.jsp").forward(request,
					response);
		}
	}

}
