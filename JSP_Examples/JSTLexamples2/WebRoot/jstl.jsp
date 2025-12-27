<h1>
	<%@page isELIgnored="false"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<c:set var="x" value="10" scope="session"></c:set>
	<c:set var="y" value="20" scope="session"></c:set>
	<c:set var="result" value="${x+y}" scope="request"></c:set>
	<c:out value="${result}"></c:out>
</h1>