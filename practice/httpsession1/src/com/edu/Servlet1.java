package com.edu;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Servlet1 extends HttpServlet {

	private static final long serialVersionUID = 5482454330379645779L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		String uid = request.getParameter("uid");
		HttpSession session = request.getSession();
		session.setAttribute("uid", uid);
		String htm = "";
		PrintWriter out = response.getWriter();
		htm += "<html><body><h1><u>hi,"
				+ uid
				+ "Welcome to Nava Computers</u></h1><form action=./s2 method=post><h2><u>select hard disk</u></h2><br><h3><input type=radio name=hd value=320GBHD />320GB<br/><input type=radio name=hd value=512GBHD />512GB<br/><input type=radio name=hd value=1024GBHD />1024GB<br/></h3><h2><u>select RAM</u></h2><br><h3><input type=radio name=ram value=2GBRAM />2GB<br/><input type=radio name=ram value=3GBRAM />3GB<br/><input type=radio name=ram value=4GBRAM />4GB<br/><input type=submit value=next /><br/></h3></form></center></body></html>";
		out.println(htm);
		out.flush();
		out.close();
	}

}
