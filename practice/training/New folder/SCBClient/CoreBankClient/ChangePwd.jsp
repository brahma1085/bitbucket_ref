

<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@page import="java.util.Map"%>
<%--
  
  
  To change this template use File | Settings | File Templates.
--%>

<html>
  <head><title>ChangePassword</title>
    <center>
<h2 class="h2">Change Password</h2></center>
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
   
   function setFlag(flagValue){
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     
     
     }
     
     function validate(){
      var ele=document.forms[0].elements;
      var val=document.forms[0].validations.value;
      if(val!=0 || val!=""){
         alert(val);
       } 
       
       else{
        for(var i=0;i<ele.length;i++){
       if(ele[i].type=="hidden"){
       ele[i].value="";
       }
       }
         return false;
       }
       
      
       for(var i=0;i<ele.length;i++){
       if(ele[i].type=="hidden"){
       ele[i].value="";
       }
       }
     }; 
     function clearFunc()
    {
    	
    	var ele=document.forms[0].elements;
    	  
    	for(var i=0;i<ele.length;i++)
    	{
    		if(ele[i].type=="text")
    		{ 
    			ele[i].value="";
    		}
    		if(ele[i].type=="password")
    		{ 
    			ele[i].value="";
    		}
    		if(ele[i].type=="hidden"){
       ele[i].value="";
       }
    	}
    
    };
    
    function nameLimit()
   {
 	if(document.forms[0].name.value.length<=15)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].name.value="";
 	document.forms[0].name.focus;
 	alert("Enter not more than 15 characters");
 	return false;
 	}
 	}
 	
 	function oldPwd_Limit()
 	{
 	if(document.forms[0].oldPwd.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].oldPwd.value="";
 	document.forms[0].oldPwd.focus;
 	alert("Enter not more than 10 characters");
 	return false;
 	}
 	}
 	
 	function newPwd_Limit()
 	{
 	if(document.forms[0].newPwd.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].newPwd.value="";
 	document.forms[0].newPwd.focus;
 	alert("Enter not more than 10 characters");
 	return false;
 	}
 	}
 	
 	function confirmPwd_Limit()
 	{
 	if(document.forms[0].confirmPwd.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].confirmPwd.value="";
 	document.forms[0].confirmPwd.focus;
 	alert("Enter not more than 10 characters");
 	return false;
 	}
 	}
 	
 	function onlyalpha_num()
{
        	
        	var cha =   event.keyCode;

            if (( cha >= 48 && cha <= 57  )||(cha >= 97 && cha <= 122 )) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
        };
 	
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
     
    function onlypwdchar()
    {
    var cha = event.keyCode;

            if ( (cha >= 48 && cha <= 57 )  || (cha >= 97 && cha <= 122 ) ) {
            
            		return true;
            		
        	} else {
        		
        			return false;
        	}
			        
    };
    
    </script>
  </head>
  <body class="Mainbody" onload="return validate()">
    <%!
    Map user_role;
    String access;
    
    %>
    <%
    String accesspageId=(String)request.getAttribute("accesspageId");
	user_role=(Map)request.getAttribute("user_role");
	
	
	if(user_role!=null&&accesspageId!=null){
		if(user_role.get(accesspageId)!=null){
			access=(String)user_role.get(accesspageId);
			System.out.println("access-----In SBOpening------>"+access);
		}

		
		}
    
    %>
     <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
     <html:form action="/Administrator/ChangePwd?pageId=10009">
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="validations" />
     	<html:hidden property="flag"/>
     	<html:hidden property="pageId" value="3005"/>
     	
     	   <table background="#fffccc" align="center">
     	
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	  Enter User Name:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:text property="name" onblur="setFlag('username')" onkeypress="return onlyalpha_num()" onkeydown="return nameLimit()"></html:text>
     	 </td>
     	</tr> 
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	  Enter Old Password:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:password property="oldPwd" onblur="setFlag('oldpwd')" onkeypress="return onlypwdchar()" onkeydown="return oldPwd_Limit()"></html:password>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	  Enter New Password:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:password property="newPwd" onkeydown="return newPwd_Limit()" onkeypress="return onlypwdchar()"></html:password>
     	 </td>
     	</tr>
     	<tr>
     	<td align="center" style="font-family:bold;color:blue">
     	  Confirm Password:
     	</td>
     	 <td align="center" style="font-family:bold;color:blue">
     	  <html:password property="confirmPwd" onblur="setFlag('from_pwd')" onkeydown="return confirmPwd_Limit()" onkeypress="return onlypwdchar()"></html:password>
     	 </td>
     	</tr>
     	</table>
    
     	  
     	   	
    <br>	
 	<table name="button_table" align="center">
     <tr>	
     	
        <td>
        <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
        <html:button  property="sub" onclick="setFlag('submit')" styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message> </html:button>
        <%}else{ %>
        <html:button  property="sub" onclick="setFlag('submit')" styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="label.submit"></bean:message> </html:button>
        <%} %>
        </td>
        <td>&nbsp;</td>
        <td><html:button  property="but2" onclick="clearFunc()" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message> </html:button></td>
     </tr>
     	</table>
    
               </html:form>													
 	
     	   
     	   
     	   <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
     	   
     	 	
     	
  </body>
  </html>          