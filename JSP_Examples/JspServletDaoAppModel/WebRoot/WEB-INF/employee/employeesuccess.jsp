<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>success page</title>
	</head>
	<body>
		<center>
			<h2>
				<font color="#00b000"><u>Employee Details</u> </font>

				<br>
				<table width="200" cellspacing="1" cellpadding="1" border="2"
					bgcolor="#80ff80" align="center">
					<tbody>
						<tr>
							<th>
								Employee No
							</th>
							<td>
								<c:out value="${requestScope.employee.employeeNo}"></c:out>
							</td>
						</tr>
						<tr>
							<th>
								Employee Name
							</th>
							<td>
								<c:out value="${requestScope.employee.employeeName}"></c:out>
							</td>
						</tr>
					</tbody>
				</table>
				<br>
			</h2>
		</center>
	</body>
</html>