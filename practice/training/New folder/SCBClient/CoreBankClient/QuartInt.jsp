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
dep_rep_obj = (DepositIntRepObject[])request.getAttribute("quarterlyinterestcalc");
double double_total_interest_amount=0;
for(int int_no_of_rows=0;int_no_of_rows<dep_rep_obj.length;int_no_of_rows++){
	double_total_interest_amount+= dep_rep_obj[int_no_of_rows].getInterestAmt();
}
%>
<%System.out.println("double_total_interest_amount=====> "+double_total_interest_amount); %>
<div  id = "table1" style="display:block; overflow:scroll; width: 750px;height: 300px">

<display:table name="quarterlyinterestcalc" export="true" id="currentRowObject" class="its" sort="list">
   			

<display:column property="accType" ></display:column>

<display:column property="accNo" ></display:column>

<display:column property="name"  title ="Name"></display:column>

<display:column property="depositAmt"  title ="Depositor Amt"></display:column>

<display:column property="interestRate"  title="Int Rate"></display:column>

<display:column property="interestFrq"  title="Int Freq"></display:column>

<display:column property="interestAmt"  title ="Int Amt"></display:column>

<display:column property="interestUpto" title ="Calculated upto"></display:column>


</display:table>
<table><tr><td >&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp</td><td></td><td></td><td></td>

<td> &nbsp  &nbsp &nbsp  &nbsp &nbsp &nbsp &nbsp&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp<font color="red" >Total-Int Amt<input type="text"  value=<%=""+double_total_interest_amount %> readonly="true" size="7"></font></td>
</tr>
</table>
</div>


</body>
</html>