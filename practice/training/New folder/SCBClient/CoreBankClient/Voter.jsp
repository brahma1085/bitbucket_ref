<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.ShareReportObject"%>
<%@page import="java.util.Map"%>
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
String access;
String  accesspageId;
 Map user_role;

%>

<%

  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");

if(user_role!=null&&accesspageId!=null){
			if(user_role.get(accesspageId)!=null){
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}

			
			}

// Note:access=1111==>read,insert,delete,update
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<%!
   ShareReportObject[] shreport;
%>
<%
    shreport=(ShareReportObject[])request.getAttribute("Report");
 
 %>
   
 
<br>
   <div  id = "table1" style="display: block;width: 750px;height: 300px">
   			<display:table name="Report" export="true" id="currentRowObject" class="its" sort="list" pagesize="10">
   			<!--<display:setProperty name="export.rtf.filename" value="example.rtf"/>
   			<display:column media="csv excel" title="URL" property="url" />
    		<display:setProperty name="export.pdf" value="true" /> 
   
   
       --><display:column property="shareNumber" ></display:column>
       <display:column property="name" ></display:column>
       <display:column property="address" ></display:column>
       <display:column property="city" ></display:column>
       <display:column property="pin" ></display:column>
       <display:column property="shareStatus"  ></display:column>
     </display:table>
</div>
    
   <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>

</body>
</html>