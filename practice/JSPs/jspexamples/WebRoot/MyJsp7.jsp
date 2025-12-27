
<%@page import="java.util.Date"%>
<%
	out.println(session.getId());
%>
<br />
<%
	out.println(pageContext.getSession().getMaxInactiveInterval());
%><br />
------------------------------------------------------------------
<br />
<%=session.getId()%><br />
<%=pageContext.getSession().getId()%><br/>
-------------------------------------------------------------------<br/>
${sessioScope.id}<br/>
-------------------------------
${pageContext.session.id}