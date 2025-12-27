<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>    
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.ShareReportObject"%>

<%@page import="java.util.Date;"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
            @import url("../css/displaytag.properties");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
	
</head>
<body class="Mainbody">

<%!
   ShareReportObject[] shreport;
%>
<%
    shreport=(ShareReportObject[])request.getAttribute("Report");
    
 %>
 
 
 <br>


<div  id = "table1" style="display: block;width: 750px;height: 300px">
   			<display:table  name="Report" uid="report" id="currentRowObject" class="its" sort="list" pagesize="10" requestURI="/Share/ShareOpenReport.do">
   			
   			
    		
       <display:column property="shareNumber" title="Shno"><input type="text" readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${shareNumber}" /></display:column>
       <display:column property="shareStatus"  style="text-align:right"><input type="text" readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0"  /></display:column>
       <display:column property="customerId"   style="text-align:center"></display:column>
       <display:column property="name" title="Name" style="text-align:left"></display:column>
       <display:column property="numberofShares"  style="text-align:right"></display:column>
       <display:column property="branchCode"  style="text-align:right"></display:column>
       <display:column property="issueDate"   style="text-align:right;"></display:column>
       <display:column property="shareVal"  style="text-align:right"></display:column>
  
    </display:table>
</div>

</body>
</html>