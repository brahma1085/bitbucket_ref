<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.VehicleObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
</style>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" /><h2 class="h2"><center>Vehicle Details</center></h2>
  
</head>
<body>
<%! VehicleObject vecObj; %>
<% vecObj=(VehicleObject)request.getAttribute("VECHOBJ"); %>
<%System.out.println("This is from Vehicle Form"); 
String panelName; 
panelName=(String)request.getAttribute("panelName");%>
<html:form action="/Loans/LoanAmmendments?pageId=5051">
<table cellspacing="5" style="border-bottom-color:black;border-bottom-style:groove;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">
<tr>
  <td>
    <h1 style="font:small-caps;font-style:normal;font-size:small;"><center>Vehicle Details</center></h1>
  </td>
</tr>
 <tr>
 
 </tr>
   <tr>
       <td><font style="font-style:normal;font-size:12px"><bean:message key="label.make&type"></bean:message></font>
       	<%if(vecObj!=null){%>
       		<html:text property="maketype" size="10" value="<%=""+vecObj.getVehicleMake() %>" disabled="true"></html:text>
       	<%}else{ %>
			<html:text property="maketype" size="10" value=""></html:text>
		<%} %>       	
		</td>
  </tr>
  <tr>
       <td>
       		<font style="font-style:normal;font-size:12px"><bean:message key="label.costVehicle"></bean:message></font>
       
       	<%if(vecObj!=null){%>
       		<html:text property="cost" size="10" value="<%=""+vecObj.getVehicleCost() %>" disabled="true"></html:text>
       	<%}else{ %>
       		<html:text property="cost" size="10"></html:text>
       	<%} %>
       		</td>
  </tr>
  <tr><td><font style="font-style:normal;font-size:12px"><bean:message key="label.nameDealer"></bean:message></font>
       
       		<%if(vecObj!=null){%>
       			<html:text property="dealername" size="10" value="<%= ""+vecObj.getAddressDealer() %>" disabled="true"></html:text>
       		<%}else{ %>
       			<html:text property="dealername" size="10"></html:text>
       		<%} %>
       	</td>
  </tr>
  <tr>
       <td>
       	<font style="font-style:normal;font-size:12px"><bean:message key="label.licenseno"></bean:message></font>
  
              		<%if(vecObj!=null){%>
       			<html:text property="licenseno" size="5" value="<%=""+vecObj.getLicenceNo() %>" disabled="true"></html:text>
				<html:text property="validity" size="5" value="<%=""+vecObj.getLicenceValidity() %>" disabled="true"></html:text>       		
       		<%}else{%>
       			<html:text property="licenseno" size="5"></html:text>	
       			<html:text property="validity" size="5"></html:text></td>
 			<%} %>
 			
 			 </tr>		
  <tr>
       <td>
       		<font style="font-style:normal;font-size:12px"><bean:message key="label.licenseno"></bean:message></font>
           	<%if(vecObj!=null){%>
       		   	<html:text property="permitno" size="5" value="<%=""+vecObj.getPermitNo() %>" disabled="true"></html:text>
       			<html:text property="validity" size="5" value="<%=""+vecObj.getPermitValidity()%>" disabled="true"></html:text>
       		<%}else{ %>
       			<html:text property="permitno" size="5"></html:text>
       			<html:text property="validity" size="5"></html:text>
       		<%} %>
       		</td>
  </tr>
  <tr>
       <td ><font style="font-style:normal;font-size:12px"><bean:message key="label.vehicletype"></bean:message></font>
       <html:select property="vehicletype" >
       		<html:option value="0">Light Motor Vehicle</html:option>
       		<html:option value="1">Heavy Motor Vehicle</html:option>
       		</html:select></td>
  </tr>
  <tr>
       <td><font style="font-style:normal;font-size:12px"><bean:message key="label.vehiclefor"></bean:message></font>
       <html:select property="vehiclefor" >
       		<html:option value="0">Own use</html:option>
       		<html:option value="1">Hire</html:option>
       		</html:select></td>
  </tr>
  <tr>
       <td>
       	<font style="font-style:normal;font-size:12px"><bean:message key="label.vehicleuse"></bean:message></font>
       <%if(vecObj!=null){ 
       		System.out.println("Area is ========"+vecObj.getArea());%>
       		<html:text property="vehiclearea" size="10" value="<%=""+vecObj.getArea()%>" disabled="true"></html:text>
       <%}else {%>
       		<html:text property="vehiclearea" size="10"></html:text>
       	<%} %>
       </td>
       		
  </tr>
  <tr>
       <td><font style="font-style:normal;font-size:12px"><bean:message key="label.vehiclepark"></bean:message></font>
       <%if(vecObj!=null){ %>
       		<html:text property="vehicleparked" size="10" value="<%=""+vecObj.getAddressParking() %>" disabled="true"></html:text>
       	<%}else{ %>
       		<html:text property="vehicleparked" size="10"></html:text>
       	<%} %>
       	</td>
       	
  </tr>
  <tr>
       <td><font style="font-style:normal;font-size:12px"><bean:message key="label.othervehicle"></bean:message></font>
       
       <%if(vecObj!=null){ %>
       	<html:text property="othervehicle" size="10" value="<%=""+vecObj.getOtherDet() %>" disabled="true"></html:text>
       <%}else{ %>
       	<html:text property="othervehicle" size="10"></html:text>
       <%} %>
       </td>
  </tr>
  <tr>
       <td>
       <%if(vecObj!=null){ %>
       <html:submit styleClass="ButtonBackgroundImage" disabled="true">Submit</html:submit>
		<%}else{ %>       
       <td><input type="submit" value="UpdateVehicle" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=UpdateVehicle'" />
       <%} %>
       <html:submit styleClass="ButtonBackgroundImage" disabled="true">Clear</html:submit>
       
  </tr>  
  </table>   
  </html:form>
  </body>
</html>