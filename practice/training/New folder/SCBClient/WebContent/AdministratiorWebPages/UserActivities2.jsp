

<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
 
  
  To change this template use File | Settings | File Templates.
--%>

<%@page import="masterObject.administrator.TerminalObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<html>
  <head><title>User Activities</title>
    <center>
<h2 class="h2">User Activities</h2></center>
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
     
    function frmDate_Limit()
    {
 	if(document.forms[0].frmDate.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].frmDate.value="";
 	document.forms[0].frmDate.focus;
 	alert("Enter dd/mm/yyyy Format!!!");
 	return false;
 	}
 	}
 	
 	function toDate_Limit()
    {
 	if(document.forms[0].toDate.value.length<=10)
 	{
 	return true;
 	}
 	else
 	{
 	document.forms[0].toDate.value="";
 	document.forms[0].toDate.focus;
 	alert("Enter dd/mm/yyyy Format!!!");
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
     
     <html:form action="/Administrator/UserActivity?pageId=10006" focus="<%=(String)request.getAttribute("FocusTo") %>">
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="validations" />
     	<html:hidden property="flag"/>
     	<html:hidden property="tmlNo" value="CA99"/>
     	<html:hidden property="tml" value="tml"/>
     	<html:hidden property="pageId" value="3005"/>
     	<html:hidden property="ipAddrValue" value="127.0.0.1"/>
     	<!--<html:hidden property="ipAddrValue" value="192.168.1.22"/>
     	--><html:hidden property="ipAddr" value="IpAddress"/>
     	<%!
     	int count=0;
     	%>
     	<%
     	String showDt=(String)request.getAttribute("ShowDates");
     	String[] user=(String[])request.getAttribute("UserDetails");
     	String[] tmlno=(String[])request.getAttribute("TerminalNoArray");
     	ArrayList arl=(ArrayList)request.getAttribute("IpAddress");
     	Object[][] userActivObj=(Object[][])request.getAttribute("UserActivityObj");
     	
     	
     	%>
     	<table background="#fffccc">
     	<thead Style="font-family:bold;color:brown">Search According to</thead>
     	<tr>
     	<td><html:checkbox property="ckDate" value="Date" onclick="setFlag('UserChoice')"><b><font color="brown">Date</font></b></html:checkbox></td>
     	<%if(showDt!=null){ %>
     	<td>&nbsp;</td><td>&nbsp;</td>
     	<td Style="font-family:bold;color:brown">From Date: <html:text property="frmDate" style="color:blue" size="10" onkeypress="return only_numbers()" onkeydown="return frmDate_Limit()" ></html:text></td>
     	<td Style="font-family:bold;color:brown">To Date: <html:text property="toDate" style="color:blue" size="10" onkeypress="return only_numbers()" onkeydown="return toDate_Limit()" ></html:text></td>
     	<%} %>
     	</tr>
     	
     	<tr>
     	<td><html:checkbox property="uid" value="userid" onclick="setFlag('UserChoice')"><b><font color="brown">UserId</font></b></html:checkbox>
     	
     	</td>
     	<%if(user!=null){ %><td>&nbsp;</td><td>&nbsp;</td>
     	<td Style="font-family:bold;color:brown">UserId:<html:select property="uidValue" styleClass="formTextFieldWithoutTransparent">
     	<core:if test="${requestScope.UserDetails!=null}">
     	<core:forEach var="usr" items="${requestScope.UserDetails}">
     	<html:option value="${usr}" style="color:blue"><core:out value="${usr}"></core:out></html:option>
          </core:forEach>
     	</core:if>
     	</html:select></td>
     	<%} %>
     	</tr>
     	
     	
     	</table>
     	<br>
     	<table name="button_table">
    <tr>
     	<td><html:button  property="srch" onblur="submit()" onclick="setFlag('search')" styleClass="ButtonBackgroundImage"><bean:message key="label.search"></bean:message></html:button></td>
     	<td><html:button  property="printFile" onclick="window.print() " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.print"></bean:message></html:button></td>
        <td><html:button  property="clear" onclick="clearFunc()" styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message> </html:button></td>
        </tr>
    </table>
     	
     	
    
     <div id="userActivity" style="display:block;overflow:scroll;width:700px;height:250px">
    <table  border="1">
    <tr>
    <td  align="center" Style="font-family:bold;color:brown">
    <b>User Id</b>
    </td>
    
    <td  align="center" Style="font-family:bold;color:brown">
    <b>Menu Path</b>
    </td>
    <td  align="center" Style="font-family:bold;color:brown">
    <b>Activity</b>
    </td>
    
    <!--<td  align="center" Style="font-family:bold;color:brown">
    <b>Cid</b>
    </td>
    --><td  align="center" Style="font-family:bold;color:brown">
    <b>Activity Date</b>
    </td>
    <td  align="center" Style="font-family:bold;color:brown">
    <b>Activity Time</b>
    </td>
    </tr>
    <%
    if(userActivObj!=null){
    	for(int j=0;j<userActivObj.length;j++){
    		 %>
    		
    		 <tr bordercolor="none">
    		 
    	  
    	 
    	 <td bordercolor="none" ><%=userActivObj[j][0]%>
    	 </td>
          <td bordercolor="none" ><%=userActivObj[j][3]%>
    	 </td>
    	  <td bordercolor="none" ><%=userActivObj[j][4]%>
    	 </td>
    	  <td bordercolor="none" ><%=userActivObj[j][8]%>
    	 </td>
    	 <td bordercolor="none" ><%=userActivObj[j][9]%>
    	 </td>
    	 <%}
    			 %>
    	 
    	 </tr>
    	 
    	 <%}
    		 
    
    	 %>

        
              
    </table>
    </div>
  </html:form>
  
  <%}else{ %>
        <font color="red"><bean:message key="label.accessdenied"></bean:message></font>
        <%} %>
  </body>
  </html> 
    
    	
    
    	
    
          