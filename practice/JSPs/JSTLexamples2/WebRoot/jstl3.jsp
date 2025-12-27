<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>
<c:set var="a" value="${param.dropdown}"></c:set>
<c:choose>
<c:when test="${a==1}">Ramu</c:when>
<c:when test="${a==2}">Sita</c:when>
<c:when test="${a==3}">Laxmana</c:when>
<c:when test="${a==4}">Bharatha</c:when>
<c:when test="${a==5}">Satrugna</c:when>
<c:when test="${a==6}">Ravana</c:when>
</c:choose>
</h1>