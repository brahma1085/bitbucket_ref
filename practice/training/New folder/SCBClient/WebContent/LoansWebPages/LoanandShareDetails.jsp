<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<%System.out.println("++++++++++++++++++==Hi from Loansharedetail+++++++++++++====="); %>
   
  <%!int category=0; %>
  <%!double loan_amt=0.0,amt=0.0,disb_amt=0.0,sanc_amt=0.0,share_amt=0.0; %>
  
   
  <%!Object[][] sharedet;%>
  <%sharedet=(Object[][])request.getAttribute("L&DDetails");%> 
  
  
  <%!Double double_maxloanamt,maxloanins; %>
  <%double_maxloanamt=(Double) request.getAttribute("MaxLoanAmt"); %>
  <%System.out.println("MaxLoanAmt"+double_maxloanamt);%>  
  <%maxloanins=(Double) request.getAttribute("MaxLoanIns"); %>
  <%System.out.println("LoanIns"+maxloanins);%>
  
  
  <%
  if(sharedet!=null){
     for(int i=1;i<sharedet.length;i++)
     {
    	 Double.parseDouble(sharedet[i][4].toString());
     }
  }  
  %>
  
<table cellspacing="5" class="txtTable">
<tr>
  
    <td><center><h1 style="font:small-caps;font-style:normal;font-size:small;">LoanandShareDetails</h1></center></td>
  
</tr>
<tr>
<html:form action="/Loans/LoanandShareDetails?pageidentity.pageId=5029">

<table class="txtTable" style="border-bottom-color:black;border-bottom-style:solid;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">
<tr>
			<td class="PageHeader"><%="LoanType" %></td>	
			<td class="PageHeader"><%="LoanA/cNo" %></td>
			<td class="PageHeader"><%="Sanc Amt" %></td>
			<td class="PageHeader"><%="Disb Left" %></td>
			<td class="PageHeader"><%="Prn Bal" %></td>
			<td class="PageHeader"><%="Share %" %></td>
			<td class="PageHeader"><%="Amount" %></td>
		</tr>
  
		<%if(sharedet!=null)
		{ 
			for(int i=1;i<sharedet.length;i++)
			{System.out.println("i " +i+  " Object " +sharedet.length);%>
			<tr>
			<%for(int j=0;j<6;j++){%>
			  <%System.out.println("j" +j+ "  Object " +sharedet.length); %>
			   <td><%=sharedet[i][j] %></td>
			   <%System.out.println("ShareDet===>"+sharedet[i][j]); %>
			<%}%>
			<%System.out.println("Hiii"); %>
			</tr>
			<%}
			System.out.println("Hello"); 
		
		}%>
		
</table>		
		<%System.out.println("Double"+double_maxloanamt+"Max"+maxloanins); %>
		<%double maxloanamt=0.0;%>
		<%maxloanamt=(category==0)?double_maxloanamt:maxloanins; %>
		
<tr>
   <td><bean:message key="label.maxloanamt"></bean:message>
   <html:text property="max_loan_amt" value="<%="Rs."+maxloanamt%>" styleClass="formTextField" size="8"></html:text></td>
</tr>


<%
	if(sharedet!=null)
	{ 
	 for(int i=1;i<sharedet.length;i++)
 	 {
		 System.out.println("m"); 
		System.out.println("LoanAvail====<"+amt);   
 	  amt = (Double.parseDouble(sharedet[i][3].toString()))+ Double.parseDouble(sharedet[i][4].toString());
  	  loan_amt+=Double.parseDouble(sharedet[i][4].toString());
  	  share_amt+= amt;
  	  sanc_amt+=Double.parseDouble(sharedet[i][2].toString());
      disb_amt+=Double.parseDouble(sharedet[i][3].toString()); 
     }
	 System.out.println("Share Amt"+share_amt);
    }
%> 
 
 
 <%double loan_avail; %>
 <%loan_avail = (category==0)?double_maxloanamt-loan_amt:maxloanins-loan_amt;%>
 <%System.out.println("LoanAvailed====="+loan_avail); %>
<tr>
   <td><bean:message key="label.loanavail"></bean:message>
   <%if(loan_avail!=0){ %>
   <html:text property="loan_avail" value="<%="Rs."+loan_avail%>" styleClass="formTextField" size="8"></html:text></td>
   <%} %>
</tr>

<tr>
   <td><bean:message key="label.totnoshares"></bean:message>
   <%if(sharedet[0][2].toString()!=null){ %>
   
   <html:text property="tot_no_shares" value="<%=sharedet[0][2].toString()%>" styleClass="formTextField" size="8"></html:text></td>
   <%} %>
</tr>

<%double val1= Double.parseDouble(sharedet[0][2].toString());%>
<%double val2= Double.parseDouble(sharedet[0][0].toString());%>
<%double val3 = val1 * val2; %>

<tr>
   <td><bean:message key="label.totshareval"></bean:message>
   <%if(val3!=0){%>
   <%System.out.println("Val3"+val3); %>
   <html:text property="tot_share_value" value="<%="Rs."+val3%>" styleClass="formTextField" size="8"></html:text></td>
   <%} %>
</tr>
</html:form>
</table>  
</body>
</html>