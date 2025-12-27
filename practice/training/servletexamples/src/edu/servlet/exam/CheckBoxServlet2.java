package edu.servlet.exam;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.GenericServlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CheckBoxServlet2 extends GenericServlet {
	public void service(ServletRequest req, ServletResponse res)
			throws IOException {
		Enumeration e = req.getParameterNames();
		ArrayList<Object> a = new ArrayList<Object>();
		while (e.hasMoreElements()) {
			a.add(e);
		}
		String htm = "<html>";
		for (Object x : a) {
			htm += "<h1><u>" + x + "</u></h1>";
			String s[] = req.getParameterValues(x.toString());
			for (String y : s) {
				htm += "<h3>" + y + "</h3>";
			}
		}
		PrintWriter pw = res.getWriter();
		pw.println(htm);
		pw.flush();
	}
}
