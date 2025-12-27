<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@page import="masterObject.loans.PriorityMasterObject"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Vector"%>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.loans.LoanMasterObject"%>
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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" /><h2 class="h2"><center>Loan Ammendments</center></h2>
    
</head>
<script type="text/javascript">


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



function setDisb(target)
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
		else
		{
			document.forms[0].forward.value=target;
			document.forms[0].submit();
		}


}

function setSub(target)
{		
		alert(target);
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
		else if(document.forms[0].disbamt.value=="")
		{
			alert('Disbursement Amount Cannot Be Blank');
   			document.forms[0].disbamt.focus();
		}
		else if(document.forms[0].disbamt.value==0.0)
		{
			alert('Disbursement Amount Cannot Be Zero');
   			document.forms[0].disbamt.focus();
		}
		else
		{
			document.forms[0].forward.value=target;
			document.forms[0].submit();
		}
 
}

function setDel(target)
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
		else
		{
			document.forms[0].forward.value=target;
			document.forms[0].submit();
		}
	

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
<%! String str[];%>
<% str=(String[])request.getAttribute("Details"); %>
<%System.out.println("hi from sanction"); %>
<%!String app,per,veh,prop,emp,sign,coBorow,relative,lsdet,gold,surities,schedule,msg;
 LoanMasterObject loanobj=null;%>  
<% loanobj=(LoanMasterObject)request.getAttribute("LoanDetails");%>
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
<%schedule=(String) request.getAttribute("Schedule"); %>
<%msg=(String) request.getAttribute("msg"); %>

<%! PriorityMasterObject[] PriorityDesc;%> 

<%PriorityDesc=(PriorityMasterObject[])request.getAttribute("PriorityDesc"); %>
</script>

<body onload="payMode()">

<html:form action="/Loans/LoanAmmendments?pageidentity.pageId=5051">
	
	<%if(msg!=null){ %>
	<font color="red"><%=""+msg%></font>
	<%} %>
	<html:hidden property="forward" value="error"></html:hidden>
	
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
				<html:text property="accno" styleId="accno" styleClass="formTextFieldWithoutTransparent" onblur="loanSanctionSubmit()" onkeypress=" return numbersOnly()" size="7" maxlength="7"></html:text>
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
              <html:option value="1001001">SH</html:option>
           </html:select>
			<html:text property="shno" size="7" readonly="true"></html:text>	
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
           <td><html:select property="detailsammend" onchange="submit()">
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
           <td><html:select property="detailsammend" onchange="submit()">
       <%for(int i=0;i<str.length;i++){ %>
       <html:option value="<%=str[i]%>"><%=str[i]%></html:option>
       
       <%}%>
       
       </html:select></td>
       <%}%>    
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
			</td>
		</tr>
		<tr>
			<td>
				  &nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="label.priority_num"></bean:message>
			</td>
			<td>
				<html:select property="priority" styleClass="formTextFieldWithoutTransparent">
					<html:option value="None"/>	
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
						}
					%>	
						
					<html:option value="<%=""+prior%>"/>
						
					<%} }%> 
				</html:select>
			</td>
		</tr>
		<tr>
			<td>
			</td>
		</tr>
	<tr>
	
	<%if(loanobj!=null){
	 double disbamt=loanobj.getSanctionedAmount()-loanobj.getDisbursementLeft(); %>
      <td>
          &nbsp;&nbsp;&nbsp;&nbsp;Disb Amt:</td>
          <td> <html:text styleClass="formTextFieldWithoutTransparent" property="disbamt" value="<%="" + disbamt %>" styleId="disbamt" size="10" onkeypress="return only_numbers()"  onblur="setDisb('Disburse')"></html:text></td>
          <% }else { %>  																	
          <td> <html:text styleClass="formTextFieldWithoutTransparent" property="disbamt" value="" styleId="disbamt" size="10" onkeypress="return only_numbers()"  onblur="setDisb('Disburse')"></html:text>
          <% } %>       
      </td>
      </tr>
      <tr>
      <%if(loanobj!=null){%>
      <td>
          &nbsp;&nbsp;&nbsp;&nbsp;Disb Left:</td>
          <td> <html:text styleClass="formTextFieldWithoutTransparent" property="disbleft" value="<%="" +loanobj.getDisbursementLeft() %>" styleId="disbleft" size="10" onkeypress="return only_numbers()" readonly="true"></html:text></td>
          <% }else { %>
          <td> <html:text styleClass="formTextFieldWithoutTransparent" property="disbleft" styleId="disbleft" size="10" onkeypress="return only_numbers()" readonly="true"></html:text>
           <% } %>         
      </td>
      </tr>
   <tr>
   	<td>
   	</td>
   </tr>
		<tr>
      <td>
         <bean:message key="label.combo_pay_mode"></bean:message></td>
       <td>  
       <html:select property="combo_pay_mode" onchange="payMode()">
           <html:option value="C">Cash</html:option>
           <html:option value="T">Transfer</html:option>
           <html:option value="P">PayOrder</html:option>
         </html:select>
      </td>
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
    <td><html:text property="payaccno" size="10"></html:text></td>
    
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
		</tr>
			<tr>
			<td>
			</td>
			<td>
				<html:submit styleClass="ButtonBackgroundImage"  property="disburse_submit" value="Submit" onclick="submit()">Submit</html:submit>
				<html:button property="clear" styleClass="ButtonBackgroundImage" onclick="return clearFields()">Clear</html:button>
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
</html:form>
</body>
</html>