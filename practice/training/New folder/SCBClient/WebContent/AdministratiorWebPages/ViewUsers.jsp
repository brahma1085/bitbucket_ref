

<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%@page import="java.util.Map"%>
<%--
 
  
  To change this template use File | Settings | File Templates.
--%>

<html>
  <head><title>Viewing User Information</title>
    <center>
<h2 class="h2">View User Information</h2></center>
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
     }; 
     
     function uid_Limit()
 	{
 	if(document.forms[0].uid.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].uid.value="";
 	document.forms[0].uid.focus;
 	alert("Enter not more than 10 characters");
 	return false;
 	}
 	}
 	
 	function frmdate_Limit()
 	{
 	if(document.forms[0].frmDate.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].frmDate.value="";
 	document.forms[0].frmDate.focus;
 	alert("Enter dd/mm/yyyy Format!!");
 	return false;
 	}
 	}
 	
 	function todate_Limit()
 	{
 	if(document.forms[0].toDate.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].toDate.value="";
 	document.forms[0].toDate.focus;
 	alert("Enter dd/mm/yyyy Format!!");
 	return false;
 	}
 	}
     
     function only_numbers()
	{
		var cha =   event.keyCode;
		 if ( (cha >= 48 && cha <=57)||cha==47 ) 
		 {
   		 	return true;
          } 
          else 
          {
   			return false;
          }
	};
	
	function clearFunc()
    {
    	
    	var ele=document.forms[0].elements;
    	  
    	for(var i=0;i<ele.length;i++)
    	{
    		if(ele[i].type=="text")
    		{ 
    			ele[i].value="";
    		}
    		if(ele[i].type=="password")
    		{ 
    			ele[i].value="";
    		}
    		if(ele[i].type=="hidden"){
       ele[i].value="";
       }
    	}
    
    };
    
    </script>
  </head>
  <body class="Mainbody" onload="return validate()">
    <%!
    Map user_role;
    String access;
    %>
    <%
    String accesspageId=(String)request.getAttribute("accesspageId");
	user_role=(Map)request.getAttribute("user_role");
	
	
	if(user_role!=null&&accesspageId!=null){
		if(user_role.get(accesspageId)!=null){
			access=(String)user_role.get(accesspageId);
			System.out.println("access-----In SBOpening------>"+access);
		}

		
		}
    
    %>
    <%if(access!=null&&accesspageId!=null&&access.toCharArray()[0]=='1'){%>
    
     <html:form action="/Administrator/ViewUsers?pageId=10003">
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="validations" />
     	<html:hidden property="flag"/>
     	<html:hidden property="pageId" value="3005"/>
     	<table background="#fffccc" align="center">
     	<tr>
     	  <td colspan="2">&nbsp;</td>
     	  <td align="center" style="font-family:bold;color:blue">
     	  <html:radio property="userChoice" value="All" onclick="setFlag('from_radio')"><b>All</b></html:radio>
     	  </td>
     	  <td align="center" style="font-family:bold;color:blue">
     	  <html:radio property="userChoice" value="Select" onclick="setFlag('from_radio')"><b>Select</b></html:radio>
     	  </td>
     	</tr>
     	<%!
     	String dispUidFld;
     	
     	%>
     	<%
     	dispUidFld=(String)request.getAttribute("UserChoice");
     	%>
     	<tr>
     	<%
     	if(dispUidFld!=null){
     	if(dispUidFld.equalsIgnoreCase("Select"))	
     	{
     	%>
     	  <td align="center" style="font-family:bold;color:blue">
     	  User Id:
     	  </td>
     	  <td align="center" style="font-family:bold;color:blue">
     	  <html:text property="uid" size="10" onkeydown="return uid_Limit()"></html:text>
     	  </td>
     	  <%}
     	else{
     	%>
     	<td align="center" style="font-family:bold;color:blue">
     	  User Id:
     	  </td>
     	  <td align="center" style="font-family:bold;color:blue">
     	  <html:text property="uid" size="10" disabled="true" style="background-color:grey;"></html:text>
     	  </td>
     	  <%
     	}
     	}
     	  %>
     	  <td colspan="2">&nbsp;</td>
     	</tr>
     	
     	<tr>
     	  <td align="center" style="font-family:bold;color:blue">
     	  From Date:
     	  </td>
     	  <td align="center" style="font-family:bold;color:blue">
     	  <html:text property="frmDate" size="10" onkeypress="return only_numbers()" onkeydown="return frmdate_Limit()"></html:text>
     	  </td>
     	  <td align="center" style="font-family:bold;color:blue">
     	  To Date:
     	  </td>
     	  <td align="center" style="font-family:bold;color:blue">
     	  <html:text property="toDate" size="10" onkeypress="return only_numbers()" onkeydown="return todate_Limit()"></html:text>
     	  </td>
     	</tr>
     	
     	
     	</table>
     	
     	<table name="button_table" align="center">
     	<html:button  property="view"  onclick="setFlag('view'); " styleClass="ButtonBackgroundImage" ><bean:message key="gl.label.view"></bean:message> </html:button>
       <!--<html:button  property="file" onclick="set('file')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.file"></bean:message> </html:button>
        --><html:button  property="printFile" onclick="window.print() " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.print"></bean:message></html:button>
        <html:button property="reset" onclick="setFlag('clearFunc()')" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message> </html:button>
     	
     	</table>
    <%!
    int count=0;
    %> 	
    <%
    Object[][] arrayObj=(Object[][])request.getAttribute("ArrayObject");
    if(arrayObj!=null){
    %> 
     <div id="viewusers" style="display:block;overflow:scroll;width:700px;height:250px">
    <table  border="1">
    <tr>
    <td style=background-color:#fffccc;font-family:bold;color:yellow">
    <b>User Id</b>
    </td>
    <td style=background-color:#fffccc;font-family:bold;color:yellow">
    <b>CId</b>
    </td>
    <td style=background-color:#fffccc;font-family:bold;color:yellow">
    <b>ShortName</b>
    </td>
    <td style=background-color:#fffccc;font-family:bold;color:yellow">
    <b>pwd expiring period</b>
    </td>
    <td style=background-color:#fffccc;font-family:bold;color:yellow">
    <b>pwd expiring date</b>
    </td>
    <td style=background-color:#fffccc;font-family:bold;color:yellow">
    <b>thump_ipm</b>
    </td>
    <td style=background-color:#fffccc;font-family:bold;color:yellow">
    <b>Operative from date</b>
    </td>
    <td style=background-color:#fffccc;font-family:bold;color:yellow">
    <b>Operative to date</b>
    </td>
    <td style=background-color:#fffccc;font-family:bold;color:yellow">
    <b>disable</b>
    </td>
    <td style=background-color:#fffccc;font-family:bold;color:yellow">
    <b>tmlName</b>
    </td>
    <td style=background-color:#fffccc;font-family:bold;color:yellow">
    <b>de_User</b>
    </td>
    <td style=background-color:#fffccc;font-family:bold;color:yellow">
    <b>de_date</b>
    </td>
    </tr>
    
    <%for(int j=0;j<arrayObj.length;j++){
	 if(count!=12){%>
	
	 <tr bordercolor="none">
	 <%for(int k=0;k<12;k++){
		 if(arrayObj[j][k]!=null){
%> 
  
 
 <td bordercolor="none" >
 <core:if test="<%=arrayObj[j][k]!=null %>">
 <%=arrayObj[j][k]%>
 </core:if>
 </td>

 <%}else
	 count++;
		 }%>
 
 </tr>
 
 <%}
	 }
 %>
 
    
    
    </table>
    </div>
    
    	
    <%} %>	
 
            </html:form>
            <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
  </body>
  </html>          