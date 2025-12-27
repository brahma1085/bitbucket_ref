<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.ShareReportObject"%>
<%@page import="org.displaytag.decorator.TotalTableDecorator;"%>
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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
  
</head>
<body class="Mainbody">
  <%!
  ShareReportObject []shrep;
  %>
  
  <%
  shrep=(ShareReportObject[])request.getAttribute("passBook");
  %>
  
     <div  id = "table1" style="display: block;width: 600px;height: 300px">
   			<display:table name="passBook" export="true" id="currentRowObject" class="its" >
   			<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   			<display:column media="csv excel" title="URL" property="url" />
    		<display:setProperty name="export.pdf" value="true" />
    		 
	  <display:column property="transDate"   title="Trn_date"></display:column>
            
      <display:column property="debitAmount"  title="Deb_amt"></display:column>
      
      <display:column property="creditAmount"  total="true"   title="Cr_amt"></display:column>
      <display:column property="share_bal"  title="Sh_Bal"></display:column>
      
      <display:column property="numberofShares"  title="No_of_shares"></display:column>
      <display:column property="deUserId" ></display:column>
      <display:column property="veUserId" ></display:column>
     </display:table>
    
 
</body>
</html>