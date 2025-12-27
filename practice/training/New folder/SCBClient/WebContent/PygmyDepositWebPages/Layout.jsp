<%--
  Created by IntelliJ IDEA.
  User: Suraj
  Date: Oct 29, 2007
  Time: 5:48:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Layout</title>
  
  </head>
  <body>

    <jsp:include page="header.jsp"></jsp:include>

  <jsp:include page="menu.jsp"></jsp:include>
   
  <%--<%!
      String file_name="0000";
  %>
  <%
      if(file_name.equals("0000")){
          file_name="Test1.jsp";
      }
      else{
       file_name=request.getParameter("pageId");
      System.out.println("the page id is"+file_name);
      }
   %>
   <jsp:include page="<%=file_name%>" ></jsp:include>  --%>

  <jsp:include page="footer.jsp"></jsp:include>

  </body>
</html>