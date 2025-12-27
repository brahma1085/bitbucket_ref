<jsp:directive.page import="com.personal.epf.model.Employee" />
<jsp:directive.page import="com.personal.epf.utils.Constants" />
<html>
<head>
<title>Employee PF Details</title>
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
	<form action="PFCalculationServlet">
		<font color="purple"> <b> Employee ID : </b></font><input type="text"
			name="employeeID" />   <input type="submit" value="view details" />
		<jsp:scriptlet>Double employee_PF = (Double) session
					.getAttribute(Constants.EMP_PF);
			if (employee_PF != null) {</jsp:scriptlet>
		<h3>PF Details</h3>
		<table border="1">
			<tr>
				<td bgcolor="silver" class="medium">Total PF Amount for this
					month :</td>
				<td class="normal" valign="top"><jsp:expression>employee_PF</jsp:expression></td>
			</tr>
			<jsp:scriptlet>}</jsp:scriptlet>
		</table>
	</form>
</body>
</html>