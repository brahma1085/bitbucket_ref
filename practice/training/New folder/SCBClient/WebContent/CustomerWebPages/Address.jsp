<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <link rel="stylesheet" href="<%=request.getContextPath()%>/Images/DMTStyle.css" type="text/css" media="print" />
</head>
<body>
<html:form action="/Address">

Address Type:<html:select property="combo_addresstype" onchange="submit()">
				<core:forEach items="${AddrType}" var="AddrType">
					<html:option value="${AddrType.addressCode}">${AddrType.addressDesc}</html:option>
				</core:forEach>
			</html:select>
			<html:textarea property="addressarea2" value="${requestScope.addr2}"></html:textarea>
			<input type="submit" value="Save" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Save'" />
			<input type="submit" value="Submit" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=Submit'" />



</html:form>
</body>
</html>