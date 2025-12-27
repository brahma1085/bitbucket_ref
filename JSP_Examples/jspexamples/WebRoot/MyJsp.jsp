<%!void fun(PageContext pageContext) throws java.io.IOException {
		JspWriter out = pageContext.getOut();
		out.println("<h1>hello");
	}%>
<%
	fun(pageContext);
%>