<%@ page contentType="text/html; charset=ISO-8859-1"
	errorPage="error.jsp"%>
<jsp:root version="2.0">
	<jsp:scriptlet>String s1 = request.getParameter("text1");
			String s2 = request.getParameter("text2");
			int a = Integer.parseInt(s1);
			int b = Integer.parseInt(s2);
			int c = a + b;
			out.print("sum="+c);</jsp:scriptlet>
</jsp:root>