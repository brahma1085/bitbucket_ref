package com.eris;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class UpdAppServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		PrintWriter out = resp.getWriter();
		HttpSession ses = req.getSession(true);
		try {
			int applicantid;
			applicantid = Integer.parseInt(req.getParameter("applicantid"));
			ses.setAttribute("appid", req.getParameter("applicantid"));
			String s1 = req.getParameter("state");
			String s2 = req.getParameter("dateofbirth");
			String s3 = req.getParameter("title");
			String s4 = req.getParameter("firstname");
			String s5 = req.getParameter("lastname");
			String s6 = req.getParameter("middlename");
			String s7 = req.getParameter("appdate");
			String s8 = req.getParameter("dayphone");
			String s9 = req.getParameter("availability");
			String s10 = req.getParameter("extenstion");
			String s11 = req.getParameter("immigration");
			String s12 = req.getParameter("martialstatus");
			String s13 = req.getParameter("eveningphone");
			String s14 = req.getParameter("gender");
			String s15 = req.getParameter("mobilephone");
			String s16 = req.getParameter("address");
			String s17 = req.getParameter("email");
			String s18 = req.getParameter("city");
			String s19 = req.getParameter("zip");
			String s20 = req.getParameter("qualification");
			String s21 = req.getParameter("specialization");
			String s22 = req.getParameter("experience");
			String s23 = req.getParameter("reference");
			String s24 = req.getParameter("presentsalary");
			String s25 = req.getParameter("expectedsalary");
			String s26 = req.getParameter("security");
			String s27 = req.getParameter("comments");
			DBConnection db = new DBConnection();
			Connection con = db.getConnection();
			PreparedStatement ps = con
					.prepareStatement("update  applicantsdetails  set   APPLICANTID=?, STATE=?, DATEOFBIRTH=?, TITLE=?, FIRSTNAME=?, LASTNAME=?, MIDDLEINITIAL=?, APPLICATIONDATE=?, DAYPHONE=?, AVAILABILITY=?, EXTENSION=?, IMMIGRATION=?, MARTIALSTATUS=?, EVENINGPHONE=?, GENDER=?, MOBILEPHONE=?, ADDRESS=?, EMAIL=?, CITY=?, ZIP=?, QULIFICATION=?, SPECIALIZATION=?, EXPERIENCE=?, REFERENCE=?, PRESENTSALARY=?, EXPECTEDSALARY=?, SECURITYNUMBER=?, COMMENTS=? where applicantid="
							+ req.getParameter("applicantid"));
			ps.setInt(1, applicantid);
			ps.setString(2, s1);
			ps.setString(3, s2);
			ps.setString(4, s3);
			ps.setString(5, s4);
			ps.setString(6, s5);
			ps.setString(7, s6);
			ps.setString(8, s7);
			ps.setString(9, s8);
			ps.setString(10, s9);
			ps.setString(11, s10);
			ps.setString(12, s11);
			ps.setString(13, s12);
			ps.setString(14, s13);
			ps.setString(15, s14);
			ps.setString(16, s15);
			ps.setString(17, s16);
			ps.setString(18, s17);
			ps.setString(19, s18);
			ps.setString(20, s19);
			ps.setString(21, s20);
			ps.setString(22, s21);
			ps.setString(23, s22);
			ps.setString(24, s23);
			ps.setString(25, s24);
			ps.setString(26, s25);
			ps.setString(27, s26);
			ps.setString(28, s27);
			System.out.println("before update");
			ps.executeUpdate();
			System.out.println("afeter up");
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println("in exp:");
			e.printStackTrace();
		}
		out.println("Record Updatedff");
		resp.setHeader("REFRESH", "2;URL=./updskill-resume.html");
	}

}
