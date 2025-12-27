<%@taglib prefix="html" uri="loginapp"%>
<html>
	<body bgcolor="gropererer">
		<center>
			<h1>
				<u>Login Screen</u>
			</h1>
			<br />
			<h3>
				<html:form action="/login" method="post">
				User Name :
				<html:text property="username"></html:text>
					<br />
					<br />
				Password :
				<html:password property="password"></html:password>
					<br />
					<br />
					<html:submit>submit</html:submit>
				</html:form>
			</h3>
		</center>
	</body>
</html>
