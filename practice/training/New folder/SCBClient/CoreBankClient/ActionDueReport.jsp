<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.loans.DocumentsObject"%>
<%@page import="masterObject.loans.LoanMasterObject"%>
<%@page import="java.util.Map"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>ActionDueReport</title>
	<script type="text/javascript">
		function set(target)
		{
		    		document.forms[0].forward.value=target;
		    		return true;
    		
		};
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
		function fetchLoanModuleAbbr()
		{
			//alert("fetchLoanModuleAbbr---->");
			document.forms[0].submit();
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
    <h2 class="h2"><center>Action Due Report</center></h2>	
</head>
<body>
	<%!ModuleObject[] object; %>
	<%!String moddesc; %>
	<%!DocumentsObject[] docobj; %>
	<% object=(ModuleObject[])request.getAttribute("LoanModuleCode");%>
	<%moddesc=(String)request.getAttribute("moddesc"); %>
	<%docobj = (DocumentsObject[])request.getAttribute("DueReport"); %>
	<% LoanMasterObject lnmodobj;
 lnmodobj = (LoanMasterObject) request.getAttribute("ModName");%>
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
	<%System.out.println("DocumentObject====>"+docobj); %>
  	<table>
  		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>	
  		<html:form action="/Loans/ActionDueReport?pageidentity.pageId=5034">
  		<tr>
      		<td>
      			<bean:message key="label.date"></bean:message>&nbsp;
      			<html:text property="date" size="10" readonly="true" styleClass="formTextFieldWithoutTransparent" value="<%=""+request.getAttribute("Date")%>"></html:text>
     	    </td>
    	</tr>
    	<tr>
    		<td>
    		</td>
    	</tr>
        <tr> 
 	      <td>
 	      	<font style="font-style:normal;font-size:12px">
 	      		<bean:message key="label.combo_loan"></bean:message>
 	      	</font>&nbsp;
      		<html:select property="acctype" styleClass="formTextFieldWithoutTransparent" onchange="fetchLoanModuleAbbr()">
      			<html:option value="SELECT">Select</html:option>
    			   	<core:forEach var="acType" varStatus="count" items="${requestScope.LoanModuleCode}">
      					<html:option value="${acType.moduleCode}">
      						<core:out value="${acType.moduleAbbrv}"></core:out>
							
      					</html:option>      		
      				</core:forEach>
       		</html:select>
       		<font size="3" color="blue">
         		<%if(lnmodobj!=null){ %>
         		<%=""+lnmodobj.getLoanMod()%>
         		<%}else{%>
         		<%=""+""%>
         		<%} %>
       	  </td>
    	</tr>
    	<tr>
    		<td>
    		</td>
    	</tr>
    	<tr>
       		<td>
       			<bean:message key="label.stagecode"></bean:message>&nbsp;
       				<html:text property="stagecode" size="10" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers()" 
onkeyup="numberlimit(this,'11')"></html:text>
      		</td>
    	</tr>
   		<tr>
    		<td>
    		</td>
    	</tr>
    	<tr>
    		<td>
    		</td>
    	</tr>
    	<tr>
      		<td>
      			<html:hidden property="forward" value="error"/>
      				<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>	
         		<html:submit property="View" styleClass="ButtonBackgroundImage"  onclick="set('view')" value="View"></html:submit>
         		<!--<html:button property="print" onclick="window.print()" styleClass="ButtonBackgroundImage"  value="Print"></html:button>
         		--><html:submit styleClass="ButtonBackgroundImage" property="Clear" value="Clear" onclick="return function_clear()"></html:submit>
         		<%}else{ %>
         		<html:submit property="View" styleClass="ButtonBackgroundImage"  disabled="true" value="View"></html:submit>
         		<!--<html:button property="print" onclick="window.print()" styleClass="ButtonBackgroundImage"  value="Print"></html:button>
         		--><html:submit styleClass="ButtonBackgroundImage" property="Clear" value="Clear" disabled="true"></html:submit>
         		<%} %>
         		
      		</td>
    	</tr>
   
    <%if(docobj!=null){%>
    <div id="table1" style="display: block;width: 750px;height: 300px:">
       	<display:table name="DueReport" export="true" id="currentRowObject" class="its" sort="list" pagesize="10" requestURI="/Loans/ActionDueReport.do">
       		<display:column property="acno" style="background-color:#F2F0D2; font:Garamond"></display:column>					
            <display:column property="doccode" style="background-color:#F2F0D2; font:Garamond"></display:column>
	        <display:column property="doc_desc" style="background-color:#F2F0D2; font:Garamond"></display:column>
	        <display:column property="memno" style="background-color:#F2F0D2; font:Garamond"></display:column>					
   		</display:table>
    </div>
    <%} %>
    </html:form>
    <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>	
  </table>
</body>
</html>