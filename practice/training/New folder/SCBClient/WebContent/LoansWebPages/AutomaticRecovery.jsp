<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
 <%@page import="java.util.Map"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
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
<h2 class="h2"><center>Loan Automatic Recovery</center></h2>
<% String msg=(String)request.getAttribute("msg");
 if(msg !=null){
 %>
 <br><br><font size="2" color="red"><%= msg %></font>
 <%} %>
</head>
<body>
<font>Click the button to process Auto Recovery</font>
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
<html:form action="/Loans/AutomaticRecovery?pageidentity.pageId=5046">
<table>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
	<tr><html:submit onclick="submit()"  styleClass="ButtonBackgroundImage" ></html:submit></tr>
	<%}else{ %>
	<tr><html:submit onclick="submit()" disabled="true" styleClass="ButtonBackgroundImage" ></html:submit></tr>
	<%} %>
</table>


</html:form>
</body>
</html>