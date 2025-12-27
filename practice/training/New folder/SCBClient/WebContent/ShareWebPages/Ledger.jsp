<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="dispaly" uri="http://displaytag.sf.net/el" %>    
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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
	
  
</head>
<body class="Mainbody">
  <%!
  ShareReportObject []shrep;
  %>
  
  <%
  shrep=(ShareReportObject[])request.getAttribute("shareledger");
  %>
  
  
 <div  id = "table1" style="display: block;width: 750px;height: 300px">
   			<dispaly:table name="shareledger" export="true" id="currentRowObject" class="its" sort="list" pagesize="10" requestURI="/Share/ShareLedger.do">
   			<dispaly:setProperty name="export.rtf.filename" value="example.rtf"/>
   			<dispaly:column media="csv excel" title="URL" property="url" />
    		<dispaly:setProperty name="export.pdf" value="true" /> 

  
    <dispaly:column property="transDate"  title="Trn_date" ></dispaly:column>
    <dispaly:column property="name" ></dispaly:column>
    <dispaly:column property="debitAmount"  ></dispaly:column>
    <dispaly:column property="creditAmount" ></dispaly:column>
    <dispaly:column property="share_bal"  title="Sh_Bal"></dispaly:column>
      
      <dispaly:column property="numberofShares"  title="No_of_shares"></dispaly:column>
  </dispaly:table>
  
     
    
 
</body>
</html>