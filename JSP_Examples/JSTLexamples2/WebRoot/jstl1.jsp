<h1>
	<%@page isELIgnored="false"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<c:out value="MNVV Satya Narayana"></c:out>
	<jsp:include page="view.jsp">
		<jsp:param value="satya" name="uid" />
		<jsp:param value="nara" name="pwd" />
	</jsp:include>
</h1>