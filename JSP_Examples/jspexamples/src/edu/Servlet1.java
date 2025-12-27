package edu;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet1 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	Cookie cookie1=new Cookie("c1", "10");
	Cookie cookie2=new Cookie("c2", "20");
	response.addCookie(cookie1);
	response.addCookie(cookie2);
	response.getWriter().println("<a href=./view1.jsp>click here to see the cookies");
	}
}