<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.*"%>
<%@page import="masterObject.cashier.VoucherDataObject"%>
<%@page import="masterObject.cashier.CashObject"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map"%>

 <style type="text/css" media="all">
       @import url("css/alternative.css");
       @import url("css/maven-theme.css");
       @import url("css/site.css");
       @import url("css/screen.css");
    </style>
<link rel="stylesheet" href="css/print.css" type="text/css" media="print" />

<link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />



<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Miscellaneous </title>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

function but_fun(target)
{

	if(document.forms[0].required.value=="Required")
 		{
 		 var refund= document.forms[0].refund.value
 	  
 	     var total=document.forms[0].rec_amt.value
 	  
 		 if(parseInt(document.forms[0].amount.value)==parseInt(refund))
 	        {
 	            alert("Invalid Data Entry");
 	         }
 	
         else
           {
             /* if(refund<total)
                 {
                   alert("Please Enter Correct Denomination")
                   }
              else*/ 
              if(refund>total)
                {
                  alert("Please Enter Correct Denomination")
                 } 	  
 	          else{
	                //alert(target);
	                  if(target=="submit")
	                   {
		                    alert(target);
		                    document.forms[0].but_value.value=target;
		                    document.getElementById("subvalue").value="true";
	                    }
	                   else
	                   {
		                 document.getElementById("subvalue").value="false";
	                   }	
	               }
	               }
	               }
	               else
	               {
	                 //alert(target);
	                  if(target=="submit")
	                   {
		                    alert(target);
		                    document.forms[0].but_value.value=target;
		                    document.getElementById("subvalue").value="true";
	                    }
	                   else
	                   {
		                 document.getElementById("subvalue").value="false";
	                   }
	                 
	               }
}
function onlyDoublenumbers(){
        	
        	var cha = event.keyCode;

            if ( (cha >= 48 && cha <= 57 )  || cha == 46 ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        };
       
function addRow()
{
var gltype=document.forms[0].gl_type.value;
var glcode=document.forms[0].gl_code.value;
var glamount=document.forms[0].gl_amount.value;
var tbl = document.getElementById('addTable');
var lastRow = tbl.rows.length;
// if there's no header row in the table, then iteration = lastRow + 1
var iteration = lastRow;
var itr=iteration-1; 
var row = tbl.insertRow(lastRow);


// left cell
var cellLeft = row.insertCell(0);
var textNode = document.createTextNode(iteration);
cellLeft.appendChild(textNode);

// right cell

var cellRight = row.insertCell(1);
var e1 = document.createElement('input');
e1.setAttribute('type', 'checkbox');
e1.setAttribute('name', 'cbox');
e1.setAttribute('value', ' '+ itr);
cellRight.appendChild(e1);

var cellRight = row.insertCell(2);
var e2 = document.createElement('input');
e2.setAttribute('type', 'text');
e2.setAttribute('name', 'gltype');
e2.setAttribute('size', '5');
e2.setAttribute('value',gltype);
cellRight.appendChild(e2);



var cellRight = row.insertCell(3);
var e3 = document.createElement('input');
e3.setAttribute('type', 'text');
e3.setAttribute('name', 'glcode');
e3.setAttribute('size', '5');
e3.setAttribute('value',glcode);
cellRight.appendChild(e3);

var cellRight = row.insertCell(4);
var e4 = document.createElement('input');
e4.setAttribute('type', 'text');
e4.setAttribute('name', 'glamount');
e4.setAttribute('size', '5');
e4.setAttribute('value',glamount);
cellRight.appendChild(e4);



};

function onlynumbers()
{
        	
        	var cha = event.keyCode;

            if ( cha >= 48 && cha <= 57  ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        };
 function onlyalpha()
	{
 		var cha =event.keyCode;
		if ( (cha >= 97 && cha <= 122 ) ) 
		{
			return true;
            		
        	} else {
        		
        	return false;
        	}
	};
	function clearfun()
  {
  	
  		var ele=document.forms[0].elements;
  		document.forms[0].narration.value="";
  		for(var i=0;i<ele.length;i++)
  		{
  			if(ele[i].type=="text")
  			{
  				ele[i].value="";   
  			}
  		}
  };	




function sub_button(target)
{
   

	alert(target);
	var value=confirm("Data OK ??");
	if(value==true)
   	{
 			document.getElementById("subvalue").value="true";
 			document.forms[0].but_value.value=target;
 			document.forms[0].submit();
	}
 			else 
 			{
 			document.getElementById("subvalue").value="false";
 			return false;
 			}
 		
};

function up_button(target)
{
	alert(target);
	document.forms[0].but_value.value=target;
	var value=confirm("Data OK ??");
		if(value==true)
   			{
 			document.getElementById("subvalue").value="true";
 			document.forms[0].submit();
			}
 			else 
 			{
 			document.getElementById("subvalue").value="false";
 			return false;
 			}
 		
};
function disable(target)
{
 alert(document.forms[0].updatefun.value);
 if(document.forms[0].updatefun.value=="update")
 {
  document.forms[0].narration.disabled=false;
  document.forms[0].amount.disabled=false;
  document.forms[0].gl_type.disabled=false;
  document.forms[0].gl_code.disabled=false;
  document.forms[0].gl_amount.disabled=false;
  document.forms[0].total.disabled=false;
  document.forms[0].updatefun.value="Confirm";
  }
  else if(document.forms[0].updatefun.value=="Confirm")
  {
    alert(target);
   
	document.forms[0].but_value.value=target;
	
	var value=confirm("Data OK ??");
	 alert(value);
		if(value==true)
   			{
 			document.getElementById("subvalue").value="true";
 			document.forms[0].submit();
			}
 			else 
 			{
 			document.getElementById("subvalue").value="false";
 			return false;
 			}
 		
  }
  
  }
function ver_button(target)
{
	alert(target);
	document.forms[0].but_value.value=target;
	var value=confirm("Data OK ??");
		if(value==true)
   			{

 			document.getElementById("subvalue").value="true";
 			document.forms[0].submit();
			}
 			else 
 			{
 			document.getElementById("subvalue").value="false";
 			return false;
 			}
 		
};
function del_button(target)
{
	alert(target);
	document.forms[0].but_value.value=target;
	var value=confirm("Data OK ??");
		if(value==true)
   			{
 			document.getElementById("subvalue").value="true";
 			document.forms[0].submit();
			}
 			else 
 			{
 			document.getElementById("subvalue").value="false";
 			return false;
 			}
 		
};

function validations()
{
	if(document.forms[0].gen_scroll.value!=0)
 	{
 		alert(" Note Scroll No<"+document.forms[0].gen_scroll.value+">");
 		clearfun();
 		return false;
 		
 	}  
 			
 	if(document.forms[0].update_scroll.value!=0)
 			{
 				alert("Scroll No <" +document.forms[0].update_scroll.value+ " > has been Updated");
 				clearfun()
 				return false;
 			}    
	if(document.getElementById("msClosed").value=="dayclose")
	{
		alert("Day closed\n You can't do any Transactions");
		return false;
	}
	if(document.getElementById("msClosed").value=="cashclose")
	{
		alert("Cash closed\n You can't do any Transactions");
		return false;
	}
	if(document.getElementById("msClosed").value=="noentry")
	{
		alert("Error : no entry in Daily status for today");
		return false;
	}
	if(document.getElementById("msClosed").value=="scrolverify")
	{
		alert("Scroll No Already Verified");
		return false;
	}
	if(document.getElementById("msClosed").value=="errDB")
	{
		alert("Eror : Check DB Entries");
		return false;
	}
	if(document.getElementById("msClosed").value=="invalid")
	{
		alert("Invalid Scroll No");
		return false;
	}
	if(document.getElementById("msClosed").value=="error")
	{
		alert("Error: ");
		return false;
	}
	if(document.getElementById("msClosed").value=="notverify")
	{
		alert("Transaction Not Verified");
		clearfun()
		return false;
	}
	if(document.getElementById("msClosed").value=="success")
	{
		alert("Transaction Verified sucessfully");
		clearfun();
		return false;
	}
	if(document.getElementById("msClosed").value=="recordverify")
	{
		alert("Record Already Verified");
		clearfun()
		return false;
	}
	if(document.getElementById("msClosed").value=="enterscroll")
	{
		alert("Please enter the scroll no to be verified");
		clearfun()
		return false;
	}
	if(document.getElementById("msClosed").value=="delete")
	{
		alert("Scroll No Deleted successfully!");
		clearfun()
		return false;
	}
	if(document.getElementById("msClosed").value=="alldetail")
	{
		alert("Please enter all the details correctly");
		
		return false;
	}
	if(document.getElementById("msClosed").value=="terminalnotexist")
	{
		alert("Terminal doesnt exist");
		return false;
	}
	if(document.getElementById("msClosed").value=="Insufficient")
	{
		alert("Insufficient Currency Denominations");
		return false;
	}
	if(document.getElementById("msClosed").value=="noentries")
	{
		alert("No Entries in Table");
		return false;
	}
	if(document.getElementById("msClosed").value=="enteramt")
	{
		alert("Enter Amount");
		return false;
	}
	if(document.getElementById("msClosed").value=="narr")
	{
		alert("Enter textarea_narration");
		return false;
	}
	if(document.getElementById("msClosed").value=="fornewacc")
	{
		alert("Enter 0 For New Account or Valid Scroll No For Updation");
		return false;
	}
	
	
};

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
	function setFlag(flagValue){
	document.forms[0].flag.value=flagValue;
	document.forms[0].submit();
	}
	function fun_denom()
	{
		if(document.forms[0].required.value=="Required")
		alert("Enter Currency Denmoinations");
	};
	
	function num_Limit()
 	{
 	if(document.forms[0].scroll_no.value.length<=5)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].scroll_no.value="";
 	document.forms[0].scroll_no.focus;
 	alert("Warning:\nDigit Limit is 5!!!");
 	return false;
 	}
 	}
 	
 	function amt_Limit()  
 	{
 	if(document.forms[0].amount.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].amount.value="";
 	document.forms[0].amount.focus;
 	alert("Warning:\nDigit Limit is 10!!!");
 	return false;
 	}
 	}
 	
 	function gl_amount_Limit()   
 	{
 	if(document.forms[0].gl_amount.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].gl_amount.value="";
 	document.forms[0].gl_amount.focus;
 	alert("Warning:\nDigit Limit is 10!!!");
 	return false;
 	}
 	}
	
	function narr_Limit()
 	{
 	if(document.forms[0].narration.value.length<=20)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].narration.value="";
 	document.forms[0].narration.focus;
 	alert("Warning:\nEnter not more than 20 characters!!!");
 	return false;
 	}
 	}
	
	     
	  
	
</script>

</head>

<body class="Mainbody" onload="validations()">
<%System.out.println("For getting values from action class in jsp ");%>

<%!int[] glcode;
Map user_role;
String access;
%>

<%glcode=(int[])request.getAttribute("glcode"); %>


<%!

String  accesspageId;


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



<%

String msg1=(String)request.getAttribute("Code1");
String msg2=(String)request.getAttribute("Code2");
String msg3=(String)request.getAttribute("Code3");
String msg4=(String)request.getAttribute("Code4");
%>
<%
ModuleObject[] gltype=(ModuleObject[])request.getAttribute("gltype"); %>
<%//String unverifyrunnable=(String)request.getAttribute("unverifyrunnable");%>
<% double tml_runable=(Double)request.getAttribute("Terminal"); %>
<%String button_val=(String)request.getAttribute("submit");%>
<%
System.out.println("Button bval in jsp--------------"+button_val);
%>
<%int scroll = (Integer)request.getAttribute("scrollno");%>
<%System.out.println("Scroll in jsp=========="+scroll); %>
<%String amount=(String)request.getAttribute("amount"); %>

<%String narr=(String)request.getAttribute("narr"); %>
<% String amt1 =(String)request.getAttribute("amt1");  %>
<%String[][] glnmcd=(String[][])request.getAttribute("glnmcd"); %>


<%String accname=(String)request.getAttribute("accname"); %>
<%String gl_amt=(String)request.getAttribute("glamount"); %>
<%Double sum =(Double)request.getAttribute("sum");%>
<% System.out.println("The sum in jsp===="+sum);%>
<%VoucherDataObject[] vchdataobject =(VoucherDataObject[])request.getAttribute("vchdataobject"); 

System.out.println("Voucher in jsp====="+vchdataobject);

%>
<%String totalamt=(String)request.getAttribute("totalamt");%>
<% System.out.println("The total amount from jsp -----"+totalamt);
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
<%//double tot_runable=(Double)request.getAttribute("Allterminal"); %>

<html:form action="/Miscellaneous?pageId=2002">

<%if(Integer.parseInt(button_val.trim())==1){ %>
<h2 class="h2">
<center>Miscellaneous Receipt</center></h2>
<core:choose>
<core:when test="${requestScope.tml_runable!=0}">
<h3 align="left" style="font-size:x-small;color: red;font-family:Comic Sans MS "><bean:message key="label.terminalrs"/><%=tml_runable %></h3>
</core:when>
</core:choose>

<%} else if(Integer.parseInt(button_val.trim())==2){ %>
<h2 class="h2">
<center>Miscellaneous Verification</center></h2>

<h3 align="left" style="font-size:x-small;color: red;font-family:Comic Sans MS "><bean:message key="label.terminalrs"/></h3>
<%} %>
<table>
<html:hidden property="flag"/>
<tr>
<td>
<table class="txtTable">
	
	<tr>
		<td align="right"><bean:message key="label.scroll_no"/></td>
		<td><html:text property="scroll_no" onblur="submit()" onkeypress="return onlynumbers()" onkeydown="return num_Limit()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
	</tr>
	
	<tr>
		
		<td align="right"><bean:message key="label.narration"/></td>
		<core:choose>
		<core:when test="${requestScope.accname!=null}">
		<td><html:textarea property="narration" value="${requestScope.accname}" disabled="true" onkeypress="return onlyalpha()"></html:textarea></td>
		</core:when>
		<core:otherwise>
	    <% if(narr!=null){%>
		<td><html:textarea property="narration" value="<%=""+narr %>"onkeypress="return onlyalpha()" disabled="true"></html:textarea></td>
	    <% }
	     else{%>
	         <td><html:textarea property="narration" onkeypress="return onlyalpha()" onkeydown="return narr_Limit()"></html:textarea></td>
	         <% }%>
		</core:otherwise>
		</core:choose>
		
	</tr>
	
	<tr>
		<td align="right"><bean:message key="label.amount"/></td>
		<core:choose>
		<core:when test="${requestScope.amount!=null}">
		<td><html:text property="amount" value="${requestScope.amount}" disabled="true" onkeypress="return onlyDoublenumbers()" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		</core:when>
		<core:otherwise>
		  <% if(amt1!=null){%>
		<td><html:text property="amount" value="<%=""+amt1 %>" disabled="true" onkeypress="return onlyDoublenumbers()"styleClass="formTextFieldWithoutTransparent"></html:text></td>
	    <% }
	     else{%>
	         <td><html:text property="amount" onkeypress="return onlyDoublenumbers()" onkeydown="return amt_Limit()" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
	         <% }%>
		</core:otherwise>
		</core:choose>
		
	</tr>
	

	
	

</table>
<table class="txtTable">
	<%System.out.println("Sow======>"+request.getAttribute("addrow")); %>

	<tr>
		<table border="1"width="25%">
		<tr>
		<td>
		Click
		</td>
		<td width="5">
		GLType
		</td>
		<td >
		GLCode      
		</td>
		<td>
		GLAmount
		</td>
		
		</tr>
	</table>
	
	<td></td> <td></td><td></td>
	<tr></tr>
		<td>
		
		<div>
		<%if(vchdataobject!=null){
			
		System.out.println("cashobject====="+vchdataobject[0].getGlType());
		System.out.println("cashobject====="+vchdataobject[0].getTransactionAmount());
		%>
		<table border=1 width="25%">
		<th>GLCode</th><th>GLType</th><th>Amount</th>
		</table>
			<table border=1 width="25%">
	<% for(int i= 0; i <vchdataobject.length;i++){%>
	<tr>
	
	<td><input type="checkbox" name="cbox" ></td>
	<td><input type="text"  size="5" value="<%=""+vchdataobject[i].getGlType()%> " disabled="true" ></td>
	<td><input type="text"  size="5" value="<%=""+vchdataobject[i].getGlCode()%>" readonly="readonly"></td>
	<td><input type="text"  size="5" value="<%=""+vchdataobject[i].getTransactionAmount()%>" readonly="readonly" ></td>
	</tr>
	<%} %>
	</table>
<%} %>
</div>
</td>

	
	<table border="1" id="addTable" width="25%">
	<tr>
	</tr>
	</table>
	

</table>
<table class="txtTable">	
	
	
	<tr>
	      
   
       	<td align="right"><bean:message key="label.gltype"/></td>
       	<%if(scroll==0){ %>
        <td>
		<html:select property="gl_type" styleId="gltype"styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
		<%if(gltype!=null){ %>
		<%for(int i=0;i<gltype.length;i++){ %>
		<html:option value="<%=gltype[i].getModuleAbbrv()%>"><%=gltype[i].getModuleAbbrv()%></html:option>
		<%}} %>
		</html:select>
		</td>
		<%}else if(scroll!=0){%>
		<td>
		<html:select property="gl_type" styleId="gltype" styleClass="formTextFieldWithoutTransparent" disabled="true" style="font-family:bold;color:blue">
		<%if(gltype!=null){ %>
		<%for(int i=0;i<gltype.length;i++){ %>
		<html:option value="<%=gltype[i].getModuleAbbrv()%>"><%=gltype[i].getModuleAbbrv()%></html:option>
		<%}} %>
		</html:select>
		</td>
		<%} %>        	
		</tr>
		<tr>
		<td align="right"><bean:message key="label.glcode"/></td>	
     	<%if(scroll==0){ %>
		<td>
		<html:select property="gl_code"  styleId="glcode" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
		<%if(glnmcd!=null){ %>
		<%for(int i=0;i<glnmcd.length;i++){ %>
		
		<html:option value="<%=""+glnmcd[i][0]%>"><%=""+glnmcd[i][0]%>----<%=""+glnmcd[i][1]%> </html:option>
		<%}} %>
		</html:select>
		</td>
		<%}else if(scroll!=0){%>
		<td>
		<html:select property="gl_code"  styleId="glcode" disabled="true"  styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
		<%if(glnmcd!=null){ %>
		<%for(int i=0;i<glnmcd.length;i++){ %>
		
		<html:option value="<%=""+glnmcd[i][0]%>"><%=""+glnmcd[i][0]%>----<%=""+glnmcd[i][1]%> </html:option>
		<%}} %>
		</html:select>
		</td>
		<%} %>
		
		
		
		</tr>
		
		<td align="right"><bean:message key="label.amount"/></td>
		<%if(scroll==0){%>	
		<td> <html:text property="gl_amount" onkeypress="return onlyDoublenumbers()" onkeydown="return gl_amount_Limit()" styleId="glamount" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue"></html:text></td>
		<%}else if(scroll!=0){%>
		<td> <html:text property="gl_amount" disabled="true" onkeypress="return onlyDoublenumbers()" styleId="glamount" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue"></html:text>
		</td>
		<% }%>
         
	<tr>
		<td align="right"><bean:message key="label.tot"/></td>
		<% if(sum!=null){%>
		<td><html:text property="total" onkeypress="return onlyDoublenumbers()" value="<%=""+sum %>"  disabled="true" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
		<%}else { %>
		<td><html:text property="total"  size="10" onkeypress="return onlyDoublenumbers()" disabled="true" styleClass="formTextFieldWithoutTransparent" value="0.0"></html:text></td>
	     <%} %>
	
</tr>
		

	
	
	<%System.out.println("Table ends");%>
	<tr>
					<TD align="right"><bean:message key="label.currdenom" /></TD>
					<td><html:select property="required" onchange="submit()" onblur="fun_denom()">
						<html:option value="Not Required"></html:option>
						<html:option value="Required"></html:option>
						</html:select>
					</td>
			</tr>
			
			
</table>
<%System.out.println("Before placing buttons");%>

<html:hidden property="but_value" value="error" />
<html:hidden property="subutton" styleId="subvalue"/>
<html:hidden property="delete" styleId="delvalue"/>
<html:hidden property="msClosed" styleId="msClosed"/>
<html:hidden property="gen_scroll" styleId="genscroll"/>
<html:hidden property="update_scroll" styleId="updatescr"/>
<%System.out.println("Before Submit button");%>
<table class="txtTable">
	<tr>
	<%System.out.println("---------At line 690-------- ");%>
	
		<%if(Integer.parseInt(button_val.trim())==1){ %>
					<%if(scroll==0){ %>
				<td><html:submit onclick="but_fun('submit')" value="Submit"styleClass="ButtonBackgroundImage">submit</html:submit></td>
	            <td><html:button property="addbutton" onclick="addRow()" styleId="button" value="AddRow"styleClass="ButtonBackgroundImage">add row</html:button></td>	
	            <td><html:button property="deletefun" onclick="('Delete')" value="Delete" styleClass="ButtonBackgroundImage">Delete</html:button></td>
		        
					<%} else if(scroll!=0){ %>
					
				<td><html:button onclick="disable('Confirm')" property="updatefun" value="update" styleClass="ButtonBackgroundImage">update</html:button></td>
             	<td><html:button property="deletefun" onclick="sub_button('Delete')" value="Delete" styleClass="ButtonBackgroundImage">Delete</html:button></td>
                 <td><html:button property="addbutton" onclick="addRow()" styleId="button" value="AddRow"styleClass="ButtonBackgroundImage">add row</html:button></td>
	            <td><html:button property="verifyfun" onclick="sub_button('Verify')" value="Verify" styleClass="ButtonBackgroundImage">Verify</html:button></td>
	            
					<%} %>
					<td><html:button property="clear" onclick="return clearfun()" value="Clear" styleClass="ButtonBackgroundImage" ></html:button></td>
					<%}
					else if(Integer.parseInt(button_val.trim())==2){ %>
					<td><html:button property="verifyfun" onclick="sub_button('Verify')" value="Verify" styleClass="ButtonBackgroundImage">Verify</html:button></td>
	                <td><html:button property="clear" onclick="return clearfun()" value="Clear" styleClass="ButtonBackgroundImage" ></html:button></td>
					<%} %>
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
		<td align="right"><html:text size="8" property="p_thousand"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<TD><html:text size="2" property="lbl_thousand" value="1000" disabled="true" styleClass="formTextField"></html:text></TD>
		<td align="center"><html:text size="8" property="r_thousand" onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_thousand" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
	
	<tr>
		<td align="right"><html:text size="8" property="pval_fivered" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_fivered"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_fivered" value="500" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_fivered"  onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_fivered" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_hundred" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_hundred"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_hundred" value="100" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_hundred"  onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_hundred" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_fifty" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_fifty"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_fifty" value="50" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_fifty"  onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_fifty" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_twenty" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_twenty"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_twenty" value="20" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_twenty" onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_twenty" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_ten" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="center"><html:text size="8" property="p_ten"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_ten" value="10" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_ten" onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_ten" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_five" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_five"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_five" value="5" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_five"  onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_five" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
	
	<tr>
		<td align="right"><html:text size="8" property="pval_two" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_two"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_two" value="2" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_two" onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_two" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_one" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="right"><html:text size="8" property="p_one"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><html:text size="2" property="lbl_one" value="1" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_one"  onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_one" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()"></html:text></td>
	</tr>
	
	<tr>
		<td align="right"><html:text size="8" property="pval_coins" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()"></html:text></td>
		<td align="center"><html:text size="8" property="p_coins"  onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td><bean:message key="label.coins"></bean:message></td>
		<td align="center"><html:text size="8" property="r_coins"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"></html:text></td>
		<td align="left"><html:text size="8" property="rval_coins"  styleClass="formTextFieldcolor" onblur="fun_exc()"></html:text></td>
	</tr>	
	
	<tr>
		
		<td></td>
		<td></td>
		<td></td>
		<td><bean:message key="label.total"></bean:message></td>
		
		<td align="left"><html:text size="8" property="cur_total" styleClass="formTextFieldcolor"></html:text></td>
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
</td>
</tr>
</table>	
	

</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>
