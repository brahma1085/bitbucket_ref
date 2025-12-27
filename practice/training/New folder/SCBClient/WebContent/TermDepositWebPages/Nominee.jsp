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
     function totalsubmit()
     {
     if(document.forms[0].has_ac.checked)
     {
     	document.getElementById("tom").style.display='block';
     }
    }
     
     
     
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
    String cidcore;
    
    ModuleObject[] mod_obj=null;
%>
<%
  cusobj=(CustomerMasterObject)request.getAttribute("custdetails");
	
  shnum=(Integer)request.getAttribute("shnum");
  mod_obj=(ModuleObject[])request.getAttribute("Dep type");
  cidcore=(String)request.getAttribute("cid_psnt");
  System.out.println("value===============>"+cidcore);
%>
<body class="Mainbody" style="overflow: scroll;" onload="return checkpercentage()">
<html:form action="/Term/Nominee?pageId=4032">
  <table class="txtTable">
  <html:hidden property="forward"></html:hidden>
  <html:hidden property="validations"></html:hidden>
   <tr>
   
    <td><bean:message key="label.actype"></bean:message></td>
    
     <td><html:select property="actype" styleClass="formTextFieldWithoutTransparent">
      <%
        if(mod_obj!=null){
        	for(int i=0;i<mod_obj.length;i++){
      %>
      <html:option value="<%=""+mod_obj[i].getModuleCode() %>"><%=""+mod_obj[i].getModuleAbbrv()%></html:option>
      <%}}else{ %>
      <html:option value="SELECT">SELECT</html:option>
      <%} %>
    </html:select></td>
    
    <td><bean:message key="label.acno"></bean:message></td>
      <%if(shnum!=null){ %>
    <td><html:text property="acno" value="<%=""+shnum %>" styleClass="formTextFieldWithoutTransparent" readonly="true"></html:text></td>
    <%}else{ %>
    <td><html:text property="acno" styleClass="formTextFieldWithoutTransparent"></html:text></td>
    <%} %>
   </tr>
   
   <tr>
    <td>Has Account</td>
    <td><html:select property="has_ac" styleClass="formTextFieldWithoutTransparent" onblur="submit()">
    	<html:option value="Yes">Yes</html:option>
    	<html:option value="No">No</html:option>
    </html:select> </td>
    
   </tr>

    <tr> 
   <%if(cidcore!=null){ %>
   <%System.out.println("Hiiiiiiiiiiiiiiiiiiiiii"); %>
   <td> <bean:message key="label.custid"></bean:message></td>   
   <td><html:text property="cid" styleClass="formTextFieldWithoutTransparent" disabled="false" onchange="submit()"></html:text></td>
   <%}else if(cusobj!=null){%>
   <td> <bean:message key="label.custid"></bean:message></td>   
   <td><html:text property="cid" styleClass="formTextFieldWithoutTransparent" value="<%= ""+cusobj.getCustomerID() %>" disabled="false" onchange="submit()"></html:text></td>
   <%}else { %>
   <td> <bean:message key="label.custid"></bean:message></td>   
   <td><html:text property="cid" styleClass="formTextFieldWithoutTransparent" disabled="true"></html:text></td>
   <%}%>
   </tr>
   
   
   
          
   
   <tr>
    <td><bean:message key="label.name"></bean:message></td>
    <%if(cusobj!=null){ %> 
    <td><html:text property="name" value="<%=""+cusobj.getName() %>" styleClass="formTextFieldWithoutTransparent"></html:text> </td>
    <%}else { %>
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
    <td><html:text property="gender" value="<%=""+cusobj.getSex() %>" styleClass="formTextFieldWithoutTransparent" ></html:text></td>
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
    <td><html:text property="percentage" styleClass="formTextFieldWithoutTransparent" value="100"></html:text></td>
   </tr>
      
   <tr>
    <td>
     <html:submit styleClass="ButtonBackgroundImage" onclick="set('Submit')"><bean:message key="label.submit"></bean:message> </html:submit>
   
   </td>
    <td align="left">
    <html:reset styleClass="ButtonBackgroundImage"></html:reset>
    </td> 
   </tr>
  </table>
</html:form>
</body>
</html>