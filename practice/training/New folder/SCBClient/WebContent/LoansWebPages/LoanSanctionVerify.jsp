<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>    
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.loans.PriorityMasterObject"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Vector"%>
<%@page import="masterObject.general.ModuleObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">
function Combo_check(){

 
if(document.getElementById("relativetodir").checked!=true){
document.forms[0].dirrelations.disabled = true;
document.forms[0].dirdetails.disabled = true;
}else{
document.forms[0].dirrelations.disabled = false;
document.forms[0].dirdetails.disabled = false;
}
if(document.getElementById("paymtmode").value=='Cash'){
alert(Hiii111);
document.forms[0].payaccno.disabled = true;
document.forms[0].payactype.disabled = true;
document.forms[0].coshareno.value=0;
}else if(document.getElementById("paymtmode").value=='PayOrder'){
alert(Hiii2233);
document.forms[0].payaccno.disabled = true;
document.forms[0].payactype.disabled = true;
document.forms[0].coshareno.value=0;
}
else{
alert(Hiii7777);
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
   		document.forms[0].accno.focus();
   }
    else if(document.forms[0].amount.value==0.0)
   {
   		alert('Amount Cannot Be Zero');
   		document.forms[0].accno.focus();
   }
   else
   {
   		document.forms[0].buttonvalue.value=target;
   		document.forms[0].submit(); 
   	}	
 }
 
 
  
 
 
 function  function_clear()
 {
 		var ele=document.forms[0].elements;
        	for(var i=0;i<ele.length;i++)
        	{
        		if(ele[i].type=="text")
        		{
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
<%!String app,per,veh,prop,emp,sign,coBorow,relative,lsdet,gold,surities,paymode,Priority,penalty;%>  

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
<%paymode=(String)request.getAttribute("paymode"); %>
<%Priority =(String)request.getAttribute("Priority");%>
<%penalty =(String)request.getAttribute("penalty");%>

<%! PriorityMasterObject[] PriorityDesc;%> 
<%! String str[];%>
<%PriorityDesc=(PriorityMasterObject[])request.getAttribute("PriorityDesc"); %>

<% str=(String[])request.getAttribute("Details"); %>

<% String msg=(String)request.getAttribute("msg");
 if(msg !=null){
 %>
 <br><br><font size="2" color="red"><%= msg %></font>
 <%} %>

<html:form action="/Loans/SanctionVerify?pageidentity.pageId=5083">
<body onload="Combo_check()">
<table>
<tr><td>
<table class="txtTable">
   <tr>
     <td>
         <bean:message key="label.loanaccno"></bean:message></td>
         <td>
         	<html:select property="acctype" onchange="submit()" styleClass="formTextFieldWithoutTransparent">
         	<core:forEach var="acType" varStatus="count" items="${requestScope.LoanModuleCode}">
      			  <html:option value="${acType.moduleCode}">
      			  <core:out value="${acType.moduleAbbrv}">
      			  </core:out>
      			  </html:option>
      	 </core:forEach>
      	 </html:select>
      </td> 
        <td><html:text styleClass="formTextFieldWithoutTransparent" property="accno" size="10" onkeypress="return only_numbers()"  onblur="submit()"></html:text></td>
    </tr>   
         
     
         
    <tr>
       <td><bean:message key="label.sh_type"></bean:message></td>
       <td><html:select property="shtype" disabled="true">
              <html:option value="1001001">SH</html:option>
           </html:select>
       	<html:text property="shno" size="5"  ></html:text></td>
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
       <%if(arr!=null){%>
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
          <td><html:text styleClass="formTextFieldWithoutTransparent" property="amount" size="10" onkeypress="return only_numbers()" readonly="true"></html:text>
      </td>
   </tr> 	
   
   
   
   <tr>
      <td>
          <bean:message key="label.period"></bean:message></td>
          <td><html:text styleClass="formTextFieldWithoutTransparent" property="period" readonly="true" size="10" onkeypress="return only_numbers()"  ></html:text>
          <html:text styleClass="formTextFieldWithoutTransparent" property="holiday" size="10" readonly="true" onkeypress="return only_numbers()" ></html:text>
      </td>
   </tr>	 
   
   <tr>
      <td>
          <bean:message key="label.Installmentamt"></bean:message></td>
        <td>  <html:text styleClass="formTextFieldWithoutTransparent" property="installment" size="10" onkeypress="return only_numbers()" readonly="true"></html:text>
      </td>
   </tr>
      	 
   <tr>
      <td>
          <bean:message key="label.intrate"></bean:message></td>
        <td>  <html:text styleClass="formTextFieldWithoutTransparent" property="intrate" size="10" readonly="true" onkeypress="return only_numbers()" ></html:text>
      </td>
   </tr> 
   
   
   <tr>
      <td>
          <bean:message key="label.penalrate"></bean:message></td>
          <%if(penalty!=null){ %>
         <td> <html:text styleClass="formTextFieldWithoutTransparent" property="penalrate" value="<%=penalty %>" readonly="true" size="10"  ></html:text>
      </td>
      <%}else{ %>
        <td> <html:text styleClass="formTextFieldWithoutTransparent" property="penalrate" readonly="true" size="10" onkeypress="return only_numbers()" ></html:text>
      </td>
      <%} %>
   </tr>  
   
   <tr>
    <td>
          <bean:message key="label.priority_num"></bean:message></td>
   <%if(Priority!=null){ %>
     
          <td> <html:select property="priority" value="<%=Priority %>">
          <html:option value="None"></html:option>
         <%if(PriorityDesc!=null ){%>
         <%for(int i=0;i<PriorityDesc.length;i++){ 
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
      <%}else{ %>
         <td> <html:select property="priority">
          <html:option value="None"></html:option>
         <%if(PriorityDesc!=null ){%>
         <%for(int i=0;i<PriorityDesc.length;i++){ 
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
      <%} %>
     
   </tr>  
   <tr>
      <td>WeakSection:            <html:checkbox property="weak"></html:checkbox>
      </td>
      </tr>
   
   
   <tr>
   <%if(paymode!=null){ %>
      <td>
         <bean:message key="label.combo_pay_mode"></bean:message></td>
       <td>  <html:select property="combo_pay_mode" value="<%=paymode %>">
           <html:option value="C">Cash</html:option>
           <html:option value="T">Transfer</html:option>
           <html:option value="P">PayOrder</html:option>
         </html:select>
      </td>
      <%}else{ %>
       <td>
         <bean:message key="label.combo_pay_mode"></bean:message></td>
       <td>  <html:select property="combo_pay_mode">
           <html:option value="C">Cash</html:option>
           <html:option value="T">Transfer</html:option>
           <html:option value="P">PayOrder</html:option>
         </html:select>
      </td>
      <%} %>
   </tr>  
   
   <tr>
   <td><bean:message key="label.paymentactype"></bean:message></td>
            <td><html:select  property="payactype" >
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
       <td><html:button property="sub" styleClass="ButtonBackgroundImage" onclick="return fun_button('Verify')" >Verify</html:button>
           <html:button styleClass="ButtonBackgroundImage" value="Clear" property="clear" onclick="return function_clear()"></html:button>
       </td>
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
</html>