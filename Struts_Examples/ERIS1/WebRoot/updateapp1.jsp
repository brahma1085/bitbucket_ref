<html>

<head>
<title>UPDATE APPLICATION DETAILS</title>

</head>

<%@page import="javax.servlet.http.*,java.sql.*,javax.servlet.*,com.eris.DBConnection" %>
<body>
<form action="./updateapp.jsp">
<p align="center"><u><b>UPDATE APPLICATION DETAILS</b></u></p>

<p align="center"> &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; APPLICANT ID
<select   name="applicantid"  style="border-style: solid; border-width: 1">
<%

DBConnection  db= new DBConnection();
ResultSet rs=db.executeQuery("select applicantid from applicantsdetails" );

      	while(rs.next())
	{

%>

<option> <%=rs.getInt(1)%> </option>

<%
	}

%>

<input type="submit" value="Update" name="update" style="border-style: solid; border-width: 1"></p>
</form>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p align="center"> <a href="MARKETING.htm">BACK</a></p>
</body>

</html>