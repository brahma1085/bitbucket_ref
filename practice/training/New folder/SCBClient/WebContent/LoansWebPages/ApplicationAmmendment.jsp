<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.DirectorMasterObject"%>
<%@page import="com.scb.designPatterns.ShareDelegate"%>
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.loans.LoanMasterObject"%>
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

<% System.out.println("******************ApplicationAmmendments.jsp**************************** "); %>

	<%	String ApplicationDate; 
		LoanMasterObject lMasterObject;
		ShareDelegate sharedel=null;
	%>
	<%! ModuleObject module_object[]=null; %>
	<% DirectorMasterObject DirRelations[];  %>
	<%  ApplicationDate=(String)request.getAttribute("ApplicationDate");
		lMasterObject = (LoanMasterObject)request.getAttribute("LoanDetails");
		module_object=(ModuleObject[])request.getAttribute("pay_actype");
	%>

</head>

<body onload="getTodayDate()">

<html:form action="/Loans/LoanAmmendments?pageId=5099">

<%   System.out.println("Hello from application"); %>

<table cellspacing="5" style="border-bottom-color:black;border-bottom-style:groove;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">

<tr>
  <td>
    <center><h2 class="h2">Application</h2></center>
  </td>
</tr>
<tr>
<%   System.out.println("Hello from **********application"); %>
   <%if(lMasterObject!=null){ %>
   <td>
   <%   System.out.println("Hello from 11111111111 application"); %>
    <font style="font-style:normal;font-size:12px"><bean:message key="label.applnserial"></bean:message></font>
    <html:text property="appln_no" value="<%=""+lMasterObject.getApplicationSrlNo()%>" size="5"></html:text>
    </td>
   
   <%} else{ %>
    <td>
    <%   System.out.println("Hello from 222222222222 application"); %>
    <font style="font-style:normal;font-size:12px"><bean:message key="label.applnserial"></bean:message></font>
    <html:text property="appln_no" size="5"></html:text>
    </td>
    <%} %>
</tr>
<tr>  
	<%if(lMasterObject!=null){ %> 
	<%   System.out.println("Hello from 333333333 application"); %> 
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.appndate"></bean:message></font>
    <html:text property="appndate" size="10" value="<%=""+lMasterObject.getApplicationDate()%>"></html:text></td>
    <%}else { %>
    <%   System.out.println("Hello from *4444444444 application"); %>
     <td><font style="font-style:normal;font-size:12px"><bean:message key="label.appndate"></bean:message></font>
    <html:text property="appndate" size="10"></html:text></td>
    <%} %>
</tr>
<tr>  
	<%if(lMasterObject!=null){ %>
	<%   System.out.println("Hello from *555555555 application"); %>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.reqamount"></bean:message></font>
    <html:text property="reqamount" value="<%=""+lMasterObject.getRequiredAmount()%>" size="10"></html:text></td>
    <%}else{ %>
    <%   System.out.println("Hello from 6666666 application"); %>
     <td><font style="font-style:normal;font-size:12px"><bean:message key="label.reqamount"></bean:message></font>
    <html:text property="reqamount" size="10"></html:text></td>
    <%} %>
</tr>

<tr>  
<%if(lMasterObject!=null){ %>  
<%   System.out.println("Hello from 7777777 application"); %>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.reltodir"></bean:message></font>
    <html:checkbox property="relativetodir"  value="true"></html:checkbox></td>
<%}else{ %>
<%   System.out.println("Hello from 8888888888**********application"); %>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.reltodir"></bean:message></font>
    <html:checkbox property="relativetodir" value="false"></html:checkbox></td>
<%} %>    
</tr>
<% String[] dirrelations=(String[])request.getAttribute("Dirrelations"); %>
<tr>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.dirrelations"></bean:message></font>
        <html:select property="dirrelations">
        
        <%if(dirrelations!= null){
          System.out.println("Hello from 000**********application"); 
        	for(int i=0;i<dirrelations.length;i++){
        	%>
        	<%   System.out.println("Hello from 1212121221211**********application"); %>
            <html:option value="<%=""+dirrelations[i]%>"></html:option>
                        <% }}%></html:select></td>
 </tr>

<% String dirdetails[][]=(String[][])request.getAttribute("DirDetails"); %>
<tr>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.dirdetails"></bean:message></font>
        <html:select property="dirdetails">
        <%if(dirdetails!= null){
         System.out.println("Hello from 45454545454**********application"); 
        	for(int i=0;i<dirdetails.length;i++){
        	%>
        	<%   System.out.println("Hello from 7878787878**********application"); %>
            <html:option value="<%=""+dirdetails[i][0]%>"><%=""+dirdetails[i][2]%></html:option>
        <% }}%>
        </html:select></td>
 </tr>



<tr>
<%if(lMasterObject!=null){ %>
<%   System.out.println("Hello from 565656565**********application"); %>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.paymentmode1"></bean:message></font>
        <html:select property="paymtmode">
        <%if(lMasterObject.getPayMode().equalsIgnoreCase("C")){ %>
        	<html:option value="C">Cash</html:option>
        	<%   System.out.println("Hello from 32232323**********application"); %>
        <%}else if(lMasterObject.getPayMode().equalsIgnoreCase("T")){ %>
        	<html:option value="T">Transfer</html:option>
        	<%   System.out.println("Hello from 96969699**********application"); %>
        <%}else{ %>
        <%   System.out.println("Hello from 98898988989**********application"); %>
        	<html:option value="PO">PayOrder</html:option>
        <%} %>
        </html:select>
    </td>
  <%}else{ %>
  <%   System.out.println("Hello from 8686868+686**********application"); %>
   <td><font style="font-style:normal;font-size:12px"><bean:message key="label.paymentmode1"></bean:message></font>
        <html:select property="paymtmode">
        <html:option value="Cash">Cash</html:option>
        <html:option value="Transfer">Transfer</html:option>
        <html:option value="PayOrder">PayOrder</html:option></html:select>
    </td>
  
  <%} %>  
 </tr>


 
 <tr>
 		
 			<td><bean:message key="label.paymentactype"></bean:message>
 			
			<html:select  property="payactype" styleClass="formTextFieldWithoutTransparent">
           
            <% 
            	for(int i=0;i<module_object.length;i++)
			    {
					System.out.println("module_object"+ module_object[i].getModuleAbbrv());
			 %>	   			    			    	 
			    	<html:option value="<%=""+module_object[i].getModuleCode()%>"><%=""+module_object[i].getModuleAbbrv()%></html:option>
			 <%	} %>
			        	 
			</html:select>
			
		  </td>
            
</tr>   
<tr>  
<%if(lMasterObject!=null){ %>
<%   System.out.println("Hello from 466464646464**********application"); %>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.payaccno"></bean:message></font>
    <html:text property="payaccno" size="10" value="<%=""+lMasterObject.getPaymentAccno() %>"></html:text></td>
<%}else{ %> 
<%   System.out.println("Hello from 191991919191**********application"); %>
 <td><font style="font-style:normal;font-size:12px"><bean:message key="label.payaccno"></bean:message></font>
    <html:text property="payaccno" size="10"></html:text></td>

<%} %>   
</tr>         
<tr>
<%if(lMasterObject!=null){ %>
<%   System.out.println("Hello from 49494949**********application"); %>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.interesttype"></bean:message></font>
        <html:select property="interesttype">
        <%if(lMasterObject.getInterestType()==0){ %>
        <%   System.out.println("Hello from 595959595**********application"); %>
        <html:option value="0">EMI</html:option>
        <%}else{ %>
        <%   System.out.println("Hello from 4949494949**********application"); %>
        	<html:option value="1">Reducing Balance</html:option>
    	<% } %>
    	</html:select>
    </td>
 <% }else{ %>
 <%   System.out.println("Hello from 101010110**********application"); %>
 	<td><font style="font-style:normal;font-size:12px"><bean:message key="label.interesttype"></bean:message></font>
        <html:select property="interesttype">
        <html:option value="0">EMI</html:option>
        <html:option value="1">Reducing Balance</html:option></html:select>
    </td>
 <%} %>   
 </tr>
<tr>   
	<%if(lMasterObject!=null){ %> 
	<%   System.out.println("Hello from 9292929292**********application"); %>
    	<td><font style="font-style:normal;font-size:12px"><bean:message key="label.interestcalctype"></bean:message></font>
        <html:select property="interestcalctype">
        <%if(lMasterObject.getInterestRateType()==0){ %>
        <%   System.out.println("32472746274274application"); %>
        	<html:option value="0">Fixed</html:option>
        <%}else{ %>
        <%   System.out.println("Hello from 84627846274*********application"); %>
        	<html:option value="1">Floating</html:option>
        <%} %>
        </html:select>
        </td>
    <%} else{ %>
    <%   System.out.println("Hello from 884848474674**********application"); %>
    	<td><font style="font-style:normal;font-size:12px"><bean:message key="label.interestcalctype"></bean:message></font>
        <html:select property="interestcalctype">
        <html:option value="0">Fixed</html:option>
        <html:option value="1">Floating</html:option>
        </html:select></td>
    <%} %>
</tr>
<tr>                                                                                           
    <td><input type="submit" value="UpdateApplication" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=UpdateApplication'"/>
       <html:reset  property="clear" value="Clear" styleClass="ButtonBackgroundImage"></html:reset>
       
</tr>
</table>
</html:form>
</body>
</html>


