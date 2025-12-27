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
        function only_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122) || (cha >= 65 && cha <=90) ||cha==32) 
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





</script>


<%IncomeObject incomeObject= (IncomeObject)request.getSession().getAttribute("Pension");%>
	
<html:form action="/Loans/LoanApplicationDE?pageidentity.pageId=5008">
   <table>
  <tr>
      <td><bean:message key="label.employer"></bean:message></td> 
      <td><%if(incomeObject!=null){ %>
      <html:text property="empname" value="<%=""+incomeObject.getName()%>" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="empname" onkeypress="return only_alpha()"></html:text>
      <%} %></td>
   </tr>
   <tr>
     <td> <bean:message key="label.address"></bean:message> </td>
    <td>  <%if(incomeObject!=null){ %>
      <html:text property="address" value="<%=""+incomeObject.getAddress()%>" readonly="true"></html:text>
   	  <%}else{ %>
   	  <html:text property="address" onkeypress="return only_for_address()"></html:text>
   	  <%} %>	</td>
   </tr>
   
   
   <tr>
      <td><bean:message key="label.Phone"></bean:message> </td>
      <td><%if(incomeObject!=null){ %>
      <html:text property="phno" value="<%=""+incomeObject.getPhNo()%>" readonly="true"></html:text>
   	  <% }else{	%>
   	  <html:text property="phno" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'11')"></html:text>
   	  <%} %></td>
   </tr>
   <tr>
      <td><bean:message key="label.pensionamt"></bean:message> </td>
      <td><%if(incomeObject!=null){ %>
      <html:text property="pensionamt" value="<%=""+incomeObject.getIncome()%>" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="pensionamt" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text>
      <%} %></td>
   </tr>
   
   <tr>
      <td><bean:message key="label.bankname"></bean:message> </td>
     <td> <%if(incomeObject!=null){ %>
      <html:text property="bankname" value="<%=""+incomeObject.getBankName()%>" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="bankname" onkeypress="return only_alpha()"></html:text>
      <%} %> </td>
   </tr>
   
   <tr>
     <td> <bean:message key="label.AccountType"></bean:message> </td>
     <td> <%if(incomeObject!=null){ %>
      <html:text property="acctype" value="<%=""+incomeObject.getAccType()%>" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="acctype"></html:text>
      <%} %> </td>
   </tr>
   
   <tr>
      <td> <bean:message key="label.AccountNumber"></bean:message> </td>
     <td> <%if(incomeObject!=null){ %>
      <html:text property="accno" value="<%=""+incomeObject.getAccNo()%>" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="accno" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'11')"></html:text>
      <%} %> </td>
   </tr>
   
   <tr>
     <td> <bean:message key="label.netincome"></bean:message> </td>
    <td>  <%if(incomeObject!=null){ %>
      <html:text property="netincome" value="<%=""+incomeObject.getNetIncome()%>" readonly="true"></html:text>
      <%}else{ %>
      <html:text property="netincome" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text>
      <%} %> </td>
   </tr>
   
   <tr>
    <!--<td><input type="button" value="SavePension" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=SavePension'"/></td>
      -->
      <td>
      <%if(incomeObject!=null){%>
      <html:submit property="emp_submit" value="SavePension" styleClass="ButtonBackgroundImage" disabled="true"></html:submit>
      <%}else{%>
      <html:submit property="emp_submit" value="SavePension" styleClass="ButtonBackgroundImage" ></html:submit>
      <%} %>
      </td>
      <td> <html:button property="clear" value="Clear" styleClass="ButtonBackgroundImage" onclick="func_clear()"></html:button></td>
         
</tr>
   
</table>  
</html:form> 
</body>
</html>