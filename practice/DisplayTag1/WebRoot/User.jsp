<%-- 
    Document   : user
    Created on : Jan 7, 2009, 8:23:48 AM
    Author     : eswar@vaannila.com
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Details</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <display:table id="data" name="sessionScope.UserForm.actorList" requestURI="/userAction.do" decorator="com.vaannila.ActorDecorator" pagesize="6" >
            <display:column property="tvShow" title="TV Show" />
            <display:column property="userName" title="User Name" />
            <display:column property="emailId" title="Email Id" />
        </display:table>
    </body>
</html>
