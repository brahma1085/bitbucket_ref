<%@taglib prefix="html" uri="htmltags"%>
<html>
	<body bgcolor="1jdfh0fhg">
		<center>
			<h1>
				<u> Please Fill The Following To Complete The Registration </u>
			</h1>
			<h3>
				<html:form action="registration2" method="post">
					<html:hidden property="name" write="registration.name" />
					<html:hidden property="password" write="registration.password" />
					<html:hidden property="profession" write="registration.profession" />
					<br />
					Cell No : <html:text property="cell"></html:text>
					<br />Gender : <html:radio property="gender" value="male">male</html:radio>
					<html:radio property="gender" value="female">female</html:radio>
					<br />
					<html:checkbox property="agree"></html:checkbox>I agree the terms and conditions of this registration<br />
					<html:submit>Complete Registration</html:submit>
				</html:form>
			</h3>
		</center>
	</body>
</html>