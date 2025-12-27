<html>

<head>
<title> UPDATE APPLICATION DETAILS</title>

</head>

<%@page import="javax.servlet.http.*,java.sql.*,javax.servlet.*,com.eris.DBConnection" %>

<body>
<%!
String s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20, s21, s22, s23, s24, s25, s26, s27, s28;%>






<form action="./updAppServlet">
<p align="center"><u><b>UPDATE APPLICATION DETAILS</b></u></p>

<p align="center"> &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; APPLICANT ID
<%!String appid ;%>

<%
appid=request.getParameter("applicantid");
DBConnection  db= new DBConnection();
ResultSet rs=db.executeQuery("select * from applicantsdetails where applicantid="+appid);

      	if(rs.next())
	{
 s1=rs.getString(1);
 s2=rs.getString(2);
 s3=rs.getString(3);
 s4=rs.getString(4);
 s5=rs.getString(5);
 s6=rs.getString(6);
 s7=rs.getString(7);
 s8=rs.getString(8);
 s9=rs.getString(9);
 s10=rs.getString(10);
 s11=rs.getString(11);
 s12=rs.getString(12);
 s13=rs.getString(13);
 s14=rs.getString(14);
 s15=rs.getString(15);
 s16=rs.getString(16);
 s17=rs.getString(17);
System.out.println("the value of address"+s17);
 s18=rs.getString(18);
 s19=rs.getString(19);
 s20=rs.getString(20);
 s21=rs.getString(21);
 s22=rs.getString(22);
 s23=rs.getString(23);
 s24=rs.getString(24);
 s25=rs.getString(25);
 s26=rs.getString(26);
 s27=rs.getString(27);
 s28=rs.getString(28);
}
%>
  <input  name="applicantid" size="20" value=<%=s1%> style="border-style: solid; border-width: 1">
</p>
<table border="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="100%" id="AutoNumber1">
  <tr>
    <td width="16%">Title</td>
    <td width="16%">
      <select size="1" name="title"  style="border-style: solid; border-width: 1">
        <option selected>Mr</option>
    <option>Mrs</option>
    </select></td>
    <td width="17%">Address</td>
    <td width="17%">
             <input type="text" name="address" value="<%=s17%>"  style="border-style: solid; border-width: 1" cols="20">
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
        <input type="text" name="firstname" value=<%=s5%> size="20" style="border-style: solid; border-width: 1">
      </td>
    <td width="17%">City </td>
    <td width="17%">
        <input type="text" name="city" size="20"  value=<%=s19%>  style="border-style: solid; border-width: 1">
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
        <input type="text" size="20" value=<%=s6 %>  style="border-style: solid; border-width: 1" name="lastname">
    </td>
    <td width="17%">State</td>
    <td width="17%">
        <input type="text" name="state" size="20" value=<%=s2 %> style="border-style: solid; border-width: 1">
    </td>
    <td width="17%">Experience</td>
    <td width="17%">
        <input type="text" name="experience" size="20" value=<%=s23 %>  style="border-style: solid; border-width: 1">
    </td>
  </tr>
  <tr>
    <td width="16%">MidName</td>
    <td width="16%">
        <input type="text" name="middlename" size="20" value=<%=s7 %>   style="border-style: solid; border-width: 1">
    </td>
    <td width="17%">Zip </td>
    <td width="17%">
        <input type="text" name="zip"  value=<%=s20 %>  size="20" style="border-style: solid; border-width: 1">
    </td>
    <td width="17%">PresentSalary</td>
    <td width="17%">
        <input type="text" name="presentsalary" value="<%=s25 %>"     size="20" style="border-style: solid; border-width: 1">
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
        <input type="text" name="extensition"  value=<%=s11 %>  size="20" style="border-style: solid; border-width: 1">
    </td>
    <td width="17%">Expectedsalary</td>
    <td width="17%">
        <input type="text" name="expectedsalary"  value=<%=s26 %>  size="20" style="border-style: solid; border-width: 1">
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
        <input type="text" size="20" value=<%=s9 %>   style="border-style: solid; border-width: 1" name="dayphone">
    </td>
    <td width="17%">Reference</td>
    <td width="17%">
        <input type="text" name="reference" value=<%=s24 %>   size="20" style="border-style: solid; border-width: 1">
    </td>
  </tr>
  <tr>
    <td width="16%">Date of Birth</td>
      <td width="16%">
        <input type="text" name="dateofbirth" size="20"  value=<%=s3 %>  style="border-style: solid; border-width: 1" >
      </td>
    <td width="17%">EvePhone</td>
    <td width="17%">
        <input type="text" size="20"  value=<%=s16 %>  style="border-style: solid; border-width: 1" name="eveningphone">
    </td>
    <td width="17%">Security</td>
    <td width="17%">
        <input type="text" name="security" value=<%=s27 %>   size="20" style="border-style: solid; border-width: 1">
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
        <input type="text" name="mobilephone"  value=<%=s16 %>  size="20" style="border-style: solid; border-width: 1">
    </td>
    <td width="17%">Applicant Date</td>
    <td width="17%">
        <input type="text" name="appdate"  value=<%=s8 %>  size="20" style="border-style: solid; border-width: 1">
    </td>
  </tr>
  <tr>
    <td width="16%">Availability</td>
    <td width="16%">
        <input type="text" name="availability"  value=<%=s10 %>  size="20" style="border-style: solid; border-width: 1">
    </td>
    <td width="17%">E-Mail</td>
    <td width="17%">
        <input type="text" name="email"  value=<%=s18 %>  size="20" style="border-style: solid; border-width: 1">
    </td>
    <td width="17%">Comment</td>
    <td width="17%">
        <input type="text" name="comments"  value=<%=s28 %>  size="20" style="border-style: solid; border-width: 1">
    </td>
  </tr>
</table>
<p align="center">&nbsp; </p>
  <p align="center">&nbsp;
    <input type="submit" value="Save" name="B3" style="border-style: solid; border-width: 1">
  </p>
  <p align="center"><a href="updateapp1.jsp">BACK</a></p>
</form>
</body>

</html>