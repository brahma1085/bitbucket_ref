<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@taglib prefix="tabs" uri="/WEB-INF/tabbedpanetag.tld" %>
<%@page import="masterObject.general.SignatureInstructionObject"%>
<%@page import="masterObject.customer.CustomerMasterObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
        <style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
        
         <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
</head>
<body class="MainBody">
<html:form action="/Common/ClgSignature">
 <%! String panelName;
  			CustomerMasterObject cmObject;	
  			SignatureInstructionObject[] signObject;
  			int length;
  			String addSiFlag;
  			String callPagePath;
  %>
  <%
       		panelName=(String)request.getAttribute("panelName");
  			//System.out.println("panelName"+panelName);
  			signObject=(SignatureInstructionObject[])request.getAttribute("signInfo");
  			cmObject=(CustomerMasterObject)request.getAttribute("personalInfo");
  			//System.out.println("signObject"+signObject);
  			addSiFlag=(String)request.getAttribute("AddSiFlag");
  			callPagePath=(String)request.getAttribute("SIFormPath");
  			//callPagePath="/Common/signCombo";
   %>
   				
   			<tabs:tabbedPane>	
   				
				<table class="txtTable"  style="border:thin solid #000000;">
           		
           			 		<tr><td></td><td align="center">Signature Details</td></tr>
      						<tr>
                   			<td><bean:message key="label.cid"></bean:message></td>
                   			<td>
                   			<%if(signObject!=null){ %>
                   			<html:text property="cid"  size="5" styleClass="formTextField" value="<%=""+signObject[0].getCid()%>" readonly="true"></html:text>
                   			<%}else{ %>	
                   			<html:text property="cid" size="5" styleClass="formTextField"  readonly="true" value="0"></html:text>
                    		<%} %>		
                   			</td>	
                   			<td><bean:message key="label.photo"></bean:message></td>
                   			<td>
                   			<%if(cmObject!=null){ %>
                   				<html:img src=""  border="1" height="80" width="70"></html:img>
                   			<%} else {%>
                   				<html:img src="" border="1" height="80" width="70"></html:img>
							<%} %>
							</td>
               				</tr>

               				<tr>
                   			<td><bean:message key="label.acType"></bean:message></td>
                   			<td>
                   			<%if(signObject!=null) { %>
                   			<html:text property="acType"  size="7" styleClass="formTextField" value="<%=""+signObject[0].getSAccType()%>" readonly="true"></html:text>
                   			<%}else{ %>
                   			<html:text property="acType" size="7" styleClass="formTextField" readonly="true" ></html:text>
                    		<%} %> 	
                    		</td>			
               				</tr>

               				<tr>
                   			<td><bean:message key="label.acNum"></bean:message></td>
                   			<td>
                   			<%if(signObject==null){ %>
                   			<html:text property="acNum" size="7" styleClass="formTextField" readonly="true" value="0"></html:text>
                   			<%}else{ %>
                   			<html:text property="acNum" size="7" readonly="true" styleClass="formTextField"  value="<%=""+signObject[0].getSAccNo() %>" ></html:text>
                   			<% } %>
                   			</td>
                   			</tr>
               
               				<tr>
                    		<td><bean:message key="label.custname"></bean:message></td>
                    		<td>
                    		<%if(signObject==null){%>
                   			<html:text property="name" size="9" styleClass="formTextField" readonly="true" ></html:text>
                   			<%}else{ %>
                   			<html:text property="name" size="9" styleClass="formTextField" readonly="true" value="<%=""+signObject[0].getName() %>"></html:text>
                   			<%} %>
                   			</td>
               				</tr>

               				<tr>
                   			<td><bean:message key="label.TypeOfOperation"></bean:message></td>
                   			<td>
                   			<%if(signObject==null){ %>
                   			<html:text property="tyop" size="7" styleClass="formTextField" readonly="true"></html:text>
                   			<%}else{ %>
							<html:text property="tyop" size="7" styleClass="formTextField"  readonly="true"></html:text>                  			
                   			<%} %>
                   			</td> 
                   			<td><bean:message key="label.sign"></bean:message></td>
                   			<td>
                   			<%if(signObject==null){ %>
                   			<html:text property="sign" style="border:none;background:transparent;width:100%" readonly="true"></html:text>
               				<%} else{%>
               				<html:text property="sign" style="border:none;background:transparent;width:100%" readonly="true"></html:text>
               				<%} %>
               				</td>
               				</tr>
								
							
           				</table>  
                     </tabs:tabbedPane>
</html:form>
</body>
</html>