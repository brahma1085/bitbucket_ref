package com.edu;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAddition1 extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		getInitParameter("i1");
		getInitParameter("i2");
		String a=req.getParameter(null);
		String b=req.getParameter(null);
		int c=Integer.parseInt(a)+Integer.parseInt(b);
		PrintWriter pw = null;
		try {
			pw = res.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.println("<h1>result is:"+c+"</h1>");
		pw.flush();
		pw.close();
	}
}
