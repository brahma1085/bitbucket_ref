<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/c-rt.tld" prefix="core"%>

<%@page import="masterObject.clearing.AckObject"%>
<%@page import="java.util.Map"%>
<html>
<head>
<script type="text/javascript">

function setFlag(flagValue)
{	
		document.forms[0].flag.value=flagValue;
		document.forms[0].booleanFlag.value=0;
		document.forms[0].submit();
}

function clearAll()
{
	
		var form_ele = document.forms[0].elements;
		
		var val = document.forms[0].validateMsg.value;
		
		for(var i=0;i<form_ele.length;i++)
		{
			if(form_ele[i].type=="text")
				form_ele[i].value = "";
		}
		
	document.forms[0].validateMsg.value=val;
}
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

function setAlert(flagValue)
{	
	if(document.forms[0].ackNum.value=='')
	{
		document.forms[0].validateMsg.value= "Ack Num Can't Be Blank";
	}
	else if(document.forms[0].bundleType.value==0)
	{
		document.forms[0].validateMsg.value= "Bundled Type Can't be Blank";
	}
	else if(document.forms[0].destinationBranch.value==0)
	{
		document.forms[0].validateMsg.value = "Enter Destination Branch";
		document.forms[0].destinationBranch.focus();
	}
	else if(document.forms[0].document.value==0)
	{
		document.forms[0].validateMsg.value= "Enter Number of Documents";
		document.forms[0].document.focus();
	}
	else if(document.forms[0].totalAmount.value=="")
	{
		document.forms[0].validateMsg.value = "Enter Total Amount";
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
		document.forms[0].booleanFlag.value=0;
		document.forms[0].submit();
	}
	else
	{
		document.forms[0].validateMsg.value = "Enter Valid Acknowledgement Number";
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
function set(flagValue)
{
	if(document.forms[0].controlNum.value=="")
	{
		document.forms[0].validateMsg.value= "Control Number Can't Be Blank";
		
	}
	else if(document.forms[0].controlNum.value==0)
	{
		document.forms[0].validateMsg.value= "Enter Valid Control Number";
		
	}
	else
	{
			
				document.forms[0].flag.value=flagValue;
				document.forms[0].submit();
				return true
			
	}	
	
}
function onlyDoublenumbers()
{
        	
        	var cha = event.keyCode;

            if((cha >= 48 && cha <= 57 )|| cha == 46) 
            {
            		return true;
          	} 
        	else 
        	{
        			return false;
        	}
			        
}
function setSubmit(flagValue)
{

	
		if(document.forms[0].destinationBranch.value==9999)
		{
			setBankCode(flagValue);
		}
		else if(document.forms[0].controlNum.value!=0)
		{
			document.forms[0].validateMsg.value = "Enter Zero In Control Number Field";
		}
		else if(document.forms[0].controlNum.value=="")
		{
			document.forms[0].validateMsg.value = "Enter Zero In Control Number Field";
		}
		else
		{
				setAlert(flagValue);
		}
		
	
}

function setBankCode(flagValue)
{

	if(document.forms[0].cityCode.value==000)
	{
		document.forms[0].validateMsg.value= "Enter Valid City Code";
	}
	else if(document.forms[0].bankCode.value==000)
	{
		document.forms[0].validateMsg.value= "Enter Valid Bank Code" ;
	}
	else if(document.forms[0].branchCode.value==000)
	{
		document.forms[0].validateMsg.value= "Enter Valid Branch Code";
	}
	else if(document.forms[0].controlNum.value!=0)
	{
		document.forms[0].validateMsg.value= "Control Number Should Be Zero";
	}
	else if(document.forms[0].controlNum.value=="")
	{
		document.forms[0].validateMsg.value = "Enter Zero In Control Number Field";
	}
	else
	{
			setAlert(flagValue);
	}

}

function submitMICR(id)
{
 	
 		if(document.forms[0].cityCode.value.length != 3)
 		{
				document.forms[0].validateMsg.value= "Enter the City Code" ;
				document.forms[0].cityCode.focus();
				return false;
		} 
		else if(document.forms[0].cityCode.value == '000')
 		{
				document.forms[0].validateMsg.value = "Enter Valid City Code" ;
				document.forms[0].cityCode.focus();
				return false;
		} 
		else if(document.forms[0].bankCode.value.length != 3)
		{
				document.forms[0].validateMsg.value= "Enter the Bank Code" ;
				document.forms[0].bankCode.focus();
				return false;
		}
		else if(document.forms[0].bankCode.value == '000')
		{
				document.forms[0].validateMsg.value= "Enter Valid Bank Code";
				document.forms[0].bankCode.focus();
				return false;
		} 
		else if(document.forms[0].branchCode.value.length != 3 )
		{
				document.forms[0].validateMsg.value= "Enter the Branch Code";
				document.forms[0].branchCode.focus();
				return false;
		}
		else if(document.forms[0].branchCode.value.length == '000')
		{
				document.forms[0].validateMsg.value="Enter the Branch Code";
				document.forms[0].branchCode.focus();
				return false;
		}  
		else 
		{                              
				document.forms[0].flag.value ='frmBankCode';
			 	document.forms[0].submit();				
				return true;
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
			document.forms[0].validateMsg.value="Can't Update Data";
		}
	
}

  function numbersonly(input)
 {
		    var cha = event.keyCode;

            if(cha>= 48 && cha<= 57) 
            {
					 if(input.value.length >= 2)  
					 {
							if(input == document.getElementById("branch_code"))
							{
								if(input.value.length == 2)
									input.value = input.value + String.fromCharCode(cha);
								return false ;
							}
							else 
							{			 	
								input.value = input.value + String.fromCharCode(cha);
					 			var j = getnextFeild(input);
                				input.form[j].focus();
                				return false ;
                			}
					 
					}	
                    return true;
              } 
              else 
              {
	             return false;
		      }
 }

 function getnextFeild(input)
 {
			var index  = -1;
			var i = 0 ;
			while(i< input.form.length  && index == -1)   
			{
				if(input.form[i] == input) 
				{
					index = i + 1 ;
					return index;
				} 
				else 
				{
					  i++;
				}
			}
			
		return index;
 }
 function clearfeild(ids)
 {
			document.getElementById(ids).value= "";
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

<center><h2 class="h2"><i><bean:message key="label.BundledDataEntry"></bean:message> </i></h2></center>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Clearing/BundledDataEntrylink?pageId=7016">

<table>
	<tr><html:text property="validateMsg" style="color:red;font-family:bold;" readonly="true" size="100" styleClass="formTextField"></html:text></tr>
	<tr><td>
	<table class="txtTable">

	<%! AckObject ackObject; %>
	<% ackObject=(AckObject)request.getAttribute("ackobject");%>

<tr>
<td><bean:message key="label.controlno"></bean:message> </td>
<td><html:text property="controlNum" onblur="setFlag('frmCntrlNum')" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
</tr>


<tr>
<td><bean:message key="label.ack"></bean:message> </td>
<td><html:text property="ackNum" styleClass="formTextFieldWithoutTransparent" onblur="setAckNum('frmAckNum')" onkeypress="return onlynumbers()"></html:text></td>
<td><html:text property="ackName" readonly="true" styleClass="formTextField"></html:text></td>
</tr>

<tr>
<td><bean:message key="label.bundleType"></bean:message> </td>
<td><html:text property="bundleType" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
</tr>

<tr>
<td><bean:message key="label.bankCodeName"></bean:message> </td>
<%if(ackObject!=null){ 
	if((ackObject.getDocSource()%1111)==0){
%>
<td><html:text property="cityCode" size="4" readonly="true" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()"></html:text>-<html:text property="bankCode"  size="4" onkeypress="return onlynumbers()" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>-<html:text property="branchCode" size="4"  onkeypress="return onlynumbers()"  readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
<%} 
	else{
%>
<td><html:text property="cityCode" size="4" styleId="city_code" onfocus="clearfeild(this.id)" onkeypress="return numbersonly(this)" styleClass="formTextFieldWithoutTransparent"></html:text>-<html:text property="bankCode" onfocus="clearfeild(this.id)" size="4" styleId="bank_code" onkeypress="return numbersonly(this)" styleClass="formTextFieldWithoutTransparent"></html:text>-<html:text property="branchCode" size="4" onfocus="clearfeild(this.id)" onkeypress="return numbersonly(this)" styleId="branch_code" onblur="return submitMICR(this)"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
<%} %>
<%}
 else{
%>
<td><html:text property="cityCode" size="4" styleId="city_code" onfocus="clearfeild(this.id)" styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly(this)"></html:text>-<html:text property="bankCode"  size="4" onkeypress="return numbersonly(this)" styleId="bank_code" onfocus="clearfeild(this.id)" styleClass="formTextFieldWithoutTransparent"></html:text>-<html:text property="branchCode" size="4"  onkeypress="return numbersonly(this)" onblur="return submitMICR(this)" styleId="branch_code" onfocus="clearfeild(this.id)"  styleClass="formTextFieldWithoutTransparent"></html:text></td>
<%} %> 
<td><html:text property="bankName" styleClass="formTextField" readonly="true"></html:text></td>
</tr>


<tr>
<td><bean:message key="label.destinationBranch"></bean:message> </td>
<td><html:text property="destinationBranch" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()" onblur="setFlag('DestBranch')"></html:text></td>
<td><html:text property="destName" styleClass="formTextField" readonly="true"></html:text></td>
</tr>

<tr>
<td><bean:message key="label.Document"></bean:message> </td>
<td><html:text property="document" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()"></html:text></td>
</tr>

<tr>
<td><bean:message key="label.amount"></bean:message> </td>
<td><html:text property="totalAmount" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlyDoublenumbers()" onblur="setAlert('frmAmount')"></html:text></td>
</tr>

<tr>
<td><bean:message key="label.totalAmount"></bean:message> </td>
<td><html:text property="amount" styleClass="formTextField" readonly="true"></html:text></td>
</tr>

<tr>
<td><bean:message key="label.remAckAmount"></bean:message> </td>
<td><html:text property="remAckAmount" styleClass="formTextField" readonly="true"></html:text></td>
</tr>

<tr>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
             <td><html:button property="buttonSubmit" value="Submit" onclick="setSubmit('frmButton')" styleClass="ButtonBackgroundImage"></html:button>
             <%}else{ %>
             <td><html:button property="buttonSubmit" disabled="true" value="Submit" onclick="setSubmit('frmButton')" styleClass="ButtonBackgroundImage"></html:button>
             <%} %>
		
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
           <html:button property="buttonUpdate" value="Update" onclick="setUpdate('frmButton')" styleClass="ButtonBackgroundImage"></html:button>
             <%}else{ %>
            <html:button property="buttonUpdate" value="Update" onclick="setUpdate('frmButton')" styleClass="ButtonBackgroundImage"></html:button>
             <%} %>
			
		 </td>
		 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
            <td> <html:button property="buttonDelete" value="Delete" onclick="set('frmDelete')" styleClass="ButtonBackgroundImage"></html:button>
             <%}else{ %>
             <td> <html:button property="buttonDelete" disabled="true" value="Delete" onclick="set('frmDelete')" styleClass="ButtonBackgroundImage"></html:button>
             <%} %>
		 
		
        	<html:button property="buttonClear"  value="Clear" onclick="clearAll()" styleClass="ButtonBackgroundImage"></html:button>
       </td>
</tr>

</table>
<html:hidden property="validateFlag"></html:hidden>
<html:hidden property="booleanFlag"></html:hidden>
<html:hidden property="errorFlag"></html:hidden>
<html:hidden property="flag"></html:hidden>
</td>
</tr>
</table>
</html:form>

<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>


</body>
</html>