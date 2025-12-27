package com.eris;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class UpdSkillControllServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		String status = req.getParameter("radiobutton");
		if (status.equals("resume")) {
			resp.sendRedirect("updresume.jsp");
		} else if (status.equals("skill")) {
			resp.sendRedirect("updskill.jsp");
		}

	}
}