

<%--
  Created by IntelliJ IDEA.
  User: user
  Date: Oct 16, 2007
  Time: 10:36:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>--%>
<%--<html>
  <head><title></title></head>
  <body>
  <tiles:insert attribute="Header"/> --%>
  <%--<tiles:insert attribute="Date"/>--%>
 <%-- <tiles:insert attribute="Body"/>
  <tiles:insert attribute="Footer"/>
  </body>
  </html>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html>
  <head>
  <title>Sun Core Bank</title>
      <%--<script type="text/javascript" src="menu.js"></script>--%>
  </head>
  <body>
  

     <table>
           <tr>
              <td></td>
         </tr>

         <tr>
            <td><jsp:include page="CBSMenu.jsp"></jsp:include></td>
       </tr>
         

     <%! String jspName;%>
        <!-- <tr>
         <td height ="250"><td>-->
         
           
     <%
         jspName=(String)request.getAttribute("pageId");
         if(jspName!=null){
             if(!jspName.equals("")){
        
     %>

      <tr><td>
      <% System.out.println("My JSP NAME is(frm Layout)=="+jspName); %>
      <jsp:include page="<%=jspName.toString()%>"></jsp:include>
   <%--<jsp:include page="Customer/parameter.jsp"></jsp:include>--%>
      <!--</td></tr>-->
         </td></tr>
  <%}}%>


   <tr><td>
   <jsp:include page="Footer.jsp"></jsp:include>
   </td></tr>

  </table>
  </body>
  </html>
