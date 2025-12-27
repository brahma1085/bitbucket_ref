<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form>

<%String cols[]=(String[])request.getAttribute("sbcols");
Object colData[][]=(Object[][])request.getAttribute("columnData");
int k,j;
%>

<%if(colData!=null){
	if(colData.length!=0){
%>
<div id="pocon" style="display:block;overflow:scroll;width:750px;height:200px">
<table border="1" style="background-color:#CDCEAE">
<tr bgcolor="#0099CC">
<% for( k=0;k<cols.length;k++){%>
<td style="background-color:#CDCEAE">
<core:if test="<%=cols[k]!=null %>">
<font style="font: bold;"> <%=cols[k]%></font>
</core:if>
</td>
<%}%>
</tr>
<%if(colData!=null){
for(j=0;j<colData.length;j++){%>
<tr>
	<%for( k=0;k<cols.length;k++){
	System.out.println("column data is not null");
%> 
<td style="background-color:#ECEBD2">
<core:if test="<%=colData[j][k]!=null %>">
<%=colData[j][k].toString() %>
</core:if>
</td>
<%}%>
</tr>
<%}}
else {out.println("data is null");}
%>


</table>
</div>
<%}
	else{%>
		<font color="red"> Records Not Found</font>
		
<%	}	
} else{%>
		<font color="red"> Records Not Found</font>
		
<%	}%>
</form>
</body>
</html>