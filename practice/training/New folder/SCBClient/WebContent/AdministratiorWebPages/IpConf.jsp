

<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Amzad
  Date: Feb 13, 2009
  
  To change this template use File | Settings | File Templates.
--%>

<%@page import="masterObject.administrator.TerminalObject"%>
<%@page import="java.util.ArrayList"%>
<html>
  <head><title>IP Configuration</title>
      <center>
<h2 class="h2">IP Configuration</h2></center>
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
   alert(flagValue);
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
     
     function ipAddr_Limit()
     {
 	if(document.forms[0].ipAddr.value.length<=13)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].ipAddr.value="";
 	document.forms[0].ipAddr.focus;
 	alert("Length Limit is 13");
 	return false;
 	}
 	}
     
     function only_numbers()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57)||cha==46 ) 
		 {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};
	
	function isValidIPAddress(ipAddr){
	var re=/^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;
	if(re.test(ipAddr)){
	var part=ipAddr.split(".");
	if(parseInt(parseFloat(parts[0]))==0){
	return false;
	}
	for(var i=0;i<parts.length;i++)
	{
	if(parseInt(parseFloat(parts[i]))>255)
	{
	return false;
	}
	}
	return true;
	}
	else
	{
	return false;
	}
	}
    
    </script>
  </head>
  <body class="Mainbody" onload="return validate()">
    
    
    
     <html:form action="/Administrator/IpConf?pageId=10004">
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="validations" />
     	<html:hidden property="flag"/>
     	<html:hidden property="pageId" value="3005"/>
     	<table background="#fffccc" align="center">
     	
     	
     	<tr>
     	<%!
     	TerminalObject[] tmlObj;
     	%>
     	<%
     	ArrayList IpAddrArrayList=(ArrayList)request.getAttribute("IpAddrArrayList");
     	Object[] arrTmlObj=(Object[])request.getAttribute("TmlObj");
     	ArrayList arrlist=(ArrayList)request.getAttribute("arrlist");
     	String insertRow=(String)request.getAttribute("InsertRow");
     	%>
     	  <td align="center" style="font-family:bold;color:blue">
     	  Terminal No:
     	  </td>
     	  <td align="center" style="font-family:bold;color:blue">
     	  <html:select property="tmlNo" styleClass="formTextFieldWithoutTransparent" onchange="setFlag('from_tmlno')">
     	  <html:option value="All" style="color:blue" >All</html:option>
     	  
     	  <core:forEach var="tmlname" varStatus="count" items="${requestScope.TmlObj}" >
             <html:option value="${tmlname}" style="color:blue"><core:out value="${tmlname}"></core:out></html:option>
          </core:forEach>
                        
     	  
     	  </html:select>
     	  </td>
     	 
     	  </tr>
     	  </table>
     	<br>
     	
     	
     	
     <center>
    <div id="ipconfig" style="display:block;overflow:scroll;width:200px;height:250px">
    <table  border="1" align="center">
    <tr>
    <td style=background-color:#fffccc;font-family:bold;color:yellow">
    <b>Check</b>
    </td>
    <td style=background-color:#fffccc;font-family:bold;color:yellow">
    <b>IP Address</b>
    </td>
    </tr>
    <%
    if(insertRow!=null){
    %>
    <tr>
    <td><html:checkbox property="ckBox"></html:checkbox>
    <td><input type="text" name="ipAddr" size="15" onkeypress="return only_numbers()"></td>
    </tr>
    <%} %>
             
     <%!int c=0;
     String nam="cbx";
     %>
      <core:if test="${IpAddrArrayList!=null}">    
    <%for(int a=0;a<IpAddrArrayList.size();a++) {%>
    <tr>
    <td><html:checkbox property="ckBox" value="<%=""+a %>"></html:checkbox></td>
    <td><input type="text" name="ipAddr" size="15" value="<%=IpAddrArrayList.get(a) %>" onkeypress="return only_numbers()"></td>
    </tr>
    
    
    <%} %>
    </core:if>
    </table>
    </div>
    </center>
    
    <br>
   
    <br>
    <table name="button_table" align="center" >
     	<html:button  property="view"  onclick="setFlag('delete'); " styleClass="ButtonBackgroundImage" ><bean:message key="gl.label.delete"></bean:message> </html:button>
       <html:button  property="file" onclick="setFlag('insert')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.insert"></bean:message> </html:button>
        <html:button  property="printFile" onclick="setFlag('submit')" styleClass="ButtonBackgroundImage"><bean:message key="label.submit"></bean:message></html:button>
        <html:reset   styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message> </html:reset>
    </table>	
    
 
            </html:form>
  </body>
  </html>          