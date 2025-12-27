<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<%@page import="masterObject.clearing.Reason;"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
function onlyDoublenumbers()
{
     var cha = event.keyCode;
		if ( (cha >= 48 && cha <= 57 )  || cha == 46 )
             {
            	return true;
            } 
        	else 
        	{
        		return false;
        	}
			        
}
 
 
 
 
 
 
  function numbersonly(input)
  {
	 var cha=event.keyCode;
	if(cha >= 48 && cha <= 57)
	 {
		if(input.value.length >= 2)
		  {
			if(input == document.getElementById("branch_code"))
			{
				if (input.value.length == 2 )
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
 
        
function onlynumbers()
{
   var cha =   event.keyCode;

   if ( cha >= 48 && cha <= 57  ) 
     {
        return true;
      } 
    else 
       {
        return false;
        }
			        
}
  
 function clearfeild(ids)
	{
	
		document.getElementById(ids).value= "";
		
	} 
        
function setFlag(flagVal)
{
	if(document.forms[0].ctrlNum.value!=0)
	{
		document.forms[0].flag.value=flagVal;
		document.forms[0].booleanFlag.value=1;
		document.forms[0].submit();
	}
	else
	{
		document.forms[0].validateFlag.value="Enter Valid Control Number";
	}
}
function callBounce()
       {
       
       			if (document.forms[0].returned.checked )
		 		{
					document.getElementById("bounce_fine").style.display = 'block';  
					
				} 
				else 
				{
					document.getElementById("bounce_fine").style.display = 'none';  
					
				}
       		
       	    
       }

function getAlertMessages()
{
	
	if(document.forms[0].errorFlag.value=="1")
	{
		clearAll();
	}
	
	callBounce();
}

function clearAll()
{
	
		var form_ele = document.forms[0].elements;
		
		var val = document.forms[0].validateFlag.value;
		
		for ( var i = 0 ; i< form_ele.length; i++ )
		{
			if(form_ele[i].type=='text')
				form_ele[i].value = "";
		
		}
		
		document.getElementById("bounce_id").checked = false; 
		document.getElementById("prev_ctrlno").value=0;
		document.getElementById("panel_amt").value=0.0;
		document.getElementById("ctrl_no").value = 0;
		document.forms[0].validateFlag.value=val;	
		return false;
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

<center><h2 class="h2"><bean:message key="label.OutStationChequeVerify"/></h2></center>
<html:form  action="/Clearing/OutStationChequeLink?pageId=7056">

<table>
	<tr>
	<td>
	<table>
	<tr><html:text property="validateFlag" readonly="true" styleClass="formTextField" style="color:red;font-family:bold;" size="100"></html:text></tr>
	</table>
	</td>
	</tr>
	<tr>
	<td>
	<table>		
	<tr>
		<td valign="top">	
		<table align="left"> 
		<tr>
		<td><bean:message key="label.controlno"></bean:message></td>
		<td><html:text property="ctrlNum"  onblur="setFlag('frmCtrlNum')" onkeypress="return onlynumbers()" styleId="ctrl_no" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		</tr>
		<tr>
		<td><bean:message key="label.microcde"></bean:message></td>
		<td><html:text property="cityCode" size="3" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text>-<html:text property="bnkCode"  size="5" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>-<html:text property="brhCode" size="3" readonly="true" styleClass="formTextFieldWithoutTransparent"></html:text>
		<html:text property="bnkName" readonly="true" styleClass="formTextField" style="color:blue"></html:text>
		</td>
		</tr>
		<tr>
		<td><bean:message key="label.branch_name"></bean:message></td>
		<td><html:text property="brhName" readonly="true" styleClass="formTextFieldWithoutTransparent"  ></html:text></td>
		</tr>
		<tr>
		<td> <bean:message key="label.acctype"></bean:message></td>
		<td> <html:select property="crAccTyp"  styleClass="formTextFieldWithoutTransparent" styleId="credit_acc_type"  >
				<html:option value="select">select</html:option>
				<core:forEach var="module"  varStatus="count" items="${requestScope.Main_Module_code}" >
				<html:option value="${module.moduleCode}"><core:out value="${module.moduleAbbrv}"></core:out></html:option>
				</core:forEach>
				</html:select>
		 </td>
		 </tr>						 
		 <tr>
		 <td> <bean:message key="label.creditAcNum"></bean:message></td>
		 <td><html:text property="crAccNum" readonly="true"  styleClass="formTextFieldWithoutTransparent" size="10" styleId="credit_acc_no" ></html:text>	</td>
		 </tr>
		 <tr>
		 <td><bean:message key="label.amount"/></td>
		 <td><html:text property="amount" readonly="true" size="10" styleClass="formTextFieldWithoutTransparent" styleId="amt" ></html:text></td>
		 </tr>
		
		 <tr>
		 <td><bean:message key="label.chqdate"/></td>
		 <td><html:text property="chqDate" readonly="true" size="10" styleClass="formTextFieldWithoutTransparent"   styleId="chqdate" /></td>
		 </tr>
		 <tr>
		 <td><bean:message key="label.return"></bean:message></td>
		 <td><html:checkbox  property="returned" styleId="bounce_id" styleClass="formTextFieldWithoutTransparent" onclick="callBounce()"></html:checkbox></td>
		</tr>
		
		
		<tr>
		<td><bean:message key="label.bounceFine"></bean:message></td>
		<td><html:text property="bounceFine" styleClass="formTextField" readonly="true"></html:text></td>
		</tr>
		<tr>
		 <td><html:button property="verify" onclick="setFlag('frmUpdate')" styleClass="ButtonBackGroundImage" value="Verify"></html:button>
		 <html:button property="clear" onclick="clearAll()" styleClass="ButtonBackGroundImage" value="Clear"></html:button></td>
		 </tr>
		</table>
		</td>
		<td valign="top">
		
		
				<html:hidden property="pageId" ></html:hidden>
				<html:hidden property="flag"/>
				<html:hidden property="booleanFlag"></html:hidden>
				<html:hidden property="errorFlag"></html:hidden>
				
				<html:hidden property="form_flag"></html:hidden>
				
		<table   style="border:thin solid #000000;">
			<tr>
				<td><bean:message key="label.microcde"></bean:message></td>
				<td><html:text property="panelCityCode" readonly="true" onkeypress="return numbersonly(this)" onfocus="clearfeild(this.id)" styleId="panel_city_code" size="3" styleClass="formTextFieldWithoutTransparent"></html:text>-<html:text property="panelBnkCode" readonly="true" onkeypress="return numbersonly(this)" onfocus="clearfeild(this.id)" styleId="panel_bank_code" size="3"  styleClass="formTextFieldWithoutTransparent"></html:text>-<html:text property="panelBrhCode" size="3" readonly="true" styleId="panel_branch_code" onkeypress="return numbersonly(this)" onfocus="clearfeild(this.id)" styleClass="formTextFieldWithoutTransparent" onblur="return submitMICR('frmMicrCode')"></html:text></td>
				<td><html:text property="panelBnkName"  size="15" readonly="true" styleClass="formTextField" style="color:blue"></html:text></td>
				
			</tr>
			
			<tr>
				<td><bean:message key="label.branch_name"></bean:message></td>
				<td><html:text property="panelBrhName" readonly="true"  styleClass="formTextFieldWithoutTransparent" size="16" style="color:blue" ></html:text></td>
				
		 		<td><bean:message key="label.chqDdPoNo"/></td>
				<td><html:text property="panelChqDDNum" readonly="true" styleId="panel_chqddno" size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		 	</tr>
			
			<tr>
				<td><bean:message key="label.amount"/></td>
				 <td><html:text property="panelAmount" readonly="true" onkeypress="return onlyDoublenumbers()" size="16" styleClass="formTextFieldWithoutTransparent" styleId="panel_amt" ></html:text></td>
		 
		 		<td><bean:message key="label.pocomm"/></td>
				<td><html:text property="commission" readonly="true" size="10" styleClass="formTextFieldWithoutTransparent" styleId="comm"></html:text></td>
		 	</tr>
			
			<tr>
				<td><bean:message key="label.chqdate"/></td>
				<td><html:text property="panelChqDate" readonly="true"  styleClass="formTextFieldWithoutTransparent"  size="16" styleId="panel_chqdate"  /></td>
				
				<td><bean:message key="label.earlier"></bean:message></td>
				<td><html:text property="prevCtrlNum"  size="10" readonly="true" styleId="prev_ctrlno" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		
			</tr>
				
		</table>
				
		<div id="bounce_fine" style="display:none;overflow:scroll;width:310px;height:100px;">
			<table border="1" style="border:thin solid #000000;">
				
			<tr><td>ResCode</td><td>Description</td><td>FineAmt</td><td>Select</td></tr>
				
				<%Reason[] reason=(Reason[])request.getAttribute("Reason"); %>
			<% 
			for(int i=0;i<reason.length;i++)
				{ %>
				
				<tr>
				<td><html:text property="txtReasonCode" size="5" value="<%=""+reason[i].getReasonCd()%>" ></html:text></td>
				<td><html:text property="txtDesription"  value="<%=""+reason[i].getReasonDesc()%>" ></html:text></td>	
				<td><html:text property="txtBounceFine" size="5" value="<%=""+reason[i].getBounceFine()%>" ></html:text></td>
				<td><html:checkbox property="chkBox"  value="<%=""+i%>"/></td>
				</tr>
			<%} %>
					
		</table>
 	</div>
	
 			<% 
 				String jspPath=(String)request.getAttribute("flag");
				System.out.println("'m inside panel"+jspPath);
						        
				if(jspPath!=null)
				{
					System.out.println("wen 'm  null");
            %>
            <table style="border:thin solid #000000;">
 		 	<tr>
 			<td>
            	<jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>
            
            <%} else{	%>
                <jsp:include page="/LockerWebPages/blank.jsp"></jsp:include>
          	<%} %>	
 			</td>
 		</tr>
 	</table>		
	</td>
	</tr>
	
			
		</table>
			</td>
			</tr>
		</table>		
	</html:form>
</body>
</html>