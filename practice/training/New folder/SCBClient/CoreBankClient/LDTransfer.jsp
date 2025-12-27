<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@page import="masterObject.general.ModuleObject"%>
<html>
<head>

<script type="text/javascript">
  		function setButtton(target)
  		{  
  			if(target=='verify'){
			document.forms[1].button.value=target;
			
			}
			else{
			document.forms[1].button.value=target;
			}
		}	
		function numbersOnly(eve){
         var cha = event.keyCode
				if (  ( cha  > 47 && cha < 58  ) ) {
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
         
        function fun_clear()
  {
  		
  		var ele=document.forms[0].elements;
  		for(var i=0;i<ele.length;i++)
  		{
  			if(ele[i].type=="text")
  			{
  				ele[i].value="";   
  			}
  		}
  }; 
        
</script>		


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<title>Insert title here</title>
</head>
<body>

<%ModuleObject[] PayAcNum; %>
<%PayAcNum=(ModuleObject[])request.getAttribute("PayAcNum");%>

<%! double cashtotalamt,trf_others,trf_extra_int; %>

<%! String PayAcNumabbr,Acc_name,cashintpaiduptodate,Button_value,sb_index,sb_abbr,displaymsg,buttonvalue; %>
<%sb_index=(String)request.getAttribute("sb_index");%>
<%sb_abbr=(String)request.getAttribute("sb_abbr");%>
<%PayAcNumabbr=(String)request.getAttribute("PayAcNumabbr");%>
<%Button_value=(String)request.getAttribute("Button_value");%>
<%Acc_name=(String)request.getAttribute("Acc_name");%>
<%! double Accbal,cashprinciple,cashint_rate,trf_int_amt; %>
<%Accbal=(Double)request.getAttribute("Accbal"); %>
<%cashprinciple=(Double)request.getAttribute("cashprinciple");%>
<%cashintpaiduptodate=(String)request.getAttribute("cashintpaiduptodate");%>
<%cashint_rate=(Double)request.getAttribute("cashintrate");%>
<%cashtotalamt=(Double)request.getAttribute("cashtotalamt");%>
<%displaymsg=(String)request.getAttribute("displaymsg"); %>
<%buttonvalue=(String)request.getAttribute("buttonvalue"); %>
  <%System.out.println("***************hiiiiiiiiiiiiiiiiiiii tran******************"); %>

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
<html:form action="/LDRecovery?pageId=6004">
<table class="txtTable">
 <td>
  <table class="txtTable">
	<tr>
	   <td align="right">
	    <bean:message key="label.voucherno"></bean:message></td>
	    <td><html:text property="txt_voucherno" size="10" disabled="true"></html:text>  
	   </td>
	</tr>
	<tr>
	   <%if(sb_index!=null && sb_abbr!=null){%>	
		<td align="right">
	    <bean:message key="lable.tranfrom"></bean:message></td>
	    <td><html:select property="txt_tranfrom" onchange="submit()">
	    <html:option value="<%=""+sb_index%>"><%=""+sb_abbr%></html:option>
	    </html:select></td>
	   <%}else {%>
	   <td align="right">
	    <bean:message key="lable.tranfrom"></bean:message></td>
	    <td><html:select property="txt_tranfrom" onchange="submit()">
	    <%if(PayAcNum!=null){%>
	    <%for(int i=0;i<PayAcNum.length;i++){ %>
	    <html:option value="<%=""+i%>"><%=""+PayAcNum[i].getModuleAbbrv()%></html:option>
	    <%}%>
	    <%}%>
	    </html:select>  
	    <%if(PayAcNumabbr!=null){%>
	    <html:text property="txt_ac" value="<%=PayAcNumabbr%>" size="10"></html:text>
	    <%}%>
	   </td>
	   
	  <%} %> 
	</tr>
	<tr>
	   <td align="right">
	    <bean:message key="label.acNum"></bean:message></td>
	    <td><html:text property="txt_tracNum" size="10" onchange="submit()" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'11')"></html:text>  
	   </td>
	</tr>
	<tr>
	   <td align="right">
	    <bean:message key="label.custname"></bean:message></td>
	    <%if(Acc_name!=null){ %>
	    <td><html:text property="txt_custname" size="10" value="<%=Acc_name%>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
	    <%} %>  
	   </td>
	</tr>
	<%System.out.println("Accbal========testinf================"+Accbal); %>
	<tr>
	   <td align="right">
	    <bean:message key="lable.bal"></bean:message></td>
	    <%if(Accbal!=0){ %>
	    <td><html:text property="txt_bal" size="10" value="<%=""+Accbal%>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text>
	    <%} %>  
	   </td>
	</tr>
	<%System.out.println("txt_entAmo========================"); %>
	<tr> 
	   <td align="right">
	    <bean:message key="lable.entAmo"></bean:message></td>
	    <td><html:text property="txt_entAmo" size="10" onchange="submit()" onkeypress=" return numbersOnlydot()" onkeyup="numberlimit(this,'9')"></html:text>  
	   </td>
	</tr>
	
	<html:hidden property="button" value="error"/>
	
	<tr>
		<% if(Button_value !=null){%>
		<td align="right"><html:submit onclick="setButtton('verify')" styleClass="ButtonBackgroundImage">Verify</html:submit>
	   <html:button property="clear" value="Clear" onclick="fun_clear()" styleClass="ButtonBackgroundImage">CLEAR</html:button></td>
	   <%}else{%>
	   <td align="right">
	   <%if(displaymsg!=null){ %>
	   <html:submit onclick="setButtton('verify')" disabled="true" styleClass="ButtonBackgroundImage">Verify</html:submit>
	   <html:submit onclick="setButtton('submit')" disabled="true" styleClass="ButtonBackgroundImage">SUBMIT</html:submit>
	   <%}else if(buttonvalue!=null){ %>
	   <html:submit onclick="setButtton('verify')" styleClass="ButtonBackgroundImage">Verify</html:submit>
	   <html:submit onclick="setButtton('submit')" disabled="true" styleClass="ButtonBackgroundImage">SUBMIT</html:submit>
	   <%}else{ %>
	   <html:submit onclick="setButtton('verify')" styleClass="ButtonBackgroundImage">Verify</html:submit>
	   <html:submit onclick="setButtton('submit')" styleClass="ButtonBackgroundImage">SUBMIT</html:submit>
	   <%} %>
	   <html:button property="clear" value="Clear" onclick="fun_clear()" styleClass="ButtonBackgroundImage">CLEAR</html:button></td>
	   <%} %>
	</tr>
</table>
       </td>
       

	   <td>
	   
	   <tr> 
	      <td align="right"><font color="red" style="font-family: serif;font-style:bold"><bean:message key="lable.amtadjust"></bean:message></font></td>
	   </tr>
	   
	   <tr>
	      <td align="right"><bean:message key="label.principal"></bean:message></td>
	      
	      
	      <td><html:text property="txt_prin"  size="10" onchange="submit()" value="<%=""+cashprinciple%>" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	      
	   </tr>
	   
	   <%trf_int_amt=(Double)request.getAttribute("trfintamt");%>
	   <tr>
	   
	      <td align="right"><bean:message key="label.interest"></bean:message></td>
	      
	      <td><html:text property="txt_interest" value="<%=""+trf_int_amt%>" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
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
	      <td align="right"><bean:message key="label.paid"></bean:message></td>
	      <%if(cashintpaiduptodate!=null){%>
	      <td><html:text property="txt_interest" value="<%=cashintpaiduptodate%>" size="10" style="border:transparent;background-color:beige;color:blue" readonly="true"></html:text></td>
	      <%}%>	   
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
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>