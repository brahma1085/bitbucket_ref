<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RBIForm9Posting</title>

<style type="text/css" media="all">
            @import url("../css/alternative.css");
            @import url("../css/maven-theme.css");
            @import url("../css/site.css");
            @import url("../css/screen.css");
    </style>
    <link rel="stylesheet" href="../css/print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css" media="print" />

<link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

function onlyDoublenumbers()
{
        	var cha = event.keyCode;
            if((cha >= 48 && cha <= 57 )||cha == 46) 
            {
            		return true;
           	} 
           	else 
           	{
        			return false;
        	}
			        
}

function setSubmit(flagVal)
{
	document.forms[0].flag.value=flagVal;
	document.forms[0].submit();

}

</script>
</head>
<body>
<center><h2 class="h2"><i><bean:message key="gl.label.posting"></bean:message> </i></h2></center>
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
<html:form action="/GL/GLRBIForm9Posting?pageId=12035">

<html:hidden property="flag"></html:hidden>
<%
	String[][] listrr =(String[][]) request.getAttribute("Form9LinkCodes");
%>
<table>
		<tr><html:text property="validations" readonly="true" size="100" style="color:red;font-family:bold;" styleClass="formTextField"></html:text></tr>
		<tr>
		<td>
		<table>
			<tr>
				<td><bean:message key="label.year"></bean:message></td>
				<td><html:select property="year" styleClass="formTextFieldWithoutTransparent">>
					<core:forEach var="n" begin="1980" end="2020">
						<html:option value="${n}">
						</html:option>
					</core:forEach>
					</html:select>	
				</td>
				<td></td>
				<td><bean:message key="label.month"></bean:message></td>
				<td><html:select property="month" styleClass="formTextFieldWithoutTransparent" onchange="setSubmit('monthdet')">
					<core:forEach var="n" begin="1" end="12">
						<html:option value="${n}">
						</html:option>
					</core:forEach>
					</html:select>
				</td>
			</tr>
		</table>
		</td>
	
	</tr>
	<tr>
		<td>
			<table>
				<tr>
					
					<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
					<td><html:button property="process" styleClass="ButtonBackGroundImage"  value="Process" onclick="setSubmit('process')"></html:button></td>
					<%}else{ %>
					<td><html:button property="process" styleClass="ButtonBackGroundImage"  value="Process" onclick="setSubmit('process')" disabled="true"></html:button></td>
					<%} %>
					<td><html:button property="cancel" styleClass="ButtonBackGroundImage"  value="Cancel" onclick="clear()"></html:button></td>	
				</tr>
			</table>
		</td>
	</tr>
	<tr>
	
		<td>
			<table> 
			<%if(listrr!=null){ %>
				<tr><td>GLCode</td><td>Description</td><td>Amount</td></tr>
				<%for(int i=0;i<listrr.length;i++){ %>
				<tr>
					<td><input type="text" name="glCode" value="<%=listrr[i][0]%>" size="8"/></td><td><html:text property="description" value="<%=listrr[i][1]%>"></html:text></td><td><input type="text" name="amount"  onkeypress="return onlyDoublenumbers()"/></td>
				</tr>	
				<%} }%>
			</table>
		
		</td>
	</tr>
	
</table>

</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>