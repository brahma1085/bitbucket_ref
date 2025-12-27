package com.eris;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class EmpRegServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		try {
			int empid = Integer.parseInt(req.getParameter("employeeid"));
			String s1 = req.getParameter("state");
			String s2 = req.getParameter("dateofbirth");
			String s3 = req.getParameter("password");
			String s4 = req.getParameter("firstname");
			String s5 = req.getParameter("lastname");
			String s6 = req.getParameter("middlename");
			String s7 = req.getParameter("designation");
			String s8 = req.getParameter("dayphone");
			String s9 = req.getParameter("extension");
			String s10 = req.getParameter("role");
			String name = null;
			if (s10.equals("RECRUTIER")) {
				name = "RE" + s4;
			} else {
				if (s10.equals("MARKETING EXECUTIVE"))
					name = "ME" + s4;
			}

			String s11 = req.getParameter("martialstatus");
			String s12 = req.getParameter("countryname");
			String s13 = req.getParameter("gender");
			String s14 = req.getParameter("mobilephone ");
			String s15 = req.getParameter("address");
			String s16 = req.getParameter("email");
			String s17 = req.getParameter("city");
			String s18 = req.getParameter("zip");
			String s19 = req.getParameter("clientid");
			System.out.println(s18);
			DBConnection db = new DBConnection();
			Connection con = db.getConnection();
			PreparedStatement ps = con
					.prepareStatement("insert into employeedetails values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, empid);
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
			System.out.println("before update");
			ps.executeUpdate();
			System.out.println("afeter up");
			PreparedStatement ps2 = con
					.prepareStatement("insert into login values(?,?)");
			ps2.setString(1, name);
			ps2.setString(2, s3);
			ps2.executeUpdate();
			ps.close();
			ps2.close();
			con.close();
		} catch (Exception e) {
			System.out.println("in exp:");
			e.printStackTrace();
		}
		out.println("Record Saved");
		resp.setHeader("REFRESH", "2;URL=./admin.htm");
	}

}