<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>

<html>
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
      <center>Term Deposit Access Rights</center></h2>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body class="Mainbody">
<html:form action="/TDAccessRightsAction?pageId=13039">
<table border="1" width="732" height="374" bordercolor="#0000FF">
	<tr>
		<td height="25" width="333">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		Term Deposit Forms</td>
		<td height="25" width="100">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		Read</td>
		<td height="25" width="88">&nbsp;&nbsp;&nbsp;&nbsp; Insert</td>
		<td height="25" width="90">&nbsp;&nbsp;&nbsp;&nbsp; Delete</td>
		<td height="25" width="87">&nbsp;&nbsp;&nbsp; Update</td>
	</tr>
	<tr>
		<td height="23" width="333">Deposit Account Opening</td>
		<td height="23" width="100"><html:checkbox property="chk11"></html:checkbox></td>
		<td height="23" width="88"><html:checkbox property="chk21"></html:checkbox></td>
		<td height="23" width="90"><html:checkbox property="chk31"></html:checkbox></td>
		<td height="23" width="87"><html:checkbox property="chk41"></html:checkbox></td>
	</tr>
	<tr>
		<td height="25" width="333">Amendments</td>
		<td height="25" width="100"><html:checkbox property="chk12"></html:checkbox></td>
		<td height="25" width="88"><html:checkbox property="chk22"></html:checkbox></td>
		<td height="25" width="90"><html:checkbox property="chk32"></html:checkbox></td>
		<td height="25" width="87"><html:checkbox property="chk42"></html:checkbox></td>
	</tr>
	<tr>
		<td height="30" width="333">Deposit View Log</td>
		<td height="30" width="100"><html:checkbox property="chk13"></html:checkbox></td>
		<td height="30" width="88"><html:checkbox property="chk23"></html:checkbox></td>
		<td height="30" width="90"><html:checkbox property="chk33"></html:checkbox></td>
		<td height="30" width="87"><html:checkbox property="chk43"></html:checkbox></td>
	</tr>
	<tr>
		<td height="28" width="333" >Query On Receipt 
		Number</td>
		<td height="28" width="100"><html:checkbox property="chk14"></html:checkbox></td>
		<td height="28" width="88"><html:checkbox property="chk24"></html:checkbox></td>
		<td height="28" width="90"><html:checkbox property="chk34"></html:checkbox></td>
		<td height="28" width="87"><html:checkbox property="chk44"></html:checkbox></td>
	</tr>
	<tr>
		<td height="23" width="333">Receipt Printing</td>
		<td height="23" width="100"><html:checkbox property="chk15"></html:checkbox></td>
		<td height="23" width="88"><html:checkbox property="chk25"></html:checkbox></td>
		<td height="23" width="90"><html:checkbox property="chk35"></html:checkbox></td>
		<td height="23" width="87"><html:checkbox property="chk45"></html:checkbox></td>
	</tr>
	<tr>
		<td height="23" width="333">Receipt Updation</td>
		<td height="23" width="100"><html:checkbox property="chk16"></html:checkbox></td>
		<td height="23" width="88"><html:checkbox property="chk26"></html:checkbox></td>
		<td height="23" width="90"><html:checkbox property="chk36"></html:checkbox></td>
		<td height="23" width="87"><html:checkbox property="chk46"></html:checkbox></td>
	</tr>
	<tr>
		<td height="27" width="333">Interest Payment</td>
		<td height="27" width="100"><html:checkbox property="chk17"></html:checkbox></td>
		<td height="27" width="88"><html:checkbox property="chk27"></html:checkbox></td>
		<td height="27" width="90"><html:checkbox property="chk37"></html:checkbox></td>
		<td height="27" width="87"><html:checkbox property="chk47"></html:checkbox></td>
	</tr>
	<tr>
		<td height="26" width="333">Quarterly Interest</td>
		<td height="26" width="100"><html:checkbox property="chk18"></html:checkbox></td>
		<td height="26" width="88"><html:checkbox property="chk28"></html:checkbox></td>
		<td height="26" width="90"><html:checkbox property="chk38"></html:checkbox></td>
		<td height="26" width="87"><html:checkbox property="chk48"></html:checkbox></td>
	</tr>
	<tr>
		<td height="30" width="333">TD Closure</td>
		<td height="30" width="100"><html:checkbox property="chk19"></html:checkbox></td>
		<td height="30" width="88"><html:checkbox property="chk29"></html:checkbox></td>
		<td height="30" width="90"><html:checkbox property="chk39"></html:checkbox></td>
		<td height="30" width="87"><html:checkbox property="chk49"></html:checkbox></td>	
  </tr>
	<tr>
		<td height="25" width="333">Account Renewal</td>
		<td height="25" width="100"><html:checkbox property="chk110"></html:checkbox></td>
		<td height="25" width="88"><html:checkbox property="chk210"></html:checkbox></td>
		<td height="25" width="90"><html:checkbox property="chk310"></html:checkbox></td>
		<td height="25" width="87"><html:checkbox property="chk410"></html:checkbox></td>
	</tr>
	<tr>
		<td height="24" width="333">Auto Renewal</td>
		<td height="24" width="100"><html:checkbox property="chk111"></html:checkbox></td>		
		<td height="24" width="88"><html:checkbox property="chk211"></html:checkbox></td>
		<td height="24" width="90"><html:checkbox property="chk311"></html:checkbox></td>
		<td height="24" width="87"><html:checkbox property="chk411"></html:checkbox></td>
	</tr>
	
	<tr>
		<td height="27" width="333">Voucher Payment</td>
		<td height="27" width="100"><html:checkbox property="chk112"></html:checkbox></td>
		<td height="27" width="88"><html:checkbox property="chk212"></html:checkbox></td>
		<td height="27" width="90"><html:checkbox property="chk312"></html:checkbox></td>
		<td height="27" width="87"><html:checkbox property="chk412"></html:checkbox></td>
	</tr>
	
</table>
<table align="center">
<tr><td>&nbsp</td></tr>
<tr><td>&nbsp</td></tr>

<tr></tr>
<tr >
<td>
<html:button property="subbut" value=" Submit "   styleClass="ButtonBackgroundImage"></html:button>
</td>
</tr>
</table>



</html:form>
</body>
</html>