<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
	<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>    
	<%@taglib prefix="display" uri="http://displaytag.sf.net/el"%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositIntRepObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%!

ModuleObject[] array_module;

DepositIntRepObject[] dep_rep_obj;

%>

<%
dep_rep_obj = (DepositIntRepObject[])request.getAttribute("interestcalculation");
%>
<div  id = "table1" style="display:block; overflow:scroll; width: 750px;height: 300px">

<display:table name="interestcalculation" export="true" id="currentRowObject" class="its" sort="list" pagesize="10">
   			<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   			<display:column media="csv excel" title="URL" property="url" />
    		<display:setProperty name="export.pdf" value="true" /> 
   

<display:column property="accType" ></display:column>

<display:column property="accNo" ></display:column>

<display:column property="name"  title ="Name"></display:column>

<display:column property="depositAmt"  title ="Depositor Amt"></display:column>

<display:column property="interestRate"  title="Int Rate"></display:column>

<display:column property="interestFrq"  title="Int Freq"></display:column>

<display:column property="interestAmt"  title ="Int Amt"></display:column>

<display:column property="interestUpto"  title ="Calculated upto"></display:column>

<display:column property="interestMode"  title ="Payment Mode"></display:column>

<display:column property="state"  title ="State"></display:column>

<display:column property="narration"  title ="Narration"></display:column>


</display:table>


</div>


</body>
</html>