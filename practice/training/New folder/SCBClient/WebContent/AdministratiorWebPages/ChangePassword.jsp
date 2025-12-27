

<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  Create by Mohsin on 22DEC2008
  
  To change this template use File | Settings | File Templates.
--%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>

<html>
  <head><title>AccountOpening</title>
      <h1 style="font:small-caps;font-style:normal;color: blue" ><center>Change Password</center></h1>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
       
      
      <!--<style type="text/css">
          body{
              font-size:10px;
              font-family:serif;
              font-style:oblique;
              font-weight:bold;
              background:beige;
          }
          table{
               background:transperent; 
          }
          
    </style>
    --><link href="Images/DMTStyle.css" rel="stylesheet" type="text/css" />
    <script>
   function confirmsubmit(){
  
   
   
   }
   
   
    
    </script>
  </head>
  <body class="Mainbody">
    
    
    
     <html:form action="/Administrator/ChangePassword?pageId=10002" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="defaultTab" value="PersonalDetails"/>
     	<html:hidden property="defSIndex" value="1"/>
     	<html:hidden property="pageId" value="3005"/>
     	<table width="726"><tr valign="middle" align="center"><td align="center">
	</td></tr></table>
<table border="0" width="637" height="250" align="center">
	<tr>
		<td height="48" width="637" colspan="2" align="center"></td>
	</tr>
	<tr>
		<td height="37" width="299" align="right">Enter User Name:</td>
		<td height="37" width="322"><input type="text" name="usn" size="32" ></td>
	</tr>
	<tr>
		<td height="36" width="299" align="right">Enter Old Password:</td>
		<td height="36" width="322"><input type="text" name="oldpsw" size="32"></td>
	</tr>
	<tr>
		<td height="37" width="299" align="right">Enter New Password:</td>
		<td height="37" width="322"><input type="text" name="newpsw" size="32"></td>
	</tr>
	<tr>
		<td height="37" width="299" align="right">Confirm Password:</td>
		<td height="37" width="322"><input type="text" name="confpsw" size="32"></td>
	</tr>
	<tr>
		<td height="39" width="299" align="right"><input type="submit" name="sub" value="Submit"></td>
		<td height="39" width="322"><input type="reset" name="clear" value="Clear"></td>
	</tr>
</table>
 
            </html:form>
  </body>
  </html>          