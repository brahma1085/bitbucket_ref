<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Map"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@page import="masterObject.general.ModuleObject"%>
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
   




function set(target)
{
if(document.getElementById("acno").value=="0")
	{
		alert('Enter the Account Number!');
		document.getElementById("acno").focus();
		return false;
		}else if(document.getElementById("acno").value=="")
	{
		alert('Enter the Account Number!');
		document.getElementById("acno").focus();
		return false;
		}
		else if(document.getElementById("pen_amt").value=="0")
	{
		alert('Enter the Penalty Amount!');
		document.getElementById("pen_amt").focus();
		return false;
		}
		else if(document.getElementById("pen_amt").value=="")
	{
		alert('Enter the Penalty Amount!');
		document.getElementById("pen_amt").focus();
		return false;
		}else{
		 document.forms[0].forward.value=target;
    if(document.forms[0].valid.value=='penalset')
    {
    	alert(" Penal Interest is set");
    }
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

</script>

<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../mages/DMTStyle.css" type="text/css" media="print" />
    <center><h2 class="h2">Penal Int Fix</h2></center>	
</head>
<body>
<%!ModuleObject object[];
Object obj;%>
<% object=(ModuleObject[])request.getAttribute("LoanModuleCode");
	obj=(Object)request.getAttribute("return_value");%>
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

<% String msg=(String)request.getAttribute("msg"); 
	if(msg!=null){
	%>
	<font color="red"><%=msg %></font>
	<br><br>
	<%} %>
  	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>	
<html:form action="/Loans/PenalIntFix?pageidentity.pageId=5033">

<%

	System.out.println("-----Hi from Penal Int fix----------------------"); 
%>

		

    <table class="txtTable">
    <tr>
    <td>
    <table>
      <tr>
        <td><bean:message key="label.combo_loan"></bean:message>
    	 <html:select property="acctype" styleClass="formTextFieldWithoutTransparent">
      			 
         		  <core:forEach var="acType" varStatus="count" items="${requestScope.LoanModuleCode}">
      			  <html:option value="${acType.moduleCode}"><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
      			  </core:forEach>
       			  </html:select></td>
       			  
       	<td>
           <bean:message key="label.loanaccno"></bean:message>
           <html:text property="acno" size="10" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')" onblur="submit()"></html:text>
        </td>	
       			  	  
      </tr>
      <tr>
        <td>
          <bean:message key="label.penalamt"></bean:message>
          <html:text property="pen_amt" size="10" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text>
        </td>
        
          <td>Details
          <html:select property="details" onchange="submit()">
              <html:option value="select">Select</html:option>
          <html:option value="Personal">Personal</html:option>
          <html:option value="Loan Status">Loan Status</html:option>
         	</html:select>
         	</td>
      </tr>
      
      <tr>
        <td>
          <html:hidden property="valid" value="valid"/>
          <html:hidden property="forward" value="error"/>
            	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
        	<html:submit styleClass="ButtonBackgroundImage" property="Submit" onfocus="set('Submit');" value="Submit"></html:submit>
        	<html:button styleClass="ButtonBackgroundImage" property="Clear" value="Clear" onclick="return clearPage()"></html:button>
        	<%}else{ %>
        	<html:submit styleClass="ButtonBackgroundImage" property="Submit" disabled="true" value="Submit"></html:submit>
        	<html:button styleClass="ButtonBackgroundImage" property="Clear" value="Clear" disabled="true"></html:button>
        	<%} %>
        </td>
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
</tr>
</table>
</html:form>  
 <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>	
</body>
</html>