<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.loans.LoanReportObject"%>
 <%@page import="java.util.Map"%> 
<html>
<head>

<script type="text/javascript">

function fun_button(target)
{
	if(document.forms[0].txt_loanaccno.value=="0")
	{ 
	   alert(" Loan Account Number Cannot Be Zero");
	   return false;
	}
	else if(document.forms[0].txt_loanaccno.value=="")
	{ 
	   alert("Loan Account Number Cannot Be Blank");
	   return false;
	}
	else{
	document.forms[0].button_value.value=target;
	}
}

function fun_clear()
{
	alert("Clear");
	document.forms[0].txt_loanaccno.value="";
	document.getElementById("table1").style.display='none'; 
	return false;

}

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
    <link rel="stylesheet" href="../mages/DMTStyle.css" type="text/css" media="print" />
    <h2 class="h2"><center>Loan Meeting Agenda</center></h2>	

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body class="Mainbody">

<%ModuleObject LoanAccType[]; 
  LoanReportObject rep_obj[];


%>
<%LoanAccType=(ModuleObject[])request.getAttribute("LoanAccType");
String message=(String)request.getAttribute("msg");
rep_obj=(LoanReportObject[])request.getAttribute("rep_obj");

System.out.println("Reportobject in jsp==========<>"+rep_obj);
%>
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
<%if(message!=null){ %>
<div id = "div1" class = "myLayersClass">
    
    <core:if test="<%=message!=null %>">
    <font color="red"><%=message %></font>
    <br><br><br><br>
    </core:if>
    </div>
<%} %>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Loans/LoanMeetingAgenda?pageidentity.pageId=5030">
	
<table class="txtTable">
		
	<tr>
		<td align="right"><bean:message key="label.loancat"/></td>
		<td><html:select property="txt_Loancat">
		    <%if(LoanAccType!=null){ %>
		    <%for(int i=0;i<LoanAccType.length;i++){ %>
		    <html:option value="<%= ""+LoanAccType[i].getModuleCode() %>"><%= ""+LoanAccType[i].getModuleAbbrv() %></html:option>
		    <%} }%>
		    </html:select>
		</td>

		<td></td>
		
		<td align="right"><bean:message key="label.loanaccno"/></td>
		<td><html:text property="txt_loanaccno" size="10"  onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')"></html:text></td>
	</tr>
	
	<html:hidden property="button_value" value="error"/>
	
	<tr></tr>
	<tr></tr>
	
	<tr>
	    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
	    <td><html:submit property="print" onfocus="fun_button('File')" styleClass="ButtonBackgroundImage"  value="File"></html:submit></td>
	     <td><html:button property="clear" styleClass="ButtonBackgroundImage" value="clear" onclick="fun_clear()"></html:button></td>
	    <%}else{ %>
	    <td><html:submit property="print" disabled="true" styleClass="ButtonBackgroundImage"  value="File"></html:submit></td>
	   
	    
	    <td><html:button property="clear" disabled="true" styleClass="ButtonBackgroundImage" value="clear" onclick="fun_clear()"></html:button></td>
	 <%} %>
	</tr>
	
	           	

</table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>		
</body>
</html>