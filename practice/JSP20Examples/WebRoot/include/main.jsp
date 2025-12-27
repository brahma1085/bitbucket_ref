<%@ include file="header.jspf" %>

<jsp:include flush="true" page="content.jsp">
    <jsp:param name="a" value="1"/>
    <jsp:param name="b" value="2"/>
    <jsp:param name="c" value="3"/>
</jsp:include>

<%@ include file="footer.jspf" %>
