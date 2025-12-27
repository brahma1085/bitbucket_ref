<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@page import="masterObject.general.ModuleObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
      function set(){
        
        alert(document.forms[1].forward.value);
       if(document.forms[1].forward.value="Add")
       {
         document.getElementById("blok").style.display='block'
         return false;
        }
        
      };
</script>
</head>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
</style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="Images/DMTStyle.css" type="text/css" media="print" />



<body>

<html:form action="/Loans/LoanApplicationDE?pageidentity.pageId=5001">
<%!ModuleObject[] shobj; %>
<% shobj = (ModuleObject[])request.getAttribute("ShareModule"); %>
<% System.out.print("-----inside the CoBorrowers Page-----");  %>
<table width="300" height="300" style="border-bottom-color:black;border-bottom-style:solid;border-bottom-style:solid;border-left-color:black;border-left-style:solid;border-right-style:solid;border-right-color:black;border-top-color:black;border-top-style:solid;">
<tr>
  <td><center>
    <h1 style="font:small-caps;font-style:normal;font-size:small;">CoBorrower</h1></center>
  </td>
</tr>
  <html:hidden property="forward" value="add"></html:hidden>
  
 
  
 <tr>
 	<td>
 		<html:submit onclick="return set()" value="Add" styleClass="ButtonBackGroundImage"></html:submit>
 		<html:submit value="Delete" styleClass="ButtonBackGroundImage"></html:submit>
 		<html:submit value="Delete All" styleClass="ButtonBackGroundImage"></html:submit>
 		<html:submit value="Done" styleClass="ButtonBackGroundImage"></html:submit>
 	</td>
 </tr>
 
  <tr>
    <td id="blok">
       <html:select property="coshtype">
       			 
       			 <core:forEach var="shType" varStatus="count"  items="${requestScope.ShareModule}" step="1">
      			 	 <html:option value="${count.index}"><core:out value="${shType.moduleAbbrv}"></core:out></html:option>
      			 	 ${count} 
      			 </core:forEach>
      			  </html:select>
       <bean:message key="label.coshno"></bean:message> 
       <html:text property="coshno" onchange="submit()"></html:text>
    </td>
  </tr>
  
</table>  
</html:form>   
</body>
</html>