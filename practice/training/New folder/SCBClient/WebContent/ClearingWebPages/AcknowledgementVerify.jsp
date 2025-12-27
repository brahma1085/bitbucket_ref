<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="c"    uri="/WEB-INF/c-rt.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.BranchObject"%>
<%@page import="masterObject.clearing.AckObject"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@page import="java.util.Map" %>
<html>
<head>
<script type="text/javascript">

function getClearingType()
{
	document.forms[0].flag.value="1";
	document.forms[0].submit();
}
function clearAll()
{
		var form_ele=document.forms[0].elements;
		
		var val = document.forms[0].validateFlag.value;
		
	
		
		for(var i=0;i<form_ele.length;i++)
		{
			if(form_ele[i].type=='text')
				form_ele[i].value = "";
		}
		document.forms[0].validateFlag.value=val;
}

 function onlynumbers()
 {
    var cha = event.keyCode;
    if(cha >= 48 && cha <= 57) 
    {
       return true;
    } 
    else 
    {
       return false;
    }
			        
 }

function getMessages()
{
	
	if(document.forms[0].errorFlag.value=='1')
	{
		clearAll();
	}

}
function set(target)
{
   if(document.forms[0].ackNumber.value=="")
   {
   		document.forms[0].validateFlag.value="Enter Valid Acknowledgement Number";
   }
   else if(document.forms[0].ackNumber.value==0)
   {
   		 document.forms[0].validateFlag.value="Enter Valid Acknowledgement Number";
   }
   else
   {
        document.forms[0].flag.value=target;
        document.forms[0].booleanFlag.value=1;
        document.forms[0].submit();
   }
           
};

function setFlag(flagVal)
{
	
	if(document.forms[0].ackNumber.value!=0)
	{
		document.forms[0].flag.value=flagVal;
		document.forms[0].booleanFlag.value=1;
		document.forms[0].submit();
	}
	else
	{
		document.forms[0].validateFlag.value="Enter Valid Acknowledgement Number";
 	}

}

</script>
</head>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />
 
<body class="Mainbody"  onload="getMessages()">
<center><h2 class="h2"><bean:message key="label.AcknowledgementVerify"></bean:message></h2></center>



<%!
String access;
String  accesspageId;
 Map user_role;

%>


<%

  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");

	if(user_role!=null&&accesspageId!=null)
	{
			if(user_role.get(accesspageId)!=null)
			{
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}
	}
%>

// Note:access=1111==read,insert,delete,update


 
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Clearing/ackDataEntryLink?pageId=7052">
<%! BranchObject[] branchObjects;
	AckObject ackObject;
%>
<% branchObjects=(BranchObject[])request.getAttribute("sourceNums");
	ackObject=(AckObject)request.getAttribute("ackObject");%>



<table>
	<tr>
		<td>
			<html:text property="validateFlag" readonly="true" size="100" styleClass="formTextField" style="color:red;font-family:bold;"></html:text>
		</td>
	</tr>
	<tr>
		
	<td>
	
	<table class="txtTable">

	<tr><td><bean:message key="label.ack"></bean:message></td>
	<td><html:text property="ackNumber" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent" onblur="setFlag('frmAccountNumber')"> </html:text></td>
	</tr>
	<%if(ackObject==null){ %>
	<tr><td><bean:message key="label.ack_date"></bean:message></td>
	<td><html:text property="ackDate"  readonly="true"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
	</tr>

	<tr><td><bean:message key="label.source"></bean:message></td>
	<td><html:text property="source"  styleClass="formTextFieldWithoutTransparent"></html:text>
	</td>
	<td><html:text property="bname" styleClass="formTextField" readonly="true"></html:text></td>
	</tr>

	<tr><td><bean:message key="label.clgType"></bean:message></td>
	<td><html:text property="clgType" styleClass="formTextFieldWithoutTransparent"></html:text></td> 
	</tr>


	<tr><td><bean:message key="label.amount"></bean:message></td>
	<td><html:text property="amount" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
	</tr>



	<tr>
	<td>
		<html:button property="verifybutton" styleClass="ButtonBackgroundImage" onclick="set('submit')"> <bean:message key="label.verify"></bean:message> </html:button>
		<html:button property="clearbutton"  styleClass="ButtonBackgroundImage" onclick="clearAll()"> <bean:message key="label.clear"></bean:message> </html:button>
	</td>
	</tr>                             


<%}else{ %>


	<tr><td><bean:message key="label.ack_date"></bean:message></td>
	<td><html:text property="ackDate" value="<%=ackObject.getAckDate()%>" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
	</tr>

	<tr><td><bean:message key="label.source"></bean:message></td>
	<td><html:text property="source" styleClass="formTextFieldWithoutTransparent" readonly="true" value="<%=""+ackObject.getDocSource()%>" ></html:text> </td>
	</tr>



	<tr><td><bean:message key="label.clgType"></bean:message></td>
	<td><html:text property="clgType" styleClass="formTextFieldWithoutTransparent" readonly="true" value="<%=""+ackObject.getClg_type()%>" ></html:text>
	 </td>
	</tr>


	<tr><td><bean:message key="label.amount"></bean:message></td>
	<td><html:text property="amount" styleClass="formTextFieldWithoutTransparent" readonly="true" value="<%=""+ackObject.getTotal() %>"></html:text></td>
	</tr>



	<tr><td>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
            <html:button property="verifybutton" styleClass="ButtonBackgroundImage" onclick="set('submit')"> <bean:message key="label.verify"></bean:message> </html:button>
             <%}else{ %>
             <html:button property="verifybutton" disabled="true" styleClass="ButtonBackgroundImage" onclick="set('submit')"> <bean:message key="label.verify"></bean:message> </html:button>
             <%} %>
	
	
	<html:button property="clearbutton"  styleClass="ButtonBackgroundImage" onclick="clearAll()"> <bean:message key="label.clear"></bean:message> </html:button>

	</td></tr>                             

	<%} %>

	</table>
	</td>	
	</tr>
	
	<tr>
		<display:table name="ackChqDetails" id="ackChqDetails" class="its">
			<display:column title="ControlNo"><c:out value="${ackChqDetails.controlNo}"></c:out></display:column>
			<display:column title="SourceBank"><c:out value="${ackChqDetails.SourceName}"></c:out></display:column>
			<display:column title="DestinationBank"><c:out value="${ackChqDetails.DestName}"></c:out></display:column>
			<display:column title="BankName"><c:out value="${ackChqDetails.BankName}"></c:out></display:column>
			<display:column title="DocTotal"><c:out value="${ackChqDetails.docTotal}"></c:out></display:column>
		</display:table>
	</tr>
	
	</table>
	<html:hidden property="flag"></html:hidden>
	<html:hidden property="booleanFlag"></html:hidden>
	<html:hidden property="errorFlag"></html:hidden>


</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>