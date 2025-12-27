<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@page import="com.scb.loans.forms.LoanHistForm"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%!LoanHistForm LoanHistory; %>
<%LoanHistory=(LoanHistForm)request.getAttribute("LoanHistory");%>
<%System.out.println("Loan History=====>"+LoanHistory); %>


<html:form action="/Loans/LoanHist?pageId=5019">
<table>
   <tr>
     <td align="right">
         <bean:message key="label.telephoneno."></bean:message>
         <%if(LoanHistory!=null){ %>
         <html:text property="phone" size="10" readonly="true" value="<%=""+LoanHistory.getPhone() %>"  style="border:transparent;background-color:beige;color:blue"></html:text>
         <%} %>
     </td>
   </tr>
   
   <tr>
     <td align="right">
         <bean:message key="label.mobile"></bean:message>
         <%if(LoanHistory!=null){ %>
         <html:text property="mob" size="10" value="<%=""+LoanHistory.getMob() %>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
         <%} %>
     </td>
   </tr>
   
   
   <tr>
     <td align="right">
         <bean:message key="label.fax"></bean:message>
         <%if(LoanHistory!=null){ %>
         <html:text property="fax" size="10" value="<%=""+LoanHistory.getFax()%>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
         <%} %>
     </td>
   </tr>
   
   <tr>
     <td align="right">
         <bean:message key="label.email"></bean:message>
         <%if(LoanHistory!=null){ %>
         <html:text property="email" size="10" value="<%=""+LoanHistory.getEmail() %>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
         <%} %>
     </td>
   </tr>
   
    <tr>
     <td align="right">
         <bean:message key="label.npadate"></bean:message>
         <%if(LoanHistory!=null){ %>
         <html:text property="npadate" size="10" value="<%=""+LoanHistory.getNpadate() %>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
         <%} %>
     </td>
   </tr>
   
   
   <tr>
     <td align="right">
         <bean:message key="label.npastage"></bean:message>
         <%if(LoanHistory!=null){ %>
         <html:text property="npastage" size="10" value="<%=""+LoanHistory.getNpastage() %>"  style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
         <%} %>
     </td>
   </tr>
   
   
   <tr>
     <td align="right">
         <bean:message key="label.npaodfrom"></bean:message>
         <%if(LoanHistory!=null){ %>
         <html:text property="npaprinciplefrom" size="10" value="<%=""+LoanHistory.getNpaprinciplefrom() %>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
         <%} %>
     </td>
   </tr>
   
   <tr>
     <td align="right">
         <bean:message key="label.npaprinciamt"></bean:message>
         <%if(LoanHistory!=null){ %>
         <html:text property="npaprincipleamt" size="10" value="<%=""+LoanHistory.getNpaprincipleamt() %>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
         <%} %>
     </td>
   </tr>
   
   <tr>
     <td align="right">
         <bean:message key="label.npaprinciodprd"></bean:message>
         <%if(LoanHistory!=null){ %>
         <html:text property="npaodprd" size="10" value="<%=""+LoanHistory.getNpaodprd() %>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
         <%} %>
     </td>
   </tr>
   
   
   <tr>
     <td align="right">
         <bean:message key="label.npaintamt"></bean:message>
         <%if(LoanHistory!=null){ %>
         <html:text property="npaintamt" size="10" value="<%=""+LoanHistory.getNpaintamt() %>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
         <%} %>
     </td>
   </tr>
   
   
   <tr>
     <td align="right">
         <bean:message key="label.lastnotice"></bean:message>
         <%if(LoanHistory!=null){ %>
         <html:text property="lastnotice" size="10" value="<%=""+LoanHistory.getLastnotice() %>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
         <%} %>
     </td>
   </tr>
</table>
</html:form>
</body>
</html>