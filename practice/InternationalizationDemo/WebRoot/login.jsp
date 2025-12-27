<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<html:html>
<html:form action="login">
	<center>
		<h1>
			<u> <bean:message key="welcome.message" /> </u>
		</h1>
		<br />
		<h3>
			<bean:message key="username" />
			<html:text property="username"></html:text>
			<html:errors property="username" />
			<br />
			<bean:message key="password" />
			<html:password property="password"></html:password>
			<html:errors property="password" />
			<br />
			<html:submit>
				<bean:message key="register.submit" />
			</html:submit>
		</h3>
	</center>
</html:form>
</html:html>