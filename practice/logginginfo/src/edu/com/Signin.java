package edu.com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Signin extends HttpServlet {
	private static final long serialVersionUID = 755938229223329849L;

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		String dc, user, url, pwd;
		String a = null;
		String x = req.getParameter("uid");
		dc = getInitParameter("driverclass");
		url = getInitParameter("dburl");
		user = getInitParameter("dbuser");
		pwd = getInitParameter("dbpwd");
		try {
			Class.forName(dc);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, pwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Statement st = null;
		try {
			st = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			String query="select password from login where userid="+x+"";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				a = rs.getString(1);				
			}
		} catch (SQLException n) {
			n.printStackTrace();
		}
		if (a == x)
			res.getWriter().println("<h3>Login successful.This is your Inbox page</h3>");
		else
			res.getWriter().println("<h3>login failed</h3>");
		try {
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
