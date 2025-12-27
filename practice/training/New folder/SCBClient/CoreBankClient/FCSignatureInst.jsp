<%@ page import="java.io.*" %>
<%@ page import="masterObject.general.ModuleObject" %>
<%@ page import="masterObject.frontCounter.AccountMasterObject" %>
<%@ page import="masterObject.customer.CustomerMasterObject" %>
<%@page import="masterObject.frontCounter.ODCCMasterObject"%>

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
  <head><title>Signature</title>
      <font color="blue" >
      <center>
<h2 class="h2">Signature Instruction</h2>
</center>
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
    -->
    
    <link href="Images/DMTStyle.css" rel="stylesheet" type="text/css" />
    
    
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
   // System.out.println("Welcome to SignatureInst.jsp");
    SignatureInstructionObject[] signObject=(SignatureInstructionObject[])request.getAttribute("sigObject");
   //System.out.println(" FIRST BLOCK IN JSP NOT NULL========================"+signObject);
   CustomerMasterObject cust=(CustomerMasterObject)request.getAttribute("cust");
   FileOutputStream fos;
    %>
    
  <!-- /FrontCounter/SignatureInst?pageId=3012 -->
  <form>
     	<!--<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="defaultTab" value="PersonalDetails"/>
     	<html:hidden property="defSIndex" value="1"/>
     	<html:hidden property="pageId" />
     	--><div id="tabs">
     	</div>
     	
   
  
   <%
   if(signObject!=null){

   %>
   
	<table style="border:thin solid #000000;">
		<tr>
		<td>cid:</td>
		<td><input type="text" name="1" value="<%=String.valueOf(signObject[0].getCid()) %>" onkeypress="return false;"></td>
		<td height="115" width="101" rowspan="3" valign="top">Photo:
		
		<%
		String s1=String.valueOf(signObject[0].getCid());
		String n1="C:/JBoss-4.0.0/Photos/p"+s1+".jpg" ;
		
		try
		{
			System.out.println("to print photo");
			if(signObject[0].getPhoto()!=null){
				byte[] b=signObject[0].getPhoto();
				System.out.println("to print photo----------128--------");
				fos=new FileOutputStream("C:/JBoss-4.0.0/Photos/p"+s1+".jpg");
					fos.write(b);
					fos.close();
				%>
				
				<img src="<%=n1%>"  height="80" width="100">
			<%}
		}
		catch (Exception exc)
		{ System.out.println(exc);
		}
		
		%>
		
		
		</td>
	</tr>
	<tr>
		<td>Account Type: </td>
		<td><input type="text" name="2" value="<%=signObject[0].getSAccType() %>" onkeypress="return false;"></td>
	</tr>
	<tr>
		<td>Account No.:</td>
		<td><input type="text" name="3" value="<%=String.valueOf(signObject[0].getSAccNo())%>" onkeypress="return false;"></td>
	</tr>
	<tr>
		<td>Name:</td>
		<td><input type="text" name="4" value="<%=signObject[0].getName() %>" onkeypress="return false;"></td>
		<td height="76" width="101" rowspan="2" valign="top">Signature: 
		<%	
		try
		{ 
		String s=String.valueOf(signObject[0].getCid());
		String n="C:/JBoss-4.0.0/Photos/s"+s+".jpg" ;

			System.out.println("to print photo");
			if(signObject[0].getSignature()!=null){ 
				byte[] b=signObject[0].getSignature();
				System.out.println("to print photo----------128--------");
				fos=new FileOutputStream("../Photos/s"+s+".jpg");
					fos.write(b);
					fos.close();
					
				System.out.println("--value n-->"+n);	
				%>
				
				<img src="<%=n%>"  height="80" width="100">
			<%}
		}
		catch(Exception exc)
		{ 
			System.out.println(exc);
		}
		
		
		%>
		</td>
	</tr>
	<tr>
		<td  colspan="2">Type of Operation:<input type="text" name="5" size="5" value="<%=signObject[0].getTypeOfOperation()%>" onkeypress="return false;"></td>
	</tr>
	<tr>
	
	<td>
	
	
	
	</td>
	</tr>
</table>
<%} %>

<%if(signObject==null){%>
<font color="red"><%="Signature Details Not Available" %></font>
<%} %>
                </form>
                
  </body>
  </html>          