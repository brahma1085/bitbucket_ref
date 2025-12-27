<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>Daily Posting</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>Year Closing</center></h2>
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
     }; 
    </script>

 </head>
  
<body class="Mainbody" onload="return validate()">
<%String result=(String)request.getAttribute("result"); %>
<html:form action="/GL/GLBranchPLPosting?pageId=12007">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
   <tr>
    <td>   
    Year    
      <html:text property="year" value="<%=""+request.getAttribute("year") %>" size="4"  styleClass="formTextFieldWithoutTransparent"></html:text>
    
    &nbsp;&nbsp;&nbsp;&nbsp;Month    
      <html:text property="month"  size="2" onchange="submit()" styleClass="formTextFieldWithoutTransparent"></html:text>
    </td>
   </tr>
   
   <tr>
     <td>
        <html:submit  onclick="set('submit'); " styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message> </html:submit>&nbsp;&nbsp;
        
        <html:submit  onclick="set('post'); " styleClass="ButtonBackgroundImage"><bean:message key="label.post"></bean:message> </html:submit>&nbsp;&nbsp;
        <html:submit  onclick="set('close'); " styleClass="ButtonBackgroundImage"><bean:message key="label.closeyear"></bean:message> </html:submit>&nbsp;&nbsp;
        <html:submit  onclick="clear_fun()" styleClass="ButtonBackgroundImage"><bean:message key="label.cancel"></bean:message> </html:submit>
     </td>
   </tr>
 </table>
</html:form>
</body>
</html>