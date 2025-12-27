<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@page import="masterObject.loans.PriorityMasterObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
    </style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
    
</head>
<script type="text/javascript">

function payMode()
{
	if(document.forms[0].combo_pay_mode.value=='Transfer')
	{
		document.getElementById("intup").style.display ='block';
	}
	else
	{
		document.getElementById("intup").style.display ='none';
	}
	if(document.forms[0].validateFlag.value==""){
	return false;
	}else{
	alert(document.forms[0].validateFlag.value);
	}
}
function loanSanctionSubmit()
{
	document.forms[0].submit();
}
function loanHolidayValidation()
{
	if((document.getElementById('amount').value==0.0)||(document.getElementById('amount').value==''))
	{
		alert('Enter the Amount to be Sanctioned');
		document.getElementById('amount').select;
		document.getElementById('amount').focus;
		return false;
	}
	if((document.getElementById('period').value==0)||(document.getElementById('period').value==''))
	{
		alert('Enter the Period');
		return false;
	}
	if((document.getElementById('holiday').value==0.0)||(document.getElementById('holiday').value==''))
	{
		alert('Enter the Holiday Period');
		return false;
	}
	document.forms[0].submit();
}
function numbersOnly(eve)
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
function fun_button(target)
{
   alert(target);
   document.forms[0].buttonvalue.value=target; 
}
function clearFields()
{
	document.getElementById("acctype").selectedIndex='0';
	document.getElementById("accno").value='0';
	document.getElementById("shno").value='0';
	document.getElementById("amount").value='0.0';
	document.getElementById("holiday").value='0.0';
	document.getElementById("period").value='0';
	document.getElementById("installment").value='0.0';
	document.getElementById("intrate").value='0.0';
	document.getElementById("penalrate").value='0.0';
	document.getElementById("disbamt").value='';
}

</script>

<body onload="payMode()">
<br>
	<center><font size="4" color="green">Sanction/Disbursement</font></center>
<br>
<html:form action="/Loans/LoanPersonalInfoTab?pageidentity.pageId=5090">
	<%System.out.println("hi from Loans"); %>
	<table class="txtTable">
		<tr>
			<td>
			<table class="txtTable">
		<tr>
			<td>
				&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.loanaccno"></bean:message>
				
			</td>
			<td> 
				<html:select property="acctype" styleId="acctype" styleClass="formTextFieldWithoutTransparent">
         		<core:forEach var="acType" varStatus="count" items="${requestScope.LoanModuleCode}">
      			  <html:option value="${acType.moduleCode}">
      			  <core:out value="${acType.moduleAbbrv}">
      			  </core:out>
      			  </html:option>
      	 </core:forEach>
      	 </html:select>		
				<html:text property="accno" styleId="accno" styleClass="formTextFieldWithoutTransparent" onblur="loanSanctionSubmit()" onkeypress=" return numbersOnly()" size="4" maxlength="5"></html:text>
			</td>
		</tr>
		
		<tr>
			<td>
			</td>
		</tr>
		
		<tr>
			<td>
				&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.sh_type"></bean:message>
			</td>
       		<td><html:select property="shtype" styleClass="formTextFieldWithoutTransparent">
              <html:option value="SH">SH</html:option>
           </html:select>
			<html:text property="shno" size="5" readonly="true"></html:text>	
			</td>
		</tr>
		<tr>
			<td>
			</td>
		</tr>
		<tr>
			<td>
				&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.purpose"/>
			</td>
			<td>
				<html:select property="purpose" styleClass="formTextFieldWithoutTransparent">
				<core:forEach var="acPur" varStatus="count" items="${requestScope.LoanPurpose}">
      			  <html:option value="${acPur.purposeCode}">
      			  <core:out value="${acPur.purposeDesc}">
      			  </core:out>
      			  </html:option>
					</core:forEach>
				</html:select>
			</td>
		</tr>
		<tr>
			<td>
			</td>
		</tr>
		<tr>
			<td>
				&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.details"></bean:message>
			</td>
			<td>
				<html:select property="details" styleClass="formTextFieldWithoutTransparent" onchange="submit()">
				<% String[] details; 
				   details = (String []) request.getAttribute("Details");
				   for(int i=0;i<details.length;i++)
				   {%>
					   <html:option value="<%= ""+details[i] %>"></html:option>
				  <% }%>
			   </html:select>
			</td>
		</tr>
		<tr>
			<td>
			</td>
		</tr>
		<tr>
			<td>
				&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.amount"></bean:message>
			</td>
			<td>
				<html:text property="amount" styleId="amount" styleClass="formTextFieldWithoutTransparent" size="20"></html:text>
			</td>
		</tr>
		<tr>
			<td>
			</td>
		</tr>
		<tr>
			<td>
				&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.period"/>
			</td>
			<td>
				<html:text property="period" styleId="period" styleClass="formTextFieldWithoutTransparent" size="9"></html:text>
				<html:text property="holiday" styleId="holiday" onblur="loanHolidayValidation()" size="8"></html:text>
			</td>
		</tr>
		<tr>
			<td>
			</td>
		</tr>
		 <tr>
      <td>
          &nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.Installmentamt"></bean:message></td>
        <td>  <html:text property="installment" styleId="installment" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text>
      </td>
   </tr>
      	 <tr>
			<td>
			</td>
		</tr>
   <tr>
      <td>
          &nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.intrate"></bean:message></td>
        <td>  <html:text property="intrate" styleId="intrate" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text>
      </td>
   </tr>
   <tr>
      <td>
          &nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.penalrate"></bean:message></td>
         <td> <html:text property="penalrate" styleId="penalrate" size="10" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text>
      </td>
   </tr>  
   <tr>
			<td>
			</td>
		</tr>
		<tr>
			<td>
				  &nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.priority_num"></bean:message>
			</td>
			<td>
				<html:select property="priority" styleClass="formTextFieldWithoutTransparent">
					<%PriorityMasterObject[] priority;%>
					<% priority = (PriorityMasterObject[])request.getAttribute("PriorityDesc");%>
					<%if(priority!=null){
					 for(int i=0;i<priority.length;i++)
					 {
						String prior = priority[i].getPrior_desc();
						if(prior.length()<=26)
						{
							prior = prior.substring(0,prior.length());
						}
						else
						{
							prior = prior.substring(0,35);
						}%>
					<html:option value="<%=""+prior%>"/>	
					<%}}%> 
				</html:select>
			</td>
		</tr>
		<tr>
			<td>
			</td>
		</tr>
		<tr>
      <td>
          &nbsp;&nbsp;&nbsp;&nbsp;Disb Amt:</td>
          <td> <html:text styleClass="formTextFieldWithoutTransparent" property="disbamt" styleId="disbamt" size="10" onkeypress="return only_numbers()"  onblur="submit()"></html:text>         
      </td>
   </tr>
   <tr>
   	<td>
   	</td>
   </tr>
		<tr>
			<td>
         &nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.combo_pay_mode"></bean:message></td>
       <td>  <html:select property="combo_pay_mode" styleClass="formTextFieldWithoutTransparent" onchange="payMode()">
           <html:option value="Cash">Cash</html:option>
           <html:option value="Transfer">Transfer</html:option>
           <html:option value="Pay Order">Pay Order</html:option>
         </html:select>
         <div id="intup" style="display:none;"> 
		<br>Account Type: <html:select property="payAccountType" styleClass="formTextFieldWithoutTransparent">
				<core:forEach var="payAcType" varStatus="count" items="${requestScope.AccountTypeCode}">
      			  <html:option value="${payAcType.moduleCode}">
      			  <core:out value="${payAcType.moduleAbbrv}">
      			  </core:out>
      			  </html:option>
					</core:forEach>
				</html:select>
		Acc Num: <font color="red"><html:text property="payAccNo" value="" onblur="submit()"></html:text></font>
		&nbsp;&nbsp;&nbsp;
		<br>Name: <font color="red"><html:text property="payAccName" disabled="true"></html:text></font>			      
         </div>
        
      </td>
		</tr>
		<html:hidden property="buttonvalue" value="error"/>
		<html:hidden property="validateFlag"></html:hidden>
		<tr>
			<td>
			</td>
		</tr>
			<tr>
			<td>
			</td>
		</tr>
		
			<tr>
			<td>
			</td>
		</tr>
			<tr>
			<td>
			</td>
			<td>
				<html:submit styleClass="ButtonBackgroundImage" value="Submit" onclick="return fun_button('Submit')">Submit</html:submit>
				&nbsp;&nbsp;<html:submit styleClass="ButtonBackgroundImage" onclick="return clearFields()">Clear</html:submit>
			</td>
		</tr>
	</table>
	</td>
	<td>
		<%
                        String jspPath=null; 
			 			jspPath=(String)request.getAttribute("flag");
                     		System.out.println("------>>>>>"+jspPath);
                         if(jspPath!=null){
                     %>
                         <jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>

                     <%  } %>
		
	</td>
	</tr>
	</table>
	
		 	
</html:form>
</body>
</html>