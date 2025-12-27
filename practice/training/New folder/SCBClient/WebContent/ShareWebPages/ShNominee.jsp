<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="masterObject.customer.CustomerMasterObject"%>
<%@page import="masterObject.general.ModuleObject"%>
<html>
<head><title>Share Allotment</title>
  <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <h2 class="h2">
      <center>Nominee</center></h2>
      <hr  color="#339999">
      
    
    <script type="text/javascript">
     function set(target){
    
        
        document.forms[0].forward.value=target;
       
        
       
     };
     
     function clearfun(){
       var v=document.forms[0].elements;
       for(var i=0;i<v.length;i++){
         if(v[i].type=="text"){
            v[i].value="";
         }
       }
     };
     
     
     
     function checkpercentage(){
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
<%!
    CustomerMasterObject cusobj;
    Integer shnum=0;
    ModuleObject[] mod_obj=null;
%>
<%
   System.out.println("Inside nominee page");
  cusobj=(CustomerMasterObject)request.getAttribute("custdetails");
  shnum=(Integer)request.getAttribute("shnum");
  mod_obj=(ModuleObject[])request.getAttribute("AllAc_types");
  System.out.println("Inside nominee page111");
%>
<body class="Mainbody" style="overflow: scroll;" onload="return checkpercentage()">
<html:form action="/Share/Nominee?pageId=4041">
  <table class="txtTable">
  <html:hidden property="forward"></html:hidden>
  <html:hidden property="validations"></html:hidden>
   
   <tr>
    <td>Has Account</td>
    <td><html:checkbox property="has_ac" styleClass="formTextFieldWithoutTransparent" onchange="submit()"></html:checkbox> </td>
   </tr>
   
   <tr style="visibility: <%=""+request.getAttribute("vis") %>">
    <td><bean:message key="label.custid"></bean:message></td>
    <td><html:text property="cid" styleClass="formTextFieldWithoutTransparent" onchange="submit()"></html:text></td>
   </tr>
           
   <tr>
    <td><bean:message key="label.name"></bean:message></td>
    <%if(cusobj!=null){ %> 
    <td><html:text property="name" value="<%=""+cusobj.getName() %>" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
    <%}else{ %>
    <td><html:text property="name"  styleClass="formTextFieldWithoutTransparent"></html:text> </td>
    <%} %>
   </tr>
   
   <tr>
     <td><bean:message key="label.dob"></bean:message> </td>
     <%if(cusobj!=null){ %>
     <td><html:text property="dob" value="<%=""+cusobj.getDOB() %>" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
    <%}else{
    %>
     <td><html:text property="dob" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
     <%} %>
   </tr>
   
   <tr>
    <td><bean:message key="label.gender"></bean:message></td>
    <%if(cusobj!=null){ %>
    <td><html:text property="gender" value="<%=""+cusobj.getSex() %>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
    <%}else{ %>
    <td><html:text property="gender" styleClass="formTextFieldWithoutTransparent"></html:text></td>
    <%} %>
   </tr>
   
   <tr>
    <td><bean:message key="label.address"></bean:message></td>
      <%if(cusobj!=null){ %>
    <td><html:textarea property="address" value="<%=""+cusobj.getAddressProof()%>" styleClass="formTextFieldWithoutTransparent"></html:textarea></td>
        <%}else{ %>
    <td><html:textarea property="address" styleClass="formTextFieldWithoutTransparent"></html:textarea></td>
     <%} %>
   </tr>
   
   <tr>
     <td><bean:message key="label.rel"></bean:message></td>
     <td><html:text property="rel_ship" styleClass="formTextFieldWithoutTransparent"></html:text></td>
   </tr>

   <tr>
    <td><bean:message key="label.percentage"></bean:message></td>
    <td><html:text property="percentage" styleClass="formTextFieldWithoutTransparent" value="0"></html:text></td>
   </tr>
      
   <!--<tr>
    <td>
     <html:submit  onclick="set('submit'); " styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message> </html:submit>
   
   </td>
    <td align="left">
    <html:reset styleClass="ButtonBackgroundImage"></html:reset>
    </td> 
   </tr>
  --></table>
</html:form>
</body>
</html>