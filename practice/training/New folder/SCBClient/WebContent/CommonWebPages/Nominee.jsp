<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: Dec 7, 2007
  Time: 11:36:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="masterObject.general.NomineeObject"%>
<html>
  <head><title>Nominee Screen</title></head>
  <body>
  <%!
    String pageNum;
  NomineeObject[] objects;
  %>
  <%
      pageNum=(String)request.getAttribute("pageNum");
  		objects=(NomineeObject[])request.getAttribute("nomineeDetails");
  %>
      <html:form action="/Common/Nominee?pageId=<%=pageNum%>">
        <table class="txtTable" style="border:solid thin #000000; ">
         <%if(objects!=null){ %>
        	<tr><td></td><td><center>Nominee Details</center></td><td></td></tr>
       
        <%if(objects[0].getCustomerId()!=0){ %>
            <tr>
            	<td><bean:message key="label.cid"></bean:message></td>
        		<td><html:text property="cid" value="<%=""+objects[0].getCustomerId()%>" styleClass="formTextField" readonly="true"></html:text></td>
       		</tr>
       	<%} %>	
       		<tr>
       			<td><bean:message key="label.custname"></bean:message></td>
       			<td><html:text property="name" value="<%=""+objects[0].getNomineeName()%>" styleClass="formTextField" readonly="true"></html:text></td>
       		</tr>
       		<tr>
       			<td><bean:message key="label.dob"></bean:message></td>
       			<td><html:text property="dob" value="<%=""+objects[0].getNomineeDOB()%>" styleClass="formTextField" readonly="true"></html:text></td>
       		</tr>
       		<tr>
       			<td><bean:message key="label.age"></bean:message></td>
       			<td><html:text property="age" value="<%=""+objects[0].getSex()%>" styleClass="formTextField" readonly="true"></html:text></td>
       		</tr>
       		<tr>
       			<td><bean:message key="label.sex"></bean:message></td>
       			<td><html:text property="sex" styleClass="formTextField" readonly="true"></html:text></td>
       		</tr>
       		<tr>
       			<td><bean:message key="label.address"></bean:message></td>
       			<td><html:textarea property="address" value="<%=""+objects[0].getNomineeAddress()%>" styleClass="formTextField" readonly="true"></html:textarea></td>
			</tr>
			<tr>
				<td><bean:message key="label.relationship"></bean:message></td>
				<td><html:text property="relationship" value="<%=""+objects[0].getNomineeRelation()%>" styleClass="formTextField" readonly="true"></html:text></td>
			</tr>
			<tr>
				<td><bean:message key="label.percentage"></bean:message></td>
				<td><html:text property="percentage" value="<%=""+objects[0].getPercentage()%>" styleClass="formTextField" readonly="true"></html:text></td>
			</tr>
			<%} %>
        </table>
       </html:form>
  </body>
</html>