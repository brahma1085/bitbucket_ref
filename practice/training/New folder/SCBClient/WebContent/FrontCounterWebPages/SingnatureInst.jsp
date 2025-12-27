<%@ page import="java.io.*" %>
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@page import="masterObject.frontCounter.ODCCMasterObject"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page language="java" session="true" 
import= "
java.awt.image.BufferedImage,java.io.ByteArrayOutputStream,java.io.InputStream,java.sql.Blob,java.sql.Connection,
java.sql.ResultSet,javax.swing.ImageIcon,java.sql.*,java.io.*" %>
<%--
  User: Mohsin
  Date: Dec 27, 2008
  
  To change this template use File | Settings | File Templates.
--%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>

<%@page import="com.scb.fc.forms.AccountCloseActionForm"%>
<%@page import="masterObject.general.SignatureInstructionObject"%>
<%@page import="javax.imageio.stream.ImageOutputStream"%>
<html>
  <head><title>AccountOpening</title>
      <font color="blue" ><center>Signature Instruction</center>
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
    
    
  </head>
  <body class="Mainbody">
    <%!
        ModuleObject[] array_module;
        String details[];
        AccountMasterObject acountMaster;
        String jspPath;
        ModuleObject[] mod;
        CustomerMasterObject cmObject;
         AccountCloseActionForm acForm;
         int size;
         byte[] bytea= new byte[4096];
         InputStream image;
         
    %>
    <% 
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    acForm=(AccountCloseActionForm)request.getAttribute("Account");
   // SignatureInstructionObject sign[]=(SignatureInstructionObject[])request.getAttribute("signInst");
    System.out.println("Welcome to SignatureInst.jsp");
    SignatureInstructionObject[] signObject=(SignatureInstructionObject[])request.getAttribute("sigObject");
   System.out.println(" FIRST BLOCK IN JSP NOT NULL========================"+signObject);
   
   FileOutputStream fos;
    %>
    
  <!-- /FrontCounter/SignatureInst?pageId=3012 -->
  <html:form  action="/FrontCounter/Signature?3012" focus="<%=(String)request.getAttribute("FocusTo") %>">
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="defaultTab" value="PersonalDetails"/>
     	<html:hidden property="defSIndex" value="1"/>
     	<html:hidden property="pageId" />
     	<div id="tabs">
     	</div>
     	<br><br><br>
   
   <core:if test="<%=signObject!=null %>">
   <%
   

   
   %>
<table border="1" width="372" height="244" >
	<tr>
		<td height="35" width="362" colspan="3" align="center"><b>
		<font size="4">Signature Instructions</font></b></td>
	</tr>
	<tr>
		<td height="39" width="95">cid:</td>
		<td height="39" width="154"><html:text property="cid" value="<%=String.valueOf(signObject[0].getCid()) %>"></html:text></td>
		<td height="115" width="101" rowspan="3" valign="top">Photo:
		<%
		if(signObject[0].getPhoto()!=null){
			byte[] b=signObject[0].getPhoto();
			fos=new FileOutputStream("C://image.jpg");
				fos.write(b);
				fos.close();
			%>
			<html:img height="100" width="100" src="C://image.jpg"/>
		<%}%>
			
		
		
		
		</td>
	</tr>
	<tr>
		<td height="39" width="95">Account Type: </td>
		<td height="39" width="154"><html:text property="actype" value="<%=signObject[0].getSAccType() %>"></html:text></td>
	</tr>
	<tr>
		<td height="36" width="95">Account No.:</td>
		<td height="36" width="154"><html:text property="acno" value="<%=String.valueOf(signObject[0].getSAccNo())%>"></html:text></td>
	</tr>
	<tr>
		<td height="39" width="95">Name:</td>
		<td height="39" width="154"><html:text property="name" value="<%=signObject[0].getName() %>"></html:text></td>
		<td height="76" width="101" rowspan="2" valign="top">Signature: 
		<%=signObject[0].getSignature()%>
		</td>
	</tr>
	<tr>
		<td height="39" width="255" colspan="2">Type of Operation:<html:text property="operationtype" size="40"></html:text></td>
	</tr>
</table>
</core:if>

<core:if test="<%=signObject==null %>">
<font color="red"><%="Signature Details Not Available" %></font>
</core:if>
                </html:form>
                
  </body>
  </html>          