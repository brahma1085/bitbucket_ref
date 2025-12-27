
<%@page import="java.util.Enumeration"%>
<h3>
	${header.host}
	<br />
	${headerValues.connection[0]}
	<br />
	${headerValues.connection[1]}
	<br />
	<%
		out.println("output from Scriptlet");
		out.println(request.getHeader("host"));
		Enumeration e = request.getHeaders("connection");
		while (e.hasMoreElements())
			out.println(e.nextElement());
	%>
</h3>
