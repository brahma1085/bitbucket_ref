<%--
  Created by IntelliJ IDEA.
  User: user
  Date: Dec 3, 2007
  Time: 10:56:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true"  %>
<html>
  <head><title>Error Page</title></head>
  <body>
  <br><br><br><br>
      <center> <font size="5" color="red"><%=""+request.getAttribute("exception") %></font>
     
      </center>
  </body>
</html>