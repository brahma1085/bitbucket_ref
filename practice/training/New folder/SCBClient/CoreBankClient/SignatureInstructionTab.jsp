<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="/WEB-INF/tabbedpanetag.tld" prefix="tabs" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="/WEB-INF/c-rt.tld"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>
<%@page import="masterObject.general.SignatureInstructionObject"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
<style type="text/css">
    .activeTab {
        Font-Family: Arial;
        Font-Size  : 12px;
        Font-Weight: Bold;
        Background-Color: #FFFFFF;
        Border-Top:   1px solid #000000;
        Border-Left:  1px solid #000000;
        Border-Right: 1px solid #000000;
    }
    .inactiveTab {
        Font-Family: Arial;
        Font-Size  : 12px;
        Font-Weight: Boldx;
        Background-Color: #EEEEEE;
        Border     : 1px solid #000000;
    }
    .tabContent {
        Font-Family: Arial;
        Font-Size  : 12px;
        Font-Weight: Boldx;
        Background-Color: #FFFFFF;
        Border-Left:  1px solid #000000;
        Border-Right: 1px solid #000000;
        Border-Bottom:   1px solid #000000;
    }
    a {
        Text-Decoration: None;
    }
</style>

<%! 
		String pagePath;
		String pageAction[];
		String tabNum;
		String tabHeading[];
		SBOpeningActionForm sbForm;
		String actionPath;
		String changePath;
		
		SignatureInstructionObject[] siObj;
		
		String path;
		String href;
		String jspPath;
	%>
	<%	
		sbForm=(SBOpeningActionForm)request.getAttribute("SBOpeningForm");
		System.out.println("*****=="+sbForm);
    	pageAction=(String[])request.getAttribute("SignPageActions");
		pagePath=(String)request.getAttribute("SignPagePath");
    	tabNum=(String)request.getAttribute("SignTabNum");
    	System.out.println("Tabnum==="+tabNum);
    	tabHeading=(String[])request.getAttribute("SignTabHeading");
    	actionPath=(String)request.getAttribute("SignActionPath");
    	System.out.println("actionpath=="+actionPath);
    	
    	siObj=(SignatureInstructionObject[])request.getAttribute("signInfo");
    	path=(String)request.getAttribute("SignInstActnPath");
		path=path+"?signPageID=0005";
			
    	
    	
   %>
	
   	<tabs:tabbedPane defaultTab="<%=tabNum.trim() %>"  >
    	<table cellspacing="0" cellpadding="5" style="background:transparent;">
        	<tr style="background: transparent;">
            	<%for(int i=0; i<siObj.length; i++){
            		System.out.println("siobj["+i+"]=="+siObj[i]);
            		
            	%>
            		<td colspan="<%=siObj.length-1%>" style="background:silver;" class="<tabs:tab tabId="<%=String.valueOf(i)%>" active="activeTab" inactive="inactiveTab"/>">
                		<html:link action="<%=pageAction[i]%>"><%=tabHeading[i] %></html:link>
            		</td>
            	<%}%>
        	</tr>
        	<tr>
        			<td colspan="<%=siObj.length%>" class="tabContent" style="background:threedlightshadow;"><br/><br/>
            		<%for(int i=0; i<siObj.length; i++){
            				
            		%>
            			
            			<tabs:tabContent tabId="<%=String.valueOf(i)%>">
            				<jsp:include page="<%=pagePath.toString().trim()%>"></jsp:include>	
            			</tabs:tabContent>
            		
            		<%}%>
        			</td>
        	</tr>
    </table>
    </tabs:tabbedPane><br/>
    <table>
    	<tr>
    		<td>
   				<html:submit><bean:message key="label.submit"/></html:submit>
    			<html:submit><bean:message key="label.clear"/></html:submit>
    			<html:submit><bean:message key="label.modify"/></html:submit>
    		</td>
    	</tr>
   	</table>	

</body>
</html>