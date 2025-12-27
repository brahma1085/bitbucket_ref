<h1>
	Hello
</h1>
<%
	int a[] = { 10, 20, 100, 300 };
	for (int i = 0; i < a.length; i++)
		out.println(a[i]);
%>
<jsp:scriptlet>for (int x : a)
				out.println(x);</jsp:scriptlet>