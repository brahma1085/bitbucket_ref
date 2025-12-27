<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
    
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.scb.loans.forms.LoanStatusForm"%>
<%@page import="masterObject.loans.LoanTransactionObject"%>
<html>
<head>
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
<h2 class="h2"><center>Loan Status</center></h2></head>


<body bgcolor="beige">
<script type="text/javascript">
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





</script>


<%! LoanStatusForm stat; %>
<%!LoanTransactionObject tranobj;
 String advance="0.0";
 String overdue="0.0";
%>
<%tranobj=(LoanTransactionObject)request.getAttribute("LoanStatusInfo"); 
  
  tranobj=(LoanTransactionObject)request.getAttribute("LoanTranObject"); 
  String totalLoanAmount=(String)request.getAttribute("TotalLoanAmount");
  String totalClosableAmount=(String)request.getAttribute("TotalClosableAmount");
  String advancePaidAmount=(String)request.getAttribute("AdvancePaidAmount");
  if(tranobj!=null){
	  	System.out.println("Inside Loanstatus.jsp value of Advance Paid---Amzad"+tranobj.getPrincipalBalance());
  		advance=String.valueOf(tranobj.getPrincipalPaid());
  		overdue=String.valueOf(tranobj.getPrincipalBalance());
  }
%>


<%if(stat!=null){ 
  System.out.println("^^^^^^^^^^^^^^"+stat.getTxt_loanba());} %>


<html:form action="/Loans/LoanStaus?pageId=5018">



	<table cellspacing="5" class="txtTable" style="border: thin solid black;">
	      <tr>
		  	<td align="right">
		  	    <bean:message key="label.loanbal"></bean:message>
		  	    <%if(stat!=null){ %>
		  	    <html:text property="txt_loanba" value="<%=""+ stat.getTxt_loanba() %>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
		  	    <%}else if(tranobj!=null){ %>
		  	    <html:text property="txt_loanba" value="<%=""+tranobj.getLoanBalance()  %>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
		  	    <%}else{ %>
		  	    <html:text property="txt_loanba" style="border:transparent;background-color:beige;color:red" onkeypress="return only_numbers_doublevalue()"></html:text>
		  	    <%} %>
		  	</td>	
		  </tr>	
		  <tr>
		  	<td align="right">
		  	    <bean:message key="label.advpaid"></bean:message>
		  	    <%if(stat!=null){ %>
		  	    <html:text property="txt_addpaid" value="<%=""+ stat.getTxt_addpaid()%>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
		  	    <%}else if(advance!=null){ %>
		  	    <html:text property="txt_addpaid" value="<%=advance %>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
		  	    <%}else { %>
		  	    <html:text property="txt_addpaid" style="border:transparent;background-color:beige;color:red" onkeypress="return only_numbers_doublevalue()"></html:text>
		  	    <%} %>
		  	</td>	
		  </tr>
		  <tr>
		  	<td align="right">
		  	    <bean:message key="label.po"></bean:message>
		  	    <%if(stat!=null){%>
		  	    <html:text property="txt_ppoverdue" value="<%= ""+ stat.getTxt_ppoverdue()%>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
		  	    <%}else if(tranobj!=null){ %>
		  	    <html:text property="txt_ppoverdue" value="<%=overdue%>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
		  	    <%}else{ %>
		  	    <html:text property="txt_ppoverdue" style="border:transparent;background-color:beige;color:red" onkeypress="return only_numbers_doublevalue()"></html:text>
		  	    <%} %>
		  	</td>	
		  </tr>
		  <tr>
		  	<td align="right">
		  	    <bean:message key="label.ci"></bean:message>
		  	    <%if(stat!=null){ %>
		  	    <html:text property="txt_current_inst" value="<%=""+ stat.getTxt_current_inst()%>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
		  	    <%}else if(tranobj!=null){ %>
		  	    <html:text property="txt_current_inst" value="<%= ""+tranobj.getPrincipalPayable() %>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
		  	    <%} else { %>
		  	    <html:text property="txt_current_inst" style="border:transparent;background-color:beige;color:red" onkeypress="return only_numbers_doublevalue()"></html:text>
		  	    <%} %>
		  	</td>	
		  </tr>
		  <tr>
		  	<td align="right">
		  	    <bean:message key="label.interest"></bean:message>
		  	    <%if(stat!=null){ %>
		  	    	<html:text property="txt_interest" value="<%=""+ stat.getTxt_interest()%>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
		  	    	<%}else if(tranobj!=null){ %>
		  	    	<html:text property="txt_interest" value="<%= ""+tranobj.getInterestPayable() %>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
		  	     	<%} else { %>	
		  	        <html:text property="txt_interest" style="border:transparent;background-color:beige;color:red" onkeypress="return only_numbers_doublevalue()"></html:text>
		  	     <%} %>   
		  	</td>	
		  </tr>
		  <tr>
		  	<td align="right">
		  	    <bean:message key="label.pi"></bean:message>
		  	    <%if(stat!=null){ %>
		  	    <html:text property="txt_pinealInt" value="<%=""+ stat.getTxt_pinealInt()%>" style="border:transparent;background-color:beige;color:red" readonly="true" ></html:text>
		  	    <%}else if(tranobj!=null){ %>
		  	    <html:text property="txt_pinealInt" value="<%= ""+tranobj.getPenaltyAmount() %>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
		  	    <%} else { %>
		  	    <html:text property="txt_pinealInt" style="border:transparent;background-color:beige;color:red" onkeypress="return only_numbers_doublevalue()"></html:text>
		  	    <%}%>
		  	</td>	
		  </tr>
		  <tr>
		  	<td align="right">
		  	    <bean:message key="label.oc"></bean:message>
		  	    <%if(stat!=null){ %>
		  	    <html:text property="txt_otherchar" value="<%= ""+stat.getTxt_otherchar() %>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
		  	    <%}else if(tranobj!=null){ %>
		  	    <html:text property="txt_otherchar" value="<%= ""+tranobj.getOtherAmount() %>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
		  	    <%}else { %>
		  	    <html:text property="txt_otherchar" style="border:transparent;background-color:beige;color:red" onkeypress="return only_numbers_doublevalue()"></html:text>
		  	    <%} %>
		  	</td>	
		  </tr>
		  <tr>
		  	<td align="right">
		  	    <bean:message key="label.ei"></bean:message>
		  	    <%if(stat!=null){ %>
		  	    <html:text property="txt_extra" value="<%= ""+stat.getTxt_extra() %>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
		  	    <%}else if(tranobj!=null){ %>
		  	    <html:text property="txt_extra" value="<%= ""+tranobj.getExtraIntAmount() %>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
		  	    <%}else{ %>
		  	    <html:text property="txt_extra" style="border:transparent;background-color:beige;color:red" onkeypress="return only_numbers_doublevalue()"></html:text>
		  	    <%} %>
		  	</td>	
		  </tr>
		  <tr>
		  	<td align="right">
		  	    <bean:message key="label.totalamt"></bean:message>
		  	    <%if(stat!=null){ %>
		  	    <html:text property="txt_tca" value="<%= ""+stat.getTxt_tca() %>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
		  	    <%}else if(totalLoanAmount!=null){ %>
		  	    
		  	    <html:text property="txt_tca" style="border:transparent;background-color:beige;color:red" value="<%=totalLoanAmount %>" readonly="true"></html:text>
		  	    
		  	    <%}else { %>
		  	    <html:text property="txt_tca" style="border:transparent;background-color:beige;color:red" onkeypress="return only_numbers_doublevalue()"></html:text>
		  	    <%} %>
		  	   
		  	</td>	
		  </tr>
		  <tr>
		  	<td align="right">
		  	    <bean:message key="label.closeamt"></bean:message>
		  	    <%if(stat!=null){ %>
		  	    <html:text property="txt_totcloamt" value="<%= ""+stat.getTxt_totcloamt() %>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
		  	    <%}else if(totalClosableAmount!=null){ %>
		  	    
		  	    <html:text property="txt_tca"  style="border:transparent;background-color:beige;color:red" value="<%=totalClosableAmount %>" readonly="true"></html:text>
		  	    
		  	    <%}else { %>
		  	    <html:text property="txt_tca" style="border:transparent;background-color:beige;color:red" onkeypress="return only_numbers_doublevalue()"></html:text>
		  	    <%} %>
		  	</td>	
		  </tr>
		  
	</table>	
</html:form>
</body>
</html>