<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="/WEB-INF/c-rt.tld" prefix="core" %>

<%@page import="masterObject.clearing.ClearingObject"%>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
function setFlag(flagVal)
{
	document.forms[0].flag.value=flagVal;
	document.forms[0].submit();
}

function clearAll()
{
	
		var form_ele = document.forms[0].elements;
		for ( var i = 0 ; i< form_ele.length; i++ )
		{
			if(form_ele[i].type=="text")
			form_ele[i].value = "";
		}
		return false;
	}



function setChkBox()
{
	
	var v=document.forms[0].chkBox;
	
	if(document.forms[0].selectAll.checked)
	{
	  for(var i=0;i<v.length;i++)
	  {
		v[i].checked=true;
	  }
	}
	else
	{
		for(var i=0;i<v.length;i++)
	  	{
			v[i].checked=false;
	  	}
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
<body class="MainBody">

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
<html:form action="/Clearing/ClearingPostLink?pageId=7010" styleId="kk">
<center><h2 class="h2">ClearingPost</h2></center>



<table>
	
	<tr><html:text property="validateFlag" styleClass="formTextField" style="color:red;font-family:bold;" size="100"></html:text></tr>
	
	<tr>
	<td>
	<table>
	<tr>
	<td><bean:message key="label.clr_date"></bean:message></td>
	<td><html:select property="clrDate" styleClass="formTextFieldWithoutTransparent" onblur="setFlag('clearingDate')">
	<core:forEach var="arrayClearingObject" items="${requestScope.arrayClearingObject}" >
		<html:option value="${arrayClearingObject.clgDate}" ><core:out value="${arrayClearingObject.clgDate}"/></html:option>
	</core:forEach>
	</html:select></td>
	</tr>
	<tr>
	<td><bean:message key="label.clearingBank" ></bean:message></td>
	<td><html:select property="clrBank"   styleClass="formTextFieldWithoutTransparent" onblur="setFlag('clearingBank')">
		<core:forEach var="arrayClrBank" items="${requestScope.arrayClrBank}" >
		<html:option value="${arrayClrBank}" ><core:out value="${arrayClrBank}"/></html:option>
		</core:forEach>
		</html:select>
	</td>
	</tr>
	<tr>
	<td><bean:message key="label.clr_no"></bean:message></td>
	<td><html:select property="clrNum"  styleClass="formTextFieldWithoutTransparent" onblur="setFlag('clrNum')">
		<core:forEach var="arrayClrNum" items="${requestScope.arrayClrNum}" >
		<html:option value="${arrayClrNum}" ><core:out value="${arrayClrNum}"/></html:option>
		</core:forEach>
		</html:select>
	</td>
	<td><bean:message key="label.selectAll"></bean:message></td>
	<td><html:checkbox property="selectAll" styleClass="formTextFieldWithoutTransparent" onclick="setChkBox()"></html:checkbox></td>
	
	</tr>
	</table>
	</td>
	</tr>
		<% ClearingObject[] clearingObjects=(ClearingObject[])request.getAttribute("arrayTableData"); %>
	<tr>
	<td>
		<%if(clearingObjects!=null){ %>
	<table>
		<tr><td>CtrlNum</td><td>ClgBank</td><td>BankCode</td><td>A/c Type</td><td>A/c No</td><td>TotAmt</td><td>Select</td></tr>
	<%for(int i=0;i<clearingObjects.length;i++){ %>
		<tr>
			<td><html:text property="txtCtrlNum" size="7" value="<%=""+clearingObjects[i].getControlNo()%>"></html:text></td>
			<td><html:text property="txtBankCode" size="7" value="<%=""+clearingObjects[i].getBankCode()%>"></html:text></td>
			<td><html:text property="txtBankName" size="10" value="<%=""+clearingObjects[i].getBankName()%>"></html:text></td>
			<td><html:text property="txtSrcName" size="7" value="<%=""+clearingObjects[i].getCreditACType()%>"></html:text></td>
			<td><html:text property="txtCreAccNum" size="7" value="<%=""+clearingObjects[i].getCreditACNo() %>"></html:text></td>
			<td><html:text property="txtTranAmt" size="7" value="<%=""+clearingObjects[i].getTranAmt() %>"></html:text></td>
			<td><html:checkbox property="chkBox" value="<%=""+(i) %>"></html:checkbox></td>
		</tr>
	<%} %>
	</table>
	</td>
	</tr>
	<tr><td>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
    <html:button property="submitValue" value="Submit" styleClass="ButtonBackGroundImage" onclick="setFlag('frmSubmit')"></html:button>
    <%}else{ %>
   	<html:button property="submitValue" value="Submit" styleClass="ButtonBackGroundImage" disabled="true" onclick="setFlag('frmSubmit')"></html:button>
    <%} %>
	
	
	</td></tr>
	<%} %>

<html:hidden property="pageId"></html:hidden>
<html:hidden property="flag"></html:hidden>
</table>

</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>