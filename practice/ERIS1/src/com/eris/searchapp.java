package com.eris;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class searchapp extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		HttpSession session = req.getSession(true);
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		try {
			String s1 = req.getParameter("skills");
			System.out.println(s1);
			String s2 = req.getParameter("exp");
			System.out.println(s2);
			String s3 = req.getParameter("d1");
			System.out.println(s3);

			DBConnection db = new DBConnection();
			Connection con = db.getConnection();
			ResultSet rs = db
					.executeQuery("select skills from applicantskills where applicantid='"
							+ s3 + "'");
			System.out.println("before update");
			while (rs.next()) {
				out
						.println("<center> <b><i>Skill Search Result </i></b></center> <br><br><br><br>");
				out
						.println("<center> <table  border='2' bordercolor='#C0C0C5' >");
				out.println("<center> <table bgcolor='#C0C0C0' ><tr>");
				out.println("<td>" + rs.getString(1)
						+ "</td></tr></table></center>");
				out.println("</table > <br><br><br>");
			}
			System.out.println("afeter up");

			con.close();
		} catch (Exception e) {
			System.out.println("in exp:");
			e.printStackTrace();
		}

		out.println("<center><a href='./searchapp.jsp'>BACK</a>");
	}

}