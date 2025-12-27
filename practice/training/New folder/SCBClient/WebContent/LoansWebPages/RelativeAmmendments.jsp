<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
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
<title></title>

</head>
<body>
<html:form action="/Loans/LoanApplicationDE?pageId=5001">
<table cellspacing="5" style="border-bottom-color:black;border-bottom-style:groove;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">
<tr>
  <td>
    <h2 class="h2"><center>Relative Details</center></h2>
  </td>
</tr>
<tr>
<tr>
	<td align="left">
		<font color="blue">Relation Details</font>
	</td>
</tr>
<tr>
	<td>
		Name<html:text property="relName" styleId="relName"></html:text> 
			
	</td>
	<td>
		DOB &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:text property="relDob" styleId="relDob"></html:text>
	</td>
</tr>
<tr>
	<td>
		Type &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:select property="relTor" styleId="relTor">
		<html:option value="Father">Father</html:option>
		<html:option value="Mother">Mother</html:option>
		<html:option value="Self">Self</html:option>
		<html:option value="Son">Son</html:option>
		<html:option value="Daughter">Daughter</html:option>
		</html:select>
		
	</td>
	<td>
		Sex &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:select property="relTos" styleId="relTos">
		<html:option value="Male">Male</html:option>
		<html:option value="Female">Female</html:option>
		</html:select>
		
	</td>
</tr>
<tr>
	<td>Martial &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:select property="relTom" styleId="relTom">
	<html:option value="UnMarried">UnMarried</html:option>
	<html:option value="Maried">Married</html:option>
	</html:select>
	 
	
	</td>
	<td>Status &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:select property="relTostatus" styleId="relTos">
	<html:option value="Live">Live</html:option>
	<html:option value="Alive">Alive</html:option>
	</html:select>
		
	</td>
</tr>
<tr>
	<td align="left"><font color="blue">
		Indent Details</font>
	</td>
</tr>
<tr>
	<td>
		Name &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:text property="indName" styleId="indName"></html:text>
		
	</td>
	<td>
		DOB &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:text property="infDob" styleId="infDob"></html:text>
	</td>
</tr>
<tr>
	<td>Sex &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:select property="indTos" styleId="indTos">
	<html:option value="Male">Male</html:option>
	<html:option value="Female">Female</html:option>
	</html:select>
	
	</td>
	<td>Martial &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:select property="indTom" styleId="indTom">
	<html:option value="UnMarried">UnMarried</html:option>
	<html:option value="Maried">Maried</html:option>
	</html:select>
	 
		
	</td>
</tr>
<tr>
	<td>Status &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:select property="indTostatus" styleId="indTostatus">
	<html:option value="Live">Live</html:option>
	<html:option value="Alive">Alive</html:option>
	</html:select>
	
	</td>
</tr>
<tr>
	<td align="left">
		<font color="blue">Dependent Details</font>
	</td>
</tr>
<tr>
	<td>
		Name &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:text property="depName" styleId="depName"></html:text>
		
	</td>
	<td>
		DOB &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:text property="depDob" styleId="depDob"></html:text>
	</td>
</tr>
<tr>
	<td>Sex &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:select property="depTos" styleId="depTos">
	<html:option value="Male">Male</html:option>
	<html:option value="Female">Female</html:option>
	</html:select>
	</td>
	<td>
		Relation With &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:select property="depTor" styleId="depTor">
		<html:option value="Father">Father</html:option>
		<html:option value="Mother">Mother</html:option>
		<html:option value="Self">Self</html:option>
		<html:option value="Son">Son</html:option>
		<html:option value="Daughter">Daughter</html:option>
	</html:select>
		
		
		
	</td>
</tr>
<tr>
	
	<td>
	<td>
		<input type="submit" value="UpdateRelative" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=UpdateRelative'" />
	</td>                                             
	
	
	
</tr>
</table>
</html:form>
</body>

</html>