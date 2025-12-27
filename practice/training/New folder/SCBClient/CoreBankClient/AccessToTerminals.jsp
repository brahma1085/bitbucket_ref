

<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Mohsin
  Date: Feb 14, 2009
  
  To change this template use File | Settings | File Templates.
--%>

<%@page import="masterObject.administrator.TerminalObject"%>
<html>
  <head><title>Role Access Admin</title>
      <center>
<h2 class="h2">User Access Admin</h2></center>
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
      var val=document.forms[0].validations.value;
      if(val!=0 || val!=""){
         alert(val);
       } 
       
       else{
         return false;
       }
     var ele=document.forms[0].elements;
    for(var i=0;i<ele.length;i++){
    
    if(ele[i].type=="hidden")
    {
    ele[i].value="";
    
    } 
     
   }
     }; 
     
    
    </script>
  </head>
  <body class="Mainbody" onload="return validate()">
    
    
    
     <html:form action="/Administrator/AccessToTerminals?pageId=10005">
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="validations" />
     	<html:hidden property="flag"/>
     	<html:hidden property="pageId" value="3005"/>
     	
  <%!
    String userId=null;
    String[] hasAccess=null;
    %>
    <%
    int c;
    hasAccess=(String[])request.getAttribute("tml_ids");
    String[][] tmlobj=(String[][])request.getAttribute("UserTmlObj");
    if(tmlobj!=null){
    System.out.println("==KKK===in jsp lenght of the array is===="+tmlobj.length);
    for(int i=0;i<tmlobj.length;i++){
    	System.out.println("=====in jsp contents of the array is===="+tmlobj[i][0]);
    	System.out.println("=====in jsp contents of the array is===="+tmlobj[i][1]);
    }
    }
    %>
    
    
     	<table background="#fffccc" align="center">
     	<tr>
     	 <td align="center" style="font-family:bold;color:blue">
     	  User Id:
     	  </td>
     	  <td align="center" style="font-family:bold;color:blue">
     	  <html:select property="uid" styleClass="formTextFieldWithoutTransparent" onchange="setFlag('from_uid')">
     	  <html:option value="All" style="color:blue">All</html:option>
     	  <core:forEach var="userid" varStatus="count" items="${requestScope.UserIds}" >
             <html:option value="${userid}" style="color:blue"><core:out value="${userid}"></core:out></html:option>
          </core:forEach>
     	  </html:select>
     	  </td>
     	  
     	</tr>
     	
     	</table>
     	
     	
    
    
   <br>
  <% if(tmlobj!=null){%>
    <table align="center" >
    <tr>
     	<td><html:button  property="printFile" onclick="setFlag('submit')" styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message></html:button></td>
        
        </tr>
    </table>
     <center>
     <div id="viewusers" style="display:block;overflow:scroll;width:300px;height:250px">
    <table  border="1">
    <tr>
    <td style=background-color:#fffccc;font-family:bold;color:yellow">
    <b>&nbsp;</b>
    </td>
    <td style=background-color:#fffccc;font-family:bold;color:yellow" align="center">
    <b>Terminal Name</b>
    </td>
    <td style=background-color:#fffccc;font-family:bold;color:yellow" align="center">
    <b>Terminal Description</b>
    </td>
    </tr>
    
    
         
    <%
    if(tmlobj!=null){
    for(int a=0;a<tmlobj.length;a++) {
    	System.out.println("=====in jsp contents of the array is===="+tmlobj[a][0]);
    	System.out.println("=====in jsp contents of the array is===="+tmlobj[a][1]);
    %>
    <tr>
    <td>
   <input type="checkbox" name="chbox" value="<%=""+a %>">
    
    
    </td>
    <td> 
    <input type="text" name="tmlname" value="<%=tmlobj[a][0]%>" onkeypress="return false;">
    </td>
    <td> <html:text property="tmlDesc" size="15" value="<%=tmlobj[a][1]%>" onkeypress="return false;"></html:text>  </td>
    </tr>
    
    <%} 
    }%>
    <tr>
    <td>
              
    </table>
    <%} %>
    </div>
    </center>
    <br><br>
    
    
    <center>
     <div id="viewusers" style="display:block;overflow:scroll;width:300px;height:250px">
    
     <%if(hasAccess!=null){ %>
     
     <center>Already has Access to Terminals</center>
    <table  border="1">
    <tr>
    <td style=background-color:#fffccc;font-family:bold;color:yellow">
    <b>&nbsp;</b>
    </td>
    <td style=background-color:#fffccc;font-family:bold;color:yellow" align="center">
    <b>Terminal Name</b>
    </td>
    
    </tr>
    <%if(hasAccess!=null){ 
    	for(int k=0;k<hasAccess.length;k++){ 
    %>
    <tr>
    <td>
   
   <input type="Checkbox" name="cbx" value="<%=""+k %>" checked="checked"> 
    
    </td>
    <td><input type="text" name="txt1" value="<%=hasAccess[k] %>" onkeypress="return false;"></td>
  
    </tr>
    
    
    <%}} %>
    </table>
    <%} %>
    </div>
    </center>
  </html:form>
  </body>
  </html>          