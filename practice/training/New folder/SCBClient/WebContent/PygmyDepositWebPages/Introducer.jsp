
<%@ page import="com.scb.pd.forms.PersonalForm" %>
<%@ page import="com.scb.pd.actions.PersonalDetails" %>


<%--
  Created by IntelliJ IDEA.
  User: Suraj
  Date: Nov 19, 2007
  Time: 11:38:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<html>
  <head><title>Simple jsp page</title>
      <style type="text/css">
     h1,h2,h3,h4{

              font:"Times New Roman", Times, serif;
              font-size:18px;
              font-style:normal;

              font-weight:bold;
              text-align:center;
      }

      body{
          background:beige;

      }

      table{

           background:transparent;
          border:solid black;

      }

      tr{
          background:transparent;

      }

      td{
          background:transparent;

      }

   </style>
  </head>
  <body>
      <html:form action="/intr">

      <h4><center><b><bean:message key="label.introducer"></bean:message></b></center></h4>

            <%--<%! IntroducerDetails introduce; %>
            <% introduce  = (IntroducerDetails) request.getAttribute("IntrDetails");%>--%>

           <%! PersonalDetails perform; %>
            <% perform = (PersonalDetails) request.getAttribute("IntrDetails");%>
          <table>
              <tr>
                  <td>
                      <bean:message key="label.custid"></bean:message></td>
                  <%
                      if(perform!=null) {
                  %>
                        <td><html:text property="custid" size="10" value="<%= perform.getCust_id()%>"></html:text></td>
                  <% }else {%>
                         <td><html:text property="custid" size="10" ></html:text></td>
                  <%}%>
              </tr>
              <tr>
                  <td>
                      <bean:message key="label.category"></bean:message> </td>
                   <td><html:text property="category" size="10"></html:text>
                  </td>
                  <td>
                      <bean:message key="label.sub-cat"></bean:message> </td>
                     <td>  <html:text property="subcat" size="10"></html:text>
                  </td>
              </tr>
               <tr>
                  <td>
                      <bean:message key="label.mailid"></bean:message></td>
                   <td>   <html:select property="mailid">
                           <html:option value="email"></html:option></html:select>
                  </td>
                    <td>
                      <bean:message key="label.scst"></bean:message></td>
                    <td><html:text property="scst" size="10"></html:text>
                  </td>
              </tr>
              <tr>
                  <td>
                      <bean:message key="label.address"></bean:message> </td>
                   <td>   <html:textarea property="address" ></html:textarea>
                  </td>
              </tr>
               <tr>
                  <td>
                      <bean:message key="label.dob"></bean:message> </td>
                    <%
                      if(perform!=null) {
                     %>
                         <td>   <html:text property="dob" size="10" value="<%= perform.getDob()%>"></html:text></td>
                      <% }else {%>
                         <td>   <html:text property="dob" size="10" ></html:text></td>
                    <%}%>
                   <td>
                      <bean:message key="label.sex"></bean:message> </td>
                     <td>  <html:text property="sex" size="10"></html:text>
                  </td>
              </tr>
              <tr>
                  <td>
                      <bean:message key="label.age"></bean:message></td>
                   <td>   <html:text property="age" size="10"></html:text>
                  </td>
                  <td>
                      <bean:message key="label.occupation"></bean:message></td>
                   <td>   <html:text property="occupation" size="10"></html:text>
                  </td>
              </tr>
          </table>
      </html:form>
  </body>
</html>
