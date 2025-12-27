<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script type="text/javascript">
function editRow()
{
	alert("Edit a row");
	var a = document.getElementById("moduleCode").value;
	var b = document.getElementById("ModuleAbbr").value;
	var c = document.getElementById("Personal").value;
	var d = document.getElementById("Relative").value;
	alert("a value is ======="+a+b+c+d);
}
</script>
<body>
<html:form action="/Loans/LoanAdminNew?pageidentity.pageId=40001">
<display:table name="ViewIt" id="currentRowObject" sort="list">
	     
    <display:column title="ModuleCode">
              <input type="text" name="moduleCode" id="moduleCode" align="middle"  value="<core:out value="${currentRowObject.ModuleCode}"></core:out>" size="4" class="dispTabTextField" readonly="readonly"/>
    </display:column>
    
    <display:column title="ModuleAbbr">
              <input type="text" id="ModuleAbbr" name="ModuleAbbr" align="middle"  value="<core:out value="${currentRowObject.ModuleAbbr}"></core:out>" size="4" class="dispTabTextField" readonly="readonly"/>
    </display:column>
    
    <display:column title="Personal">
              <input type="text" align="middle" id="Personal" name="Personal" value="<core:out value="${currentRowObject.Personal}"></core:out>" size="4" class="dispTabTextField" readonly="readonly"/>
    </display:column>
    
    <display:column title="Relative">
              <input type="text" align="middle" id="Relative" name="Relative" value="<core:out value="${currentRowObject.Relative}"></core:out>" size="4" class="dispTabTextField" readonly="readonly"/>
    </display:column>
   <display:column title="Update">
   			<input type="checkbox" name="update" onclick="editRow()"/> 
   </display:column>
</display:table>
<html:submit>OK</html:submit>
<html:submit onclick="">Edit</html:submit>
</html:form>
</body>
</html>