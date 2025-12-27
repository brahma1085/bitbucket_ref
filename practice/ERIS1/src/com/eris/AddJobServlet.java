package com.eris;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class AddJobServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String submit;
		HttpSession ses = req.getSession(true);
		AddJobBean add = (AddJobBean) ses.getAttribute("add");
		submit = req.getParameter("submit");
		if (submit.equals("update")) {
			try {
				System.out.println("in addjobservlet:update");
				add.updateJob();
				System.out.println("in addjobservlet:after update");
			} catch (Exception e) {
				System.out.println("in addjobservlet:exceptio");
			}
			resp.sendRedirect("admin.htm");
		} else if (submit.equals("no")) {
			ses.removeAttribute("add");
			resp.sendRedirect("admin.htm");
		}

	}

}