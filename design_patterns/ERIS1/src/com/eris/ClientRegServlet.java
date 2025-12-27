package com.eris;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class ClientRegServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		try {
			int clientid = Integer.parseInt(req.getParameter("clientid"));
			String s1 = req.getParameter("companyname");
			String s2 = req.getParameter("name");
			String s3 = req.getParameter("address");
			String s4 = req.getParameter("city");
			String s5 = req.getParameter("state");
			String s6 = req.getParameter("zip");
			String s7 = req.getParameter("title");
			String s8 = req.getParameter("ext");
			String s9 = req.getParameter("telephone");
			String s10 = req.getParameter("fax");
			String s11 = req.getParameter("country");
			String s12 = req.getParameter("email");
			String s13 = req.getParameter("dept");
			DBConnection db = new DBConnection();
			Connection con = db.getConnection();
			PreparedStatement ps = con
					.prepareStatement("insert into clientdetails values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, clientid);
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
		resp.setHeader("REFRESH", "0;URL=./admin.htm");
	}

}