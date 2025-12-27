<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@page import="masterObject.backOffice.AdminObject"%>
<%@page import="masterObject.general.ModuleObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SIParameters</title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />

    <script type="text/javascript">
   
    function set(target)
    {
    alert(target);
    document.forms[0].forward.value=target;
    };
    
    
    function callClear(target){
           document.forms[0].forward.value=target;
         	 var ele= document.forms[0].elements;
             for(var i=0;i<ele.length;i++){
               if(ele[i].type=="text"){
                  
               ele[i].value="";
               }
           
           }
            };
    </script>
	 		      
</head>
<body class="Mainbody" style="overflow: scroll;">
<%@page import="java.util.Map"%>


<%!
String access;
String  accesspageId;
 Map user_role;

%>

<%

  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");

if(user_role!=null&&accesspageId!=null){
			if(user_role.get(accesspageId)!=null){
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}

			
			}
%>
<center><h2 class="h2">Standing Instruction Parameters</h2></center>
<%ModuleObject[] frommod=(ModuleObject[])request.getAttribute("fromacctype"); %>
<% String displaymsg=(String)request.getAttribute("displaymsg");%>
<%if(displaymsg!=null){ %>
<font style="font-style:normal;font-size:12px" color="red"><%=displaymsg %></font>
<%} %>
<%ModuleObject[] tomod=(ModuleObject[])request.getAttribute("toacctype"); %>
<%System.out.println("fromAcc--------"+frommod);%>
<%System.out.println("toAcc--------"+tomod);%>
<%//AdminObject[] adminObj=(AdminObject[])request.getAttribute("Details"); %>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/BackOffice/SIParameters?pageId=11014">
<br><br>
<center>
<display:table name="Details" id="Details" class="its" requestURI="/BackOffice/SIParameters.do" >
<display:column title="checkAll" style="width:1% "><input type="checkbox" name="id" value="${Details.id}" style="margin:0 0 0 4px" onclick="radio(this)" <core:if test="${param.id==Details.id and param.method != 'Save' and param.method != 'Clear' and  param.method != 'Reset'}" >checked="checked"</core:if>/> </display:column>
          <display:column style="width:3%" title="Priority No">
          <core:choose>
				<core:when test="${param.method=='Edit' and param.id==Details.id }">
				<%System.out.println("in jsp i ifffffff");%>
					<input type="text" name="Priority_No" readonly="readonly" style="padding:0;text-align: right;" value="${Details.prioritynum}" />
				</core:when>
				<core:otherwise>
				<%System.out.println("in jsp i else");%>
					<core:out value="${Details.prioritynum}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
	
			
		<display:column style="width:3%" title="From Type">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==Details.id }">
					<input type="text" name="From_Type"  style="padding:0;text-align: right;" value="${Details.frm_acc_type}" />
				</core:when>
				<core:otherwise>
					<core:out value="${Details.frm_acc_type}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%" title="Description">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==Details.id }">
					<input type="text" name="fromdescrip"  style="padding:0;text-align: right;" value="${Details.fromDescription}" />
				</core:when>
				<core:otherwise>
					<core:out value="${Details.fromDescription}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%" title="To Type">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==Details.id }">
					<input type="text" name="To_Type"  style="padding:0;text-align: right;" value="${Details.to_acc_type}" />
				</core:when>
				<core:otherwise>
					<core:out value="${Details.to_acc_type}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		<display:column style="width:3%" title="Description">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==Details.id }">
					<input type="text" name="todescrip"  style="padding:0;text-align: right;" value="${Details.toDescription}" />
				</core:when>
				<core:otherwise>
					<core:out value="${Details.toDescription}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		
</display:table>


<br><br>		
    <input type="submit" value="Reset" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Reset'" />
	
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
	<input type="submit" value="Add" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Add'"/>
    <input type="submit" onclick="location.href='?method=Update'" class="ButtonBackgroundImage" value="Update" name="method"/>
    <%}else{ %>
    <input type="submit" value="Add" name="method" disabled="disabled" class="ButtonBackgroundImage" onclick="location.href='?method=Add'"/>
    <input type="submit" disabled="disabled" onclick="location.href='?method=Update'" class="ButtonBackgroundImage" value="Update" name="method"/>
    <%} %>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
    <html:submit  onclick="set('delete')" styleClass="ButtonBackgroundImage"><bean:message key="label.delete"></bean:message> </html:submit>
    <%}else{ %>
    <html:submit  onclick="set('delete')" disabled="true" styleClass="ButtonBackgroundImage"><bean:message key="label.delete"></bean:message> </html:submit>
    <%} %>
	
	
<br><br>
<html:hidden property="forward" value="error"></html:hidden>
<table class="txtTable">
	<tr style="visibility: <%=""+request.getAttribute("vis") %>" >
		<td><bean:message key="label.priority_num"/>
		<%String prino=(String)request.getAttribute("pri_no"); 
		if(prino!=null){
		%>
		<html:text property="prior_num" styleClass="formTextFieldWithoutTransparent" size="6"  value="<%=""+prino%>" readonly="true"></html:text>
		<%} else{%>
		<html:text property="prior_num" styleClass="formTextFieldWithoutTransparent" size="6" ></html:text>
		<%} %></td>
		<td><bean:message key="label.fromacc"/><html:select property="fromacc">
		<%System.out.println("-------inside fromaccount---------");
		 for(int i=0;i<frommod.length;i++){ 
		   %>
		<html:option value="<%=frommod[i].getModuleCode()%>"><%=frommod[i].getModuleAbbrv()%></html:option>
		<%} %>
		</html:select></td>
		<td><bean:message key="label.toacc"/><html:select property="toacc">
		<% System.out.println("-------inside toaccount---------");
    		for(int i=0;i<tomod.length;i++){ 
			 %>
	
		<html:option value="<%=tomod[i].getModuleCode()%>"><%=tomod[i].getModuleAbbrv()%></html:option>
		<%} %>
		</html:select></td>
	</tr>
	
	<tr style="visibility: <%=""+request.getAttribute("vis") %>">
	<%String str=(String)request.getAttribute("update_insert"); %>
	<%if(str!=null){
	 if(str.equalsIgnoreCase("INSERT")){%>
	 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             <td><html:submit  onclick="set('insert')" styleClass="ButtonBackgroundImage"><bean:message key="label.insert"></bean:message> </html:submit></td>
             <%}else{ %>
            <td><html:submit  onclick="set('insert')" disabled="true" styleClass="ButtonBackgroundImage"><bean:message key="label.insert"></bean:message> </html:submit></td>
             <%} %>
	
	<%}else{ %>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             <td><html:submit  onclick="set('submit')" styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message> </html:submit></td>
             <%}else{ %>
            <td><html:submit  onclick="set('submit')" disabled="true" styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message> </html:submit></td>
             <%} %>
	
	<% }}%>
	</tr>
</table>	

</center>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>