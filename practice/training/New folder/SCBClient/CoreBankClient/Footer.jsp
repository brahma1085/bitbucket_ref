<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<%@page import="org.ajaxtags.demo.TimeDeamon"%>
<jsp:useBean id="now" class="java.util.Date"/>

<%--
  Created by IntelliJ IDEA.
  User: user
  Date: Oct 25, 2007
  Time: 12:34:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Simple jsp page</title>
  <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript">
function updateDate(){
	for (i=0; i < this.list.length; i++)
	{
		if (this.list[i][0] === "datum") {
				$('datum').innerHTML = this.list[i][1];
		}
		if (this.list[i][0] === "count") {
				$('count').innerHTML = this.list[i][1];
		}
	}
}
</script>
  
  </head>
  
  <body class="Footerbody"><!--
        <ajax:callback url="callback.view" postFunction="updateDate"/>
		<span id="count" style="visibility: hidden"><%= TimeDeamon.get().countObservers() %></span> 
		<p style="visibility: hidden">
			<span id="datum"> <%=now%></span>
		</p>
--></body>
</html>
