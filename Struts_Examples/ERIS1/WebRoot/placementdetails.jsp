<%@ page import="javax.servlet.http.*,java.sql.*,javax.servlet.*,com.eris.DBConnection" %>
<html>
<body>
<form action="addPlacementServlet">
<div style="background-color: #C0C0C0">
  <p align="center"><b>Enterprise Resource Information System</b></div>
  <p align="center"><b> PLACEMENT DETAILS</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
<table width="87%" border="1" align="center" bordercolor="#8080C0" height="303">
  <tr>
    <td height="328">
      <table width="96%" border="0" align="center" bgcolor="#E2E2E2">
        <tr>
          <td colspan="2">
            <div align="center"><b>COMPANY DETAILS&nbsp;</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
          </td>
          <td colspan="2">
            <div align="center"><b>EMPLOYEE DETAILS</b></div>
          </td>
        </tr>
        <tr>
          <td width="40%">Client ID</td>
          <td width="19%">
            <%
DBConnection  db= new DBConnection();
ResultSet rs=db.executeQuery("select empid.nextval  from dual ");
      %>

            <select name="clientid">
			<%    ResultSet rs2=db.executeQuery("select clientid  from clientdetails ");

      	while(rs2.next())
	{
%><option> <%=rs2.getInt(1)%> </option>
<%
	}
  %>
            </select>
          </td>
          <td width="20%"> Eemployee ID</td>
          <td width="21%">
            <input type="text" name="empid">
          </td>
        </tr>
        <tr>
          <td width="40%">Company Name</td>
          <td width="19%">
            <input type="text" name="companyname">
          </td>
          <td width="20%">Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
          <td width="21%">
            <input type="text" name="name">
          </td>
        </tr>
        <tr>
          <td width="40%">Department&nbsp;&nbsp;</td>
          <td width="19%">
            <input type="text" name="department">
          </td>
          <td width="20%">Qualification</td>
          <td width="21%">
            <input type="text" name="qua">
          </td>
        </tr>
        <tr>
          <td width="40%">Position&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
          <td width="19%">
            <input type="text" name="position">
          </td>
          <td width="20%">Specilization</td>
          <td width="21%">
            <input type="text" name="spec">
          </td>
        </tr>
        <tr>
          <td width="40%">Status&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
          <td width="19%">
            <select name="status" size="1">
              <option selected>permenent</option>
              <option>temporary</option>
            </select>
          </td>
          <td width="20%">Experience&nbsp;&nbsp;</td>
          <td width="21%">
            <input type="text" name="exp">
          </td>
        </tr>
        <tr>
          <td width="40%">Appointment Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
          <td width="19%">
            <input type="text" name="adate">
          </td>
          <td width="20%">Salary&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </td>
          <td width="21%">
            <input type="text" name="salary">
          </td>
        </tr>
        <tr>
          <td colspan="4">
            <div align="center">
              <input type="submit" value="SAVE" name="save">
                <input type="submit" name="cancle" value="cancle">
              </div>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
  <p align="center">&nbsp;&nbsp;<a href="admin.htm">BACK</a></p>
<p align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
<p align="left">&nbsp;</p>
<p align="center">&nbsp;</p>
</form>
</body>
</html>