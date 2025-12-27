<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
	</head>
	<body>
		<table>
			<h1>
				Simple Loan Forward Page 
			</h1>
			<br><br>
			<center>
			<html:form action="/simpleForwardAction?pageId=8023">
			<% System.out.println("Hi from Simple Loan Page"); %>
			<display:table name="ModuleCodeObject" id="ModuleCodeObject" class="its" sort="list">
			<display:column title="Module Code">
			<input type="text" align="middle"  value="<core:out value="${ModuleCodeObject.ModuleCode}"></core:out>" size="4" class="dispTabTextField" readonly="readonly"/>
			</display:column>
			</display:table>
				<html:text property="simpLoanName">
				</html:text>
				<html:text property="cid">
				</html:text>
				<html:submit>OK</html:submit>
			</html:form>
			</center>
		</table>
	</body>
</html>