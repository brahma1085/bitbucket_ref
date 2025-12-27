<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.backOffice.VoucherDataObject" %>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Vector"%>
<%@page import="com.scb.csh.forms.PaymentForm"%>
<%@page import="com.scb.bk.forms.PaymentVoucherForm"%>

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
<title>payment voucher</title>

  
    
    <script type="text/javascript">
    
  function onlynumbers()
{
        	
        	var cha =   event.keyCode;

            if ( cha >= 48 && cha <= 57  ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        };
    
   
    function set(target)
    {
    alert(target);
    document.forms[0].forward.value=target;
    };
    function getvalue(cv)
    {
    alert(""+cv);
    document.forms[0].totaldebitamt.value=cv;
      
    }
    //by mohsin
    function copyvalue(c){
    alert("hi");
    document.forms[0].amount.value=c;
    var v=document.forms[0].totalcreditamt.value;
    if(document.forms[0].cbox.value==0){
    document.forms[0].col1.value=v;
    }
    
    }
    //end
    function callClear(target){
           document.forms[0].forward.value=target;
           document.forms[0].narration.value="";
         	 var ele= document.forms[0].elements;
             for(var i=0;i<ele.length;i++){
               if(ele[i].type=="text"){
                  
               ele[i].value="";
               }
               if(ele[i].type=="hidden")
               {
                 ele[i].value="";
               }
           
           }
           
            };
            
 function disable()
{
 alert(document.forms[0].updatefun.value);
 if(document.forms[0].updatefun.value=="update")
 {
  document.forms[0].creditamt.disabled=false;
  document.forms[0].amount.disabled=false;
  document.forms[0].narration.disabled=false;
 }
};


  function addRow()
{

var gltype=document.forms[0].gltype.value;

var glcode=document.forms[0].glcode.value;
var amount=document.forms[0].amount.value;

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
e1.setAttribute('value', ' '+itr);
cellRight.appendChild(e1);

var cellRight = row.insertCell(2);
var e2 = document.createElement('input');
e2.setAttribute('type', 'text');
e2.setAttribute('name', 'gltype');
e2.setAttribute('size', '3');
e2.setAttribute('value',gltype);

cellRight.appendChild(e2);



var cellRight = row.insertCell(3);
var e3 = document.createElement('input');
e3.setAttribute('type', 'text');
e3.setAttribute('name', 'glcode');
e3.setAttribute('size', '3');
e3.setAttribute('value',glcode);


cellRight.appendChild(e3);



var cellRight = row.insertCell(4);
var e6 = document.createElement('input');
e6.setAttribute('type', 'text');
e6.setAttribute('name', 'cind');
e6.setAttribute('size', '1');
e6.setAttribute('value','D');
cellRight.appendChild(e6);


var cellRight = row.insertCell(5);
var e7 = document.createElement('input');
e7.setAttribute('type', 'text');
e7.setAttribute('name', 'amount');
e7.setAttribute('size', '4');
e7.setAttribute('value',amount);

cellRight.appendChild(e7);

};



            
         
            
     
           
            
            function fromCreditAmount(amntVal){
            
            document.forms[0].totalcreditamt.value=amntVal;
            
            };
            
            function fromDebitAmount(amnt){
            
            
            document.forms[0].totaldebitamt.value=amnt;
            
            
            alert(amt);
            
            };
            
            function validate()
            {
             if(document.forms[0].narration.value=="")
             {
               alert("Plese Enter Narration")
             }
             if(document.forms[0].gltype.value=="Select")
             {
              alert("GLType Not Selected")
             }
            }
            
           
            
            function fromCreditAmount(amntVal){
            if(document.forms[0].creditamt.value=="0.0")
            {
              alert("Credit Amount Not Entered")
            }
           
            
            document.forms[0].totalcreditamt.value=amntVal;
            
            };
            
 function button_insert(target)
 {
   if(document.forms[0].creditamt.value=="0.0"||document.forms[0].narration.value=="")
             
            {
              alert("Please Enter All The Details ")
            }
            
            else if(document.forms[0].amount.value=="0.0")
            {
                alert("Please Enter All The Details ")
            }
         else{
          	
          	document.forms[0].forward.value=target;
            var entryval=confirm("Do you want to submit ????");
            if(entryval==true)
            	{
           		 	document.getElementById("vch").value="true";
           			document.forms[0].submit();
           			 
           			return true;
           			
         		}
          	else 
          		{
          			document.getElementById("vch").value="false";
          				return false;
          		}
            }
            }
         
       function button_delete(target)
        {
      
       document.forms[0].forward.value=target;
     
      var deleteval=confirm("Are you sure you want to delete the voucher no ???");
 		if(deleteval==true)
 		{
 		
 			
 			document.forms[0].submit();
 			return true;
		}
 		else 
 		{
 			return false;
 		}
      
        };
    
       function button_update(target)
        {
    
       document.forms[0].forward.value=target;
       	var value=confirm("R u sure you want to update??");
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
           
          
      
        };
        
        
       function button_verify(target)
        {
           
           document.forms[0].forward.value=target;
           	var value=confirm("Are you sure you want to verify ???");
   		if(value==true)
   		{
 			document.getElementById("verify").value="true";
 			document.forms[0].submit();
		}
    	else 
 		{
 			document.getElementById("verify").value="false";
 			return false;
 		}
           
          
           
        }; 
        
    
            
    </script>
	 		      

</head>
<%Vector vec_glcode=(Vector)request.getAttribute("glcode");

String gldesc=(String)request.getAttribute("gldesc");
int vch_no=(Integer)request.getAttribute("vchno");
ModuleObject[] gltype=(ModuleObject[])request.getAttribute("gltype");
PaymentVoucherForm payform=new PaymentVoucherForm();
%>

<%! String date; %>
<% date=(String)request.getAttribute("date");
   System.out.println("Date1233336------>"+date);
%>
<%String[][] glnmcd=(String[][])request.getAttribute("glnmcd"); %>
<body class="Mainbody" style="overflow: scroll;" onload="instruction()"()>
<%
Double sum = (Double)request.getAttribute("sum");
System.out.println("THe value of sum ===="+sum);
String msg=(String)request.getAttribute("msg");
System.out.println("Do yu hav a problem");
VoucherDataObject[] vchdataobject =(VoucherDataObject[])request.getAttribute("vchdataobject");
VoucherDataObject[] focback =(VoucherDataObject[])request.getAttribute("focback");
%>

<%@page import="java.util.Map"%>


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
<center><h2 class="h2">Payment Voucher</h2></center>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/BackOffice/PaymentVoucher?pageId=11016">
<body>
<br><br>

<%if(msg!=null){ %>
<font color="red"><%=msg %></font>
<%} %>

<% String displaymsg=(String)request.getAttribute("displaymsg");%>
<%if(displaymsg!=null){ %>
<font style="font-style:normal;font-size:12px" color="red"><%=displaymsg %></font>
<%} %>

<table align="left">
<tr>


<table  style="border:thin solid #339999;">
<thead><center><b>Narration Entry</b></center></thead>
<tr>
<td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.vchnumber" ></bean:message></font></td>
<td><html:text property="vchnum" size="8" onkeypress="return onlynumbers()"  onblur="submit()" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
</tr>
<tr>
<td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.date" ></bean:message></font></td>
<td><html:text property="date" size="9" disabled="true" value="<%=""+date%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
</tr>
<tr>
<td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.creditamt" ></bean:message></font></td>
<td><html:text   property="creditamt" onkeypress=" return onlynumbers()"  size="8"  value="0.0" styleClass="formTextFieldWithoutTransparent" onblur="fromCreditAmount(this.value)" ></html:text></td>
</tr>
<%if(focback!=null){ %>
<tr>
<td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.narr" ></bean:message></font></td>
<td><html:textarea  property="narration"  cols="10" rows="3" value="<%=""+focback[0].getNarration()%>" onblur="validate()" ></html:textarea></td>
</tr>
    
<%} else{%>
<tr>
<td align="right" ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.narr" ></bean:message></font></td>
<td><html:textarea  property="narration"  value=" " cols="10" rows="3"  onblur="validate()" ></html:textarea></td>
</tr>
<%} %>
</table>
</tr>

<tr></tr>


<tr>
<table class="txtTable">
	<%System.out.println("Sow======>");%>

	<tr>
		<table border="1"width="25%" bordercolor="black">
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
		CDInd
		</td>
		<td>
		Amount
		</td>

	</tr>
	
	</table>
	</tr>
	
	</table>
	</tr>
	<table border="1" id="addTable" width="25%" bordercolor="black">
	<tr>
	</tr>
	</table>
		<div>
		<%if(vchdataobject!=null){
		%>
		<table border=1 width="25%" bordercolor="black">
		<td>GLCode</td><td>GLType</td><td>CDInd</td><td>Amount</td>
		
	<% for(int i= 0; i<vchdataobject.length;i++){%>
	<tr>

	<td><input type="text" disabled="true" size="5" value="<%=""+vchdataobject[i].getGlType()%>"></td>
	<td><input type="text" disabled="true" size="3" value="<%=""+vchdataobject[i].getGlCode()%>"></td>
	<td><input type="text" disabled="true" size="1" value="<%=""+vchdataobject[i].getCdIndicator()%>"></td>
	<td><input type="text" disabled="true" size="5" value="<%=""+vchdataobject[i].getTransactionAmount()%>"></td>
	</tr>
	<%} %>
	</table>
<%} %>
</div>
	
	


<tr>
<table style="border:thin solid #339999;" align="left">
<thead><center><b>Transaction Entry</b></center></thead>
<tr>
<td ><font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.creditamt" ></bean:message></font>
<%if(request.getAttribute("credit")!=null){ %>
<html:text property="totalcreditamt"  onkeypress="return onlynumbers()"  size="8" value="<%=""+request.getAttribute("credit") %>" styleClass="formTextFieldWithoutTransparent" disabled="true"></html:text>
<%}else{ %>
<html:text property="totalcreditamt" onkeypress="return onlynumbers()"  size="8" value="0.0" styleClass="formTextFieldWithoutTransparent" disabled="true"></html:text>
<%} %>
<font style="font-style:normal;font-size:12px" color="brown"><bean:message key="label.debitamt" ></bean:message></font>
<% if(sum!=null){%>
<html:text property="totaldebitamt" size="8" onkeypress="return onlynumbers()"  value="<%=""+request.getAttribute("sum") %>" styleClass="formTextFieldWithoutTransparent"  disabled="true"></html:text>
<% }else if(request.getAttribute("debit")!=null){ %>
<html:text property="totaldebitamt" size="8" onkeypress="return onlynumbers()"  value="<%=""+request.getAttribute("debit")%>" styleClass="formTextFieldWithoutTransparent"  disabled="true"></html:text>

<% }else{ %>
<html:text property="totaldebitamt" onkeypress="return onlynumbers()"   size="8"  value="0.0" styleClass="formTextFieldWithoutTransparent"  disabled="true"></html:text>
<%} %>
</td>
</tr>
<td>
<tr></tr><tr></tr><tr></tr><tr></tr>
<tr>
<td>
<center>
<table>
<tr>
<td align="right"><bean:message key="label.gltype"/></td>
<td>
<html:select property="gltype">

<%for(int j=0;j<gltype.length;j++){ %>
<html:option value="<%=gltype[j].getModuleCode() %>" ><%=gltype[j].getModuleAbbrv()%></html:option>
<%} %>
</html:select>
</td>
</tr>
<tr>
<td align="right"><bean:message key="label.glcode"/></td>
<td>
<html:select property="glcode">
	<%if(glnmcd!=null){ %>
		<%for(int i=0;i<glnmcd.length;i++){ %>
		
		<html:option value="<%=""+glnmcd[i][0]%>"><%=""+glnmcd[i][0]%>----<%=""+glnmcd[i][1]%> </html:option>
		<%}} %>
		</html:select>
</td>
</tr>
<tr>
<td align="right"><bean:message key="label.amount"/></td>
<td>
<input type="text" name="amount" onkeypress="return onlynumbers()" onblur="fromDebitAmount(this.value)" value="0.0" size="8"  >


</td>



</tr>

</table>


</center>
</td>

<tr></tr><tr></tr><tr></tr><tr></tr>




<tr></tr><tr></tr><tr></tr><tr></tr>
<tr>
<html:hidden property="forward" value="error"></html:hidden>
<html:hidden property="vouchervalue" styleId="vch"/>
<html:hidden property="paym_vch" styleId="paym_vch"/>
<html:hidden property="scr_verify" styleId="scr_verify"/>
<html:hidden property="delete" styleId="delvalue"/>
<html:hidden property="deling" styleId=" deleting"/>
<html:hidden property="updated" styleId="updated"/>
<html:hidden property="ver_button" styleId="verify"></html:hidden>
<td>
    <%if(vch_no==0){ %>
    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
    <html:button property="sub" onclick="return button_insert('submit')"  styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message> </html:button>
    <%}else{ %>
    <html:button property="sub" disabled="true" onclick="return button_insert('submit')"  styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message> </html:button>
    <%} %>
   
   	<html:button property="addbutton" onclick="addRow()" styleId="button" value="AddRow"styleClass="ButtonBackgroundImage">label.addrow</html:button>
   	<html:button property="clear" onclick="callClear()" value="Clear" styleClass="ButtonBackgroundImage" ></html:button>
    <%}else{%>
    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
    <html:button property="ver" onclick="button_verify('verify')" styleClass="ButtonBackgroundImage"><bean:message key="label.verify"></bean:message> </html:button>
    <%}else{ %>
    <html:button property="ver" disabled="true" onclick="button_verify('verify')" styleClass="ButtonBackgroundImage"><bean:message key="label.verify"></bean:message> </html:button>
    <%} %>
    
    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[2]=='1'){%>
    <html:button property="del" onclick="button_delete('delete')" styleClass="ButtonBackgroundImage"><bean:message key="label.delete"></bean:message> </html:button>
    <%}else{ %>
    <html:button property="del" disabled="true" onclick="button_delete('delete')" styleClass="ButtonBackgroundImage"><bean:message key="label.delete"></bean:message> </html:button>
    <%} %>
   
    <html:button property="clear" onclick="callClear()" value="Clear" styleClass="ButtonBackgroundImage" ></html:button>
    <% }%>	
</td>
</tr>
</table>
</tr>


</table>




</body>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</html>