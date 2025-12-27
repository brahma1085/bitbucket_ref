<%@ page import=" masterObject.lockers.LockerDetailsObject" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>
<%@ taglib prefix="c"    uri="/WEB-INF/c-rt.tld" %>
<html>
<head>
<script type="text/javascript">

function validate(field){
if(field.value==""){
alert("Field is Empty");
}

else if(document.getElementById("masterKey").value.length!=0){
			document.getElementById("cabDesciption").style.border='transparent';
            document.getElementById("cabDesciption").style.backgroundColor='#f0cdcd';
            document.getElementById("cabDesciption").readOnly='true';
            document.getElementById("totLockers").style.border='transparent';
            document.getElementById("totLockers").style.backgroundColor='#f0cdcd';
            document.getElementById("totLockers").readOnly='true';
            document.getElementById("masterKey").style.border='transparent';
            document.getElementById("masterKey").style.backgroundColor='#f0cdcd';
            document.getElementById("masterKey").readOnly='true';
}

}


</script>
</head>
<body bgcolor="#f3f4c8">
<center><h2 class="h2"><bean:message key="lable.dataEntry"></bean:message></h2></center>
<html:form action="/LockerDELink?pageId=00">
<table class="txtTable">
<tr><td>

	<table class="txtTable">
	<tr><td>

		<table border="2" align="left" class="txtTable" >
		<tr>
		<td><bean:message  key="lab.cabDescription"></bean:message> </td>
		<td><html:text property="cabDesciption"   styleId="cabDesciption" onblur="validate(this)">1</html:text> </td></tr>
		
		<tr><td><bean:message key="lab.numLockers"></bean:message> </td>
		<td><html:text property="totLockers"  styleId="totLockers" onblur="validate(this)"></html:text> </td></tr>
		
		<tr><td><bean:message key="lab.masterKey"></bean:message> </td>
		<td><html:text property="masterKey"  styleId="masterKey" onblur="validate(this)"></html:text> </td></tr>
		
		
		<tr><td><bean:message key="lab.totRows"></bean:message> </td>
		<td><html:text property="noRows"></html:text> </td></tr>
		
		
		<tr><td><bean:message key="lab.rowNum"></bean:message> </td>
		<td><html:text property="rowNum"></html:text> </td></tr>
		
		<tr><td><bean:message key="label.lockerNum"></bean:message> </td>
		<td><html:text property="noLocks"></html:text> </td></tr>
		</table>
		</td>	
		</tr>
			
	</table>

</td>
<td>
	<table border="3" class="txtTable">
	<tr><td><bean:message key="lab.lockerTypes"></bean:message> </td>
	<td>
	 
	<html:select property="lkTypes" onchange="submit()">
			<html:option value="SELECT">SELECT</html:option>
			<c:forEach var="lktypes" items="${requestScope.lktypes}">              
            <html:option value="${lktypes}" > <c:out value="${lktypes}"></c:out>  </html:option>
			</c:forEach>
   </html:select></td>
	</tr>
<%! LockerDetailsObject lockerDetailsObjec; %>
<% lockerDetailsObjec=(LockerDetailsObject)request.getAttribute("lkdetails");%>


	<c:choose>
    <c:when test="${requestScope.lkdetails != null}">
	
	<tr>
	<td><bean:message key="lable.description" ></bean:message>  </td>
	<td><html:text property="description" readonly="true" value="<%=lockerDetailsObjec.getDescription()%>" ></html:text> </td></tr>
	
	<tr><td><bean:message key="lab.keyNo"></bean:message> </td>
	<td><html:text property="keyNum" value=""></html:text> </td>
	</tr>
	
	<tr><td><bean:message key="lable.length"></bean:message> </td>
	<td><html:text property="length" readonly="true" value="<%=lockerDetailsObjec.getLockerLength() %>"></html:text> </td></tr>
	
	<tr><td><bean:message key="lable.height"></bean:message> </td>
	<td><html:text property="height" readonly="true" value="<%=lockerDetailsObjec.getLockerHeight() %>"></html:text> </td></tr>
	
	<tr><td><bean:message key="lable.depth"></bean:message> </td>
	<td><html:text property="depth" readonly="true" value="<%=lockerDetailsObjec.getLockerDepth() %>"></html:text> </td></tr>
	</c:when>
	
	<c:otherwise>
	<tr><td><bean:message key="lable.description"></bean:message> </td>
	<td><html:text property="description" readonly="true" value=""></html:text> </td></tr>
	
	<tr><td><bean:message key="lab.keyNo"></bean:message> </td>
	<td><html:text property="keyNum" readonly="true"  value=""></html:text> </td>
	
	</tr>
	
	
	<tr><td><bean:message key="lable.length"></bean:message> </td>
	<td><html:text property="length"  readonly="true" value=""></html:text> </td></tr>
	
	<tr><td><bean:message key="lable.height"></bean:message> </td>
	<td><html:text property="height" readonly="true"  value=""></html:text> </td></tr>
	
	<tr><td><bean:message key="lable.depth"></bean:message> </td>
	<td><html:text property="depth" readonly="true" value=""></html:text> </td></tr>
	
	</c:otherwise>
	</c:choose>
	
	
	<tr><td><html:submit property="sub" value="Submit" styleClass="ButtonBackGroundImage"></html:submit>
	<html:submit property="clear" value="Clear" styleClass="ButtonBackGroundImage"></html:submit>
	</td>
	</tr>
	</table>
</td></tr>
</table>
</html:form>
</body>
</html>