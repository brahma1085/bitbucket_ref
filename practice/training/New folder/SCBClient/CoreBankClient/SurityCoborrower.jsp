<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@page import="java.util.ArrayList"%>
<%@page import="masterObject.loans.SurityCoBorrowerObject"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


  
   
<html:form action="/Loans/SurityCoBorrower?pageId=5022">   
<table>

 <%
 SurityCoBorrowerObject[]  borrowerObjects = (SurityCoBorrowerObject[])request.getAttribute("SurityDetails");
	
   %>
		<tr><th><h5>A/C Type </h5></th><th><h5>A/C No</h5></th><th><h5>Type</h5></th><th><h5>Moduleabbr</h5></th><th><h5>Cid</h5></th></tr>
	
<% if(borrowerObjects!=null){%>
<%for(int i=0;i<borrowerObjects.length;i++){ %>
      <tr>	
		<td>
		<core:out value="<%=borrowerObjects[i].getAc_type()%>" ></core:out>
		</td>
		<td>
		<core:out value="<%=borrowerObjects[i].getAc_no()%>"></core:out>
		</td>
		<td>
		<core:out value="<%=borrowerObjects[i].getType()%>"></core:out>
		</td>
		<td>
		<core:out value="<%=borrowerObjects[i].getModuleabbr()%>"></core:out>
		</td>
		<td>
		<core:out value="<%=borrowerObjects[i].getCid()%>"></core:out>
		</td>
	</tr>
	<%} %>	    
<%}else{ %>
<bean:message key="label.cosurity"/>
<%} %>
</table>
</html:form>
</body>
</html>