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
      <center>Consolidate Day Book</center></h2>
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
<html:form action="/GL/GLConsolidatedDayBook?pageId=12017">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
   <tr>
    <td><b>Date</b>    
      <html:text property="date" value="<%=""+request.getAttribute("date") %>" size="10" onchange="submit()" styleClass="formTextFieldWithoutTransparent"></html:text>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           
    </td>
    
   </tr>
   <tr>
   <td>
    <html:radio property="brConsolidation" value="branchConsolidation"></html:radio><b>Branch Consolidation</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <html:radio property="conDayBook" value="consolidatedDayBook"></html:radio><b>consolidated Day Book</b>  
   </td>
   </tr>
   <tr>
   <td>
    <html:radio property="dayBook" value="DayBook"></html:radio><b>Day Book</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <html:radio property="trialBalance" value="TrialBalance"></html:radio><b>Trial Balance</b>  
   </td>
   </tr>
   
   
   <tr>
     <td>
        <html:submit  onclick="set('submit'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.view"></bean:message> </html:submit>
        <html:submit  onclick="set('file')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.file"></bean:message> </html:submit>
        <html:submit  onclick="set('print'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.print"></bean:message> </html:submit>
        <html:submit  onclick="clear_fun(); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message> </html:submit>
     </td>
   </tr>
   
 </table>
 
 
</html:form>
</body>
</html>