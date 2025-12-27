<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<body>

  <%System.out.println("HI FROM DEPOSIT DETAIL JSP"); %>
 
 
 
  <%! double Maturityamount=0.0,DepositAmmount=0.0;%>
 
  <% Maturityamount= (Double)request.getAttribute("Maturityamount");
  		DepositAmmount=(Double)request.getAttribute("DepositAmmount");
  %>
  
  <%System.out.println("Maturityamount=====>"+Maturityamount); %>
  
  <%double Pygmyamount=0.0; %>
  <% Pygmyamount= ((Double)request.getAttribute("Pygmyamount"));%>
  
  <% System.out.println("Pygmyamount=====>"+Pygmyamount);%>
  <%! double Intrestrate=0.0;%>
  
  <%Intrestrate=(Double)request.getAttribute("Intrestrate");%>
  <%System.out.println("Intrestrate=====>"+Intrestrate); %>
  
  
  <%! String  deposit_matdate=""; %>
  <%deposit_matdate=(String)request.getAttribute("deposit_matdate");%>
  <%System.out.println("deposit_matdate=====>"+deposit_matdate); %>
  
  
  <%! String  deposit_Depdate=""; %> 
  <%deposit_Depdate=(String)request.getAttribute("deposit_Depdate");%>
  <%System.out.println("deposit_Depdate=====>"+deposit_Depdate); %>
   
  <%! int AccountNumber=0; %>
  <%AccountNumber=(Integer)request.getAttribute("AccountNumber");%>
  <%System.out.println("AccountNumber=====>"+AccountNumber); %>
   

  <%! int deposit_days=0;%>
  <%deposit_days=(Integer)request.getAttribute("deposit_days");%>
  <%System.out.println("deposit_days=====>"+deposit_days); %>
   

<html:form action="/LoansOnDeposit/DepositDetail?pageId=6002">

<table cellspacing="5" style="border-bottom-color:black;border-bottom-style:;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">

  <tr><td><h1 align="center"><font size="3" style="font-family: serif">Deposit Detail</font></h1></td></tr>
    
      <tr>
         <td align="right">
         <font color="blue" style="font-family: serif" size="3"><bean:message key="label.mat_date"></bean:message></font> 
         <%if(deposit_matdate!=null){ %>
         <html:text property="txt_matdate" size="10" style="border:transparent;background-color:beige" value="<%=deposit_matdate%>" readonly="true"></html:text>
         <%}else{ %>
         <html:text property="txt_matdate" size="10" style="border:transparent;background-color:beige" readonly="true"></html:text>
         <%} %>
         </td>
      </tr>
      <tr>
         <td align="right">
         <font color="blue" style="font-family: serif" size="3"><bean:message key="lable.Depdate"></bean:message></font>
         <%if( deposit_Depdate!=null){ %>
         <html:text property="txt_depdate" size="10" style="border:transparent;background-color:beige" value="<%= deposit_Depdate %>" readonly="true"></html:text>
         <%}else{ %>
         <html:text property="txt_depdate" size="10" style="border:transparent;background-color:beige" readonly="true"></html:text>
         <%} %>
         
         </td>
      </tr>
      <tr>
         <td align="right">
         <font color="blue" style="font-family: serif" size="3"><bean:message key="lable.period"></bean:message></font>
         <%if(deposit_days!=0){ %>
         <html:text property="txt_period" size="10" style="border:transparent;background-color:beige" value="<%=""+deposit_days%>" readonly="true"></html:text>
         <%}else{ %>
         <html:text property="txt_period" size="10" style="border:transparent;background-color:beige" readonly="true"></html:text>
         <%} %>
         
         </td>
      </tr>
      <tr>
         <td align="right">
         <font color="blue" style="font-family: serif" size="3"><bean:message key="lable.DepIntRate"></bean:message></font>
         <%if(Intrestrate!=0){ %>
         <html:text property="txt_depintrate" size="10" style="border:transparent;background-color:beige" value="<%=""+Intrestrate%>" readonly="true"></html:text>
         <%}else{ %>
         <html:text property="txt_depintrate" size="10" style="border:transparent;background-color:beige" readonly="true"></html:text>
         <%} %>
         
         </td>
      </tr> 
      <tr>
         <td align="right">
         <font color="blue" style="font-family: serif" size="3"><bean:message key="label.dep_amt"></bean:message></font>
         <%if(DepositAmmount!=0){%>
         <html:text property="txt_amt" size="10" style="border:transparent;background-color:beige" value="<%=""+DepositAmmount%>" readonly="true"></html:text>
         <%}else if(Pygmyamount!=0){%>
         <html:text property="txt_amt" size="10" style="border:transparent;background-color:beige" value="<%=""+Pygmyamount%>" readonly="true"></html:text>
         <%}else{%>
        	 <html:text property="txt_amt" size="10" style="border:transparent;background-color:beige" readonly="true"></html:text>	 
        <%}%>
         
         </td>
      </tr>
      <tr>
         <td align="right">
         <font color="blue" style="font-family: serif" size="3"><bean:message key="label.Mat_amt"></bean:message></font>
         <%if(Maturityamount!=0){%>
         <html:text property="txt_matamount" size="10" style="border:transparent;background-color:beige" value="<%=""+Maturityamount%>" readonly="true"></html:text>
         <%}else{ %>
         <html:text property="txt_matamount" size="10" style="border:transparent;background-color:beige" readonly="true"></html:text>
         <%} %>
         
         </td>
      </tr>
            
</table>
</html:form>
</body>
</html>