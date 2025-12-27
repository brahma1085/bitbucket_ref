<%--
  Created by IntelliJ IDEA.
  User: sangeetha
  Date: Dec 8, 2007
  Time: 12:20:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@ taglib uri="/WEB-INF/c-rt.tld" prefix="core"%>
<%@page import="masterObject.general.SignatureInstructionObject"%>
<%@page import="com.scb.pd.forms.AgentOpeningForm"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>
<%@page import="com.scb.sh.forms.AllotmentForm"%>
<%@page import="masterObject.general.AccountObject"%>
<html>
  <head><title></title>
       <style type="text/css">
          body{
             background:beige;
          }
          table{
             background:transparent;
             /*border:solid burlywood;*/

                 }
          tr{
             background:transparent;
          }
          td{
             background:transparent;

          }
        
          
        </style>
  </head>
  <body>
  		 <%! String panelName;
  			CustomerMasterObject cmObject;	
  			SignatureInstructionObject[] signObject;
  			AgentOpeningForm agForm;
  			int length;
  			String addSiFlag;
  			String callPagePath;
  		 %>
  		 <%
       		panelName=(String)request.getAttribute("panelName");
  			System.out.println("panelName"+panelName);
  			signObject=(SignatureInstructionObject[])request.getAttribute("SignInfo");
  			System.out.println("signObject"+signObject);
  			//addSiFlag=(String)request.getAttribute("AddSiFlag");
  			callPagePath=(String)request.getAttribute("SIFormPath");
  			callPagePath="/Common/signCombo";
   		 %>
       <html:form action="<%=callPagePath%>">
    
            				<table cellspacing="5" style="border-bottom-color:black;border-bottom-style:solid;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">
           						<tr>
               						<tr><td align="center" ><b><%="SignatureInstruction"%></b></td></tr>
				    	        <tr>
                   					<td><bean:message key="label.cid"></bean:message></td>
                   					<%if(signObject!=null){ 
                   						for(int i=0;i<signObject.length;i++){
                   					%>
                   						<td><html:text property="cid" size="8" onchange="submit()" style="border:none;background:transparent;width:100%" value="<%=""+signObject[i].getCid()%>" readonly="true"></html:text></td>
                   					<%}}else{ %>	
                   						<td><html:text property="cid" size="8" onchange="submit()" value="0"></html:text></td>	
                    				<%} %>		
                   					<td><bean:message key="label.photo"></bean:message></td>
                   					<td><html:img  src="swe.jpg" border="1" height="80" width="70"></html:img></td>
               					</tr>

               					<tr>
                   					<td><bean:message key="label.acType"></bean:message></td>
                   					<%if(signObject!=null) {
                   						for(int i=0;i<signObject.length;i++){	
                   					%>
                   						<td><html:text property="acType"  size="8" onchange="submit()" style="border:none;background:transparent;width:100%" value="<%=""+signObject[i].getSAccType()%>" readonly="true"></html:text></td>
                   					<%}}else{ %>
                   						<td><html:text property="acType"  size="8"></html:text></td>
                    				<%} %> 				
               					</tr>

               					<tr>
                   					<td><bean:message key="label.acNum"></bean:message></td>
                   					<%if(signObject==null){ 
                   						
                   					%>
                   					<td><html:text property="acNum"  value="0" size="8"></html:text></td>
                   					<%}else{ 
                   						for(int i=0;i<signObject.length;i++){
                   					%>
                   					<td ><html:text property="acNum" style="border:none;background:transparent;width:100%"  value="<%=""+signObject[i].getSAccNo() %>" size="8" ></html:text></td>
                   					<% }} %>
                   					
               					</tr>
               
               					<tr>
                    				<td><bean:message key="label.custname"></bean:message></td>
                    				<%if(signObject==null){
                    					
                    				%>
                   					<td><html:text property="name"  size="8"></html:text></td>
                   					<%}else{ 
                   						for(int i=0;i<signObject.length;i++){
                   					%>
                   					<td><html:text property="name" style="border:none;background:transparent;width:100%" value="<%=""+signObject[i].getName() %>" size="8"></html:text></td>
                   					<%}} %>
               					</tr>

               					<tr>
                   					<td><bean:message key="label.TypeOfOperation"></bean:message></td>
                   					<td><html:text property="tyop"  size="8"></html:text></td>
                   					<td><bean:message key="label.sign"></bean:message></td>
                   					<td><html:text property="sign" style="border:none;background:transparent;width:100%" size="8"></html:text></td>
               					</tr>
								<tr>
           							<td><html:submit></html:submit></td>
                    				<td><html:submit value="Delete"></html:submit></td>
                    				<td><html:submit value="Clear"></html:submit></td>
           						</tr>
           					</table>  
                     
    
 </html:form>
 </body>
</html>
