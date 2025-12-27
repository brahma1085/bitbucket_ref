<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%--
  Created by IntelliJ IDEA.
  User: swetha
  Date: Oct 25, 2007
  Time: 3:12:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<%@page import="org.ajaxtags.demo.TimeDeamon"%>
<jsp:useBean id="now" class="java.util.Date"/>
<html>
  <head><title>Simple jsp page</title>
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/prototype.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/scriptaculous/scriptaculous.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/overlibmws/overlibmws.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/overlibmws/overlibmws_crossframe.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/overlibmws/overlibmws_iframe.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/overlibmws/overlibmws_hide.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/overlibmws/overlibmws_shadow.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/ajax/ajaxtags.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/ajax/ajaxtags_controls.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/ajax/ajaxtags_parser.js"></script>
  <link href="Images/DMTStyle.css" rel="stylesheet" type="text/css" />
   <script type="text/javascript">
 	function updateDate(){
		for (i=0; i < this.list.length; i++)
		{
			if (this.list[i][0] === "datum") {
					$('datum').innerHTML = this.list[i][1];
			}
			if (this.list[i][0] === "count") {
					$('count').innerHTML = this.list[i][1];
			}
		}
	}
	
	function clear(){
		document.getElementById("uid").value="";
		document.getElementById("pass").value="";
		document.getElementById("tml").value="";
	}
	
	

</script> 	
  </head>
  <body class="Mainbody" onload="return clear()">
  <!--<img src="Background2.JPG" alt="" width="108" height="108"/>-->
  <html:form action="/corelink">
  <table class="txtTableHeader" width="120%">
  	<thead class="txtTableHeader" align="right">Sunrise IT Solutions Pvt Ltd.</thead>
  	<tr><td></td></tr>
  	<tr><td></td></tr>
  	<tr><td></td></tr>
  	<tr><td></td></tr>
  	<tr><td></td></tr>
  	<tr><td></td></tr>
  	<tr><td></td></tr>
  	<tr><td></td></tr>
  	<tr><td></td></tr>
  	<tr><td></td></tr>
  	
  	
  	
  	
  </table>
  <br>
  <br>
  <br>
  <br>
  <br>
  <center>
   	<table class="txtLoginTable">
    		<thead class="ThStyle" align="center"><font color="yellow">SUNBANK</font></thead>
    		<tr class="TrHeight5PX"><td></td><td align="left" ><font style="font-weight: bold;">Login</font></td></tr>
    		<tr><td></td></tr>
    		<tr><td></td></tr>
    		<tr>
    			<td></td>
    			<td align="left">
    				<font color="black" style="txtTable"><ajax:callback url="/callback.view" postFunction="updateDate" /><span id="count" style="visibility: hidden"><%= TimeDeamon.get().countObservers() %></span><span id="datum"><%=now %></span></font>
    			</td>
    		</tr>
    		<tr><td></td></tr>
    		<tr><td></td></tr>
    		<tr><td></td></tr>
    		<tr><td></td></tr>
    		<tr><td></td></tr>
    		<tr><td></td></tr>
    		<tr><td></td></tr>
    		<tr>
    			<td align="right">User Id:</td>
    			<td><html:text property="name" maxlength="20" styleId="uid" styleClass="formTextFieldWithoutTransparent"/></td>
    		</tr>
    		<tr>
    			<td align="right">Password:</td>
    			<td ><html:password property="pass" maxlength="20"  styleId="pass"  styleClass="formTextFieldWithoutTransparent"/></td>
    		</tr>
    		<tr>
    			<td align="right">TML No:</td>
    			<td><html:text property="tml" maxlength="20" styleId="tml" styleClass="formTextFieldWithoutTransparent" ></html:text>
    			</td>
    		</tr>
    		<tr><td></td></tr>
    		<tr><td></td></tr>
    		<tr><td></td></tr>
    		<tr><td></td></tr>
    		<tr><td></td></tr>
    		<tr><td></td></tr>
    		<tr><td></td></tr>
    		<tr class="TdThBackgroundImage">
    			<td bordercolor="blue" align="right"><html:submit property="submit"  styleClass="ButtonBackgroundImage">Submit</html:submit></td>
    			<td align="left">
    				<html:reset property="clear" styleClass="ButtonBackgroundImage">Cancel</html:reset>
    			</td>
    		</tr>
    	</table>
   		
    </center>
    <br>
    <br>
    <br>
   <table class="loginTableFooter" width="120%">
  	<thead class="loginTableFooter"></thead>
  		<tr><td class="loginTableFooter">#337,FF4,Karuna Complex,SampigeRoad, Malleswaram,Bangalore-560003 </td></tr>
  	</table>
  <jsp:include page="Footer.jsp"></jsp:include>
  </html:form>
 
	
   
  </body>
</html>
