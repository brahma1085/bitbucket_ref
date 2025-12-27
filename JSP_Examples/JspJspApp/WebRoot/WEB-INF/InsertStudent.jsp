<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.SQLException"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		insert
	</head>
	<body>
		<br>
		<%
			Connection connection = null;
			java.sql.PreparedStatement ps = null;
			RequestDispatcher rd = null;
			try {
				String StudentNo = request.getParameter("Student No");
				String StudentName = request.getParameter("Student Name");
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				connection = DriverManager.getConnection(
						"jdbc:oracle:thin:@localhost:1521:XE", "system",
						"satya");
				System.out.println("Connection established");
				ps = connection
						.prepareStatement("INSERT INTO STUDENT VALUES(?,?)");
				ps.setString(1, StudentNo);
				ps.setString(2, StudentName);

				if (ps == null) {
					rd = request.getRequestDispatcher("./j4");
					rd.forward(request, response);
				} else {
					rd = request.getRequestDispatcher("./j3");
					rd.forward(request, response);
				}
			} catch (SQLException e) {
			} finally {
				if (ps != null)
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				if (connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		%>
	</body>
</html>