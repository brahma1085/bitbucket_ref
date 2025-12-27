
<%
	String s[] = { "123", "456", "789", "abc", "def" };
	session.setAttribute("s", s);
%>
<jsp:forward page="./jstl7.jsp"></jsp:forward>

=========================================================
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach items="${s}" var="x" varStatus="x">
	<h1>
		The Current Object is : ${x}
		<br />
		${x.first}
		<br />
		${x.last}
		<br />
		${x.current}
	</h1>
</c:forEach>