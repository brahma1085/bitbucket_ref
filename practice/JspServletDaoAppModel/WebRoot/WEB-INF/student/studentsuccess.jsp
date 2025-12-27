<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>success page</title>
	</head>
	<body>
		<center>
			<h2>
				<font color="#00b000"><u>Student Details</u> </font>

				<br>
				<table width="200" cellspacing="1" cellpadding="1" border="2"
					bgcolor="#80ff80" align="center">
					<tbody>
						<tr>
							<th>
								Student No
							</th>
							<td>
								<c:out value="${requestScope.student.studentNo}"></c:out>
							</td>
						</tr>
						<tr>
							<th>
								Student Name
							</th>
							<td>
								<c:out value="${requestScope.student.studentName}"></c:out>
							</td>
						</tr>
					</tbody>
				</table>
				<br>
			</h2>
		</center>
	</body>
</html>