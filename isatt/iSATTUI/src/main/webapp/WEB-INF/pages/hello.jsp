<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>

	<h3>Welcome to iTAP Application</h3>
	<h3>You are an Authorized User for iTAP Application </h3>
	<%-- <h3>Message : ${message}</h3>	 --%>
	<h3>Username : ${username}</h3>	
	
	<a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
	
</body>
</html>