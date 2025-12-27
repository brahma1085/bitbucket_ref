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
<head><title>Daily Posting</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>Monthly Posting</center></h2>
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
      function setFlag(flagValue){
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     
     
     }
     
     
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
<%String result=(String)request.getAttribute("result");
String buttonStatus=(String)request.getAttribute("MonthPosting");
String msg=(String)request.getAttribute("msg");
%>

<%if(msg!=null){ %>
  <font color="red"><%=msg %></font>
  <br><br>
<%} %>
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
<html:form action="/GL/GLMonthlyPosting?pageId=12003">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
 <html:hidden property="flag"></html:hidden>
   <tr>
    <td>Year    
      <html:select property="year" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
      <%
      for(int i=1980;i<=2030;i++){
      
      %>
      <html:option value="<%=""+i %>"><%=i %></html:option>
      <%}%>
      </html:select>
    
    &nbsp;&nbsp;&nbsp;&nbsp;Month    
      <html:select property="month" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue" onblur="setFlag('frm_date')" >
      <%
      for(int i=01;i<=12;i++){
		   
		   %>	
		    <html:option value="<%=""+i %>"> <%=i%></html:option>
		
		<%
		    }
		%>
      
      </html:select>
    </td>
   </tr>
   
   <tr>
     <td>
     <%
     if(buttonStatus!=null){
    	 if(buttonStatus.equalsIgnoreCase("NoPosting")){
     
     %>
        <html:button property="sub" disabled="true" styleClass="ButtonBackgroundImage"><bean:message key="label.post"></bean:message> </html:button>
        <%}else{%>
        
<%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
        <html:button property="sub" onclick="setFlag('submit'); " styleClass="ButtonBackgroundImage"><bean:message key="label.post"></bean:message> </html:button>
        <%}else{ %>
        <html:button property="sub" onclick="setFlag('submit'); " styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="label.post"></bean:message> </html:button>
        <%} %>
    	 <%}
    	 }%>
        <html:button  property="clr" onclick="clear_fun()" styleClass="ButtonBackgroundImage"><bean:message key="label.cancel"></bean:message> </html:button>
     </td>
   </tr>
 </table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>