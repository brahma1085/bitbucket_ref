<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>
<head>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
</style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
      <h2 class="h2">
      <center>Customer Access Rights</center></h2>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body class="Mainbody">
<html:form action="/customerAccessRightsAction?pageId=130404">
<table border="1" width="732" height="241" bordercolor="#0000FF">
	<tr>
		<td height="35" width="333">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		Customer Forms</td>
		<td height="35" width="100">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		Read</td>
		<td height="35" width="88">&nbsp;&nbsp;&nbsp;&nbsp; Insert</td>
		<td height="35" width="90">&nbsp;&nbsp;&nbsp;&nbsp; Delete</td>
		<td height="35" width="87">&nbsp;&nbsp;&nbsp; Update</td>
	</tr>
	<tr>
		<td height="30" width="333">New Customer</td>
		<td height="30" width="100"><html:checkbox property="chk11"></html:checkbox></td>
		<td height="30" width="88"><html:checkbox property="chk21"></html:checkbox></td>
		<td height="30" width="90"><html:checkbox property="chk31"></html:checkbox></td>
		<td height="30" width="87"><html:checkbox property="chk41"></html:checkbox></td>
	</tr>
	<tr>
		<td height="28" width="333">Verification</td>
		<td height="28" width="100"><html:checkbox property="chk12"></html:checkbox></td>
		<td height="28" width="88"><html:checkbox property="chk22"></html:checkbox></td>
		<td height="28" width="90"><html:checkbox property="chk32"></html:checkbox></td>
		<td height="28" width="87"><html:checkbox property="chk42"></html:checkbox></td>
	</tr>
	<tr>
		<td height="37" width="333">Ammendments</td>
		<td height="37" width="100"><html:checkbox property="chk13"></html:checkbox></td>
		<td height="37" width="88"><html:checkbox property="chk23"></html:checkbox></td>
		<td height="37" width="90"><html:checkbox property="chk33"></html:checkbox></td>
		<td height="37" width="87"><html:checkbox property="chk43"></html:checkbox></td>
	</tr>
	<tr>
		<td height="31" width="333" >Customization</td>
		<td height="31" width="100"><html:checkbox property="chk14"></html:checkbox></td>
		<td height="31" width="88"><html:checkbox property="chk24"></html:checkbox></td>
		<td height="31" width="90"><html:checkbox property="chk34"></html:checkbox></td>
		<td height="31" width="87"><html:checkbox property="chk44"></html:checkbox></td>
	</tr>
	<tr>
		<td height="30" width="333">Customer View Log</td>
		<td height="30" width="100"><html:checkbox property="chk15"></html:checkbox></td>
		<td height="30" width="88"><html:checkbox property="chk25"></html:checkbox></td>
		<td height="30" width="90"><html:checkbox property="chk35"></html:checkbox></td>
		<td height="30" width="87"><html:checkbox property="chk45"></html:checkbox></td>
	</tr>
	<tr>
		<td height="32" width="333">Query On Customer</td>
		<td height="32" width="100"><html:checkbox property="chk16"></html:checkbox></td>
		<td height="32" width="88"><html:checkbox property="chk26"></html:checkbox></td>
		<td height="32" width="90"><html:checkbox property="chk36"></html:checkbox></td>
		<td height="32" width="87"><html:checkbox property="chk46"></html:checkbox></td>
	</tr>
	
	
</table>
</html:form>
</body>
</html>