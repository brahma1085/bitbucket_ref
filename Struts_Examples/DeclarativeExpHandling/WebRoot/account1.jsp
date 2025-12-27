<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>

<html>
	<head>
		<title>JSP for AccountForm form</title>
	</head>
	<body>
		<html:form action="/account">
			accno : <html:text property="accno" />
			<html:errors property="accno" />
			<br />
			<html:submit />
		</html:form>
	</body>
</html>

