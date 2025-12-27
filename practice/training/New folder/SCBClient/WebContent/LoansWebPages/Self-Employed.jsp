<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<html:form action="/LoansWebPages/Self-Employed?pageidentity.pageId=5006">
<table>
   <tr>
      <bean:message key="label.empmtnature"></bean:message> 
      <html:text property="employmtnature"></html:text>
   </tr>
   <tr>
      <bean:message key="label.address"></bean:message> 
      <html:text property="address"></html:text>
   </tr>
   <tr>
    <td><html:submit>Submit</html:submit>
       <html:submit>Clear</html:submit>
       <html:submit>Update</html:submit></td>  
</tr>
</table>
</html:form>
</body>
</html>