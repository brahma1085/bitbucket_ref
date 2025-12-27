<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix = "html" uri = "http://jakarta.apache.org/struts/tags-html"%>
<%@taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.cashier.VoucherDataObject" %>
<%@page import="java.util.Map"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<h2 class="h2">
<center>General Payment</center></h2>
<script type="text/javascript">



	function HideShow(AttributetoHide)
	{
		if(document.getElementById("type").value == 'General')
		{
			document.getElementById('token'+AttributetoHide).style.display='block';
			document.getElementById('vouc'+AttributetoHide).style.display='none';
			document.getElementById('voucbk'+AttributetoHide).style.display='none';
			document.getElementById('payee'+AttributetoHide).style.display='block';
			document.getElementById('nar'+AttributetoHide).style.display='none';
		///	document.getElementById('gltype'+AttributetoHide).style.display='none';
			document.getElementById('accno'+AttributetoHide).style.display='block';
			document.getElementById('accty'+AttributetoHide).style.display='block';
			document.getElementById('slip'+AttributetoHide).style.display='block';
			document.getElementById('accname'+AttributetoHide).style.display='block';
		}
		else if(document.getElementById("type").value == 'Cash/Voucher')
		{
			document.getElementById('token'+AttributetoHide).style.display='none';
			document.getElementById('vouc'+AttributetoHide).style.display='block';
			document.getElementById('voucbk'+AttributetoHide).style.display='none';
			document.getElementById('payee'+AttributetoHide).style.display='none';
			document.getElementById('nar'+AttributetoHide).style.display='none';
		///	document.getElementById('gltype'+AttributetoHide).style.display='none';
			document.getElementById('accno'+AttributetoHide).style.display='block';
			document.getElementById('accty'+AttributetoHide).style.display='block';
			document.getElementById('slip'+AttributetoHide).style.display='none';
			document.getElementById('accname'+AttributetoHide).style.display='block';
		}
		else if(document.getElementById("type").value == 'Voucher')
		{
			document.getElementById('token'+AttributetoHide).style.display='none';
			document.getElementById('vouc'+AttributetoHide).style.display='none';
			document.getElementById('voucbk'+AttributetoHide).style.display='block';
			document.getElementById('payee'+AttributetoHide).style.display='none';
			document.getElementById('nar'+AttributetoHide).style.display='block';
			///document.getElementById('gltype'+AttributetoHide).style.display='block';
			document.getElementById('accno'+AttributetoHide).style.display='none';
			document.getElementById('accty'+AttributetoHide).style.display='none';
			document.getElementById('slip'+AttributetoHide).style.display='none';
			document.getElementById('accname'+AttributetoHide).style.display='none';
		}
		if(document.getElementById("invaltkn").value!=null)
       	{
       		if(document.getElementById("invaltkn").value=="token")
		 	{
 				alert("Warning:\nInvalid Token number\n Token Number is not verified");
 				return false;
 			}
 	  	}
 	  
		
		if(document.getElementById("invalvcno").value!=null)
       	{
       		if(document.getElementById("invalvcno").value=="voucher")
		 	{
 				alert("InValid Voucher No");
 				return false;
 			}
 	  	}
 	  
      	
      	if(document.getElementById("invalcsh").value!=null)
       	{
       		if(document.getElementById("invalcsh").value=="cshvoucher")
		 	{
 				alert("InValid Voucher No");
 				return false;
 			}
 	  	}
 	  
      	
      	if(document.getElementById("invalamt").value!=null)
       	{
       		if(document.getElementById("invalamt").value=="invalamt")
		 	{
 				alert("Amount should be greater than 0");
 				return false;
 			}
 	  	}
 	  
		
		if(document.forms[0].acc_no.value==null)
		{
			alert("A/c No field is Empty");
			return false;
		}
		if(document.forms[0].dated.value==null)
		{
			alert("Date field is Empty");
			return false;
		}
		if(document.forms[0].amount.value==null)
		{
			alert("Amount field is Empty");
			return false;
		}
		if(document.forms[0].pay_name.value==null)
		{
			alert("payee's name field is Empty");
			return false;
		}
		if(document.forms[0].nar_name.value==null)
		{
			alert("Narration field is Empty");
			return false;
		}
		if(document.forms[0].slip_no.value==null)
		{
			alert("Slip number field is Empty");
			return false;
		}
		if(document.getElementById("valid").value!=null)
 	  	{
 	  		if(document.getElementById("valid").value=="dayclose")
 	  		{
 	  			alert("dayclose","Day closed\n You can't do any Transactions");
 	  			return false;
 	  		}
 	  		
 	  		if(document.getElementById("valid").value=="noentery")
 	  		{
 	  			alert("noentry","Error : no entry in Daily status for today");
 	  			return false;
 	  		}
 	  		if(document.getElementById("valid").value=="cashclose")
 	  		{
 	  			alert("cashclose","Cash closed\n You can't do any Transactions");
 	  			return false;
 	  		}
		}
			if(document.forms[0].update_scroll.value!=0)
 			{
 				alert("Scroll No <" +document.forms[0].update_scroll.value+ "> is being Updated");
 				document.forms[0].token_no.value="0";
				document.forms[0].voucher_no.value="0";
				document.forms[0].voucher_no_bk.value="0";
				document.forms[0].acc_no.value="0";
				document.forms[0].slip_no.value="0";
				document.forms[0].dated.value="";
				document.forms[0].amount.value="0.0";
				document.forms[0].pay_name.value="";
				document.forms[0].nar_name.value="";
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
 			
 			scroll();  
	}
	
	function scroll()
	{
			if(document.forms[0].gen_scroll.value!=0)
 			{
 				alert(" Note Scroll No <" +document.forms[0].gen_scroll.value+ ">");
 				document.forms[0].token_no.value="0";
				document.forms[0].voucher_no.value="0";
				document.forms[0].voucher_no_bk.value="0";
				document.forms[0].acc_no.value="0";
				document.forms[0].slip_no.value="0";
				document.forms[0].dated.value="";
				document.forms[0].amount.value="0.0";
				document.forms[0].pay_name.value="";
				document.forms[0].nar_name.value="";
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
 		
	
	
	}
	
	function combotype()
	{
		document.forms[0].submit();
	}
	
	 function onlynumbers(){
        	
        	var cha =   event.keyCode;
			            if ( cha >= 48 && cha <= 57  ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        }
    
    function onlyDoublenumbers()
    	{
        	
        	var cha = event.keyCode;
			   if ( (cha >= 48 && cha <= 57 )  || cha == 46 ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        }
        
    
	function onlyalpha()
	{
 		var cha =event.keyCode;
		if ( (cha >= 97 && cha <= 122 ) ) 
		{
			return true;
            		
        	} else {
        		
        	return false;
        	}
	}
	
	function vchnoLimit()
 	{
 	if(document.forms[0].voucher_no.value.length<=5)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].voucher_no.value="";
 	document.forms[0].voucher_no.focus;
 	alert("Digit Limit is 5");
 	return false;
 	}
 	}
 	
 	function vchno_Limit()
 	{
 	if(document.forms[0].voucher_no_bk.value.length<=5)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].voucher_no_bk.value="";
 	document.forms[0].voucher_no_bk.focus;
 	alert("Digit Limit is 5");
 	return false;
 	}
 	}
 	
 	function token_no_Limit()
 	{
 	if(document.forms[0].token_no.value.length<=5)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].token_no.value="";
 	document.forms[0].token_no.focus;
 	alert("Digit Limit is 5");
 	return false;
 	}
 	}
 	
 	function amount_Limit()
 	{
 	if(document.forms[0].amount.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].amount.value="";
 	document.forms[0].amount.focus;
 	alert("Digit Limit is 10");
 	return false;
 	}
 	}
 	
 	function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("Warning:\nOnly 7 digits allowed!!!")
         		txt.value="";
         		return false;
          	}
         }
	
	function paybutton(target)
 	{
 		document.forms[0].but_value.value =target;
 		var updateval=confirm("Are you sure you want to go ahead with the operation ?");
 				if(updateval==true)
 				{	
 				document.getElementById("payval").value="true";
 				document.forms[0].submit();
 				}
 				else 
 				{
 				document.getElementById("payval").value="false";
 				return false;
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
		var total=document.forms[0].cur_total.value;
		var refund=document.forms[0].refund.value;	
		document.forms[0].rec_amt.value=total-refund;
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
	var total=document.forms[0].cur_total.value;
	var refund=document.forms[0].refund.value;	
		document.forms[0].rec_amt.value=total-refund;
	};    
		
function fun_exc()
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
	};
	function fun_denom()
	{
		if(document.forms[0].required.value=="Required")
		alert("Enter Currency Denmoinations");
	};
	
	function clearfun()
  {
  		
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
<body onload="HideShow(11)" class="Mainbody">
<%!
Map user_role;
String access;
%>
<%
String accesspageId=(String)request.getAttribute("accesspageId");
user_role=(Map)request.getAttribute("user_role");


if(user_role!=null&&accesspageId!=null){
	if(user_role.get(accesspageId)!=null){
		access=(String)user_role.get(accesspageId);
		System.out.println("access-----In SBOpening------>"+access);
	}

	
	}

%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<%double tml_runable=(Double)request.getAttribute("tml_runable");%>

<!--<h3 align="left" style="font-size:x-small;color: red;font-family:Comic Sans MS "><bean:message key="label.terminalrs"/></h3>

--><html:form action="/Payment?pageId=2003">

<%ModuleObject[] moduleObject_gen =(ModuleObject[])request.getAttribute("MainModules");%>

<%String module = (String)request.getAttribute("module");%>

<%String modulegen=(String)request.getAttribute("modulegen"); %>

<%String genabbrv=(String)request.getAttribute("genabbrv");%>

<%String moduleindex=(String)request.getAttribute("moduleindex"); %>

<%String dayclose=(String)request.getAttribute("dayclose"); %>
<%String cashclose=(String)request.getAttribute("cashclose"); %>
<%String noentry=(String)request.getAttribute("noentry"); %>
<%String payment=(String)request.getAttribute("payment"); %>
<%VoucherDataObject[] vchdataobject =(VoucherDataObject[])request.getAttribute("arrayvoucherObject"); %>

<%String required=(String)request.getAttribute("required"); %>


<%//String tokenNo=(String)request.getAttribute("tokenNo"); %>

<%String accNo=(String)request.getAttribute("accNo"); 
String panelName=(String)request.getAttribute("panelName");
%>

<%String chqDate=(String)request.getAttribute("chqDate"); %>

<%Double trnAmt=(Double)request.getAttribute("trnAmt"); %>

<%String payName=(String)request.getAttribute("payName"); %>
<%String slipNo=(String)request.getAttribute("slipNo"); %>
<%String accName=(String)request.getAttribute("accName"); %>
<%String Narration=(String)request.getAttribute("Narration"); 
//String panelName=(String)request.getAttribute("panelName");
%>


<%masterObject.cashier.VoucherDataObject[] voucherDataObject=(masterObject.cashier.VoucherDataObject[])request.getAttribute("arrayvoucherObject"); %>

<table class="txtTable">
<tr>
<td>
<table class="txtTable">
		<html:hidden property="validation" styleId="valid"/>
		<html:hidden property="invalid_tkn" styleId="invaltkn"></html:hidden>	
		<html:hidden property="invalid_vcno" styleId="invalcsh"></html:hidden>
		<html:hidden property="inval_vch" styleId="invalvcno"></html:hidden>
		<html:hidden property="inval_amt" styleId="invalamt"/>
		
				
	<tr>
		<td align="right"><bean:message key="label.type"/></td>
		<td><html:select property="combo_type" onchange="combotype()" styleId="type" styleClass="formTextFieldWithoutTransparent">
		
			<html:option value="General"></html:option>
			<html:option value="Cash/Voucher"></html:option>
			<html:option value="Voucher"></html:option>
			</html:select>
		</td>
	</tr>

	<tr id="token11">
		<td align="right"><bean:message key="label.token"/></td>
		<td><html:text property="token_no" size="10" onkeypress="return onlynumbers()" onkeydown="return token_no_Limit()" onblur="submit()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		
	</tr>

	
	<tr id="vouc11">
		<td align="right"><bean:message key="label.voucherno"/></td>
		<td><html:text property="voucher_no" size="10"  onkeypress="return onlynumbers()" onkeydown="return vchnoLimit()" onblur="submit()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
	</tr>
	
	<tr id="voucbk11">
		<td align="right"><bean:message key="label.voucherno"/></td>
		<td><html:text property="voucher_no_bk" size="10" onkeypress="return onlynumbers()" onkeydown="return vchno_Limit()" onblur="submit()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
	</tr>
	
		
	
	<tr id="accty11">
		
		<%if(modulegen!=null && genabbrv!=null && moduleindex!=null){ %>
		<td align="right"><bean:message key="lable.acctye"/></td>
		<td><html:select property="acc_type" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
			<html:option value="<%=""+moduleindex%>"><%=genabbrv%></html:option>
			</html:select></td>
		<td><html:text property="Moduledesc" value="<%=modulegen%>" size="10" readonly="true" ></html:text></td>
		
		
		<%} else { %>
		
		<td align="right"><bean:message key="lable.acctye"/></td>
		<td><html:select property="acc_type" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
			<%if(moduleObject_gen!=null){%>
			<%for(int i=0;i<moduleObject_gen.length;i++){ %>
			<html:option value="<%=""+i%>"><%=moduleObject_gen[i].getModuleAbbrv()%></html:option>
			<%} }%>
			</html:select></td>
			<%if(module!=null){ %>
		<TD><html:text property="Moduledesc" value="<%=module%>" size="10" readonly="true"></html:text></TD>
		
			<%}} %>
	</tr>
	
	<tr id="accno11">
		<td align="right"><bean:message key="label.acc_no2"/></td>
		<core:choose>
		<core:when test="${requestScope.accNo!=0}">
		<td><html:text property="acc_no" value="${requestScope.accNo}" size="10" onkeypress="return onlynumbers()" disabled="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		</core:when>
		<core:otherwise>
		<td><html:text property="acc_no" size="10" onkeypress="return onlynumbers()" disabled="true" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
		</core:otherwise>
		</core:choose>
	</tr>
	
	<tr>
		<td></td>
		<core:choose>
		<core:when test="${requestScope.accName!=0}">
		<td id="accname11"><html:text property="acc_name" value="${requestScope.accName}" onkeypress="return onlyalpha()" disabled="true" styleClass="formTextField"></html:text></td>
		</core:when>
		<core:otherwise>
		<td id="accname11"><html:text property="acc_name" value="" onkeypress="return onlyalpha()" disabled="true" styleClass="formTextField"></html:text></td>
		</core:otherwise>
		</core:choose>
	</tr>
	
	<tr>
		<td align="right"><bean:message key="label.paytype"/></td>
		<td><html:select property="pay_type" disabled="true" styleClass="formTextFieldWithoutTransparent">
			<html:option value="Cheque"></html:option>
			<html:option value="Slip"></html:option>
			<html:option value="PayOrder"></html:option>
			<html:option value="Cash"></html:option>
			</html:select>
		</td>
		<core:choose>
		<core:when test="${requestScope.slipNo!=0}">
		<td id="slip11">Cheque/Slip No:<html:text property="slip_no" size="10" value="${requestScope.slipNo}" onkeypress="return false;" disabled="false" styleClass="formTextField"></html:text></td>
		</core:when>
		<core:otherwise>
		<td id="slip11">Cheque/Slip No:<html:text property="slip_no" value="" size="10" onkeypress="return false;" disabled="false" styleClass="formTextField"></html:text></td>
		</core:otherwise>
		</core:choose>
	</tr>
	
	<tr>
		<td align="right"><bean:message key="label.dated"/></td>
		<core:choose>
		
		<core:when test="${requestScope.chqDate!=null}">
		<td><html:text property="dated" size="10" value="${requestScope.chqDate}" disabled="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		</core:when>
		<core:otherwise>
		<td><html:text property="dated" value="" size="10" disabled="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		</core:otherwise>
		</core:choose>
	</tr>
	
	<tr>
		<td align="right"><bean:message key="label.amount"/></td>
		<core:choose>
		
		<core:when test="${requestScope.trnAmt!=null}">
		<%System.out.println("The amt in jsp is"+trnAmt); %>
		<td><html:text property="amount" value="<%=""+trnAmt %>" size="10" onkeypress="return onlyDoublenumbers()" onkeydown="return amount_Limit()" styleClass="formTextFieldWithoutTransparent"></html:text></td>	
		</core:when>
		<core:otherwise>
		<td><html:text property="amount" size="10" onkeypress="return onlyDoublenumbers()" onkeydown="return amount_Limit()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		</core:otherwise>
		</core:choose>
	</tr>
		<div>
		<%if(vchdataobject!=null){
			
		System.out.println("cashobject====="+vchdataobject[0].getGlType());
		System.out.println("cashobject====="+vchdataobject[0].getTransactionAmount());
		%>
		<table border=1 width="25%">
		<td>GLCode</td><td>GLType</td><td>Amount</td>
		</table>
			<table border=1 width="25%">
	<% for(int i= 0; i <vchdataobject.length;i++){%>
	<tr>

	<td><input type="text" disabled="true" size="5" value="<%=""+vchdataobject[i].getGlType()%>"></td>
	<td><input type="text" disabled="true" size="3" value="<%=""+vchdataobject[i].getGlCode()%>"></td>
	<td><input type="text" disabled="true" size="5" value="<%=""+vchdataobject[i].getTransactionAmount()%>"></td>
	</tr>
	<%} %>
	</table>
<%} %>
</div>
	
	<tr>
		<TD align="right"><bean:message key="label.currdenom" /></TD>
		<td><html:select property="currency" onchange="submit()">
		<html:option value="Not Required"></html:option>
		<html:option value="Required"></html:option>
		</html:select>
		</td>
	</tr>
	
	<tr id="payee11">
		<td align="right"><bean:message key="label.payee"/></td>
		<core:choose>
		
		<core:when test="${requestScope.payName!=null}">
		
		<td><html:text property="pay_name" value="${requestScope.payName}" onkeypress="return onlyalpha()" disabled="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		</core:when>
		<core:otherwise>
		<td><html:text property="pay_name" value="" onkeypress="return onlyalpha()" disabled="true" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		</core:otherwise>
		</core:choose>
	</tr>
	
	<tr id="nar11">
		<td align="right"><bean:message key="label.narration"/></td>
		<core:choose>
		<core:when test="${requestScope.Narration!=null}">
		<td><html:textarea property="nar_name" value="${requestScope.Narration}"  disabled="true" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlyalpha()" ></html:textarea></td>
		</core:when>
		<core:otherwise>
		<td><html:textarea property="nar_name" value="" onkeypress="return onlyalpha()" disabled="true" styleClass="formTextFieldWithoutTransparent"></html:textarea></td>
		</core:otherwise>
		</core:choose>
	</tr>
	
	
</table>



<html:hidden property="but_value" value="error" />
<html:hidden property="dayclose" value="<%=dayclose%>"/>
<html:hidden property="cashclose" value="<%=cashclose%>"/>
<html:hidden property="noentry" value="<%=noentry%>"/>
<html:hidden property="payment" value="<%=payment%>"/>
<html:hidden property="pay_value" styleId="payval"/>
<html:hidden property="gen_scroll"/>
<html:hidden property="update_scroll"/>

<table class="txtTable">
	
	<tr>
		<td>
		 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
		<html:button property="pay_value" onclick="paybutton('PAY')" onblur="submit()" styleClass="ButtonBackgroundImage">Pay</html:button>
		<%}else{ %>
		<html:button property="pay_value"  styleClass="ButtonBackgroundImage" disabled="true">Pay</html:button>
		<%} %>
		</td>
		<td><html:button property="clear" onclick="clearfun()" value="Clear" styleClass="ButtonBackgroundImage" ></html:button></td>
	</tr>

</table>

</td>

	<%if(required!=null && required.equalsIgnoreCase("Required")){ %>
	
	<table align="center" cellspacing="10" style="border:thin solid #339999;" class="txtTable">
		
	<tr>
		<td align="center"><bean:message key="label.amount"></bean:message></td>
		<td align="center"><bean:message key="label.no"></bean:message></td>
		<td></td>
		<td align="center"><bean:message key="label.no"></bean:message></td>
		<td align="center"><bean:message key="label.amount"></bean:message></td>
	</tr>
	<tr>
			<td align="right"><html:text size="8" property="pval_thousand" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
			<td align="right"><html:text size="8" property="p_thousand"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			<TD><html:text size="2" property="lbl_thousand" value="1000" disabled="true" styleClass="formTextField"></html:text></TD>
			<td align="center"><html:text size="8" property="r_thousand" onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			<td align="left"><html:text size="8" property="rval_thousand" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()" ></html:text></td>
		</tr>
		
		<tr>
			<td align="right"><html:text size="8" property="pval_fivered" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
			<td align="right"><html:text size="8" property="p_fivered"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			<td><html:text size="2" property="lbl_fivered" value="500" disabled="true"  styleClass="formTextField"></html:text></td>
			<td align="center"><html:text size="8" property="r_fivered"  onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			<td align="left"><html:text size="8" property="rval_fivered" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()" ></html:text></td>
		</tr>
		
		<tr>
			<td align="right"><html:text size="8" property="pval_hundred" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
			<td align="right"><html:text size="8" property="p_hundred"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			<td><html:text size="2" property="lbl_hundred" value="100" disabled="true"  styleClass="formTextField"></html:text></td>
			<td align="center"><html:text size="8" property="r_hundred"  onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
			<td align="left"><html:text size="8" property="rval_hundred" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
		</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_fifty" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_fifty"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_fifty" value="50" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_fifty"  onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_fifty" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_twenty" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_twenty"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_twenty" value="20" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_twenty" onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_twenty" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_ten" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="center"><html:text size="8" property="p_ten"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_ten" value="10" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_ten" onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_ten" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_five" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_five"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_five" value="5" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_five"  onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_five" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_two" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_two"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_two" value="2" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_two" onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_two" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_one" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_one"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_one" value="1" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_one"  onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_one" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_coins" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="center"><html:text size="8" property="p_coins"  onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><bean:message key="label.coins"></bean:message></td>
		<td align="center"><html:text size="8" property="r_coins"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_coins" readonly="true"  styleClass="formTextFieldcolor" onblur="fun_exc()"></html:text></td>
	</tr>

	<tr>
		
		<td></td>
		<td></td>
		<td></td>
		<td><bean:message key="label.total"></bean:message></td>
		
		<td align="left"><html:text size="8" property="cur_total" readonly="true" styleClass="formTextFieldcolor"></html:text></td>
	</tr>
	<tr>
		
		<td align="right"><html:text size="8" property="refund" readonly="true" styleClass="formTextFieldcolor"></html:text></td>
		<td><bean:message key="label.refund"/></td>
		<td></td>
		<td><bean:message key="label.receive"/></td>
		<td align="left"><html:text size="8" property="rec_amt" readonly="true" styleClass="formTextFieldcolor"></html:text></td>
	</tr>
	
	<html:hidden property="cur_denom" styleId="currency"/>	
	
	</table>


<%} else {%>

<td>

 		<html:hidden property="defaultTab" value="Personal"></html:hidden>
  
   			<%
   			if(panelName!=null){
   			String jspPath=(String)request.getAttribute("flag");
    	  			System.out.println("jspPath=="+jspPath);
         	%>
   			<%if(jspPath!=null){ %>
   			<jsp:include page="<%=jspPath%>"></jsp:include>
			<%}}%>
		
</td>
<%}%>
</tr>
</table>


</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>

</html>