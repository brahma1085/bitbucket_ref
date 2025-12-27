package com.eris;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class InterviewTyp extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		int id = Integer.parseInt(req.getParameter("d1"));
		String type = req.getParameter("textfield2");
		String idate = req.getParameter("textfield3");
		String icom = req.getParameter("textfield4");
		try {
			DBConnection db = new DBConnection();
			Connection con = db.getConnection();
			System.out.println("con");
			PreparedStatement ps = con
					.prepareStatement("insert into interviewdetails values(?,?,?,?)");
			System.out.println("ps");
			ps.setInt(1, id);
			ps.setString(2, type);
			ps.setString(3, idate);
			ps.setString(4, icom);
			System.out.println("before update");
			int i = ps.executeUpdate();
			if (i == 1) {
				res.sendRedirect("recruit.htm");
			} else {
				res.sendRedirect("interview.jsp");
			}
			System.out.println("afeter up");
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
