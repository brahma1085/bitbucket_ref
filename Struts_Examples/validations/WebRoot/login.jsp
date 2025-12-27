<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>

<html>
	<head>
		<title>JSP for LoginForm form</title>
	</head>
	<body>
		<html:form action="/login" method="post">
			<br />
			username : <html:text property="username" />
			<html:errors property="username" />
			<br />
			password : <html:password property="password" />
			<html:errors property="password" />
			<br />
			<html:submit />
		</html:form>
	</body>
</html>

