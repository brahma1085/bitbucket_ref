<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.DirectorMasterObject"%>
<%@page import="com.scb.designPatterns.ShareDelegate"%>
<%@page import="masterObject.general.ModuleObject"%>
<html>
<head>

<script type="text/javascript">

function getTodayDate()
        {
        
        	var date = new Date();
        	
        	var day = date.getDate();
        	var month = date.getMonth()+1; 
        	var year = date.getYear();
        	
		
			

        	if(day <= 9)
        	{
        		day = "0"+day;
        	}  
        	if (month <= 9)
        	{
				month = "0"+month;        	
        	}
        	document.forms[0].appndate.value=day+"/"+month+"/"+year;
        	alert(document.forms[0].appndate.value);
        };

function disEnable(){
    
    var mode=document.getElementById("paymtmode").value;
    alert(mode);
    if(mode=="Cash"){
    alert("cash");
    document.forms[0].paymentactype.disabled = true;
  }
  else{
  	document.forms[0].paymentactype.disabled = false;
    }
}
function EnableDir(){

    
    document.forms[0].dirdetails.disabled = false;
    
  }
  else{
  	
  	document.forms[0].dirdetails.disabled = true;
  
  }


}
</script>

<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
    </style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<% System.out.println("******************Application1.jsp**************************** "); %>
<%String ApplicationDate; 
ShareDelegate sharedel=null;%>
<%DirectorMasterObject DirRelations[];  %>
<%ApplicationDate=(String)request.getAttribute("ApplicationDate");
  System.out.println("ApplicationDate=====>"+ApplicationDate);
%>

</head>

<body onload="getTodayDate()">

<html:form action="/Loans/Sanctioning?pageidentity.pageId=5040">

<%   System.out.println("********* Application Sanction*************"); %>

<table cellspacing="5" style="border-bottom-color:black;border-bottom-style:groove;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">

<tr>
  <td>
    <h2 class="h2"><center>Application</center></h2>
  </td>
</tr>
<tr>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.applnserial"></bean:message></font>
    <html:text property="appln_no" size="5"></html:text></td>
    
</tr><%System.out.println("ApplicationDate=====>"+ApplicationDate);%>
<tr>    
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.appndate"></bean:message></font>
    <html:text property="appndate" size="10" value="<%=ApplicationDate %>"></html:text></td>
    
</tr>
<tr>  
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.reqamount"></bean:message></font>
    <html:text property="reqamount" size="10"></html:text></td>
    
</tr>

<tr>    
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.reltodir"></bean:message></font>
    <html:checkbox property="relativetodir"></html:checkbox></td>
    
    
</tr>
<% String[] dirrelations=(String[])request.getAttribute("Dirrelations"); %>
<tr>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.dirrelations"></bean:message></font>
        <html:select property="dirrelations">
        
        <%if(dirrelations!= null){
        	for(int i=0;i<dirrelations.length;i++){
        	%>
            <html:option value="<%=""+dirrelations[i]%>"></html:option>
                        <% }}%></html:select></td>
 </tr>

<% String dirdetails[][]=(String[][])request.getAttribute("DirDetails"); %>
<tr>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.dirdetails"></bean:message></font>
        <html:select property="dirdetails">
        <%if(dirdetails!= null){
        	for(int i=0;i<dirdetails.length;i++){
        	%>
            <html:option value="<%=""+dirdetails[i][0]%>"><%=""+dirdetails[i][2]%></html:option>
                        <% }}%></html:select></td>
 </tr>



<tr>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.paymentmode1"></bean:message></font>
        <html:select property="paymtmode">
        <html:option value="Cash">Cash</html:option>
        <html:option value="Transfer">Transfer</html:option>
        <html:option value="PayOrder">PayOrder</html:option></html:select></td>
 </tr>


 
 <tr>
 			<td><bean:message key="label.paymentactype"></bean:message>
            <html:select  property="payactype" styleClass="formTextFieldWithoutTransparent">
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
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.payaccno"></bean:message></font>
    <html:text property="payaccno" size="10"></html:text></td>
    
</tr>         
<tr>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.interesttype"></bean:message></font>
        <html:select property="interesttype">
        <html:option value="0">EMI</html:option>
        <html:option value="1">Reducing Balance</html:option></html:select></td>
 </tr>
<tr>    
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.interestcalctype"></bean:message></font>
        <html:select property="interestcalctype">
        <html:option value="0">Fixed</html:option>
        <html:option value="1">Floating</html:option>
        </html:select></td>
    
</tr>
<tr>                                                                                           
    <td><input type="submit" value="SaveApplication" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=SaveApplication'"/>
       <html:reset  property="clear" value="Clear" styleClass="ButtonBackgroundImage"></html:reset>
       <html:submit  value="Update" styleClass="ButtonBackgroundImage"></html:submit></td>  
</tr>
</table>
</html:form>
</body>
</html>