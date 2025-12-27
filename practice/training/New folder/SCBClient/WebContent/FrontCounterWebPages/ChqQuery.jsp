
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@page import="java.util.Map"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Mohsin
  Date: Dec 24, 2008
  
  To change this template use File | Settings | File Templates.
--%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>

<html>
  <head><title>AccountOpening</title>
       <font color="blue" ><center>
<h2 class="h2">Query On Cheque</h2></center></font>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
       
      
      <!--<style type="text/css">
          body{
              font-size:10px;
              font-family:serif;
              font-style:oblique;
              font-weight:bold;
              background:beige;
          }
          table{
               background:transperent; 
          }
          
    </style>
    --><link href="Images/DMTStyle.css" rel="stylesheet" type="text/css" />
    
    <script>
    
    function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("only "+size+"digits allowed")
         		txt.value="";
         		return false;
          	}
         }
    
    function setval(){
    document.forms[0].clr.value='clearall';
    document.forms[0].submit();
    
    }
    </script>
    
  </head>
  <body class="Mainbody">
    <%!
        ModuleObject[] array_module;
        String details[];
        AccountMasterObject acountMaster;
        String jspPath;
        ModuleObject[] mod;
        CustomerMasterObject cmObject;
        SBOpeningActionForm sbForm;
        Map user_role;
        String access;
    %>
    <% 
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    	String msg=(String)request.getAttribute("empty");
    	String details[]=(String[])request.getAttribute("chqdetail");
    	String accesspageId=(String)request.getAttribute("accesspageId");
    	user_role=(Map)request.getAttribute("user_role");
    	
    	
    	if(user_role!=null&&accesspageId!=null){
			if(user_role.get(accesspageId)!=null){
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}

			
			}
    	
    %>
    
    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
        <core:if test="<%=msg!=null%>">
    <font color="red"><%=msg%></font>
    </core:if>
    
     <html:form action="/FrontCounter/ChqQuery?pageId=3010" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="defaultTab" value="PersonalDetails"/>
     	<html:hidden property="defSIndex" value="1"/>
     	<html:hidden property="clr" />
     	<html:hidden property="pageId" value="3010"/>
 
 <table border="1" width="458" height="20" class="txtTable">
	<tr>
		<td height="38" width="105">Cheque No.:</td>
		<td height="38" width="279"><html:text property="chqno" onblur="submit()" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;" onkeyup="numberlimit(this,'10')"/></td>
		</tr>
		</table>
		<br><br>
		
		<core:if test="<%= details!=null%>">
    <font color="blue"><%="Cheque Details"%></font>
    
		<table border="1" width="458"><tr>
		<td height="38" width="124">Account Details:</td>
		<td height="38" width="89">
		<core:if test="<%=details[1]!=null %>">
		<font color="blue"><%=details[1]%></font>
		</core:if></td>
	</tr>
	<tr>
		<td height="21" width="448" colspan="4">&nbsp;</td>
	</tr>
	<tr>
		<td height="35" width="219" colspan="2">Cheque Details of :</td>
		<td height="35" width="223" colspan="2">
		<core:if test="<%=details[0]!=null %>">
		<font color="blue"><%=details[0]%></font>
		</core:if>
		</td>
	</tr>
	<tr>
		<td height="36" width="219" colspan="2">Account No.:</td>
		<td height="36" width="223" colspan="2">
		<core:if test="<%=details[1]!=null %>">
		<font color="blue"><%=details[1]%></font>
		</core:if>
		</td>
	</tr>
	<tr>
		<td height="37" width="219" colspan="2">Cheque Book No.:</td>
		<td height="37" width="223" colspan="2">
		<core:if test="<%=details[2]!=null %>">
		<font color="blue"><%=details[2]%></font></core:if></td>
	</tr>
	<tr>
		<td height="41" width="219" colspan="2">Payment stopped?:</td>
		<td height="41" width="223" colspan="2">
		<core:if test="<%=details[3]!=null %>">
		<font color="blue"><%=details[3]%></font></core:if></td>
	</tr>
	<tr>
		<td height="39" width="219" colspan="2">Post Dated ?:</td>
		<td height="39" width="223" colspan="2">
		<core:if test="<%=details[4]!=null %>">
		<font color="blue"><%=details[4]%></font></core:if></td>
	</tr>
	<tr>
		<td height="39" width="219" colspan="2">Expiry Date:</td>
		<td height="39" width="223" colspan="2">
		<core:if test="<%=details[5]!=null %>">
		<font color="blue"><%=details[5]%></font></core:if></td>
	</tr>
	<tr>
		<td height="41" width="219" colspan="2">Canceled ?:</td>
		<td height="41" width="223" colspan="2">
		<core:if test="<%=details[6]!=null %>">
		<font color="blue"><%=details[6]%></font></core:if></td>
	</tr>
	<tr>
		<td height="45" width="219" colspan="2">Deleted ?:</td>
		<td height="45" width="223" colspan="2">
		<core:if test="<%=details[7]!=null %>">
		<font color="blue"><%=details[7]%></font></core:if></td>
	</tr>
	<tr>
	<td><html:button property="but" value="Cancel" onclick="setval()" styleClass="ButtonBackgroundImage"></html:button> </td>
	
	</tr>
</table>
</core:if>
                </html:form>
                <%
                jspPath=(String)request.getAttribute("flag");
      		System.out.println("jspPath=="+jspPath);
            %>
       		<core:if test="<%=jspPath!=null %>">	     
            	<jsp:include page="<%=jspPath.trim() %>"></jsp:include>
            </core:if>
            <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
  </body>
  </html>          