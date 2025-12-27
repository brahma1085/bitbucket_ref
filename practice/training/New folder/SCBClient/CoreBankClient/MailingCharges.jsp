<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@page import="java.util.Map"%>

<%@page import="masterObject.clearing.ChequeDepositionObject"%>
<html>
<head>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

function setFlag(flagVal)
{
	document.forms[0].form_flag.value=flagVal;
	document.forms[0].submit();

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

<center><h2 class="h2"><i><bean:message key="label.mailingcharge"></bean:message> </i></h2></center>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Clearing/MailingChargesLink?pageId=7013">

<table>
	<tr>
	<td>
	<table>
		<tr><html:text property="error_message" readonly="true" styleClass="formTextField" style="color:red;font-family:bold;" size="100"></html:text></tr>
	</table>
	</td>
	</tr>
	<tr>
	<td>
	<table>
	<tr>
	<td><bean:message key="label.selectAll"></bean:message></td>
	<td><html:checkbox property="selectAll" onclick="setChkBox()"></html:checkbox></td>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
    <td><html:button property="ClickMe" value="Submit" styleClass="ButtonBackGroundImage" onclick="setFlag('frmButton')"></html:button></td>
    <%}else{ %>
    <td><html:button property="ClickMe" value="Submit" disabled="true" styleClass="ButtonBackGroundImage" onclick="setFlag('frmButton')"></html:button></td>
    <%} %>
	
	</tr>
	</table>
	</td>
	</tr>
	<%ChequeDepositionObject[] chq=(ChequeDepositionObject[])request.getAttribute("ctrlNums"); %>
	<tr>	
	<td>	
			<% if(chq!=null){%>
			<table>
			<tr>
			<td>CtrlNum</td>
			<td>InstrTyp</td>
			<td>InstrNo</td>
			<td>Amount</td>
			<td>CrA/cTyp</td>
			<td>Select</td>
			</tr>
			<% for(int i=0;i<chq.length;i++){ %>
			<tr>
		   <td><html:text property="txtCtrlNum" size ="7" value="<%=""+chq[i].getControlNo()%>"/></td>
		   <td> <html:text property="txtChqDDPO" size="7" value="<%=""+chq[i].getChqDDPO()%>"/></td>
		   <td> <html:text property="txtChqNum" size="7" value="<%=""+chq[i].getQdpNo()%>"/> </td>
	       <td> <html:text property="txtTranAmt" size="7" value="<%=""+chq[i].getTranAmt()%>"/></td> 
		   <td><html:text property="txtCreditAcNum" size="7" value="<%=""+chq[i].getCreditACNo()%>"/></td>
		   <td><html:checkbox property="chkBox"  value="<%=String.valueOf(i)%>"></html:checkbox></td>
		  </tr>
			<% } %>
			</table>
			<% } %>
		</td>
		</tr>	
				<html:hidden property="form_flag" styleId="flagset"></html:hidden>
				<html:hidden property="error_flag" styleId="err_id"></html:hidden>
				
	</table>
				
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>