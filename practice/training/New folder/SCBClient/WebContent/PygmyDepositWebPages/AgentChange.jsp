<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>

<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.pygmyDeposit.PygmyMasterObject"%>
<html>
<head>
<title>Insert title here</title>
<h2 class="h2"><center>Agent Changing</center></h2>
</head>
<body>
<% String msg=(String)request.getAttribute("msg");
		if(msg!=null){
		%>
		<font color="red"><%=msg %></font>
		<br><br>
		<%} %>
<html:form action="/Pygmy/AgentChange?PageId=8021">
	<%ModuleObject[] array_module;
	 
	 PygmyMasterObject[] pMaster;
	
	%>
	<%
	
	pMaster=(PygmyMasterObject[])request.getAttribute("AGCHANGE");
	System.out.println("<<<<<<========>>>>>>>"+pMaster);
	%>
	<table class="txtTable">
			<tr>
                 <td><bean:message key="label.agentcode"></bean:message></td>
                  <td><html:select  property="agType" styleClass="formTextFieldWithoutTransparent">
                         <html:option value="SELECT"></html:option>
                         <% array_module=(ModuleObject[])request.getAttribute("AgAcType");
 							System.out.println("here i am=="+array_module); %>                       
							<core:forEach var="agType" items="${requestScope.AgAcType}" varStatus="count">
								<html:option value="${agType.moduleCode}">${agType.moduleAbbrv}</html:option>
                        	</core:forEach>
                         </html:select></td>
            </tr>
            
            <tr>
            	<td><bean:message key="label.agentno"></bean:message></td>
            	<td><html:select property="agNo" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
               		<%if(pMaster!=null){ %>
               		<%	for(int i=0;i<pMaster.length;i++) {%>
                     		<html:option value="<%=""+pMaster[i].getAgentNo()%>"><%=""+pMaster[i].getAgentNo()%></html:option>
                     	<%} }%>
                 </html:select></td>    	
           </tr>
           </table>
  </html:form>          
</body>
</html>