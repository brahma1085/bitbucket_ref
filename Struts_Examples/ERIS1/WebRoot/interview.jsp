<%@ page import="javax.servlet.http.*,java.sql.*,javax.servlet.*,com.eris.DBConnection" %>
<html>

<head>
<meta http-equiv="Content-Language" content="en-us">
<meta name="GENERATOR" content="Microsoft FrontPage 5.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>Enterprise Resource Information System</title>
</head>

<body>
<form action="./InterviewType">
<%
DBConnection  db= new DBConnection();
ResultSet rs=db.executeQuery("select applicantid  from applicantsdetails ");
%>



<div style="background-color: #C0C0C0">
  <p align="center"><b>Enterprise Resource Information System</b></div>
<p align="center"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <u><b>INTERVIEW
  DETAILS</b></u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</p>
<table border="1" cellspacing="0" style="border-collapse: collapse; border-style: solid" bordercolor="#111111" width="100%" id="AutoNumber1">
  <tr>
    <td width="25%">
    <p align="center">Applicant ID</td>
    <td width="25%" align="center">Interview Type</td>
    <td width="25%" align="center">Interview Date</td>
    <td width="25%">
    <p align="center">Comments</td>
  </tr>
  <tr>
    <td width="25%">
      <div align="center">&nbsp;
  <select size="1"   name="d1" style="border-style: solid; border-width: 1">
<% while(rs.next())
	{
          	%>
<option>   <%=rs.getInt(1) %>   </option>
<%

                      }%>


</select></td>
      </div>


    </td>
    <td width="25%">
      <div align="center">
        <input type="text" name="textfield2" >
      </div>
    </td>
    <td width="25%">
      <div align="center">
        <input type="text" name="textfield3">
      </div>
    </td>
    <td width="25%">
      <div align="center">
        <input type="text" name="textfield4">
      </div>
    </td>
  </tr>
</table>
<p align="center">&nbsp; </p>
<p align="center">&nbsp;</p>
  <p align="center">
    <input type="submit" value="ADD INTERVIEW" name="B3" style="border-style: solid; border-width: 1">
    &nbsp; </p>
  <p align="center"><a href="recruit.htm">BACK</a></p>
<p align="center">&nbsp;</p>
</form>
</body>

</html>
