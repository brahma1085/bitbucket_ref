<%@page import="com.personal.epf.utils.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/easyEPF.css" />
</head>
<body>
	<%
		String value = (String) session.getAttribute(Constants.VALUE);
	%>
	<%
		if (value != null) {
	%>
	<jsp:include page="<%=value.trim()%>"></jsp:include>
	<%
		}
	%>
</body>
</html>