<%@taglib prefix="html" uri="htmltags"%>
<%@taglib prefix="bean" uri="beantags"%>
<html>
	<body bgcolor="grewf">
		<center>
			<html:form action="login" method="post">
				<h1>
					<u> <bean:message key="welcome.message" /> </u>
				</h1>
				<br />
				<h4>
					<font color="red"><html:errors /> </font>
				</h4>
				<br />
				<h3>
					<html:link href="login.do?language=en&country=us&page=/show.do">english</html:link>
					<html:link href="login.do?language=fr&page=/show.do">french</html:link>
					<html:link href="login.do?language=de&page=/show.do">german</html:link>
					<html:link href="login.do?language=la&page=/show.do">latin</html:link>
					<html:link href="login.do?language=it&page=/show.do">italian</html:link>
				</h3>
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