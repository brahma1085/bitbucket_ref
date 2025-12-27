<jsp:directive.page import="com.personal.epf.model.Employee"/>
<jsp:directive.page import="com.personal.epf.utils.Constants"/>
<html>
<head>
<title>Employee Summary</title>
<link type="text/css" rel="stylesheet" href="css/easyEPF.css" />
<style type="text/css">
.normal {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: normal;
	color: #3333cc;
}

.medium {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 15px;
	font-weight: bold;
	color: #3333cc;
	text-decoration: none
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
	<form action="EmployeeDetailsServlet">
		<font color="purple"> <b> Employee ID : </b></font><input type="text" name="employeeID"/>   <input type="submit" value="view details"/>
		<h3>Employee Details</h3>
		<table border="1">
			<tr>
				<td bgcolor="silver" class="medium">empid</td>
				<td bgcolor="silver" class="medium">firstname</td>
				<td bgcolor="silver" class="medium">middlename</td>
				<td bgcolor="silver" class="medium">lastname</td>
				<td bgcolor="silver" class="medium">pwd</td>
				<td bgcolor="silver" class="medium">state</td>
				<td bgcolor="silver" class="medium">dob</td>
				<td bgcolor="silver" class="medium">designation</td>
				<td bgcolor="silver" class="medium">dayphone</td>
				<td bgcolor="silver" class="medium">mobile</td>
				<td bgcolor="silver" class="medium">pfvalue</td>
				<td bgcolor="silver" class="medium">maritalstatus</td>
				<td bgcolor="silver" class="medium">country</td>
				<td bgcolor="silver" class="medium">gender</td>
				<td bgcolor="silver" class="medium">address</td>
				<td bgcolor="silver" class="medium">email</td>
				<td bgcolor="silver" class="medium">city</td>
				<td bgcolor="silver" class="medium">zip</td>
				<td bgcolor="silver" class="medium">salary</td>
			</tr>
			<jsp:scriptlet>
				Employee employee = (Employee) session
						.getAttribute(Constants.EMP_OBJ);
				if (employee != null) {
			</jsp:scriptlet>
			<tr>
				<td class="normal" valign="top"><jsp:expression>employee.getEmpid()</jsp:expression></td>
				<td class="normal" valign="top"><jsp:expression>employee.getFirstname()</jsp:expression></td>
				<td class="normal" valign="top"><jsp:expression>employee.getMiddlename()</jsp:expression></td>
				<td class="normal" valign="top"><jsp:expression>employee.getLastname()</jsp:expression></td>
				<td class="normal" valign="top"><jsp:expression>employee.getPwd()</jsp:expression></td>
				<td class="normal" valign="top"><jsp:expression>employee.getState()</jsp:expression></td>
				<td class="normal" valign="top"><jsp:expression>employee.getDob()</jsp:expression></td>
				<td class="normal" valign="top"><jsp:expression>employee.getDesignation()</jsp:expression></td>
				<td class="normal" valign="top"><jsp:expression>employee.getDayphone()</jsp:expression></td>
				<td class="normal" valign="top"><jsp:expression>employee.getMobile()</jsp:expression></td>
				<td class="normal" valign="top"><jsp:expression>employee.getPfvalue()</jsp:expression></td>
				<td class="normal" valign="top"><jsp:expression>employee.getMaritalstatus()</jsp:expression></td>
				<td class="normal" valign="top"><jsp:expression>employee.getCountry()</jsp:expression></td>
				<td class="normal" valign="top"><jsp:expression>employee.getGender()</jsp:expression></td>
				<td class="normal" valign="top"><jsp:expression>employee.getAddress()</jsp:expression></td>
				<td class="normal" valign="top"><jsp:expression>employee.getEmail()</jsp:expression></td>
				<td class="normal" valign="top"><jsp:expression>employee.getCity()</jsp:expression></td>
				<td class="normal" valign="top"><jsp:expression>employee.getZip()</jsp:expression></td>
				<td class="normal" valign="top"><jsp:expression>employee.getSalary()</jsp:expression></td>
			</tr>
			<jsp:scriptlet>
				}
			</jsp:scriptlet>
		</table>
	</form>
</body>
</html>