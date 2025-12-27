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
   
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
       
      <script type="text/javascript">
      
      function func_clear()
{

    var v = document.forms[0].elements;
    for(var i=0;i<=v.length;i++)
    {
       if(v[i].type =="text")
       {
          v[i].value = "";

        }
     }
 
};

function only_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122) || (cha >= 65 && cha <=90) ) 
 		{
   			return true;
       	} else if(cha==36 || cha==47) 

       	{
   			return false;
       	}
       	else {
       	return false;
       	}
	};
	
      </script>
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
    	<%String disable; %>
<%disable=(String)request.getAttribute("disable"); %>
<%boolean flag=true; %>
<%if(disable!=null){ 
flag=true;
}else{
flag=false;
}
%>
    <% 
    	acountMaster=(AccountMasterObject)request.getAttribute("AccountDetails");
    acForm=(AccountCloseActionForm)request.getAttribute("Account");
   // SignatureInstructionObject sign[]=(SignatureInstructionObject[])request.getAttribute("signInst");
    System.out.println("Welcome to SignatureInst1.jsp");
    SignatureInstructionObject[] signObject=(SignatureInstructionObject[])request.getAttribute("sigObject");
   System.out.println(" FIRST BLOCK IN JSP NOT NULL========================"+signObject);
   
   FileOutputStream fos;
    %>
    
  <!-- /FrontCounter/SignatureInst?pageId=3012 -->
  <html:form  action="/Loans/SignatureInst?5053" focus="<%=(String)request.getAttribute("FocusTo") %>">
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
			fos=new FileOutputStream("//root//image.jpg");
				fos.write(b);
				fos.close();
			%>
			<html:img height="100" width="100" src="//root//image.jpg"/>
		<%}%>
			
		
		
		
		</td>
	</tr>
	<tr>
		<td height="39" width="95">Account Type: </td>
		<td height="39" width="154"><html:text property="actype" value="<%=signObject[0].getSAccType() %>" readonly="true"></html:text></td>
	</tr>
	<tr>
		<td height="36" width="95">Account No.:</td>
		<td height="36" width="154"><html:text property="acno" value="<%=String.valueOf(signObject[0].getSAccNo())%>" readonly="true"></html:text></td>
	</tr>
	<tr>
		<td height="39" width="95">Name:</td>
		<td height="39" width="154"><html:text property="name" value="<%=signObject[0].getName() %>" readonly="true"></html:text></td>
		<td height="76" width="101" rowspan="2" valign="top">Signature: 
		<%
		if(signObject[0].getSignature()!=null){
			byte[] b=signObject[0].getPhoto();
			fos=new FileOutputStream("//root//image1.jpg");
				fos.write(b);
				fos.close();
			%>
			<html:img height="100" width="100" src="//root//image1.jpg"/>
		<%}%>
		</td>
	</tr>
	<!--<tr>
		<td height="39" width="255" colspan="2">Type of Operation:
		<%if(signObject[0].getTypeOfOperation()!=null){ %>
		<html:text property="operationtype" value="<%=""+signObject[0].getTypeOfOperation()%>" size="40" onkeypress="return only_alpha()"></html:text>
		<%}else{%>
		<html:text property="operationtype" size="40" onkeypress="return only_alpha()"></html:text>
		<%} %>
		</td>
	</tr>
	--><tr>
	<%if(disable!=null){ %>
<td><input type="submit" value="SaveSignature" name="method" class="ButtonBackgroundImage" disabled="<%=flag %>" /></td>
<%}else{ %>
<td><input type="submit" value="SaveSignature" name="method" class="ButtonBackgroundImage" onclick="location.href='?method=SaveSignature'" /></td>
<%} %>
<!--<td><html:submit styleClass="ButtonBackgroundImage" onclick="func_clear()">Clear</html:submit></td>
    <td><html:submit styleClass="ButtonBackgroundImage">Update</html:submit></td>  
--></tr>
</table>
</core:if>

<core:if test="<%=signObject==null %>">
<font color="red"><%="Signature Details Not Available" %></font>
</core:if>
                </html:form>
                
  </body>
  </html>          