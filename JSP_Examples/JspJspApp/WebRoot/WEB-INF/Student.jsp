<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'Student.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body bgcolor="blue">
		<br>
		<center>
			<h1>
				<u><font color="white">Enter Student Details</font> </u>
			</h1>
			<br>
			<br>
			<form action="./j2" method="post">
				<table border="4">
					<tr>
						<th>
							<font color="white">Student No:</font>
						</th>
						<td>
							<input>
						</td>
					</tr>
					<tr>
						<th>
							<font color="white">Student Name:</font>
						</th>
						<td>
							<input>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" value="submit" />
						</td>
				</table>
			</form>
		</center>
	</body>
</html>
