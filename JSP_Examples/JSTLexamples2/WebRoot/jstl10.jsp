<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>
	Hello, This is redirecting page example
	<c:redirect url="jstl10.jsp">
		<c:param name="y" value="NARESH"></c:param>
	</c:redirect>
</h1>

============================================================

<h1>
	From jstl10 page
	<br />
	The retrieved value from redirected page is : ${param.y}
</h1>
