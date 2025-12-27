<h2>
	three begin
</h2>
<%
	out.println(request.getAttribute("n1") + "<br>");
	out.println(session.getAttribute("n2") + "<br>");
	out.println(application.getAttribute("n3") + "<br>");
	session.invalidate();
%>
<h3>
	three end
</h3>