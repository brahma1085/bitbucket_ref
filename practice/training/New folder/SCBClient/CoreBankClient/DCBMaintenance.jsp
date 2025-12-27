<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>    
  <%@page import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript">
	function fun_delete(target)
	{
		alert(target);
		document.forms[0].button_value.value=target;		
	
	};
	function fun_valid()
	{
		
		if(document.forms[0].delete_sucess.value!=null)
		{
			if(document.forms[0].delete_sucess.value=="DELETED")
			{
				alert("DELETED SUCESSFULLY");
			}
		}
	
	}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />
<h2 class="h2"><center>DCB Records Deletion</center></h2> 

</head>

<%! String DCBMaint[]; %>
<%DCBMaint=(String[])request.getAttribute("DCBMaint");
System.out.println("Records in DCBMaintain---->"+DCBMaint);
%>
<%!
String access;
String  accesspageId;
 Map user_role;
%>
<%
  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");
System.out.println("11111111"+user_role);
System.out.println("222222222"+accesspageId);
if(user_role!=null&&accesspageId!=null){
System.out.println("11111111"+access);
			if(user_role.get(accesspageId)!=null){
			System.out.println("222222222"+access);
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}
			}
// Note:access=1111==>read,insert,delete,update
%>

<body >
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
	<html:form action="/Loans/DCBMaintenance?pageidentity.pageId=5098">
<table>
	<tr>
		<td align="right"><FONT style="font: bold; "><bean:message key="label.records"/></FONT></td>
		
	</tr>
	
	<tr></tr>
	
	
	<%if(DCBMaint!=null){ %>
	
	
			<tr>
				<td><%="Month&Year"%></td>
				<td><html:select property="DCBMainValue">
				<%for(int i=0;i<DCBMaint.length;i++)
					{%>
					<html:option value="<%=DCBMaint[i].split(" ")[0]%>"></html:option>
					<%}%>
				</html:select></td>
			</tr>
		
	<%}%>
		<html:hidden property="button_value" value="error"/>
		<html:hidden property="delete_sucess" styleId="delete_sucess"/>
		
		
	<tr>
	<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
		<td><html:submit value="Delete" onclick="fun_delete('Delete')" onchange="fun_valid()"></html:submit></td>
		<%}else{ %>
		<td><html:submit property="delete" value="Delete" disabled="true" ></html:submit></td>
		<%} %>
	</tr>
		
</table>		
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>	
</body>
</html>