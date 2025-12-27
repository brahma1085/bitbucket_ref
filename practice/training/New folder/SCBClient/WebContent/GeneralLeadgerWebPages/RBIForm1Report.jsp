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
<head><title>Form 1 Report</title>
      <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" /> 
      <h2 class="h2">
      <center>Form1 Report</center></h2>
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
<%String result=(String)request.getAttribute("result"); 
String[] reportname=(String[])request.getAttribute("reportTable");
String[][] form1Reports=(String[][])request.getAttribute("RBIForm1ReportsTable");
String[] form1Friday=(String[])request.getAttribute("RBIForm1Fridays");
String formname=(String)request.getAttribute("RBIForm1ABC");
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
<html:form action="/GL/GLRBIForm1Reports?pageId=12028">
<%if(result!=null){ %>
<font color="blue"><%=result%></font>
<%} %>
 <table class="txtTable">
 <html:hidden property="forward"></html:hidden>
 <html:hidden property="validations"></html:hidden>
 <html:hidden property="flag"></html:hidden>
   <tr>
   <b>Report</b>  <html:select property="reportName" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue" onchange="setFlag('Reports')">
   <html:option value="Form1A">Form1 A</html:option>
   <html:option value="Form1B">Form1 B</html:option>
   <html:option value="Form1C">Form1 C</html:option>
   </html:select>
    <td><b>Year</b>    
      <html:select property="year" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
     <%
      for(int i=1980;i<=2030;i++){
      
      %>
      <html:option value="<%=""+i %>"><%=i %></html:option>
      <%}%>
      </html:select>
      <b>Month</b>    
      <html:select property="month" styleClass="formTextFieldWithoutTransparent" style="font-family:bold;color:blue">
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
        <html:button property="view"  onclick="setFlag('view'); " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.view"></bean:message> </html:button>
        <!--<html:submit  onclick="set('file')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.file"></bean:message> </html:submit>
        --><html:button  property="printFile" onclick="window.print() " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.print"></bean:message></html:button>
        <html:button property="clear"  onclick="setFlag('clear') " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message> </html:button>
     </td>
   </tr>
   
 </table>
 <%if(form1Reports!=null && form1Friday!=null){
	 if(form1Friday.length==1){%>
 <table border="1">
 <tr>
 <td align="center">Code</td>
 <td align="center">Name</td>
 <td align="center"><%=form1Friday[0] %></td>
 </tr>
 <%for(int l=0;l<form1Reports.length;l++){ %>
 <tr>
 <%for(int k=0;k<3;k++){ %>
 <%if(form1Reports[l][k]!=null){ %>
 <td align="center"><%=form1Reports[l][k] %></td>
 <%}else{ %>
 <td></td>
 <%} %>
 <%} %>
 </tr>
 <%} %>
 
 </table>
 <%}%>
 <%if(form1Friday.length==2){%>
 <table border="1">
 <tr>
 <td align="center">Code</td>
 <td align="center">Name</td>
 <td align="center"><%=form1Friday[0] %></td>
 <td align="center"><%=form1Friday[1] %></td>
 </tr>
 <%for(int l=0;l<form1Reports.length;l++){ %>
 <tr>
 <%for(int k=0;k<4;k++){ %>
 <%if(form1Reports[l][k]!=null){ %>
 <td align="center"><%=form1Reports[l][k] %></td>
 <%}else{ %>
 <td></td>
 <%} %>
 <%} %>
 </tr>
 <%} %>
 
 </table>
 <%}%>
 <%if(form1Friday.length==3){%>
 <table border="1">
 <tr>
 <td align="center">Code</td>
 <td align="center">Name</td>
 <td align="center"><%=form1Friday[0] %></td>
 <td align="center"><%=form1Friday[1] %></td>
 <td align="center"><%=form1Friday[2] %></td>
 </tr>
 <%for(int l=0;l<form1Reports.length;l++){ %>
 <tr>
 <%for(int k=0;k<5;k++){ %>
 <%if(form1Reports[l][k]!=null){ %>
 <td align="center"><%=form1Reports[l][k] %></td>
 <%}else{ %>
 <td></td>
 <%} %>
 <%} %>
 </tr>
 <%} %>
 
 </table>
 <%}%>
<%}%>
<%
if(form1Reports!=null){
if(formname.equalsIgnoreCase("Form1B") || formname.equalsIgnoreCase("Form1C")){
%>
<table border="1">
<tr>
<%
for(int i=0;i<reportname.length;i++){
 %>
   <td style="color: red;border:thin solid #339999;" class="formTextField"><b>
   <%=reportname[i].toString() %>
   </b>
   </td>
   <%}
 
 %>
</tr>
<%for(int l=0;l<form1Reports.length;l++){ %>
 <tr>
 <%for(int k=0;k<=6;k++){ %>
 <%if(form1Reports[l][k]!=null){ %>
 <td align="center"><%=form1Reports[l][k] %></td>
 <%}else{ %>
 <td></td>
 <%} %>
 <%} %>
 </tr>
 <%} %>
</table>
<%}
}%>
 
</html:form> 
<%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
</body>
</html>