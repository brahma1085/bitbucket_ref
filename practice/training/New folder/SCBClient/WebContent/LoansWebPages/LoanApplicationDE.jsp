<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.loansOnDeposit.LoanPurposeObject"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Enumeration"%>
<%@page import="masterObject.loans.LoanMasterObject"%>
<html>
<head>
<script type="text/javascript">
function clear(){
document.getElementById("surityname").value=="";
document.getElementById("covalue").value=="0.0";
document.getElementById("brcode").value=="0";
document.getElementById("brname").value=="";

};

function func_clear()
{

    var v = document.forms[0].elements;
    for(var i=0;i<=v.length;i++)
    {
       if(v[i].type =="text")
       {
          v[i].value = "";

        }
     }
 
}



function numbersOnly1(eve){
         var cha = event.keyCode
				if (  ( cha  > 47 && cha < 58 ) ) {
				return true ;
                
            	}
            else{
              	alert("Please enter the numbers only ");
              	return false ;
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
         		alert("You can't enter more than "+parseInt(size-1)+" digits!")
         		txt.value="";
         		return false;
          	}
         };

function fun_submit(target)
{

	document.forms[0].button_value.value=target;
	document.forms[0].submit();

};
function function_loan_accgen()
{

document.forms[0].dirrelations.disabled = true;
document.forms[0].dirdetails.disabled = true;
document.forms[0].payaccno.disabled = true;
document.forms[0].payactype.disabled = true;
document.forms[0].coshareno.value=0;
 if(document.getElementById("loan_acc_no").value!=0)
 {
 	alert("New Loan Account Number="+document.getElementById("loan_acc_no").value);
 }
 if(document.forms[0].validateShno.value==""){
  	return false;
 }else{
 	alert(document.forms[0].validateShno.value);
 }
 if(document.getElementById("validat").value!=0)
	{
	alert("hi function1");
		alert("Account Created Successfully!!!");
		alert("The New Account number Is:"+document.getElementById("validat").value);	
		func_clear();
		return false;		
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
      		alert("Please enter the numbers only ");
       		return false ;
   		}
}
function shNoDetailsValidations()
{
	 
	if(document.getElementById("validateShno").value=="ShareNotfound")
	{
		alert("Share number not found");
	}
	document.forms[0].submit();
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
</style>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" /><h2 class="h2"><center>Loan Application DE</center></h2>
  
 
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


<%if(request.getAttribute("Signature")!=null)%>	
<%sign =(String)request.getAttribute("Signature");
System.out.println("Signature Ins====>"+sign);
%> 
<%!
String access;
String  accesspageId;
 Map user_role;

%>

<%

  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");
System.out.println("11111111"+user_role);
System.out.println("222222222"+accesspageId);
if(user_role!=null&&accesspageId!=null){
	System.out.println("3333333333"+user_role.get(accesspageId));
			if(user_role.get(accesspageId)!=null){
			System.out.println("44444"+access);
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}

			
			}

// Note:access=1111==>read,insert,delete,update
%>

<%! ModuleObject object[];%>
<%! LoanPurposeObject lnpurpose[];%>  
<%! String str[];%>
<%! String lntype,disable1; %>
<% object=(ModuleObject[])request.getAttribute("LoanModuleCode");%>
<% lnpurpose=(LoanPurposeObject[])request.getAttribute("LoanPurpose");%>
<% str=(String[])request.getAttribute("Details"); %>
<% lntype=(String)request.getAttribute("lntype"); %>
<% disable1=(String)request.getAttribute("disable1"); %>
<% LoanMasterObject lnmodobj;
 lnmodobj = (LoanMasterObject) request.getAttribute("ModName");%>
 <% String msg=(String)request.getAttribute("msg");
 if(msg !=null){
 %>
 <br><br><font size="3" color="red"><%= msg %></font>
 <%} %>
 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
<table>

<html:form action="/Loans/LoanApplicationDE?pageidentity.pageId=5001">
<tr>

<td width="40%"> 
       <table align="left">
         
         
 			   <tr> 
 			      <td align="right"><font style="font-style:normal;font-size:12px"><bean:message key="label.combo_loan"></bean:message></font></td>
 			      <%if(disable1==null){ %>
 			      <td><html:select property="loantype" onchange="submit()">
 			      <html:option value="Select">Select</html:option>
         		  <core:forEach var="acType" varStatus="count" items="${requestScope.LoanModuleCode}">
      			  <html:option value="${acType.moduleCode}"><core:out value="${acType.moduleAbbrv}"></core:out></html:option>
      			  </core:forEach>
       			  </html:select></td>
       			  <%}else{ %>
       			  <td>
 			  	<%for(int i=0;i<object.length;i++){ %>
 			  	<%if(lntype.equalsIgnoreCase(object[i].getModuleCode())){ %>
 			  	<html:select property="loantype" disabled="true">
 			  	<html:option value="<%=""+lntype%>">
 			  		<core:out value="<%=""+object[i].getModuleAbbrv()%>"></core:out>
 			  	</html:option>
 			  	</html:select>
 			  		<%}%>
 			  		<%} %>
 			  	</td>
 			   <%} %>
       			<font size="3" color="blue">
         		<%if(lnmodobj!=null){ %>
         		<%=""+lnmodobj.getLoanMod()%>
         		<%}else{%>
         		<%=""+"Select the Loan Type!"%>
         		<%} %>
         		
         		</font>
 			  </tr>
 			  
   			  <tr>
                   <td align="right"><font style="font-style:normal;font-size:12px"><bean:message key="label.loanaccno"></bean:message></font></td>  
       			    <td><html:text property="lnaccno" value="0" readonly="true" onchange="submit()" onkeypress=" return numbersOnly1()" onkeyup="numberlimit(this,'11')" ></html:text></td>
              </tr>
   
              <tr>
              	 <td align="right"><font style="font-style:normal;font-size:12px"><bean:message key="label.sh_type"></bean:message></font></td>
       			 <td><html:select property="shtype" styleId="shtype"><html:option value="SELECT">Select</html:option><html:option value="1001001">SH</html:option></html:select>
       			 <html:text property="shno" styleId="shno" size="5" maxlength="11" onkeypress="return numbersOnly()" onkeyup="numberlimit(this,'11')"  onblur="shNoDetailsValidations()"></html:text></td>
               </tr>
    <tr>
       <td align="right"><font style="font-style:normal;font-size:12px"><bean:message key="label.purpose"></bean:message></font></td>
           <td><html:select property="purpose">
           <%System.out.println("purpose in JSP"+lnpurpose.length);%>
       <%for(int i=0;i<lnpurpose.length;i++){ %>
       <html:option value="<%=""+lnpurpose[i].getPurposeCode()%>"><core:out value="<%=""+lnpurpose[i].getPurposeDesc() %>"></core:out></html:option>
       <%} %>
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
       <td><font style="font-style:normal;font-size:12px"><bean:message key="label.details"></bean:message></font></td>
           <td><html:select property="details" onblur="submit()">
        <%
        	details=arr.elements();
        	en_pageID=vec_pageId.elements();
            while(details.hasMoreElements()&& en_pageID.hasMoreElements()){
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
       <td>
       <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1' ){%>
     	<%if(msg!=null){ %>
       		<html:submit property="main_submit" styleClass="ButtonBackgroundImage" disabled="true">Submit</html:submit>
       	<%}else{ %>
       		<html:submit property="main_submit" styleClass="ButtonBackgroundImage"  onclick="return fun_submit('submit')">Submit</html:submit>
       	<%} %>
       <%}else{ %>
       <html:submit property="main_submit" styleClass="ButtonBackgroundImage" disabled="true">Submit</html:submit>
       <%} %>
       </td>
       <td><html:button property="dummycancel" styleClass="ButtonBackgroundImage">Cancel</html:button></td>
       <td><html:button property="dummyclear" styleClass="ButtonBackgroundImage" onclick="func_clear()">Clear</html:button></td>
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
	<html:hidden property="validate" styleId="validat"></html:hidden>
<td> 
     <table >
         <% String jspPath=(String)request.getAttribute("flag");
    	  			System.out.println("jspPath=="+jspPath);
         		%>
   				<%if(jspPath!=null && veh==null){ %>
   				<jsp:include page="<%=jspPath%>"></jsp:include>
				<%}%>
         <%if(app!=null){ %>
         <td align="right" ><jsp:include page="<%=app%>" ></jsp:include></td>
         <% jspPath=(String)request.getAttribute("flag");
    	  			System.out.println("jspPath=="+jspPath);
         		%>
   				<%if(jspPath!=null && app!=null){ %>
   				<jsp:include page="<%=jspPath%>"></jsp:include>
				<%}%>
         <%}else if(per!=null){%>
          <td align="right"><jsp:include page="<%=per%>"></jsp:include></td>
          <%} else if(app!=null){ %>
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
		   <td align="left" onkeypress="clear()"><jsp:include page="<%=surities%>"></jsp:include></td>    
          <%}%>
          
          
     </table>
  </td>  
   
</tr>   
</html:form>
</table>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
        
</body>
</html>