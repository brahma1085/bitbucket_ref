<%@taglib prefix="c" uri="accapp"%>
<html>
	<body bgcolor="grew">
		<center>
			<h1>
				<u>Your Account Details</u>
			</h1>
			<table width="300" cellspacing="4" cellpadding="2" border="2"
				bgcolor="#80ffff" align="center" height="69">
				<tbody>
					<tr>
						<th>
							Account No
						</th>
						<th>
							Account Name
						</th>
						<th>
							Balance
						</th>
					</tr>
					<tr>
						<td>
							<c:out value="${acc.accno}"></c:out>
						</td>
						<td>
							<c:out value="${acc.name}"></c:out>
						</td>
						<td>
							<c:out value="${acc.balance}"></c:out>
						</td>
					</tr>
				</tbody>
			</table>
		</center>
	</body>
</html>