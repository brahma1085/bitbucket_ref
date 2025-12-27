package com.edu;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Servlet3 extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("uid");
		String processor = request.getParameter("pro");
		session.setAttribute("processor", processor);
		String htm = "<html><body><center><h1>hi,"
				+ uid
				+ " Welcome to Nava Computers</h1><br><form action=./s4 method=post><br><h2>you have selected</h2>";

		Enumeration<String> en = session.getAttributeNames();
		while (en.hasMoreElements()) {
			String n = en.nextElement();
			if (!n.equals("uid"))
				htm += "<br>" + n + "," + session.getAttribute(n);
		}
		htm += "<br><input type=submit value=pay /></form></center></body></html>";
		out.println(htm);
		out.flush();
		out.close();
	}
}
