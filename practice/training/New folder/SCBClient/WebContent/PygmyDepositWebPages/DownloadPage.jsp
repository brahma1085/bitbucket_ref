<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<html>
<script type="text/javascript">
		function set(target){
       	document.forms[0].forward.value=target;
       	document.forms[0].submit();
        };
</script>
<head>
<h1>DownLoad Page</h1>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>File Download</title>
</head>
<body>
<html:form action="/Pygmy/AgentOpening?PageId=8888">
<html:hidden property="forward"></html:hidden>
<html:button property="but" onclick="set('download')">Download</html:button>
</html:form>
</body>
</html>