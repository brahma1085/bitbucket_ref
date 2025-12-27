<%@taglib prefix="bean" uri="beantags"%>
<%@taglib prefix="logic" uri="logictags"%>
<html>
	<body bgcolor="dfaerwa">
		<center>
			<h1>
				<u>Account Details</u>
			</h1>
			<table width="200" border="2" bgcolor="#80ffff" align="center">
				<tbody>
					<tr>
						<th>
							Account No
						</th>
						<th>
							Name
						</th>
						<th>
							Balance
						</th>
					</tr>
					<logic:iterate id="account" scope="request" name="accounts">
						<tr>
							<td>
								<bean:write name="account" property="accno" />
							</td>
							<td>
								<bean:write name="account" property="name" />
							</td>
							<td>
								<bean:write name="account" property="balance" />
							</td>
						</tr>
					</logic:iterate>
				</tbody>
			</table>
		</center>
	</body>
</html>