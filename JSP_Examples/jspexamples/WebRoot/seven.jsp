
<%
	request.setAttribute("r1", 30);
	request.setAttribute("r2", 40);
	request.setAttribute("r3", 50);
%>
<jsp:forward page="./eight.jsp">
	<jsp:param value="10" name="n1" />
	<jsp:param value="20" name="n2" />
</jsp:forward>