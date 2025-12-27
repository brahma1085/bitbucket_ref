<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.apache.struts.taglib.tiles.GetAttributeTag"%>
<%@page import="masterObject.cashier.TerminalObject"%>
<%@page import="masterObject.cashier.CashObject"%>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<h2 class="h2">
<center>Inter Counter Transfer</center></h2>
<script type="text/javascript">

function validations()
{
	if(document.forms[0].gen_scroll.value!=0)
 			{
 				alert(" Note Scroll No"+document.forms[0].gen_scroll.value+"Scroll Generation");
 				
 				document.forms[0].amount.value="0";
 				document.forms[0].p_thousand.value="0";
				document.forms[0].r_thousand.value="0";
				document.forms[0].p_fivered.value="0";
				document.forms[0].r_fivered.value="0";
				document.forms[0].p_hundred.value="0";
				document.forms[0].r_hundred.value="0";
				document.forms[0].p_fifty.vlue="0";
				document.forms[0].r_fifty.value="0";
				document.forms[0].p_twenty.value="0";
				document.forms[0].r_twenty.value="0";
				document.forms[0].p_ten.value="0";
				document.forms[0].r_ten.value="0";
				document.forms[0].p_five.value="0";
				document.forms[0].r_five.value="0";
				document.forms[0].p_two.value="0";
				document.forms[0].r_two.value="0";
				document.forms[0].p_one.value="0";
				document.forms[0].r_one.value="0";
				document.forms[0].p_coins.value="0.0";
				document.forms[0].r_coins.value="0.0";
 				return false;
 			}
	if(document.forms[0].accept_scroll.value!=0)
 			{
 				alert(" Note Scroll No"+document.forms[0].accept_scroll.value+"Scroll Generation");
 				document.forms[0].amount.value="0";
 				document.forms[0].p_thousand.value="0";
				document.forms[0].r_thousand.value="0";
				document.forms[0].p_fivered.value="0";
				document.forms[0].r_fivered.value="0";
				document.forms[0].p_hundred.value="0";
				document.forms[0].r_hundred.value="0";
				document.forms[0].p_fifty.vlue="0";
				document.forms[0].r_fifty.value="0";
				document.forms[0].p_twenty.value="0";
				document.forms[0].r_twenty.value="0";
				document.forms[0].p_ten.value="0";
				document.forms[0].r_ten.value="0";
				document.forms[0].p_five.value="0";
				document.forms[0].r_five.value="0";
				document.forms[0].p_two.value="0";
				document.forms[0].r_two.value="0";
				document.forms[0].p_one.value="0";
				document.forms[0].r_one.value="0";
				document.forms[0].p_coins.value="0.0";
				document.forms[0].r_coins.value="0.0";
 				return false;
 			} 
 	if(document.forms[0].update_scroll.value!=0)
 			{
 				alert("Scroll No<"+document.forms[0].update_scroll.value+">has been Updated");
 				
 				document.forms[0].scroll_no.value="0";
 				document.forms[0].amount.value="0";
 				document.forms[0].p_thousand.value="0";
				document.forms[0].r_thousand.value="0";
				document.forms[0].p_fivered.value="0";
				document.forms[0].r_fivered.value="0";
				document.forms[0].p_hundred.value="0";
				document.forms[0].r_hundred.value="0";
				document.forms[0].p_fifty.vlue="0";
				document.forms[0].r_fifty.value="0";
				document.forms[0].p_twenty.value="0";
				document.forms[0].r_twenty.value="0";
				document.forms[0].p_ten.value="0";
				document.forms[0].r_ten.value="0";
				document.forms[0].p_five.value="0";
				document.forms[0].r_five.value="0";
				document.forms[0].p_two.value="0";
				document.forms[0].r_two.value="0";
				document.forms[0].p_one.value="0";
				document.forms[0].r_one.value="0";
				document.forms[0].p_coins.value="0.0";
				document.forms[0].r_coins.value="0.0";
 				return false;
 			} 
	if(document.getElementById("valid").value!=null)
 		{
 			if(document.getElementById("valid").value=="denied")
		 		{
 					alert("Access denied: U cannot alter this Scroll No (Tml Close)");
 					return false;
 				}
 			if(document.getElementById("valid").value=="cantupdate")
		 		{
 					alert("U cannot update verified scroll no");
 					return false;
 				}
 			if(document.getElementById("valid").value=="invalid")
		 		{
 					alert("Invalid Scroll No");
 					return false;
 				}
 			if(document.getElementById("valid").value=="verified")
		 		{
 					alert("Scroll No Already Verified");
 					return false;
 				}
 			if(document.getElementById("valid").value=="checkdb")
		 		{
 					alert("Eror : Check DB Entries");
 					return false;
 				}
 			if(document.getElementById("valid").value=="error")
		 		{
 					alert("Eror :");
 					return false;
 				}
 			if(document.getElementById("valid").value=="confirm")
		 		{
 					alert("Confirm..");
 					return false;
 				}
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
 			if(document.getElementById("valid").value=="terminal")
		 		{
 					alert("U cant close the Terminal");
 					return false;
 				}
 			if(document.getElementById("valid").value=="money")
		 		{
 					alert("You cannot transfer money to the same terminal");
 					return false;
 				}
 			if(document.getElementById("valid").value=="unableto")
		 		{
 					alert("Error.....Unable to Accept Money");
 					return false;
 				}
 			if(document.getElementById("valid").value=="delete")
		 		{
 					alert("Deleted Successfully");
 					return false;
 				}
 			
 			if(document.getElementById("valid").value=="ReOpened")
		 		{
 					alert("Tml Re-Opened Successfully");
 					return false;
 				}
 			if(document.getElementById("valid").value=="notdelete")
		 		{
 					alert("Error : Could not Delete");
 					return false;
 				}
 			if(document.getElementById("valid").value=="reopenerror")
		 		{
 					alert("Error : Could not Re-Open the Tml");
 					return false;
 				}
 			if(document.getElementById("valid").value=="notexist")
		 		{
 					alert("Terminal doesnt exist");
 					return false;
 				}
 			if(document.getElementById("valid").value=="Insufficient")
		 		{
 					alert("Insufficient Currency Denominations");
 					return false;
 				}
 		}
 				
};

function but_transfer(target)
	{
		alert(target);
		document.forms[0].transfer.value=target;
		
		var value=confirm("Data OK??");
		if(value==true)
   				{
 					document.getElementById("counter").value="true";
 					document.forms[0].submit();
				}
 				else 
 				{
 					document.getElementById("counter").value="false";
 					return false;
 				}
 	
	};
function but_delete(target)
	{
		alert(target);
		document.forms[0].transfer.value=target;
		
		var value=confirm("Are you sure you want to delete ?");
		alert("Are U Sure??");
  			if(value==true)
   				{
 					
 					document.getElementById("delvalue").value="true";
 					document.forms[0].submit();
				}
 				else 
 				{
 					document.getElementById("delvalue").value="false";
 					return false;
 				}
 				
	};
	function but_modify(target)
	{
		alert(target);
		
		document.forms[0].transfer.value=target;
		var value=confirm("Are U sure u wnat to Update!!!");
  			if(value==true)
   				{
 					document.getElementById("counter").value="true";
 					document.forms[0].submit();
				}
 				else 
 				{
 					document.getElementById("counter").value="false";
 					return false;
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
			document.forms[0].cur_total.value=sum;
			document.forms[0].rec_amt.value=sum;
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
		
			if(document.forms[0].amount.value!=null)
		{
		
			var value1=document.forms[0].cur_total.value;
			
			var value2=document.forms[0].refund.value;
			
			if(document.forms[0].amount.value==value2)
			{
				alert("currency denomination is entered");
			}
			if(document.forms[0].amount.value==value1)
			{
				alert("u r paying more than amount"+document.forms[0].amount.value);
			}
		}
		}
	};
	function fun_denom()
	{
		if(document.forms[0].required.value=="Required")
		alert("Enter Currency Denmoinations");
	};
        
</script>
</head>
<body class="Mainbody" onload="validations()">
<%!TerminalObject[] terminalobject_view;%>
<%terminalobject_view=(TerminalObject[])request.getAttribute("comboterminal");%>
<%String terminal=(String)request.getAttribute("objterminal"); %>
<%double tml_runable=(Double)request.getAttribute("tml_runable");%>

<%int scrollno=(Integer)request.getAttribute("scrollno");%>

<%Double cashamount=(Double)request.getAttribute("cashamount");%>


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
<html:form action="/Transfer?pageId=2004">
<h3 align="left" style="font-size:x-small;color: red;font-family:Comic Sans MS "><bean:message key="label.terminalrs"/><%=tml_runable%></h3>

<table>
<tr>
<td>

<table class="txtTable">
	<tr>
		
		<td align="right"><bean:message key="label.scroll_no"/></td>
		<TD><html:text property="scroll_no" onblur="submit()" styleClass="formTextFieldWithoutTransparent"></html:text></TD>
	
	</tr>
	<tr>
		<td align="right"><bean:message key="label.term"/></td>		
		<%if(terminal!=null){ %>
		<td><html:select property="terminal" styleClass="formTextFieldWithoutTransparent">
			
			<html:option value="<%=terminal%>"><%=terminal%></html:option>
			
			</html:select>
		</td>
		<%}else { %>
		<td><html:select property="terminal" styleClass="formTextFieldWithoutTransparent">
			<%if(terminalobject_view!=null){ %>
			<%for(int i=0;i<terminalobject_view.length;i++){ %>
			<html:option value="<%=""+terminalobject_view[i].getTerminals()%>"></html:option>
			<%} }%>
			</html:select>
		</td>
		<%}%>
	</tr>
	
	<tr>	
		<td align="right"><bean:message key="label.Amt"/></td>
		<%if(cashamount!=null){ %>
		<TD><html:text property="amount" value="<%=""+cashamount%>" styleClass="formTextFieldWithoutTransparent" ></html:text></TD>
		<%}else{ %>
		<TD><html:text property="amount" value="0.0" styleClass="formTextFieldWithoutTransparent" ></html:text></TD>
		<%} %>
		
	</tr>
		<tr>
					<TD align="right"><bean:message key="label.currdenom" /></TD>
					<td><html:select property="required" onchange="submit()" onblur="fun_denom()">
						<html:option value="Required"></html:option>
						<html:option value="Not Required"></html:option>
						</html:select>
					</td>
			</tr>
	<html:hidden property="transfer" value="error"/>
	
		<html:hidden property="trbutton" styleId="counter"/>
		<html:hidden property="delbutton" styleId="delvalue"/>
		<html:hidden property="validation" styleId="valid"/>
		<html:hidden property="gen_scroll"/>
		<html:hidden property="accept_scroll"/>
		<html:hidden property="update_scroll"/>
	<tr>
		<%if(scrollno==0){ %>
		<td>
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
		<html:button property="transferfun" onclick="but_transfer('Transfer')" value="Transfer" styleClass="ButtonBackgroundImage"></html:button>
		<%}else{ %>
		<html:button property="transferfun" onclick="but_transfer('Transfer')" value="Transfer" styleClass="ButtonBackgroundImage" disabled="true" ></html:button>
		<%} %>
		</td>
		<%}if(scrollno!=0){ %>
		<td>
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
		<html:button property="dletefun" onclick="but_delete('delete')"  styleClass="ButtonBackgroundImage" >Delete</html:button>
		<%}else{ %>
		<html:button property="dletefun" onclick="but_delete('delete')"  styleClass="ButtonBackgroundImage" disabled="true" >Delete</html:button>
		<%}%>
		</td>
		<td>
		
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
		<html:button property="modifyfun" onclick="but_modify('Modify')"  styleClass="ButtonBackgroundImage">Modify</html:button>
		<%}else{ %>
		<html:button property="modifyfun" onclick="but_modify('Modify')"  styleClass="ButtonBackgroundImage">Modify</html:button>
		<%} %>
		</td>
		
		
		
		
		
		
		
		<%} %>
		<td><html:button property="clear" onclick="return clearfun()" value="Clear" styleClass="ButtonBackgroundImage" ></html:button></td>
	</tr>
	
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
		
		<td></td>
		<td></td>
		<td></td>
		<td><bean:message key="label.total"></bean:message></td>
		
		<td align="left"><html:text size="8" property="cur_total" styleClass="formTextFieldcolor"></html:text></td>
	</tr>
	
	<tr>
		
		<td align="right"><html:text size="8" property="refund" styleClass="formTextFieldcolor"></html:text></td>
		<td><bean:message key="label.refund"/></td>
		<td></td>
		<td><bean:message key="label.receive"/></td>
		<td align="left"><html:text size="8" property="rec_amt" styleClass="formTextFieldcolor"></html:text></td>
	</tr>
	
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