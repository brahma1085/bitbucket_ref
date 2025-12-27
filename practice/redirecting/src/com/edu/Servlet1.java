package com.edu;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet1 extends HttpServlet {

	private static final long serialVersionUID = -7540286244218180435L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String htm="";
		String cp=getServletContext().getContextPath();
		String uid=request.getParameter("uid");
		String bgcolor=request.getParameter("color");
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		htm+="<html><body bgcolor="+bgcolor+"><center><h1><br>hi,"+uid+" welcome to NitMail<br>";
		htm+="<form action=./s2 method=post><br>";
		htm+="<input type=hidden name=uid value="+uid+"/><br><input type=hidden name=color value="+bgcolor+"/><br><input type=submit value=inbox /></form>";
		htm+="<form action=./s3 method=post><br><input type=hidden name=uid value="+uid+"/><br><input type=hidden name=color value="+bgcolor+"/><br><input type=submit value=compose /></form>";
		htm+="<form action=./s4 method=post><br><input type=hidden name=uid value="+uid+"/><br><input type=hidden name=color value="+bgcolor+"/><br><input type=submit value=trash /></form>";
		htm+="</h1></center></body></html>";
		pw.println(htm);
		pw.flush();
		pw.close();
	}
}
