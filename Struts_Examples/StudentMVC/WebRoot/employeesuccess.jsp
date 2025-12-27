<%@ page contentType="text/html; charset=ISO-8859-1"
	import="edu.forms.EmployeeForm"%>
<html>
	<body>
		<center>
			<h1>
				<u>Employee Details</u>
				<%
					EmployeeForm form = (EmployeeForm) request
							.getAttribute("EmployeeForm");
				%><br />
				Employee No :
				<%
					out.println(form.getEmployeeNo());
				%><br />
				Employee Name :
				<%
					out.println(form.getEmployeeName());
				%>
			</h1>
		</center>
	</body>
</html>