<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.ShareReportObject"%>
<html>
<head>
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
</head>
<body>

<%!
   ShareReportObject[] shreport;
%>
<%
    shreport=(ShareReportObject[])request.getAttribute("Report");
 
 %>
 
 
 <br>

<div  id = "table1" style="display: block;width: 750px;height: 300px">
   			<display:table name="Report" id="currentRowObject" class="its" sort="list" pagesize="10" requestURI="/Share/ShareAllotwith.do"  >
   			<!--<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   			<display:column media="csv excel" title="URL" property="url" value="true"/>
    		<display:setProperty name="export.pdf" value="true" /> 

  
    
    
       --><display:column property="shareNumber"></display:column>
       <display:column property="name"></display:column>
       <display:column property="transDate"></display:column>
       <display:column property="transType"></display:column>
       <display:column property="numberofShares"></display:column>
       <display:column property="shareVal"></display:column>
       
    </display:table>
</div>
    

</body>
</html>