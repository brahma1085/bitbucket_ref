package edu.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentServlet extends HttpServlet {

	private static final long serialVersionUID = 1035422556975699074L;
	Connection connection = null;

	public void destroy() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.err.println("SQLException" + e.getMessage());
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String studentNo = request.getParameter("StudentNo");
		String studentName = request.getParameter("StudentName");
		String query = "INSERT INTO STUDENT VALUES(?,?)";
		java.sql.PreparedStatement pst;
		RequestDispatcher rd = null;
		if (connection != null) {
			try {
				pst = connection.prepareStatement(query);
				pst.setLong(1, new Long(studentNo));
				pst.setString(2, studentName);
				rd = request
						.getRequestDispatcher("/WEB-INF/student/success.jsp");
				rd.forward(request, response);
			} catch (SQLException e) {
				System.err.println("SQLException" + e.getMessage());
				rd = request
						.getRequestDispatcher("/WEB-INF/student/failure.jsp");
				rd.forward(request, response);
			}
		} else {
			rd = request.getRequestDispatcher("/WEB-INF/student/failure.jsp");
			rd.forward(request, response);
		}
	}

	public void init(ServletConfig config) throws ServletException {
		String driver = config.getInitParameter("driver");
		String url = config.getInitParameter("url");
		String user = config.getInitParameter("username");
		String pwd = config.getInitParameter("password");
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException" + e.getMessage());
		} catch (SQLException e) {
			System.err.println("SQLException" + e.getMessage());
		}

	}

}
