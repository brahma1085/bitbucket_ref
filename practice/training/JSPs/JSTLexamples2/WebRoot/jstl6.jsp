
<%
	String a[] = { "10", "20", "30", "40", "50" };
	session.setAttribute("a", a);
%>
<jsp:forward page="./jstl6.jsp"></jsp:forward>


=======================================================
<%@page isELIgnored="false"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach begin="0" end="10" var="a" items="${sessionScope.a}">
	<h1>
		the values are:${a}
	</h1>
</c:forEach>