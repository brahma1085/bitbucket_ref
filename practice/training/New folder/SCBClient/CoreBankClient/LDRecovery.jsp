<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Map"%>
<%@taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="com.scb.ld.forms.LDCashForm"%>
<%@page import="masterObject.loans.LoanTransactionObject"%>
<html>
<head>

	<script type="text/javascript">
	 function Only_Numbers()
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
	<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
<h2 class="h2"><center>Recovery</center></h2> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body class="Mainbody">

<%boolean flag=false; %>

<%!ModuleObject[] LoanACType; 
ModuleObject[] mo;
%>
<%mo=(ModuleObject[])request.getAttribute("ac_type_ver");
if(mo!=null)
System.out.println("HI module nmo in jsp--------------------->"+mo[0].getModuleAbbrv());
%>

<%String loan_acc_type,loan_acc_num; %>

<%loan_acc_type=(String)request.getAttribute("loan_acc_type");%>

<%loan_acc_num=(String)request.getAttribute("loan_acc_num");%>

 <%System.out.println("--------------------------->>>>>."+loan_acc_type);%>
 
 <%System.out.println("--------------------------->>>>>."+loan_acc_type);%>

<% LoanACType=(ModuleObject[])request.getAttribute("LoanACType"); %>

<%String action=null,Page_value=null,Recoveryabbr=null,DepositAccNum=null,Depdate=null,matdate=null,sancdate=null,sancpur=null,status=null,intpaiduptodate=null,int_freq=null;%>

<%double depamt=0.0,sancamt=0.0,matammount=0.0; %>

<%int reptnum=0;%>
<%System.out.println("*********Hi from LDRecovery***********"); %>

<%!
String access;
String  accesspageId;
Map user_role;

%>

<%

  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");

if(user_role!=null&&accesspageId!=null){
			if(user_role.get(accesspageId)!=null){
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}

			
			}

// Note:access=1111==>read,insert,delete,update
%>
  
 <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>  
<%Page_value=(String)request.getAttribute("Page_value");
if(Page_value!=null)
	{
		if(Page_value.equalsIgnoreCase("1"))
		{
			action="/LDRecovery?pageId=6004&value=1";
			flag=true;
		}
		else
		{
			action="/LDRecovery?pageId=6004&value=2";
			flag=false;
		}
			
	}

%>

<%System.out.println("*********0st***********"+Page_value+"action=====>"+action); %>


<%depamt=(Double)request.getAttribute("depamt");%>

<%System.out.println("*********1st***********"); %>

<%sancamt=(Double)request.getAttribute("sancamt");%>

<%System.out.println("*********2st***********"); %>

<%matammount=(Double)request.getAttribute("matammount");%>

<%System.out.println("*********3st***********"); %>

<%Recoveryabbr=(String)request.getAttribute("Recoveryabbr");%>

<%System.out.println("*********4st***********"); %>

<%DepositAccNum=(String)request.getAttribute("DepositAccNum");%>

<%System.out.println("*********5st***********"); %>

<%Depdate=(String)request.getAttribute("Depdate");%>

<%System.out.println("*********6st***********"); %>

<%matdate=(String)request.getAttribute("matdate");%>

<%System.out.println("*********7st***********"); %>

<%sancdate=(String)request.getAttribute("sancdate");%>

<%System.out.println("*********8st***********"); %>

<%sancpur=(String)request.getAttribute("sancpur");%>

<%System.out.println("*********9st***********"); %>

<%status=(String)request.getAttribute("status");%>

<%System.out.println("*********10st***********"); %>

<%reptnum=(Integer)request.getAttribute("reptnum");%>

<%System.out.println("*********11st***********"); %>

<%intpaiduptodate=(String)request.getAttribute("intpaiduptodate");%>

<%System.out.println("*********12st***********"); %>

<%int_freq=(String)request.getAttribute("int_freq");%>

<%System.out.println("*********13st***********"); %>

<%String displaymsg=(String)request.getAttribute("displaymsg");
System.out.println("Display msg in jsp---------->"+displaymsg);
%>

<table class="txtTable">
<td>
<html:form action="<%=action%>">

<%System.out.println("action path======>"+action); %>

<%if(displaymsg!=null){%>
<font color="red" ><%=displaymsg%></font>
<%} %>
<br><br>
<table class="txtTable">
	
	<tr>
	   <td align="right">
	    <bean:message key="label.voucherno"></bean:message></td>  
	    <td><html:text property="txt_voucherno" size="10" onblur="submit()"   onkeypress="return Only_Numbers()" onkeyup="numberlimit(this,'9')" styleClass="formTextFieldWithoutTransparent"></html:text>  
	   </td>
	</tr>

      <tr>   
      	<%if(loan_acc_type!=null && loan_acc_num!=null)
      	{ %>	
           <td align="right">
            <bean:message key="label.combo_loan"></bean:message></td>
            <td><html:select property="combo_loantype" onchange="submit()">
			 <html:option value="<%=loan_acc_num%>"><%=loan_acc_type%></html:option>
			 </html:select></td>
			 
		<%}else if(mo!=null){ 
			System.out.println("*********INSIDE MO NOt Null***********"+mo[0].getModuleAbbrv());
		%>
			  <td align="right">
            <bean:message key="label.combo_loan"></bean:message></td>
            
            <td><html:select property="combo_loantype" value="<%=""+mo[0].getModuleAbbrv()%>">
            <html:option value="BL">BL</html:option>
            <html:option value="CL">CL</html:option>
            <html:option value="FL">FL</html:option>
            <html:option value="RL">RL</html:option>
            <html:option value="BVL">BVL</html:option>
            <html:option value="ZL">ZL</html:option>
            </html:select>
            </td>
    				 
         <%}else{ %>
     		<td align="right">
            <bean:message key="label.combo_loan"></bean:message></td>
            <td><html:select property="combo_loantype" onchange="submit()">
          <html:option value="Select">Select</html:option>
			 <%if(LoanACType!=null){%>
			 <%for(int i=0;i<LoanACType.length;i++){%>
			 <html:option value="<%=""+i%>"><%=LoanACType[i].getModuleAbbrv()%></html:option>
			 <%}%>
			 <%}%>            
            </html:select>
             </td>
             <td>
         <%if(Recoveryabbr!=null){ %>
         <html:text property="txt_loandesc" value="<%=Recoveryabbr%>" size="10" styleClass="formTextFieldWithoutTransparent"></html:text>
         <%}else { %>
         <html:text property="txt_loandesc" size="10" styleClass="formTextFieldWithoutTransparent"></html:text>
         <%}%> </td>
         
         <%} %>
      </tr>
   
      <tr>
         <td align="right">
         	<bean:message key="lable.accno"></bean:message></td>
            <td><html:text property="txt_accno" size="10" onchange="submit()" onkeypress="return Only_Numbers()" onkeyup="numberlimit(this,'11')"></html:text></td>
         
      </tr>
     
      <tr>   
         <td align="right">
         	<bean:message key="label.sancamt"></bean:message></td>
         	<%if(sancamt!=0){ %>
            <td><html:text property="txt_sancamt" value="<%= ""+sancamt %>" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
            <%} %>
         </td>
      </tr>
       
      <tr>   
         <td align="right">
         	<bean:message key="label.sancdate"></bean:message></td>
         	<%if(sancdate!=null){ %>
            <td><html:text property="txt_sancdate" value="<%=""+sancdate%>" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
            <%} %>
         </td>
      </tr>
    
      <tr>   
         <td align="right">
         	<bean:message key="lable.sancpur"></bean:message></td>
         	<%if(sancpur!=null){ %>
            <td><html:text property="txt_sancpur" value="<%=sancpur%>" size="15" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
            <%} %>
         </td>
      </tr>
       
      <tr>   
         <td align="right">
         	<bean:message key="lable.accstatus"></bean:message></td>
         	<%if(status!=null){%>
            <td><html:text property="txt_accstatus" value="<%= status %>" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
            <%} %>
         </td>
      </tr>
       
      <tr>   
         <td align="right">
         	<bean:message key="lable.depacnum"></bean:message></td>
         	<%if(DepositAccNum!=null){ %>
            <td><html:text property="txt_depacnum" size="30" style="border:transparent;background-color:beige;color:red" readonly="true" value="<%=DepositAccNum%>"></html:text>
            <%} %>
         </td>
       </tr>
        
       <tr>  
         <td align="right">
         	<bean:message key="label.dep_date"></bean:message></td>
         	<%if(Depdate!=null){ %>
           <td><html:text property="txt_dep_date" value="<%=Depdate%>" size="10" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
         </td>
         <%}%>
       </tr>
       
       <tr>  
         <td align="right">
         	<bean:message key="label.mat_date"></bean:message></td>
         	<%if(matdate!=null){%>
            <td><html:text property="txt_mat_date" value="<%=matdate%>"   size="10" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text></td>
            <%}%>
       </tr>
       
       <tr>  
         <td align="right">
         	<bean:message key="label.dep_amt"></bean:message></td>
         	<%if(depamt!=0){ %>
            <td><html:text property="txt_dep_amt" value="<%=""+depamt%>"   size="10" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
            <%} %>
         </td>
       </tr>
        
       <tr>  
         <td align="right">
         	<bean:message key="label.Mat_amt"></bean:message></td>
         	<%if(matammount!=0){ %>
            <td><html:text property="txt_Mat_amt" size="10" value="<%=""+matammount%>" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
            <%}%>
         </td>
       </tr>
        
       <tr>  
         <td align="right">
         	<bean:message key="label.receipt_no"></bean:message></td>
         	<%if(reptnum!=0){ %>
            <td><html:text property="txt_receipt_no" value="<%=""+reptnum%>" size="10" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
            <%}%>
         </td>
       </tr>
        
       <tr>  
         <td align="right">
         	<bean:message key="label.Int_freq"></bean:message></td>
         	<%if(int_freq!=null){ %>
            <td><html:text property="txt_Int_freq" value="<%=int_freq%>" size="10" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
            <%}%>
         </td>
       </tr>
       <tr>  
         <td align="right">
         	<bean:message key="label.int_upto_date"></bean:message></td>
         	<%if(intpaiduptodate!=null){ %>
            <td><html:text property="txt_int_upto_date" value="<%=intpaiduptodate%>" size="10" style="border:transparent;background-color:beige;color:red" readonly="true"></html:text>
            <%} %>
         </td>
       </tr>
        
       <tr> 
         <!--<td align="right">
         	<bean:message key="lable.c/t"></bean:message></td>
            <td><html:select property="txt_cash">
            <html:option value="cash">Cash</html:option>
            <html:option value="transfer">Transfer</html:option>
            </html:select>
         </td> 
                                     
        --></tr>   
        <html:hidden property="defaultTab" value="Personal"></html:hidden> 
        <html:hidden property="defaultTab" value="Status"></html:hidden> 
        <html:hidden property="defaultTab" value="Cash"></html:hidden> 
        <html:hidden property="defaultTab" value="Transfer"></html:hidden> 
       
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