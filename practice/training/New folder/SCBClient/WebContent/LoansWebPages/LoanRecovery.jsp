<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="masterObject.loans.LoanTransactionObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>  
<%@page import="java.util.Map"%> 
<%@page import="masterObject.loans.LoanMasterObject"%>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="com.scb.loans.forms.LoanStatusForm"%>
<%@page import="masterObject.share.ShareMasterObject"%>
<html>
<head>

<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<h2 class="h2"><center>Query On Loan Status</center></h2>
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
   






function chkAccountExist()
{
  
  if(document.getElementById("notfound").value!=null)
  {
    if(document.getElementById("notfound")=="accountnotfound")
    {
      alert("Account Not Found");
      document.getElementById("notfound").value="";
      return false;
    }
  
  }
  
  if(document.forms[0].clearFl.value!=""){
      if(document.forms[0].clearFl.value!="Y"){
         var form_ele = document.forms[0].elements;
  
  		for(var i=0;i< form_ele.length;i++ )
		{
			if(form_ele[i].type=="text")
				form_ele[i].value = "";
			if(form_ele[i].type=="hidden")
		        form_ele[i].value = "";
		}
  
      }
  }

}

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


</script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
   
</head>
<body onload="return chkAccountExist()">
<%! ModuleObject[] lmobj=null; %>
<%! String arr[]=null;%>
<%! LoanStatusForm stat; %>
<%stat=(LoanStatusForm)request.getAttribute("LoanStatus");%>
<%System.out.println("Loan status form=========>"+stat); %>

<%if(stat!=null){ 
  System.out.println("^^^^^^^^^^^^^^"+stat.getTxt_loanba());		 
} %>
<%
    arr = (String[])request.getAttribute("LoanQueryDet");
    lmobj = (ModuleObject[])request.getAttribute("LoanModuleCode");
    
String msg=(String)request.getAttribute("msg");
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
System.out.println("11111111"+access);
			if(user_role.get(accesspageId)!=null){
			System.out.println("222222222"+access);
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}
			}
// Note:access=1111==>read,insert,delete,update
%>

<%if(msg!=null){ %>
  <font color="red"><%=msg %></font>
  <br><br>
<%} %>

<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%> 
<table>
<td>
<html:form action="/Loans/LoanRecovery?pageidentity.pageId=5017">
<table>
<tr>
   
      
      <td><bean:message key="label.combo_loan"></bean:message></td>
      <td><html:select property="acctype">
       <html:option value="SELECT">Select</html:option>
         		  <core:forEach var="acType" varStatus="count" items="${requestScope.LoanModuleCode}">
      			  <html:option value="${acType.moduleCode}"><core:out value="${acType.moduleAbbrv}"></core:out></html:option>		  
      </core:forEach>
      </html:select></td>
   
</tr>
<tr>
  
      <td><bean:message key="label.loanaccno"></bean:message></td>
      <td><html:text property="accno"  onblur="submit()" onkeypress="return only_Numbers()" onkeyup="numberlimit(this,'11')"></html:text></td>
</tr>

<tr>
   
      <td><bean:message key="label.Shno"></bean:message></td>
      <td><html:text property="shno" size="8" styleClass="formTextField" readonly="true"></html:text></td>
   
</tr>

<tr>
   
      <td><bean:message key="label.shareamt"></bean:message></td>
      <td><html:text property="shamt" size="10" styleClass="formTextField" readonly="true"></html:text></td>
                                                                                
</tr>


<tr>
   
      <td><bean:message key="label.sharetype"></bean:message></td>
      <td><html:text property="shtype" size="8" styleClass="formTextField" readonly="true"></html:text></td>
</tr>

<tr>
   
      <td><bean:message key="label.excessshareamt"></bean:message></td>
      <td><html:text property="excessshamt" size="10" styleClass="formTextField" readonly="true"></html:text></td>
   
</tr>


<tr>
   
      <td><bean:message key="label.loanamt"></bean:message></td>
      <td><html:text property="loanamt" size="10" styleClass="formTextField" readonly="true"></html:text>
   </td>
</tr>

<tr>
      <td><bean:message key="label.disbureddate"></bean:message></td>
      <td><html:text property="disbursed_on" size="10" styleClass="formTextField" readonly="true"></html:text></td>
</tr>

<tr>
      <td><bean:message key="label.Installmentamt"></bean:message></td>
      <td><html:text property="instamt" size="10" styleClass="formTextField" readonly="true"></html:text></td>
</tr>


<tr>
      <td><bean:message key="label.loanperiod"></bean:message></td>
      <td><html:text property="period" size="10" styleClass="formTextField" readonly="true"></html:text></td>
</tr>


<tr>
   
      <td><bean:message key="label.intrate"></bean:message></td>
      <td><html:text property="intrate" size="10" styleClass="formTextField" readonly="true"></html:text></td>
</tr>

<tr>
   <td><bean:message key="label.penalrate"></bean:message></td>
      <td><html:text property="penrate" size="10" styleClass="formTextField" readonly="true"></html:text></td>
</tr>

<tr>
   <td><bean:message key="label.purpose"></bean:message></td>
      <td><html:text property="purpose" styleClass="formTextField" readonly="true"></html:text></td>
</tr>


<tr>
   <td><bean:message key="label.repayment"></bean:message></td>
   <td><html:text property="repay_type" size="10" styleClass="formTextField" readonly="true"></html:text></td>
</tr>


<tr>
   <td><bean:message key="label.intratetype"></bean:message></td>
   <td><html:text property="int_rate_type" size="10" styleClass="formTextField" readonly="true"></html:text></td>
   
</tr>
    <html:hidden property="clearFl"></html:hidden> 
 	<html:hidden property="defaultTab" value="Personal"></html:hidden> 
    <html:hidden property="defaultTab" value="LoanStatus"></html:hidden>
    <html:hidden property="defaultTab" value="LoanHistory"></html:hidden>
    <html:hidden property="defaultTab" value="LoanTran"></html:hidden>
       
    <html:hidden property="accountnotfound" styleId="notfound"></html:hidden>
       				
 
</table>
</html:form>
     
</td>   
<td>
	<% String jspPath=(String)request.getAttribute("flag");
    	  			System.out.println("jspPath=="+jspPath);
         		%>
   				<%if(jspPath!=null){ %>
   				<jsp:include page="<%=jspPath%>"></jsp:include>
				<%}%>
</td>
</table>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>