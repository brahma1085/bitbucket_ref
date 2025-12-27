package edu.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;

public class StudentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DataSource dataSource = null;
		Connection connection1 = null;
		Connection connection2 = null;
		RequestDispatcher dispatcher = null;
		Context context = null;
		try {
			context = new InitialContext();
			dataSource = (DataSource) context
					.lookup("java:comp/env/brahmaJNDI");
			connection1 = dataSource.getConnection();
			connection2 = dataSource.getConnection();
			System.out.println("data source==>" + dataSource);
			System.out.println("connection 1 ==>" + connection1.hashCode());
			System.out.println("connection 2 ==>" + connection2.hashCode());
			dispatcher = request
					.getRequestDispatcher("/WEB-INF/student/success.jsp");
			dispatcher.forward(request, response);
		} catch (NamingException e) {
			System.out.println("NamingException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			dispatcher = request
					.getRequestDispatcher("/WEB-INF/student/failure.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException e) {
			System.out.println("SQLException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			dispatcher = request
					.getRequestDispatcher("/WEB-INF/student/failure.jsp");
			dispatcher.forward(request, response);
		} finally {
			DbUtils.closeQuietly(connection1);
			DbUtils.closeQuietly(connection2);
		}
	}

}
