<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %> 
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>   
  <%@page import="java.util.Map"%> 
<%@taglib prefix="core" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
function func_button(target){
	alert(target);
	alert(document.forms[0].button_value.value);	
	document.forms[0].button_value.value=target;
}
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
<h2 class="h2"><center>DCB Summary</center></h2> 
</head>
<body>

<%!String DCBSummary[]; %>
<%DCBSummary=(String[])request.getAttribute("DCBSummary"); %>
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
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/Loans/DCBSummary?pageidentity.pageId=5039">
	<table>
		<tr>
			<td  align="right"><bean:message key="label.monthandyear"></bean:message></td>
			<td><html:select property="combo_Monthyear">
				<%if(DCBSummary!=null){ 
				for(int i=0;i<DCBSummary.length;i++){%>
				<html:option value="<%=""+DCBSummary[i].split(" ")[0] %>"></html:option>
				<%}} %>
			</html:select> </td>
		</tr>
		<html:hidden property="button_value" value="error"/>
		<tr>
		
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
			<td align="right"><html:submit value="View" onclick="func_button('View')" styleClass="ButtonBackgroundImage"></html:submit></td>
			<td align="right"><html:button value="clear" property="clear" styleClass="ButtonBackgroundImage"></html:button></td>
			<%}else{ %>
			<td align="right"><html:submit value="View" disabled="true" styleClass="ButtonBackgroundImage"></html:submit></td>
			<td align="right"><html:button value="clear" property="clear" disabled="true" styleClass="ButtonBackgroundImage"></html:button></td>
			<%} %>
		</tr>
	</table>
	<div style="display: block;overflow: scroll;width:700px;height: 200px">
	<display:table name="arraylist" id="arraylist" class="its">
			<display:column title="PrinOD"  style="width:3%;"  maxLength="50"><input type="text" align="middle"  value="<core:out value="${arraylist.PrinOD}"></core:out>" size="10" class="dispTabTextField" readonly="readonly"/></display:column>
			<display:column title="IntrOD" style="width:3%" maxLength="50"><input type="text" align="right" value="<core:out value="${arraylist.IntrOD}"></core:out>" size="10" class="dispTabTextField" readonly="readonly"/></display:column>
			<display:column title="PIntrOD" style="width:3%" maxLength="50"><input type="text" align="right" value="<core:out value="${arraylist.PIntrOD}"></core:out>" size="10" class="dispTabTextField" readonly="readonly"/></display:column>
			<display:column title="OtherOD" style="width:3%"><input type="text"align="right" value="<core:out value="${arraylist.OtherOD}"></core:out>" size="10" class="dispTabTextField" readonly="readonly"/></display:column>
			<display:column title="AdvPaid" style="width:3%"><input type="text" align="right" value="<core:out value="${arraylist.AdvPaid}"></core:out>" size="10" class="dispTabTextField" readonly="readonly"/></display:column>
			<display:column title="Prin Demnd" style="width:3%"><input type="text" align="right" value="<core:out value="${arraylist.PrinDemand}"></core:out>" size="10" class="dispTabTextField" readonly="readonly"/></display:column>
			<display:column title="Intr Demnd" style="width:3%"><input type="text" align="right" value="<core:out value="${arraylist.IntrDemand}"></core:out>" size="10" class="dispTabTextField" readonly="readonly"/></display:column>
			<display:column title="Othr Demnd" style="width:3%"><input type="text" align="right" value="<core:out value="${arraylist.OtherDemand}"></core:out>" size="10" class="dispTabTextField" readonly="readonly"/></display:column>
			<display:column title="Intr Coll" style="width:3%"><input type="text" align="right" value="<core:out value="${arraylist.IntrColl}"></core:out>" size="10" class="dispTabTextField" readonly="readonly"/></display:column>
			<display:column title="PIntr Coll" style="width:3%"><input type="text" align="right" value="<core:out value="${arraylist.PIntrColl}"></core:out>" size="10" class="dispTabTextField" readonly="readonly"/></display:column>
			<display:column title="Other Coll" style="width:3%"><input type="text" align="right" value="<core:out value="${arraylist.OtherColl}"></core:out>" size="10" class="dispTabTextField" readonly="readonly"/></display:column>
			<display:column title="Adv Coll" style="width:3%"><input type="text" align="right" value="<core:out value="${arraylist.AdvColl}"></core:out>" size="10" class="dispTabTextField" readonly="readonly"/></display:column>
			<display:column title="Prin Bal" style="width:3%"><input type="text" align="right" value="<core:out value="${arraylist.PrinBal}"></core:out>" size="10" class="dispTabTextField" readonly="readonly"/></display:column>
			<display:column title="Intr Bal" style="width:3%"><input type="text" align="right" value="<core:out value="${arraylist.IntrBal}"></core:out>" size="10" class="dispTabTextField" readonly="readonly"/></display:column>
			<display:column title="Pintr Bal" style="width:3%"><input type="text" align="right" value="<core:out value="${arraylist.PintrBal}"></core:out>" size="10" class="dispTabTextField" readonly="readonly"/></display:column>
			<display:column title="Othr Bal" style="width:3%"><input type="text" align="right" value="<core:out value="${arraylist.OtherBal}"></core:out>" size="10" class="dispTabTextField" readonly="readonly"/></display:column>
	</display:table>
	</div>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>		
</body>
</html>