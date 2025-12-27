<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<html:form action="/Loans/LoanStaus?pageId=5018">
<table border="0">
<tr>
	<td>
		<center><b> Indent </b></center>
	</td>
</tr>
<tr>
	<td>
		Name &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="indName">
	</td>
</tr>
<tr>
	<td>
		DOB &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="infDob">
	</td>
</tr>
<tr>
	<td>Sex &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<select name="indTos">
		<option>Male</option>
		<option>Female</option>
		</select>
	</td>
</tr>

<tr>
	<td>Martial &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<select name="indTom">
		<option>UnMarried</option>
		<option>Maried</option>
		</select>
	</td>
</tr>
<tr>
	<td>Status &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<select name="indTos">
		<option>Live</option>
		<option>Alive</option>
		</select>
	</td>
</tr>
<tr>
	<td>
		<input type="submit" value="Submit">
	</td>
	<td>
		<input type="submit" value="Clear">
	</td>
	<td>
		<input type="submit" value="Update">
	</td>
</tr>

</table>
</html:form>
</body>

</html>