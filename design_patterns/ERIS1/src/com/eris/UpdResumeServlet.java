package com.eris;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class UpdResumeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		HttpSession session = req.getSession(true);
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		String s = session.getAttribute("appid").toString();
		try {
			int empid = Integer.parseInt(s);
			String s1 = req.getParameter("skills");

			DBConnection db = new DBConnection();
			Connection con = db.getConnection();
			PreparedStatement ps = con
					.prepareStatement("update resume set APPLICANTID=?, RESUME1=?");
			ps.setInt(1, empid);
			ps.setString(2, s1);
			System.out.println("before update");
			ps.executeUpdate();
			System.out.println("afeter up");

			con.close();
		} catch (Exception e) {
			System.out.println("in exp:");
			e.printStackTrace();
		}
		out.println("Record Saved");
		resp.setHeader("REFRESH", "2;URL=./updskill-resume.html");
	}

}