<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@page import="masterObject.general.ModuleObject"%>
<html>
<head>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function only_numbers_doublevalue() {
	
	var cha=event.keyCode;
	if((cha>=48 && cha<=57) || cha==46)
	{
		return true;
	}
	else
	{
		return false;
	}
}  ;






function only_numbers()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57) ) 
		 {
   		 	return true;
         } 
          else 
          {
   			return false;
          }
	};
	
function fuc_submit(target)
{

   		
   		if(document.getElementById("trf_accno").value=="0")
	{
		alert('Enter the Transfer Account number!');
		document.getElementById("trf_accno").focus();
		return false;
		}else if(document.getElementById("trf_accno").value=="")
	{
		alert('Transfer Account number cannot be Blank!');
		document.getElementById("trf_accno").focus();
		return false;
		}
		
		
		else{
	document.forms[1].button_value.value=target;
	document.forms[1].submit();
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
         		alert("You can't enter more than "+((parseInt(size))-1)+" digits!!");
         		txt.value="";
         		return false;
          	}
         };
   


	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<%!String Valid_account=null;%>
<%Valid_account = (String)request.getAttribute("Valid_account");%>

<%ModuleObject ModuleobjTrf[];%>
<%ModuleobjTrf =(ModuleObject[])request.getAttribute("ModuleobjTrf");
  System.out.println("Module Obj Transfer===>"+ModuleobjTrf); 	
%>




<body>
<html:form action="/Loans/LoanRecovery?pageidentity.pageId=5017">
<table>
   <tr>
   <td align="right">
    <bean:message key="label.trf_vocuherno"></bean:message>
    <html:text property="trf_voucherno"  onchange="submit()" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_Numbers()" onkeyup="numberlimit(this,'11')"></html:text>
   </td> 
   </tr>
   
   <tr>
   <td align="right">
    <bean:message key="label.trffrom"></bean:message>
    <html:select property="trf_from" styleClass="formTextFieldWithoutTransparent">
    <%if(ModuleobjTrf!=null ){%>
    	<%for(int i=0;i<ModuleobjTrf.length;i++){ %>
    	<html:option value="<%= ModuleobjTrf[i].getModuleCode() %>"><%= ModuleobjTrf[i].getModuleAbbrv()%></html:option>
    <%} }%>
    </html:select>
    
    <html:text property="trf_accno"  onblur="submit()" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers()" onkeyup="numberlimit(this,'11')"></html:text>
   </td> 
   
   <td>
       <html:text property="name" size="18" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
   </td>
   
  <td>
   <%if(Valid_account!=null)
   {%>
   	<html:text property="validacc" value="<%= Valid_account %>" size="15" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text> 
    
  <%} %>
   </td>
   </tr>
   
   <tr>
   <td align="right">
    <bean:message key="label.balance"></bean:message>
    <html:text property="balance"  style="border:transparent;background-color:beige;color:red" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text>
   </td> 
   
   <td align="right">
    <bean:message key="label.amt"></bean:message>
    <html:text property="amount" size="8" onchange="submit()" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_numbers_doublevalue()" onkeyup="numberlimit(this,'11')"></html:text>
   </td> 
   </tr> 
   <html:hidden property="acc_notfound" styleId="notfound"></html:hidden>
   <html:hidden property="button_value" value="error"/>
   <tr>
     <td align="right">
       <html:button property="Submit" value="Submit" styleClass="ButtonBackgroundImage" onclick="fuc_submit('Submit')">Submit</html:button>
       <html:button property="verify" value="Verify" styleClass="ButtonBackgroundImage" onclick="fuc_submit('verify')">Verify</html:button>
       <html:button property="Update" value="Update" styleClass="ButtonBackgroundImage">Update</html:button>
       <html:button property="Delete" value="Delete" styleClass="ButtonBackgroundImage">Delete</html:button>
       
     </td>
   </tr>
   
   
   <tr>
     <td align="right">
       <bean:message key="label.advance"></bean:message>
       <html:text property="advance" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
     </td>
   </tr>
   
   
   <tr>
     <td align="right">
       <bean:message key="label.principle"></bean:message>
       <html:text property="principle" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
     </td>
   </tr>
   
   <tr>
     <td align="right">
       <bean:message key="label.interest"></bean:message>
       <html:text property="interest" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
     </td>
   </tr>
   
   
   <tr>
     <td align="right">
       <bean:message key="label.intupto"></bean:message>
       <html:text property="intuptodate" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
     </td>
   </tr>
   
   
   <tr>
     <td align="right">
       <bean:message key="label.penal"></bean:message>
       <html:text property="penalinterest" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
     </td>
   </tr>
   
   
   <tr>
     <td align="right">
       <bean:message key="label.other"></bean:message>
       <html:text property="othercharges" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
     </td>
   </tr>
   
   
   <tr>
     <td align="right">
       <bean:message key="label.extra"></bean:message>
       <html:text property="extraint" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
     </td>
   </tr>
   
   <html:hidden property="acc_notfound" styleId="acc_notfound"/>
   
</table>   
</html:form> 
</body>
</html>