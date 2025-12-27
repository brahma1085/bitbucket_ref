<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %> 
<%@page import="masterObject.general.GoldObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<%GoldObject goldObject = (GoldObject)request.getAttribute("GoldDet"); %>

<table cellspacing="5" style="border-bottom-color:black;border-bottom-style:groove;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">
	
	<%if(goldObject!=null){ %>
	<tr>
		<td>Sl. No<input type="text" name="slno" ></td>
	</tr>
	<tr>
		<td>
			Description: <input type="text" name="desc">
		</td>
	</tr>
	<tr>
		<td>
			Gr. Weight: <input type="text" name="grWeight">
		</td>
	</tr>
	<tr>
		<td>
			Net. Weight: <input type="text" name="netWeight">
		</td>
	</tr>
	<tr>
		<td>
			Rate / Gms: <input type="text" name="rate">
		</td>
	</tr>
	<tr>
		<td>
			Net Rate: <input type="text" name="netRate">
		</td>
	</tr>
	<tr>
	</tr>
	<tr>
		<td>
			Appriser's Code: <input type="text" name="appCode">
		</td>
	</tr>
	<%}else{ %>
	<tr>
		<td>Sl. No<input type="text" name="slno"></td>
	</tr>
	<tr>
		<td>
			Description: <input type="text" name="desc">
		</td>
	</tr>
	<tr>
		<td>
			Gr. Weight: <input type="text" name="grWeight">
		</td>
	</tr>
	<tr>
		<td>
			Net. Weight: <input type="text" name="netWeight">
		</td>
	</tr>
	<tr>
		<td>
			Rate / Gms: <input type="text" name="rate">
		</td>
	</tr>
	<tr>
		<td>
			Net Rate: <input type="text" name="netRate">
		</td>
	</tr>
	<tr>
	</tr>
	<tr>
		<td>
			Appriser's Code: <input type="text" name="appCode">
		</td>
	</tr>
	
	
	<%} %>
	<tr>
		<td>
			<html:submit property="sub" value="Submit" styleClass="ButtonBackgroundImage"></html:submit>
		</td>
		<td>
			<input type="button" class="ButtonBackgroundImage" value="Clear">
		</td>
	</tr>
</table>
</body>
</html>