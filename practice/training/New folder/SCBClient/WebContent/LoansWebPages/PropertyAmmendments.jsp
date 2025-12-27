<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.general.PropertyObject"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css" media="all">
            @import url("<%=request.getContextPath()%>/css/alternative.css");
            @import url("<%=request.getContextPath()%>/css/maven-theme.css");
            @import url("<%=request.getContextPath()%>/css/site.css");
            @import url("<%=request.getContextPath()%>/css/screen.css");
    </style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
<title>Insert title here</title>
</head>
<body>

<%PropertyObject object = (PropertyObject)request.getAttribute("PROPOBJ"); %>



<html:form action="/Loans/LoanAmmendments?pageId=5051">
<table cellspacing="5" style="border-bottom-color:black;border-bottom-style:groove;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">
<tr>
  <td>
    <h2 class="h2"><center>Property Details</center></h2>
  </td>
</tr>
<tr>
	<%if(object!=null){ %>
    <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.propertyno"></bean:message></font></td>
    <td><html:text property="propertyno" value="<%=""+object.getPropertyNo()%>"></html:text></td>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.address"></bean:message></font></td>
    <td><html:text property="addr" value="<%=""+object.getAddress() %>"></html:text></td>
    
    <%}else{ %>
     <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.propertyno"></bean:message></font></td>
    <td><html:text property="propertyno"></html:text></td>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.address"></bean:message></font></td>
    <td><html:text property="addr"></html:text></td>
    
    <%} %>
    
</tr>
   
<tr>      
 <td align="left"><font style="font-style:normal;font-size:12px">
    <bean:message key="label.measurements"></bean:message></font>
 </td>   
</tr>

<tr>   

	<%if(object!=null){ %>
	<td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.EWbyfeets"></bean:message></font></td>
    <td><html:text property="ewbyfeets" value="<%=""+object.getMeasurementEW()%>"></html:text></td>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.NSbyfeets"></bean:message></font></td>
    <td><html:text property="nsbyfeets" value="<%=""+object.getMeasurementNS()%>"></html:text></td>
 	<%}else{ %>
 	<td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.EWbyfeets"></bean:message></font></td>
    <td><html:text property="ewbyfeets"></html:text></td>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.NSbyfeets"></bean:message></font></td>
    <td><html:text property="nsbyfeets"></html:text></td>
 	<%} %>
    
</tr>

<tr>    
	<%if(object!=null){ %>
    <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.eastby"></bean:message></font></td>
    <td><html:text property="eastby" value="<%=""+object.getEastBy()%>"></html:text></td>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.westby"></bean:message></font></td>
    <td><html:text property="westby" value="<%=""+object.getWestBy()%>"></html:text></td>
    <%}else{ %>
    <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.eastby"></bean:message></font></td>
    <td><html:text property="eastby"></html:text></td>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.westby"></bean:message></font></td>
    <td><html:text property="westby"></html:text></td>
    <%} %>
</tr>

<tr> 
  <%if(object!=null){ %>   
    <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.northby"></bean:message></font></td>
    <td><html:text property="northby" value="<%=""+object.getNorthBy()%>"></html:text></td>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.southby"></bean:message></font></td>
    <td><html:text property="southby" value="<%=""+object.getSouthBy()%>"></html:text></td>
    <%}else{ %>
     <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.northby"></bean:message></font></td>
    <td><html:text property="northby"></html:text></td>
    <td><font style="font-style:normal;font-size:12px"><bean:message key="label.southby"></bean:message></font></td>
    <td><html:text property="southby"></html:text></td>
    <%} %>
</tr>

<tr> 
	<%if(object!=null){ %>   
    <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.marketvalue"></bean:message></font></td>
    <td><html:text property="value" value="<%=""+object.getPropertyValue()%>"></html:text></td>
    <%}else{ %>
     <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.marketvalue"></bean:message></font></td>
    <td><html:text property="value"></html:text></td>
    <%} %>
</tr>

<tr> 
	<%if(object!=null){ %>   
    <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.propertyacquired"></bean:message></font></td>
    <td><html:select property="propertyacquiredby">
        <html:option value="<%=""+object.getPropertyAqdBy()%>"></html:option>
     </html:select></td>
     <%}else{ %>
      <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.propertyacquired"></bean:message></font></td>
    	<td><html:select property="propertyacquiredby">
        <html:option value="Ancestoral Property">Ancestoral Property</html:option>
        <html:option value="Partitioned Property">Partitioned Property</html:option>
        <html:option value="Self Acquired">Self Acquired</html:option>
        <html:option value="Joint Family">Joint Family</html:option>
     </html:select></td>
     <%} %>
</tr>

<tr>   
<%if(object!=null){ %> 
    <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.nature"></bean:message></font></td>
    <td><html:select property="nature">
        <html:option value="<%=""+object.getPropertyNature()%>"></html:option>
        </html:select></td>
  <%}else{ %>
  		 <td align="left"><font style="font-style:normal;font-size:12px"><bean:message key="label.nature"></bean:message></font></td>
    <td><html:select property="nature">
        <html:option value="Site">Site</html:option>
        <html:option value="Site with Building">Site with Building</html:option>
        <html:option value="Residential">Residential</html:option>
        <html:option value="Commercial">Commercial</html:option>
        <html:option value="Cornersite">Cornersite</html:option>
        <html:option value="Partly residential & Partly commercial">Partly residential & Partly commercial</html:option></html:select></td>
   <%} %>     
</tr>

<tr>
<td><input type="submit" value="UpdateProperty" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=UpdateProperty'"/></td>
<td><html:submit>Clear</html:submit></td>
     
</tr>
</table>
</html:form>
</body>
</html>