<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table>
<td>
<display:table name="SHARE REPORT" export="true" id="row" class="dataTable" defaultsort="1" defaultorder="ascending" pagesize="2" style="width:60%">

<display:column title="Share No" sortable="true"></display:column>
<display:column property="CID"></display:column>

</display:table>
</td>
</table>
</body>
</html>