<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="masterObject.general.ModuleObject" %>
     <%@ page import="masterObject.general.AccountObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@page import="masterObject.frontCounter.ODCCMasterObject"%>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ page import="masterObject.loansOnDeposit.LoanPurposeObject" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Application Details</title>
</head>
<body>
<%AccountObject master=(AccountObject)session.getAttribute("master");
ODCCMasterObject odccmaster=(ODCCMasterObject)session.getAttribute("odccmaster");
String givedata=(String)session.getAttribute("data");
LoanPurposeObject[] loanpurposeobject=(LoanPurposeObject[])request.getAttribute("purpose");

System.out.println("give data value is =======New====12345====>>");
if(loanpurposeobject!=null){
	System.out.println("loanpurpose is not null");
}
%>
<html:form action="/FrontCounter/OdccApplication?pageId=3028">
<font color="blue" ><center>
<h2 class="h2">Application Details</h2></center></font>
<core:if test="${givedata==null}">
<core:if test="<%=odccmaster!=null %>">
<table border="1" width="349" height="269" class="txtTable">
	
	<tr>
		<td height="25" width="158">Purpose:</td>
		<td>
		<core:if test="<%=loanpurposeobject!=null %>">
		<html:select property="purpose" value="<%=String.valueOf(odccmaster.getPurposeCode()) %>" disabled="true">
		<html:option value="SELECT">SELECT</html:option>
		<%for(int i=0;i<loanpurposeobject.length;i++){ %>
		<html:option value="<%=String.valueOf(loanpurposeobject[i].getPurposeCode())%>"><%=loanpurposeobject[i].getPurposeDesc()%></html:option>
		
		<%}%>
		</html:select>
		</core:if>
		</td>
		
		
	</tr>
	<tr>
		<td height="25" width="158">Appln. Srl No:</td>
		<td height="25" width="175"><html:text property="srlno" value="<%=String.valueOf(odccmaster.getApplicationSrlNo())%>" onblur="setme()"></html:text></td>
		
	</tr>
	<tr>
		<td height="29" width="158">Appln Date:</td>
		<td height="29" width="175"><html:text property="date" value="<%=odccmaster.getApplicationDate()%>"></html:text></td>
	</tr>
	<tr>
		<td height="26" width="158">Amount Required</td>
		<td height="26" width="175"><html:text property="amount" value="<%=String.valueOf(odccmaster.getRequiredAmount())%>"></html:text>
		</td>
	</tr>
	<tr>
		<td height="29" width="158">Payment Mode:</td>
		<td height="29" width="175">
		<html:select property="mode" >
		<html:option value="cash">Cash</html:option>
		
		</html:select>
	</td>
	</tr>
	<tr>
		<td height="25" width="158">Interest Type:</td>
		<td height="25" width="175">
	<html:select property="intType" value="<%=String.valueOf(odccmaster.getInterestRateType())%>" disabled="true">
	<html:option value="1">Fixed</html:option>
		<html:option value="2">Floating</html:option>
	
	</html:select>
</td>
	</tr>
	<tr>
		<td height="29" width="158">Inerest Calc. Type:</td>
		<td height="29" width="175">
		<html:select property="intcalc">
		<html:option value="1">Daily basis</html:option>
	
		
		</html:select>
		</td>
	</tr>
	
	
	<tr>
		<td height="29" width="158"></td>
		<td height="29" width="175"></td>
	</tr>
	<tr>
		<td height="30" width="158">&nbsp;</td>
		<td height="30" width="175">&nbsp;</td>
	</tr>
</table>
</core:if>
<core:if test="${givedata!=null}">
<table border="1" width="349" height="269">
	<tr>
		<td height="25" width="158">Purpose:</td>
		<td height="25" width="175"><html:text property="purpose" ></html:text></td>
	</tr>
	<tr>
		<td height="25" width="158">Appln. Srl No:</td>
		<td height="25" width="175"><html:text property="srlno" ></html:text></td>
	</tr>
	<tr>
		<td height="29" width="158">Appln Date:</td>
		<td height="29" width="175"><html:text property="date" ></html:text></td>
	</tr>
	<tr>
		<td height="26" width="158">Amount Required</td>
		<td height="26" width="175"><html:text property="amount" ></html:text></td>
	</tr>
	<tr>
		<td height="29" width="158">Payment Mode:</td>
		<td height="29" width="175"><html:text property="mode" ></html:text></td>
	</tr>
	<tr>
		<td height="25" width="158">Interest Type:</td>
		<td height="25" width="175"><html:text property="intType" ></html:text></td>
	</tr>
	<tr>
		<td height="29" width="158">Inerest Calc. Type:</td>
		<td height="29" width="175"><html:text property="intcalc" ></html:text></td>
	</tr>
	<tr>
		<td height="29" width="158"><html:submit value="Modify"></html:submit></td>
		<td height="29" width="175"></td>
	</tr>
	<tr>
		<td height="30" width="158">&nbsp;</td>
		<td height="30" width="175">&nbsp;</td>
	</tr>
</table>

</core:if>
</core:if>
</html:form>
</body>
</html>