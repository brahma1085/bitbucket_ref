<h2>
	one begin
</h2>
<%
	request.setAttribute("n1", 20);
	session.setAttribute("n2", 30);
	application.setAttribute("n3", 40);
	javax.servlet.RequestDispatcher rd = request
			.getRequestDispatcher("two.jsp");
	rd.include(request, response);
%>
<h3>
	one end
</h3>
