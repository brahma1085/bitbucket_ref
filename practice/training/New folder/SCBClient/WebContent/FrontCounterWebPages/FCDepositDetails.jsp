<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="masterObject.general.ModuleObject" %>
     <%@page import="masterObject.frontCounter.ODCCMasterObject"%>
     <%@ page import="masterObject.general.AccountObject" %>
     <%@page import="masterObject.termDeposit.DepositMasterObject"%>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>

<body>
<%AccountObject master=(AccountObject)request.getAttribute("master");
ODCCMasterObject odccmaster=(ODCCMasterObject)request.getAttribute("odccmaster");
DepositMasterObject object=(DepositMasterObject)request.getAttribute("depositInfo");

CustomerMasterObject cust=(CustomerMasterObject)request.getAttribute("cust");

System.out.println("master is in JSP-------PersonalDetails.jsp----"+cust);%>
<html:form action="/FrontCounter/FCDepositDetails?pageId=3055">
<center><h2>Deposit Details</h2></center>
<core:if test="${cust!=null}">
<table border="1" width="257" height="262" style="border:thin solid navy">
	<tr>
		<td height="37" width="245" colspan="2" align="center"><b>
		<font size="4">Deposit Details</font></b></td>
	</tr>
	<tr>
		<td height="26" width="96">Customer ID: </td>
		<td height="26" width="143"><%=String.valueOf(cust.getCustomerID())%></td>
	</tr>
	<tr>
		<td height="26" width="96">Name: </td>
		<td height="26" width="143"><%=String.valueOf(cust.getName())%></td>
	</tr>
	<tr>
		<td height="34" width="96">Category:<%=cust.getCategory()%></td>
		<td height="34" width="143">Sub-Category:<%=cust.getSubCategory()%></td>
	</tr>
	<tr>
		<td height="37" width="96">&nbsp;</td>
		<td height="37" width="143">SC/ST:<%=cust.getScSt()%></td>
	</tr>
	<tr>
		<td height="136" width="96" rowspan="2" valign="top">Address:
		<br><br>
		<%=cust.getAddressProof()%>
		</td>
		<td height="73" width="143" valign="top">Photo:
		<%//cust.getPhoto() %>
		</td>
	</tr>
	<tr>
		<td height="61" width="143" valign="top">Sign.
		<%=cust.getSign() %>
		</td>
	</tr>
	<tr>
		<td height="49" width="96">DOB:<%=cust.getDOB()%></td>
		<td height="49" width="143">Sex:<%=cust.getSex()%></td>
	</tr>
	<tr>
		<td height="36" width="96">Age:<%="no"%></td>
		<td height="36" width="143">Occupation:<%=cust.getOccupation()%></td>
	</tr>
</table>
<core:if test="<%=object!=null %>">
<table width="257" height="262" style="border:thin solid navy" border="1">
<tr><td><font color="navy"> Deposit Details</font></td></tr>
 <tr>
   		<td align="right"><bean:message key="label.dep_date"></bean:message></td>
   		
   		<td><%=""+object.getDepDate()%></td>
   		
   </tr>
   <tr>
   		<td align="right"><bean:message key="label.mat_date"></bean:message></td>
   		
   		<td> <%=""+object.getMaturityDate()%></td>
   		
   </tr>
   <tr>
   		<td align="right"><bean:message key="label.Period_in_days"></bean:message></td>
   		
   		<td><%=""+object.getDepositDays()%></td>
  		
   </tr>
   <tr>
   		<td align="right"><bean:message key="label.IR"></bean:message></td>
   	
   		<td><%=""+object.getInterestRate()%></td>
		 
   </tr>
   <tr>
   		<td align="right"><bean:message key="label.dep_amt"></bean:message></td>
   		
   		<td><html:text property="dep_amt" value="<%=""+object.getDepositAmt()%>" styleClass="formTextField" readonly="true"></html:text></td>
   		
   </tr>
   <tr>
   		<td align="right"><bean:message key="label.Mat_amt"></bean:message></td>
   		
   		<td><%=""+object.getMaturityAmt()%></td>
  		
   </tr>
   <tr>
   		<td align="right"><bean:message key="label.IntRate"></bean:message></td>
   		
   		<td><%=""+object.getInterestAccured()%></td>
  	
   </tr>
   

</table>
</core:if>
</core:if>
</html:form>
</body>
</html>