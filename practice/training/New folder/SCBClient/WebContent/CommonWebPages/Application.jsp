<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table>
<tr>
    <td><bean:message key="label.applnserial"></bean:message></td>
    <td><html:text property=""></html:text></td>
    <td><bean:message key="label.appndate"></bean:message></td>
    <td><html:text property=""></html:text></td>
    <td><bean:message key="label.reqamount"></bean:message></td>
    <td><html:text property=""></html:text></td>
    <td><bean:message key="label.paymtmode"></bean:message></td>
    <td><html:text property=""></html:text></td>
    <td><bean:message key="label.interesttype"></bean:message></td>
    <td><html:select property=""></html:select></td>
    <td><bean:message key="label.interestcalctype"></bean:message></td>
    <td><html:select property=""></html:select></td>
    <td><html:submit>Submit</html:submit>
       <html:submit>Clear</html:submit>
       <html:submit>Modify</html:submit></td>
</tr>
</table>
</body>
</html>