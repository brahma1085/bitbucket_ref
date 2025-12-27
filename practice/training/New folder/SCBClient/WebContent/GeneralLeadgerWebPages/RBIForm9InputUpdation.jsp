<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld" %>
<%@page import="java.util.Map"%>
<%--
  User: Amzad
  Date: Feb 17, 2009
  
  To change this template use File | Settings | File Templates.
--%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>RBI Form IX Input Updation</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>RBI Form IX Input Updation</center></h2>
      <hr>
      
    
    <script type="text/javascript">
     function set(target){
       document.forms[0].forward.value=target
      };
     
      function check_acnt(){
      
        if(document.getElementById("ac").value=='T'){
          alert("Account not found")
           document.forms[0].acno.value="";
        }
      };
        
   function clear_fun()
    {
    	
    	var ele=document.forms[0].elements;
    	document.getElementById("forward").style.display='none';  
    	for(var i=0;i<ele.length;i++)
    	{
    		if(ele[i].type=="text")
    		{ 
    			ele[i].value="";
    		}
    	}
    
    };
    
    function numbersonly(eve){
         var cha = event.keyCode
            if (  ( cha  > 47 && cha < 58  ) ) {
                return true ;
                
            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
      };
     
     
     function validate(){
      var val=document.forms[0].validations.value;
      if(val!=0 || val!=""){
         alert(val);
       } 
       
       else{
         return false;
       }
     } 
     
     function setFlag(flagValue){
    
     
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     
     }
    </script>

 </head>
  
<body class="Mainbody" onload="return validate()">
<%String result=(String)request.getAttribute("result");
String insertValue=(String)request.getAttribute("form9inputcodesinsert");
String[][] form9inputCodes=(String[][])request.getAttribute("form9inputcodes");
%>
<%!
String access;
String  accesspageId;
 Map user_role;

%>

<%

  accesspageId=(String)request.getAttribute("accesspageId");
    user_role=(Map)request.getAttribute("user_role");

if(user_role!=null&&accesspageId!=null){
			if(user_role.get(accesspageId)!=null){
				access=(String)user_role.get(accesspageId);
				System.out.println("access-----In SBOpening------>"+access);
			}

			
			}

// Note:access=1111==>read,insert,delete,update
%>
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
<html:form action="/GL/GLRBIForm9InputUpdation?pageId=12031">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
<html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
 <html:hidden property="flag"/>
 <table class="txtTable">
 
   
   <tr>
     <td>
     <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
        <html:submit  onclick="setFlag('insert') " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.insert"></bean:message> </html:submit> 
       <html:submit onclick="setFlag('submit')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.submit"></bean:message> </html:submit>
       <%}else{ %>
       <html:submit  onclick="setFlag('insert') " styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="gl.label.insert"></bean:message> </html:submit> 
       <html:submit onclick="setFlag('submit')" styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="gl.label.submit"></bean:message> </html:submit>
       <%} %>
        <html:submit onclick="setFlag('clear') " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message></html:submit>
        <html:submit  onclick="setFlag('exit') " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.exit"></bean:message> </html:submit>
     </td>
   </tr>
   
 </table>
 <table border="1" bordercolor="blue">
 <tr> <td  align="center"  ><b>Code</b></td><td align="center"  ><b>Description</b></td> </tr>
 <%if(insertValue!=null&&insertValue.equals("insert")){ %>
<b>Select Code here and Press Tab</b><html:select property="code" styleClass="formTextFieldWithoutTransparent" onblur="setFlag('code')">
 <% 
 if(form9inputCodes!=null){
 for(int i=0;i<form9inputCodes.length;i++){
	 
	 %>
 <html:option value="<%=form9inputCodes[i][0] %>"><%=form9inputCodes[i][0]%></html:option>
 <%}}%>
 </html:select>

 
 
 <tr>
 
 
 <td   ><html:text property="description" style="font-family:bold;color:red" styleClass="formTextField"></html:text>
 
 </td> 
 <td ><html:text property="description1" style="font-family:bold;color:red" styleClass="formTextField" size="35"></html:text></td>
 
 </tr>
 </table>
 	
 <%} %>

 
 
 
</html:form>
 <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>