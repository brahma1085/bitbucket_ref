<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: Dec 7, 2007
  Time: 11:37:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Joint Holders</title>
      <h1 style="font:small-caps;font-style:normal;font-size:small;"><center>Joint Holders</center></h1>
  </head>
  <body>
  <%!
      String jspPath;
      String pageNum;
      String cid;
  %>
  <%
      pageNum=(String)request.getAttribute("pageNum");
  		cid=(String)request.getAttribute("JointCid");
  %>
  <html:hidden property="pageId"></html:hidden>
     <table>
         <html:form action="/Common/JointHolder?pageId=<%=pageNum%>">
          <tr>
         	<td>
         	<table class="txtTable">
                 <tr>
                      <td><bean:message key="label.cid"></bean:message></td>
                      <td><html:text property="cid" value="<%=""+cid%>" readonly="true" size="5" styleClass="formTextField"></html:text></td>
                 </tr>
                 <tr>
                 		<%  String jspPath=(String)request.getAttribute("flagName");
            				System.out.println("'m inside panel"+jspPath);
            
            			if(jspPath!=null)
            			{
            				System.out.println("wen 'm  null");
           				 %>
           				 <jsp:include page="<%=jspPath.toString().trim()%>"></jsp:include>
            
            			<%}else {%>
            			<jsp:include page="/LockerWebPages/blank.jsp"></jsp:include>
            			<%} %>
                 </tr>
             </table>
             </td>
         </tr>
         </html:form>    
         
     </table>
  </body>
</html>