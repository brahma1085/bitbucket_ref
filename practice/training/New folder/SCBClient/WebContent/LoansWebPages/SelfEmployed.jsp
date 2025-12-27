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

<body class="Mainbody">
<script type="text/javascript">
function netincome(){
if(document.forms[0].income.value && document.forms[0].expenditure.value ){
var income_value=document.forms[0].income.value;
var exp_value=document.forms[0].expenditure.value;
var net_income=parseInt(income_value)-parseInt(exp_value);
document.forms[0].netIncome.value=net_income;
}
}
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
   function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size) )
         	{
         		return true;
         	}
         	else
         	{	
         		alert("You can't enter more than "+((parseInt(size))-1)+" digits!!")
         		txt.value="";
         		return false;
          	}
         }



function numberlimit2(txt,size)
         {	
         	var val=txt.value;
         	if(val.length==10 )
         	{
         		return true;
         	}
         	else
         	{
         	alert("Enter valid Phone Number")
         		txt.value="";
         		return false;	
          	}
         }
         function numbersOnly(eve){
         var cha = event.keyCode
				if (  ( cha  > 47 && cha < 58) || cha==46  ) {
				return true ;
                
            	}
            else{
              //	alert("Please enter the numbers only ");
              	return false ;
            }
        };
        function only_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122) || (cha >= 65 && cha <=90) || cha==32) 
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
        
        function numbersOnly1(eve){
         var cha = event.keyCode
				if (  ( cha  > 47 && cha < 58  ) ) {
				return true ;
                
            	}
            else{
              //	alert("Please enter the numbers only ");
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
</script>
<html:form action="/Loans/LoanApplicationDE?pageidentity.pageId=5005">

<%IncomeObject inObj= (IncomeObject)request.getSession().getAttribute("Self");%>

<table>
   <tr>
   <td><bean:message key="label.empmtnature"></bean:message></td>
   <td> 
      <%if(inObj!=null && inObj.getTypeOfIncome()!=null){%>
      <html:text property="empNature" value="<%=inObj.getTypeOfIncome()%>" readonly="true"></html:text>
      <%}else{ 
      System.out.println("This is null object");%>
      <html:text property="empNature" onkeypress="return only_alpha()"></html:text>
      <%} %>
  </td>	
   </tr>
   <tr>
   <td><bean:message key="label.address"></bean:message></td>
   <td>
   <%if(inObj!=null && inObj.getAddress()!=null){%>
       <html:text property="address" value="<%=""+inObj.getAddress()%>" readonly="true"></html:text>
    <%}else{ %>
      <html:text property="address" onkeypress="return only_for_address()"></html:text>
     <%} %></td>
   </tr>
   
   <tr>
      <td><bean:message key="label.Phone"></bean:message> </td>
     <td> <%if(inObj!=null && inObj.getStringPhNo()!=null){%>
      	<html:text property="phoneNum" value="<%=""+inObj.getStringPhNo()%>" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="phoneNum" onkeydown="numberlimit1(this,'11')"  onkeypress=" return numbersOnly1()"></html:text>
      <%} %></td>
   </tr>
   <tr>
     <td> <bean:message key="label.los"></bean:message> </td>
      <td> <%if(inObj!=null && inObj.getService()!=0){%>
      <html:text property="loService" value="<%=""+inObj.getService()%>" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="loService" onkeypress=" return numbersOnly1()" onkeyup="numberlimit1(this,'3')"></html:text>
      <%} %>
      </td>
   </tr>
   
   <tr>
      <td><bean:message key="label.income"></bean:message> </td>
     <td> <%if(inObj!=null && inObj.getIncome()!=0){%>
      <html:text property="income" value="<%=""+inObj.getIncome()%>" readonly="true"></html:text>
      <%}else {%>
      <html:text property="income" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text>
      <%} %> </td>
   </tr>
   
   <tr>
     <td> <bean:message key="label.expenditure"></bean:message> </td>
    <td>  <%if(inObj!=null && inObj.getExpenditure()!=0){%>
      <html:text property="expenditure" value="<%=""+inObj.getExpenditure() %>" readonly="true"></html:text>
      <%}else {%>
      <html:text property="expenditure" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')" onblur="netincome()"></html:text>
      <%} %>
      </td>
   </tr>
   <tr>
     <td> <bean:message key="label.netincome"></bean:message> </td>
     <td> <%if(inObj!=null && inObj.getNetIncome()!=0){%>
      <html:text property="netIncome" value="<%=""+inObj.getNetIncome()%>" readonly="true"></html:text>
      <%}else {%>
      <html:text property="netIncome" readonly="true"></html:text>
      <%} %> </td>
   </tr>
   
   <tr>
    <!--<td><html:button property="method" value="SaveSelfEmployed"  styleClass="ButtonBackgroundImage" onclick="location.href='?method=SaveSelfEmployed'"/></td>
       -->
       <td>
      <%if(inObj!=null){%>
      <html:submit property="emp_submit" value="SaveSelfEmployed" styleClass="ButtonBackgroundImage" disabled="true"></html:submit>
      <%}else{%>
      <html:submit property="emp_submit" value="SaveSelfEmployed" styleClass="ButtonBackgroundImage" ></html:submit>
      <%} %>
      </td>
       <td><html:button property="clear" value="Clear" styleClass="ButtonBackgroundImage" onclick="func_clear()"></html:button></td>
   </tr>
</table>
</html:form>   
</body>
</html>