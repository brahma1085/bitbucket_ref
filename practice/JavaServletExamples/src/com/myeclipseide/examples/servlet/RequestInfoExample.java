package com.myeclipseide.examples.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myeclipseide.examples.servlet.util.HTMLFilter;

public class RequestInfoExample extends HttpServlet {

	ResourceBundle rb = ResourceBundle.getBundle("LocalStrings");

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<head>");

		String title = rb.getString("requestinfo.title");
		out.println("<title>" + title + "</title>");
		out.println("</head>");
		out.println("<body bgcolor=\"white\">");

		out.println("<h3>" + title + "</h3>");
		out.println("<table border=0><tr><td>");
		out.println(rb.getString("requestinfo.label.method"));
		out.println("</td><td>");
		out.println(request.getMethod());
		out.println("</td></tr><tr><td>");
		out.println(rb.getString("requestinfo.label.requesturi"));
		out.println("</td><td>");
		out.println(HTMLFilter.filter(request.getRequestURI()));
		out.println("</td></tr><tr><td>");
		out.println(rb.getString("requestinfo.label.protocol"));
		out.println("</td><td>");
		out.println(request.getProtocol());
		out.println("</td></tr><tr><td>");
		out.println(rb.getString("requestinfo.label.pathinfo"));
		out.println("</td><td>");
		out.println(HTMLFilter.filter(request.getPathInfo()));
		out.println("</td></tr><tr><td>");
		out.println(rb.getString("requestinfo.label.remoteaddr"));

		String cipherSuite = (String) request
				.getAttribute("javax.servlet.request.cipher_suite");
		out.println("</td><td>");
		out.println(request.getRemoteAddr());
		out.println("</table>");

		if (cipherSuite != null) {
			out.println("</td></tr><tr><td>");
			out.println("SSLCipherSuite:");
			out.println("</td>");
			out.println("<td>");
			out.println(request
					.getAttribute("javax.servlet.request.cipher_suite"));
			out.println("</td>");
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}

}
