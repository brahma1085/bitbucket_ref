<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/c-rt.tld" prefix="core"%>
<%@page import="java.util.Map"%>
<%@page import="masterObject.clearing.AckObject"%>
<html>
<head>
<script type="text/javascript">

function setFlag(flagValue)
{	
		document.forms[0].flag.value=flagValue;
		document.forms[0].booleanFlag.value=1;
		document.forms[0].submit();
}

function onlynumbers()
{
     var cha =   event.keyCode;

     if(cha >= 48 && cha <= 57) 
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
		
		var val = document.forms[0].validateMsg.value;
		
		for ( var i = 0 ; i< form_ele.length; i++ )
		{
			if(form_ele[i].type=="text")
				form_ele[i].value = "";
		
		}
		 document.forms[0].validateMsg.value=val;
}


function setAlert(flagValue)
{
	if(document.forms[0].bundleType.value==0)
	{
		document.forms[0].validateFlag.value="Bundled Type Can't be Blank";
	}
	else if(document.forms[0].destinationBranch.value==0)
	{
		document.forms[0].validateFlag.value="Enter Destination Branch";
		document.forms[0].destinationBranch.focus();
		
	}
	else if(document.forms[0].document.value==0)
	{
		document.forms[0].validateFlag.value="Enter No of Documents";
		document.forms[0].document.focus();
	}
	else if(document.forms[0].totalAmount.value=="")
	{
		document.forms[0].validateFlag.value="Enter Total Amount";
	}
	else
	{
		setAckNum(flagValue);
	}
	

}

function setAckNum(flagValue)
{
	if(document.forms[0].ackNum.value!=0)
	{
		document.forms[0].flag.value=flagValue;
		document.forms[0].booleanFlag.value=1;
		document.forms[0].submit();
	}
	else
	{
		document.forms[0].validateFlag.value="Enter Valid Ack Num";
		document.forms[0].ackNum.focus();
	}

}


function getAlertMessages()
{

	if(document.forms[0].errorFlag.value=="1")
	{
		clearAll();
	}
	
}


function setSubmit(flagValue)
{

		
		if(document.forms[0].controlNum.value==0)
		{
			document.forms[0].validateFlag.value="Enter Valid Control Num";
		}
		else
		{
			setAlert(flagValue);
			
		}
	
}

function setUpdate(flagValue)
{

	
		
		if(document.forms[0].controlNum.value!=0)
		{
			document.forms[0].flag.value=flagValue;
			setAlert(flagValue);
		}
		else
		{
			document.forms[0].validateFlag.value="Can't Update Data";
		}
	
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
<body class="Mainbody" onload="getAlertMessages()">

<center><h2 class="h2"><i><bean:message key="label.BundledEntryVerify"></bean:message> </i></h2></center>



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
<html:form action="/Clearing/BundledDataEntrylink?pageId=7054">

<table>
<tr><html:text property="validateMsg" style="color:red;font-family:bold;" readonly="true" size="100"  styleClass="formTextField"></html:text></tr>

<tr><td>
<table class="txtTable">

<%! AckObject ackObject; %>
<% ackObject=(AckObject)request.getAttribute("ackobject"); %>



<tr>
<td><bean:message key="label.controlno"></bean:message> </td>
<td><html:text property="controlNum" onblur="setFlag('frmCntrlNum')" onkeypress="return onlynumbers()"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
</tr>


<tr>
<td><bean:message key="label.ack"></bean:message> </td>
<td><html:text property="ackNum" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
<td><html:text property="ackName" readonly="true" styleClass="formTextField"></html:text></td>
</tr>

<tr>
<td><bean:message key="label.bundleType"></bean:message> </td>
<td><html:text property="bundleType" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
</tr>

<tr>
<td><bean:message key="label.bankCodeName"></bean:message> </td>
<td><html:text property="cityCode" size="3" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>-<html:text property="bankCode" readonly="true" size="3" styleClass="formTextFieldWithoutTransparent"></html:text>-<html:text property="branchCode" size="3" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
<td><html:text property="bankName" styleClass="formTextField" readonly="true"></html:text></td>
</tr>


<tr>
<td><bean:message key="label.destinationBranch"></bean:message> </td>
<td><html:text property="destinationBranch" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
<td><html:text property="destName" styleClass="formTextField" readonly="true"></html:text></td>
</tr>

<tr>
<td><bean:message key="label.Document"></bean:message> </td>
<td><html:text property="document" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
</tr>

<tr>
<td><bean:message key="label.totalAmount"></bean:message> </td>
<td><html:text property="totalAmount" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
</tr>

<tr>
<td><bean:message key="label.amount"></bean:message> </td>
<td><html:text property="amount" styleClass="formTextField" readonly="true"></html:text></td>
</tr>

<tr>
<td><bean:message key="label.remAckAmount"></bean:message> </td>
<td><html:text property="remAckAmount" styleClass="formTextField" readonly="true"></html:text></td>
</tr>

<tr>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             <td><html:button property="buttonSubmit" value="Verify" onclick="setSubmit('frmButton')" styleClass="ButtonBackgroundImage"></html:button>
             <%}else{ %>
             <td><html:button property="buttonSubmit" value="Verify" onclick="setSubmit('frmButton')" styleClass="ButtonBackgroundImage"></html:button>
             <%} %>

		
        	<html:button property="buttonClear"  value="Clear" onclick="clearAll()" styleClass="ButtonBackgroundImage"></html:button>
        </td>
</tr>
</table>

</td>
</tr>
</table>

<html:hidden property="booleanFlag"></html:hidden>
<html:hidden property="errorFlag"></html:hidden>
<html:hidden property="flag"></html:hidden>

</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>


</body>
</html>