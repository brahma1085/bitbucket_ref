<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="masterObject.general.ModuleObject" %>
     <%@page import="masterObject.frontCounter.ODCCMasterObject"%>
     <%@ page import="masterObject.general.AccountObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="java.util.Date" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Receipt Details</title>
</head>

<body>
<%!ODCCMasterObject odccmaster; %>
<%AccountObject master=(AccountObject)request.getAttribute("master");
AccountMasterObject sbcamaster=(AccountMasterObject)request.getAttribute("sbcamaster");
ODCCMasterObject odccmaster=(ODCCMasterObject)request.getAttribute("odccmaster");
Date d= new Date();
DateFormat fmd=DateFormat.getDateInstance(DateFormat.MEDIUM);
String newdate=fmd.format(d);
String givedata=(String)request.getAttribute("data");
String newdata=(String)request.getAttribute("newdata");
request.setAttribute("master",master);

System.out.println("master is in JSP-----------");%>
<html:form action="/FrontCounter/OdccReceiptDetails?pageId=3031">
<center><font color="blue" ><center>
<h2 class="h2">Receipt Details</h2></center></font></center>
<core:if test="<%=givedata==null%>">



<core:if test="${master!=null}">
<core:if test="<%=newdata==null %>">
<table border="1" width="267" height="180">
	<tr>
		<td height="40" width="69">Scroll No:</td>
		<td height="40" width="182"><html:text property="scrollno" value="<%=String.valueOf(master.getScrollno()) %>" onblur="show()"></html:text></td>
	</tr>
	<tr>
		<td height="41" width="69">Date:</td>
		<core:if test="${odccmaster!=null}">
		<td height="41" width="182"><html:text property="date" ></html:text></td>
		</core:if>
		
	</tr>
	<tr>
		<td height="41" width="69">Name:</td>
		<td height="41" width="182"><html:text property="name" size="30" value="<%=master.getAccname() %>"></html:text></td>
	</tr>
	<tr>
		<td height="45" width="69">Amount:</td>
		<td height="45" width="182"><html:text property="amount" value="<%=String.valueOf(master.getAmount()) %>"></html:text></td>
	</tr>
	
</table>
</core:if>
<core:if test="<%=newdata!=null %>">
<table border="1" width="267" height="180">
<core:if test="${odccmaster!=null}">
	<tr>
		<td height="40" width="69">Scroll No:
		<%System.out.println("odccmaster.getRef_No()===>"+odccmaster.getRef_No()); %>
		</td>
		<td height="40" width="182"><html:text property="scrollno" value="<%=String.valueOf(odccmaster.getRef_No()) %>"></html:text></td>
	</tr>
	<tr>
		<td height="41" width="69">Date:</td>
		
		<td height="41" width="182"><html:text property="date" value="<%=odccmaster.getAccOpenDate()%>"></html:text></td>
		
		
	</tr>
	</core:if>
	<tr>
		<td height="41" width="69">Name:</td>
		<td height="41" width="182"><html:text property="name" size="30" value="<%=master.getAccname() %>"></html:text></td>
	</tr>
	<tr>
		<td height="45" width="69">Amount:</td>
		<td height="45" width="182"><html:text property="amount" value="<%=String.valueOf(master.getAmount()) %>"></html:text></td>
	</tr>
	
</table>

</core:if>
</core:if>
</core:if>
<core:if test="<%=givedata!=null %>">
<core:if test="${sbcamaster!=null}">
<%System.out.println("--------------------data is not null"+givedata); %>
<table border="1" width="267" height="180">
	<tr>
		<td height="40" width="69">Scroll No:</td>
		<td height="40" width="182"><html:text property="scrollno"  value="<%=String.valueOf(sbcamaster.getRef_No()) %>"></html:text></td>
	</tr>
	<tr>
		<td height="41" width="69">Date:</td>
		
		<td height="41" width="182"><html:text property="date"  value="<%=String.valueOf(sbcamaster.getAccOpenDate()) %>"></html:text></td>
		
		
		
	</tr>
	<tr>
		<td height="41" width="69">Name:</td>
		<td height="41" width="182"><html:text property="name" size="30"  value="<%=String.valueOf(sbcamaster.getAccName()) %>"></html:text></td>
	</tr>
	<tr>
		<td height="45" width="69">Amount:</td>
		<td height="45" width="182"><html:text property="amount"  value="<%=String.valueOf(sbcamaster.getTransAmount()) %>"></html:text></td>
	</tr>
	
</table>
</core:if>
</core:if>
</html:form>
</body>
</html>