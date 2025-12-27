<h2>
	two begin
</h2>
<%
	out.println(request.getAttribute("n1") + "<br>");
	out.println(session.getAttribute("n2") + "<br>");
	out.println(application.getAttribute("n3") + "<br>");
%>
<h3>
	two end
</h3>