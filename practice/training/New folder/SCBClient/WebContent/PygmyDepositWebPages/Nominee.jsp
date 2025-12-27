<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@taglib prefix="core" uri="/WEB-INF/c-rt.tld" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>    
<%@page import="masterObject.general.ModuleObject"%>
<%@page import="masterObject.customer.CustomerMasterObject;"%>
<html>
<head><title>Pygmy Opening</title>
  <link href="../Images/DMTStyle.css" rel="stylesheet" type="text/css" />
      <h2 class="h2">
      <center>Nominee</center></h2>
      <hr  color="#339999">
  <script>
  function set(val){
  alert("Submittin");
  document.forms[0].nomforward.value='submit';
  }
  function numbersOnly(eve){
         var cha = event.keyCode
				if (  ( cha  > 47 && cha < 58  ) ) {
				return true ;
                
            	}
            else{
              	alert("Alphabets are not allowed!!!!!!!Please enter numbers only ");
              	return false ;
            }
        }; 
        function numberlimit(txt,size)
         {	
         	var val=txt.value;
         	if(val.length<parseInt(size))
         	{
         		return true;
         	}
         	else
         	{	
         		alert("only "+size+" digits allowed")
         		txt.value="";
         		return false;
          	}
         }
        function numbersonly_date(eve){
         var cha = event.keyCode

            if (  ( cha  >= 47 && cha < 58  ) ) {
              
                return true ;
                            }
            else{
              alert("Please enter numbers only");
              return false ;
            }
         
        
      }; 
      function dateLimit()
 	{
 	if(document.forms[0].datepropertyname.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].datepropertyname.value="";
 	document.forms[0].datepropertyname.focus;
 	alert("Enter dd/mm/yyyy Format!!!");
 	return false;
 	}
 	} 
  function only_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122) ) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
	};
	
	function number_alpha()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122)||(cha >=48 && cha <=57)||(cha >= 65 && cha <=90)|| cha==32) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
	};
	
	
	function only_for_address()
	{
		var cha =   event.keyCode;

 		if ( (cha >= 97 && cha <=122)||(cha >= 44 && cha <=58)||(cha >= 65 && cha <=90)||cha==32||cha==35) 
 		{
   			return true;
       	} else 
       	{
   			return false;
       	}
	};
  </script>    
    
  
</head>

<body class="Mainbody">
<%! CustomerMasterObject cmObj;
	String agno;
	ModuleObject[] array_module;
%>
<!--<%
System.out.println("--- m In Nomi-------");
	agno = (String)request.getAttribute("AgNum");
	System.out.println("--- m In Nomi-------"+agno);
	String vis=(String)request.getAttribute("vis");
%>

--><html:form action="/Pygmy/PygmyOpening?PageId=8023">

<html:hidden property="hidval" value="<%=agno%>"/>
  <table class="txtTable">
  <html:hidden property="nomforward"></html:hidden>
  
  <html:hidden property="nomvalidations"></html:hidden>
   <!--<tr>
    <td><bean:message key="label.actype"></bean:message></td>
    <td><html:select property="actype" styleClass="formTextFieldWithoutTransparent" onblur="checkAcno()">
               		<html:option value="SELECT"></html:option>
               		<%array_module=(ModuleObject[])request.getAttribute("PygmyAcType");
               			for(int i=0;i<array_module.length;i++){%>
               				<html:option value="<%= array_module[i].getModuleCode()%>"><%= array_module[i].getModuleAbbrv()%></html:option>
               		<%} %>
               		
             </html:select></td>     
    
    <td><bean:message key="label.acno"></bean:message></td>
    <td><html:text property="acno" value="<%=agno%>" styleClass="formTextFieldWithoutTransparent"></html:text></td>
   </tr>
   
   --><tr>
    <td>Has Account</td>
    <td><html:checkbox property="has_ac" styleClass="formTextFieldWithoutTransparent"></html:checkbox>
     </td>
     
   </tr>
   
  <tr>
    <td><bean:message key="label.custid"></bean:message></td>
    
    	
    	<td><html:text property="cidis"  styleClass="formTextFieldWithoutTransparent" onblur="submit()" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'6')"></html:text> </td>
   
   </tr>	
           
   <tr>
    <td><bean:message key="label.name"></bean:message></td>
   
    <td><html:text property="nomname"  styleClass="formTextFieldWithoutTransparent" onkeypress="return number_alpha()"></html:text> </td>
   
    
   </tr>
   
   <tr>
     <td><bean:message key="label.dob"></bean:message> </td>
     
     <td><html:text property="dob"  styleClass="formTextFieldWithoutTransparent" onkeypress="return numbersonly_date(this)" onkeydown="return dateLimit()"></html:text> </td>
    
   </tr>
   
   <tr>
    <td><bean:message key="label.gender"></bean:message></td>
    
    <td><html:text property="gender" styleClass="formTextFieldWithoutTransparent" onkeypress="return number_alpha()"></html:text></td>
   
   </tr>
   
   <tr>
    <td><bean:message key="label.address"></bean:message></td>
     
    <td><html:textarea property="address" styleClass="formTextFieldWithoutTransparent" onkeypress="return only_for_address()"></html:textarea></td>
     
   </tr>
   
   <tr>
     <td><bean:message key="label.rel"></bean:message></td>
     <td><html:text property="rel_ship"  styleClass="formTextFieldWithoutTransparent" onkeypress="return number_alpha()"></html:text></td>
   </tr>

   <tr>
    <td><bean:message key="label.percentage"></bean:message></td>
    <td><html:text property="percentage"  styleClass="formTextFieldWithoutTransparent" onkeypress=" return numbersOnly()" onkeyup="numberlimit(this,'6')"></html:text></td>
   </tr>
      
   <!--<tr>
    <td>
     <html:submit  onclick="set('submit'); " styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message> </html:submit>
   </td>
   </tr>
    <td align="left">
    <html:reset styleClass="ButtonBackgroundImage"></html:reset>
    </td> 
   </tr>
 --></table>
</html:form>
</body>

</html>