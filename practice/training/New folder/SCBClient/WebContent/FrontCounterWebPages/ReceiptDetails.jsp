<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: Dec 7, 2007
  Time: 1:24:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Receipt Details</title></head>
  <h1 style="font:small-caps;font-style:normal;font-size:small;"><center>Receipt Details</center></h1>
  <body>
  <%!
      String pageNum; 
  %>
  <%
    pageNum=(String)request.getAttribute("pageNum");  
  %>
  <html:form action="/FrontCounter/ReceiptDetails?pageId=<%=pageNum%>">
   <table>
       <tr>
           <td>
               <bean:message key="label.scrollNum"></bean:message>
           </td>
           <td>
               <html:text property="scrollNum" value="0"></html:text>
           </td>
       </tr>
       <tr>
           <td>
               <bean:message key="label.date"></bean:message>
           </td>
           <td>
               <html:text property="date" value="0"></html:text>
           </td>
       </tr>
       <tr>
           <td>
               <bean:message key="label.name"></bean:message>
           </td>
           <td>
               <html:text property="name" value=""></html:text>
           </td>
       </tr>
       <tr>
           <td>
               <bean:message key="label.amount"></bean:message>
           </td>
           <td>
               <html:text property="amount" value="0.0"></html:text> 
           </td>
       </tr>
       <tr>

       </tr>
   </table>
  </html:form>
  </body>
</html>