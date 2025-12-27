package com.edu;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Servlet2 extends HttpServlet {

	private static final long serialVersionUID = -7965350145486757266L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		HttpSession session=request.getSession();
		String uid=(String)session.getAttribute("uid");
		String hd=request.getParameter("hd");
		String ram=request.getParameter("ram");
		PrintWriter out = response.getWriter();
		session.setAttribute("hd", hd);
		session.setAttribute("ram", ram);
		String htm="";
		htm+="<html><body><center><h1><u>hi,"+uid+" Welcome to Nava Computers</u></h1><br><form action=./s3 method=post><br><h2><u>select processor</u></h2><h3><input type=radio name=pro value=core2duo />core2duo<br><input type=radio name=pro value=i3 />i3<br><input type=radio name=pro value=i5 />i5<br><input type=radio name=pro value=i7 />i7<br><input type=submit value=add to cart /></h3></form></center></body></html>";		
		out.println(htm);
		out.flush();
		out.close();
	}
}
