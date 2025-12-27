<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Loan And Share Details</title>
</head>
<body>
<html:form action="/FrontCounter/LoanAndShareDetails?pageId=3030">
<table border="1" width="642" height="44">
	<tr>
		<td height="38" width="82">Loan Type</td>
		<td height="38" width="116">Loan A/c Type</td>
		<td height="38" width="98">Sanc Amt</td>
		<td height="38" width="82">Disb Left</td>
		<td height="38" width="85">Prn Bal</td>
		<td height="38" width="62">Share %</td>
		<td height="38" width="71">Amount</td>
	</tr>
</table>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>

<table border="1" width="553" height="178">
	<tr>
		<td height="42" width="244">Maximum Loan Amount for Individual:</td>
		<td height="42" width="293"><html:text property="maxloan"></html:text></td>
	</tr>
	<tr>
		<td height="39" width="244">Total Number of Shares (Existing):</td>
		<td height="39" width="293"><html:text property="noshare"></html:text></td>
	</tr>
	<tr>
		<td height="43" width="244">Total Share values:</td>
		<td height="43" width="293"><html:text property="totalvalue"></html:text></td>
	</tr>
	<tr>
		<td height="41" width="244">&nbsp;</td>
		<td height="41" width="293">&nbsp;</td>
	</tr>
</table>


</html:form>

</body>
</html>