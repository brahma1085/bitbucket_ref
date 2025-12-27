package edu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = { "/LoginServlet", "/Servlet3" }, initParams = { @WebInitParam(name = "UserID", value = "CMSASIA"), @WebInitParam(name = "password", value = "CMSASIA") })
public class H5Servlet extends HttpServlet {
	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;

	private static Connection con;
	private static PreparedStatement ps;

	public void init(ServletConfig config) {
		ServletContext context = config.getServletContext();
		try {
			Class.forName(context.getInitParameter("driver"));
			con = DriverManager.getConnection(context.getInitParameter("url"), config.getInitParameter("UserID"), config.getInitParameter("password"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("text/html");
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		session.setAttribute("userID", session);

		String userID = req.getParameter("uname");
		String password = req.getParameter("pwd");
		try {
			ps = con.prepareStatement("select user_id from tb_cms_user where user_id = ?");
			ps.setString(1, userID);

			PrintWriter out = res.getWriter();

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (userID.equals(rs.getString(1)) && userID.equals(password)) {
					out.println("Login Success");
					RequestDispatcher rd = req.getRequestDispatcher("Servlet1");
					rd.forward(req, res);
				} else {
					res.sendRedirect(req.getContextPath() + "/index.html");
				}
			}
			out.print("u invoked me again");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
