<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body bgcolor="beige">
 <%! int Loanacnum; %>
 <% Loanacnum=(Integer)request.getAttribute("Loanacnum");%>
 
 <%! String ValidationMD,DateFun,vocher_num,Loanacc_closed,Acc_closed,DeleteSucess; %>
 
 <% DeleteSucess=(String)request.getAttribute("DeleteSucess");%>
 
 <% Acc_closed=(String)request.getAttribute("Acc_closed");%>
 
 <% ValidationMD=(String)request.getAttribute("ValidationMD");%>
 
 <% Loanacc_closed=(String)request.getAttribute("Loanacc_closed");%>
 
 <% vocher_num=(String)request.getAttribute("vocher_num");%>
 
 <% DateFun=(String)request.getAttribute("DateFun");%>
 <%System.out.println("ValidationMD========="+ValidationMD); %>
 
 <%! int recov_num; %>
 <%recov_num=(Integer)request.getAttribute("recov_num");%>
 
 <%! String AccountNotFound,NoRecords,Verification; %>
 <%AccountNotFound=(String)request.getAttribute("AccountNotFound");%>
 
 <%Verification=(String)request.getAttribute("Verification");%>
 
 <%NoRecords=(String)request.getAttribute("NoRecords");%>

 
 <html:form action="/LoansOnDeposit/LDsucess?pageId=6010"></html:form>
 
 <%System.out.println("*****Hi from LD Sucesss page*****"); %>
 
 
 <table>
       <tr>
          <td align="center">
          
             
             <%if(Loanacnum!=0){%>
             <FONT color="red" size="3" style="font-family: serif;font: bold"><bean:message key="label.loanaccno"></bean:message></FONT>
             <html:text property="txt_lnnum" size="4" value="<%=""+Loanacnum%>" style="border:transparent;background-color:beige"></html:text>
             <font color="red" size="3" style="font-family: serif;font: bold">generated sucessfully</font>
             <%}else if(ValidationMD!=null){%>
             <font color="red" size="3" style="font-family: serif;font: bold;">
             <bean:message key="lable.msg"></bean:message>
             </font>
             <%}%>
          </td>
          </tr>
          
          <tr>
             <td> 
                 <%if(recov_num!=0){%>
                 <FONT color="red" size="3" style="font-family: serif;font: bold"><bean:message key="lable.vocher"></bean:message></FONT>
                 <html:text property="txt_lnnum" value="<%=""+recov_num%>" style="border:transparent;background-color:beige"></html:text>
                 <FONT color="red" size="3" style="font-family: serif;font: bold"><bean:message key="lable.sam"></bean:message></FONT>
                 <%}else if(recov_num==10)
                 {%>
                 <FONT color="red" size="3" style="font-family: serif;font: bold"><bean:message key="lable.msg1"></bean:message></FONT>
                 <%}%>
             </td>
          </tr>
          <tr>
          		<td>
          			<%if(AccountNotFound!=null)
          			{%>
          			<FONT color="red" size="3" style="font-family: serif;font: bold"><bean:message key="lable.msg4"></bean:message></FONT>	
          			<%} %>
          		</td>
          </tr>
          
          <tr>
          		<td>
          			<%if(DateFun!=null)
          			{%>
          			<FONT color="red" size="3" style="font-family: serif;font: bold"><bean:message key="lable.msg2"></bean:message></FONT>	
          			<%} %>
          		</td>
          </tr>
         
           <tr>
          		<td>
          			<%if(NoRecords!=null)
          			{%>
          			<FONT color="red" size="3" style="font-family: serif;font: bold"><bean:message key="lable.msg3"></bean:message></FONT>	
          			<%} %>
          		</td>
          </tr>
          <tr>
          		<td>
          			<%if(Verification!=null)
          			{%>
          			<FONT color="red" size="3" style="font-family: serif;font: bold"><bean:message key="label.msg4"></bean:message></FONT>&nbsp;&nbsp;&nbsp;&nbsp;
          			<FONT color="red" size="3" style="font-family: serif;font: bold"></FONT>&nbsp;&nbsp;&nbsp;&nbsp;<%=""+Verification%>
          			<%} %>
          		</td>
          </tr>
          
          <tr>
          		<td>
          			<%if(vocher_num!=null)
          			{%>
          			<FONT color="red" size="3" style="font-family: serif;font: bold"></FONT>&nbsp;&nbsp;&nbsp;&nbsp;<%=""+vocher_num%>
          			<FONT color="red" size="3" style="font-family: serif;font: bold"><bean:message key="label.msg4"></bean:message></FONT>
          			<%} %>
          		</td>
          </tr>
          <tr>
          		<td>
          			<%if(Loanacc_closed!=null)
          			{%>
          			<FONT color="red" size="3" style="font-family: serif;font: bold"><bean:message key="label.msg6"></bean:message></FONT>
          			<%}else if(Acc_closed!=null){%>
          				<%if(Acc_closed.equalsIgnoreCase("Acc_closed")){ %>
          				<FONT color="red" size="3" style="font-family: serif;font: bold"><bean:message key="label.msg7"></bean:message></FONT>
          				<%}else if(Acc_closed.equalsIgnoreCase("Matdate")){%>
          				<FONT color="red" size="3" style="font-family: serif;font: bold"><bean:message key="label.msg8"></bean:message></FONT>
          			<%}else if(Acc_closed.equalsIgnoreCase("Elapsed")){%>
          				<FONT color="red" size="3" style="font-family: serif;font: bold"><bean:message key="label.msg9"></bean:message></FONT>
          			<%}%>
          			<%}%>
          		</td>
          </tr>
          <tr>
          		<td>
          			<%if(DeleteSucess!=null){%>
          				<%if(DeleteSucess.equalsIgnoreCase("DeleteSucess"))
          				{%>
          					<FONT color="red" size="3" style="font-family: serif;font: bold"><bean:message key="label.msg10"></bean:message></FONT>
          				<%}else {%>
          				
          					<FONT color="red" size="3" style="font-family: serif;font: bold"><bean:message key="label.msg11"></bean:message></FONT>
          				<%} %>
          		<%}%>
          		</td>
          </tr>
</table>
</body>
</html>