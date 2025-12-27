<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>MyEclipse JSF Login Example Project</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
		<link rel="stylesheet" type="text/css" href="styles.css">
		-->
	</head>
	<body>
		<f:view>
			<f:loadBundle
				basename="com.myeclipseide.examples.login.bundle.MessageBundle"
				var="bundle" />
			<h:form id="loginForm" rendered="true">
				<h:panelGrid border="1" columns="3">
					<h:outputLabel rendered="true" for="userName">
						<h:outputText value="#{bundle.user_name_label}" />
					</h:outputLabel>
					<h:inputText value="#{UserBean.userName}" rendered="true"
						required="true" id="userName">
						<f:validator
							validatorId="com.myeclipseide.examples.login.validator.UserName" />
					</h:inputText>
					<h:message for="userName" styleClass="errorMessage" />
					<h:outputLabel rendered="true" for="password">
						<h:outputText value="#{bundle.user_password_label}" />
					</h:outputLabel>
					<h:inputSecret value="#{UserBean.password}" rendered="true"
						required="true" redisplay="false" id="password">
						<f:validator
							validatorId="com.myeclipseide.examples.login.validator.Password" />
					</h:inputSecret>
					<h:message for="password" styleClass="errorMessage" />
					<h:panelGroup></h:panelGroup>
					<h:commandButton value="#{bundle.login_button_label}"
						rendered="true" action="#{UserBean.loginUser}" id="submit" />
				</h:panelGrid>
			</h:form>
		</f:view>
	</body>
</html>