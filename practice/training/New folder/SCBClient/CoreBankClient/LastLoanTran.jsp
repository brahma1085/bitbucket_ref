<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@page import="com.scb.loans.forms.LastLoanTranForm"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
	<%!LastLoanTranForm LoanTran; %>
	<% LoanTran=(LastLoanTranForm)request.getAttribute("LoanTran");%>



<body>
<html:form action="/Loans/LoanTran?pageId=5021">

 <table>
     <tr>
       <td align="right">
        <bean:message key="label.lastprincipleamt"></bean:message>
        <%if(LoanTran!=null){ %>
        <html:text property="lastprincipleamt"  value="<%=""+LoanTran.getLastprincipleamt()%>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
        <%} %>
       </td>
    </tr>
    <tr>   
       <td align="right">
        <bean:message key="label.lastintamt"></bean:message>
        <%if(LoanTran!=null){ %>
        <html:text property="lastintamt" value="<%=""+LoanTran.getLastintamt() %>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
        <%} %>
       </td>
    </tr>
    <tr>    
       <td align="right">
        <bean:message key="label.piamt"></bean:message>
        <%if(LoanTran!=null){ %>
        <html:text property="lastpiamt" value="<%=""+LoanTran.getLastpiamt()%>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
        <%} %>
       </td>
    </tr>
    <tr>    
       <td align="right">
        <bean:message key="label.otheramt"></bean:message>
        <%if(LoanTran!=null){ %>
        <html:text property="lastotheramt" value="<%=""+LoanTran.getLastotheramt()%>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
        <%} %>
       </td> 
     </tr>
 </table>
</html:form>
</body>
</html>