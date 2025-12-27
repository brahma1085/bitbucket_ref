<%@page import="com.personal.epf.utils.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/easyEPF.css" />
<style type="text/css">
#customers {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	width: 100%;
	border-collapse: collapse;
}

#customers td,#customers thead {
	font-size: 1em;
	border: 1px solid #98bf21;
	padding: 3px 7px 2px 7px;
}

#customers thead {
	font-size: 1.1em;
	text-align: center;
	padding-top: 5px;
	padding-bottom: 4px;
	background-color: #d8da3d;
	color: #d8da3d;
}

#customers tr.alt td {
	color: purple;
	background-color: #d8da3d
}

body {
	background-image: url('images/htmlbackground.jpg');
	background-repeat: repeat;
}
</style>
</head>
<body>
	<table id="customers">
		<thead><jsp:include page="/WEB-INF/pages/header.jsp"></jsp:include></thead>
		<tbody>

			<%
				String msg = (String) request.getSession().getAttribute(
						Constants.MESSAGE);
				if (msg != null && !msg.trim().equals("")) {
			%>
			<tr align="justify">
				<%=msg%>
			</tr>
			<%
				}
			%>

			<tr>
				<td><jsp:include page="/WEB-INF/pages/options.jsp"></jsp:include></td>
				<td><jsp:include page="/WEB-INF/pages/content.jsp"></jsp:include></td>
			</tr>
		</tbody>
	</table>
</body>
</html>