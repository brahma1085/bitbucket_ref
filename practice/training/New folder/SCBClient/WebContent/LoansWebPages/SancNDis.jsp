<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt" %>
<%@page import="java.util.Vector"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
    </style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
</head>
<body>
<br>
<h1>
<center>
	<b>
		<font size="4" color="green">
			Loan Sanction/Disbursement
		</font>
	</b>
</center>
</h1>
<html:form action="/Loans/Sanctioning">
<table>
	<tr>
		<td>
			<table class="txtTable" align="left">
				<tr>
					<td>
       					&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.sh_type"/>
       				</td>
       				<td>
       					<html:select property="shtype" styleId="shtype" styleClass="formTextFieldWithoutTransparent">
              				<html:option value="6">
              					SH
              				</html:option>
           				</html:select>
           				<html:text property="shno" size="20" styleClass="formTextFieldWithoutTransparent">
       					</html:text>
       					<tr>
				<td height="2px">
				</td>
    			<tr>
       				<td>
       				&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.purpose"/>
       				</td>
           			<td>
           				<html:select property="purpose" styleClass="formTextFieldWithoutTransparent">
           					<html:option value="a">Purpose of loan taking 1&nbsp;&nbsp;&nbsp;&nbsp;</html:option>
           					<html:option value="a">Purpose of loan taking 2</html:option>
           					<html:option value="a">P4</html:option>
           					<html:option value="a">P6</html:option>
				        </html:select>
				    </td>   
 				</tr>
 				<tr>
				<td height="2px">
				</td>
				</tr>
				 <tr>
       				<td>
       					&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.details"/>
       				</td>
       				<td>
       					<html:select property="details" styleClass="formTextFieldWithoutTransparent">
        					<html:option value="Personal">Personal</html:option>
       						<html:option value="Application">Application</html:option>
       						<html:option value="Employment">Employment</html:option>
					        <html:option value="Property">Property</html:option>
					        <html:option value="Loan and Share Details">Loan and Share Details&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html:option>
					        <html:option value="Signature Ins">Signature Ins</html:option>
					    </html:select>
					 </td>
     		   	</tr>
           		<tr>
				<td height="2px">
				</td>
				</tr>
				
           		<tr>
      				<td>
          			&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.amount"/>
          			</td>
          			<td>
          				<html:text styleClass="formTextFieldWithoutTransparent" property="amount" size="16">
          				</html:text>
      				</td>
   				</tr> 	
   				<tr>
				<td height="2px">
				</td>
				</tr>
				
   				<tr>
      				<td>
          		&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.period"/>
          			</td>
          			<td>
          				<html:text styleClass="formTextFieldWithoutTransparent" property="period" size="16">
          				</html:text>
          				<html:text styleClass="formTextFieldWithoutTransparent" property="holiday" size="8">
          				</html:text>
      				</td>
   				</tr>
   				<tr>
				<td height="2px">
				</td>
				</tr>
				 <tr>
      <td>
          &nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.Installmentamt"></bean:message></td>
        <td>  <html:text styleClass="formTextFieldWithoutTransparent" property="installment" size="10" onkeypress="return only_numbers()"  onblur="submit()"></html:text>
      </td>
   </tr>
      	 <tr>
				<td height="2px">
				</td>
				</tr>
   <tr>
      <td>
          &nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.intrate"></bean:message>
      </td>
       <td>
      	12323
      </td>   
     
     
   </tr> 
   <tr>
				<td height="2px">
				</td>
				</tr>
   
   <tr>
      <td>
         &nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.penalrate"></bean:message>
      </td>
      <td>
      	12323
      </td>   
        
   </tr>  	 
   <tr>
				<td height="2px">
				</td>
				</tr>
	<tr>
      <td>
        &nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.priority_num"/>
      </td>
       <td> 
       		<html:select property="priority" styleClass="formTextFieldWithoutTransparent">
       			<html:option value="er">Priority 1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html:option>
       			<html:option value="er">Priority 2</html:option>
       			<html:option value="er">Priority 3</html:option>
       			<html:option value="er">Priority 4</html:option>
       		</html:select>&nbsp;&nbsp;
       		<html:checkbox property="weak"></html:checkbox><b>Weaker Section</b>
      </td>		
		</tr>
		<tr>
				<td height="2px">
				</td>
				</tr>
		<tr>
		<td>
		&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.disAmt.disLeft"/>
		</td>
		  <td> 
          <html:text styleClass="formTextFieldWithoutTransparent" property="disbamt" size="18">
          </html:text>         
      </td>
		</tr>
	   <tr>
				<td height="2px">
				</td>
	   </tr>
	   <tr>
      <td>
       &nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.combo_pay_mode"/>
      </td>
       <td>  
       <html:select property="combo_pay_mode" styleClass="formTextFieldWithoutTransparent">
           <html:option value="Cash">Cash</html:option>
           <html:option value="Transfer">Transfer</html:option>
           <html:option value="Pay Order">Pay Order</html:option>
         </html:select>
      </td>
   </tr>
   <tr>
				<td>
				<br>
				</td>
				</tr>
		<tr>
				<td>
				<br>
				</td>
				</tr>	
	<tr>
		<td>
		</td>
		<td>
			<html:submit styleClass="ButtonBackgroundImage">Submit</html:submit>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:cancel styleClass="ButtonBackgroundImage">Cancel</html:cancel>
		</td>
	</tr>  
    		</table>
		</td>
	</tr>
  			</table>
	
</html:form>
</body>
</html>