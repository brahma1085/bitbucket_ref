<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@page import="masterObject.general.IncomeObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />

<body class="Mainbody">
<script type="text/javascript">
function netincome1(){
if(document.forms[0].totamt.value && document.forms[0].tax_payment.value ){
var income_value=document.forms[0].totamt.value;
var exp_value=document.forms[0].tax_payment.value;
var net_income=parseInt(income_value)-parseInt(exp_value);
document.forms[0].netincome.value=net_income;
}
};
function only_for_address()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122)||(cha >= 44 && cha <=58)||(cha >= 65 && cha <=90)||cha==32||cha==35) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
	};
	
function only_numbers_doublevalue() {
	
	var cha=event.keyCode;
	if((cha>=48 && cha<=57) || cha==46)
	{
		return true;
	}
	else
	{
		return false;
	}
}  ;
function func_clear()
{

    var v = document.forms[0].elements;
    for(var i=0;i<=v.length;i++)
    {
       if(v[i].type =="text")
       {
          v[i].value = "";

        }
     }
 
};

function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("You can't enter more than "+((parseInt(size))-1)+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
   
















</script>

<%IncomeObject incomeObject= (IncomeObject)request.getSession().getAttribute("Rent"); %>

<html:form action="/Loans/LoanApplicationDE?pageidentity.pageId=5009">
  <table>
  <tr>
     <td> <bean:message key="label.landaddr"></bean:message></td> 
      <td><%if(incomeObject!=null){ %>
      <html:text property="land_addr" value="<%=""+incomeObject.getAddress()%>" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="land_addr" onkeypress="return only_for_address()"></html:text>
      <%} %></td>
   </tr>
   <tr>
      <td><bean:message key="label.totamt"></bean:message></td> 
    <td>   <%if(incomeObject!=null){ %>
      <html:text property="totamt" value="<%=""+incomeObject.getIncome()%>" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="totamt" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text>
      <%} %></td>
   </tr>
   
   
   <tr>
      <td><bean:message key="label.tax"></bean:message></td> 
     <td> <%if(incomeObject!=null){ %>
      <html:text property="tax_payment" value="<%=""+incomeObject.getExpenditure() %>" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="tax_payment" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')" onblur="netincome1()"></html:text>
      <%} %> </td>
   </tr>
   <tr>
    <td>  <bean:message key="label.netincome"></bean:message> </td>
       <td><%if(incomeObject!=null){ %>
      <html:text property="netincome" value="<%=""+incomeObject.getNetIncome()%>" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="netincome" readonly="true"></html:text>
      <%} %></td>
   </tr>
   
   <tr>
    <!--<td><input type="button" value="SaveRent" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=SaveRent'"/></td>
     -->
     <td>
      <%if(incomeObject!=null){%>
      <html:submit property="emp_submit" value="SaveRent" styleClass="ButtonBackgroundImage" disabled="true"></html:submit>
      <%}else{%>
      <html:submit property="emp_submit" value="SaveRent" styleClass="ButtonBackgroundImage" ></html:submit>
      <%} %>
      </td>
     <td> <html:button property="clear" value="Clear" styleClass="ButtonBackgroundImage" onclick="func_clear()"
     ></html:button></td>
         
</tr>
   </table>
   </html:form>
</body>
</html>