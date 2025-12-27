<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.scb.loans.forms.LoanApplicationForm"%>
<%@ taglib uri="/WEB-INF/tabbedpanetag.tld" prefix="tabs" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %> 
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%! 
		String pagePath[];
		String pageAction[];
		String tabNum;
		String tabHeading[];
		LoanApplicationForm appForm;
		String actionPath;
		String changePath;
	%>
	
<%

	pageAction=(String[])request.getAttribute("PageActions");
	System.out.println("pageAction==="+pageAction);
	
	pagePath=(String[])request.getAttribute("PagePath");
	System.out.println("pagePath==="+pageAction);
	
	tabNum=(String)request.getAttribute("TabNum");
	System.out.println("Tabnum==="+tabNum);
	
	tabHeading=(String[])request.getAttribute("TabHeading");
	System.out.println("tabHeading==="+tabHeading);


%> 

<tabs:tabbedPane defaultTab="<%=tabNum.trim() %>"  >
    	<table cellspacing="0" cellpadding="5" style="background:transparent;">
        	<tr style="background: transparent;">
            	<%for(int i=0; i<pageAction.length; i++){
            		System.out.println("pageAction["+i+"]=="+pageAction[i]);
            	%>
            		<td style="background:silver;" class="<tabs:tab tabId="<%=String.valueOf(i)%>" active="activeTab" inactive="inactiveTab"/>">
                		<html:link action="<%=pageAction[i]%>"><%=tabHeading[i] %></html:link>
            		</td>
            	<%}%>
        	</tr>
        	<tr>
        			<td colspan="<%=pagePath.length%>" class="tabContent" style="background:threedlightshadow;"><br/><br/>
            		<%for(int i=0; i<pagePath.length; i++){
            		%>
            			<tabs:tabContent tabId="<%=String.valueOf(i)%>">
            				<jsp:include page="<%=pagePath[i].toString().trim()%>"></jsp:include>	
            			</tabs:tabContent>
            		
            		<%}%>
        				</td>
        	</tr>
    </table>
    </tabs:tabbedPane><br/>

</body>
</html>