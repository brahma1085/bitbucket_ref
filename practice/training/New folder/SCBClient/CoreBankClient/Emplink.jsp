<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%! String selfemp; %>
<%selfemp=(String)request.getAttribute("path"); %>
<%System.out.println("employment ======="+selfemp); %>

<html:link action="/LoansWebPages/Self-Employed?pageidentity.pageId=5006">Self-Employed</html:link>

<%if(selfemp!=null){%>
<jsp:include page="<%=selfemp%>"></jsp:include>
<%} %>

</body>
</html>