<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%--
  Created by IntelliJ IDEA.
  User: shwetha
  Date: Nov 23, 2007
  Time: 1:16:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Simple jsp page</title></head>
  <body>
  <center>   
       <br><br><br><br><br><html:form action="/photo" method="post" enctype="multipart/form-data">
       FileName:<html:file property="theFile" ></html:file><br><br>
       <html:submit>Upload File</html:submit>
</center>
   
   <table>
       <tr><td>
         <html:img src="goldfade.JPG" width="60" height="60" ></html:img>
         <%--<html:img src="<%=request.getAttribute("swe")%>"></html:img>--%>

         <html:img src="<%=""+request.getAttribute("swe")%>" width="60" height="60" ></html:img>

          <% System.out.println("swe"+request.getAttribute("swe"));%>   

       </td></tr>
   </table>

  
         </html:form>
    </body>
  </html>