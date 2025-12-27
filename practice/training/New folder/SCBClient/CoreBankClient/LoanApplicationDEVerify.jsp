<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.loansOnDeposit.LoanPurposeObject"%>

<%@page import="java.util.Vector"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.scb.loans.forms.ApplicationForm"%>
<%@page import="masterObject.loans.LoanMasterObject"%>
<html>
<head>
<script type="text/javascript">

function fun_submit(target)
{
	
	if(document.getElementById("loantype").value=='SELECT')
	{
		alert('Select the Loan Type');
		document.getElementById("loantype").focus();
		return false;
	}else
	if(document.getElementById("shtype").value=='SELECT')
	{
		alert('Select the Share type');
		document.getElementById("shtype").focus();
		return false;
	}else
	if(document.getElementById("shno").value==0)
	{
		alert('Enter the Share Number');
		document.getElementById("shno").focus();
		document.getElementById("shno").select();
		return false;
	}
	document.forms[0].button_value.value=target;

};
function function_loan_accgen()
{
if(document.getElementById("relativetodir").checked!=true){
document.forms[0].dirrelations.disabled = true;
document.forms[0].dirdetails.disabled = true;
}else{
document.forms[0].dirrelations.disabled = false;
document.forms[0].dirdetails.disabled = false;
}
if(document.getElementById("paymtmode").value=='Cash'){
document.forms[0].payaccno.disabled = true;
document.forms[0].payactype.disabled = true;
document.forms[0].coshareno.value=0;
}else if(document.getElementById("paymtmode").value=='PayOrder'){
document.forms[0].payaccno.disabled = true;
document.forms[0].payactype.disabled = true;
document.forms[0].coshareno.value=0;
}
else{
document.forms[0].payaccno.disabled = false;
document.forms[0].payactype.disabled = false;
}

 if(document.getElementById("loan_acc_no").value==1)
 {
 	alert("Loan Account Number is verified successfully");
 }
}
function numbersOnly()
{
	 var cha = event.keyCode
   		if (  ( cha  >= 47 && cha < 58  ) ) 
   		{
			return true ;
   		}
   		else
   		{
      		alert("Alphabets are not allowed,Please enter numbers only ");
       		return false ;
   		}
}
function shNoDetailsValidations()
{
	var a = document.getElementById("shno").value;
	
	document.forms[0].submit();
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
</style>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" /><h2 class="h2"><center>Loan Application Verification</center></h2>
  


 
</head>
<body background="biege" onload="function_loan_accgen()">

 
<%!String app,per,veh,prop,emp,sign,coBorow,relative,lsdet,gold,surities;%>  

<%app=(String)request.getAttribute("Application"); %>
<%per=(String)request.getAttribute("Personnel");%>
<%veh=(String)request.getAttribute("Vehicle");%>
<%prop=(String)request.getAttribute("Property");%>
<%emp=(String)request.getAttribute("Employment");%>
<%coBorow =(String) request.getAttribute("CoBorrower"); %>
<%relative=(String) request.getAttribute("RelativeDet"); %>
<%lsdet=(String) request.getAttribute("LoanandShareDetails"); %>
<%gold=(String) request.getAttribute("Gold"); %>
<%surities=(String) request.getAttribute("Surities"); %>

<%System.out.println("property value in jsp page============="+prop);%>
<%System.out.println("application value in jsp===================" +app); %>
<%System.out.println("Employment value in jsp===================" +emp); %>

<%if(request.getAttribute("Signature")!=null)%>	
<%sign =(String)request.getAttribute("Signature");
System.out.println("Signature Ins====>"+sign);
%> 

<%! ModuleObject object[];%>
<%! LoanPurposeObject lnpurpose[];%>  
<%! String str[];%>
<% object=(ModuleObject[])request.getAttribute("LoanModuleCode");%>
<% lnpurpose=(LoanPurposeObject[])request.getAttribute("LoanPurpose");%>
<% str=(String[])request.getAttribute("Details"); %>
<% LoanMasterObject lnmodobj;
 lnmodobj = (LoanMasterObject)request.getAttribute("ModName");%>
 <% String msg=(String)request.getAttribute("msg");
 if(msg !=null){
 %>
 <br><br><font size="2" color="red"><%= msg %></font>
 <%} %>
<table>
<html:form action="/Loans/LoansApplicationDEVerify?pageidentity.pageId=5082">
<tr>

<td width="40%"> 
       <table align="left">
         
         <font size="3" color="blue">
         		<%if(lnmodobj!=null){ %>
         		<%=""+lnmodobj.getLoanMod()%>
         		<%}%></font>
 			   <tr> 
 			      <td align="right"><font style="font-style:normal;font-size:12px"><bean:message key="label.combo_loan"></bean:message></font></td>
      			  <td><html:select property="loantype" onchange="submit()">
      			  <html:option value="SELECT">Select</html:option>
         		  <core:forEach var="acType" varStatus="count" items="${requestScope.LoanModuleCode}">
      			  <html:option value="${acType.moduleCode}"><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
      			  </core:forEach>
       			  </html:select></td>
 			  </tr>
   
   			  <tr>
                   <td align="right"><font style="font-style:normal;font-size:12px"><bean:message key="label.loanaccno"></bean:message></font></td>  
       			    <td><html:text property="lnaccno" onkeypress="return numbersOnly()" onchange="submit()"></html:text></td>
              </tr>
   
              <tr>
              	 <td align="right"><font style="font-style:normal;font-size:12px"><bean:message key="label.sh_type"></bean:message></font></td>
       			 <td><html:select property="shtype" styleId="shtype"><html:option value="1001001">SH</html:option></html:select>
       			 <html:text property="shno" styleId="shno" size="5" maxlength="11" readonly="true"></html:text></td>
               </tr>
    <tr>
       <td align="right"><font style="font-style:normal;font-size:12px"><bean:message key="label.purpose"></bean:message></font></td>
           <td><html:select property="purpose">
           <%System.out.println("purpose in JSP"+lnpurpose.length);%>
           <core:forEach var="Purpose" varStatus="count" items="${requestScope.LoanPurpose}">
          <html:option value="${Purpose.purposeDesc}"><core:out value="${Purpose.purposeDesc}"></core:out></html:option>
          </core:forEach>
       </html:select></td>   
      
   </tr> 
       
       
   <tr><%! Enumeration details; %>
       <%! 
       		Vector vec_final[];	
       		Vector arr;
       		Vector vec_pageId;
       		Enumeration en_pageID;
       %>
       <% 
       		arr=(Vector)request.getAttribute("RelevantDetails");
       		
       		vec_pageId=(Vector)request.getAttribute("RelevantPageId");
       %>
       <%if(arr!=null){%>
       <td align="right"><font style="font-style:normal;font-size:12px"><bean:message key="label.details"></bean:message></font></td>
           <td><html:select property="details" onchange="submit()">
        <%
        	details=arr.elements();
        	en_pageID=vec_pageId.elements();
            while(details.hasMoreElements()&&en_pageID.hasMoreElements()){
            	String str=""+details.nextElement();
            	String str_pageId=""+en_pageID.nextElement();
        %>
        <html:option value="<%=str_pageId%>"><%=str%></html:option> 
       <%}%>
       </html:select></td>
       <%}else{%>
       <td align="right"><font style="font-style:normal;font-size:12px"><bean:message key="label.details"></bean:message></font></td>
           <td><html:select property="details" onchange="submit()">
       <%for(int i=0;i<str.length;i++){ %>
       <html:option value="<%=str[i]%>"><%=str[i]%></html:option>
       <%}%>
       </html:select></td>
       <%}%>    
   </tr>
   
   <tr> 
       <td></td><td><html:submit styleClass="ButtonBackgroundImage" onclick="return fun_submit('verify')">Verify</html:submit></td>
       <td><html:submit  styleClass="ButtonBackgroundImage">Cancel</html:submit></td>
  </tr>
   
    	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="defaultTab" value="PersonalDetails"/>
     	<html:hidden property="defSIndex" value="1"/>
     	<html:hidden property="button_value" value="error"/>
     	
   
   
</table>
</td>
	<html:hidden property="defaultTab" value="Personal"></html:hidden>
	<html:hidden property="loan_acc_no" styleId="loan_acc_no"></html:hidden>
	<html:hidden property="validateShno" styleId="validateShno"/>
<td> 
     <table>
         <% String jspPath=(String)request.getAttribute("flag");
    	  			System.out.println("jspPath=="+jspPath);
         		%>
   				<%if(jspPath!=null && veh==null){ %>
   				<jsp:include page="<%=jspPath%>"></jsp:include>
				<%}%>
         <%if(app!=null){ %>
         <td align="right"><jsp:include page="<%=app%>"></jsp:include></td>
          <%}else if(veh!=null){%>
          <td align="right"><jsp:include page="<%=veh%>"></jsp:include></td>
          <%} else if(prop!=null){%>
          <td align="right"><jsp:include page="<%=prop%>"></jsp:include></td>
          <%}%>
          <%if(sign!=null){%>
          <%System.out.println("***********Inside sign*********"+sign); %>
          <td align="right"><jsp:include page="<%=sign%>"></jsp:include></td>
          <%} %>
          <%if(coBorow!=null){%>
			<td align="right"><jsp:include page="<%=coBorow %>"></jsp:include></td>
		   <%} %>
		   <%if(relative!=null){ %> 
		   <td align="right"><jsp:include page="<%=relative%>"></jsp:include></td>    
          <%}%>
          <%if(lsdet!=null){ %> 
		   <td align="right"><jsp:include page="<%=lsdet%>"></jsp:include></td>    
          <%}%>
          <%if(gold!=null){ %> 
		   <td align="right"><jsp:include page="<%=gold%>"></jsp:include></td>    
          <%}%>
           <%if(surities!=null){ %> 
		   <td align="right"><jsp:include page="<%=surities%>"></jsp:include></td>    
          <%}%>
          
          
     </table>
  </td>  
   
</tr>   
</html:form>
</table>

</body>
</html>