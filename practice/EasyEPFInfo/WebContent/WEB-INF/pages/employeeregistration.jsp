<%@page import="com.personal.epf.utils.Constants"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/easyEPF.css" />
<style type="text/css">
#customers {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	width: 100%;
	border-collapse: collapse;
}

#customers td,#customers th {
	font-size: 1em;
	border: 0px solid #98bf21;
	padding: 3px 7px 2px 7px;
}

#customers th {
	font-size: 1.1em;
	text-align: left;
	padding-top: 5px;
	padding-bottom: 4px;
	background-color: #A7C942;
	color: #3333cc;
}

#customers tr.alt td {
	color: #3333cc;
	background-color: #EAF2D3;
}

h3 {
	font-family: Verdana, Arial, Helvetica;
	font-size: 18px;
	font-weight: bold;
	text-decoration: none;
	color: #3333cc;
	text-align: center;
}
</style>
</head>

<body>
	<%
		String value = (String) session.getAttribute(Constants.EMP_KEY);
		if (value != null && !value.trim().equals("")) {
	%>
	<font color="blue"> Your Employee ID : <%=value%>
	</font>
	<%
		}
	%>

	<form action="EmployeeRegistrationServlet">
		<h3>REGISTRATION FORM</h3>
		<table id="customers">
			<tr>
				<td colspan="2">
					<div align="center">
						<font face="Constantia" size="4"><b>PERSONAL DETAILS</b> </font>
					</div>
				</td>
				<td colspan="2">
					<div align="center">
						<font face="Constantia" size="4"><b>ADDRESS DETAILS</b> </font>
					</div>
				</td>
			</tr>
			<tr>
				<td><font face="Constantia" size="4">FirstName*</font></td>
				<td><font face="Constantia" size="4"><input type="text"
						name="firstname"> </font></td>
				<td><font face="Constantia" size="4">Address</font></td>
				<td><font face="Constantia" size="4"><textarea
							name="address" wrap="soft"></textarea> </font></td>
			</tr>
			<tr>
				<td><font face="Constantia" size="4">MiddleName</font></td>
				<td><font face="Constantia" size="4"><input type="text"
						name="middlename"> </font></td>
				<td><font face="Constantia" size="4">City</font></td>
				<td><font face="Constantia" size="4"><input type="text"
						name="city"> </font></td>
			</tr>
			<tr>
				<td><font face="Constantia" size="4">LastName</font></td>
				<td><font face="Constantia" size="4"><input type="text"
						name="lastname"> </font></td>
				<td><font face="Constantia" size="4">Zip</font></td>
				<td><font face="Constantia" size="4"><input type="text"
						name="zip"> </font></td>
			</tr>
			<tr>
				<td><font face="Constantia" size="4">Date of Dirth</font></td>
				<td><font face="Constantia" size="4"><input type="text"
						name="dob"> </font></td>
				<td><font face="Constantia" size="4">State</font></td>
				<td><font face="Constantia" size="4"><input type="text"
						name="state"> </font></td>
			</tr>
			<tr>
				<td><font face="Constantia" size="4">Martial Status</font></td>
				<td><font face="Constantia" size="4"><select
						name="maritalstatus">
							<option>SINGLE</option>
							<option>MARRIED</option>
					</select> </font></td>
				<td><font face="Constantia" size="4">Country</font></td>
				<td><font face="Constantia" size="4"><input type="text"
						name="country"> </font></td>
			</tr>
			<tr>
				<td><font face="Constantia" size="4">Gender</font></td>
				<td><font face="Constantia" size="4"><input type="radio"
						name="gender" value="M"> M <input type="radio"
						name="gender" value="F"> F </font></td>
				<td><font face="Constantia" size="4">Day Phone</font></td>
				<td><font face="Constantia" size="4"><input type="text"
						name="dayphone"> </font></td>
			</tr>
			<tr>
				<td><font face="Constantia" size="4">Designation</font></td>
				<td><font face="Constantia" size="4"><input type="text"
						name="designation"> </font></td>
				<td><font face="Constantia" size="4">E-Mail</font></td>
				<td><font face="Constantia" size="4"><input type="text"
						name="email"> </font></td>
			</tr>
			<tr>
				<td><font face="Constantia" size="4">PF Value (%)</font></td>
				<td><font face="Constantia" size="4"><select
						name="pfvalue">
							<option value="8.5">8.5</option>
							<option value="12">12</option>
					</select> </font></td>
				<td><font face="Constantia" size="4">Salary* (monthly)</font></td>
				<td><font face="Constantia" size="4"><input type="text"
						name="salary"> </font></td>
			</tr>
			<tr>
				<td><font face="Constantia" size="4">Password*</font></td>
				<td><font face="Constantia" size="4"><input
						type="password" name="pwd"> </font></td>
				<td><font face="Constantia" size="4">Mobile Phone</font></td>
				<td><font face="Constantia" size="4"><input type="text"
						name="mobile"> </font></td>
			</tr>
			<tr>
				<td colspan="4">
					<div align="center">
						<input type="submit" name="Submit" value="save">
					</div>
				</td>
			</tr>
		</table>
		<p align="center">&nbsp;</p>
	</form>
</body>
</html>