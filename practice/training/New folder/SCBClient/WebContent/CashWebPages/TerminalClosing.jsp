<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Terminal Closing</title>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<h2 class="h2">
<center>Terminal Closing</center></h2>
<script type="text/javascript">
function rmultiplvalue()
{
		var multiple=new Array(9);
		var i=0;
		var sum=0;
		
		multiple[0]=document.forms[0].lbl_thousand.value * document.forms[0].r_thousand.value;
		document.forms[0].rval_thousand.value=multiple[0];
		
		multiple[1]= document.forms[0].lbl_fivered.value * document.forms[0].r_fivered.value;
		document.forms[0].rval_fivered.value=multiple[1];
		
		multiple[2]= document.forms[0].lbl_hundred.value * document.forms[0].r_hundred.value;
		document.forms[0].rval_hundred.value=multiple[2];
		
		multiple[3]= document.forms[0].lbl_fifty.value * document.forms[0].r_fifty.value;
		document.forms[0].rval_fifty.value=multiple[3];
		
		multiple[4]= document.forms[0].lbl_twenty.value * document.forms[0].r_twenty.value;
		document.forms[0].rval_twenty.value=multiple[4];
		
		multiple[5]= document.forms[0].lbl_ten.value * document.forms[0].r_ten.value;
		document.forms[0].rval_ten.value=multiple[5];
		
		multiple[6] = document.forms[0].lbl_five.value * document.forms[0].r_five.value;
		document.forms[0].rval_five.value=multiple[6];
		
		multiple[7] = document.forms[0].lbl_two.value * document.forms[0].r_two.value;
		document.forms[0].rval_two.value=multiple[7];
		
		multiple[8] = document.forms[0].lbl_one.value * document.forms[0].r_one.value;
		document.forms[0].rval_one.value=multiple[8];
		
		multiple[9]= document.forms[0].r_coins.value;		
		document.forms[0].rval_coins.value=multiple[9];
		for(i=0;i<9;i++)
		{
			sum=sum+multiple[i];
			document.forms[0].total.value=sum;
		}
	};	
	
function pmultiplvalue()
	{ 
		var multiple=new Array(9);
		var i=0;
		var sum1=0;
			
		multiple[0]=document.forms[0].lbl_thousand.value * document.forms[0].p_thousand.value;
		document.forms[0].pval_thousand.value=multiple[0];
		
		multiple[1]= document.forms[0].lbl_fivered.value * document.forms[0].p_fivered.value;
		document.forms[0].pval_fivered.value=multiple[1];
		
		multiple[2]= document.forms[0].lbl_hundred.value * document.forms[0].p_hundred.value;
		document.forms[0].pval_hundred.value=multiple[2];
		
		multiple[3]= document.forms[0].lbl_fifty.value * document.forms[0].p_fifty.value;
		document.forms[0].pval_fifty.value=multiple[3];
		
		multiple[4]= document.forms[0].lbl_twenty.value * document.forms[0].p_twenty.value;
		document.forms[0].pval_twenty.value=multiple[4];
		
		multiple[5]= document.forms[0].lbl_ten.value * document.forms[0].p_ten.value;
		document.forms[0].pval_ten.value=multiple[5];
		
		multiple[6] = document.forms[0].lbl_five.value * document.forms[0].p_five.value;
		document.forms[0].pval_five.value=multiple[6];
		
		multiple[7] = document.forms[0].lbl_two.value * document.forms[0].p_two.value;
		document.forms[0].pval_two.value=multiple[7];
		
		multiple[8] = document.forms[0].lbl_one.value * document.forms[0].p_one.value;
		document.forms[0].pval_one.value=multiple[8];
		
		multiple[9]= document.forms[0].p_coins.value;		
		document.forms[0].pval_coins.value=multiple[9];
		
		for(i=0;i<9;i++)
		{
			sum1=sum1+multiple[i];
			document.forms[0].refund.value=sum1;
		}
		
		
	};     
	
	function fun_exc()
	{
		if(document.forms[0].amount.value!=null)
		{
		
			var value1=document.forms[0].total.value;
			if(document.forms[0].amount.value == value1)
 			{
 				var value=confirm("Data OK ??");
   				if(value==true)
   				{
 					document.getElementById("currency").value="true";
 					document.forms[0].submit();
				}
 				else 
 				{
 					document.getElementById("currency").value="false";
 					document.forms[0].submit();
 				}
 			}	
 		}
 		
	}; 	
function validations()
{
	if(document.forms[0].gen_scroll.value!=0)
 		{
 			alert("Terminal closed...Note the Scroll No"+document.forms[0].gen_scroll.value);
 			return false;
 		}
		if(document.getElementById("valid").value!=null)
 		{
 			if(document.getElementById("valid").value=="dayclose")
		 		{
 					alert("Day closed\n You can't do any Transactions");
 					return false;
 				}
 			if(document.getElementById("valid").value=="cashclose")
		 		{
 					alert("Cash closed\n You can't do any Transactions");
 					return false;
 				}
 			if(document.getElementById("valid").value=="noentry")
		 		{
 					alert("Error : no entry in Daily status for today");
 					return false;
 				}
 			if(document.getElementById("valid").value=="closeterminal")
		 		{
 					alert("Do you want to close the Cash Terminal?");
 					return false;
 				}
 			if(document.getElementById("valid").value=="progress")
		 		{
 					alert("Terminal Closing...transaction in progress");
 					return false;
 				}
 			if(document.getElementById("valid").value=="alreadyclose")
		 		{
 					alert("Terminal is already Closed");
 					return false;
 				}
 			if(document.getElementById("valid").value=="incurrency")
		 		{
 					alert("No Entries in Currency Stock");
 					return false;
 				}
 			if(document.getElementById("valid").value=="errorac")
		 		{
 					alert("Error: Existing A/c Rec. Ver. is Pending");
 					return false;
 				}
 			if(document.getElementById("valid").value=="newac")
		 		{
 					alert("Error: New A/c Rec. Ver. is Pending");
 					return false;
 				}
 			if(document.getElementById("valid").value=="pending")
		 		{
 					alert("Error: Misc. Rec. Ver. is Pending");
 					return false;
 				}
 		}
 			
};
function termclose(target)
{
	document.forms[0].but_value.value =target;
 	alert(document.forms[0].but_value.value);
 	var value=confirm("Data OK ??");
   if(value==true)
   	{
 		document.getElementById("closed").value="true";
 		document.forms[0].submit();
	}
 	else 
 	{
 	document.getElementById("closed").value="false";
 	document.forms[0].submit();
 	}
};
function clearfun()
  {
  		alert("Clearing TextFields");
  		var ele=document.forms[0].elements;
  		
  		for(var i=0;i<ele.length;i++)
  		{
  			if(ele[i].type=="text")
  			{
  				ele[i].value="";   
  			}
  		}
  };	
</script>
</head>
<body class="Mainbody">
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

// Note:access=1111==>read,insert,delete,update
%>

<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Terminalclose?pageId=2008">
<table class="txtTable">
<tr>
<td>
	<table class="txtTable">
	<tr>
		<td><bean:message key="label.scroll_no"/></td>
		<td><html:text property="scroll_no" onblur="submit()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
	</tr>
	<tr>
		<td><bean:message key="label.Amt"/></td>
		<td><html:text property="tr_amount" styleClass="formTextFieldWithoutTransparent"></html:text></td>
	</tr>
	<tr>
		<td><bean:message key="label.sent_to"/></td>
		<td><html:text property="send_to" styleClass="formTextFieldWithoutTransparent"></html:text></td>
	</tr>
	<html:hidden property="but_value" value="error" />
	<html:hidden property="closed" styleId="closed" />
	<html:hidden property="validation" styleId="valid"/>
	<html:hidden property="gen_scroll"/>
		<table class="txtTable">
			<tr>
				<td><html:submit onclick="termclose('Close')" value="Close" styleClass="ButtonBackgroundImage">Close</html:submit></td>
				<td>
				<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
				<html:submit styleClass="ButtonBackgroundImage">Update</html:submit>
				<%}else{ %>
				<html:submit styleClass="ButtonBackgroundImage" disabled="true">Update</html:submit>
				<%}%>
				</td>
				<td><html:button property="clear" onclick="return clearfun()" value="Clear" styleClass="ButtonBackgroundImage"></html:button></td>
			</tr>
		</table>
	</table>	
</td>
<td>
	<table cellspacing="10" style="border:thin solid #339999;" align="center" class="txtTable">
		
	<tr>
		<td align="center"><bean:message key="label.amount"></bean:message></td>
		<td align="center"><bean:message key="label.no"></bean:message></td>
		<td></td>
		<td align="center"><bean:message key="label.no"></bean:message></td>
		<td align="center"><bean:message key="label.amount"></bean:message></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_thousand" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_thousand"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<TD><html:text size="2" property="lbl_thousand" value="1000" disabled="true" styleClass="formTextField"></html:text></TD>
		<td align="center"><html:text size="8" property="r_thousand" onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_thousand" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_fivered" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_fivered"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_fivered" value="500" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_fivered"  onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_fivered" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_hundred" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_hundred"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_hundred" value="100" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_hundred"  onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_hundred" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_fifty" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_fifty"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_fifty" value="50" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_fifty"  onblur="rmultiplvalue()" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_fifty" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_twenty" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_twenty"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_twenty" value="20" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_twenty" onblur="rmultiplvalue()" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_twenty" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_ten" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="center"><html:text size="8" property="p_ten"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_ten" value="10" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_ten" onblur="rmultiplvalue()" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_ten" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_five" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_five"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_five" value="5" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_five"  onblur="rmultiplvalue()" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_five" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_two" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_two"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_two" value="2" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_two" onblur="rmultiplvalue()" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_two" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_one" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_one"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_one" value="1" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_one"  onblur="rmultiplvalue()" onkeypress="return onlynumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_one" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_coins" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="center"><html:text size="8" property="p_coins"  onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><bean:message key="label.coins"></bean:message></td>
		<td align="center"><html:text size="8" property="r_coins"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" size="8" property="rval_coins"  styleClass="formTextFieldcolor" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td><html:text size="8" property="refund" styleClass="formTextFieldcolor"></html:text></td>
		<td></td>
		<td><bean:message key="label.total"></bean:message></td>
		<td></td>
		<td align="left"><html:text size="8" property="total" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
	</tr>
	<html:hidden property="but_value"></html:hidden>
	<html:hidden property="exchange" styleId="currency"/>
	<html:hidden property="closed" styleId="close"/>
</table>
</td>
</tr>
</table>

</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>