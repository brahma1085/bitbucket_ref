<%@ taglib uri="/WEB-INF/tabbedpanetag.tld" prefix="tabs" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%> 
<%@page import="com.scb.common.help.CommonPanelHeading"%>
<html>
<head>
    <link rel="stylesheet" href="/prizetagsdemo/stylesheet.css" type="text/css">
    <title>Tabbed Pane Tag</title>
    <style type="text/css">
    .activeTab {
        Font-Family: Arial;
        Font-Size  : 12px;
        Font-Weight: Bold;
        font-color : #1234;
        background-image: url(../Images/s2.gif);
        Border-Top:   1px solid #000000;
        Border-Left:  1px solid #000000;
        Border-Right: 1px solid #000000;
    }
    .inactiveTab {
        Font-Family: Arial;
        Font-Size  : 12px;
        Font-Weight: Boldx;
        Background-Color: #EEEEEE;
        color:black;
        font-color : #1234;
        background-image: url(../Images/s2.gif);
        Border     : 1px solid #000000;
    }
    .tabContent {
        Font-Family: Arial;
        Font-Size  : 12px;
        Font-Weight: Boldx;
        color:black;
        font-color : #1234;
        background-image: url(../Images/FrontDivBack1.jpg);
        Border-Left:  1px solid #000000;
        Border-Right: 1px solid #000000;
        Border-Bottom:   1px solid #000000;
    }
   
</style>
    <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
    
    
</head>

<body class="Mainbody">
	
	<%! 
		String pagePath[];
		String pageAction[];
		String tabNum;
		String tabHeading[];
		SBOpeningActionForm sbForm;
		String actionPath;
		String changePath;
		String panel;
	%>
	<%	
		
    	pageAction=(String[])request.getAttribute("PageActions");
		pagePath=(String[])request.getAttribute("PagePath");
    	tabNum=(String)request.getAttribute("TabNum");
    	System.out.println("Tabnum==="+tabNum);
    	tabHeading=(String[])request.getAttribute("TabHeading");
    	panel=(String)request.getAttribute("panel");
    	System.out.println("The panel name in Tabbed.jsp------"+panel);
    	request.setAttribute("panelName",CommonPanelHeading.getPersonal());
    	System.out.println("The  Common panel name in Tabbed.jsp------"+CommonPanelHeading.getPersonal());
   %>
   	<tabs:tabbedPane defaultTab="<%=tabNum.trim() %>"  >
    	<table cellspacing="0" cellpadding="5" class="txtTable">
        	<tr >
            	<%for(int i=0; i<pageAction.length; i++){
            		System.out.println("pageAction["+i+"]=="+pageAction[i]);
            	%>
            		<td class="<tabs:tab tabId="<%=String.valueOf(i)%>"  active="activeTab" inactive="inactiveTab"/>">
                		<font color="black"><html:link style="color:black" styleClass="formLinkField"  action="<%=pageAction[i]%>"><%=tabHeading[i] %></html:link></font>
            		</td>
            	<%}%>
        	</tr>
        	<tr>
        			<td colspan="<%=pagePath.length%>" class="tabContent" ><br/><br/>
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