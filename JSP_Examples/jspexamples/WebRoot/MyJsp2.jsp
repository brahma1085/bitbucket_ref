<%@page import="edu.Book"%>
<%
	Book b = new Book();
	b.setISOno(Integer.parseInt(request.getParameter("bid")));
	b.setBookName(request.getParameter("bname"));
	session.setAttribute("b", b);
	application.setAttribute("b", b);
%>
<jsp:include page="/MyJsp8.jsp"></jsp:include>