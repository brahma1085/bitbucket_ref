<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%> 
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.loans.PriorityMasterObject"%>
<%@page import="masterObject.loansOnDeposit.LoanPurposeObject;"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
    <h2 class="h2">

      <center>Loan Ammendments</center></h2>
<title>Insert title here</title>

<script type="text/javascript">
function HideShow()
{
     
     
     
     if(document.getElementById("totaltesting").value!=0)
	{
		
		alert(document.getElementById("totaltesting").value);	
		
			
	}
    hide();
}
function hide()
{

   if(document.getElementById("ln_paymode").value=="S")
   {
   document.getElementById("labpayacno").style.display='block';
   document.getElementById("ln_paytypediv").style.display='block';
   document.getElementById("ln_payac_nodiv").style.display='block';
   }
   else if(document.getElementById("ln_paymode").value=="C"){ 	
   document.getElementById("labpayacno").style.display='none';
   document.getElementById("ln_paytypediv").style.display='none';
   document.getElementById("ln_payac_nodiv").style.display='none';
   
   }else if(document.getElementById("ln_paymode").value=="T")
   {
   document.getElementById("labpayacno").style.display='block';
   document.getElementById("ln_paytypediv").style.display='block';
   document.getElementById("ln_payac_nodiv").style.display='block';
   }
   else if(document.getElementById("ln_paymode").value=="P")
   {
   document.getElementById("labpayacno").style.display='none';
   document.getElementById("ln_paytypediv").style.display='none';
   document.getElementById("ln_payac_nodiv").style.display='none';
   }



}


function setsubmit(target)
{
  alert(target);
  document.forms[0].temp.value=target;
  alert(document.forms[0].temp.value);
  document.forms[0].submit();
}



</script>
</head>
<body onload="HideShow()">

<%!
ModuleObject[] array_module;
LoanPurposeObject lnpurpuose[];
String[] combodetail;
PriorityMasterObject[] priomast;
%>

<html:form action="/TermDeposit/LoansSanctionDibursement?pageId=5051">

<html:hidden property="testing" styleId="totaltesting"></html:hidden>
<html:hidden property="forward"/>
<html:hidden property="temp"/>
<table>
<tr>
<td><bean:message key="label.loanaccno"></bean:message></td>
<td><html:select property="ln_ac_type" styleClass="formTextFieldWithoutTransparent" onchange="setsubmit('actype')">
                     <% array_module=(ModuleObject[])request.getAttribute("loanActype");
			    			
                     if(array_module!=null){ 
                     for(int i=0;i<array_module.length;i++)
			         {
			        System.out.println("acctype--------------1--->"+ array_module);
			    	%>	    	
			    	<html:option value="<%=""+array_module[i].getModuleCode()%>"><%=""+array_module[i].getModuleAbbrv()%></html:option>
			    	<%}}%>

</html:select>
</td>
<td><html:text property="ln_acno" onblur="submit()"></html:text></td>
</tr>
<tr>
<td><bean:message key="lable.sharetypenumber"></bean:message>
<td><html:select property="ln_shr_type">
<html:option value="1001001">Share</html:option>
</html:select></td>
<td><html:text property="ln_sharenumber"></html:text></td>
</tr>
<tr>
<td><bean:message key="label.purpose"></bean:message></td>
<td><html:select property="ln_purpose" >
<html:option value="Select">Select</html:option>
<% lnpurpuose=(LoanPurposeObject[])request.getAttribute("laonPurpose");
			    			
                     if(lnpurpuose!=null){ 
                     for(int i=0;i<lnpurpuose.length;i++)
			         {
			        System.out.println("lnpurpuose-acctype--------------1--->"+ lnpurpuose);
			    	%>	    	
			    	<html:option value="<%=""+lnpurpuose[i].getPurposeCode()%>"><%=""+lnpurpuose[i].getPurposeDesc()%></html:option>
			    	<%}}%>

</html:select></td>
</tr>
<tr>
<td><bean:message key="label.details"></bean:message></td>
<td><html:select property="ln_details">
<html:option value="Select">Select</html:option>
<% combodetail=(String[])request.getAttribute("comDetails");
			    			
                     if(combodetail!=null){ 
                     for(int i=0;i<combodetail.length;i++)
			         {
			        System.out.println("combodetail-a--->"+ combodetail);
			    	%>	    	
			    	<html:option value="<%=""+combodetail[i]%>"><%=""+combodetail[i]%></html:option>
			    	<%}}%>
</html:select> </td>
</tr>
<tr>
<td><bean:message key="label.amount"></bean:message></td>
<td><html:text property="ln_amount"></html:text></td>
</tr>
<tr>
<td><bean:message key="lable.periodholiday"></bean:message></td>
<td><html:text property="ln_period" readonly="true"></html:text></td>
<td><html:text property="ln_months" readonly="true"></html:text></td>
</tr>
<tr>
<td><bean:message key="label.instal"></bean:message></td>
<td><html:text property="ln_installments" readonly="true"></html:text></td>
</tr>
<tr>
<td><bean:message key="label.Intrate"></bean:message></td>
<td><html:text property="ln_intrate" readonly="true"></html:text></td>
</tr>
<tr>
<td><bean:message key="label.penalrate"></bean:message></td>
<td><html:text property="ln_penalrate"></html:text></td>
</tr>
<tr>
<td><bean:message key="lable.priority"></bean:message></td>
<td><html:select property="ln_priority">
<html:option value="Select">Select</html:option>
<% priomast=(PriorityMasterObject[])request.getAttribute("LNpriority");%>
<%if(priomast!=null){ %>
<%for(int i=0;i<priomast.length;i++){ %>
 <%     String item = priomast[i].getPrior_desc();
String prior;	 
 int code=priomast[i].getPrior_code();
	 if((item.length()==17) || (item.length()==26))
	     prior=item.substring(0,item.length());
	 else
		 prior=item.substring(0,35);%>		    			
 <html:option value="<%=""+priomast[i].getPrior_code() %>"><%=""+prior%></html:option>
<%} %>
<%} %>


</html:select></td>
<td><html:checkbox property="ln_weaksectionchk"></html:checkbox>Weaker Section</td>
</tr>
<tr>
<td><bean:message key="lable.disAmtleft"></bean:message></td>
<td><html:text property="ln_disbamtleft"></html:text></td>
</tr>
<tr>
<td><bean:message key="label.paymtmode"></bean:message></td>
<td><html:select property="ln_paymode" onchange="hide()">
           <html:option value="S">Select</html:option>
           <html:option value="C">Cash</html:option>
           <html:option value="T">Transfer</html:option>
           <html:option value="P">Pay Order</html:option>
</html:select></td>
</tr>
<tr>
<td><div id="labpayacno" style="display:none"><bean:message key="lable.payacno"></bean:message></div></td>
<td><div id="ln_paytypediv" style="display:none"><html:select property="ln_paytype">
<html:option value="Select">Select</html:option>
<% array_module=(ModuleObject[])request.getAttribute("pay_actype");
			    			
                     if(array_module!=null){ 
                     for(int i=0;i<array_module.length;i++)
			         {
			        System.out.println("pay_actype---->"+ array_module);
			    	%>	    	
			    	<html:option value="<%=""+array_module[i].getModuleCode()%>"><%=""+array_module[i].getModuleAbbrv()%></html:option>
			    	<%}}%>
</html:select></div></td>
<td><div id="ln_payac_nodiv"><html:text property="ln_payac_no"></html:text></div></td>
</tr>
<tr>
<td><bean:message key="label.selfname"></bean:message></td>
<td><html:text property="ln_custname"></html:text></td>
</tr>
<tr>
<td><html:button property="sub" value="Submit" styleClass="ButtonBackgroundImage" onclick="submit()"></html:button></td>
<td><html:button property="cancl" value="cancel" styleClass="ButtonBackgroundImage"></html:button></td>
</tr>
</table>




</html:form>
</body>
</html>