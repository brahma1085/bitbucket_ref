package com.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Servlet1 extends HttpServlet {

	private static final long serialVersionUID = -6996230846289009058L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uid = "";
		String htm = "";
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
			htm += "<html><body><center><h1>";
			htm += "<form action=./s1 method=get>";
			htm += "<br>user name:<input type=text name=uid />";
			htm += "<br>password:<input type=password name=uid />";
			htm += "<br><input type=submit value=signin />";
			htm += "</form></h1></center></body></html>";
		} else {
			uid = request.getParameter("uid");
			if (uid != null)
				session.setAttribute("uid", uid);
			else {
				uid = (String) session.getAttribute("uid");

			}
			htm += "hi," + uid + " welcome to nit e-mail";
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(htm);
		out.flush();
		out.close();
	}

}
