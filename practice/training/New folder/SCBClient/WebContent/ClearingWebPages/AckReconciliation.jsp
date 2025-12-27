<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@ taglib prefix="core"  uri="/WEB-INF/c-rt.tld"%>
<%@page import="java.util.ArrayList"%>
<%@page import="masterObject.clearing.ChequeAckObject"%>
<%@page import="java.util.Map"%>
<%@page import="masterObject.clearing.AckObject;"%>
<html>
<head>
<script type="text/javascript">

function sendValue()
{

document.forms[0].flag.value="fromAckNum";
document.forms[0].submit();
}

function setFlag(flagValue)
{

	document.forms[0].flag.value=flagValue;
	var conVar;

	if(flagValue=='submit')
	{
		document.forms[0].submit();
	}
	

}


function getAlertMessages()
{
	if(document.forms[0].validateFlag!="")
	{
	
		var val = document.forms[0].validateFlag.value;
	
		if(document.forms[0].validateFlag.value=="getNewTable")
		{
			document.forms[0].flag.value="getNewTable";
			document.forms[0].submit();
		}
		else if(document.forms[0].validateFlag.value=="buildNewTable")
		{
			buildNewTable();
		}
		else
		{
			document.forms[0].validateFlag.value = val;
		}		
	}


}



function buildNewTable()
{
	<%ChequeAckObject chequeAckObject[]=(ChequeAckObject[])request.getAttribute("chequeAckObject"); %>
	<%if(chequeAckObject!=null){ %>
	div_ele=document.getElementById("tabs");
	var trs="";
	

	
	<%	
	
	for(int i=0;i<chequeAckObject.length;i++){%>
		
		trs+="<tr><td>ControlNum</td><td>No Of Docu</td><td>Doc Total</td><td>CreditAcNum</td><td>CreditAcTyp</td><td>Cheque Num</td></tr><tr><td><input type = 'text' size = 8 value = '" + <%=chequeAckObject[i].getControlNo() %> + "'></td>  <td><input type = 'text' size = 10 value = '" + <%=chequeAckObject[i].getNoOfDocs() %> + "'></td>   <td><input type = 'text' size = 8 value = '" + <%=chequeAckObject[i].getDocTotal()%> + "'></td>      <td><input type = 'text' size = 8 value = '" + <%=chequeAckObject[i].getCreditACNo()%> + "'></td>    <td><input type = 'text' size = 8 value = '" + <%=chequeAckObject[i].getCreditACType()%> + "'></td>   <td><input type = 'text' size = 8 value = '" + <%=chequeAckObject[i].getChequeNo() %> + "'></td> </tr>";  
	
	<% } %>
	div_ele.innerHTML+= "<table>"+ trs + "</table>"
<%} %>

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

</head>

<body class="Mainbody" onload="getAlertMessages()">

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

<center><h2 class="h2">Acknowledgement Reconciliation </h2></center>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Clearing/AckReconciliationlink?pageId=7017">

<table>
	<tr><html:text property="validateFlag" size="100" readonly="true" style="color:red;font-family:bold;" styleClass="formTextField"></html:text></tr>
	<tr>
	<td>
	<table>
	<%AckObject ackObject[]= (AckObject[])request.getAttribute("details");  %>

	<core:if test="${requestScope.details!=null}">
	<table>
	<tr><td>AckNum</td><td>AckDate</td><td>TotalAmount</td><td>Select</td></tr>
	
	<%for(int i=0;i<ackObject.length;i++){ %>
	<tr><td><html:text property="txtAckNum" readonly="true" value="<%=""+ackObject[i].getAckNo() %>"></html:text></td>
	<td><html:text property="txtAckDate" readonly="true" value="<%=""+ackObject[i].getAckDate()%>"></html:text></td>
	<td><html:text property="txtTotAmount" readonly="true" value="<%=""+ackObject[i].getTotal()%>"></html:text></td>
	<td><html:radio property="chkBox" value="<%=""+i %>"/></td>
	</tr>
	<%} %>
	
	</table>
 
	</core:if>
	</table>
	</td>
	</tr>
	<tr>
	<td>
	<div id="tabs">

	</div>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
            <html:button property="submitbutton" onclick="setFlag('submit')"  styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message></html:button>
             <%}else{ %>
            <html:button property="submitbutton" disabled="true" onclick="setFlag('submit')"  styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message></html:button>
             <%} %>


</td>
</tr>
</table>

<html:hidden property="flag"></html:hidden>



</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>