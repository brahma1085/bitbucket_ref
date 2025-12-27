<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.Map"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld" %>
<%--
  User: Amzad
  Date: Feb 17, 2009
  
  To change this template use File | Settings | File Templates.
--%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>RBI Form I & IX Admin </title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>RBI Form I & IX Admin </center></h2>
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
     
     function setFlag(flagValue){
     document.forms[0].flag.value=flagValue;
     document.forms[0].submit();
     
     
     }
    </script>

 </head>
  
<body class="Mainbody" onload="return validate()">
<%!
String[] form19master=null;
String[] form19masterInsert=null;
String[][] tabledata=null;
int tablelength=0;

%>
<%String result=(String)request.getAttribute("result");
form19masterInsert=(String[])request.getAttribute("form19masterInsert");
 form19master=(String[])request.getAttribute("form19master");
 tabledata=(String[][])request.getAttribute("displaytabledata");
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
<html:form action="/GL/GLRBIForm1Master?pageId=12025">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
 <html:hidden property="flag"></html:hidden>
   <tr>
     <td>
      <html:select property="tableName" styleClass="formTextField" onblur="setFlag('from_tablename')">
      <html:option value="RBIForm1Head">RBIForm1Head</html:option>
      <html:option value="RBIForm1Link">RBIForm1Link</html:option>
      <html:option value="Form9Master">Form9Master</html:option>
      <html:option value="Form9Link">Form9Link</html:option>
      </html:select>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <html:button property="refresh"   onclick="setFlag('refresh') " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.fetch"></bean:message></html:button>  
     </td>
   </tr>
   
   
   <tr>
     <td>
     <%if(access!=null&&accesspageId!=null&&access.toCharArray()[1]=='1'){%>
        <html:button property="insertForm" onclick="setFlag('insert') " styleClass="ButtonBackgroundImage"><bean:message key="label.add.row"></bean:message> </html:button>
        <!--<html:submit  onclick="setFlag('update') " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.update"></bean:message> </html:submit>
        <html:submit  onclick="setFlag('delete') " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.delete"></bean:message> </html:submit>
        --><html:button property="submitForm"   onclick="setFlag('submit') " styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message> </html:button>
        <%}else{ %>
        <html:button property="insertForm" onclick="setFlag('insert') " styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="label.add.row"></bean:message> </html:button>
        <html:button property="submitForm"   onclick="setFlag('submit') " styleClass="ButtonBackgroundImage" disabled="true"><bean:message key="label.submit"></bean:message> </html:button>
        <%} %>
        <!--<html:button property="next"   onclick="setFlag('next') " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.next"></bean:message> </html:button>
        --><html:button property="exitForm"   onclick="setFlag('exit') " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.exit"></bean:message></html:button>
     </td>
   </tr>
   
 </table>
 
 <table border="1" bordercolor="blue">
 <tr>
 <%
 if(form19master!=null){
 for(int i=0;i<form19master.length;i++){
 %>
   <td><b>
   <%=form19master[i].toString() %>
   </b>
   </td>
   <%}
 }
 %>
 </tr>
 <%
 if(tabledata!=null ){
	 for(int j=0;j<tabledata.length;j++){%><tr>
	 <%for(int k=0;k<form19master.length-1;k++){	 
%>
 
 
 <td><%=tabledata[j][k] %>
 </td>
 <%} %>
 <td><input type="checkbox" name="cbox">
 </td>
 
 
 <%}%>
 </tr><%
	 }
	 %>
 </table>
 <%
 if(form19masterInsert!=null){
 %>
 <table style="border:thin solid #339999;" border="1">
<tr>
<%
for(int i=0;i<form19masterInsert.length;i++){
 %>
   <td style="color: red;" class="formTextField"><b>
   <%=form19masterInsert[i].toString() %>
   </b>
   </td>
   <%}
 
 %>
 </tr>
 <tr>
<%
for(int i=0;i<form19masterInsert.length-1;i++){
 %>
   <td ><b>
   <input type="text"  name="txtFld">
   </b>
   </td>
    
   <%}%>
   <td ><input type="checkbox" name="cbox" checked="checked">
 </td>
 <%
 }
 %>
 </tr>
 </table>
</html:form>
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>