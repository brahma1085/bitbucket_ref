<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>    
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>    
<%@page import="java.util.Map"%> 
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">
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
function clearPage()
 	{ 
        var ele=document.forms[0].elements;
        	for(var i=0;i<ele.length;i++){
        		if(ele[i].type=="text"){
        			ele[i].value="";
        		}
        	}	
 	};
function refnoGen()
{
  if(document.getElementById("notfound").value!=null)
  {
     if(document.getElementById("notfound").value=="notfound")
     {
        alert("Loan Account not found");
        document.getElementById("notfound").value="";
        return false;
     }
     
  }
  
  if(document.getElementById("ref").value!=0)
  { 
    alert("Reference Number "+document.getElementById("ref").value+" is generated");
    clearPage();
    return false;
  }
  
  if(document.getElementById("updateid").value!=0)
  {
    if(document.getElementById("updateid").value==1)
    {
    	alert("Updated Successfully");
    	clearPage();
    	return false;
    }else
        alert("Updation failed");
  }
  if(document.getElementById("deleteid").value!=0)
  {
  	if(document.getElementById("deleteid").value==1)
    {
    	alert("deleted Successfully");
    	clearPage();
    	return false;
    }else
        alert("deleted failed");
  }
  
}

function set(target){
if(document.getElementById("acno").value=="0")
	{
		alert('Account Number Cannot Be Zero!');
		document.getElementById("acno").focus();
		return false;
		}else if(document.getElementById("acno").value=="")
	{
		alert('Account Number Cannot Be Blank!');
		document.getElementById("acno").focus();
		return false;
		}

document.forms[0].forward.value=target;
  document.forms[0].submit();
return true;
    
};

function Only_Numbers()
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


<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
<h2 class="h2"><center>Other Charges</center></h2></head>
<body onload="return refnoGen()">
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
<%String message=(String)request.getAttribute("msg"); %>
<%if(message!=null){ %>
<div id = "div1" class = "myLayersClass">
    <core:if test="<%=message!=null %>">
    <font color="red"><%=message %></font>
    <br><br><br><br>
    </core:if>
    </div>
<%} %>
	
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
<html:form action="/Loans/OtherCharges?pageidentity.pageId=5012">

<%! String Transaction_amount,Reason; %>

<%

Transaction_amount=(String)request.getAttribute("Transaction_amount");
Reason=(String)request.getAttribute("Reason");

%>

<table>
	<td>
<table>

<tr>
<td align="right"><bean:message key="label.refno"></bean:message></td>
<td><html:text property="refno" onkeypress="return Only_Numbers()" onblur="submit()" onkeyup="numberlimit(this,'11')"></html:text></td>
</tr>

<tr>
<td align="right"><bean:message key="label.combo_loan"></bean:message></td>
<td><html:select property="acctype">
      			  
         		  <core:forEach var="acType" varStatus="count" items="${requestScope.LoanModuleCode}">
      			  <html:option value="${acType.moduleCode}"><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
      			  </core:forEach>
       			  </html:select></td>
<td><html:text property="acno" onkeyup="numberlimit(this,'11')" onblur="submit()" onkeypress="return Only_Numbers()"></html:text></td>
</tr>

<tr>
<td align="right"><bean:message key="label.sancdate"></bean:message></td>
<td><html:text property="sancdate" readonly="true" styleClass="formTextField" size="10" ></html:text></td>
</tr>

<tr>
<td align="right"><bean:message key="label.sancamt"></bean:message></td>
<td><html:text property="sancamt" readonly="true" styleClass="formTextField"  size="10" onkeypress="return Only_Numbers()" ></html:text></td>
</tr>

<tr>
<td align="right"><bean:message key="label.noofinstal"></bean:message></td>
<td><html:text property="installments" readonly="true" styleClass="formTextField"  size="10" onkeypress="return Only_Numbers()"></html:text></td>
</tr>

<tr>
<td align="right"><bean:message key="label.trandate"></bean:message></td>
<td><html:text property="trandate" readonly="true" styleClass="formTextField"  size="10"></html:text></td>
</tr>

<tr>
<td align="right"><bean:message key="label.tranamt"></bean:message></td>
<%if(Transaction_amount!=null){ %>
<td><html:text property="tranamt" styleClass="formTextFieldWithoutTransparent" value="<%= ""+Transaction_amount %>" size="10" onkeypress="return Only_Numbers()" readonly="true"></html:text></td>
<%}else { %>
<td><html:text property="tranamt" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text></td>
<%} %>
</tr>

<tr>
<td align="right"><bean:message key="label.reason"></bean:message></td>
<%if(Reason!=null){ %>
<td><html:text property="reason" value="<%= ""+Reason %>" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
<%}else{%>
<td><html:text property="reason"  styleClass="formTextFieldWithoutTransparent" onkeypress="return only_alpha()"></html:text></td>
<%} %>
</tr>
<html:hidden property="refnoGen" styleId="ref"></html:hidden>
<html:hidden property="accountnotfound" styleId="notfound"></html:hidden>
<html:hidden property="update" styleId="updateid"></html:hidden>
<html:hidden property="delete" styleId="deleteid"></html:hidden>
<html:hidden property="defaultTab" value="Personal"></html:hidden>
<html:hidden property="defaultTab" value="LoanStatus"></html:hidden> 
      			        


<tr>
<html:hidden property="forward" value="error"/>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
<td><html:submit onclick="set('submit')" styleClass="ButtonBackgroundImage" value="Submit"></html:submit></td>
<td><html:submit onclick="set('update')" styleClass="ButtonBackgroundImage" value="Update"></html:submit></td> 
<%}else{ %>
<td><html:submit onclick="set('submit')" styleClass="ButtonBackgroundImage" disabled="true" value="Submit"></html:submit></td>
<td><html:submit onclick="set('update')" styleClass="ButtonBackgroundImage" disabled="true" value="Update"></html:submit></td> 
<%} %>
 <td> <html:button styleClass="ButtonBackgroundImage" property="Clear" value="Clear" onclick="return clearPage()"></html:button> </td>
  <td>  <html:submit styleClass="ButtonBackgroundImage" onclick="set('delete');" value="Delete"></html:submit></td>
     
   

</tr>
</table>
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
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>