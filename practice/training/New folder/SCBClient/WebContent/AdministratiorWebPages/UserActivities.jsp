

<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  User: Amzad
  Date: Feb 16, 2009
  
  To change this template use File | Settings | File Templates.
--%>

<%@page import="masterObject.administrator.TerminalObject"%>
<%@page import="java.util.ArrayList"%>
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
     
    
    </script>
  </head>
  <body class="Mainbody" onload="return validate()">
    
    
    
     <html:form action="/Administrator/UserActivity?pageId=10006">
     	<html:hidden property="defaultSignIndex" value="0"/>
       	<html:hidden property="validations" />
     	<html:hidden property="flag"/>
     	<html:hidden property="pageId" value="3005"/>
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
     	<td Style="font-family:bold;color:brown">From Date: <html:text property="frmDate" style="color:blue" size="10"></html:text></td>
     	<td Style="font-family:bold;color:brown">To Date: <html:text property="toDate" style="color:blue" size="10"></html:text></td>
     	<%} %>
     	</tr>
     	<tr>
     	<td><html:checkbox property="ipAddr" value="IpAddress" onclick="setFlag('UserChoice')"><b><font color="brown">IPAddress</font></b></html:checkbox></td>
     	<%if(arl!=null){ %>
     	<td>&nbsp;</td><td>&nbsp;</td>
     	<td Style="font-family:bold;color:brown">IPAddress:<html:select property="ipAddrValue" styleClass="formTextFieldWithoutTransparent">
     	<core:if test="${requestScope.IpAddress!=null}">
     	<core:forEach var="arl" items="${requestScope.IpAddress}">
     	<html:option value="${arl}" style="color:blue"><core:out value="${arl}"></core:out></html:option>
          </core:forEach>
     	</core:if>
     	
     	</html:select>
     	</td>
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
     	<tr>
     	<td><html:checkbox property="tml" value="tml" onclick="setFlag('UserChoice')"><b><font color="brown">Code </font></b></html:checkbox>
     	</td>
     	<%if(tmlno!=null){ %>
     	<td>&nbsp;</td><td>&nbsp;</td>
     	<td Style="font-family:bold;color:brown">Tml<html:select property="tmlNo" styleClass="formTextFieldWithoutTransparent">
     	<core:if test="${requestScope.TerminalNoArray!=null}">
     	<core:forEach var="tmlno" items="${requestScope.TerminalNoArray}">
     	<html:option value="${tmlno}" style="color:blue"><core:out value="${tmlno}"></core:out></html:option>
          </core:forEach>
     	</core:if>
     	</html:select></td>
     	<%} %>
     	</tr>
     	
     	</table>
     	<br>
     	<table name="button_table">
    <tr>
     	<td><html:button  property="srch" onclick="setFlag('search')" styleClass="ButtonBackgroundImage"><bean:message key="label.search"></bean:message></html:button></td>
     	<td><html:button  property="printFile" onclick="window.print() " styleClass="ButtonBackgroundImage"><bean:message key="gl.label.print"></bean:message></html:button></td>
        <td><html:reset   styleClass="ButtonBackgroundImage"><bean:message key="gl.label.clear"></bean:message> </html:reset></td>
        </tr>
    </table>
     	
     	
    
     <div id="userActivity" style="display:block;overflow:scroll;width:700px;height:250px">
    <table  border="1">
    <tr>
    <td  align="center" Style="font-family:bold;color:brown">
    <b>User Id</b>
    </td>
    <td  align="center" Style="font-family:bold;color:brown">
    <b>Terminal No</b>
    </td>
    <td  align="center" Style="font-family:bold;color:brown">
    <b>IP Address</b>
    </td>
    <td  align="center" Style="font-family:bold;color:brown">
    <b>Menu Path</b>
    </td>
    <td  align="center" Style="font-family:bold;color:brown">
    <b>Activity</b>
    </td>
    <td align="center" Style="font-family:bold;color:brown">
    <b>Ac Type</b>
    </td>
    <td  align="center" Style="font-family:bold;color:brown">
    <b>Ac No</b>
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
    		 if(count!=10){%>
    		
    		 <tr bordercolor="none">
    		 <%for(int k=0;k<10;k++){
    			 if(userActivObj[j][k]!=null){
    	%> 
    	  
    	 
    	 <td bordercolor="none" ><%=userActivObj[j][k]%>
    	 </td>

    	 <%}else
    		 count++;
    			 }%>
    	 
    	 </tr>
    	 
    	 <%}
    		 }
    }
    	 %>

        
              
    </table>
    </div>
  </html:form>
  </body>
  </html> 
    
    	
    
    	
    
          