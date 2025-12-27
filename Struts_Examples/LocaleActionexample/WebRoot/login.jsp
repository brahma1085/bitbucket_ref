<%@taglib prefix="html" uri="htmltags"%>
<%@taglib prefix="bean" uri="beantags"%>
<html>
	<body bgcolor="grewf">
		<center>

			<html:form action="login" method="post">
				<h4>
					<font color="red"><html:errors /> </font>
				</h4>
				<br />
				<h1>
					<bean:message key="username" />
					<html:text property="username"></html:text>
					<br />
					<bean:message key="password" />
					<html:password property="password"></html:password>
					<br />
					<br />
					<html:submit>
						<bean:message key="register.submit" />
					</html:submit>
				</h1>
			</html:form>

		</center>
	</body>
</html>