<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@page import="java.util.Map"%>
<html>
<head>
<script type="text/javascript">

function setFlag(flagVal)
{
	if(document.forms[0].controlNum.value!=0)
	{
		document.forms[0].flag.value=flagVal;
		document.forms[0].submit();
	}
	else
	{	
		document.forms[0].validateFlag.value="Enter Valid Control Num";
	}
}

function getAlertMessages()
{	
	
	if(document.forms[0].errorFlag.value=='1')
	{
		clearAll();
	}
	
}

function onlynumbers()
 {
       var cha = event.keyCode;
		if ( cha >= 48 && cha <= 57  )
        {
           return true;
        } 
        else 
        {
        	return false;
        }
			   		 
 }

function clearAll()
{
	
		var form_ele = document.forms[0].elements;
		
		var val = document.forms[0].validateFlag.value;
		
		for( var i = 0 ; i< form_ele.length; i++ )
		{
			if(form_ele[i].type=="text")
			{
			form_ele[i].value = "";
			}
		}
	document.forms[0].validateFlag.value=val;	
}







</script>
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



<body class="MainBody" onload="getAlertMessages()">
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
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Clearing/ChequeWithdrawalLink?pageId=7019">
<center><h2 class="h2">ChequeWithdrawal</h2></center>

	<table>
		<tr>
			<td>
			<table>
				<tr><html:text property="validateFlag" size="100" styleClass="formTextField" style="color:red;font-family:bold;"></html:text></tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>	
			<table>
				<tr>
				<td><bean:message key="label.controlno"></bean:message></td>
				<td><html:text property="controlNum" styleClass="formTextFieldWithoutTransparent"  onblur="setFlag('fromControlNum')" onkeypress="return onlynumbers()"></html:text></td>
				</tr>
				<tr>
				<td><bean:message key="label.microcde"></bean:message></td>
				<td><html:text property="MICRCode" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
				</tr>
				<tr>
				<td><bean:message key="label.branch_name"></bean:message></td>
				<td><html:text property="branchName" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
				</tr>
				<tr>
				<td><bean:message key="label.acTypNum"></bean:message></td>
				<td><html:select property="creditAcType" styleClass="formTextFieldWithoutTransparent" disabled="true" styleId="credit_acc_type">
					<html:option value="select">select</html:option>
					<core:forEach var="module"  varStatus="count" items="${requestScope.Main_Module_code}" >
					<html:option value="${module.moduleCode}"><core:out value="${module.moduleAbbrv}"></core:out></html:option>
					</core:forEach>
					</html:select>
				</td>
				<td><html:text property="creditAcNum" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
				</tr>

				<tr>
				<td><bean:message key="label.amount"></bean:message></td>
				<td><html:text property="amount" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
				</tr>
				<tr>
				<td><bean:message key="label.payee"></bean:message></td>
				<td><html:text property="payee" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
				</tr>
				<tr>
				<td><bean:message key="label.chqddpo"></bean:message></td>
				<td><html:text property="chqddpo" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
				</tr>

				<tr>
				<td><bean:message key="label.chqno"></bean:message></td>
				<td><html:text property="chqno" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
				</tr>
				<tr>
				<td><bean:message key="label.chqdate"></bean:message></td>
				<td><html:text property="chqdate" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
				</tr>
				<tr>
				<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             	<td><html:button property="withdrawn" value="Withdraw" onclick="setFlag('withdraw')" styleClass="ButtonBackGroundImage"></html:button></td>
             	<%}else{ %>
            	<td><html:button property="withdrawn" disabled="true" value="Withdraw" onclick="setFlag('withdraw')" styleClass="ButtonBackGroundImage"></html:button></td>
            	 <%} %>
				
				
				<td><html:button property="clear" value="Clear" styleClass="ButtonBackGroundImage" onclick="clearAll()"></html:button></td>
				</tr>
				</table>
				
					<html:hidden property="flag"></html:hidden>
					<html:hidden property="errorFlag"></html:hidden>
		</td>
		</tr>
		</table>
					
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>