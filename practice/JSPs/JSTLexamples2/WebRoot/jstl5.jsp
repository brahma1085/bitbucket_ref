<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach begin="0" end="10" var="x" step="2"><h1>the counter is : ${x}</h1></c:forEach>