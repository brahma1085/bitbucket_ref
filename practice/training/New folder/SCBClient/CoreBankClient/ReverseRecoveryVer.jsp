<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Vector"%>
<%@page import="masterObject.loans.LoanTransactionObject"%>
<html>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<head>

    
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
      
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">

      function only_Numbers()
       {
        var cha =   event.keyCode;
		if ( (cha >= 48 && cha <= 57 ) ) 
 		{
	    	return true;
   		} 
   		else 
   		{
		    return false;
   		}
	   }; 
	   
      function set(target){
      if(document.forms[0].voucherno.value=="")
      {
        alert("Enter the VoucherNumber");
        return false;
      }
      if(document.forms[0].voucherno.value==0)
      {
        alert("Enter the VoucherNumber");
        return false;
      }
      else
      {
              document.forms[0].forward.value=target;
              return true;
      }
      };
      
      function func_toDelete(target)
      {
         
         
         var result=confirm("Do You want to Delete");
         if(result==true)
         {
           document.forms[0].forward.value=target;
         }
         else if(result==false)
         {
         	alert("In else Stmt");
            return false;
         }
      
      };
      
      function func_toUpdate(target)
      {
         var result = confirm("Do U Want To Update");
         if(result==true)
         {
           document.forms[0].forward.value=target;
         }
         else
            return false;
      };
      
      function voucherGen()
      {
      		
      	 if(document.getElementById("submit_res").value!=0)
      	 {
      	    alert("Voucher Number"+document.getElementById("submit_res").value+"generated successfully");
      	    clearPage();
      	 }
      	 else if(document.getElementById("update_res").value!=0)
      	 {
      	 	alert("Vocher Number"+document.getElementById("update_res").value+"Updated Sucessfully");
      	 	clearPage();
      	 }
      	 if(document.getElementById("del_res").value!=0)
      	 {
      	    alert("Voucher Number deleted successfully");
      	    clearPage();
      	 }
      	 	
         if(document.getElementById("vouchernum").value!=0)
         {
            alert("Voucher Number "+document.getElementById("vouchernum").value+" is generated");
            return false;
         }
         
         if(document.getElementById("notexist").value!=null)
         {
         	if(document.getElementById("notexist").value=="NOTFOUND")
         	{
           		alert("Voucher Number Does not Exist");
           		clearPage();
         	}
         }
         if(document.getElementById("accountnotexist").value!=null)
         {
         	if(document.getElementById("accountnotexist").value=="ACOUNTNOTFOUND")
         	{
           		alert("Loan Account Does not Exist");
           		clearPage();
         	}
         }
          
         
      }
      
      function clearPage()
 	   { 
 	   
        var ele=document.forms[0].elements;
        	for(var i=0;i<ele.length;i++){
        		if(ele[i].type=="text"){
        			ele[i].value="";
        		}
        	}	
 	   };
 	   
 	   function unverified(KeyEvent e)
 	   {
 	      alert(e);
 	      var cha =   e.keyCode;
 	      alert(cha);
 	      if(e.getSource()==voucherno)
 	      {
 	      if(e.getKeyCode() == KeyEvent.VK_F1) 
 	      {
 	          alert("I am in F1"); 	      
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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
<h2 class="h2"><center>Reverse Recovery Verification</center></h2>
</head> 
<body onload="return voucherGen()">
<%!ModuleObject[] object;%>
 <%!Hashtable gl_code;%>
<%!LoanTransactionObject lntrn; %>
<%!String[] gl_type;%>
<%!Vector vec_gl_code=null; %>
<%
String message=(String)request.getAttribute("msg");
object=(ModuleObject[]) request.getAttribute("LoanModuleCode");
System.out.println("The length of object is"+object.length);
gl_type=(String[])request.getAttribute("GLType");
gl_code=(Hashtable)request.getAttribute("GLCodes");
vec_gl_code = (Vector)(gl_code.get("ALL"));
lntrn = (LoanTransactionObject) request.getAttribute("LoanTrnObj");
if(lntrn!=null)
	System.out.println("M in Reverse Recovery Page"+lntrn.getOtherAmount());
%>
<div id = "div1" class = "myLayersClass">
    <core:if test="<%=message!=null %>">
    <font color="red"><%=message %></font>
    <br><br><br><br>
    </core:if>
    </div>
<html:form action="/Loans/ReverseRecovery?pageidentity.pageId=5087">

<table>
<td>

<table class="txtTable" cellspacing="5" style="border: thin solid black">


   <tr>
      <td><bean:message key="label.voucherno"></bean:message></td>
      <td><core:choose>
      	<core:when test="${empty requestScope.VoucherNum}">
      		<html:text property="voucherno" size="10" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_Numbers()" onblur="submit()"></html:text>
      	</core:when>
      	<core:otherwise>
      		<html:text property="voucherno" styleClass="formTextFieldWithoutTransparent" size="10" onkeypress="return only_Numbers()" onchange="submit()"></html:text>
     	 </core:otherwise>
      </core:choose></td>  
  </tr>  
  
 
   <tr>
      <td ><bean:message key="label.combo_loan"></bean:message></td>
      <td> <html:select property="acctype">
      <core:choose>
      	<core:when test="${empty requestScope.LoanTrnObj}">
      
          <core:forEach var="actypes" items="${requestScope.LoanModuleCode}">
          <html:option value="${actypes.moduleCode}">
      <core:out value="${actypes.moduleAbbrv}"></core:out>
        </html:option> 
      </core:forEach>
      </core:when>
      
      <core:otherwise>
       <core:forEach var="actypes" items="${requestScope.LoanModuleCode}">
        
 
         <html:option value="${LoanTrnObj.accType}"><core:out value="${actypes.moduleAbbrv}"></core:out></html:option>
        
         </core:forEach>
      </core:otherwise>
     </core:choose>
      </html:select>
      </td>
 
      <td><bean:message key="label.loanaccno"></bean:message></td>
      <td>
       <core:choose>
       
           <core:when test="${requestScope.LoanTrnObj!=null}">
           		
              <html:text property="accno" size="10" value="${LoanTrnObj.accNo}" onkeypress="return only_Numbers()" readonly="true" ></html:text>
              </core:when>
              
           <core:otherwise> 
                <html:text property="accno" size="10" onkeypress="return only_Numbers()" onblur="submit()" readonly="true" ></html:text>
            </core:otherwise>
            
       </core:choose>
      </td>
      
      <td><html:multibox property="creditgl" value="" onblur="submit()" disabled="true"></html:multibox><bean:message key="label.creditgl"></bean:message></td>	
   </tr>
   <tr>
      <td ><bean:message key="label.creditgltype"></bean:message></td>
          <td><html:select property="creditgltype" onchange="submit()">
          <%if(gl_type!=null){ %>
          <%for(int i=0;i<gl_type.length;i++){ %>
          <html:option value="<%=gl_type[i]%>"></html:option>
          <%} %>
          <%} %> 
          </html:select></td>
      <td ><bean:message key="label.creditglcode"></bean:message></td>
         <td> <html:select property="creditglcode" onchange="submit()">
          <%if(vec_gl_code!=null){%>
          <%for(int j=0;j<vec_gl_code.size();j++){ %>
          <html:option value="<%=""+vec_gl_code.get(j)%>"></html:option>
          <%} %>
          <%} %>
          </html:select></td>    
   </tr>
   
   <tr>
       <td><bean:message key="label.othercharges"></bean:message></td>
       <td>
       <core:choose>
           <core:when test="${requestScope.LoanTrnObj!=null}"> 
           
            	<html:text property="othercharges" value="${requestScope.LoanTrnObj.otherAmount}" styleClass="formTextFieldWithoutTransparent" size="10" readonly="true"></html:text>
            </core:when>
            <core:otherwise>
            	<html:text property="othercharges" styleClass="formTextFieldWithoutTransparent" size="10" readonly="true" ></html:text>
           </core:otherwise>
       </core:choose>
       </td>
       <td><bean:message key="label.penalamt"></bean:message></td>
       <td><core:choose>
           <core:when test="${requestScope.LoanTrnObj!=null}">
                <html:text property="penalamt" value="${requestScope.LoanTrnObj.penaltyAmount}" styleClass="formTextFieldWithoutTransparent" size="10" readonly="true"></html:text>
           </core:when>
       <core:otherwise>    
               <html:text property="penalamt" styleClass="formTextFieldWithoutTransparent" size="10" readonly="true"></html:text>
       </core:otherwise>
       </core:choose>
       </td>
   </tr>
   
   <tr>
       <td><bean:message key="label.intamt"></bean:message></td>   
       <td><core:choose>
           <core:when test="${requestScope.LoanTrnObj!=null}">
       			<html:text property="intamt" value="${requestScope.LoanTrnObj.interestPayable}" styleClass="formTextFieldWithoutTransparent" size="10" readonly="true"></html:text>
           </core:when>
       <core:otherwise>
       			<html:text property="intamt" styleClass="formTextFieldWithoutTransparent" size="10" readonly="true"></html:text>
       </core:otherwise>
       </core:choose>
       </td>
       
     <td><bean:message key="label.princiamt"></bean:message></td>
     <td>
     <core:choose>
       
       <core:when test="${requestScope.LoanTrnObj!=null}">
              <html:text property="prinamt" value="${requestScope.LoanTrnObj.principalPayable}" styleClass="formTextFieldWithoutTransparent" size="10" readonly="true"></html:text>
       </core:when>
       <core:otherwise>
       		  <html:text property="prinamt" styleClass="formTextFieldWithoutTransparent" size="10" readonly="true" ></html:text>
       </core:otherwise>
     </core:choose>
      </td>
       
   </tr>
   <html:hidden property="vouchernumber" styleId="vouchernum"></html:hidden>
   <html:hidden property="accountclosed" styleId="notexist"></html:hidden>
   <html:hidden property="accountnotfound" styleId="accountnotexist"></html:hidden>
   <html:hidden property="delete" styleId="delete_val"></html:hidden>
   <html:hidden property="result_del" styleId="del_res"></html:hidden>
   <html:hidden property="result_update" styleId="update_res"></html:hidden>
   <html:hidden property="result_submit" styleId="submit_res"></html:hidden>
   
   <tr>
      <html:hidden property="forward" value="error"></html:hidden>
      
      
      <td align="right"><html:submit styleClass="ButtonBackgroundImage" onclick="return set('verify');" value="verify"></html:submit></td>
      <td><html:button styleClass="ButtonBackgroundImage" property="clear" onclick="return clearPage()" value="clear"></html:button></td>
     
   </tr>
   <tr>
   <td>
   
   </td>
   </tr>
</table> 
</td>
<td>
	<%if(request.getAttribute("flag")!=null){ %>
	<jsp:include page="<%=""+request.getAttribute("flag") %>"></jsp:include>
	<%} %>
</td>
</table>

</html:form>
</body> 
</html>