<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="c"    uri="/WEB-INF/c-rt.tld"%>
<%@ page import="masterObject.lockers.LockerMasterObject" %>

<script type="text/javascript">

funtion set(target){
document.forms[0].forward.value=target;
}


function validateEachField(txt){ alert("jghk");
	 if(txt.length=="")      {  
	alert("Stupid u left d field blank")
	document.forms[0].field1.focus();
	  }   
}




function ValidateForm(){
	var f1=document.forms[0].field1;
	var f2=document.forms[0].field2;
	if (f1.value==""||f2.value==""){

	alert("U moron!!enter something");
	
	document.forms[0].field1.focus();
	}
    
 }

</script>




<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body bgcolor="#f3f4c8">
<html:form action="/manulink?pageId=9009" method="post" onsubmit="return ValidateForm()">
  <b><font color="RED">Auto Extension of Locker</font></b>



<table>

<tr>
<td>
LkTypess:<html:select property="lkTypes">

<c:forEach var="lktypes" items="${requestScope.lktypes}">              
            <html:option value="${lktypes}" > <c:out value="${lktypes}"></c:out>  </html:option>
			</c:forEach>
</html:select>
</td>


<td>
CAB11 NO:<html:select property="cabNo">

<html:option value="1"></html:option>
<html:option value="2"></html:option>
<html:option value="3"></html:option>
<html:option value="4"></html:option>

</html:select>
</td>
</tr>

<tr>
<td><html:text property="field1" value="" onblur="validateEachField(this.value)"></html:text></td>
<td><html:text property="field2" value="" onblur="validateEachField(this.value)"></html:text></td>
</tr>
<tr>
<%!LockerMasterObject array_lockermasterobject[]; %>


<%array_lockermasterobject= (LockerMasterObject[])request.getAttribute("details");
if(array_lockermasterobject!=null){%>
<html:select property="accountNum" size="20" onclick="submit()">
<%for(int i=0;i<array_lockermasterobject.length;i++){ %>
<html:option value="<%=""+array_lockermasterobject[i].getLockerAcNo()%>"> <%=array_lockermasterobject[i].getLockerAcNo()%>  </html:option>
<%} %>
</html:select>


<%} %>


</tr>

<html:hidden property="forward" value="error" />
<tr><td><html:submit  onclick="set('submit');"></html:submit></td></tr>

</table>
</html:form>
</body>
</html>