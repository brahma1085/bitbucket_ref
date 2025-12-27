<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="masterObject.general.ModuleObject" %>
     <%@ page import="masterObject.general.AccountObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@page import="masterObject.frontCounter.ODCCMasterObject"%>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ page import="masterObject.general.IncomeObject" %>
<%@ page import="masterObject.loansOnDeposit.LoanPurposeObject" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employment Details</title>
</head>
<body>
<%!IncomeObject incomeObj[]=null; %>

<%
AccountObject master=(AccountObject)session.getAttribute("master");
ODCCMasterObject odccmaster=(ODCCMasterObject)session.getAttribute("odccmaster");
incomeObj=odccmaster.getIncomeDetails();

%>

<html:form action="/FrontCounter/EmploymentDetails?pageId=3029">

<core:if test="<%=odccmaster!=null %>">
<core:choose>
<core:when test="<%=incomeObj!=null%>">


 <table name="business" style="border:thin solid verdana;">
     	    <thead style="font-family:bold;color:blue"><font color="red">Business</font></thead>
     	      <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Name Of Concern:
     	          </td>
     	          <td>
     	          <html:text property="businessName" style="font-family:bold;color:blue" value="<%=incomeObj[0].getName() %>"></html:text>
     	          </td>
     	       </tr>
     	      <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Address:
     	          </td>
     	          <td>
     	          <html:textarea property="businessAddr" cols="25" rows="5" style="font-family:bold;color:blue" value="<%=incomeObj[0].getAddress() %>"></html:textarea>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Nature Of Business:
     	          </td>
     	          <td>
     	          <html:text property="businessNature" style="font-family:bold;color:blue" value="<%=incomeObj[0].getNature() %>"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Value Of Stock:
     	          </td>
     	          <td>
     	          <html:text property="stockValue" style="font-family:bold;color:blue" value="<%=incomeObj[0].getStockValue() %>"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Type Of Goods:
     	          </td>
     	          <td>
     	          <html:text property="goodsType" style="font-family:bold;color:blue" value="<%=incomeObj[0].getTypeOfGoods() %>"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Condition Of Goods:
     	          </td>
     	          <td>
     	          <html:text property="goodsCondition" style="font-family:bold;color:blue" value="<%=incomeObj[0].getGoodsCondition() %>"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Average Turnover Per Month(Rs):
     	          </td>
     	          <td>
     	          <html:text property="turnover" style="font-family:bold;color:blue" value="<%=incomeObj[0].getTurnOver() %>"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Phone No:
     	          </td>
     	          <td>
     	          <html:text property="businessPhno" style="font-family:bold;color:blue" value="<%=incomeObj[0].getPhNo() %>"></html:text>
     	          </td>
     	       </tr>
     	       <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Monthly Income:
     	          </td>
     	          <td>
     	          <html:text property="busMonthlyIncome" style="font-family:bold;color:blue" value="<%=incomeObj[0].getIncome() %>"></html:text>
     	          </td>
     	        </tr>
     	        <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Monthly Expenditure:
     	          </td>
     	          <td>
     	          <html:text property="busMonthlyExpenditure" style="font-family:bold;color:blue" value="<%=incomeObj[0].getExpenditure() %>"></html:text>
     	          </td>
     	        </tr>
     	        <tr>
     	          <td align="center" style="font-family:bold;color:blue">
     	          Net Monthly Income:
     	          </td>
     	          <td>
     	          <html:text property="busNetMonthlyIncome" style="font-family:bold;color:blue" value="<%=incomeObj[0].getNetIncome()%>"></html:text>
     	          </td>
     	        </tr>
     	       </table>
</core:when>
<core:otherwise>
<table>
<tr>
<td>Employment Details not Available</td>
</tr>
</table>

</core:otherwise>
</core:choose>

</core:if>
</html:form>
</body>
</html>