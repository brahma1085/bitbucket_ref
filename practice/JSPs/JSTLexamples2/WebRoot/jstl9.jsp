<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="jstl9.jsp">
	<c:param name="x" value="narayana"></c:param>
</c:import>
<h1>
	This is Index Page
</h1>
====================================================

<h1>
	This is jstl9 page
	<br />
	The value is : ${param.x}
</h1>
