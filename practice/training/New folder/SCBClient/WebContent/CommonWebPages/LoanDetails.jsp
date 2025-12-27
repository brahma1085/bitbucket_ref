<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="masterObject.termDeposit.DepositMasterObject"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.termDeposit.DepositMasterObject"%>
<%@page import="masterObject.customer.CustomerMasterObject"%>


<%@page import="masterObject.loansOnDeposit.LoanReportObject"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TermDeposit Closure</title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
    

<title>Insert title here</title>
</head>
<body class="Mainbody">
<%!

CustomerMasterObject cmObject;
String panelName; 
ModuleObject[] array_module,module_object;
DepositMasterObject dep_mast;
DepositMasterObject[] dep_reivestment;
Double dep_int_rate,dep_int_amt,dep_closure;
CustomerMasterObject cust_obj;
LoanReportObject loan_obj;
String[] details,pay_mode,combo_mat_cat,auto,int_freq,Combodate;

int i,j;
%>
<%
Double interest_payable_penalty = (Double)request.getAttribute("int_loan");

%>
    <%  
        //cmObject = (CustomerMasterObject) request.getAttribute("personalInfo");
        panelName=(String)request.getAttribute("panelName");
        loan_obj=(LoanReportObject)request.getAttribute("LoanDetails");
        dep_mast=(DepositMasterObject)request.getAttribute("DepositDetail"); 
        System.out.println("Loan Detail object in jsp====>"+loan_obj);
        System.out.println("Loan Detail object in jsp====>"+dep_mast);
       
    %> 
    <html:form action="/Common/LoanDetails">

<table width="279" height="216" class="txtTable" style="border: thin solid black;">
<tr><td></td><td align="left" ><b><%=""+panelName%></b></td></tr>
<tr id="loantable11">
			
			<tr>
			 	<td>
			 		 <font color="red"><bean:message key="label.Acc_type"></bean:message></font>
			  		
			 	 		 	
			 	 <%if(loan_obj!=null){%>
    		    	 <font color="red"><html:text property="loan_ac_type" size="10" readonly="true" value="<%=""+ loan_obj.getTranMode() %>" styleClass="formTextField"></html:text></font>
    		 <%}else{ %>  
    		    
    		     <font color="red"><html:text property="loan_ac_type" size="10" readonly="true" value="0" styleClass="formTextField"></html:text></font>
    		     
    		     <%} %>
    		     
    		     </td>
    		  	 
    		  	
    		 </tr>
    		 <tr> 	
    		  	
    	 		 <td>
    	 		     
    		      <font color="red"><bean:message key="label.loanacc"></bean:message></font>
    		    
    		       		     
    		      <%if(loan_obj!=null && dep_mast!=null){%>
    		    	
    		    	 <font color="red"><html:text property="loan_ac_no" size="10" readonly="true" value="<%=""+dep_mast.getLoanAccno()%>" styleClass="formTextField"></html:text></font>
    		 <%}else{ %>  
    		    
    		     <font color="red"><html:text property="loan_ac_no" size="10" readonly="true" value="0" styleClass="formTextField"></html:text></font>
    		     
    		     <%} %>
    		     
    	
    		     </td>
    		     
    		    </tr>
    		    <tr> 
    		     	 <td>
    	 		     
    		      <font color="red"><bean:message key="label.sancdate"></bean:message></font>
    		    
    		    
    		    <%if(loan_obj!=null){
    		    	
    		    	
    		    	%>
    		    	
    		    	 <font color="red"><html:text property="sanc_date" size="10" readonly="true" value="<%=""+ loan_obj.getSanctionDate() %>" styleClass="formTextField"></html:text></font>
    		 <%}else{ %>  
    		    
    		     <font color="red"><html:text property="sanc_date" size="10" readonly="true" value="0" styleClass="formTextField"></html:text></font>
    		     
    		     <%} %>
    		     
    		     </td>
    		     </tr>
    		     
    		     <tr>
    		     	 <td>
    	 		     
    		      <font color="red"><bean:message key="label.sancamt"></bean:message></font>
    		      
    		      
    		      
    		      <%if(loan_obj!=null){
    		    	
    		    	
    		    	%>
    		    	
    		    	 <font color="red"><html:text property="sanc_amt" size="10" readonly="true" value="<%=""+ loan_obj.getSanctionedAmount() %>" styleClass="formTextField"></html:text></font>
    		 <%}else{ %>  
    		    
    		     <font color="red"><html:text property="sanc_amt" size="10" readonly="true" value="0" styleClass="formTextField"></html:text></font>
    		     
    		     <%} %>
    		     </td>
    		     
    		     </tr>
    		     
    		  <tr>
    		    <td>
    	 		     
    		      <font color="red"><bean:message key="label.loanee_name"></bean:message></font>
    		    
    		        		     
    		     <%if(loan_obj!=null){
    		    	
    		    	
    		    	%>
    		    	
    		    	 <font color="red"><html:text property="loanee_name" size="10" readonly="true" value="<%=""+ loan_obj.getName() %>" styleClass="formTextField"></html:text></font>
    		 <%}else{ %>  
    		    
    		     <font color="red"><html:text property="loanee_name" size="10" readonly="true" value="0" styleClass="formTextField"></html:text></font>
    		     
    		     <%} %>
    		     
    		     
    		     
    		     
    		     </td>
    		     
    		     </tr>
    		     <tr>
    		      <td>
    	 		     
    		      <font color="red"><bean:message key="label.loan_intuptodate"></bean:message></font>
    		    
    		        		     
    		     <%if(loan_obj!=null){
    		    	
    		    	
    		    	%>
    		    	
    		    	 <font color="red"><html:text property="ln_int_upto_date" size="10" readonly="true" value="<%=""+ loan_obj.getIntUptoDate() %>" styleClass="formTextField"></html:text></font>
    		 <%}else{ %>  
    		    
    		     <font color="red"><html:text property="ln_int_upto_date" size="10" readonly="true" value="0" styleClass="formTextField"></html:text></font>
    		     
    		     <%} %>
    		     
    		     </td>
    		     
    		     </tr>
    		     
    		     <tr>
    		      <td>
    	 		     
    		      <font color="red"><bean:message key="label.principal_bal"></bean:message></font>
    		    
    		        		     
    		         		     
    		     <%if(loan_obj!=null){
    		    	
    		    	
    		    	%>
    		    	
    		    	 <font color="red"><html:text property="ln_principal_bal" size="10" readonly="true" value="<%=""+ loan_obj.getLoanBalance() %>" styleClass="formTextField"></html:text></font>
    		 <%}else{ %>  
    		    
    		     <font color="red"><html:text property="ln_principal_bal" size="10" readonly="true" value="0" styleClass="formTextField"></html:text></font>
    		     
    		     <%} %>
    		     
    		     </td>
    		     </tr>
    		     
    		     <tr>
    		      <td>
    	 		     
    		      <font color="red"><bean:message key="label.int_bal"></bean:message></font>
    		    
    		       		     
    		      <%if(loan_obj!=null){
    		    	  
    		    	 
    		    	
    		    	%>
    		    	
    		    	<font color="red"><html:text property="ln_interest_bal" size="10" readonly="true" value="<%=""+loan_obj.getInterestPayable() %>" styleClass="formTextField"></html:text></font>
    		    	    		    	 
    		 <%}else{ %>  
    		    
    		     <font color="red"><html:text property="ln_interest_bal" size="10" readonly="true" value="0" styleClass="formTextField"></html:text></font>
    		     
    		     <%} %>
    		     
    		     
    		     </td>
    		     
    		     </tr>
    		     
    		     <tr>
    		      <td>
    	 		     
    		      <font color="red"><bean:message key="label.tot_bal"></bean:message></font>
    		    
    		    
    		    
    		      <%if(loan_obj!=null){
    		    	
    		    	%>
    		    	
    		    	 <font color="red"><html:text property="tot_bal" size="10" readonly="true" value="<%=""+loan_obj.getLoanBalance() %>" styleClass="formTextField"></html:text></font>
    		 <%}else{ %>  
    		    
    		     <font color="red"><html:text property="tot_bal" size="10" readonly="true" value="0" styleClass="formTextField"></html:text></font>
    		     
    		     <%} %>
    		     
    		    
    		     
    		     </td>

</tr>    		     



</table>
</html:form>
</body>
</html>