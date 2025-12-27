<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%! double loanbalance,amtpaid,intamt,intrate,Interestpaid,trf_others,trf_extra_int; %>

<%! String intpaiduptodate,lastdetail; %>


<%System.out.println("**************hi from status page*****************"); %>

<%intpaiduptodate=(String)request.getAttribute("intpaiduptodate");%>

<%lastdetail=(String)request.getAttribute("lastdetail");%>

<% loanbalance=(Double)request.getAttribute("loanbalance");%>

<%System.out.println("loanbalance====1========>"+loanbalance); %>

<%amtpaid=(Double)request.getAttribute("amtpaid");%>

<%System.out.println("amtpaid=====2=======>"+amtpaid); %>

<%intamt=(Double)request.getAttribute("intamt");%>

<%System.out.println("intamt======3======>"+intamt); %>

<%intrate=(Double)request.getAttribute("intrate");%>

<%System.out.println("intrate======4======>"+intrate); %>



<%Interestpaid=(Double)request.getAttribute("Interestpaid");%>

<%System.out.println("Interestpaid=======6=====>"+Interestpaid); %>

<html:form action="/LDStatus?pageId=6005"> 
<table>
  
    <h1><center><font color="red" size="3" style="font-family:serif;font-style: bold"><bean:message key="lable.lnstatus"></bean:message></font></center></h1>
        
	<tr>
	   <td align="right"><bean:message key="lable.lb"></bean:message></td>
	   
	   <td><html:text property="txt_lb" size="10" value="<%=""+loanbalance%>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	   
	</tr>
	<tr>
	   <td align="right"><bean:message key="lable.intamtpaid"></bean:message></td>
	   
	   <td><html:text property="txt_amtpaid" size="10" value="<%=""+amtpaid%>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	   
	</tr>
	<tr>
	   <td align="right"><bean:message key="label.int_upto_date"></bean:message></td>
	   <%if(intpaiduptodate!=null){ %>
	   <td><html:text property="txt_intuptodate" size="10" value="<%=intpaiduptodate%>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	   <%}else{%>
	   <td><html:text property="txt_intuptodate" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	   <% } %>
	</tr>
	<tr>
	   <td align="right"><bean:message key="lable.intamt"></bean:message></td>
	   
	   <td><html:text property="txt_intamt" size="10" value="<%= ""+intamt%>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	      
	</tr>
	<%trf_others=(Double)request.getAttribute("trfothers");%>
	   <tr>
	      <td align="right"><bean:message key="label.others"></bean:message></td>
	      
	      <td><html:text property="txt_others" value="<%=""+trf_others%>" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	   </tr>
	   
	   <%trf_extra_int=(Double)request.getAttribute("trfextraint");%>
	    <tr>
	      <td align="right"><bean:message key="label.extraInt"></bean:message></td>
	      
	      <td><html:text property="txt_extraint" value="<%=""+trf_extra_int%>" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	   </tr>
	
	<tr>
	
	   <td align="right"><bean:message key="lable.intrate"></bean:message></td>
	  
	   <td><html:text property="txt_intrate" size="10" value="<%=""+intrate%>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	   
	</tr>
	<tr>
	    <td><h1><font>--------------------------------------------------</font></h1></td>
	</tr>
	<tr>
	   <td>
	   <h1><center><font color="red" size="3" style="font-family: serif;font-style: bold"><bean:message key="lable.paydet"></bean:message></font></center></h1>
	   </td>
	</tr>
	<tr>
	   <td align="right"><bean:message key="lable.lb"></bean:message></td>
	  
	   <td><html:text value="<%=""+loanbalance%>" property="txt_lb1" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	   
	</tr>
	<tr>
	   <td align="right"><bean:message key="lable.Iamt"></bean:message></td>
	   
	   <td><html:text value="<%= ""+Interestpaid%>" property="txt_Iamt" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	   
	</tr>
	<tr>
	   <td align="right"><bean:message key="label.paid"></bean:message></td>
	   <% if(lastdetail!=null){ %>
	   <td><html:text property="txt_paid" size="10" value="<%=""+lastdetail%>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	  <%}else{ %>
	  <td><html:text property="txt_paid" size="10" value="-----" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	  <% } %>
	</tr>
</table>
</html:form>
</body>
</html>