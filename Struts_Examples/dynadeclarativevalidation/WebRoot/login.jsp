<%@taglib prefix="html" uri="htmltags"%>
<html>
	<body bgcolor="grewf">
		<center>
			<h1>
				Hey! login to
				<u>universal</u> community
			</h1>
			<h2>
				<html:form action="/login" method="post">
User name : <html:text property="username">
					</html:text>
					<br />
Password : <html:password property="password"></html:password>
					<br />
					<html:submit>submit</html:submit>
				</html:form>
			</h2>
		</center>
	</body>
</html>