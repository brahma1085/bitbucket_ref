<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<script type="text/javascript">
function numbersOnlydot(eve){
         var cha = event.keyCode
				if (  ( cha  > 47 && cha < 58  ) || cha==46 ) {
				return true ;
                
            	}
            else{
              	alert("Alphabets are not allowed!!!!!!!Please enter numbers only ");
              	return false ;
            }
        }; 
        function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("only "+size+" digits allowed")
         		txt.value="";
         		return false;
          	}
         } 
</script>
<body>
 
 <%! String Recoveryabbr,LDCashAbbr,LDcashacno,cashintpaiduptodate; %>
 
 <%Recoveryabbr=(String)request.getAttribute("Recoveryabbr");%>

 <%LDCashAbbr=(String)request.getAttribute("LDCashAbbr");%>
 
 <%LDcashacno=(String)request.getAttribute("LDcashacno");%>
 
 <%cashintpaiduptodate=(String)request.getAttribute("cashintpaiduptodate");%>
 
 
 <%! double cashprinciple,cashint_rate,cashtotalamt,cashintamt; %>
 
 
 <%System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiii 123*** from cash"); %>
 
 <%cashprinciple=(Double)request.getAttribute("cashprinciple");%>

 <%System.out.println("***************1************"); %>
 
 <%cashint_rate=(Double)request.getAttribute("cashintrate");%>
 
 <%System.out.println("***************2************"); %>
 
 <%cashtotalamt=(Double)request.getAttribute("cashtotalamt");%>
 
 <%System.out.println("***************3************"); %>

<html:form action="/LDRecovery?pageId=6004"> 


<%System.out.println("***********hiiiiiiiiii from LDCash**********");%>

<table>
    <tr>
    	<td align="right"><h1><font color="red" size="2" style="font-family: serif;font-style: bold"><bean:message key="lable.headRec"></bean:message></font></h1></td>
    </tr>

	<tr>
	   <td align="right">
	       <bean:message key="lable.acctye"></bean:message> </td>
	        <td><html:select property="txt_acctype" disabled="true">
	       <%if(LDCashAbbr!=null)
	       {%>
	      <html:option value="<%=LDCashAbbr%>"><%=LDCashAbbr%></html:option>
	      <%}%>
	      </html:select>
	      <%if(Recoveryabbr!=null){ %>
	      <html:text property="txt_acdesc" value="<%=Recoveryabbr%>" size="10" readonly="true"></html:text>
	      <%}%> 
	   </td>
	</tr>
	<tr>   
	   <td align="right">
	       <bean:message key="label.acNum"></bean:message></td> 
	       <%if(LDcashacno!=null){ %>
	      <td><html:text property="txt_acNum" value="<%=LDcashacno%>" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	      <%}%> 
	   
	</tr>
	<tr>   
	   <td align="right">
	       <bean:message key="label.custname"></bean:message> </td>
	      <td><html:text property="txt_name"  size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
	   </td>
	</tr>
		<tr>   
	   <td align="right">
	       <bean:message key="lable.ammount"></bean:message> </td>
	       <td><html:text property="txt_ammount" size="10" onchange="submit()" onkeypress=" return numbersOnlydot()" onkeyup="numberlimit(this,'9')"></html:text></td>
	   </tr>	   
	   <tr> 
	      <td align="right"><font color="red" style="font-family: serif;font-style:bold"><bean:message key="lable.amtadjust"></bean:message></font></td>
	   </tr>
	  
	   
	   <tr>
	      <td align="right"><bean:message key="label.principal"></bean:message></td>
	      <%if(cashprinciple!=0){ %>
	      
	      <td><html:text property="txt_prin"  size="10" onchange="submit()" value="<%=""+cashprinciple%>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	      <%} %>
	   </tr>
	   
	   <tr>
	      <td align="right"><bean:message key="label.interest"></bean:message></td>
	      <td><html:text property="txt_interest" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	   </tr>
	   
	   <tr>
	      <td align="right"><bean:message key="lable.paid"></bean:message></td>
	      <%if(cashintpaiduptodate!=null){ %>
	      <td><html:text property="txt_paid" value="<%=""+cashintpaiduptodate%>" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	      <%} %>
	   </tr>
	       
	   <tr>
	      <td align="right"><bean:message key="lable.intrate"></bean:message></td>
	      <%if(cashint_rate!=0){ %>
	      <td><html:text property="txt_intrate" value="<%=""+cashint_rate%>" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	      <%} %>
	   </tr>
	   
	   <tr>
	      <td align="right"><bean:message key="lable.totalamm"></bean:message></td>
	      <%if(cashtotalamt!=0){ %>
	      <td><html:text property="txt_totalamm" value="<%=""+cashtotalamt%>" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	      <%} %>
	   </tr>
</table>
</html:form>
</body>
</html>