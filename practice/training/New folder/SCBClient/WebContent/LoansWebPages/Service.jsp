<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
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
<body class="Mainbody" onload="Clear()">

<script type="text/javascript">
function Clear()
{
alert("hi");
document.forms[0].income.value = "";
document.forms[0].expenditure.value = "";
}
function netincome1(){
if(document.forms[0].income.value && document.forms[0].expenditure.value ){
var income_value=document.forms[0].income.value;
var exp_value=document.forms[0].expenditure.value;
var net_income=parseInt(income_value)-parseInt(exp_value);
document.forms[0].netincome.value=net_income;
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
   


function numberlimit1(txt,size)
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
   
function numbersOnly(){
         var cha = event.keyCode
				if (  ( cha  > 47 && cha < 58 ) ) {
				return true ;
            	}
            else{
              	return false ;
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
            function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("You can't enter more than "+((parseInt(size))-1)+" digits!!")
         		txt.value="";
         		return false;
          	}
        };
          
	function only_alpha1()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122) || (cha >= 65 && cha <=90) ||cha==32 || cha==46) 
 		{
   			return true;
       	} else 

       	{
   			return false;
       	}
	};
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
        function only_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122 ) ) 
 		{
   			return true;
       	} else 
       	{
       	alert("please enter True/False!!")
   			return false;
       	}
	}; 
	function trueRfalse(){
	if(document.getElementById("truRfalse").value=='true' || document.getElementById("truRfalse").value=='false')
	{
		return true;
	}
	else{
		alert('Please enter True/False!!');
		document.getElementById("truRfalse").focus();
		document.getElementById("truRfalse").select();
		return false;
		}
		}
		function trueRfalse1(){
	if(document.getElementById("truRfalse1").value=='true' || document.getElementById("truRfalse1").value=='false')
	{
		return true;
	}
	else{
		alert('Please enter True/False!!');
		document.getElementById("truRfalse1").focus();
		document.getElementById("truRfalse1").select();
		return false;
		}
		};
		function trueRfalse2(){
	if(document.getElementById("truRfalse2").value=='true' || document.getElementById("truRfalse2").value=='false')
	{
		
		return true;
	}
	else{
		alert('Please enter True/False!!');
		document.getElementById("truRfalse2").focus();
		document.getElementById("truRfalse2").select();
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

function number_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122)||(cha >=48 && cha <=57)||(cha >= 65 && cha <=90)|| cha==32) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
	};
</script>
<html:form action="/Loans/LoanApplicationDE?pageidentity.pageId=5006">

<%IncomeObject incomeObject= (IncomeObject)request.getSession().getAttribute("Service");%>

   <table>
   <tr>
     <td> <bean:message key="label.employer"></bean:message></td>
    <td>  <%if(incomeObject!=null && incomeObject.getName()!=null) {%> 
      <html:text property="employername" value="<%=""+incomeObject.getName()%>" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="employername" onkeypress="return only_alpha1()"></html:text>
      <%} %> </td>
   </tr>
   <tr>
    <td>  <bean:message key="label.address"></bean:message> </td>
    <td>  <%if(incomeObject!=null){ %>
      <html:text property="address" value="<%=""+incomeObject.getAddress()%>" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="address" onkeypress="return only_for_address()"></html:text>
      <%} %> </td>
   </tr>
   
   <tr>
     <td> <bean:message key="label.Phone"></bean:message> </td>
     <td> <%if(incomeObject!=null){ %>
      <html:text property="phoneno" value="<%=""+incomeObject.getPhNo()%>" readonly="true"></html:text>
   	  <%}else{ %>
   	  <html:text property="phoneno" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'11')"    ></html:text>
   	  <%} %> </td>
   </tr>
   <tr>
     <td> <bean:message key="label.empno"></bean:message> </td>
     <td>  <%if(incomeObject!=null){ %>
      <html:text property="empno" value="<%=""+incomeObject.getEmpNo()%>" readonly="true"></html:text>
  	  <%}else{ %>
  	  <html:text property="empno" onkeypress="return number_alpha()"></html:text>
  	  <%} %>	</td>
   </tr>
   
   <tr>
      <td> <bean:message key="label.designation"></bean:message> </td>
      <td> <%if(incomeObject!=null){ %>
      <html:text property="designation" value="<%=""+incomeObject.getDesignation()%>" readonly="true"></html:text>
   	  <%}else{ %>
   	  <html:text property="designation" onkeypress="return only_alpha1()"></html:text>
   	  <%} %>	</td>
   </tr>
   <tr>
     <td>  <bean:message key="label.Department"></bean:message> </td>
   <td> 	<%if(incomeObject!=null){ %>
      <html:text property="department" value="<%=""+incomeObject.getDept()%>" readonly="true"></html:text>
   	<%}else{ %>
   	  <html:text property="department" onkeypress="return only_alpha1()"></html:text>
   	<%} %> </td>
   </tr>
   <tr>
     <td> <bean:message key="label.present"></bean:message> </td>
   <td>   <%if(incomeObject!=null){ %> 
      <html:text property="confirmation" styleId="truRfalse" readonly="true"></html:text>
   	 <%}else{ %>
   	  <html:text property="confirmation"  styleId="truRfalse" onkeypress="return only_alpha()" onblur="trueRfalse()"></html:text>
   	 <%} %> </td>
   </tr>
   
   <tr>
      <td> <bean:message key="label.transfer"></bean:message> </td>
     <td>  <%if(incomeObject!=null){ %>
      <html:text property="transferable" styleId="truRfalse1" onblur="trueRfalse1()" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="transferable" styleId="truRfalse1" onkeypress="return only_alpha()" onblur="trueRfalse1()"></html:text>
      <%} %> </td>
   </tr>
   
   <tr>
       <td> <bean:message key="label.los"></bean:message> </td>
     <td> <%if(incomeObject!=null){ %>
      <html:text property="serv_length" value="<%=""+incomeObject.getService()%>" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="serv_length" onkeypress=" return numbersOnly()" onkeyup="numberlimit1(this,'3')"></html:text>
      <%} %> </td>
   </tr>
   
   <tr>
     <td>  <bean:message key="label.sal_certificate"></bean:message> </td>
      <td> <%if(incomeObject!=null){ %>
      <html:text property="certicateenclosed" styleId="truRfalse2" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="certicateenclosed" styleId="truRfalse2" onkeypress="return only_alpha()" onblur="trueRfalse2()"></html:text>
      <%} %> </td>
   </tr>
   
   <tr>
      <td> <bean:message key="label.income"></bean:message> </td>
      <td> <%if(incomeObject!=null){ %>
      <html:text property="income" value="<%=""+incomeObject.getIncome()%>" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="income" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text>
      <%} %> </td>
   </tr>
   
   <tr>
      <td> <bean:message key="label.expenditure"></bean:message> </td>
     <td>  <%if(incomeObject!=null){ %> 
      <html:text property="expenditure" value="<%=""+incomeObject.getExpenditure()%>" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="expenditure" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')" onblur="netincome1()"></html:text>
      <%} %> </td>
   </tr>
   
   <tr>
      <td> <bean:message key="label.netincome"></bean:message> </td>
   <td>   <%if(incomeObject!=null){ %>
      <html:text property="netincome" value="<%=""+incomeObject.getNetIncome()%>" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="netincome" readonly="true" ></html:text>
      <%}%> </td>
   </tr>
   
   <tr>
    <!--<td><input type="button" value="SaveService" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=SaveService'"/></td> 
      -->
      <td>
      <%if(incomeObject!=null){%>
      <html:submit property="emp_submit" value="SaveService" styleClass="ButtonBackgroundImage" disabled="true"></html:submit>
      <%}else{%>
      <html:submit property="emp_submit" value="SaveService" styleClass="ButtonBackgroundImage" ></html:submit>
      <%} %>
      </td>
      <td> <html:button property="clear" value="Clear" styleClass="ButtonBackgroundImage" onclick="func_clear()"></html:button> </td>
      </tr>
</table>
</html:form>
</body>
</html>