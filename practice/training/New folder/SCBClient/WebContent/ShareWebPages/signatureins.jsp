<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>Signature Instructions</title>
</head>

<body>


<html:form action="/Share/Allotment?pageId=4001">
<%System.out.println("At 16 in sign");%>
<table border="1" width="545" height="244">
	<tr>
		<td height="35" width="545" colspan="2" align="center"><b>
		<font size="4">Signature Instructions</font></b></td>
	</tr>
	<tr>
		<td height="39" width="247">cid:</td>
		<td height="115" width="282" rowspan="3" valign="top">Photo:</td>
	</tr>
	<tr>
		<td height="39" width="247">Account Type: </td>
	</tr>
	<tr>
		<td height="36" width="247">Account No.:</td>
	</tr>
	<tr>
		<td height="39" width="247">Name:</td>
		<td height="76" width="282" rowspan="2" valign="top">Signature: </td>
	</tr>
	<tr>
		<td height="39" width="247">Type of Operation: <input type="text" name="operation"></td>
	</tr>
</table>
<input type="submit" value="submit">
</html:form>
</body>

</html>