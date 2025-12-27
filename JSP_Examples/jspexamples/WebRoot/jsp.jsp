
<%
	request.setAttribute("a1", 100);
	request.setAttribute("a2", new int[] {});
	request.getRequestDispatcher("./jsp1.jsp").forward(request,
			response);
%>