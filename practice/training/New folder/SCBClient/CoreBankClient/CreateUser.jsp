
<%@page import="masterObject.general.BranchObject"%>

<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Mohsin
  Date: Dec 8, 2008
  
  To change this template use File | Settings | File Templates.
--%>
<%@page import="masterObject.general.AccountObject"%>
<%@page import="com.scb.fc.forms.SBOpeningActionForm"%>

<html>
  <head><title>AccountOpening</title>
      <h1 style="font:small-caps;font-style:normal;color: blue" ><center>Create User</center></h1>
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
    <script type="text/javascript">

  
   function confirmsubmit(){
  
   
   
   }
   
   function onlyalpha()
	{
 		var cha =event.keyCode;
		if ( (cha >= 97 && cha <= 122 ) ) 
		{
			return true;
            		
        	} else {
        		
        	return false;
        	}
	};	
   
   function onlynumbers()
{
        	
        	var cha =   event.keyCode;

            if ( cha >= 48 && cha <= 57  ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        };
    
    function only_numbers()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57)||cha==47 ) 
		 {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};
	
	
    </script>
  </head>
  <body class="Mainbody">
    <%!BranchObject[] branchObjects=null;
    
    %>
    <%branchObjects=(BranchObject[])request.getAttribute("Branch");
     %>
    
    
     <html:form action="/Administrator/CreateUser?pageId=10001" focus="<%=(String)request.getAttribute("FocusTo") %>" >
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="defaultTab" value="PersonalDetails"/>
     	<html:hidden property="defSIndex" value="1"/>
     	<html:hidden property="pageId" value="3005"/>
     	<table width="726"><tr valign="middle" align="center"><td align="center">
	</td></tr></table>
<table border="0" width="383" height="435" align="center">

<tr>
	<td width="197" valign="top" height="29"> 

</td>
	<td width="170" height="29"> 
&nbsp;</td>
</tr>
	<tr>
		<td width="197" valign="top" height="35"> 
User ID:</td>
	<td width="170" height="35"> 
<input type="text" name="uid" size="18" ></td>
	</tr>
	<tr>
		<td width="197" valign="top" height="35"> 
Customer ID:</td>
	<td width="170" height="35"> 
<input type="text" name="cid" size="18" onblur="showCustomer(this.value)" onkeypress="return onlynumbers()"></td>
	</tr>
	<tr><td>Branch Name</td><td><html:select property="branch" style="font-family:bold;color:blue;">
									<html:option value="SELECT">SELECT</html:option>
									<%for(int i=0;i<branchObjects.length;i++){%>
									<html:option value="<%=""+branchObjects[i].getBranchCode()%>"><core:out value="<%=""+branchObjects[i].getBranchName()%>"></core:out> </html:option>
									<%}%>
									</html:select>
</td></tr>
	<tr>
		<td width="197" valign="top" height="34"> 
Short Name:</td>
	<td width="170" height="34"> 
<input type="text" name="sname" size="18" onkeypress="return onlyalpha()"></td>
	</tr>
	<tr>
		<td width="197" valign="top" height="36"> 
Enter Password:</td>
	<td width="170" height="36"> 
<input type="password" name="pwd" size="18"></td>
	</tr>
	<tr>
		<td width="197" valign="top" height="36"> 
Re-type Password:</td>
	<td width="170" height="36"> 
<input type="password" name="rpwd" size="18"></td>
	</tr>
	<tr>
		<td width="197" valign="top" height="36"> 
Password Expiry Period:</td>
	<td width="170" height="36"> 
<input type="text" name="pxp" size="18" onkeypress="return onlynumbers()"></td>
	</tr>
	<tr>
		<td width="197" valign="top" height="38"> 
Password Expiry Date:</td>
	<td width="170" height="38"> 
<input type="text" name="pxd" size="18" onkeypress="return only_numbers()"></td>
	</tr>
	<tr>
		<td width="197" valign="top" height="38"> 
Acc Operation From Date:</td>
	<td width="170" height="38"> 
<input type="text" name="acopfdate" size="18" onkeypress="return only_numbers()"></td>
	</tr>
	<tr>
		<td width="197" valign="top" height="36"> 
Acc Operation To Date:</td>
	<td width="170" height="36"> 
<input type="text" name="acoptdate" size="18" onkeypress="return only_numbers()"></td>
	</tr>
	<tr>
		<td width="197" valign="top"> 
<input type="radio" name="option" value="">Enable </td>
	<td width="170" valign="top"> 
<input type="radio" name="option" value="">Disable
 </td>
	</tr>
	<tr><td align="center">
	<html:submit onclick="confirmsubmit()"/>
	
	</td></tr>
</table>

     	
 
            </html:form>
  </body>
  </html>          