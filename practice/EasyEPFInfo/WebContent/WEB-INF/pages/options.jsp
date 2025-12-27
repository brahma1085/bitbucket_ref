<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="error.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/easyEPF.css" />
</head>
<body>
	<form action="ForwardServlet">
		<p>The following registration link leads you to registration page
			where you can specify your contact details as well as professional
			details and we are going to keep your details in authenticated manner
			where you can get calculated PF information</p>
		<br> <input type="submit" name="value" value="Register">
		<br>
		<p>The following PF details link is the one where you can get the
			calculated PF information by entering your employee id which was
			generated on your registration with this application</p>
		<br> <input type="submit" name="value" value="PF Details">
		<br>
		<p>The following Summary link provides you the complete employee
			details</p>
		<br> <input type="submit" name="value" value="Summary">
	</form>
</body>
</html>