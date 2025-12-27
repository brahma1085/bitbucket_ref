<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>   
<%@page import="masterObject.loans.LoanMasterObject"%>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="com.scb.loans.forms.LoanStatusForm"%>
<%@page import="masterObject.share.ShareMasterObject"%>
<html>
<head>

<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<h2 class="h2"><center>Query On Loan Status</center></h2>
<script type="text/javascript">

function function_clear()
{
	var ele= document.forms[0].elements;
	for(var i=0;i<ele.length;i++)
	{
		if(ele[i].type=="text")
		{
			ele[i].value="";
		}
	}
}

function chkAccountExist()
{
  
  if(document.getElementById("notfound").value!=null)
  {
    if(document.getElementById("notfound")=="accountnotfound")
    {
      alert("Account Not Found");
      document.getElementById("notfound").value="";
      return false;
    }
  
  }
  
  if(document.forms[0].clearFl.value!=""){
      if(document.forms[0].clearFl.value!="Y"){
         var form_ele = document.forms[0].elements;
  
  		for(var i=0;i< form_ele.length;i++ )
		{
			if(form_ele[i].type=="text")
				form_ele[i].value = "";
			if(form_ele[i].type=="hidden")
		        form_ele[i].value = "";
		}
  
      }
  }

}

function only_Numbers()
{
   var cha =   event.keyCode;
		if ( (cha >= 48 && cha <= 57 ) ) 
 		{
	    	return true;
   		} 
   		else 
   		{
		    return false;
   		}
	   }; 

function clearPage()
 { 
        var ele=document.forms[0].elements;
        	for(var i=0;i<ele.length;i++){
        		if(ele[i].type=="text"){
        			ele[i].value="";
        		}
        	}	
 };
</script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
   
</head>
<body onload="return chkAccountExist()">
<%! ModuleObject[] lmobj=null; %>
<%! String arr[]=null;%>
<%! LoanStatusForm stat; %>
<%stat=(LoanStatusForm)request.getAttribute("LoanStatus");%>
<%System.out.println("Loan status form=========>"+stat); %>

<%if(stat!=null){ 
  System.out.println("^^^^^^^^^^^^^^"+stat.getTxt_loanba());		 
} %>
<%
    arr = (String[])request.getAttribute("LoanQueryDet");
    lmobj = (ModuleObject[])request.getAttribute("LoanModuleCode");
    
String msg=(String)request.getAttribute("msg");
%>

<%if(msg!=null){ %>
  <font color="red"><%=msg %></font>
  <br><br>
<%} %>


<table>
<td>
<html:form action="/Loans/RecoveryVerify?pageidentity.pageId=5084">
<table>
<tr>
   
      
      <td><bean:message key="label.combo_loan"></bean:message></td>
      <td><html:select property="acctype">
       <html:option value="SELECT">Select</html:option>
         		  <core:forEach var="acType" varStatus="count" items="${requestScope.LoanModuleCode}">
      			  <html:option value="${acType.moduleCode}"><core:out value="${acType.moduleAbbrv}"></core:out></html:option>		  
      </core:forEach>
      </html:select></td>
   
</tr>
<tr>
  
      <td><bean:message key="label.loanaccno"></bean:message></td>
      <td><html:text property="accno" size="8" onblur="submit()" onkeypress="return only_Numbers()"></html:text></td>
</tr>

<tr>
   
      <td><bean:message key="label.Shno"></bean:message></td>
      <td><html:text property="shno" size="8" styleClass="formTextField"></html:text></td>
   
</tr>

<tr>
   
      <td><bean:message key="label.shareamt"></bean:message></td>
      <td><html:text property="shamt" size="8" styleClass="formTextField" ></html:text></td>
                                                                                
</tr>


<tr>
   
      <td><bean:message key="label.sharetype"></bean:message></td>
      <td><html:text property="shtype" size="8" styleClass="formTextField"></html:text></td>
</tr>

<tr>
   
      <td><bean:message key="label.excessshareamt"></bean:message></td>
      <td><html:text property="excessshamt" size="8" styleClass="formTextField"></html:text></td>
   
</tr>


<tr>
   
      <td><bean:message key="label.loanamt"></bean:message></td>
      <td><html:text property="loanamt" size="8" styleClass="formTextField"></html:text>
   </td>
</tr>

<tr>
      <td><bean:message key="label.disbureddate"></bean:message></td>
      <td><html:text property="disbursed_on" size="10" styleClass="formTextField"></html:text></td>
</tr>

<tr>
      <td><bean:message key="label.Installmentamt"></bean:message></td>
      <td><html:text property="instamt" size="8" styleClass="formTextField"></html:text></td>
</tr>


<tr>
      <td><bean:message key="label.loanperiod"></bean:message></td>
      <td><html:text property="period" size="8" styleClass="formTextField"></html:text></td>
</tr>


<tr>
   
      <td><bean:message key="label.intrate"></bean:message></td>
      <td><html:text property="intrate" size="8" styleClass="formTextField"></html:text></td>
</tr>

<tr>
   <td><bean:message key="label.penalrate"></bean:message></td>
      <td><html:text property="penrate" size="8" styleClass="formTextField"></html:text></td>
</tr>

<tr>
   <td><bean:message key="label.purpose"></bean:message></td>
      <td><html:text property="purpose" styleClass="formTextField"></html:text></td>
</tr>


<tr>
   <td><bean:message key="label.repayment"></bean:message></td>
   <td><html:text property="repay_type" size="8" styleClass="formTextField"></html:text></td>
</tr>


<tr>
   <td><bean:message key="label.intratetype"></bean:message></td>
   <td><html:text property="int_rate_type" size="8" styleClass="formTextField"></html:text></td>
   
</tr>
    <html:hidden property="clearFl"></html:hidden> 
 	<html:hidden property="defaultTab" value="Personal"></html:hidden> 
    <html:hidden property="defaultTab" value="LoanStatus"></html:hidden>
    <html:hidden property="defaultTab" value="LoanHistory"></html:hidden>
    <html:hidden property="defaultTab" value="LoanTran"></html:hidden>
       
    <html:hidden property="accountnotfound" styleId="notfound"></html:hidden>
       				
 
</table>
</html:form>
     
</td>   
<td>
	<% String jspPath=(String)request.getAttribute("flag");
    	  			System.out.println("jspPath=="+jspPath);
         		%>
   				<%if(jspPath!=null){ %>
   				<jsp:include page="<%=jspPath%>"></jsp:include>
				<%}%>
</td>
</table>

</body>
</html>