<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@ page import="masterObject.clearing.CompanyMaster" %>

<%@page import="masterObject.general.ModuleObject"%>
<%@page import="java.util.Map "%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CombinedChequeEntry</title>
<script type="text/javascript">
  

 function onlynumbers()
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
  
function setFlag(flagVal)
{
		if(document.forms[0].ctrlno.value!=0)
		{
 			if(flagVal=='frmSubmit')
 			{
 				if(document.forms[0].totamount.value!=0)
 					{	
 						document.forms[0].flag.value=flagVal;
 						document.forms[0].booleanFlag.value=0;
 						document.forms[0].formflag.value=2;
 						document.forms[0].submit();
 					}
 					else
 					{
 						document.forms[0].validateFlag.value="Enter Total Amount";
 					}	
 			}
 			else if(flagVal=='frmUpdate')
 			{
 				if(document.forms[0].totamount.value!=0)
 				{
 						document.forms[0].flag.value='frmSubmit';
 						document.forms[0].booleanFlag.value=0;
 						document.forms[0].formflag.value=2;
 						document.forms[0].submit();
 				}
 				else
 				{
 						document.forms[0].validateFlag.value="Enter Total Amount";
 				}
 			}
 			else 
 			{
 				
 				document.forms[0].submit();
 			}	
 		}
 		else  
 		{
 		 document.forms[0].validateFlag.value="Enter Valid ControlNumber";
 		}	
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

function clearAll()
{
	
		var form_ele = document.forms[0].elements;
		
		var val = document.getElementById("ctrl_no").value;
		
		for(var i=0;i<form_ele.length;i++)
		{
			if(form_ele[i].type=='text')
				form_ele[i].value = "";
		}
		return false;
}
 
function addRowToTable(flagVal)
{
	if(document.forms[0].ctrlno.value!=0)
	{
		document.forms[0].flag.value=flagVal;
		document.forms[0].submit();
	}
	else
	{
		document.forms[0].validateFlag.value="Enter Valid Control Number";
	}
		
}

function removeRowFromTable(flagVal)
{
	document.forms[0].flag.value=flagVal;
	document.forms[0].submit();
}

function onlyDoublenumbers()
{
        	
        	var cha = event.keyCode;

            if((cha>=48 && cha<=57)||cha == 46) 
            {
            		return true;
        	}
        	else 
        	{
        			return false;
        	}
			        
}

function set(flagVal)
{
	if(document.forms[0].accNum.value!=0)
	{	
		document.forms[0].flag.value=flagVal;
		document.forms[0].submit();
	}
	else
	{
		document.forms[0].validateFlag.value="Enter Valid Account Number";
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
<%! CompanyMaster[] companyMasters;
	String add;
	ModuleObject moduleObject[];%>
	
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
<%add=(String)request.getAttribute("addrow"); 
moduleObject=(ModuleObject[])request.getAttribute("Main_Module_code");%>
<center><h2 class="h2">CombinedCheque</h2></center>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Clearing/CombinedChequeLink?pageId=7002">

<html:hidden property="booleanFlag"/>
<html:hidden property="formflag"/>
<html:hidden property="pageId"/>
<html:hidden property="flag"/>

<table>
	
	<tr><html:text property="validateFlag" styleClass="formTextField" style="color:red;font-family:bold;" size="100"/></tr>
	<tr>
	<td>
	<table>
	<tr>
	<td><bean:message key="label.controlno"/></td>
	<td><html:text property="ctrlno" styleId="ctrl_no" onkeypress="return onlynumbers()" onblur="setFlag('frmCtrlNum')" size="5" styleClass="formTextFieldWithoutTransparent"></html:text></td>
	<td><bean:message key="label.totalAmount"/></td>
	<td><html:text property="totamount" readonly="true" onkeypress="return onlyDoublenumbers()" styleClass="formTextFieldWithoutTransparent" size="5" ></html:text></td>
	<td><bean:message key="label.selectAll"></bean:message></td>
	<td><input type="checkbox" name="selectAll"  onclick="setChkBox()"></td>
	
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
   	<td><html:button property="submitme" value="Submit" styleClass="ButtonBackGroundImage" onclick="setFlag('frmSubmit')"></html:button></td>
    <%}else{ %>
    <td><html:button property="submitme" value="Submit" styleClass="ButtonBackGroundImage" disabled="true" onclick="setFlag('frmSubmit')"></html:button></td>
    <%} %>
	
	<td><html:button property="clear" value="Clear" styleClass="ButtonBackGroundImage" onclick="clearAll()"></html:button></td>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
    <td><html:button property="update" value="Update" styleClass="ButtonBackGroundImage" onclick="setFlag('frmUpdate')"></html:button></td>
    <%}else{ %>
   	<td><html:button property="update" value="Update" disabled="true" styleClass="ButtonBackGroundImage" onclick="setFlag('frmUpdate')"></html:button></td>
    <%} %>
	
	<td><html:button property="addrow" value="AddRow" styleClass="ButtonBackGroundImage" onclick="addRowToTable('addRow')"></html:button></td>
	</tr>
	</table>
		<% companyMasters=(CompanyMaster[])request.getAttribute("chqdetails");
		if(companyMasters!=null){ %>
	<tr>
	<td>
	<table id="tblSample" border="1">
		<tr><td>A/cType</td><td>A/cNumber</td><td>Amount</td><td>LoanA/cType</td><td>LoanA/cNumber</td><td>Select</td></tr>
		<%
		System.out.println("lenth compas master=in jsp=>"+companyMasters.length);
		for(int i=0;i<companyMasters.length;i++){ %>
		<tr>
			<%for(int k=0;k<moduleObject.length;k++){ %>
				<%if(moduleObject[k].getModuleCode().equalsIgnoreCase(companyMasters[i].getAccType())){ %>
				<td><html:text property="accTyp" size="8" styleId="accTyp" value="<%=moduleObject[k].getModuleAbbrv()%>"></html:text></td>		
		
			<%} }%>		
		
		<td><html:text property="accNum" size="8" value="<%=String.valueOf(companyMasters[i].getAccNo())%>" ></html:text></td>
		<td><html:text property="amount" size="10" value="<%=String.valueOf(companyMasters[i].getDeTml())%>"></html:text></td>
		<td><html:text property="loanActyp" size="10" value="<%=String.valueOf(companyMasters[i].getLoanAccType())%>"></html:text></td>
		<td><html:text property="loanAcnum" size="10" value="<%=String.valueOf(companyMasters[i].getLoanAccNo())%>"></html:text></td>
		<td><html:checkbox property="chkBox" value="<%=String.valueOf(i)%>"></html:checkbox></td>
		</tr>
		
		<% } %>
		<%  
		if(add!=null){ %>
		<tr>
		<td><html:select  property="accTyp" styleClass="formTextFieldWithoutTransparent" styleId="accTyp">
			<html:option value="select">select</html:option>
			<core:forEach var="module"  varStatus="count" items="${requestScope.Main_Module_code}" >
			<html:option value="${module.moduleAbbrv}"><core:out value="${module.moduleAbbrv}"></core:out></html:option>
			</core:forEach>
			</html:select>
		</td>
		<td><html:text property="accNum" size="8" value="0" ></html:text></td>
		<td><html:text property="amount" size="10" value="0.0"></html:text></td>
		<td><html:select property="loanActyp">
			<html:option value="select">select</html:option>
			<core:forEach var="module"  varStatus="count" items="${requestScope.Loan_Module_code}" >
			<html:option value="${module.moduleAbbrv}"><core:out value="${module.moduleAbbrv}"></core:out></html:option>
			</core:forEach>
			</html:select></td>
		<td><html:text property="loanAcnum" size="10" value="0"></html:text></td>
		<td><input type="checkbox" name="chkBox" value="<%=String.valueOf((companyMasters.length))%>"/></td>
		</tr>
		<%	} %>
		</table>
		</td>
		</tr>
		<% } %>
	
	</table>
	
	

</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>