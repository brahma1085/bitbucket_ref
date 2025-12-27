<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forTokens items="rama#sita$raju&rmesh*rakesh#roja" delims="#,$,&,*"
	var="x">
	<h1>
		The current tokenizer is : ${x}
	</h1>
</c:forTokens>