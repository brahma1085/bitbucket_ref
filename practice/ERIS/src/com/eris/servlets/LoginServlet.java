package com.eris.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.eris.exceptions.ServiceException;
import com.eris.factories.ServiceFactory;
import com.eris.model.Login;
import com.eris.services.LoginService;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		Login login = new Login();
		login.setUsername(request.getParameter("userid"));
		login.setPassword(request.getParameter("password"));
		login.setStatus(request.getParameter("privilize"));
		LoginService loginService = ServiceFactory.getLoginService();
		RequestDispatcher dispatcher = null;
		boolean flag = false;
		try {
			flag = loginService.insertUserDetails(login);
			if (flag) {
				dispatcher = request.getRequestDispatcher("/signupsuccess.jsp");
				dispatcher.forward(request, response);
			} else {
				dispatcher = request.getRequestDispatcher("/signupfailure.jsp");
				dispatcher.forward(request, response);
			}
		} catch (ServiceException e) {
			System.out.println("ServiceException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			dispatcher = request.getRequestDispatcher("/error.jsp");
			dispatcher.forward(request, response);
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Login login = new Login();
		String s = request.getParameter("privilize");
		String s1 = "administrator";
		String s2 = "recruitor";
		String s3 = "marketing";
		login.setUsername(request.getParameter("userid"));
		login.setPassword(request.getParameter("password"));
		login.setStatus(s);
		LoginService loginService = ServiceFactory.getLoginService();
		RequestDispatcher dispatcher = null;
		try {
			boolean flag = loginService.isAuthenticate(login);
			if (flag && StringUtils.equalsIgnoreCase(s, s1)) {
				dispatcher = request
						.getRequestDispatcher("/WEB-INF/administrator/adminindex.jsp");
				dispatcher.forward(request, response);
			} else if (flag && StringUtils.equalsIgnoreCase(s, s2)) {
				dispatcher = request
						.getRequestDispatcher("/WEB-INF/recruitor/recruitorindex.jsp");
				dispatcher.forward(request, response);
			} else if (flag && StringUtils.equalsIgnoreCase(s, s3)) {
				dispatcher = request
						.getRequestDispatcher("/WEB-INF/marketing executive/marketingindex.jsp");
				dispatcher.forward(request, response);
			} else {
				dispatcher = request.getRequestDispatcher("/signupfailure.jsp");
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
