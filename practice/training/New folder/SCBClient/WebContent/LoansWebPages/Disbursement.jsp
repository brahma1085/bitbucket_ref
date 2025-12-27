<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@page import="masterObject.loans.PriorityMasterObject"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Map"%>
<%@page import="masterObject.general.ModuleObject"%>
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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" /><h2 class="h2"><center>Disbursement of the Loan</center></h2>
    
</head>
<script type="text/javascript">
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
      	//alert("Alphabets are not allowed,Please enter numbers only ");
       	return false ;
   }
}


function payMode()
{
  if(document.forms[0].combo_pay_mode.value=="T")
  {
  	document.forms[0].payactype.disabled=false;
  	document.forms[0].payaccno.disabled=false;
  }
  else
  {
  	document.forms[0].payactype.disabled=true;
  	document.forms[0].payaccno.disabled=true;
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
<%!String app,per,veh,prop,emp,sign,coBorow,relative,lsdet,gold,surities,schedule,msg;%>  
<%! String priority_code;%>
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
<%priority_code=(String)request.getAttribute("priority_code"); %>
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

<%PriorityDesc=(PriorityMasterObject[])request.getAttribute("PriorityDesc"); %>
</script>


<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
<html:form action="/Loans/Disbursement?pageidentity.pageId=5099">
	<body onload="payMode()">
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
				<html:text property="accno" styleId="accno" styleClass="formTextFieldWithoutTransparent" onblur="loanSanctionSubmit()" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'11')" ></html:text>
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
       		<td><html:select property="shtype" styleClass="formTextFieldWithoutTransparent" disabled="true">
              <html:option value="1001001">SH</html:option>
           </html:select>
			<html:text property="shno" size="7" readonly="true" ></html:text>	
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
				<html:select property="purpose" styleClass="formTextFieldWithoutTransparent" disabled="true">
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
           <td><html:select property="details" onchange="submit()" >
        <%
        	details=arr.elements();
        	en_pageID=vec_pageId.elements();
            while(details.hasMoreElements()&&en_pageID.hasMoreElements()){
            	String str=""+details.nextElement();
            	String str_pageId=""+en_pageID.nextElement();
        %>
        <html:option value="<%=str_pageId%>"><%=str%></html:option>
         
       <%}%>
       <!--<html:option value="Scheduling">Scheduling</html:option>
       --></html:select></td>
       <%}else{%>
       <td><bean:message key="label.details"></bean:message></td>
           <td><html:select property="details" onchange="submit()" >
       <%for(int i=0;i<str.length;i++){ %>
       <html:option value="<%=str[i]%>"><%=str[i]%></html:option>
       
       <%}%>
       <html:option value="Scheduling">Scheduling</html:option>
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
				<html:text property="amount" styleId="amount" styleClass="formTextFieldWithoutTransparent" size="20" readonly="true"></html:text>
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
				<html:text property="period" styleId="period" styleClass="formTextFieldWithoutTransparent" size="9" readonly="true"></html:text>
				<html:text property="holiday" styleId="holiday" onblur="loanHolidayValidation()" size="8" readonly="true"></html:text>
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
          <bean:message key="label.priority_num"></bean:message></td>
   <%if(priority_code!=null){ %>
     
          <td> <html:select property="priority" value="<%=priority_code %>">
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
      <td>WeakSection: <html:checkbox property="weak"></html:checkbox>
      </td>
      </tr>
		<tr>
			<td>
			</td>
		</tr>
	<tr>
      <td>
          &nbsp;&nbsp;&nbsp;&nbsp;Disb Amt:</td>
          <td> <html:text styleClass="formTextFieldWithoutTransparent" property="disbamt" styleId="disbamt" size="10" onkeypress="return only_numbers_doublevalue()" 
onkeyup="numberlimit(this,'11')" onblur="setDisb('Disburse')"></html:text>         
      </td>
      </tr>
      <tr>
      <td>
          &nbsp;&nbsp;&nbsp;&nbsp;Disb Left:</td>
          <td> <html:text styleClass="formTextFieldWithoutTransparent" property="disbleft" styleId="disbleft" size="10"  readonly="true"></html:text>         
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
			<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
			<td>
				<html:button styleClass="ButtonBackgroundImage" property="disburse_submit" value="Submit" onclick="setSub('Submit')">Submit</html:button></td>
				<%}else{ %>
				<td>
				<html:button styleClass="ButtonBackgroundImage" property="disburse_submit" value="Submit" disabled="true" >Submit</html:button></td>
				<%} %>
				<!--
				<html:button styleClass="ButtonBackgroundImage" property="disburse_submit" value="Delete" onclick="setDel('Delete')">Delete</html:button>-->
				
				<td><html:button property="clear" styleClass="ButtonBackgroundImage" onclick="return clearFields()">Clear</html:button>
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
          <%}
         	else if(veh!=null){%>
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
          <%if(schedule!=null){ %> 
		   <td align="right"><jsp:include page="<%=schedule%>"></jsp:include></td>    
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