<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.ShareMasterObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Signature</title>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <h2>
      <center>Signature Details</center></h2>
      <hr style="color: green">
  
</head>
<body class="Mainbody">
<%!
    String[] sign_det;
%>
<%
sign_det=(String[])request.getAttribute("SignDetails");

%>
<table class="txtTable" border="0"  style="height: 500px;width: 300px;border-color: black" >
  <tr>
   <td>
      <bean:message key="label.cust"></bean:message>
       <%if(sign_det!=null){ %>
      <input type="text" name="cid" value="<%=""+sign_det[0]%>"  class="formTextFieldWithoutTransparent">
       <%}else{ %>
       <input type="text" name="cid" class="formTextFieldWithoutTransparent">
       <%
       }
       %>
   </td>
  </tr>
  
  <tr>
   <td>
     <bean:message key="label.sh_type"></bean:message>
     <%if(sign_det!=null){ %>
      <input type="text" name="shtype" value="<%=""+sign_det[1]%>" class="formTextFieldWithoutTransparent">
       <%}else{ %>
       <input type="text" name="shtype" class="formTextFieldWithoutTransparent">
       <%
       }
       %>
   </td>
  </tr>
  
  <tr>
   <td>
     <bean:message key="label.shareno"></bean:message>
     <%if(sign_det!=null){ %>
      <input type="text" name="shno" value="<%=""+sign_det[2]%>" class="formTextFieldWithoutTransparent">
       <%}else{ %>
       <input type="text" name="shno" class="formTextFieldWithoutTransparent">
       <%
       }
       %>
   </td>
  </tr>
  
  <tr>
   <td>
     <bean:message key="label.name"></bean:message>
     <%if(sign_det!=null){ %>
      <input type="text" name="name" value="<%=""+sign_det[3]%>" class="formTextFieldWithoutTransparent">
       <%}else{ %>
       <input type="text" name="name" class="formTextFieldWithoutTransparent">
       <%
       }
       %>
   </td>
  </tr>
  
  <tr>
   <td>
     <bean:message key="label.TypeOfOperation"></bean:message>
     
      <input type="text" name="operation" class="formTextFieldWithoutTransparent">
     
   </td>
  </tr>
  
</table>
</body>
</html>