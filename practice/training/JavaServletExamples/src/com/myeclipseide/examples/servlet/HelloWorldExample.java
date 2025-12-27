package com.myeclipseide.examples.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldExample extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ResourceBundle rb = ResourceBundle.getBundle("LocalStrings", request
				.getLocale());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head>");

		String title = rb.getString("helloworld.title");

		out.println("<title>" + title + "</title>");
		out.println("</head>");
		out.println("<body bgcolor=\"white\">");

		out.println("<h1>" + title + "</h1>");
		
		out.println("</body>");
		out.println("</html>");
	}

}
