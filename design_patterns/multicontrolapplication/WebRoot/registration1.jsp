<%@taglib prefix="html" uri="htmltags"%>
<html>
	<body bgcolor="1jdfh0fhg">
		<center>
			<h1>
				<u> Welcome To Registration Page</u>
			</h1>
			<html:form method="POST" action="registration1">
				<h3>
					Name :
					<html:text property="name"></html:text>
					<br />
					Password :
					<html:password property="password"></html:password>
					<br />
					Profession :
					<html:select property="profession">
						<html:option value="engineer">engineer</html:option>
						<html:option value="doctor">doctor</html:option>
						<html:option value="businessman">businessman</html:option>
						<html:option value="student">student</html:option>
						<html:option value="military">military</html:option>
						<html:option value="lecturer">lecturer</html:option>
						<html:option value="other">other</html:option>
					</html:select>
					<br />
					<br />
					<html:submit>register</html:submit>
				</h3>
			</html:form>
		</center>
	</body>
</html>