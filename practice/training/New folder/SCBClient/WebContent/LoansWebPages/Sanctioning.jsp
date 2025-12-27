<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>    
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.loans.PriorityMasterObject"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Map"%>
<%@page import="masterObject.general.ModuleObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">

function only_numbers() {
	
	var cha=event.keyCode;
	if(cha>47 && cha<=57 )
	{
		return true;
	}
	else
	{
		return false;
	}
}  ;
function Combo_check(){
 
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
 	
 	};
	function fun_button(target)
	{
		if(document.forms[0].accno.value=="")
   		{
   			alert('Enter Valid Account Number');
   			document.forms[0].accno.focus();
   		}
   		else if(document.forms[0].accno.value==0)
   		{
   			alert('Enter Valid Account Number');
   			document.forms[0].accno.focus();
   		}
    	else if(document.forms[0].amount.value=="")
   		{
   			alert('Amount Cannot Be Blank');
   			document.forms[0].amount.focus();
   		}
    	else if(document.forms[0].amount.value==0.0)
   		{
   			alert('Amount Cannot Be Zero');
   			document.forms[0].amount.focus();
   		}
   		else if(document.forms[0].period.value=="")
   		{
   			alert('Period Cannot Be Blank');
   			document.forms[0].period.focus();
   		}
    	else if(document.forms[0].period.value==0)
   		{
   			alert('Period Cannot Be Zero');
   			document.forms[0].period.focus();
   		}
   		else if(document.forms[0].holiday.value=="")
   		{
   			alert('Holiday Cannot Be Blank');
   			document.forms[0].holiday.focus();
   		}
    	else if(document.forms[0].holiday.value==0.0)
   		{
   			alert('Holiday Cannot Be Zero');
   			document.forms[0].holiday.focus();
   		}
   		else
		{
		
   			document.forms[0].buttonvalue.value=target;
   			document.forms[0].submit(); 
   			return true;
   		}	
   		return true;
 	};
 	
 
 
  function func_subpanels()  
  {
   
  	alert("In Detial"+document.forms[0].details.value);
  	
  	if(document.forms[0].details.value=="Personal" || document.forms[0].details.value=="Loan and Share Details" || document.forms[0].details.value=="Signature Ins")
  	{
  	       alert(document.forms[0].details.value);
  	       document.forms[0].submit();
  	}
  	else if(document.forms[0].details.value=="Application")	
  	{
  	      alert("In Application");
  	     alert("In Application");
  	      document.getElementById("Application").style.display='block';	
  	   	  document.getElementById("coborrow").style.display='none';
  	   	  document.getElementById("table_proper").style.display='none';
 	      document.getElementById("self-employed").style.display='none';
  	      return false;	
  	}
  	else if(document.forms[0].details.value=="Property")
  	{
  	   document.getElementById("table_proper").style.display='block';
  	  document.getElementById("coborrow").style.display='none';
  	  document.getElementById("Application").style.display='none';	
  	  document.getElementById("self-employed").style.display='none';	
  	  return false;	
  	}
  	else if(document.forms[0].details.value=="Employment")
  	{
  	  document.getElementById("self-employed").style.display='block';
  	  document.getElementById("Application").style.display='none';	
  	   document.getElementById("table_proper").style.display='none';
  	 return false;
  	}
  	
  	
 }
 
 
 function setMode()
 {
 	if(document.forms[0].combo_pay_mode.value=="Cash" || document.forms[0].combo_pay_mode.value=="PayOrder")
 	{
 		document.forms[0].payactype.disabled=true;
 		document.forms[0].payaccno.disabled=true;
 		document.getElementById("sub").focus();
 	}
 	else if(document.forms[0].combo_pay_mode.value=="Transfer")
 	{
 		document.forms[0].payactype.disabled=false;
 		document.forms[0].payaccno.disabled=false;
 		document.getElementById("payactype").focus();
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
         		alert("You can't enter more than "+parseInt(size-1)+" digits!")
         		txt.value="";
         		return false;
          	}
         };
 
 
 function  function_clear()
 {
 	var ele=document.forms[0].elements;
        	for(var i=0;i<ele.length;i++){
        		if(ele[i].type=="text"){
        			ele[i].value="";
        		}
        	}
 }

</script>
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
    </style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" /><h2 class="h2"><center>Sanction of the Loan</center></h2>
    </head>



<%System.out.println("hi from sanction"); %>
<%!String app,per,veh,prop,emp,sign,coBorow,relative,lsdet,gold,surities,disable;%>  

	<%app=(String)request.getAttribute("Application"); %>
	<%per=(String)request.getAttribute("Personnel");%>
	<%veh=(String)request.getAttribute("Vehicle");%>
	<%prop=(String)request.getAttribute("Property");%>
	<%emp=(String)request.getAttribute("Employment");%>
	<%coBorow =(String) request.getAttribute("CoBorrower"); %>
	<% System.out.println("Before Relative Det"); %>
	<%relative=(String) request.getAttribute("RelativeDet"); %>
	<%lsdet=(String) request.getAttribute("LoanandShareDetails"); %>
	<%gold=(String) request.getAttribute("Gold"); %>
	<%surities=(String) request.getAttribute("Surities"); %>
	<%disable=(String) request.getAttribute("disable"); %>
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
System.out.println("11111111"+access);
			if(user_role.get(accesspageId)!=null){
			System.out.println("222222222"+access);
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}

			
			}

// Note:access=1111==>read,insert,delete,update
%>
	
	<%! PriorityMasterObject[] PriorityDesc;%> 
	<%! String str[];%>
	
	<% PriorityDesc=(PriorityMasterObject[])request.getAttribute("PriorityDesc"); %>
	<%System.out.println("Hi Before HTML Form"); %>
	<% str=(String[])request.getAttribute("Details"); %>
	<% String msg=(String)request.getAttribute("msg");
 		if(msg !=null){
 	%>
 	<br><br><font  color="red"><%= msg %></font>
 	<%} %>

 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
<html:form action="/Loans/Sanctioning?pageidentity.pageId=5040">
<body onload="return Combo_Check()">
<table>
<tr><td>
<table class="txtTable">
   <tr>
     <td>
         <bean:message key="label.loanaccno"></bean:message></td>
         <td>
         	<html:select property="acctype"  styleClass="formTextFieldWithoutTransparent">
         	<core:forEach var="acType" varStatus="count" items="${requestScope.LoanModuleCode}">
      			  <html:option  value="${acType.moduleCode}">
      			  <core:out value="${acType.moduleAbbrv}">
      			  </core:out>
      			  </html:option>
      	 </core:forEach>
      	 </html:select>
      
        <html:text styleClass="formTextFieldWithoutTransparent" property="accno" size="10" onkeypress="return only_numbers()"  onkeyup="numberlimit(this,'11')" onblur="submit()"></html:text></td>
    </tr>   
         
     
         
    <tr>
       <td><bean:message key="label.sh_type"></bean:message></td>
       <td><html:select property="shtype" disabled="true">
              <html:option value="1001001">SH</html:option>
           </html:select>
       	<html:text property="shno" size="5" onblur="submit()" readonly="true"></html:text></td>
    </tr>
    
    <tr>
       <td><bean:message key="label.purpose"></bean:message></td>
           <td><html:select property="purpose" disabled="true">
           <core:forEach var="Purpose" varStatus="count" items="${requestScope.LoanPurpose}">
          <html:option value="${Purpose.purposeDesc}"><core:out value="${Purpose.purposeDesc}"></core:out></html:option>
          </core:forEach>
       </html:select></td>   
      
   </tr> 
   
   
   <tr>
  	   <%! Enumeration details; %>
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
       <% if(arr!=null){%>
       <td><bean:message key="label.details"></bean:message></td>
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
       <td><bean:message key="label.details"></bean:message></td>
           <td><html:select property="details" onchange="submit()">
       <%for(int i=0;i<str.length;i++){ %>
       <html:option value="<%=str[i]%>"><%=str[i]%></html:option>
       <%}%>
       </html:select></td>
       <%}%>    
   </tr>
           
           
   <tr>
      <td>
          <bean:message key="label.amount"></bean:message></td>
          <td><html:text styleClass="formTextFieldWithoutTransparent" property="amount" size="10" onkeypress="return only_numbers()"></html:text>
      </td>
   </tr> 	
   
   
   
   <tr>
      <td>
          <bean:message key="label.period"></bean:message></td>
          <td><html:text styleClass="formTextFieldWithoutTransparent" property="period" size="10" onkeypress="return only_numbers()"  onkeyup="numberlimit(this,'3')"></html:text>
          <html:text styleClass="formTextFieldWithoutTransparent" property="holiday" size="10" onkeypress="return only_numbers()"  onblur="submit()" onkeyup="numberlimit(this,'4')"></html:text>
      </td>
   </tr>	 
   
   <tr>
      <td>
          <bean:message key="label.Installmentamt"></bean:message></td>
        <td>  <html:text styleClass="formTextFieldWithoutTransparent" property="installment" size="10" readonly="true"  onblur="submit()"></html:text>
      </td>
   </tr>
      	 
   <tr>
      <td>
          <bean:message key="label.intrate"></bean:message></td>
        <td>  <html:text styleClass="formTextFieldWithoutTransparent" property="intrate" size="10" readonly="true"  onblur="submit()"></html:text>
      </td>
   </tr> 
   
   
   <tr>
      <td>
          <bean:message key="label.penalrate"></bean:message></td>
         <td> <html:text styleClass="formTextFieldWithoutTransparent" property="penalrate" size="10" readonly="true"  onblur="submit()"></html:text>
      </td>
   </tr>  
   
   <tr>
      <td>
          <bean:message key="label.priority_num"></bean:message></td>
          
         <td> <html:select property="priority" >
         <html:option value="None"></html:option>
         <%if(PriorityDesc!=null) {%>
        	 <%for(int i=0;i<PriorityDesc.length;i++)
         		{ 
        	 		String item = PriorityDesc[i].getPrior_desc();
         			String prior;
			 		
         			if((item.length()==17) || (item.length()==26))
			     		prior=item.substring(0,item.length());
			 		else
				 		prior=item.substring(0,35);%>
         			<html:option value="<%=""+i%>"><%=prior%></html:option>
         		<%} %>
         <%} %>
          </html:select>          
      </td>
     
      <td>WeakSection:            <html:checkbox property="weak"></html:checkbox>
      </td>
     
   </tr>  
   
   
   <tr>
      <td>
         <bean:message key="label.combo_pay_mode"></bean:message></td>
       <td>  <html:select property="combo_pay_mode" onchange="setMode()">
           <html:option value="C">Cash</html:option>
           <html:option value="T">Transfer</html:option>
           <html:option value="P">PayOrder</html:option>
         </html:select>
      </td>
   </tr>  
   
   <tr>
   <td><bean:message key="label.paymentactype"></bean:message></td>
            <td><html:select  property="payactype" disabled="true">
            <%! ModuleObject module_object[]=null; %>
            <% 
            	module_object=(ModuleObject[])request.getAttribute("pay_actype");
			        	for(int i=0;i < module_object.length;i++)
			            {
						 	 System.out.println("module_object"+ module_object[i].getModuleAbbrv());
				    	
			    	 %>	   			    			    	 
			    			<html:option value="<%=""+module_object[i].getModuleCode()%>"><%=""+module_object[i].getModuleAbbrv()%></html:option>
			    	<%	} 	%>
			        	 
			         </html:select></td>
            
</tr>   
<tr>  
    <td><bean:message key="label.payaccno"></bean:message></td>
    <td><html:text property="payaccno" size="10" readonly="true"></html:text></td>
    
</tr>         
    <html:hidden property="buttonvalue" value="error"/>
    <html:hidden property="sanction_res" styleId="sanction"/>
    
      	 
    <tr>
    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
    <%if(disable!=null && disable.equalsIgnoreCase("disable")){ %>
       <td>
       	   <html:button property="sub" styleClass="ButtonBackgroundImage" disabled="true" >Submit</html:button>
           <html:button styleClass="ButtonBackgroundImage" value="Clear" disabled="true"  property="clear" onclick="return function_clear()"></html:button>
       </td>
       <%}else{ %>
        <td>
       	   <html:button property="sub" styleClass="ButtonBackgroundImage" >Submit</html:button>
           <html:button styleClass="ButtonBackgroundImage" value="Clear"  property="clear" onclick="return function_clear()"></html:button>
       </td>
       <%} %>
    <%}else { %>  
     	<td>
       	   <html:button styleClass="ButtonBackgroundImage" disabled="true" onclick="return fun_button('Submit')" property="sub">Submit</html:button><!--
           <html:button styleClass="ButtonBackgroundImage" onclick="return fun_button('Delete')" property="sub">Delete</html:button>
           -->
           <html:button styleClass="ButtonBackgroundImage" disabled="true" value="Clear" property="clear" onclick="return function_clear()"></html:button>
       </td>
    <%} %>
   </tr>  			  
         
</table>
	</td>
	
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
          		<%if(per!=null){%>
          			<%System.out.println("***********Inside personnel*********"+per); %>
          			<td align="left"><jsp:include page="<%=per%>"></jsp:include></td>
          		<%} %>
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
</table>
</body>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</html>