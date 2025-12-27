package edu.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.exceptions.ServiceException;
import edu.factory.ServiceFactory;
import edu.models.Details;
import edu.services.AbstractService;

public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean flag = false;
		RequestDispatcher dispatcher = null;
		response.setContentType("text/html");
		Details details = new Details();
		String username = request.getParameter("userid");
		String password = request.getParameter("password");
		details.setUserid(Integer.parseInt(username));
		details.setPwd(password);
		AbstractService service = ServiceFactory.getAbstractService();
		try {
			flag = service.pushDetails(details);
			if (flag) {
				dispatcher = request
						.getRequestDispatcher("/WEB-INF/student/success.jsp");
				dispatcher.forward(request, response);
			} else {
				dispatcher = request
						.getRequestDispatcher("/WEB-INF/student/failure.jsp");
				dispatcher.forward(request, response);
			}
		} catch (ServiceException e) {
			System.out.println("ServiceException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			dispatcher = request
					.getRequestDispatcher("/WEB-INF/student/error.jsp");
			dispatcher.forward(request, response);
		}
	}
}
