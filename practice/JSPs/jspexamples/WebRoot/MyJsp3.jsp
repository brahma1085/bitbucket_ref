
<%
	request.setAttribute("a", 10);
	session.setAttribute("b", 20);
	application.setAttribute("c", 30);
%>
<jsp:forward page="/view.jsp"></jsp:forward>