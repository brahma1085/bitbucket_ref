<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>    
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.share.DividendObject"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

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
<body class="Mainbody">

<%!
   List divobj;
%>
<%
    divobj=(ArrayList)request.getAttribute("divnotice");
 
 %>
 <%
    if(divobj!=null){
 %>
 
 <br>
           <table>
           <tr>
           <td>
           <div style="width: 700px;height: 100px;overflow: scroll">
			
   			
   			<display:table name="divnotice" id="divnotice" list="${divnotice}" class="its" export="true">
   			<display:column title="${checkAll}" style="width:1%"><input type="checkbox" name="id" value="${divnotice.id}" style="margin:0 0 0 4px" onclick="radio(this)" <core:if test="${param.id==divnotice.id and param.method!='Save' and param.method!='Clear' and  param.method!='Reset'}">checked="checked"</core:if> /></display:column>
            <display:column style="width:3%;text-align: right;" title="Sh.No">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==divnotice.id }">
					<input type="text" name="Sh_No"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${divnotice.Sh_No}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${divnotice.Sh_No}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
   
          <display:column style="width:3%;text-align: right;" title="Cid">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==divnotice.id }">
					<input type="text" name="Cid"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${divnotice.Cid}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${divnotice.Cid}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		
		
		<display:column style="width:3%;text-align: right;" title="Name">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==divnotice.id }">
					<input type="text" name="Name"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${divnotice.Name}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${divnotice.Name}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		
		
		<display:column style="width:3%;text-align: right;" title="Div_Amt">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==divnotice.id }">
					<input type="text" name="Div_Amt"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${divnotice.Div_Amt}"   
					/>
				</core:when>
				<core:otherwise>
					<core:out value="${divnotice.Div_Amt}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
		
		<display:column style="width:3%;text-align: right;" title="Div rate">
			<core:choose>
				<core:when test="${param.method=='Edit' and param.id==divnotice.id }">
					<input type="text" name="Div_Date"  readonly="readonly" style="padding:0;background: transparent;border: 0px" style="padding:0" value="${divnotice.Div_Date}"   
					/>
				</core:when>
				<core:otherwise> 
					<core:out value="${divnotice.Div_Date}"></core:out>
				</core:otherwise>
			</core:choose>
		</display:column>
    
    </display:table>
  </div>
  </td>
  </tr>
  </table>
    <%} %>
</body>
</html>