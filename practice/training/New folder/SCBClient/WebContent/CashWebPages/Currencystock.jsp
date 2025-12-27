<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@taglib prefix = "html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix = "bean" uri="/WEB-INF/struts-bean.tld"%>
<%@page import="java.util.Map"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.cashier.CurrencyStockObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Currency Stock</title>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">

function validations()
{
	if(document.getElementById("valid").value!=null)
	{
		if(document.getElementById("valid").value=="checkDB")
		{
			alert("Error : Please check DB entries");
			return false;
		}
		if(document.getElementById("valid").value=="cantupdate")
		{
			alert("U can't update the currency Stock as Terminal is Closed");
			return false;
		}
		if(document.getElementById("valid").value=="terminalnot")
		{
			alert("Terminal doesn't exists");
			return false;
		}
		if(document.getElementById("valid").value=="dayclose")
		{
			alert("Day closed\n You can't do any Transactions");
			return false;
		}
		if(document.getElementById("valid").value=="cashclose")
		{
			alert("Day closed\n You can't do any Transactions");
			return false;
		}
		if(document.getElementById("valid").value=="cashclose")
		{
			alert("Day closed\n You can't do any Transactions");
			return false;
		}
		if(document.getElementById("valid").value=="noentry")
		{
			alert("Error : no entry in Daily status for today");
			return false;
		}
		if(document.getElementById("valid").value=="closed")
		{
			alert("Cash Closed");
			return false;
		}
		if(document.getElementById("valid").value=="already")
		{
			alert("Cash already Closed");
			return false;
		}
		if(document.getElementById("valid").value=="noentryincurrency")
		{
			alert("No Entry in Curr Stock");
			return false;
		}
		if(document.getElementById("valid").value=="mismatch")
		{
			alert("Cannot Close\n Mismatch of Closing Balance b/w CurrStk & GLTran");
			return false;
		}
		if(document.getElementById("valid").value=="closesubcash")
		{
			alert("Cannot Close the Cash\n Close all the sub cashier tmls");
			return false;
		}
		if(document.getElementById("valid").value=="somepayment")
		{
			alert("Cannot Close the Cash\n some payments are pending");
			return false;
		}
		if(document.getElementById("valid").value=="existacpending")
		{
			alert("Error: Existing A/c Rec. Ver. is Pending");
			return false;
		}
		if(document.getElementById("valid").value=="newacpending")
		{
			alert("Error: New A/c Rec. Ver. is Pending");
			return false;
		}
		if(document.getElementById("valid").value=="miscrecacpending")
		{
			alert("Error: Misc. Rec. Ver. is Pending");
			return false;
		}
		if(document.getElementById("valid").value=="updated")
		{
		  alert("Currency Stock is Updated Successfully");
		  return false;
		}
	}
};

function disable()
{

document.forms[0].thousand.disabled=false;
document.forms[0].fivered.disabled=false;
document.forms[0].hundred.disabled=false;
document.forms[0].fifty.disabled=false;
document.forms[0].twenty.disabled=false;
document.forms[0].ten.disabled=false;
document.forms[0].five.disabled=false;
document.forms[0].two.disabled=false;
document.forms[0].one.disabled=false;
document.forms[0].coins.disabled=false;
document.forms[0].insert.disabled=true;
document.forms[0].close_button.disabled=true;
	
};
function but_update(target)
{
	
 	document.forms[0].but_value.value =target;
 
 
     var updateval=confirm("Confirm Update????");
         	
         	
         	if(updateval==true)
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
 
function but_close(target)
{
 	
	document.forms[0].but_value.value=target;
	
 	var value=confirm("Are you sure you want to close the Cash ?");
  	if(value==true)
   		{
 			document.getElementById("close_button").value="true";
 			document.forms[0].submit();
 			document.forms[0].insert.disabled=false;
		}
 		else 
 		{
 			document.getElementById("close_button").value="false";
 			return false;
 		}
 				
 			
};
function multiplvalue()
{
		var multiple=new Array(9);
		var sum=0;
		<%double runable=(Double)request.getAttribute("tml_runable");%>
		multiple[0]=document.forms[0].lbl_thousand.value * document.forms[0].thousand.value;
		document.forms[0].val_thousand.value=multiple[0];
		
		multiple[1]= document.forms[0].lbl_fivered.value * document.forms[0].fivered.value;
		document.forms[0].val_fivered.value=multiple[1];
		
		multiple[2]= document.forms[0].lbl_hundred.value * document.forms[0].hundred.value;
		document.forms[0].val_hundred.value=multiple[2];
		
		multiple[3]= document.forms[0].lbl_fifty.value * document.forms[0].fifty.value;
		document.forms[0].val_fifty.value=multiple[3];
		
		multiple[4]= document.forms[0].lbl_twenty.value * document.forms[0].twenty.value;
		document.forms[0].val_twenty.value=multiple[4];
		
		multiple[5]= document.forms[0].lbl_ten.value * document.forms[0].ten.value;
		document.forms[0].val_ten.value=multiple[5];
		
		multiple[6] = document.forms[0].lbl_five.value * document.forms[0].five.value;
		document.forms[0].val_five.value=multiple[6];
		
		multiple[7] = document.forms[0].lbl_two.value * document.forms[0].two.value;
		document.forms[0].val_two.value=multiple[7];
		
		multiple[8] = document.forms[0].lbl_one.value * document.forms[0].one.value;
		document.forms[0].val_one.value=multiple[8];
		
		multiple[9]= document.forms[0].coins.value;		
		document.forms[0].lbl_coins.value=multiple[9];
		var i=0;
		
		for(i=0;i<9;i++)
		{
			sum=sum+multiple[i];
			document.forms[0].total.value=sum;
		}
		
	var double_amount="<%=runable%>";
	
	if(double_amount>0)
	{
		var strefamount =double_amount-sum;
		document.forms[0].differnce.value=strefamount;
	}
	else if(sum > double_amount)
    {
       	var strefamount = sum-double_amount;
        document.forms[0].differnce.value=strefamount
    }
}

function onlynumbers(){
        	
        	var cha =   event.keyCode;

            if ( cha >= 48 && cha <= 57  ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
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

<body class="Mainbody" onload="validations()">
<%String value = (String)request.getAttribute("button_value");%>
<%String value1 = (String)request.getAttribute("button_val");%>
<%!boolean flag = true, flag1=false;
double tml_runable=0.0;
%>

<%if(value1!=null){ %>
	<%if(value1.equalsIgnoreCase("disabled")){
		flag1 = false;
		System.out.print("value in JSP11111=====>"+value1);
	}}%>

<%if(value!=null){ %>
	<%if(value.equalsIgnoreCase("UPDATE")){
		flag = false;
		flag1=true;
		System.out.print("value in JSP=====>"+value);
	}
	else
	{
		flag=false;
		flag1=false;
	}
	%>

	<%if(value.equalsIgnoreCase("CLEAR")){
		flag = true;
		}
	%>	

	
<%}%>	
	
	<%tml_runable=(Double)request.getAttribute("tml_runable");
	System.out.print("terminal balance"+tml_runable);	
	%>
<%String button_val=(String)request.getAttribute("submit");%>
	<%  int obj1 =(Integer)request.getAttribute("curobject1");
		int obj2=(Integer)request.getAttribute("curobject2");
		int obj3=(Integer)request.getAttribute("curobject3");
		int obj4=(Integer)request.getAttribute("curobject4");
		int obj5=(Integer)request.getAttribute("curobject5");
		int obj6=(Integer)request.getAttribute("curobject6");
		int obj7=(Integer)request.getAttribute("curobject7");
		int obj8=(Integer)request.getAttribute("curobject8");
		int obj9=(Integer)request.getAttribute("curobject9");
		double obj10=(Double)request.getAttribute("curobject10");
	%>

<%if(button_val!=null) %>
<%if(Integer.parseInt(button_val.trim())==1){ %>
<h2 class="h2">
<center>Currency Stock Updation</center></h2>
<%} else if(Integer.parseInt(button_val.trim())==2){ %>
<h2 class="h2">
<center>Cash Closing</center></h2>
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

<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Currency?pageId=2007">
	<table align="center" class="txtTable">
	<tr>
	<td>
	<font color="red">Terminal balance:<%=tml_runable%></font>
	</td>
	
	</tr>
	</table>
	
	<table cellspacing="10" style="border:thin solid #339999;" align="center" class="txtTable">
		
		<tr><td><bean:message key="label.denom"></bean:message></td>
			<td align="center"><bean:message key="label.no"></bean:message></td>
			<td align="center"><bean:message key="label.amount"></bean:message></td>
		</tr>
		
		<tr>
		<TD><html:text size="8" property="lbl_thousand" value="1000" disabled="true" styleClass="formTextField"></html:text></TD>
		<td align="center"><html:text size="8" property="thousand" disabled="<%=flag%>" value="<%=""+obj1 %>" onblur="multiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent"><%=""+obj1 %></html:text></td>
		<td align="right"><html:text size="8" property="val_thousand" readonly="true" styleClass="formTextFieldcolor"></html:text></td>
		</tr>
		
		<tr>
		<td><html:text size="8" property="lbl_fivered" value="500" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="fivered" value="<%=""+obj2 %>" disabled="<%= flag %>" onblur="multiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent">"<%=""+obj2 %>"</html:text></td>
		<td align="right"><html:text size="8" property="val_fivered" readonly="true" styleClass="formTextFieldcolor"></html:text></td>
		</tr>
		
		
		<tr>
		<td><html:text size="8" property="lbl_hundred" value="100" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="hundred" value="<%=""+obj3 %>" disabled="<%=flag%>" onblur="multiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent">"<%=""+obj3 %>"</html:text></td>
		<td align="right"><html:text size="8" property="val_hundred" readonly="true" styleClass="formTextFieldcolor"></html:text></td>
		</tr>
		
		<tr>
		<td><html:text size="8" property="lbl_fifty" value="50" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="fifty" value="<%=""+obj4 %>" disabled="<%=flag%>" onblur="multiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent">"<%=""+obj4 %>"</html:text></td>
		<td align="right"><html:text size="8" property="val_fifty" readonly="true" styleClass="formTextFieldcolor"></html:text></td>
		</tr>
		
		<tr>
		<td><html:text size="8" property="lbl_twenty" value="20" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="twenty" value="<%=""+obj5 %>" disabled="<%=flag%>" onblur="multiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent">"<%=""+obj5 %>"</html:text></td>
		<td align="right"><html:text size="8" property="val_twenty" readonly="true" styleClass="formTextFieldcolor"></html:text></td>
		</tr>
		
		
		<tr>
		<td><html:text size="8" property="lbl_ten" value="10" disabled="true"  styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="ten" value="<%=""+obj6 %>" disabled="<%=flag%>" onblur="multiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent">"<%=""+obj6 %>"</html:text></td>
		<td align="right"><html:text size="8" property="val_ten" readonly="true" styleClass="formTextFieldcolor"></html:text></td>
		</tr>
		<tr>
		<td><html:text size="8" property="lbl_five" value="5" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="five" value="<%=""+obj7 %>" disabled="<%=flag%>" onblur="multiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent">"<%=""+obj7 %>"</html:text></td>
		<td align="right"><html:text size="8" property="val_five" readonly="true" styleClass="formTextFieldcolor"></html:text></td>
		</tr>
		
		<tr>
		<td><html:text size="8" property="lbl_two" value="2" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="two" value="<%=""+obj8 %>" disabled="<%=flag%>" onblur="multiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent">"<%=""+obj8 %>"</html:text></td>
		<td align="right"><html:text size="8" property="val_two" readonly="true" styleClass="formTextFieldcolor"></html:text></td>
		</tr>
		
		<tr>
		<td><html:text size="8" property="lbl_one" value="1" disabled="true" styleClass="formTextField"></html:text></td>
		<td align="center"><html:text size="8" property="one" value="<%=""+obj9 %>" disabled="<%=flag%>" onblur="multiplvalue()" onkeypress="return onlynumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent">"<%=""+obj9 %>"</html:text></td>
		<td align="right"><html:text size="8" property="val_one" readonly="true" styleClass="formTextFieldcolor"></html:text></td>
		</tr>
		
		<tr>
		<td><bean:message key="label.coins"></bean:message></td>
		<td align="center"><html:text size="8" property="coins" value="<%=""+obj10 %>" disabled="<%=flag%>" onblur="multiplvalue()" onkeypress="return onlyDoublenumbers()" onkeyup="numberlimit(this,'6')" styleClass="formTextFieldWithoutTransparent">"<%=""+obj10 %>"</html:text></td>
		<td align="right"><html:text size="8" size="8" property="lbl_coins" readonly="true" styleClass="formTextFieldcolor"></html:text></td>
		</tr>
		
		<tr>
		<td><bean:message key="label.total"></bean:message></td>
		<td></td>
		<td align="right"><html:text size="8" property="total" readonly="true" style="color:red" styleClass="formTextField"></html:text></td>
		</tr>
		
		<tr>
			<td><html:text size="8" property="differnce" readonly="true" style="color:red" styleClass="formTextField"></html:text></td>
		</tr>
	
	

</table>
	<html:hidden property="but_value"></html:hidden>
	<html:hidden property="upbutton" styleId="update" value="error"/>
	<html:hidden property="clbutton" styleId="close"/>
	<html:hidden property="validation" styleId="valid" />
	<html:hidden property="update_sucess" styleId="update_sucess"/>

<table align="center" class="txtTable">
	<tr>
		<td>
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[3]=='1'){%>
		<html:button property="up_button" onclick="but_update('update')" value="Update" styleClass="ButtonBackgroundImage" disabled="<%=flag1 %>">Update</html:button>
		<%}else{%>
		<html:button property="up_button" onclick="but_update('update')" value="Update" styleClass="ButtonBackgroundImage" disabled="true">Update</html:button>
		<%}%>
		</td>
		
		<td>
		<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
		<html:button property="insert" onclick="disable()" value="Insert" styleClass="ButtonBackgroundImage" disabled="<%=flag1 %>">Insert</html:button>
		<%}else{%>
		<html:button property="insert" onclick="disable()" value="Insert" styleClass="ButtonBackgroundImage" disabled="true">Insert</html:button>
		<%} %>
		</td>
		<%if(Integer.parseInt(button_val.trim())==2){ %>
		
		<td><html:button property="close_button" onclick="but_close('close')" value="Close" styleClass="ButtonBackgroundImage" >Close</html:button></td>
		<%} %>
		<td>
		
		<html:button property="B3" onclick="return clearfun()" styleClass="ButtonBackgroundImage">Clear</html:button></td>
</tr>
</table>

</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
<%} %>

</body>
</html>