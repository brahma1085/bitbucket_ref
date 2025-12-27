<%@ page import="com.scb.cm.actions.ParameterForm" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%--
  Created by IntelliJ IDEA.
  User: swetha
  Date: Nov 6, 2007   
  Time: 2:51:53 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head><title>Simple jsp page</title></head>
  <body background="tn_87.jpg">
  <html:form action="/param">
   <!--<label>swe</label>-->
  <%--<html:select property="addresstype">--%>
  <%--<html:option value="Residential">Residential</html:option>
  <html:option value="Official">Official</html:option>
  <html:option value="Communication">Communication</html:option>

  </html:select>
  </label>
   <br>  --%>
 
  <%! ParameterForm form,form1;
     %>

      <% form=(ParameterForm)request.getAttribute("swe1");
        /* form1=(ParameterForm)request.getAttribute("parameter");*/
      {

        String com=form.getComm();

       %>
      <%--form=(ParameterForm)request.getAttribute("swe");--%>
     <%--<%=form.getComm()%>--%>
       <%--<html:text property="txt" value="<%=form.getTxt()%>"></html:text>
       <html:textarea value="<%=form.getComm()%>" property="txt"></html:textarea>--%>
      <label>Address Type
      <html:select property="addresstype">
      <html:option value="<%=form.getTxt()%>"></html:option>
      <%--<html:option value="<%=form1.getResd()%>"></html:option>    --%>
      </html:select>
      </label>
      <% }%>
</html:form>
</body>
</html>