<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="jstl11.jsp" var="x" scope="session"></c:url>
<h2>
	<a href="${x}">click here</a>
</h2>
<c:url value="jstl11.jsp" scope="session" var="y">
	<c:param name="c1" value="J2SE"></c:param>
	<c:param name="c2" value="J2EE"></c:param>
</c:url>
<h1>
	<a href="${y}">your courses</a>
</h1>