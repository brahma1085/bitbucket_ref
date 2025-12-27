<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld"%>
<%@ taglib  prefix="bean" uri="/WEB-INF/struts-bean.tld"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">

function set(flagValue)
{

		if(flagValue=="submit")
		{
				 document.forms[0].flag.value=flagValue;
 				 document.forms[0].submit();
 				
		}
		else
		{
			if(document.forms[0].controlNumber.value!=0)
			{
				document.forms[0].flag.value=flagValue;
				document.forms[0].submit();
			}
		else
			{
				document.forms[0].validateFlag.value="Enter Valid Control Number";
			}	

		}
}
function numbersOnly()
{
      var cha = event.keyCode;
			
	if(cha>=48 && cha<=57) 
	{
         	return true;
    } 
    else
    {
   		 return false;
   	}
			        
}

function getAlertMessages()
{
	
	if(document.forms[0].errorFlag.value=='1')
	{
		clearAll();
	}


}

function clearAll()
{
	
		var form_ele = document.forms[0].elements;
		
		var val = document.forms[0].validateFlag.value;
		
		for ( var i = 0 ; i< form_ele.length; i++ )
		{
			if(form_ele[i].type=="text")
				form_ele[i].value = "";
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
<html:form action="/Clearing/SelectivePostLink?pageId=7008">

<center><h2 class="h2">Selective Post</h2></center>

<table>
	<tr>
	<td>
	<table><tr><html:text property="validateFlag" readonly="true" styleClass="formTextField" style="color:red;font-family:bold;" size="100"></html:text></tr></table>
	</td>
	</tr>
	<tr>
	<td>
	<table>

<tr>
<td><bean:message key="label.controlno"></bean:message></td>
<td><html:text property="controlNumber" onblur="set('frmControlNum')" onkeypress="return numbersOnly()"></html:text>  </td>
</tr>

<core:if test="${requestScope.clearingObject==null}">

<tr>
<td><bean:message key="label.sent_to" ></bean:message>  </td>
<td> <html:text property="sent_to" styleClass="formTextField" readonly="true"></html:text> </td>
</tr>

<tr>
<td><bean:message key="label.bankCodeName"></bean:message>  </td>
<td><html:text property="bankCode" readonly="true"></html:text>  </td>
<td><bean:message key="label.source"></bean:message>  </td>
<td><html:text property="source" styleClass="formTextField" readonly="true"></html:text>  </td>
</tr>

<tr>
<td><bean:message key="label.bank"></bean:message>  </td>
<td><html:text property="bankCode" styleClass="formTextField" readonly="true"></html:text>  </td>
<td><bean:message key="label.clr_no"></bean:message>  </td>
<td><html:text property="clgNum" readonly="true" ></html:text>  </td>
</tr>

<tr>
<td><bean:message key="label.branch_name"></bean:message>  </td>
<td><html:text property="branchName" styleClass="formTextField" readonly="true"></html:text>  </td>
<td><bean:message key="label.clr_date"></bean:message>  </td>
<td><html:text property="clr_date" readonly="true" ></html:text>  </td>
</tr>

<tr>
<td><bean:message key="label.multi_credit"></bean:message>  </td>
<td><html:checkbox property="multi_credit"></html:checkbox>  </td>
</tr> 

<tr>
<td><bean:message key="label.acType"></bean:message></td>
<td><html:text property="creditAcType" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
</tr>

<tr>
<td><bean:message key="label.creditAcNum"></bean:message>  </td>

<td><html:text property="creditAcNum" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
</tr>




<tr>
<td><bean:message key="label.amount"></bean:message>  </td>
<td><html:text property="amount" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
</tr>


<tr>
<td><bean:message key="label.chqDdPo"></bean:message>  </td>
<td><html:text property="chqddpo" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
</tr>


<tr>
<td><bean:message key="label.chqDdPoNo"></bean:message>  </td>
<td><html:text property="chqDdPoNo" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
</tr>


<tr>
<td><bean:message key="label.chqDdPoDate"></bean:message>  </td>
<td><html:text property="chqDdPoDate" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
</tr>

<tr><td><html:button property="clear" styleClass="ButtonBackGroundImage" onclick="clearAll()" value="Clear"></html:button></td></tr>
</core:if>

<core:if test="${requestScope.clearingObject!=null}">

<tr>
<td><bean:message key="label.sent_to"></bean:message>  </td>
<td> <html:text property="sent_to" value="${requestScope.clearingObject.sendTo}" styleClass="formTextFieldWithoutTransparent"> </html:text> </td>
</tr>

<tr>
<td><bean:message key="label.bankCodeName"></bean:message>  </td>
<td><html:text property="bankCode" value="${requestScope.clearingObject.bankCode}" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
<td><bean:message key="label.source"></bean:message>  </td>
<td><html:text property="source" value="${requestScope.clearingObject.sourceName}" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
</tr>

<tr>
<td><bean:message key="label.bank"></bean:message>  </td>
<td><html:text property="bankCode" value="${requestScope.clearingObject.bankName}" styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
<td><bean:message key="label.clr_no"></bean:message>  </td>
<td><html:text property="bankCode" value="${requestScope.clearingObject.clgNo}"  styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
</tr>

<tr>
<td><bean:message key="label.branch_name"></bean:message>  </td>
<td><html:text property="branchName" value="${requestScope.clearingObject.branchName}"  styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
<td><bean:message key="label.clr_date"></bean:message>  </td>
<td><html:text property="clr_date" value="${requestScope.clearingObject.clgDate}"  styleClass="formTextFieldWithoutTransparent"></html:text>  </td>
</tr>

<tr>
<td><bean:message key="label.multi_credit"></bean:message>  </td>
<td><html:text property="multi_credit" styleClass="formTextFieldWithoutTransparent" value="${requestScope.clearingObject.multiCredit}"></html:text>  </td>
</tr> 

<tr>
<td><bean:message key="label.creditAcNum"></bean:message>  </td>
<td><html:text property="creditAcType" styleClass="formTextFieldWithoutTransparent" value="${requestScope.clearingObject.creditACType}"></html:text>  </td>
<td><html:text property="creditAcNum" styleClass="formTextFieldWithoutTransparent" value="${requestScope.clearingObject.creditACNo}"></html:text>  </td>
</tr>




<tr>
<td><bean:message key="label.amount"></bean:message>  </td>
<td><html:text property="amount" styleClass="formTextFieldWithoutTransparent" value="${requestScope.clearingObject.tranAmt}"></html:text>  </td>
</tr>


<tr>
<td><bean:message key="label.chqDdPo"></bean:message>  </td>
<td><html:text property="chqddpo" styleClass="formTextFieldWithoutTransparent" value="${requestScope.clearingObject.chqDDPO}"></html:text>  </td>
</tr>


<tr>
<td><bean:message key="label.chqDdPoNo"></bean:message>  </td>
<td><html:text property="chqDdPoNo" styleClass="formTextFieldWithoutTransparent" value="${requestScope.clearingObject.qdpNo}"></html:text>  </td>
</tr>


<tr>
<td><bean:message key="label.chqDdPoDate"></bean:message>  </td>
<td><html:text property="chqDdPoDate" styleClass="formTextFieldWithoutTransparent" value="${requestScope.clearingObject.qdpDate}"></html:text>  </td>
</tr>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
<tr><td><html:button property="buttonSubmit" styleClass="ButtonBackGroundImage" onclick="set('submit')" value="Submit"></html:button></td>
<%}else{ %>
<tr><td><html:button property="buttonSubmit" styleClass="ButtonBackGroundImage" disabled="true" onclick="set('submit')" value="Submit"></html:button></td>
 <%} %>

<td><html:button property="clear" styleClass="ButtonBackGroundImage" onclick="clearAll()" value="Clear"></html:button></td></tr>
</core:if>

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