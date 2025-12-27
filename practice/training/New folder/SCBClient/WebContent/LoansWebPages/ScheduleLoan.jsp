<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
    </style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<% System.out.println("******************Schedule.jsp**************************** "); %>
<%! Object[][] scheduleobj; %>
<% scheduleobj=(Object[][])request.getAttribute("ScheduleData"); %>

</head>
<body>
<html:form action="/Loans/LoanApplicationDE?pageId=5001">


<%if(scheduleobj!=null){
	
	%>



	<table border="1">
	<tr>
		<td>S.N</td><td>Month</td><td>Principal</td><td>Interest</td><td>Total</td><td>O/S Bal</td>
	</tr>
	<%
		for(int i=0;i<scheduleobj.length;i++){
	%>
	<tr>
		<%for(int j=0;j<6;j++){ %>
		<td><font color="red"><%=scheduleobj[i][j] %></font></td>
		<%} %>
	</tr>
	<%}%>
	
	<%}%>
</table>	


</html:form>
</body>
</html>