
<%@page import="masterObject.general.BranchObject"%><%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: Oct 25, 2007
  Time: 12:34:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@page import="org.ajaxtags.demo.TimeDeamon"%>
<jsp:useBean id="now" class="java.util.Date"/>


<%@page import="java.util.Date"%>
<%@page import="com.scb.props.*"%>
<%@page import="java.util.Calendar"%>
<html>
  <head><title>Header jsp page</title>
  
   <!--<script type="text/javascript" src="<%=request.getContextPath()%>/js/prototype.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/scriptaculous/scriptaculous.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/overlibmws/overlibmws.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/overlibmws/overlibmws_crossframe.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/overlibmws/overlibmws_iframe.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/overlibmws/overlibmws_hide.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/overlibmws/overlibmws_shadow.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/ajax/ajaxtags.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/ajax/ajaxtags_controls.js"></script>
  --><!--<script type="text/javascript" src="<%=request.getContextPath()%>/js/ajax/ajaxtags_parser.js"></script>
 
  --><link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
 <script type="text/javascript">


 	function updateDate()
 	{
		for (i=0; i < this.list.length; i++)
		{
			if (this.list[i][0] === "datum") 
			{
				$('datum').innerHTML = this.list[i][1];
				alert($('datum').innerHTML);
			}
			if (this.list[i][0] === "count") {
				$('count').innerHTML = this.list[i][1];
			}
		}
	};
 function clear(){
 	
 };	
 </script>
  </head>
  <body>
  <%!BranchObject[] branchObjects;
  String branchname;%>
  <%String ctxPath=request.getContextPath(); 
  String host=MenuNameReader.getScreenProperties("host");
//  branchObjects=(BranchObject[])session.getServletContext().getAttribute("branches");
  %>
  <table width="100%" border="0" cellpadding="0"  class="txtTableHeader">
			<tr style="border:0px">
            	<td  width="47%"   align="left" style="border:0px"><font color="white"><%=session.getAttribute("BankName") %> &nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; </font></td><td style="border:0px" align="right"> <font color="white" >&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;User:<%=session.getAttribute("UserName")%></font> </td>
            </tr>
   			
   			<tr style="border:0px">
				<td style="border:0px" class="tdWithoutBorder"></td>
            </tr>
   			
            <tr><td></td><td></td></tr>
            <tr><td  style="border:0px" align="left"><a href="<%=host %>/SCBClient/Layout.jsp">Home</a>&nbsp;&nbsp;&nbsp;&nbsp;</td><td style="border:0px" align="right"> &nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=ctxPath %>/Images/APJ_speech.pdf" target="_blank">Help</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=host %>/SCBClient/logout.do">Logout</a></td></tr>
            
            <tr><td></td><td></td></tr>
        </table>
        	<table width="120%" border="1" cellpadding="0" class="txtTableFooter" >
		    	<tr style="border:0px">
                	<td style="border:0px" align="center">
                	<font color="blue"  size="3" face="Verdana, Arial, Helvetica,sans-serif" >
					
					</font></td>
   				</tr>
        </table>
       
</body>
</html>


