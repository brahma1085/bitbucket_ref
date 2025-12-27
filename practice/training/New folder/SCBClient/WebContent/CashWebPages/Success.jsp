<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix = "html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix = "bean" uri="/WEB-INF/struts-bean.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body bgcolor="beige">
<%!String value; %>
<%value = (String)request.getAttribute("value");System.out.print(value);%>
<h2><%=value%></h2>
<%String locker = (String)request.getAttribute("string_locker_type"); %>
<h2><%=locker%></h2> 
<%String share = (String)request.getAttribute("share_cat"); %>
<h2><%=share%></h2> 
</body>
</html>