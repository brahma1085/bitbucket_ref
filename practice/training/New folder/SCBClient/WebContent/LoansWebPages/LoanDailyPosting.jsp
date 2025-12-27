<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>    
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="java.util.Map"%> 
<html>
<head>
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
<h2 class="h2"><center>Loan Daily Posting</center></h2>	
      
<script type="text/javascript">
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

    

function only_numbers()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57) ) 
		 {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};
	
	function chkAccountExist()
	{
	if(document.getElementById("acctype").value=="All Types"){
  		   document.getElementById("accno").disabled=true;
  		   }else if(document.getElementById("acctype").value!="All Types"){
  		    document.getElementById("accno").disabled=false;
  		   }
	
	  if(document.getElementById("accountclosed").value!=null)
  		  {
  		    
  			if(document.getElementById("accountclosed").value=="accountclosed")
  			{
  				
  				alert("ACCOUNT CLOSED");
  				document.getElementById("accountclosed").value=""; 
  				return false;
  			}
  			
  		  }
  		  if(document.getElementById("result").value!=null)
  		  {
  		    
  			if(document.getElementById("result").value=="AccountisPosted")
  			{
  				alert("Loan Details are Posted");
  				document.getElementById("result").value=""; 
  				return false;
  			}
  			
  		  }
  		   
  		  
  	}	
  	
  	function set(target)
  	{
  	 alert(target);	
  	  document.forms[0].process.value=target;
  	}
  	
  	function clearPage()
 	{ 
        var ele=document.forms[0].elements;
        	for(var i=0;i<ele.length;i++){
        		if(ele[i].type=="text"){
        			ele[i].value="";
        		}
        	}	
 	};
 	
   function fun_submit(target){
   		
   		document.forms[0].process.value=target;
   		document.forms[0].submit();
};


  	
</script>	
	
</head>
<body onload="chkAccountExist()">
<%!ModuleObject object[];%>

<%!int val=0;Object post;%>
<%!boolean flag=false; %>
<% object=(ModuleObject[])request.getAttribute("LoanModuleCode"); %>
<%String AccountCloased;%>
<%String message=(String)request.getAttribute("msg"); %>
<%AccountCloased=(String)request.getAttribute("AccountCloased"); %>
<%if(AccountCloased!=null){
	if(AccountCloased.equalsIgnoreCase("AccountCloased")){
		flag=true;
	}else
		flag=false;
	
	}%>
	<%!
String access;
String  accesspageId;
 Map user_role;
%>
<%
  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");
System.out.println("11111111"+user_role);
System.out.println("222222222"+accesspageId);
if(user_role!=null&&accesspageId!=null){
System.out.println("11111111"+access);
			if(user_role.get(accesspageId)!=null){
			System.out.println("222222222"+access);
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}
			}
// Note:access=1111==>read,insert,delete,update
%>



<%  post=(Object)request.getAttribute("LoanPosting");
if(post!=null){%>
<core:out value="">Account Is Posted!</core:out>
<%}%>

<%if(message!=null){ %>
<div id = "div1" class = "myLayersClass">
    
    <core:if test="<%=message!=null %>">
    <font color="red"><%=message %></font>
    <br><br><br><br>
    </core:if>
    </div>
<%} %>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
<html:form action="/Loans/LoanDailyPosting?pageidentity.pageId=5010">

<table class="txtTable">
   <tr>
     <td><bean:message key="label.combo_loan"></bean:message></td>
     <td><html:select property="acctype" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
      			  <html:option value="All Types"></html:option>
         		  <core:forEach var="acType" varStatus="count" items="${requestScope.LoanModuleCode}">
         		  <html:option value="${acType.moduleCode}"><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
      			  </core:forEach>
       			  </html:select></td>
  </tr>
  <tr>
     <td><bean:message key="label.loanaccno"></bean:message></td>
     <td><html:text styleClass="formTextFieldWithoutTransparent" property="accno" onkeyup="numberlimit(this,'11')" onkeypress="return only_numbers()"  onblur="submit()"></html:text></td>
  </tr>
   <tr>
     <td><bean:message key="label.date"></bean:message></td>
     <td><html:text property="date" value="<%=""+request.getAttribute("Date")%>" size="10" readonly="true"></html:text></td>
  </tr>
  <tr>
  
    <html:hidden property="accountclosed" styleId="accountclosed"></html:hidden>
    <html:hidden property="result" styleId="result"></html:hidden>
    <html:hidden property="process" value="error"></html:hidden>
    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
    <td align="center"><html:submit disabled="<%=flag %>" styleClass="ButtonBackgroundImage"  onclick="fun_submit('process')"></html:submit></td>
   <td><html:button styleClass="ButtonBackgroundImage" property="Clear" value="Clear" onclick="return clearPage()"></html:button>
    </td> 
    <%}else{ %>
    <td align="center"><html:submit disabled="true" styleClass="ButtonBackgroundImage"  ></html:submit></td>
   
       <td><html:button styleClass="ButtonBackgroundImage" property="Clear" disabled="true" value="Clear" ></html:button>
    </td>  
     <%} %>
  </tr>
</table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>