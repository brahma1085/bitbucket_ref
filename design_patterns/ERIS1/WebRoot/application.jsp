<html>

<head>
<title>NEW APPLICATION DETAILS</title>

</head>

<%@page import="javax.servlet.http.*,java.sql.*,javax.servlet.*,com.eris.DBConnection" %>
<body>
<form action="./addAppServlet">
<%! int appid ;%>
<%
DBConnection  db= new DBConnection();
ResultSet rs=db.executeQuery("select appid.nextval  from dual ");

      	if(rs.next())
	{
           	appid=rs.getInt(1);
	}

%>
<p align="center"><u><b>NEW APPLICATION DETAILS</b></u></p>

<p align="center"> &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; APPLICANT ID
  <input  name="applicantid" size="20" value='<%=appid%>'f style="border-style: solid; border-width: 1">

</p>
<table border="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="100%" id="AutoNumber1">
  <tr>
    <td width="16%">Title</td>
    <td width="16%">
      <select size="1" name="title" style="border-style: solid; border-width: 1">
        <option selected>Mr</option>
    <option>Mrs</option>
    </select></td>
    <td width="17%">Address</td>
    <td width="17%">
             <textarea rows="2" name="address" cols="20"></textarea>
       </td>
    <td width="17%">Degree</td>
    <td width="17%">
      <select size="1" style="border-style: solid; border-width: 1" name="qualification">
        <option selected>M.Sc</option>
    <option>M.C.A</option>
    <option>M.Com</option>
    </select></td>
  </tr>
  <tr>
    <td width="16%">First Name</td>
      <td width="16%">
        <input type="text" name="firstname" size="20" style="border-style: solid; border-width: 1">
      </td>
    <td width="17%">City </td>
    <td width="17%">
        <input type="text" name="city" size="20" style="border-style: solid; border-width: 1">
    </td>
    <td width="17%">Specialization</td>
    <td width="17%">
        <select size="1" name="speclization" style="border-style: solid; border-width: 1">
          <option selected>Computers</option>
    <option>IT</option>
    <option>Physics</option>
    </select></td>
  </tr>
  <tr>
    <td width="16%">LastName</td>
    <td width="16%">
        <input type="text" size="20" style="border-style: solid; border-width: 1" name="lastname">
    </td>
    <td width="17%">State</td>
    <td width="17%">
        <input type="text" name="state" size="20" style="border-style: solid; border-width: 1">
    </td>
    <td width="17%">Experience</td>
    <td width="17%">
        <input type="text" name="experience" size="20" style="border-style: solid; border-width: 1">
    </td>
  </tr>
  <tr>
    <td width="16%">MidName</td>
    <td width="16%">
        <input type="text" name="middlename" size="20" style="border-style: solid; border-width: 1">
    </td>
    <td width="17%">Zip </td>
    <td width="17%">
        <input type="text" name="zip" size="20" style="border-style: solid; border-width: 1">
    </td>
    <td width="17%">PresentSalary</td>
    <td width="17%">
        <input type="text" name="presentsalary" size="20" style="border-style: solid; border-width: 1">
    </td>
  </tr>
  <tr>
    <td width="16%">Gender </td>
    <td width="16%">

          <input type="radio" value="V2" checked name="ZENDER">
          Male&nbsp;&nbsp;
          <input type="radio" value="V1" name="ZENDER">
          Female

    </td>
    <td width="17%">Extension</td>
    <td width="17%">
        <input type="text" name="extensition" size="20" style="border-style: solid; border-width: 1">
    </td>
    <td width="17%">Expectedsalary</td>
    <td width="17%">
        <input type="text" name="expectedsalary" size="20" style="border-style: solid; border-width: 1">
    </td>
  </tr>
  <tr>
    <td width="16%">MaritalStatus</td>
    <td width="16%">
        <select size="1" style="border-style: solid; border-width: 1" name="maritalstatus">
          <option selected>Single</option>
        <option>Married</option>
      </select>
    </td>
    <td width="17%">DayPh</td>
    <td width="17%">
        <input type="text" size="20" style="border-style: solid; border-width: 1" name="dayphone">
    </td>
    <td width="17%">Reference</td>
    <td width="17%">
        <input type="text" name="reference" size="20" style="border-style: solid; border-width: 1">
    </td>
  </tr>
  <tr>
    <td width="16%">Date of Birth</td>
      <td width="16%">
        <input type="text" name="dateofbirth" size="20" style="border-style: solid; border-width: 1" >
      </td>
    <td width="17%">EvePhone</td>
    <td width="17%">
        <input type="text" size="20" style="border-style: solid; border-width: 1" name="eveningphone">
    </td>
    <td width="17%">Security</td>
    <td width="17%">
        <input type="text" name="security" size="20" style="border-style: solid; border-width: 1">
    </td>
  </tr>
  <tr>
    <td width="16%">Immigration</td>
    <td width="16%">
        <select size="1" name="immigration" style="border-style: solid; border-width: 1">
          <option selected>H1</option>
    </select></td>
    <td width="17%">Mobile</td>
    <td width="17%">
        <input type="text" name="mobilephone" size="20" style="border-style: solid; border-width: 1">
    </td>
    <td width="17%">Applicant Date</td>
    <td width="17%">
        <input type="text" name="appdate" size="20" style="border-style: solid; border-width: 1">
    </td>
  </tr>
  <tr>
    <td width="16%">Availability</td>
    <td width="16%">
        <input type="text" name="availability" size="20" style="border-style: solid; border-width: 1">
    </td>
    <td width="17%">E-Mail</td>
    <td width="17%">
        <input type="text" name="email" size="20" style="border-style: solid; border-width: 1">
    </td>
    <td width="17%">Comment</td>
    <td width="17%">
        <input type="text" name="comments" size="20" style="border-style: solid; border-width: 1">
    </td>
  </tr>
</table>
<p align="center">&nbsp; </p>
  <p align="center">&nbsp;
    <input type="submit" value="Save" name="B3" style="border-style: solid; border-width: 1">
  </p>
  <p align="center"><a href="MARKETING.htm">BACK</a></p>
</form>
</body>

</html>