<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">

function onlyDoublenumbers(){
        	
        	var cha = event.keyCode;

            if ( (cha >= 48 && cha <= 57 )  || cha == 46 ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        };
        
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
 	alert("Digit Limit is 10");
 	return false;
 	}
 	}

function onlynumbers()
{
        	
        	var cha =   event.keyCode;

            if ( cha >= 48 && cha <= 57  ) {
            
            		return true;
            		
        	} else {
        		
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
		var multiple=new Array(8);
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
		
		
		for(i=0;i<8;i++)
		{
			sum=sum+multiple[i];
			document.forms[0].total.value=sum;
		}
	};	
	
function pmultiplvalue()
	{ 
		var multiple=new Array(8);
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
		
		
		for(i=0;i<8;i++)
		{
			sum1=sum1+multiple[i];
			document.forms[0].refund.value=sum1;
		}
		
		
	};    
	
	function fun_exc()
	{
		
    }
 		
	 

function validations()
{
	alert(document.forms[0].error1.value);
	
    if(document.getElementById('valid').value=="exchanged")
 	{
 	  alert("Currency Exchanged sucessfully");
 	} 
		if(document.forms[0].gen_scroll.value!=0)
 			{
 				alert("Terminal closed...Note the Scroll No"+document.forms[0].gen_scroll.value);
 				return false;
 			}
 		
 		if(document.forms[0].strecamount.value!=0)
 			{
 				alert("You are paying more"+document.forms[0].strecamount.value);
 				return false;
 			}
		if(document.getElementById("close").value!=null)
 		{
 			if(document.getElementById("close").value=="dayclose")
		 		{
 					alert("Day closed\n You can't do any Transactions");
 					return false;
 				}
 			if(document.getElementById("close").value=="cashclose")
		 		{
 					alert("Cash closed\n You can't do any Transactions");
 					return false;
 				}
 			if(document.getElementById("close").value=="Error")
		 		{
 					alert("Error : no entry in Daily status for today");
 					return false;
 				}
 			if(document.getElementById("close").value=="access")
		 		{
 					alert("U dont have an access to close the terminal");
 					return false;
 				}
 			if(document.getElementById("close").value=="doesnot")
		 		{
 					alert("Terminal does not exist");
 					return false;
 				}
 			if(document.getElementById("close").value=="exception")
		 		{
 					alert("Scroll No. not found");
 					return false;
 				}
 			
 				
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
 					32
 					
 					
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
 			if(document.getElementById("valid").value=="refund")
		 		{
 					alert("Refund the amount");
 					return false;
 				}
 			if(document.getElementById("valid").value=="invaliddata")
		 		{
 					alert("Invalid data entry");
 					return false;
 				}
 				
 		  
 		    
 			if(document.getElementById("valid").value=="denied")
		 		{
 					alert("Access denied: U cannot alter this Scroll No");
 					return false;
 				}
 			if(document.getElementById("valid").value=="invalid")
		 		{
 					alert("Invalid Scroll No");
 					return false;
 				}
 			if(document.getElementById("valid").value=="accept")
		 		{
 					alert("This Scroll No is already Accepted");
 					return false;
 				}
 		}
 			
};

function termclose(target)
{
    alert(target);
	
 
 	var value=confirm("Data OK 111111111111??");
   if(value==true)
   	{
 		document.getElementById("value").value="true"
 		document.forms[0].submit();
	}
 	else 
 	{
 	document.getElementById("value").value="false";
 	return false;
 	}
};

 	
 function but_submit(target)
 	{
 	  var refund= document.forms[0].refund.value
 	  
 	  var total=document.forms[0].total.value
 	  
 	  
 	 if(parseInt(document.forms[0].amount.value)!=parseInt(refund))
 	{
 	  alert("Invalid Data Entry");
 	}
 	
    else
     {
        if(refund<total)
        {
       alert("You r receving more amount")
        }
        else if(refund>total)
        {
        alert("You Are Refunding More Amount")
        } 	  
 	     else{
 	     alert(target)   
 	     var value=confirm("Data Ok???");
 	    if(value==true)
 	      {
 	       document.getElementById("currency").value="true";
 	       document.forms[0].submit();
 	     
 	     }
 	   
 	   else
 	   {
 	   document.getElementById("currency").value="false";
 	   }
 	   }
 	 
 	}
 
 	};
 	
 	

function but_update(target)
 	{
 		<%double trunable=(Double)request.getAttribute("sub_runable");%>
 		var amount="<%=trunable%>";
 		document.forms[0].but_value.value =target;
 		alert(target);
 		if( document.forms[0].refund.value!=null)
 		{
 			if( document.forms[0].refund.value==0)
 			{
 				var value=confirm("Data OK ??");
   				if(value==true)
   				{
 					document.getElementById("update").value="true";
 					document.forms[0].submit();
				}
 				else 
 				{
 					document.getElementById("update").value="false";
 					return false;
 				}
 			}	
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
<body class="Mainbody" onload="validations()">
<%String value = (String)request.getAttribute("button_value");
System.out.println("value in jsp........."+value);%>

<%!boolean flag = true;%>
<%if(value!=null)
	{
	if(value.equalsIgnoreCase("UPDATE"))
		{
			flag = false;
			System.out.print("value in JSP=====>"+value);
		}
	else
		{
			flag=false;
		}
	
	if(value.equalsIgnoreCase("CLEAR"))
		{
			flag = true;
		}
	}%>	

	
	
<%double tml_runable=(Double)request.getAttribute("tml_runable");%>
<%String sucess=(String)request.getAttribute("sucess"); %>
<%double sub_runable=(Double)request.getAttribute("sub_runable");%>
<%String button_val=(String)request.getAttribute("submit");%>
<%if(Integer.parseInt(button_val.trim())==1){ %>
<h2 class="h2">
<center>Currency Exchange</center></h2>


<%} %>


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

<%if(sucess!=null){ %>
<font color="red"><h3 align="left" style="font-size:x-small;color: red"><%=sucess%></h3></font>
<% }%>
<%if(Integer.parseInt(button_val.trim())==2){ %>
<h2 class="h2">
<center>Terminal Closing</center></h2>
<%if(sub_runable!=0){%>
	<h3 align="left" style="font-size:x-small;color: red;font-family:Comic Sans MS "><bean:message key="label.subcashier"/><%=sub_runable%></h3>
	
	<%}%>
<%} %>

<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Exchange?pageId=2005">

<table class="txtTable">
<tr>
<td>
	<table class="txtTable">
	<%if(Integer.parseInt(button_val.trim())==1){ %>
	<tr>
		<TD><bean:message key="lable.ammount"/></TD>
		<td><html:text property="amount" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()" onkeydown="return amt_Limit()"></html:text></td>
		<td><html:button onclick="but_submit('Submit')" property="but_sub" value="Submit" styleClass="ButtonBackgroundImage">Submit</html:button></td>
	</tr>
	<%} %>
	<%if(Integer.parseInt(button_val.trim())==2){ %>
	<tr>
		<td><bean:message key="label.scroll_no"/></td>
		<td><html:text property="scroll_no" onblur="submit()" styleClass="formTextFieldWithoutTransparent" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" value="0"></html:text></td>
	</tr>
	<tr>
	
		<td><bean:message key="label.sent_to"/></td>
		<td><html:text property="send_to" styleClass="formTextFieldWithoutTransparent" value=""></html:text></td>
		
	</tr>
	
	<tr>
		<td><bean:message key="label.Amt"/></td>
		<td><html:text property="amount" styleClass="formTextFieldWithoutTransparent" onkeypress="return doublenumbers()" value="0.0"></html:text></td>
	</tr>
	
	<html:hidden property="but_value" styleId="subvalue" value="error" />
	<html:hidden property="close" styleId="closeds" />
	<html:hidden property="validation" styleId="valid"/>
	<html:hidden property="error1"/>
	<html:hidden property="value" styleId="value"/>
	<html:hidden property="gen_scroll"/>
	<html:hidden property="closed" styleId="close" />

	<html:hidden property="upbutton" styleId="update"/>
	
		<table class="txtTable">
			<tr>
				<td><html:submit onclick="termclose('Close')" value="Close" styleClass="ButtonBackgroundImage">Close</html:submit></td>
				<td>
				<%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
				<html:submit styleClass="ButtonBackgroundImage" onclick="but_update('Update')" value="Update">Update</html:submit>
				<%}else{ %>
				<html:submit styleClass="ButtonBackgroundImage" onclick="but_update('Update')" value="Update" disabled="true">Update</html:submit>
				<%} %>
				</td>
				<td><html:button property="clear" onclick="return clearfun()" value="Clear" styleClass="ButtonBackgroundImage"></html:button></td>
			</tr>
			
		</table>
	<%} %>
	</table>	

	<table cellspacing="10" style="border:thin solid #339999;" align="center" class="txtTable">
		
	<tr>
		<td align="center"><bean:message key="label.amount"></bean:message></td>
		<td align="center"><bean:message key="label.no"></bean:message></td>
		<td></td>
		<td align="center"><bean:message key="label.no"></bean:message></td>
		<td align="center"><bean:message key="label.amount"></bean:message></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_thousand" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" value="0.0"></html:text></td>
		<td align="right"><html:text size="8" property="p_thousand"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
		<TD><html:text size="2" property="lbl_thousand" value="1000" disabled="true" styleClass="formTextField"></html:text></TD>
		<td align="center"><html:text size="8" property="r_thousand" onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
		<td align="left"><html:text size="8" property="rval_thousand" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()" value="0.0"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_fivered" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" value="0.0"></html:text></td>
		<td align="right"><html:text size="8" property="p_fivered"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
		<td><html:text size="2" property="lbl_fivered" value="500" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_fivered"  onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
		<td align="left"><html:text size="8" property="rval_fivered" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()" value="0.0"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_hundred" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" value="0.0"></html:text></td>
		<td align="right"><html:text size="8" property="p_hundred"  onblur="pmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
		<td><html:text size="2" property="lbl_hundred" value="100" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_hundred"  onblur="rmultiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
		<td align="left"><html:text size="8" property="rval_hundred" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()" value="0.0"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_fifty" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" value="0.0"></html:text></td>
		<td align="right"><html:text size="8" property="p_fifty"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
		<td><html:text size="2" property="lbl_fifty" value="50" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_fifty"  onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent" value="0"> </html:text></td>
		<td align="left"><html:text size="8" property="rval_fifty" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()" value="0.0"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_twenty" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" value="0.0"></html:text></td>
		<td align="right"><html:text size="8" property="p_twenty"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
		<td><html:text size="2" property="lbl_twenty" value="20" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_twenty" onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
		<td align="left"><html:text size="8" property="rval_twenty" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()" value="0.0"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_ten" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" value="0.0"></html:text></td>
		<td align="center"><html:text size="8" property="p_ten"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
		<td><html:text size="2" property="lbl_ten" value="10" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_ten" onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
		<td align="left"><html:text size="8" property="rval_ten" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()" value="0.0"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_five" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" value="0.0"></html:text></td>
		<td align="right"><html:text size="8" property="p_five"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
		<td><html:text size="2" property="lbl_five" value="5" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_five"  onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
		<td align="left"><html:text size="8" property="rval_five" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()" value="0.0"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_two" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" value="0.0"></html:text></td>
		<td align="right"><html:text size="8" property="p_two"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
		<td><html:text size="2" property="lbl_two" value="2" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_two" onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
		<td align="left"><html:text size="8" property="rval_two" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()" value="0.0"></html:text></td>
	</tr>
		
	<tr>
		<td align="right"><html:text size="8" property="pval_one" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" value="0.0"></html:text></td>
		<td align="right"><html:text size="8" property="p_one"  onblur="pmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
		<td><html:text size="2" property="lbl_one" value="1" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="r_one"  onblur="rmultiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
		<td align="left"><html:text size="8" property="rval_one" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" onblur="fun_exc()" value="0.0"></html:text></td>
	</tr>
		
	
	<tr>
		<td><html:text size="8" property="refund" readonly="true" styleClass="formTextFieldcolor" value="0.0"></html:text></td>
		<td></td>
		<td><bean:message key="label.total"></bean:message></td>
		<td></td>
		<td align="left"><html:text size="8" property="total" readonly="true" styleClass="formTextFieldcolor" onkeypress="return onlyDoublenumbers()" value="0.0"></html:text></td>
	</tr>
	
	<html:hidden property="exchange" styleId="currency"/>
	
	<html:hidden property="strecamount"/>

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