package com.eris;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class AddPlacementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		try {
			int clientid = Integer.parseInt(req.getParameter("clientid"));
			String s1 = req.getParameter("companyname");
			String s2 = req.getParameter("department");
			String s3 = req.getParameter("position");
			String s4 = req.getParameter("status");
			String s5 = req.getParameter("adate");
			int s6 = Integer.parseInt(req.getParameter("empid"));
			String s7 = req.getParameter("name");
			String s8 = req.getParameter("qua");
			String s9 = req.getParameter("spec");
			String s10 = req.getParameter("exp");
			String s11 = req.getParameter("salary");

			DBConnection db = new DBConnection();
			Connection con = db.getConnection();
			PreparedStatement ps = con
					.prepareStatement("insert into placementdetails values(?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, clientid);
			ps.setString(2, s1);
			ps.setString(3, s2);
			ps.setString(4, s3);
			ps.setString(5, s4);
			ps.setString(6, s5);
			ps.setInt(7, s6);
			ps.setString(8, s7);
			ps.setString(9, s8);
			ps.setString(10, s9);
			ps.setString(11, s10);
			ps.setString(12, s11);
			System.out.println("before update");
			ps.executeUpdate();
			System.out.println("afeter up");
			ps.close();
			con.close();

		} catch (Exception e) {
			System.out.println("in exp:");
			e.printStackTrace();
		}
		out.println("Client Record Saved");
		resp.setHeader("REFRESH", "1;URL=./admin.htm");
	}

}