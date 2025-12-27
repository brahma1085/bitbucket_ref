<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.pygmyDeposit.SimpleMasterObject"%>
<%@page import="masterObject.customer.CustomerMasterObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	name is <% CustomerMasterObject sm = (CustomerMasterObject) request.getAttribute("CUST");
				String name = sm.getFirstName();
				String cat = sm.getMiddleName();
	           %>	
		
		
 this is simple forward page
</body>
</html>