<%@ taglib uri="/WEB-INF/tabbedpanetag.tld" prefix="tabs" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%> 
<html>
<head>
    <link rel="stylesheet" href="/prizetagsdemo/stylesheet.css" type="text/css">
    <link rel="stylesheet" href="../Images/DMTStyle.css" type="text/css">
    <title>Tabbed Pane Tag</title>
</head>

<body class="Mainbody">
<style type="text/css">
    .activeTab {
        Font-Family: Arial;
        Font-Size  : 12px;
        Font-Weight: Bold;
        Border-Top:   1px solid #000000;
        color:black;
        Border-Left:  1px solid #000000;
        Border-Right: 1px solid #000000;
        hight:20px;
        background-image: url(../Images/s2.gif);
    }
    .inactiveTab {
        Font-Family: Arial;
        Font-Size  : 12px;
        Font-Weight: Boldx;
        
        Border     : 1px solid #000000;
        hight:20px;
        background-image: url(../Images/s2.gif);
    }
    .tabContent {
        Font-Family: Arial;
        Font-Size  : 12px;
        Font-Weight: Boldx;
        color:black;
        Border-Left:  1px solid #000000;
        Border-Right: 1px solid #000000;
        Border-Bottom:   1px solid #000000;
      	 background-image: url(../Images/FrontDivBack1.jpg);
    }
    a {
        Text-Decoration: None;
    }
</style>
	
	<%! 
		String pagePath[];
		String pageAction[];
		String tabNum;
		String tabHeading[];
		SBOpeningActionForm sbForm;
		String actionPath;
		String changePath;
	%>
	<%	   
		
    	pageAction=(String[])request.getAttribute("PageActions");
		pagePath=(String[])request.getAttribute("PagePath");
    	tabNum=(String)request.getAttribute("TabNum");
    	System.out.println("Tabnum==="+tabNum);
    	tabHeading=(String[])request.getAttribute("TabHeading");
    	
   %>
   	<tabs:tabbedPane defaultTab="<%=tabNum.trim() %>"  >
    	<table  cellspacing="0" cellpadding="5" class="txtTable">
        	<tr>
            	<%for(int i=0; i<pageAction.length; i++){
            		System.out.println("pageAction["+i+"]=="+pageAction[i]);
            	%>
            		<td style="color: black" class="<tabs:tab tabId="<%=String.valueOf(i)%>" active="activeTab" inactive="inactiveTab"/>">
                		<html:link action="<%=pageAction[i]%>"><font color="black" ><%=tabHeading[i] %></font></html:link>
            		</td>
            	<%}%>
        	</tr>
        	<tr>
        			<td colspan="<%=pagePath.length+5%>" class="tabContent" ><br/><br/>
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